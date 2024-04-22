package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.ProductOrder;
import com.facenet.mrp.repository.mrp.*;
import com.facenet.mrp.repository.sap.CoittRepository;
import com.facenet.mrp.repository.sap.OitwRepository;
import com.facenet.mrp.repository.sap.Por1Repository;
import com.facenet.mrp.repository.sap.Prq1Repository;
import com.facenet.mrp.service.dto.AdvancedMrpDTO;
import com.facenet.mrp.service.dto.mrp.*;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.mapper.MrpAnalyticsMapper;
import com.facenet.mrp.service.utils.Constants;
import com.facenet.mrp.service.utils.MrpAnalysisUtil;
import com.facenet.mrp.service.utils.Utils;
import com.facenet.mrp.thread.CloneBomService;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MrpBasicAnalysisService {
    private static final Logger log = LogManager.getLogger(MrpBasicAnalysisService.class);
    private final CoittRepository coittRepository;
    private final MrpAnalyticsMapper mrpAnalyticsMapper;
    private final ProductOrderDetailRepository poDetailRepository;
    private final MrpRepository mrpRepository;
    private final SapOnOrderSummaryRepository soosRepository;
    private final OitwRepository oitwRepository;
    private final MrpSubRepository mrpSubRepository;
    private final MrpBomDetailRepository mrpBomDetailRepository;
    private final ItemHoldRepository itemHoldRepository;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat simpleDateFormatByMonth = new SimpleDateFormat("yyyy-MM");
    private final PurchaseRecommendationPlanRepository purchaseRecommendationPlanRepository;
    private final ForecastOrderDetailRepository forecastOrderDetailRepository;
    private final ProductOrderRepository productOrderRepository;
    private final Prq1Repository prq1Repository;
    private final Por1Repository por1Repository;
    private final CloneBomService bomService;
    public MrpBasicAnalysisService(CoittRepository coittRepository, MrpAnalyticsMapper mrpAnalyticsMapper, ProductOrderDetailRepository poDetailRepository, MrpRepository mrpRepository, SapOnOrderSummaryRepository soosRepository, OitwRepository oitwRepository, MrpSubRepository mrpSubRepository, MrpBomDetailRepository mrpBomDetailRepository, ItemHoldRepository itemHoldRepository,
                                   PurchaseRecommendationPlanRepository purchaseRecommendationPlanRepository,
                                   ForecastOrderDetailRepository forecastOrderDetailRepository,
                                   ProductOrderRepository productOrderRepository,
                                   Prq1Repository prq1Repository,
                                   Por1Repository por1Repository, CloneBomService bomService) {
        this.coittRepository = coittRepository;
        this.mrpAnalyticsMapper = mrpAnalyticsMapper;
        this.poDetailRepository = poDetailRepository;
        this.mrpRepository = mrpRepository;
        this.soosRepository = soosRepository;
        this.oitwRepository = oitwRepository;
        this.mrpSubRepository = mrpSubRepository;
        this.mrpBomDetailRepository = mrpBomDetailRepository;
        this.itemHoldRepository = itemHoldRepository;
        this.purchaseRecommendationPlanRepository = purchaseRecommendationPlanRepository;
        this.forecastOrderDetailRepository = forecastOrderDetailRepository;
        this.productOrderRepository = productOrderRepository;
        this.prq1Repository = prq1Repository;
        this.por1Repository = por1Repository;
        this.bomService = bomService;
    }

    /**
     * @param input
     * @return
     */
    public ResponseEntity basicAnalysisMRP(MrpAnalyticsInput input) throws ParseException {

        List<MrpDetailDTO> mrpDetailDTOList = new ArrayList<>();
        HashMap<Integer, MrpItemQuantityDTO> mrpItemQuantityHM = new HashMap<>();
        Map<String, Double> inStockQuantityMap = new HashMap<>();
        Map<String, Double> poQuantityMap = new HashMap<>();
        Map<String, Double> prQuantityMap = new HashMap<>();
        Map<String, List<ItemQuantityWithDate>> itemHoldQuantityByWhs = new HashMap<>();
        Map<String, List<ItemQuantityWithDate>> itemHoldQuantity = new HashMap<>();
        Map<String, List<ItemQuantityWithDate>> itemOnOrderQuantityMap = new HashMap<>();
        Map<String,  List<CurrentWarehouseInventory>> inStockQuantityMapWithWhsCode = new HashMap<>();

        AdvancedMrpDTO mrpDTO;
        String mrpCode;
        String mrpSubCode;

        Date dNow = new Date();

        //Check xem co phai chay phan tich lan dau khong
        if ((input.getMrpCode() == null && input.getMrpSubCode() == null) || (input.getMrpCode().isBlank() && input.getMrpSubCode().isBlank())) {
            //Lấy count của mã MRP
            int mrpCodeCount = mrpRepository.mrpCodeCount() + 1;

            //Tự sinh mã MRP gốc chạy lần đầu
            mrpCode = "RAL-MRP-" + mrpCodeCount;

            //Tự sinh mã MRP Sub code lần đầu
            mrpSubCode = "RAL-MRP-" + mrpCodeCount + ".1";

        } else {
            String currentDate = simpleDateFormat.format(new Date());
            //Tự sinh mã MRP gốc chạy lại kịch bản
            mrpCode = input.getMrpCode();

            //Tự sinh mã sub MRP chạy lại kịch bản
            int subCodeCount = mrpSubRepository.getMrpSubCodeCount(mrpCode) + 1;
            mrpSubCode = mrpCode + "." + subCodeCount;

            log.info("input.getTimeEnd(): {}", input.getTimeEnd());

            if (input.getTimeEnd().before(simpleDateFormat.parse(currentDate))) {
                throw new CustomException("time.end.before.time.start");
            }

//            input.setTimeStart(simpleDateFormat.parse(currentDate));
        }

        // Check order type
        ProductOrder order = productOrderRepository.findProductOrderByProductOrderCodeAndIsActive(input.getSoCode(), (byte) 1);

        //Query trong mrp lấy số lượng yêu cầu của sản phẩm trong danh sách sản phẩm
        List<MrpItemQuantityDTO> quantities = !"Forecast".equals(order.getType()) ?
            poDetailRepository.getQuantity(input.getListItemId(), input.getSoCode()) :
            forecastOrderDetailRepository.getQuantity(input.getListItemId(), input.getSoCode());

        Set<String> itemList = new HashSet<>();
        if (!CollectionUtils.isEmpty(quantities)) {
            for (MrpItemQuantityDTO dto : quantities) {
                mrpItemQuantityHM.put(dto.getItemId(), dto);
                itemList.add(dto.getItemCode());
            }
        }

        // Get MRP Bom
        Map<String, List<MrpDetailDTO>> additionalItemInBomMap = new HashMap<>();
        List<MrpDetailDTO> additionalItemInBom = mrpBomDetailRepository.getMrpDetailNotInBom(input.getSoCode());
        if (!CollectionUtils.isEmpty(additionalItemInBom))
            additionalItemInBomMap = additionalItemInBom.stream().collect(Collectors.groupingBy(MrpDetailDTO::getParentPath));


        // Lấy số lượng item bị hold
        itemHoldQuantityByWhs = itemHoldRepository.getAllHoldQuantityItemMapByWhs(
            input.getListAnalysisWhs(),
            DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH),
            input.getTimeEnd(),
            input.getAnalysisPeriod()
        );
        if (input.getAnalysisType() == Constants.AnalysisType.NEW) {
            itemHoldQuantity = itemHoldRepository.getAllHoldQuantityItemMap(
                input.getListAnalysisWhs(),
                DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH),
                input.getTimeEnd()
            );
        }

        // Lấy tổng số lượng PR ở MRP
        if (input.getAnalysisOption().contains("PR/PO")) {
            itemOnOrderQuantityMap = purchaseRecommendationPlanRepository.sumAllQuantityOfItemsByDayMap(input.getTimeStart(), input.getTimeEnd());
            prQuantityMap = prq1Repository.getOpenQuantityMap();
            poQuantityMap = por1Repository.getOpenQuantityMap();
        }

        if (input.getAnalysisOption().contains("kho")) {
            //Lấy số lượng hiện trạng tồn kho của list NVL
            List<CurrentInventory> inStockQuantity = oitwRepository.getAllCurrentInventoryByWhs(input.getListAnalysisWhs());
            inStockQuantityMap = inStockQuantity.stream().collect(Collectors.toMap(CurrentInventory::getItemCode, CurrentInventory::getCurrentQuantity));

            List<CurrentWarehouseInventory> currentWarehouseInventories = oitwRepository.getAllCurrentInventoryWithWhs();
            inStockQuantityMapWithWhsCode = currentWarehouseInventories.stream().collect(Collectors.groupingBy(CurrentInventory::getItemCode));
        }

        //Phân tích từng sản phẩm trong đơn hàng
        for (Integer itemId : input.getListItemId()) {
            MrpDetailDTO mrpDetailDTO = analysisSingleProduct(
                mrpItemQuantityHM.get(itemId),
                additionalItemInBomMap,
                inStockQuantityMap,
                inStockQuantityMapWithWhsCode,
                prQuantityMap,
                poQuantityMap,
                itemHoldQuantity,
                itemHoldQuantityByWhs,
                itemOnOrderQuantityMap,
                input.getTimeStart(),
                input.getTimeEnd(),
                input.getListAnalysisWhs(),
                input.getAnalysisPeriod(),
                input.getAnalysisType(),
                input.getSoCode(),
                input.getAnalysisOption()
            );
            if (mrpDetailDTO.getStatus() != null
            && mrpDetailDTO.getStatus().equals("NOT_BOM")){
                return ResponseEntity.ok(new CommonResponse<AdvancedMrpDTO>()
                    .isOk(false)
                    .message("Mã: "+mrpDetailDTO.getItemCode()+" chưa có BOM hoặc BOM không active, Hãy kiểm tra lại trước khi phân tích.")
                    .errorCode("400")
                );
            }else {
                mrpDetailDTOList.add(mrpDetailDTO);
            }
        }

        //add biến cần vào mrpDtoMapper
        mrpDTO = mrpAnalyticsMapper.inputToDto(input, mrpCode, mrpSubCode, dNow, mrpDetailDTOList);
        return ResponseEntity.ok(new CommonResponse<AdvancedMrpDTO>()
            .isOk(true)
            .message("Success")
            .errorCode("00")
            .data(mrpDTO)
        );
    }

    /**
     * @param mrpItem
     * @param timeStart
     * @param timeEnd
     * @param listWhs
     * @param analysisPeriod
     * @return
     */
    public MrpDetailDTO analysisSingleProduct(
        MrpItemQuantityDTO mrpItem,
        Map<String, List<MrpDetailDTO>> additionalItemInBomMap,
        Map<String, Double> inStockQuantityMap,
        Map<String, List<CurrentWarehouseInventory>> inStockQuantityMapWithWhsCode,
        Map<String, Double> prQuantityMap,
        Map<String, Double> poQuantityMap,
        Map<String, List<ItemQuantityWithDate>> itemHoldQuantity,
        Map<String, List<ItemQuantityWithDate>> itemHoldQuantityByWhs,
        Map<String, List<ItemQuantityWithDate>> itemOnOrderQuantityMap,
        Date timeStart, Date timeEnd,
        List<String> listWhs,
        String analysisPeriod,
        Integer analysisType,
        String soCode,
        List<String> analysisOptions) throws ParseException {
        log.info("/***************************** Vào hàm phân tích MRP cho lần đầu tiên *****************************/");
        MrpResultDTO mrpResultDTO;
        MrpDetailDTO mrpDetailDTO;

        List<MrpResultDTO> mrpResultDTOList = new ArrayList<>();
        List<MrpDetailDTO> detailDTOS = new ArrayList<>();

        String whsName = String.join(";", listWhs);
        String parentPath;
        int countLevel = 1;
        Calendar calStartTime = Calendar.getInstance();
        Calendar calEndTime = Calendar.getInstance();
        Calendar calCheck = Calendar.getInstance();

        //Query trong sap lấy thông tin cần thiết của sản phẩm
        //TODO: Optimize
        MrpDetailDTO itemInfo = bomService.getProduct().get(Utils.toItemKey(mrpItem.getItemCode(), mrpItem.getBomVersion()));
        if (itemInfo == null){
            MrpDetailDTO mrpDetailDTOFake = new MrpDetailDTO();
            mrpDetailDTOFake.setItemCode(mrpItem.getItemCode());
            mrpDetailDTOFake.setStatus("NOT_BOM");
            return mrpDetailDTOFake;
        }
        mrpDetailDTO = new MrpDetailDTO(itemInfo);

        //Lấy số lượng hiện trạng tồn kho
        mrpResultDTO = new MrpResultDTO();
        mrpResultDTO.setLandmark(Constants.MrpAnalysis.SAP_QUANTITY_STRING);
        mrpResultDTO.setOriginQuantity(inStockQuantityMap.getOrDefault(mrpItem.getItemCode(), 0.0));

        mrpResultDTOList.add(mrpResultDTO);

        //lấy result phân tích theo ngày ở mức "TP"
        //Check xem chu kỳ phân tích là ngày / tuần / tháng?
        // phân tích thành phảm theo chu kỳ
        log.info("den vong lap thoi gian san pham");
        calStartTime.setTime(timeStart);
        calEndTime.setTime(timeEnd);
        while (calStartTime.getTime().before(calEndTime.getTime()) || calStartTime.getTime().equals(calEndTime.getTime())) {
            if (analysisPeriod.equalsIgnoreCase("ngày")) {
                mrpResultDTO = new MrpResultDTO();
                mrpResultDTO.setLandmark(simpleDateFormat.format(calStartTime.getTime()));
                mrpResultDTO.setOriginQuantity(0.0);
                mrpResultDTOList.add(mrpResultDTO);

                log.info("Vao vong lap ngay");

                calStartTime.add(Calendar.DATE, 1);

            } else if (analysisPeriod.equalsIgnoreCase("tuần")) {
                if (calStartTime.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || simpleDateFormat.format(calStartTime.getTime()).equals(simpleDateFormat.format(calEndTime.getTime()))) {
                    mrpResultDTO = new MrpResultDTO();
                    mrpResultDTO.setLandmark(simpleDateFormat.format(calStartTime.getTime()));
                    mrpResultDTO.setOriginQuantity(0.0);
                    mrpResultDTOList.add(mrpResultDTO);
                }
                calStartTime.add(Calendar.DATE, 1);

            } else if (analysisPeriod.equalsIgnoreCase("tháng")) {
                if (calStartTime.get(Calendar.DATE) == calStartTime.getActualMaximum(Calendar.DATE) || simpleDateFormat.format(calStartTime.getTime()).equals(simpleDateFormat.format(calEndTime.getTime()))) {
                    mrpResultDTO = new MrpResultDTO();
                    mrpResultDTO.setLandmark(simpleDateFormat.format(calStartTime.getTime()));
                    mrpResultDTO.setOriginQuantity(0.0);
                    mrpResultDTOList.add(mrpResultDTO);
                }
                calStartTime.add(Calendar.DATE, 1);

            } else if (analysisPeriod.equalsIgnoreCase("1/2 tuần")) {
                if (calStartTime.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY || calStartTime.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || simpleDateFormat.format(calStartTime.getTime()).equals(simpleDateFormat.format(calEndTime.getTime()))) {
                    mrpResultDTO = new MrpResultDTO();
                    mrpResultDTO.setLandmark(simpleDateFormat.format(calStartTime.getTime()));
                    mrpResultDTO.setOriginQuantity(0.0);
                    mrpResultDTOList.add(mrpResultDTO);
                }
                calStartTime.add(Calendar.DATE, 1);

            } else if (analysisPeriod.equalsIgnoreCase("2 tuần")) {
                if (calStartTime.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    calStartTime.add(Calendar.DATE, 7);

                    mrpResultDTO = new MrpResultDTO();
                    mrpResultDTO.setLandmark(simpleDateFormat.format(calStartTime.getTime()));
                    mrpResultDTO.setOriginQuantity(0.0);
                    mrpResultDTOList.add(mrpResultDTO);

                    calCheck.setTime(calStartTime.getTime());
                    calCheck.add(Calendar.DATE, 14);
                    if (calCheck.getTime().after(calEndTime.getTime())) {
                        calStartTime.add(Calendar.DATE, calEndTime.get(Calendar.DAY_OF_YEAR) - calStartTime.get(Calendar.DAY_OF_YEAR));
                    } else {
                        calStartTime.add(Calendar.DATE, 7);
                    }
                } else if (simpleDateFormat.format(calStartTime.getTime()).equals(simpleDateFormat.format(calEndTime.getTime()))) {
                    mrpResultDTO = new MrpResultDTO();
                    mrpResultDTO.setLandmark(simpleDateFormat.format(calStartTime.getTime()));
                    mrpResultDTO.setOriginQuantity(0.0);
                    mrpResultDTOList.add(mrpResultDTO);

                    calStartTime.add(Calendar.DATE, 1);
                } else {
                    calStartTime.add(Calendar.DATE, 1);
                }
            }

        }
        log.info("Số lượng ngày phân tích: {}", mrpResultDTOList.size());

        //query lấy children của sản phẩm TP
        List<MrpDetailDTO> childrenBom = bomService.getBomTree().get(Utils.toItemKey(mrpItem.getItemCode(), mrpDetailDTO.getBomVersion()));
        if (!CollectionUtils.isEmpty(childrenBom)) {
            for (MrpDetailDTO detailDTO : childrenBom) {
                mrpDetailDTO.getChildren().add(new MrpDetailDTO(detailDTO));
            }
        }

        //Merge children trong DB MRP để thêm các NVL/BTP không theo bom
        parentPath = "_" + mrpItem.getItemCode();
        MrpAnalysisUtil.addMrpBomToItem(mrpDetailDTO, additionalItemInBomMap.get(parentPath));
        log.info("parentPath: {}", parentPath);

        //Cho vào hàm đệ quy để tìm các level nhỏ hơn lồng nhau
        final long startTime = System.nanoTime();
        dfs(mrpDetailDTO.getChildren(),
            additionalItemInBomMap,
            inStockQuantityMap,
            inStockQuantityMapWithWhsCode,
            prQuantityMap,
            poQuantityMap,
            itemHoldQuantity,
            itemHoldQuantityByWhs,
            itemOnOrderQuantityMap,
            timeStart,
            timeEnd,
            Double.valueOf(mrpItem.getQuantity()),
            whsName,
            countLevel,
            parentPath,
            listWhs,
            analysisPeriod,
            analysisType,
            soCode,
            analysisOptions);
        final long endTime = System.nanoTime();
        log.info("Total execution time: {}", endTime - startTime);

        //****************************************************
        mrpDetailDTO.setRequiredQuantity(Double.valueOf(mrpItem.getQuantity()));
        mrpDetailDTO.setWhsName(whsName);
        mrpDetailDTO.setLevel(String.valueOf(countLevel));
        mrpDetailDTO.setDetailResult(mrpResultDTOList);

        return mrpDetailDTO;
    }

    /**
     * @param detailDTOS
     * @param timeStart
     * @param timeEnd
     * @param itemQuantity
     * @param whsName
     * @param level
     * @param parentPath
     * @param listWhs
     */
    public void dfs(List<MrpDetailDTO> detailDTOS,
                    Map<String, List<MrpDetailDTO>> additionalItemInBomMap,
                    Map<String, Double> inStockQuantityMap,
                    Map<String, List<CurrentWarehouseInventory>> inStockQuantityMapWithWhsCode,
                    Map<String, Double> prQuantityMap,
                    Map<String, Double> poQuantityMap,
                    Map<String, List<ItemQuantityWithDate>> itemHoldQuantity,
                    Map<String, List<ItemQuantityWithDate>> itemHoldQuantityByWhs,
                    Map<String, List<ItemQuantityWithDate>> itemOnOrderQuantityMap,
                    Date timeStart,
                    Date timeEnd,
                    Double itemQuantity,
                    String whsName,
                    Integer level,
                    String parentPath,
                    List<String> listWhs,
                    String analysisPeriod,
                    Integer analysisType,
                    String soCode,
                    List<String> analysisOptions) throws ParseException {

        final long startTime = System.nanoTime();
        List<MrpResultDTO> mrpResultDTOList;

        MrpResultDTO mrpResultDTO;
        int countLevel = level + 1;
        double requiredQuantity;

        //Vòng lặp đệ quy để kiểm tra các mức NVL và BTP
        for (MrpDetailDTO mrpDetailDTO : detailDTOS) {
            mrpResultDTOList = new ArrayList<>();

            //Từ số lượng yêu cầu của mức cha => số lượng yêu cầu mức con
            requiredQuantity = itemQuantity * mrpDetailDTO.getQuota();

            //Lấy số lượng hiện trạng cho tung NVL hoặc BTP
            mrpResultDTO = new MrpResultDTO();

            mrpResultDTO.setLandmark(Constants.MrpAnalysis.SAP_QUANTITY_STRING);
            mrpResultDTO.setOriginQuantity(inStockQuantityMap.getOrDefault(mrpDetailDTO.getItemCode(), 0.0));
            mrpResultDTOList.add(mrpResultDTO);

            //lấy result phân tích theo thời gian ở mức NVl hoặc BTP
            analysisByDayWeekMonth(
                mrpDetailDTO,
                timeStart,
                timeEnd,
                analysisPeriod,
                itemOnOrderQuantityMap,
                poQuantityMap,
                prQuantityMap,
                itemHoldQuantity,
                itemHoldQuantityByWhs,
                mrpResultDTOList
            );

            //******************************
            mrpDetailDTO.setIsHold(
                itemHoldQuantity.containsKey(mrpDetailDTO.getItemCode())
                    || itemHoldQuantityByWhs.containsKey(mrpDetailDTO.getItemCode())
            );
            // SL Trên SAP
            mrpResultDTOList.get(1).setInStockQuantity(
                inStockQuantityMap.getOrDefault(mrpDetailDTO.getItemCode(), 0.0)
            );
            mrpDetailDTO.setRequiredQuantity(requiredQuantity);
            // SL sẵn sàng
            mrpDetailDTO.setInStockQuantity(mrpResultDTOList.get(0).getReadyQuantity());
            mrpDetailDTO.setWhsName(whsName);
            mrpDetailDTO.setLevel(String.valueOf(countLevel));
            mrpDetailDTO.setParentPath(parentPath);
            mrpDetailDTO.setDetailResult(mrpResultDTOList);
            mrpDetailDTO.setCurrentWarehouseInventoryList(inStockQuantityMapWithWhsCode.get(mrpDetailDTO.getItemCode()));

            // nếu là BTP thì query tiếp con của nó
            //TODO: need to change to BTP.equals
//            if ( mrpDetailDTO.getBomVersion() != null && !mrpDetailDTO.getBomVersion().isEmpty() && !mrpDetailDTO.getBomVersion().isBlank()){
            if (mrpDetailDTO.getGroupItemInt() != null && mrpDetailDTO.getGroupItemInt() == Constants.BTP) {

                String newParentPath = mrpDetailDTO.getItemCode() + "_" + parentPath;

                //query lấy children của BTP
                List<MrpDetailDTO> childrenBom = bomService.getBomTree().get(Utils.toItemKey(mrpDetailDTO.getItemCode(), mrpDetailDTO.getBomVersion()));
                if (!CollectionUtils.isEmpty(childrenBom)) {
                    for (MrpDetailDTO detailDTO : childrenBom) {
                        mrpDetailDTO.getChildren().add(new MrpDetailDTO(detailDTO));
                    }
                }

                //Merge children trong DB MRP để thêm các NVL/BTP không theo bom
                parentPath = "_" + mrpDetailDTO.getItemCode();
                MrpAnalysisUtil.addMrpBomToItem(mrpDetailDTO, additionalItemInBomMap.get(parentPath));

                //****************************************
//                mrpDetailDTO.setStatus("Sản xuất");
//                mrpDetailDTO.setGroupItem("BTP");

                //Chạy lại hàm đệ quy
                dfs(mrpDetailDTO.getChildren(),
                    additionalItemInBomMap,
                    inStockQuantityMap,
                    inStockQuantityMapWithWhsCode,
                    prQuantityMap,
                    poQuantityMap,
                    itemHoldQuantity,
                    itemHoldQuantityByWhs,
                    itemOnOrderQuantityMap,
                    timeStart,
                    timeEnd,
                    requiredQuantity,
                    whsName,
                    countLevel,
                    newParentPath,
                    listWhs,
                    analysisPeriod,
                    analysisType,
                    soCode,
                    analysisOptions
                );
            }
        }
        log.info("/***************************** Kết thúc quá trình phân tích MRP cho lần đầu tiên *****************************/");
        final long endTime = System.nanoTime();
        log.info("Total execution time for dsf: {}", endTime - startTime);
    }

    /**
     * phân tích mrp theo ngày / tuần / tháng ở mức NVL và BTP
     *
     */
    public void analysisByDayWeekMonth(
        MrpDetailDTO mrpDetailDTO,
        Date timeStart,
        Date timeEnd,
        String analysisPeriod,
        Map<String, List<ItemQuantityWithDate>> itemOnOrderQuantityMap,
        Map<String, Double> poQuantity,
        Map<String, Double> prQuantity,
        Map<String, List<ItemQuantityWithDate>> itemHoldQuantity,
        Map<String, List<ItemQuantityWithDate>> itemHoldQuantityByWhs,
        List<MrpResultDTO> mrpResultDTOList
    ) throws ParseException {
        Calendar calStartTime = Calendar.getInstance();
        Calendar calEndTime = Calendar.getInstance();
        Calendar calCheck = Calendar.getInstance();
        MrpResultDTO mrpResultDTO;

        //lấy result phân tích theo ngày ở mức NVl hoặc BTP
        calStartTime.setTime(timeStart);
        calEndTime.setTime(timeEnd);
        boolean isFirstDate = true;
        Date beforeDate = new Date(Long.MIN_VALUE);
        while (calStartTime.getTime().before(calEndTime.getTime()) || calStartTime.getTime().equals(calEndTime.getTime())) {
            mrpResultDTO = new MrpResultDTO();
            if (isFirstDate) {
                isFirstDate = false;
                MrpAnalysisUtil.setPrPoQuantityV3(mrpDetailDTO.getItemCode(), mrpResultDTO, prQuantity, poQuantity);
            }
            if (analysisPeriod.equalsIgnoreCase("ngày")) {
                MrpAnalysisUtil.setAnalysisDetailV2(mrpDetailDTO, mrpResultDTO, beforeDate, calStartTime, itemOnOrderQuantityMap, simpleDateFormat);
                MrpAnalysisUtil.setHoldQuantity(mrpDetailDTO, mrpResultDTO, beforeDate, calStartTime, itemHoldQuantity, simpleDateFormat);
                MrpAnalysisUtil.setHoldQuantity(mrpDetailDTO, mrpResultDTO, beforeDate, calStartTime, itemHoldQuantityByWhs, simpleDateFormat);

                mrpResultDTO.setLandmark(simpleDateFormat.format(calStartTime.getTime()));
                mrpResultDTOList.add(mrpResultDTO);
                log.info("Lưu mrpResultDTOList : {}", mrpResultDTOList.size());

                beforeDate.setTime(calStartTime.getTime().getTime());
                calStartTime.add(Calendar.DATE, 1);

            } else if (analysisPeriod.equalsIgnoreCase("tuần")) {
                if (calStartTime.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || simpleDateFormat.format(calStartTime.getTime()).equals(simpleDateFormat.format(calEndTime.getTime()))) {
                    MrpAnalysisUtil.setAnalysisDetailV2(mrpDetailDTO, mrpResultDTO, beforeDate, calStartTime, itemOnOrderQuantityMap, simpleDateFormat);
                    MrpAnalysisUtil.setHoldQuantity(mrpDetailDTO, mrpResultDTO, beforeDate, calStartTime, itemHoldQuantity, simpleDateFormat);
                    MrpAnalysisUtil.setHoldQuantity(mrpDetailDTO, mrpResultDTO, beforeDate, calStartTime, itemHoldQuantityByWhs, simpleDateFormat);

                    mrpResultDTO.setLandmark(simpleDateFormat.format(calStartTime.getTime()));

                    mrpResultDTOList.add(mrpResultDTO);
                    beforeDate.setTime(calStartTime.getTime().getTime());
                }

                calStartTime.add(Calendar.DATE, 1);
            } else if (analysisPeriod.equalsIgnoreCase("tháng")) {
                if (calStartTime.get(Calendar.DATE) == calStartTime.getActualMaximum(Calendar.DATE)
                    || simpleDateFormat.format(calStartTime.getTime()).equals(simpleDateFormat.format(calEndTime.getTime()))) {
                    MrpAnalysisUtil.setAnalysisDetailV2(mrpDetailDTO, mrpResultDTO, beforeDate, calStartTime, itemOnOrderQuantityMap, simpleDateFormat);
                    MrpAnalysisUtil.setHoldQuantity(mrpDetailDTO, mrpResultDTO, beforeDate, calStartTime, itemHoldQuantity, simpleDateFormat);
                    MrpAnalysisUtil.setHoldQuantity(mrpDetailDTO, mrpResultDTO, beforeDate, calStartTime, itemHoldQuantityByWhs, simpleDateFormat);

                    mrpResultDTO.setLandmark(simpleDateFormat.format(calStartTime.getTime()));

                    mrpResultDTOList.add(mrpResultDTO);
                    beforeDate.setTime(calStartTime.getTime().getTime());
                }
                calStartTime.add(Calendar.DATE, 1);

            } else if (analysisPeriod.equalsIgnoreCase("1/2 tuần")) {
                if (calStartTime.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY
                    || calStartTime.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
                    || simpleDateFormat.format(calStartTime.getTime()).equals(simpleDateFormat.format(calEndTime.getTime()))) {

                    MrpAnalysisUtil.setAnalysisDetailV2(mrpDetailDTO, mrpResultDTO, beforeDate, calStartTime, itemOnOrderQuantityMap, simpleDateFormat);
                    MrpAnalysisUtil.setHoldQuantity(mrpDetailDTO, mrpResultDTO, beforeDate, calStartTime, itemHoldQuantity, simpleDateFormat);
                    MrpAnalysisUtil.setHoldQuantity(mrpDetailDTO, mrpResultDTO, beforeDate, calStartTime, itemHoldQuantityByWhs, simpleDateFormat);

                    mrpResultDTO.setLandmark(simpleDateFormat.format(calStartTime.getTime()));

                    mrpResultDTOList.add(mrpResultDTO);
                    beforeDate.setTime(calStartTime.getTime().getTime());
                }
                calStartTime.add(Calendar.DATE, 1);
            } else if (analysisPeriod.equalsIgnoreCase("2 tuần")) {
                if (calStartTime.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    calStartTime.add(Calendar.DATE, 7);

                    MrpAnalysisUtil.setAnalysisDetailV2(mrpDetailDTO, mrpResultDTO, beforeDate, calStartTime, itemOnOrderQuantityMap, simpleDateFormat);
                    MrpAnalysisUtil.setHoldQuantity(mrpDetailDTO, mrpResultDTO, beforeDate, calStartTime, itemHoldQuantity, simpleDateFormat);
                    MrpAnalysisUtil.setHoldQuantity(mrpDetailDTO, mrpResultDTO, beforeDate, calStartTime, itemHoldQuantityByWhs, simpleDateFormat);

                    mrpResultDTO.setLandmark(simpleDateFormat.format(calStartTime.getTime()));

                    mrpResultDTOList.add(mrpResultDTO);
                    beforeDate.setTime(calStartTime.getTime().getTime());

                    calCheck.setTime(calStartTime.getTime());
                    calCheck.add(Calendar.DATE, 14);
                    if (calCheck.getTime().after(calEndTime.getTime())) {
                        calStartTime.add(Calendar.DATE, calEndTime.get(Calendar.DAY_OF_YEAR) - calStartTime.get(Calendar.DAY_OF_YEAR));
                    } else {
                        calStartTime.add(Calendar.DATE, 7);
                    }
                } else if (simpleDateFormat.format(calStartTime.getTime()).equals(simpleDateFormat.format(calEndTime.getTime()))) {
                    MrpAnalysisUtil.setAnalysisDetailV2(mrpDetailDTO, mrpResultDTO, beforeDate, calStartTime, itemOnOrderQuantityMap, simpleDateFormat);
                    MrpAnalysisUtil.setHoldQuantity(mrpDetailDTO, mrpResultDTO, beforeDate, calStartTime, itemHoldQuantity, simpleDateFormat);
                    MrpAnalysisUtil.setHoldQuantity(mrpDetailDTO, mrpResultDTO, beforeDate, calStartTime, itemHoldQuantityByWhs, simpleDateFormat);

                    mrpResultDTO.setLandmark(simpleDateFormat.format(calStartTime.getTime()));

                    mrpResultDTOList.add(mrpResultDTO);
                    beforeDate.setTime(calStartTime.getTime().getTime());

                    calStartTime.add(Calendar.DATE, 1);
                } else {
                    calStartTime.add(Calendar.DATE, 1);
                }
            }
        }
    }
}
