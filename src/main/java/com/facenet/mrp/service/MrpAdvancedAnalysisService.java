package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.ProductOrder;
import com.facenet.mrp.repository.mrp.*;
import com.facenet.mrp.repository.sap.CoittRepository;
import com.facenet.mrp.repository.sap.OitwRepository;
import com.facenet.mrp.repository.sap.Por1Repository;
import com.facenet.mrp.repository.sap.Prq1Repository;
import com.facenet.mrp.service.dto.AdvancedMrpDTO;
import com.facenet.mrp.service.dto.mrp.*;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.mapper.MrpAnalyticsMapper;
import com.facenet.mrp.service.utils.Constants;
import com.facenet.mrp.service.utils.ExcelUtils;
import com.facenet.mrp.service.utils.MrpAnalysisUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MrpAdvancedAnalysisService {

    private static final Logger log = LogManager.getLogger(MrpAdvancedAnalysisService.class);
    private final CoittRepository coittRepository;
    private final MrpAnalyticsMapper mrpAnalyticsMapper;
    private final ProductOrderDetailRepository poDetailRepository;
    private final MrpRepository mrpRepository;
    private final SapOnOrderSummaryRepository soosRepository;
    private final ItemHoldRepository itemHoldRepository;
    private final OitwRepository oitwRepository;
    private final MrpSubRepository mrpSubRepository;
    private final MrpBomDetailRepository mrpBomDetailRepository;
    private final ProductOrderRepository productOrderRepository;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat simpleDateFormatByMonth = new SimpleDateFormat("yyyy-MM");
    private final PurchaseRecommendationPlanRepository purchaseRecommendationPlanRepository;
    private final ForecastOrderDetailRepository forecastOrderDetailRepository;
    private final Prq1Repository prq1Repository;
    private final Por1Repository por1Repository;

    public MrpAdvancedAnalysisService(CoittRepository coittRepository, MrpAnalyticsMapper mrpAnalyticsMapper, ProductOrderDetailRepository poDetailRepository, MrpRepository mrpRepository, SapOnOrderSummaryRepository soosRepository, ItemHoldRepository itemHoldRepository, OitwRepository oitwRepository, MrpSubRepository mrpSubRepository, MrpBomDetailRepository mrpBomDetailRepository,
                                      ProductOrderRepository productOrderRepository, PurchaseRecommendationPlanRepository purchaseRecommendationPlanRepository,
                                      ForecastOrderDetailRepository forecastOrderDetailRepository,
                                      Prq1Repository prq1Repository,
                                      Por1Repository por1Repository) {
        this.coittRepository = coittRepository;
        this.mrpAnalyticsMapper = mrpAnalyticsMapper;
        this.poDetailRepository = poDetailRepository;
        this.mrpRepository = mrpRepository;
        this.soosRepository = soosRepository;
        this.itemHoldRepository = itemHoldRepository;
        this.oitwRepository = oitwRepository;
        this.mrpSubRepository = mrpSubRepository;
        this.mrpBomDetailRepository = mrpBomDetailRepository;
        this.productOrderRepository = productOrderRepository;
        this.purchaseRecommendationPlanRepository = purchaseRecommendationPlanRepository;
        this.forecastOrderDetailRepository = forecastOrderDetailRepository;
        this.prq1Repository = prq1Repository;
        this.por1Repository = por1Repository;
    }

    /**
     * @param input
     * @return
     */
    public AdvancedMrpDTO estimatedProductionSchedule(MrpAnalyticsInput input) throws ParseException {

        List<MrpDetailDTO> mrpDetailDTOList = new ArrayList<>();
        List<CurrentInventory> currentInventory;

        Map<Integer, MrpItemQuantityDTO> mrpItemQuantityHM = new HashMap<>();

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
            int subCodecount = mrpSubRepository.getMrpSubCodeCount(mrpCode) + 1;
            mrpSubCode = mrpCode + "." + subCodecount;

            //Chạy lại từ thời gian hiện tại
            log.info(input.getTimeEnd().before(new Date()));
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
        log.info("quantities.size: {}", quantities.size());
        if (!CollectionUtils.isEmpty(quantities)) {
            for (MrpItemQuantityDTO dto : quantities) {
                mrpItemQuantityHM.put(dto.getItemId(), dto);
            }
        }

        //Todo: Phân tích từng sản phẩm trong đơn hàng
        for (Integer productId : input.getListItemId()) {
            MrpDetailDTO mrpDetailDTO = estimatedSingleProduct(
                mrpItemQuantityHM.get(productId),
                input.getTimeStart(),
                input.getTimeEnd(),
                input.getListAnalysisWhs(),
                input.getAnalysisPeriod(),
                input.getSoCode(),
                input.getMaterialPreparationTime()
            );
            if (mrpDetailDTO != null)
                mrpDetailDTOList.add(mrpDetailDTO);
        }

        //add biến cần vào mrpDtoMapper
        mrpDTO = mrpAnalyticsMapper.inputToDto(input, mrpCode, mrpSubCode, dNow, mrpDetailDTOList);
        mrpDTO.setListItemCode(input.getListItemId());
        return mrpDTO;
    }

    /**
     * @param mrpItem
     * @param timeStart
     * @param timeEnd
     * @param listWhs
     * @param analysisPeriod
     * @param soCode
     * @return
     */
    public MrpDetailDTO estimatedSingleProduct(
        MrpItemQuantityDTO mrpItem,
        Date timeStart,
        Date timeEnd,
        List<String> listWhs,
        String analysisPeriod,
        String soCode,
        int materialPreparationTime) {

        log.info("/***************************** Vào hàm phân tích MRP cho lần đầu tiên *****************************/");
        MrpResultDTO mrpResultDTO;
        MrpDetailDTO mrpDetailDTO;

        List<MrpResultDTO> mrpResultDTOList = new ArrayList<>();
        List<MrpDetailDTO> detailDTOS;
        List<MrpDetailDTO> detailDTOList;
        List<Calendar> timeList = new ArrayList<>();

        String whsName = String.join(";", listWhs);
        String parentPath;
        int countLevel = 1;
        Calendar calStartTime = Calendar.getInstance();
        Calendar calEndTime = Calendar.getInstance();
        Calendar calCheck = Calendar.getInstance();

        //Query trong sap lấy thông tin cần thiết của sản phẩm
        //TODO: Optimize
        log.info("mrpItem.getItemCode(): {}", mrpItem.getItemCode());
        detailDTOList = coittRepository.getOneMrpProduct(mrpItem.getItemCode(), mrpItem.getBomVersion());
        if (detailDTOList.isEmpty()) {
            return null;
        } else {
            mrpDetailDTO = detailDTOList.get(0);
        }

        //lấy result phân tích theo ngày ở mức "TP"
        //Check xem chu kỳ phân tích là ngày / tuần / tháng?
        // phân tích thành phảm theo chu kỳ
        calStartTime.setTime(timeStart);
        calEndTime.setTime(timeEnd);

        while (calStartTime.getTime().before(calEndTime.getTime()) || calStartTime.getTime().equals(calEndTime.getTime())) {
            Calendar calExactAnalysisTime = Calendar.getInstance();
            if (analysisPeriod.equalsIgnoreCase("ngày")) {
                mrpResultDTO = new MrpResultDTO();
                mrpResultDTO.setLandmark(simpleDateFormat.format(calStartTime.getTime()));
                mrpResultDTO.setOriginQuantity(0.0);
                mrpResultDTOList.add(mrpResultDTO);

                calExactAnalysisTime.setTime(calStartTime.getTime());

                timeList.add(calExactAnalysisTime);

                calStartTime.add(Calendar.DATE, 1);

            } else if (analysisPeriod.equalsIgnoreCase("tuần")) {

                calCheck.setTime(calStartTime.getTime());
                if (calCheck.getTime().before(calEndTime.getTime())) {
                    mrpResultDTO = new MrpResultDTO();
                    mrpResultDTO.setLandmark(simpleDateFormat.format(calStartTime.getTime()));
                    mrpResultDTO.setOriginQuantity(0.0);
                    mrpResultDTOList.add(mrpResultDTO);

                    calExactAnalysisTime.setTime(calStartTime.getTime());
                    timeList.add(calExactAnalysisTime);

                    //Nếu trong vòng lặp check sau khi + 7  ngày sẽ lơn hơn ngày cuối cùng
                    // thì add ngày cuối vào luôn và break
                    calCheck.add(Calendar.DATE, 7);
                    if (calCheck.getTime().after(calEndTime.getTime()) || simpleDateFormat.format(calCheck.getTime()).equals(simpleDateFormat.format(calEndTime.getTime()))) {
                        mrpResultDTO = new MrpResultDTO();
                        mrpResultDTO.setLandmark(simpleDateFormat.format(calEndTime.getTime()));
                        mrpResultDTO.setOriginQuantity(0.0);
                        mrpResultDTOList.add(mrpResultDTO);

                        timeList.add(calEndTime);
                        break;
                    }

                    calStartTime.add(Calendar.DATE, 7);
                }

            } else if (analysisPeriod.equalsIgnoreCase("tháng")) {
                if (calStartTime.get(Calendar.DATE) == calStartTime.getActualMaximum(Calendar.DATE) || simpleDateFormat.format(calStartTime.getTime()).equals(simpleDateFormat.format(calEndTime.getTime()))) {
                    mrpResultDTO = new MrpResultDTO();
                    mrpResultDTO.setLandmark(simpleDateFormat.format(calStartTime.getTime()));
                    mrpResultDTO.setOriginQuantity(0.0);
                    mrpResultDTOList.add(mrpResultDTO);

                    calExactAnalysisTime.setTime(calStartTime.getTime());

                    timeList.add(calExactAnalysisTime);
                }
                calStartTime.add(Calendar.DATE, 1);

            } else if (analysisPeriod.equalsIgnoreCase("1/2 tuần")) {
                calCheck.setTime(calStartTime.getTime());
                if (calCheck.getTime().before(calEndTime.getTime())) {
                    mrpResultDTO = new MrpResultDTO();
                    mrpResultDTO.setLandmark(simpleDateFormat.format(calStartTime.getTime()));
                    mrpResultDTO.setOriginQuantity(0.0);
                    mrpResultDTOList.add(mrpResultDTO);

                    calExactAnalysisTime.setTime(calStartTime.getTime());
                    timeList.add(calExactAnalysisTime);

                    //Nếu trong vòng lặp check sau khi + 3  ngày sẽ lơn hơn ngày cuối cùng
                    // thì add ngày cuối vào luôn và break
                    calCheck.add(Calendar.DATE, 3);
                    if (calCheck.getTime().after(calEndTime.getTime()) || simpleDateFormat.format(calCheck.getTime()).equals(simpleDateFormat.format(calEndTime.getTime()))) {
                        mrpResultDTO = new MrpResultDTO();
                        mrpResultDTO.setLandmark(simpleDateFormat.format(calEndTime.getTime()));
                        mrpResultDTO.setOriginQuantity(0.0);
                        mrpResultDTOList.add(mrpResultDTO);

                        timeList.add(calEndTime);

                        break;
                    }

                    calStartTime.add(Calendar.DATE, 3);
                }

            } else if (analysisPeriod.equalsIgnoreCase("2 tuần")) {
                calCheck.setTime(calStartTime.getTime());
                if (calCheck.getTime().before(calEndTime.getTime())) {
                    mrpResultDTO = new MrpResultDTO();
                    mrpResultDTO.setLandmark(simpleDateFormat.format(calStartTime.getTime()));
                    mrpResultDTO.setOriginQuantity(0.0);
                    mrpResultDTOList.add(mrpResultDTO);

                    calExactAnalysisTime.setTime(calStartTime.getTime());
                    timeList.add(calExactAnalysisTime);

                    //Nếu trong vòng lặp check sau khi + 14  ngày sẽ lơn hơn ngày cuối cùng
                    // thì add ngày cuối vào luôn và break
                    calCheck.add(Calendar.DATE, 14);
                    if (calCheck.getTime().after(calEndTime.getTime()) || simpleDateFormat.format(calCheck.getTime()).equals(simpleDateFormat.format(calEndTime.getTime()))) {
                        mrpResultDTO = new MrpResultDTO();
                        mrpResultDTO.setLandmark(simpleDateFormat.format(calEndTime.getTime()));
                        mrpResultDTO.setOriginQuantity(0.0);
                        mrpResultDTOList.add(mrpResultDTO);

                        timeList.add(calEndTime);

                        break;
                    }

                    calStartTime.add(Calendar.DATE, 14);
                }
            }
        }
        log.info("Số lượng ngày phân tích: {}", mrpResultDTOList.size());
//        for (Calendar item : timeList) {
//            log.info("OldTimeList: {}", simpleDateFormat.format(item.getTime()));
//        }

        //query lấy children của sản phẩm TP
        //TODO: Optimize
        detailDTOS = coittRepository.getAllBTPMrpProductBom(mrpItem.getItemCode(), mrpDetailDTO.getBomVersion());
//        log.info("Tong so children của sản phẩm: {}", detailDTOS.size());
        //Merge children trong DB MRP để thêm các NVL/BTP không theo bom
        parentPath = "_" + mrpItem.getItemCode();
//        log.info("parentPath: {}", parentPath);
        //TODO: Optimize
        mergeDetailList(detailDTOS, parentPath, soCode);


        //Cho vào hàm đệ quy để tìm các level nhỏ hơn lồng nhau
        final long startTime = System.nanoTime();
        estimatedScheduleDfs(detailDTOS,
            timeList,
            Double.valueOf(mrpItem.getQuantity()),
            whsName,
            countLevel,
            parentPath,
            listWhs,
            analysisPeriod,
            soCode,
            materialPreparationTime
        );
        final long endTime = System.nanoTime();
        log.info("Total execution time: {}", endTime - startTime);

        //****************************************************
//        mrpDetailDTO.setGroupItem(Constants.TP);
        mrpDetailDTO.setRequiredQuantity(Double.valueOf(mrpItem.getQuantity()));
        mrpDetailDTO.setWhsName(whsName);
        mrpDetailDTO.setLevel(String.valueOf(countLevel));
//        mrpDetailDTO.setStatus("Sản xuất");
        mrpDetailDTO.setDetailResult(mrpResultDTOList);
        mrpDetailDTO.setChildren(detailDTOS);

        return mrpDetailDTO;

    }

    /**
     * @param detailDTOS
     * @param parentPath
     * @param soCode
     */
    private void mergeDetailList(List<MrpDetailDTO> detailDTOS, String parentPath, String soCode) {

        List<MrpDetailDTO> detailDTOsInMrpDB = mrpBomDetailRepository.getMrpDetailBTPNotInBom(soCode, parentPath);
        if (!CollectionUtils.isEmpty(detailDTOsInMrpDB)) {
            detailDTOS.addAll(detailDTOsInMrpDB);
        }

    }

    /**
     * @param detailDTOS
     * @param itemQuantity
     * @param whsName
     * @param level
     * @param parentPath
     * @param listWhs
     * @param analysisPeriod
     * @param soCode
     */
    public void estimatedScheduleDfs(List<MrpDetailDTO> detailDTOS,
                                     List<Calendar> timeList,
                                     Double itemQuantity,
                                     String whsName,
                                     Integer level,
                                     String parentPath,
                                     List<String> listWhs,
                                     String analysisPeriod,
                                     String soCode,
                                     int materialPreparationTime) {

        final long startTime = System.nanoTime();

        List<MrpResultDTO> mrpResultDTOList;
        List<MrpDetailDTO> childrenDetail;
        List<Calendar> childrenTimeList;

        int countLevel = level + 1;
        double requiredQuantity;

        List<String> itemCodeList = detailDTOS.stream().map(MrpDetailDTO::getItemCode).collect(Collectors.toList());
        List<CurrentInventory> inStockQuantity = oitwRepository.getAllCurrentInventoryByWhs(itemCodeList, listWhs);
        Map<String, Double> inStockQuantityMap = inStockQuantity.stream().collect(Collectors.toMap(CurrentInventory::getItemCode, CurrentInventory::getCurrentQuantity));

        //Vòng lặp đệ quy để kiểm tra các mức NVL và BTP
        for (MrpDetailDTO mrpDetailDTO : detailDTOS) {

            childrenTimeList = new ArrayList<>();
            mrpResultDTOList = new ArrayList<>();

            //Từ số lượng yêu cầu của mức cha => số lượng yêu cầu mức con
            requiredQuantity = itemQuantity * mrpDetailDTO.getQuota();

            //lấy result phân tích theo ngày ở muc BTP
            subtractAnalysisPeriod(
                mrpDetailDTO,
                timeList,
                childrenTimeList,
                analysisPeriod,
                mrpResultDTOList,
                materialPreparationTime
            );

            //******************************
            mrpDetailDTO.setRequiredQuantity(requiredQuantity);
            mrpDetailDTO.setInStockQuantity(inStockQuantityMap.getOrDefault(mrpDetailDTO.getItemCode(), 0.0));
            mrpDetailDTO.setWhsName(whsName);
            mrpDetailDTO.setLevel(String.valueOf(countLevel));
            mrpDetailDTO.setParentPath(parentPath);
            mrpDetailDTO.setDetailResult(mrpResultDTOList);

            // nếu là BTP thì query tiếp con của nó
//            if (StringUtils.hasText(mrpDetailDTO.getBomVersion())) {
            if (mrpDetailDTO.getGroupItemInt() != null && mrpDetailDTO.getGroupItemInt() == Constants.BTP) {
                String newParentPath = mrpDetailDTO.getItemCode() + "_" + parentPath;

                log.info("Query lấy vật tư con của itemCode {}", mrpDetailDTO.getItemCode());
                //query lấy children của BTP
                childrenDetail = coittRepository.getAllBTPMrpProductBom(mrpDetailDTO.getItemCode(), mrpDetailDTO.getBomVersion());
                log.info("children của sản phẩm: {}", detailDTOS);

                //Merge children trong DB MRP để thêm các NVL/BTP không theo bom
                log.info("parentPath: {}", parentPath);
                mergeDetailList(childrenDetail, newParentPath, soCode);

                //****************************************
//                mrpDetailDTO.setStatus("Sản xuất");
//                mrpDetailDTO.setGroupItem(Constants.BTP);
                mrpDetailDTO.setChildren(childrenDetail);

                //Chạy lại hàm đệ quy
                estimatedScheduleDfs(
                    mrpDetailDTO.getChildren(),
                    childrenTimeList,
                    requiredQuantity,
                    whsName,
                    countLevel,
                    newParentPath,
                    listWhs,
                    analysisPeriod,
                    soCode,
                    materialPreparationTime
                );
            }
        }
    }

    /**
     * lấy ra kết quả lịch trình sản xuất theo đúng ngày san xuất của một TP
     * Lay ra danh sách thời gian của NVl và BTP để tiếp tục đệ quy tru ngay
     *
     * @param mrpDetailDTO
     * @param analysisPeriod
     * @param mrpResultDTOList
     * @param childrenTimeList => danh sách thời gian của NVl và BTP để tiếp tục đệ quy tru ngay
     */
    private void subtractAnalysisPeriod(
        MrpDetailDTO mrpDetailDTO
        , List<Calendar> timeList
        , List<Calendar> childrenTimeList
        , String analysisPeriod
        , List<MrpResultDTO> mrpResultDTOList
        , int materialPreparationTime
    ) {

        MrpResultDTO mrpResultDTO;
        Calendar calCheck;
        Calendar theLastSundayInList;
        int subtractNearestSunday;

        //lấy result phân tích trừ theo khoảng thời gian ở mức NVl hoặc BTP
        //Add các thời điểm của mức NVL và BTP để trừ tiếp cho lần gọi đệ quy sau
        for (Calendar time : timeList) {
            log.info("timeList: {}", simpleDateFormat.format(time.getTime()));
            mrpResultDTO = new MrpResultDTO();
            calCheck = Calendar.getInstance();
            calCheck.setTime(time.getTime());

            switch (analysisPeriod.toLowerCase()) {
                case "ngày":
                    calCheck.add(Calendar.DATE, -(materialPreparationTime));
                    break;
                case "tuần":
                    calCheck.add(Calendar.DATE, -(materialPreparationTime * 7));
                    break;
                case "1/2 tuần":
//                    if (simpleDateFormat.format(calCheck.getTime()).equals(simpleDateFormat.format(timeList.get(timeList.size() - 1).getTime()))){
//                        calCheck.add(Calendar.DATE, -(materialPreparationTime * 3));
//                        break;
//                    }else {
//                        //Lùi ngày 1/2 tuần với ngày thứ 4
//                        if (calCheck.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
//                            calCheck.add(Calendar.DATE, -(materialPreparationTime * 3 + (materialPreparationTime / 2)));
//
//                            break;
//                        }
//                        // Lùi ngày 1/2 tuần với ngày chủ nhật
//                        else if (calCheck.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
//                            if (materialPreparationTime % 2 == 0) {
//                                calCheck.add(Calendar.DATE, -(materialPreparationTime * 3 + (materialPreparationTime / 2)));
//                            } else {
//                                calCheck.add(Calendar.DATE, -(materialPreparationTime * 3 + (materialPreparationTime / 2) + 1));
//                            }
//
//                            break;
//                        }
//                        //lùi ngay mà không phai ngày chu nhat hay thu 4
//                        else {
//                            int newPreparationTime = materialPreparationTime - 1;
//                            if (calCheck.get(Calendar.DAY_OF_WEEK) > Calendar.SUNDAY && calCheck.get(Calendar.DAY_OF_WEEK) < Calendar.WEDNESDAY) {
//                                subtractNearestSunday = Calendar.SUNDAY - calCheck.get(Calendar.DAY_OF_WEEK);
//                                System.out.println("subtractNearestSunday: " + subtractNearestSunday);
//
//                                if (newPreparationTime % 2 == 0) {
//                                    calCheck.add(Calendar.DATE, subtractNearestSunday - (newPreparationTime * 3 + (newPreparationTime / 2)));
//                                } else {
//                                    calCheck.add(Calendar.DATE, -(materialPreparationTime * 3 + (materialPreparationTime / 2) + 1));
//                                }
//
//
//                            } else {
//                                int subtractNearestWednesday = Calendar.WEDNESDAY - calCheck.get(Calendar.DAY_OF_WEEK);
//                                System.out.println("subtractNearestWednesday: " + subtractNearestWednesday);
//
//                                calCheck.add(Calendar.DATE, subtractNearestWednesday - (newPreparationTime * 3 + (newPreparationTime / 2)));
//
//                            }
//                            break;
//                        }
//                    }
//                }
                    calCheck.add(Calendar.DATE, -(materialPreparationTime * 3));
                    break;
                case "2 tuần":
                    calCheck.add(Calendar.DATE, -(materialPreparationTime * 14));
                    break;
                case "tháng":
                    calCheck.add(Calendar.MONTH, -materialPreparationTime);
                    break;
            }
            mrpResultDTO.setLandmark(simpleDateFormat.format(calCheck.getTime()));
            mrpResultDTO.setOriginQuantity((double) 0);

            mrpResultDTOList.add(mrpResultDTO);

            childrenTimeList.add(calCheck);
//            if (analysisPeriod.equalsIgnoreCase("ngày")) {
//                calCheck.add(Calendar.DATE, -materialPreparationTime);
//                mrpResultDTO.setLandmark(simpleDateFormat.format(calCheck.getTime()));
//                mrpResultDTO.setOriginQuantity((double) 0);
//
//                mrpResultDTOList.add(mrpResultDTO);
//
//                childrenTimeList.add(calCheck);
//
//                log.info("Lưu mrpResultDTOList : {}", mrpResultDTOList.size());
//
//            } else if (analysisPeriod.equalsIgnoreCase("tuần")) {
//                if (calCheck.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
//                    calCheck.add(Calendar.DATE, -(materialPreparationTime * 7));
//
//                    mrpResultDTO.setLandmark(simpleDateFormat.format(calCheck.getTime()));
//                    mrpResultDTO.setOriginQuantity((double) 0);
//
//                    mrpResultDTOList.add(mrpResultDTO);
//
//                    childrenTimeList.add(calCheck);
//                } else {
//                    int newPreparationTime = materialPreparationTime - 1;
//                    subtractNearestSunday = Calendar.SUNDAY - calCheck.get(Calendar.DAY_OF_WEEK);
//
//                    calCheck.add(Calendar.DATE, subtractNearestSunday - newPreparationTime * 7);
//
//                    mrpResultDTO.setLandmark(simpleDateFormat.format(calCheck.getTime()));
//                    mrpResultDTO.setOriginQuantity((double) 0);
//
//                    mrpResultDTOList.add(mrpResultDTO);
//
//                    childrenTimeList.add(calCheck);
//                }
//
//            } else if (analysisPeriod.equalsIgnoreCase("tháng")) {
//                if (calCheck.get(Calendar.DATE) == calCheck.getActualMaximum(Calendar.DATE)) {
//                    for (int i = 0; i < materialPreparationTime; i++) {
//                        calCheck.add(Calendar.DATE, -(calCheck.getActualMaximum(Calendar.DATE)));
//                    }
//
//                    mrpResultDTO.setLandmark(simpleDateFormat.format(calCheck.getTime()));
//                    mrpResultDTO.setOriginQuantity((double) 0);
//
//                    mrpResultDTOList.add(mrpResultDTO);
//
//                    childrenTimeList.add(calCheck);
//                } else {
//                    int newPreparationTime = materialPreparationTime - 1;
//                    int subtractNearestEndOfMonth = calCheck.getActualMinimum(Calendar.DATE) - calCheck.get(Calendar.DATE) - 1;
//
//                    calCheck.add(Calendar.DATE, subtractNearestEndOfMonth);
//                    for (int i = 0; i < newPreparationTime; i++) {
//                        calCheck.add(Calendar.DATE, -(calCheck.getActualMaximum(Calendar.DATE)));
//                    }
//
//
//                    mrpResultDTO.setLandmark(simpleDateFormat.format(calCheck.getTime()));
//                    mrpResultDTO.setOriginQuantity((double) 0);
//
//                    mrpResultDTOList.add(mrpResultDTO);
//
//                    childrenTimeList.add(calCheck);
//                }
//
//            } else if (analysisPeriod.equalsIgnoreCase("1/2 tuần")) {
                //Lùi ngày 1/2 tuần với ngày thứ 4
//                if (calCheck.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
//                    calCheck.add(Calendar.DATE, -(materialPreparationTime * 3 + (materialPreparationTime / 2)));
//
//                    mrpResultDTO.setLandmark(simpleDateFormat.format(calCheck.getTime()));
//                    mrpResultDTO.setOriginQuantity((double) 0);
//
//                    mrpResultDTOList.add(mrpResultDTO);
//
//                    childrenTimeList.add(calCheck);
//                }
//                // Lùi ngày 1/2 tuần với ngày chủ nhật
//                else if (calCheck.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
//                    if (materialPreparationTime % 2 == 0) {
//                        calCheck.add(Calendar.DATE, -(materialPreparationTime * 3 + (materialPreparationTime / 2)));
//                    } else {
//                        calCheck.add(Calendar.DATE, -(materialPreparationTime * 3 + (materialPreparationTime / 2) + 1));
//                    }
//
//                    mrpResultDTO.setLandmark(simpleDateFormat.format(calCheck.getTime()));
//                    mrpResultDTO.setOriginQuantity((double) 0);
//
//                    mrpResultDTOList.add(mrpResultDTO);
//
//                    childrenTimeList.add(calCheck);
//                }
//                //lùi ngay mà không phai ngày chu nhat hay thu 4
//                else {
//                    int newPreparationTime = materialPreparationTime - 1;
//                    if (calCheck.get(Calendar.DAY_OF_WEEK) > Calendar.SUNDAY && calCheck.get(Calendar.DAY_OF_WEEK) < Calendar.WEDNESDAY) {
//                        subtractNearestSunday = Calendar.SUNDAY - calCheck.get(Calendar.DAY_OF_WEEK);
//                        System.out.println("subtractNearestSunday: " + subtractNearestSunday);
//
//                        if (newPreparationTime % 2 == 0) {
//                            calCheck.add(Calendar.DATE, subtractNearestSunday - (newPreparationTime * 3 + (newPreparationTime / 2)));
//                        } else {
//                            calCheck.add(Calendar.DATE, -(materialPreparationTime * 3 + (materialPreparationTime / 2) + 1));
//                        }
//
//                        mrpResultDTO.setLandmark(simpleDateFormat.format(calCheck.getTime()));
//                        mrpResultDTO.setOriginQuantity((double) 0);
//
//                        mrpResultDTOList.add(mrpResultDTO);
//
//                        childrenTimeList.add(calCheck);
//
//                    } else {
//                        int subtractNearestWednesday = Calendar.WEDNESDAY - calCheck.get(Calendar.DAY_OF_WEEK);
//                        System.out.println("subtractNearestWednesday: " + subtractNearestWednesday);
//
//                        calCheck.add(Calendar.DATE, subtractNearestWednesday - (newPreparationTime * 3 + (newPreparationTime / 2)));
//
//                        mrpResultDTO.setLandmark(simpleDateFormat.format(calCheck.getTime()));
//                        mrpResultDTO.setOriginQuantity((double) 0);
//
//                        mrpResultDTOList.add(mrpResultDTO);
//
//                        childrenTimeList.add(calCheck);
//                    }
//
//                }
//
//            } else if (analysisPeriod.equalsIgnoreCase("2 tuần")) {
//                if (calCheck.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
//                    calCheck.add(Calendar.DATE, -(materialPreparationTime * 14));
//
//                    mrpResultDTO.setLandmark(simpleDateFormat.format(calCheck.getTime()));
//                    mrpResultDTO.setOriginQuantity((double) 0);
//
//                    mrpResultDTOList.add(mrpResultDTO);
//
//                    childrenTimeList.add(calCheck);
//
//                } else {
//                    theLastSundayInList = Calendar.getInstance();
//                    theLastSundayInList.setTime(timeList.get(timeList.size() - 2).getTime());
//
//                    if ((calCheck.get(Calendar.DAY_OF_YEAR) - theLastSundayInList.get(Calendar.DAY_OF_YEAR)) <= 7) {
//                        subtractNearestSunday = Calendar.SUNDAY - calCheck.get(Calendar.DAY_OF_WEEK);
//                    } else {
//                        subtractNearestSunday = Calendar.SUNDAY - calCheck.get(Calendar.DAY_OF_WEEK) - 7;
//                    }
//
//                    int newPreparationTime = materialPreparationTime - 1;
//                    calCheck.add(Calendar.DATE, subtractNearestSunday - (newPreparationTime * 14));
//
//                    mrpResultDTO.setLandmark(simpleDateFormat.format(calCheck.getTime()));
//                    mrpResultDTO.setOriginQuantity((double) 0);
//
//                    mrpResultDTOList.add(mrpResultDTO);
//
//                    childrenTimeList.add(calCheck);
//                }
//            }
        }
    }

    /**
     * @param advancedMrpDTO
     * @return
     */
    public AdvancedMrpDTO advancedMrpAnalysis(AdvancedMrpDTO advancedMrpDTO) throws ParseException {

        HashMap<String, Double> mrpItemQuantityHM = new HashMap<>();
        HashMap<String, List<MrpResultDTO>> currentInvenHM = new HashMap<>();
        Map<String, List<List<MrpResultDTO>>> itemOnDuplicateMap = new HashMap<>();

        Calendar totalStartTime = Calendar.getInstance();
        Calendar totalEndTime = Calendar.getInstance();
        String parentPath;

        //Lấy dữ liệu hiện trạng tồn kho của sản phẩm
        getCurrentInventory(
            advancedMrpDTO.getResultData(),
            currentInvenHM,
            advancedMrpDTO.getWarehouseAnalysis(),
            mrpItemQuantityHM,
            advancedMrpDTO.getAnalysisWhs()
        );

        // xác định khoảng thời gian phân tích tổng ( startTime -> endTime )
        // Skip first landmark (sl tren kho sap)
        totalStartTime.setTime(parseStringToCalendar(advancedMrpDTO.getResultData().get(0).getDetailResult().get(0).getLandmark()).getTime());
        totalEndTime.setTime(advancedMrpDTO.getTimeEnd());
        log.info("totalstartTime1: {}", simpleDateFormat.format(totalStartTime.getTime()));
        for (MrpDetailDTO detailDTO : advancedMrpDTO.getResultData()) {
            getStartTimeOfMrpAnalytic(totalStartTime, detailDTO);
        }

        //Phân tích từng sản phẩm
        for (MrpDetailDTO detailDTO : advancedMrpDTO.getResultData()) {
            parentPath = "_" + detailDTO.getItemCode();
            analysisSingleProduct(
                detailDTO,
                itemOnDuplicateMap,
                mrpItemQuantityHM.get(detailDTO.getItemCode()),
                advancedMrpDTO.getWarehouseAnalysis(),
                totalStartTime,
                totalEndTime,
                advancedMrpDTO.getAnalysisPeriod(),
                advancedMrpDTO.getAnalysisType(),
                advancedMrpDTO.getSoCode(),
                currentInvenHM.get(detailDTO.getItemCode()),
                1,
                parentPath,
                advancedMrpDTO.getAnalysisWhs()
            );
        }

        // Post-processing
        // Re-calculate on duplicate item code
        for (String itemCode : itemOnDuplicateMap.keySet()) {
            List<List<MrpResultDTO>> mrpResults = itemOnDuplicateMap.get(itemCode);
            // On duplicate
            if (mrpResults.size() > 1) {
                // First (top-most) result
                List<MrpResultDTO> result = new ArrayList<>(mrpResults.get(0));

                // Find min date for PR PO
                Date minDate = new Date(Long.MAX_VALUE);
                double deliveringQuantity = 0.0;
                double poQuantity = 0.0;
                for (List<MrpResultDTO> mrpResult : mrpResults) {
                    for (int i = 1; i < mrpResult.size(); i++) {
                        if (mrpResult.get(i).getDeliveringQuantity() > 0.0
                            || mrpResult.get(i).getPoQuantity() > 0.0) {
                            Date currentResultDate = simpleDateFormat.parse(mrpResult.get(i).getLandmark());
                            if (minDate.compareTo(currentResultDate) >= 0) {
                                minDate = currentResultDate;
                                deliveringQuantity = mrpResult.get(i).getDeliveringQuantity();
                                poQuantity = mrpResult.get(i).getPoQuantity();
                            }
                        }
                    }
                }

                double lastInStockQuantity = result.get(0).getOriginQuantity();
                for (int i = 1; i < result.size(); i++) {
                    // Calculate sum of origin quantity
                    double sumOriginQuantity = 0.0;
                    for (List<MrpResultDTO> mrpResult : mrpResults) {
                        sumOriginQuantity += mrpResult.get(i).getOriginQuantity();
                    }
                    MrpResultDTO mrpResultDTO = result.get(i);
                    // Set sum need quantity
                    mrpResultDTO.setTotalOriginQuantity(sumOriginQuantity);

                    // min Date of Pr Po
                    if (simpleDateFormat.parse(mrpResultDTO.getLandmark()).equals(minDate)) {
                        mrpResultDTO.setDeliveringQuantity(deliveringQuantity);
                        mrpResultDTO.setPoQuantity(poQuantity);
                    } else {
                        mrpResultDTO.setDeliveringQuantity(0.0);
                        mrpResultDTO.setPoQuantity(0.0);
                    }

                    mrpResultDTO.setInStockQuantity(lastInStockQuantity);
                    mrpResultDTO.setReadyQuantity(
                        mrpResultDTO.getInStockQuantity() // Warehouse
//                            + mrpResultDTO.getExpectedQuantity() // PR
                            + mrpResultDTO.getSumPoAndDeliveringQuantity() // PR
                            - mrpResultDTO.getRequiredQuantity() // Hold quantity
                    );

//                    if (mrpResultDTO.getReadyQuantity() > 0) {
                        if (mrpResultDTO.getReadyQuantity() > sumOriginQuantity) {
                            lastInStockQuantity = mrpResultDTO.getReadyQuantity() - sumOriginQuantity;
                        } else {
                            mrpResultDTO.setOrderQuantity(
                                sumOriginQuantity - mrpResultDTO.getReadyQuantity()
                            );
                            lastInStockQuantity = 0.0;
                        }
//                    } else {
//                        mrpResultDTO.setOrderQuantity(sumOriginQuantity);
//                        lastInStockQuantity = 0.0;
//                    }
                }

                // Set all result to new result
                for (int i = 1; i < mrpResults.size(); i++) {
                    List<MrpResultDTO> mrpResult = mrpResults.get(i);
                    for (int j = 1; j < mrpResult.size(); j++) {
                        MrpResultDTO mrpResultDetail = mrpResult.get(j);
                        mrpResultDetail.setInStockQuantity(result.get(j).getInStockQuantity());
                        mrpResultDetail.setReadyQuantity(result.get(j).getReadyQuantity());
//                        mrpResultDetail.setPoQuantity(0.0);
//                        mrpResultDetail.setDeliveringQuantity(0.0);
//                        mrpResultDetail.setOrderQuantity(result.get(j).getOrderQuantity());
                        mrpResultDetail.setOrderQuantity(0.0);
                    }
                }
            }
        }

        return advancedMrpDTO;
    }

    /**
     * @param mrpDetailDTO
     * @param productQuantity
     * @param whsCodeS
     * @param analysisPeriod
     * @param soCode
     */
    public void analysisSingleProduct(
        MrpDetailDTO mrpDetailDTO,
        Map<String, List<List<MrpResultDTO>>> itemOnDuplicateMap,
        Double productQuantity,
        List<String> whsCodeS,
        Calendar totalStartTime,
        Calendar totalEndTime,
        String analysisPeriod,
        Integer analysisType,
        String soCode,
        List<MrpResultDTO> mrpResultDTOS,
        Integer countLevel,
        String parentPath,
        List<String> analysisOptions
    ) throws ParseException {

        List<MrpDetailDTO> detailDTOS;

        HashMap<String, Double> listAnalysisTime = new HashMap<>();
        HashMap<String, Double> mrpItemQuantityHM = new HashMap<>();
        HashMap<String, List<MrpResultDTO>> currentInvenHM = new HashMap<>();

        String whsName = mrpDetailDTO.getWhsName();
        MrpResultDTO resultTpDTO;
        Calendar calCheck = Calendar.getInstance();
        Calendar totalCheckStartTime = Calendar.getInstance();
        Calendar totalCheckEndTime = Calendar.getInstance();

        //Lấy danh sách thời gian phân tích ở mức cha
        for (MrpResultDTO resultDTO : mrpDetailDTO.getDetailResult()) {
            listAnalysisTime.put(resultDTO.getLandmark(), resultDTO.getOriginQuantity());
        }
        log.info("listAnalysisTime: {}", listAnalysisTime.keySet().size());

        totalCheckStartTime.setTime(totalStartTime.getTime());
        totalCheckEndTime.setTime(totalEndTime.getTime());
        //lấy result phân tích theo ky phan tich o muc TP
        //Check các thời gian có dữ liệu "số lượng yêu cầu" thì sẽ giữ nguyên, thêm các ngày của khoảng thời gian thêm
        while (totalCheckStartTime.getTime().before(totalCheckEndTime.getTime()) || totalCheckStartTime.getTime().equals(totalCheckEndTime.getTime())) {
            resultTpDTO = new MrpResultDTO();
            if (analysisPeriod.equalsIgnoreCase("ngày")) {
                for (String exactTimeTp : listAnalysisTime.keySet()) {
                    if (exactTimeTp.equals(simpleDateFormat.format(totalCheckStartTime.getTime()))) {
                        resultTpDTO.setLandmark(exactTimeTp);
                        resultTpDTO.setOriginQuantity(listAnalysisTime.get(exactTimeTp));

                        break;
                    } else {
                        resultTpDTO.setLandmark(simpleDateFormat.format(totalCheckStartTime.getTime()));
                        resultTpDTO.setOriginQuantity((double) 0);
                    }
                }
                mrpResultDTOS.add(resultTpDTO);
                totalCheckStartTime.add(Calendar.DATE, 1);

            } else if (analysisPeriod.equalsIgnoreCase("tuần")) {
//                if (totalCheckStartTime.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || simpleDateFormat.format(totalCheckStartTime.getTime()).equals(simpleDateFormat.format(totalCheckEndTime.getTime()))) {
//                    for (String exactTimeTp : listAnalysisTime.keySet()) {
//                        if (exactTimeTp.equals(simpleDateFormat.format(totalCheckStartTime.getTime()))) {
//                            resultTpDTO.setLandmark(exactTimeTp);
//                            resultTpDTO.setOriginQuantity(listAnalysisTime.get(exactTimeTp));
//                            break;
//                        } else {
//                            resultTpDTO.setLandmark(simpleDateFormat.format(totalCheckStartTime.getTime()));
//                            resultTpDTO.setOriginQuantity((double) 0);
//                        }
//                    }
//                    mrpResultDTOS.add(resultTpDTO);
//                }
//                totalCheckStartTime.add(Calendar.DATE, 1);

                calCheck.setTime(totalCheckStartTime.getTime());

                if (calCheck.getTime().before(totalCheckEndTime.getTime())){

                    for (String exactTimeTp : listAnalysisTime.keySet()) {
                        if (exactTimeTp.equals(simpleDateFormat.format(totalCheckStartTime.getTime()))) {
                            resultTpDTO.setLandmark(exactTimeTp);
                            resultTpDTO.setOriginQuantity(listAnalysisTime.get(exactTimeTp));
                            break;
                        } else {
                            resultTpDTO.setLandmark(simpleDateFormat.format(totalCheckStartTime.getTime()));
                            resultTpDTO.setOriginQuantity((double) 0);
                        }
                    }
                    mrpResultDTOS.add(resultTpDTO);

                    calCheck.add(Calendar.DATE, 7);
                    if (calCheck.getTime().after(totalCheckEndTime.getTime()) || simpleDateFormat.format(calCheck.getTime()).equals(simpleDateFormat.format(totalCheckEndTime.getTime()))){
                        resultTpDTO = new MrpResultDTO();
                        for (String exactTimeTp : listAnalysisTime.keySet()) {
                            if (exactTimeTp.equals(simpleDateFormat.format(totalCheckEndTime.getTime()))) {
                                resultTpDTO.setLandmark(exactTimeTp);
                                resultTpDTO.setOriginQuantity(listAnalysisTime.get(exactTimeTp));
                                break;
                            } else {
                                resultTpDTO.setLandmark(simpleDateFormat.format(totalCheckEndTime.getTime()));
                                resultTpDTO.setOriginQuantity((double) 0);
                            }
                        }
                        mrpResultDTOS.add(resultTpDTO);
                        break;
                    }
                    totalCheckStartTime.add(Calendar.DATE, 7);
                }


            } else if (analysisPeriod.equalsIgnoreCase("1/2 tuần")) {
//                if (totalCheckStartTime.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY || totalCheckStartTime.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || simpleDateFormat.format(totalCheckStartTime.getTime()).equals(simpleDateFormat.format(totalCheckEndTime.getTime()))) {
//                    for (String exactTimeTp : listAnalysisTime.keySet()) {
//                        if (exactTimeTp.equals(simpleDateFormat.format(totalCheckStartTime.getTime()))) {
//                            resultTpDTO.setLandmark(exactTimeTp);
//                            resultTpDTO.setOriginQuantity(listAnalysisTime.get(exactTimeTp));
//                            break;
//                        } else {
//                            resultTpDTO.setLandmark(simpleDateFormat.format(totalCheckStartTime.getTime()));
//                            resultTpDTO.setOriginQuantity((double) 0);
//                        }
//                    }
//                    mrpResultDTOS.add(resultTpDTO);
//                }
//                totalCheckStartTime.add(Calendar.DATE, 1);

                calCheck.setTime(totalCheckStartTime.getTime());
                if (calCheck.getTime().before(totalCheckEndTime.getTime())){
                    for (String exactTimeTp : listAnalysisTime.keySet()) {
                        if (exactTimeTp.equals(simpleDateFormat.format(totalCheckStartTime.getTime()))) {
                            resultTpDTO.setLandmark(exactTimeTp);
                            resultTpDTO.setOriginQuantity(listAnalysisTime.get(exactTimeTp));
                            break;
                        } else {
                            resultTpDTO.setLandmark(simpleDateFormat.format(totalCheckStartTime.getTime()));
                            resultTpDTO.setOriginQuantity((double) 0);
                        }
                    }
                    mrpResultDTOS.add(resultTpDTO);

                    calCheck.add(Calendar.DATE, 3);
                    if (calCheck.getTime().after(totalCheckEndTime.getTime()) || simpleDateFormat.format(calCheck.getTime()).equals(simpleDateFormat.format(totalCheckEndTime.getTime()))){
                        resultTpDTO = new MrpResultDTO();
                        for (String exactTimeTp : listAnalysisTime.keySet()) {
                            if (exactTimeTp.equals(simpleDateFormat.format(totalCheckEndTime.getTime()))) {
                                resultTpDTO.setLandmark(exactTimeTp);
                                resultTpDTO.setOriginQuantity(listAnalysisTime.get(exactTimeTp));
                                break;
                            } else {
                                resultTpDTO.setLandmark(simpleDateFormat.format(totalCheckEndTime.getTime()));
                                resultTpDTO.setOriginQuantity((double) 0);
                            }
                        }
                        mrpResultDTOS.add(resultTpDTO);
                        break;
                    }
                    totalCheckStartTime.add(Calendar.DATE, 3);
                }

            } else if (analysisPeriod.equalsIgnoreCase("2 tuần")) {
//                if (totalCheckStartTime.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || simpleDateFormat.format(totalCheckStartTime.getTime()).equals(simpleDateFormat.format(totalCheckEndTime.getTime()))) {
//                    for (String exactTimeTp : listAnalysisTime.keySet()) {
//                        if (exactTimeTp.equals(simpleDateFormat.format(totalCheckStartTime.getTime()))) {
//                            resultTpDTO.setLandmark(exactTimeTp);
//                            resultTpDTO.setOriginQuantity(listAnalysisTime.get(exactTimeTp));
//                            break;
//                        } else {
//                            resultTpDTO.setLandmark(simpleDateFormat.format(totalCheckStartTime.getTime()));
//                            resultTpDTO.setOriginQuantity((double) 0);
//                        }
//                    }
//                    mrpResultDTOS.add(resultTpDTO);
//
//                    calCheck.setTime(totalCheckStartTime.getTime());
//                    log.info("calcheck: {}", simpleDateFormat.format(calCheck.getTime()));
//                    calCheck.add(Calendar.DATE, 14);
//                    if (calCheck.getTime().after(totalCheckEndTime.getTime())) {
//                        totalCheckStartTime.add(Calendar.DATE, 1);
//                    } else {
//                        totalCheckStartTime.add(Calendar.DATE, 14);
//                    }
//                } else {
//                    totalCheckStartTime.add(Calendar.DATE, 1);
//                }

                calCheck.setTime(totalCheckStartTime.getTime());
                if (calCheck.getTime().before(totalCheckEndTime.getTime())){
                    for (String exactTimeTp : listAnalysisTime.keySet()) {
                        if (exactTimeTp.equals(simpleDateFormat.format(totalCheckStartTime.getTime()))) {
                            resultTpDTO.setLandmark(exactTimeTp);
                            resultTpDTO.setOriginQuantity(listAnalysisTime.get(exactTimeTp));
                            break;
                        } else {
                            resultTpDTO.setLandmark(simpleDateFormat.format(totalCheckStartTime.getTime()));
                            resultTpDTO.setOriginQuantity((double) 0);
                        }
                    }
                    mrpResultDTOS.add(resultTpDTO);

                    calCheck.add(Calendar.DATE, 14);
                    if (calCheck.getTime().after(totalCheckEndTime.getTime()) || simpleDateFormat.format(calCheck.getTime()).equals(simpleDateFormat.format(totalCheckEndTime.getTime()))){
                        resultTpDTO = new MrpResultDTO();
                        for (String exactTimeTp : listAnalysisTime.keySet()) {
                            if (exactTimeTp.equals(simpleDateFormat.format(totalCheckEndTime.getTime()))) {
                                resultTpDTO.setLandmark(exactTimeTp);
                                resultTpDTO.setOriginQuantity(listAnalysisTime.get(exactTimeTp));
                                break;
                            } else {
                                resultTpDTO.setLandmark(simpleDateFormat.format(totalCheckEndTime.getTime()));
                                resultTpDTO.setOriginQuantity((double) 0);
                            }
                        }
                        mrpResultDTOS.add(resultTpDTO);
                        break;
                    }
                    totalCheckStartTime.add(Calendar.DATE, 14);
                }

            } else if (analysisPeriod.equalsIgnoreCase("tháng")) {
                if (totalCheckStartTime.get(Calendar.DATE) == totalCheckStartTime.getActualMaximum(Calendar.DATE) || simpleDateFormat.format(totalCheckStartTime.getTime()).equals(simpleDateFormat.format(totalCheckEndTime.getTime()))) {
                    for (String exactTimeTp : listAnalysisTime.keySet()) {
                        if (exactTimeTp.equals(simpleDateFormat.format(totalCheckStartTime.getTime()))) {
                            resultTpDTO.setLandmark(exactTimeTp);
                            resultTpDTO.setOriginQuantity(listAnalysisTime.get(exactTimeTp));
                            break;
                        } else {
                            resultTpDTO.setLandmark(simpleDateFormat.format(totalCheckStartTime.getTime()));
                            resultTpDTO.setOriginQuantity((double) 0);
                        }
                    }
                    mrpResultDTOS.add(resultTpDTO);
                }
                totalCheckStartTime.add(Calendar.DATE, 1);
            }
        }

        //query lấy children mức NVL của sản phẩm
        detailDTOS = coittRepository.getAllMrpNVLProductBom(mrpDetailDTO.getItemCode(), mrpDetailDTO.getBomVersion());
        log.info("children của sản phẩm: {}", detailDTOS);
        //Merge children NVL trong DB MRP để thêm các NVL không theo bom
        log.info("parentPath: {}", parentPath);
        mergeDetailNVLList(detailDTOS, parentPath, soCode);

        //Cho vào hàm đệ quy để tìm các level nhỏ hơn lồng nhau ở mức BTP
        if (!mrpDetailDTO.getChildren().isEmpty() && mrpDetailDTO.getChildren() != null) {
            log.info("Goi vao ham BTP");
            getCurrentInventory(
                mrpDetailDTO.getChildren(),
                currentInvenHM, //Tham số cẩn tìm
                whsCodeS,
                mrpItemQuantityHM, //Tham số cần tìm
                analysisOptions
            );

            for (MrpDetailDTO mrpDetailBtpDTO : mrpDetailDTO.getChildren()) {
                String parentPathBtp = mrpDetailBtpDTO.getParentPath() + "_" + mrpDetailBtpDTO.getItemCode();
                analysisSingleProduct(
                    mrpDetailBtpDTO,
                    itemOnDuplicateMap,
                    mrpItemQuantityHM.get(mrpDetailBtpDTO.getItemCode()),
                    whsCodeS,
                    totalStartTime,
                    totalEndTime,
                    analysisPeriod,
                    analysisType,
                    soCode,
                    currentInvenHM.get(mrpDetailBtpDTO.getItemCode()),
                    Integer.valueOf(mrpDetailBtpDTO.getLevel()),
                    parentPathBtp,
                    analysisOptions
                );
            }
        }

        //Phân tích ở level2 cho mức NVL của sản phẩm
        analysisLevelNvl(
            detailDTOS,
            itemOnDuplicateMap,
            mrpDetailDTO,
            soCode,
            totalStartTime,
            totalEndTime,
            productQuantity,
            whsName,
            countLevel,
            parentPath,
            whsCodeS,
            analysisPeriod,
            analysisType,
            analysisOptions
        );

        //*******************************************************************
        //Set list result theo ngay va list children cho sản phẩm
        mrpDetailDTO.getDetailResult().clear();
        mrpDetailDTO.getDetailResult().addAll(mrpResultDTOS);
        mrpDetailDTO.getChildren().addAll(detailDTOS);
    }

    /**
     * @param detailDTOS
     * @param itemOnDuplicateMap
     * @param mrpDetailDTO
     * @param totalStartTime
     * @param totalEndTime
     * @param itemQuantity
     * @param whsName
     * @param level
     * @param parentPath
     * @param listWhs
     * @param analysisPeriod
     * @throws ParseException
     */
    public void analysisLevelNvl(
        List<MrpDetailDTO> detailDTOS,
        Map<String, List<List<MrpResultDTO>>> itemOnDuplicateMap,
        MrpDetailDTO mrpDetailDTO,
        String soCode,
        Calendar totalStartTime,
        Calendar totalEndTime,
        Double itemQuantity,
        String whsName,
        Integer level,
        String parentPath,
        List<String> listWhs,
        String analysisPeriod,
        Integer analysisType,
        List<String> analysisOptions
    ) throws ParseException {
        HashMap<String, List<ItemQuantityWithDate>> prAnalysisHM = new HashMap<>();
        HashMap<String, List<ItemQuantityWithDate>> poAnalysisHM = new HashMap<>();
        HashMap<String, Double> currentInventoryHM = new HashMap<>();

        List<MrpResultDTO> mrpResultDTOList;
//        List<ItemQuantityWithDate> mrpPrList;
//        List<ItemQuantityWithDate> mrpPoList;
        List<String> listItemCode = new ArrayList<>();
        List<ItemQuantityWithDate> mrpPrQuantity = new ArrayList<>();
        List<ItemQuantityWithDate> mrpPoQuantity = new ArrayList<>();
        Map<String, List<ItemQuantityWithDate>> itemOnOrderQuantityMap = new HashMap<>();
        Map<String, List<CurrentWarehouseInventory>> currentWarehouseInventoryMap = new HashMap<>();
        Map<String, List<ItemQuantityWithDate>> itemHoldQuantity = new HashMap<>();
        Map<String, List<ItemQuantityWithDate>> itemHoldQuantityOfFirstDate = new HashMap<>();
        Map<String, List<ItemQuantityWithDate>> itemHoldQuantityByWhs = new HashMap<>();
        Map<String, Double> poQuantityMap = new HashMap<>();
        Map<String, Double> prQuantityMap  = new HashMap<>();
//        Map<String, List<ItemQuantityWithDate>> grpoQuantityMap  = new HashMap<>();
//        Map<String, Map<String, List<ItemQuantityWithDate>>> quantityMap = new HashMap<>();
        Map<String, Map<String, Double>> quantityMap = new HashMap<>();

        Calendar timeStart;
        Calendar timeEnd;
        MrpResultDTO mrpResultDTO;
        int countLevel = level + 1;
        double requiredQuantity;

//        timeStart = parseStringToCalendar(mrpDetailDTO.getDetailResult().get(1).getLandmark());
//        timeEnd = parseStringToCalendar(mrpDetailDTO.getDetailResult().get(mrpDetailDTO.getDetailResult().size() - 1).getLandmark());
        //Query tìm số lượng PR/PO theo ds mã sản phẩm trong khoảng thời gian phân tích
        detailDTOS.forEach(detailDTO -> listItemCode.add(detailDTO.getItemCode()));

//        if (analysisPeriod.equalsIgnoreCase("ngày")) {
//            mrpPrQuantity = soosRepository.getAllPrPoQuantityByDay(timeStart.getTime(), timeEnd.getTime(), listItemCode, "PR");
//            mrpPoQuantity = soosRepository.getAllPrPoQuantityByDay(timeStart.getTime(), timeEnd.getTime(), listItemCode, "PO");
//        } else if (analysisPeriod.equalsIgnoreCase("tuần")) {
//            mrpPrQuantity.addAll(soosRepository.getAllPrPoQuantityByWeek(timeStart.getTime(), timeEnd.getTime(), listItemCode, "PR"));
//            mrpPoQuantity.addAll(soosRepository.getAllPrPoQuantityByWeek(timeStart.getTime(), timeEnd.getTime(), listItemCode, "PO"));
//        } else if (analysisPeriod.equalsIgnoreCase("tháng")) {
//            mrpPrQuantity.addAll(soosRepository.getAllPrPoQuantityByMonth(timeStart.getTime(), timeEnd.getTime(), listItemCode, "PR"));
//            mrpPoQuantity.addAll(soosRepository.getAllPrPoQuantityByMonth(timeStart.getTime(), timeEnd.getTime(), listItemCode, "PO"));
//        } else if (analysisPeriod.equalsIgnoreCase("1/2 tuần")) {
//            mrpPrQuantity.addAll(soosRepository.getAllPrPoQuantityByHalfWeek(timeStart.getTime(), timeEnd.getTime(), listItemCode, "PR"));
//            mrpPoQuantity.addAll(soosRepository.getAllPrPoQuantityByHalfWeek(timeStart.getTime(), timeEnd.getTime(), listItemCode, "PO"));
//        } else if (analysisPeriod.equalsIgnoreCase("2 tuần")) {
//            mrpPrQuantity.addAll(soosRepository.getAllPrPoQuantityByTwoWeek(timeStart.getTime(), timeEnd.getTime(), listItemCode, "PR"));
//            mrpPoQuantity.addAll(soosRepository.getAllPrPoQuantityByTwoWeek(timeStart.getTime(), timeEnd.getTime(), listItemCode, "PO"));
//        }


        log.info("listItemCode: {}", listItemCode);
        //Cho PR/PO vao HashMap với Key: mã sản phẩm - Value: pr/po theo ngày
//        for (String itemCode : listItemCode) {
//            mrpPrList = new ArrayList<>();
//            for (ItemQuantityWithDate item : mrpPrQuantity) {
//                if (item.getItemCode().equals(itemCode)) {
//                    mrpPrList.add(item);
//                }
//            }
//            prAnalysisHM.put(itemCode, mrpPrList);
//        }
//        log.info("mrpPrList: {}", prAnalysisHM);
//
//        for (String itemCode : listItemCode) {
//            mrpPoList = new ArrayList<>();
//            for (ItemQuantityWithDate item : mrpPoQuantity) {
//                if (item.getItemCode().equals(itemCode)) {
//                    mrpPoList.add(item);
//                }
//            }
//            poAnalysisHM.put(itemCode, mrpPoList);
//        }
//        log.info("mrpPoList: {}", poAnalysisHM);

        // Lấy số lượng item bị hold
        itemHoldQuantityByWhs = itemHoldRepository.getAllHoldQuantityItemMapByWhs(listItemCode, listWhs, totalStartTime.getTime(), totalEndTime.getTime(), analysisPeriod);
        if (analysisType == Constants.AnalysisType.NEW) {
            itemHoldQuantity = itemHoldRepository.getAllHoldQuantityItemMap(listItemCode, listWhs,
                DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH),
                totalEndTime.getTime(), analysisPeriod);
        }

        // Lấy tổng số lượng PR ở MRP
        if (analysisOptions.contains("PR/PO")) {
            itemOnOrderQuantityMap = purchaseRecommendationPlanRepository.sumAllQuantityOfItemsByDayMap(listItemCode, soCode, totalStartTime.getTime(), totalEndTime.getTime());
//            quantityMap = soosRepository.getAllSumQuantityByItemsMap(totalStartTime.getTime(), totalEndTime.getTime(), listItemCode);
//            quantityMap = soosRepository.getAllPOSumQuantityByItemsMap(totalStartTime.getTime(), totalEndTime.getTime(), listItemCode);
            prQuantityMap = prq1Repository.getOpenQuantityMap(listItemCode);
            poQuantityMap = por1Repository.getOpenQuantityMap(listItemCode);
        }

        if (analysisOptions.contains("kho")) {
            //Lấy số lượng hiện trạng tồn kho của list NVL
            List<CurrentInventory> currentInventory = oitwRepository.getAllCurrentInventoryByWhs(listItemCode, listWhs);
            log.info("số lượng hiện trạng tồn kho của list NVL va BTP: {}", currentInventory.size());
            for (CurrentInventory ci : currentInventory) {
                currentInventoryHM.put(ci.getItemCode(), ci.getCurrentQuantity());
            }

            List<CurrentWarehouseInventory> currentWarehouseInventories = oitwRepository.getAllCurrentInventoryWithWhs(listItemCode, listWhs);
            currentWarehouseInventoryMap = currentWarehouseInventories.stream().collect(Collectors.groupingBy(CurrentInventory::getItemCode));
        }

        //Vòng lặp để set các thông tin của từng NVL
        for (MrpDetailDTO mrpDetailNVL : detailDTOS) {
            mrpResultDTOList = new ArrayList<>();
            mrpResultDTO = new MrpResultDTO();

            //Từ số lượng yêu cầu của mức cha => số lượng yêu cầu mức con
            requiredQuantity = itemQuantity * mrpDetailNVL.getQuota();

            // Tổng số lượng hold
            double totalHoldQuantity = 0;
            if (itemHoldQuantityByWhs.containsKey(mrpDetailNVL.getItemCode())) {
                totalHoldQuantity += itemHoldQuantityByWhs.get(mrpDetailNVL.getItemCode()).stream().mapToDouble(ItemQuantityWithDate::getQuantity).sum();
            }
            if (itemHoldQuantity.containsKey(mrpDetailNVL.getItemCode())) {
                double holdQuantityByItem = itemHoldQuantity.get(mrpDetailNVL.getItemCode()).stream().mapToDouble(ItemQuantityWithDate::getQuantity).sum();
                totalHoldQuantity += holdQuantityByItem;
                mrpResultDTO.setRequiredQuantity(holdQuantityByItem);
            }

            //Lấy số lượng hiện trạng cho tung NVL
            mrpResultDTO.setLandmark(Constants.MrpAnalysis.SAP_QUANTITY_STRING);
            mrpResultDTO.setOriginQuantity(currentInventoryHM.getOrDefault(mrpDetailNVL.getItemCode(), 0.0));
            mrpResultDTO.setReadyQuantity(currentInventoryHM.getOrDefault(mrpDetailNVL.getItemCode(), 0.0) - totalHoldQuantity);
            mrpResultDTO.setExpectedQuantity(itemOnOrderQuantityMap.containsKey(mrpDetailDTO.getItemCode())
                ? itemOnOrderQuantityMap.get(mrpDetailDTO.getItemCode()).stream().mapToDouble(ItemQuantityWithDate::getQuantity).sum() : 0.0);

            mrpResultDTOList.add(mrpResultDTO);

            //lấy result phân tích theo thời gian ở mức NVl
            analysisNvlByDayWeekMonth(
                mrpDetailNVL,
                mrpDetailDTO,
                totalStartTime,
                totalEndTime,
                analysisPeriod,
                prAnalysisHM,
                poAnalysisHM,
                itemOnOrderQuantityMap,
//                poQuantityMap,
//                prQuantityMap,
//                grpoQuantityMap,
//                quantityMap.getOrDefault("PO", new HashMap<>()),
//                quantityMap.getOrDefault("PR", new HashMap<>()),
                poQuantityMap,
                prQuantityMap,
                quantityMap.getOrDefault("GRPO", new HashMap<>()),
                itemHoldQuantity,
                itemHoldQuantityByWhs,
                currentInventoryHM,
                mrpResultDTOList,
                analysisOptions);

            //******************************
            //Fill Dữ liệu cho từng NVL
//            mrpDetailNVL.setStatus("Mua hàng");
//            mrpDetailNVL.setGroupItem(100);
            mrpDetailNVL.setIsHold(
                itemHoldQuantity.containsKey(mrpDetailNVL.getItemCode())
                    || itemHoldQuantityByWhs.containsKey(mrpDetailNVL.getItemCode())
            );
            mrpDetailNVL.setRequiredQuantity(requiredQuantity);
            // SL sẵn sàng
            mrpDetailNVL.setInStockQuantity(mrpResultDTOList.get(0).getReadyQuantity());
            mrpDetailNVL.setWhsName(whsName);
            mrpDetailNVL.setLevel(String.valueOf(countLevel));
            mrpDetailNVL.setParentPath(parentPath);
            mrpDetailNVL.setDetailResult(mrpResultDTOList);
            mrpDetailNVL.setCurrentWarehouseInventoryList(currentWarehouseInventoryMap.get(mrpDetailNVL.getItemCode()));

            if (!itemOnDuplicateMap.containsKey(mrpDetailNVL.getItemCode()))
                itemOnDuplicateMap.put(mrpDetailNVL.getItemCode(), new ArrayList<>());
            itemOnDuplicateMap.get(mrpDetailNVL.getItemCode()).add(mrpDetailNVL.getDetailResult());
        }
    }

    /**
     * @param mrpDetailNVL
     * @param mrpDetailDTO
     * @param analysisPeriod
     * @param prAnalysisHM
     * @param poAnalysisHM
     * @param currentInventoryHM
     * @param mrpResultDTOList
     * @throws ParseException
     */
    public void analysisNvlByDayWeekMonth(
        MrpDetailDTO mrpDetailNVL,
        MrpDetailDTO mrpDetailDTO,
        Calendar totalStartTime,
        Calendar totalEndTime,
        String analysisPeriod,
        HashMap<String, List<ItemQuantityWithDate>> prAnalysisHM,
        HashMap<String, List<ItemQuantityWithDate>> poAnalysisHM,
        Map<String, List<ItemQuantityWithDate>> itemOnOrderQuantityMap,
//        Map<String, List<ItemQuantityWithDate>> poQuantity,
//        Map<String, List<ItemQuantityWithDate>> prQuantity,
//        Map<String, List<ItemQuantityWithDate>> grpoQuantity,
        Map<String, Double> poQuantity,
        Map<String, Double> prQuantity,
        Map<String, Double> grpoQuantity,
        Map<String, List<ItemQuantityWithDate>> itemHoldQuantity,
        Map<String, List<ItemQuantityWithDate>> itemHoldQuantityByWhs,
        HashMap<String, Double> currentInventoryHM,
        List<MrpResultDTO> mrpResultDTOList,
        List<String> analysisOptions
    ) throws ParseException {
        MrpResultDTO mrpResultDTO;
        Calendar calCheck = Calendar.getInstance();
        Calendar totalCheckStartTime = Calendar.getInstance();
        Calendar totalCheckEndTime = Calendar.getInstance();
        HashMap<String, Double> listAnalysisTime = new HashMap<>();

        //Lấy danh sách thời gian phân tích ở mức cha
        for (MrpResultDTO resultDTO : mrpDetailDTO.getDetailResult()) {
            listAnalysisTime.put(resultDTO.getLandmark(), resultDTO.getOriginQuantity());
        }
        log.info("listAnalysisTime: {}", listAnalysisTime.keySet().size());

        totalCheckStartTime.setTime(totalStartTime.getTime());
        totalCheckEndTime.setTime(totalEndTime.getTime());

        Date beforeDate = new Date(Long.MIN_VALUE);
        Date holdBeforeDate = new Date(Long.MIN_VALUE);
        double lastInStockQuantity = mrpResultDTOList.get(0).getOriginQuantity();
        boolean isFirstDate = true;
        boolean isPassFirstDate = false;
        //lấy result phân tích theo ngày ở mức NVl
        while (totalCheckStartTime.getTime().before(totalCheckEndTime.getTime()) || totalCheckStartTime.getTime().equals(totalCheckEndTime.getTime())) {
            mrpResultDTO = new MrpResultDTO();

            if (analysisPeriod.equalsIgnoreCase("ngày")) {
//                if ((prAnalysisHM.get(mrpDetailNVL.getItemCode()) != null && !prAnalysisHM.get(mrpDetailNVL.getItemCode()).isEmpty()) || (poAnalysisHM.get(mrpDetailNVL.getItemCode()) != null && !poAnalysisHM.get(mrpDetailNVL.getItemCode()).isEmpty())) {
//                    for (ItemQuantityWithDate checkDate : prAnalysisHM.get(mrpDetailNVL.getItemCode())) {
//
//                        if (simpleDateFormat.format(totalCheckStartTime.getTime()).equals(checkDate.getDate())) {
//                            mrpResultDTO.setRequiredQuantity(checkDate.getQuantity());
//                            break;
//                        } else {
//                            mrpResultDTO.setRequiredQuantity((double) 0);
//                        }
//                    }
//                    for (ItemQuantityWithDate checkDate : poAnalysisHM.get(mrpDetailNVL.getItemCode())) {
//                        if (simpleDateFormat.format(totalCheckStartTime.getTime()).equals(checkDate.getDate())) {
//                            mrpResultDTO.setExpectedQuantity(checkDate.getQuantity());
//                            mrpResultDTO.setFinalInventory(currentInventoryHM.getOrDefault(mrpDetailDTO.getItemCode(), 0.0) + checkDate.getQuantity());
//                            break;
//                        } else {
//                            mrpResultDTO.setExpectedQuantity((double) 0);
//                            mrpResultDTO.setFinalInventory(currentInventoryHM.get(mrpDetailNVL.getItemCode()));
//                        }
//                    }
//                } else {
//                    mrpResultDTO.setRequiredQuantity((double) 0);
//                    mrpResultDTO.setExpectedQuantity((double) 0);
//                    mrpResultDTO.setFinalInventory(currentInventoryHM.get(mrpDetailNVL.getItemCode()));
//                }

                //Check số lượng yêu cầu của NVl theo ngày phân tích của mức cha
                for (String exactTime : listAnalysisTime.keySet()) {
                    if (exactTime.equals(simpleDateFormat.format(totalCheckStartTime.getTime()))) {
                        mrpResultDTO.setOriginQuantity(listAnalysisTime.get(exactTime) * mrpDetailNVL.getQuota());
                        if (isFirstDate) {
                            isFirstDate = false;
                            isPassFirstDate = true;
                            MrpAnalysisUtil.setPrPoQuantity(mrpDetailNVL.getItemCode(), mrpResultDTO, prQuantity, poQuantity, grpoQuantity);
                        }
                        break;
                    } else {
                        mrpResultDTO.setOriginQuantity((double) 0);
                    }
                }

                MrpAnalysisUtil.setAnalysisDetail(mrpDetailNVL, mrpResultDTO, beforeDate, totalCheckStartTime, itemOnOrderQuantityMap, poQuantity, prQuantity, grpoQuantity, itemHoldQuantity, itemHoldQuantityByWhs, currentInventoryHM, simpleDateFormat);
                if (isPassFirstDate) {
                    MrpAnalysisUtil.setHoldQuantity(mrpDetailNVL, mrpResultDTO, holdBeforeDate, totalCheckStartTime, itemHoldQuantity, simpleDateFormat);
                    MrpAnalysisUtil.setHoldQuantity(mrpDetailNVL, mrpResultDTO, holdBeforeDate, totalCheckStartTime, itemHoldQuantityByWhs, simpleDateFormat);
                    holdBeforeDate.setTime(totalCheckStartTime.getTime().getTime());
                }
                mrpResultDTO.setLandmark(simpleDateFormat.format(totalCheckStartTime.getTime()));

                mrpResultDTOList.add(mrpResultDTO);
                log.info("Lưu mrpResultDTOList : {}", mrpResultDTOList.size());
                beforeDate.setTime(totalCheckStartTime.getTime().getTime());
                totalCheckStartTime.add(Calendar.DATE, 1);

            } else if (analysisPeriod.equalsIgnoreCase("tuần")) {
//                if (totalCheckStartTime.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || simpleDateFormat.format(totalCheckStartTime.getTime()).equals(simpleDateFormat.format(totalCheckEndTime.getTime()))) {
//                    if ((prAnalysisHM.get(mrpDetailNVL.getItemCode()) != null && !prAnalysisHM.get(mrpDetailNVL.getItemCode()).isEmpty()) || (poAnalysisHM.get(mrpDetailNVL.getItemCode()) != null && !poAnalysisHM.get(mrpDetailNVL.getItemCode()).isEmpty())) {
//                        for (ItemQuantityWithDate checkDate : prAnalysisHM.get(mrpDetailNVL.getItemCode())) {
//                            log.info("CheckDate: {}", checkDate.getDate());
//                            log.info("calender: {}", simpleDateFormat.format(totalCheckStartTime.getTime()));
//
//                            if (simpleDateFormat.format(totalCheckStartTime.getTime()).equals(checkDate.getDate())) {
//                                mrpResultDTO.setRequiredQuantity(checkDate.getQuantity());
//                                break;
//                            } else {
//                                mrpResultDTO.setRequiredQuantity((double) 0);
//                            }
//                        }
//                        for (ItemQuantityWithDate checkDate : poAnalysisHM.get(mrpDetailNVL.getItemCode())) {
//                            if (simpleDateFormat.format(totalCheckStartTime.getTime()).equals(checkDate.getDate())) {
//                                mrpResultDTO.setExpectedQuantity(checkDate.getQuantity());
//                                mrpResultDTO.setFinalInventory(currentInventoryHM.getOrDefault(mrpDetailNVL.getItemCode(), 0.0) + checkDate.getQuantity());
//                                break;
//                            } else {
//                                mrpResultDTO.setExpectedQuantity((double) 0);
//                                mrpResultDTO.setFinalInventory(currentInventoryHM.get(mrpDetailNVL.getItemCode()));
//                            }
//                        }
//                    } else {
//                        mrpResultDTO.setRequiredQuantity((double) 0);
//                        mrpResultDTO.setExpectedQuantity((double) 0);
//                        mrpResultDTO.setFinalInventory(currentInventoryHM.get(mrpDetailNVL.getItemCode()));
//                    }

                    //Check số lượng yêu cầu của NVl theo ngày phân tích của mức cha
//                    for (String exactTime : listAnalysisTime.keySet()) {
//                        if (exactTime.equals(simpleDateFormat.format(totalCheckStartTime.getTime()))) {
//                            mrpResultDTO.setOriginQuantity(listAnalysisTime.get(exactTime) * mrpDetailNVL.getQuota());
//                            if (isFirstDate) {
//                                isFirstDate = false;
//                                MrpAnalysisUtil.setPrPoQuantity(mrpDetailNVL.getItemCode(), mrpResultDTO, prQuantity, poQuantity, grpoQuantity);
//                            }
//                            break;
//                        } else {
//                            mrpResultDTO.setOriginQuantity((double) 0);
//                        }
//                    }
//
//                    MrpAnalysisUtil.setAnalysisDetail(mrpDetailNVL, mrpResultDTO, beforeDate, totalCheckStartTime, itemOnOrderQuantityMap, poQuantity, prQuantity, grpoQuantity, itemHoldQuantity, itemHoldQuantityByWhs, currentInventoryHM, simpleDateFormat);
//
//                    mrpResultDTO.setLandmark(simpleDateFormat.format(totalCheckStartTime.getTime()));
//
//                    mrpResultDTOList.add(mrpResultDTO);
//
//                    beforeDate.setTime(totalCheckStartTime.getTime().getTime());
//                }
//                totalCheckStartTime.add(Calendar.DATE, 1);
//////////////////////////////////////////////////////////////////////////////////////////////
                calCheck.setTime(totalCheckStartTime.getTime());
                if (calCheck.getTime().before(totalCheckEndTime.getTime())){
                    for (String exactTimeTp : listAnalysisTime.keySet()) {
                        if (exactTimeTp.equals(simpleDateFormat.format(totalCheckStartTime.getTime()))) {
                            mrpResultDTO.setLandmark(exactTimeTp);
                            mrpResultDTO.setOriginQuantity(listAnalysisTime.get(exactTimeTp) * mrpDetailNVL.getQuota());
                            if (isFirstDate) {
                                isFirstDate = false;
                                isPassFirstDate = true;
                                MrpAnalysisUtil.setPrPoQuantity(mrpDetailNVL.getItemCode(), mrpResultDTO, prQuantity, poQuantity, grpoQuantity);
                            }
                            break;
                        } else {
                            mrpResultDTO.setLandmark(simpleDateFormat.format(totalCheckStartTime.getTime()));
                            mrpResultDTO.setOriginQuantity((double) 0);
                        }
                    }

                    MrpAnalysisUtil.setAnalysisDetail(mrpDetailNVL, mrpResultDTO, beforeDate, totalCheckStartTime, itemOnOrderQuantityMap, poQuantity, prQuantity, grpoQuantity, itemHoldQuantity, itemHoldQuantityByWhs, currentInventoryHM, simpleDateFormat);
                    if (isPassFirstDate) {
                        MrpAnalysisUtil.setHoldQuantity(mrpDetailNVL, mrpResultDTO, holdBeforeDate, totalCheckStartTime, itemHoldQuantity, simpleDateFormat);
                        MrpAnalysisUtil.setHoldQuantity(mrpDetailNVL, mrpResultDTO, holdBeforeDate, totalCheckStartTime, itemHoldQuantityByWhs, simpleDateFormat);
                        holdBeforeDate.setTime(totalCheckStartTime.getTime().getTime());
                    }

                    mrpResultDTOList.add(mrpResultDTO);
                    beforeDate.setTime(totalCheckStartTime.getTime().getTime());

                    calCheck.add(Calendar.DATE, 7);
                    if (calCheck.getTime().after(totalCheckEndTime.getTime()) || simpleDateFormat.format(calCheck.getTime()).equals(simpleDateFormat.format(totalCheckEndTime.getTime()))){
                        mrpResultDTO = new MrpResultDTO();
                        for (String exactTimeTp : listAnalysisTime.keySet()) {
                            if (exactTimeTp.equals(simpleDateFormat.format(totalCheckEndTime.getTime()))) {
                                mrpResultDTO.setLandmark(exactTimeTp);
                                mrpResultDTO.setOriginQuantity(listAnalysisTime.get(exactTimeTp) * mrpDetailNVL.getQuota());
                                if (isFirstDate) {
                                    isFirstDate = false;
                                    isPassFirstDate = true;
                                    MrpAnalysisUtil.setPrPoQuantity(mrpDetailNVL.getItemCode(), mrpResultDTO, prQuantity, poQuantity, grpoQuantity);
                                }
                                break;
                            } else {
                                mrpResultDTO.setLandmark(simpleDateFormat.format(totalCheckEndTime.getTime()));
                                mrpResultDTO.setOriginQuantity((double) 0);
                            }
                        }

                        MrpAnalysisUtil.setAnalysisDetail(mrpDetailNVL, mrpResultDTO, beforeDate, totalCheckEndTime, itemOnOrderQuantityMap, poQuantity, prQuantity, grpoQuantity, itemHoldQuantity, itemHoldQuantityByWhs, currentInventoryHM, simpleDateFormat);
                        if (isPassFirstDate) {
                            MrpAnalysisUtil.setHoldQuantity(mrpDetailNVL, mrpResultDTO, holdBeforeDate, totalCheckStartTime, itemHoldQuantity, simpleDateFormat);
                            MrpAnalysisUtil.setHoldQuantity(mrpDetailNVL, mrpResultDTO, holdBeforeDate, totalCheckStartTime, itemHoldQuantityByWhs, simpleDateFormat);
                            holdBeforeDate.setTime(totalCheckStartTime.getTime().getTime());
                        }

                        mrpResultDTOList.add(mrpResultDTO);
                        beforeDate.setTime(totalCheckEndTime.getTime().getTime());
                        break;
                    }
                    totalCheckStartTime.add(Calendar.DATE, 7);
                }

            } else if (analysisPeriod.equalsIgnoreCase("tháng")) {
                if (totalCheckStartTime.get(Calendar.DATE) == totalCheckStartTime.getActualMaximum(Calendar.DATE) || simpleDateFormat.format(totalCheckStartTime.getTime()).equals(simpleDateFormat.format(totalCheckEndTime.getTime()))) {
//                    if ((prAnalysisHM.get(mrpDetailNVL.getItemCode()) != null && !prAnalysisHM.get(mrpDetailNVL.getItemCode()).isEmpty()) || (poAnalysisHM.get(mrpDetailNVL.getItemCode()) != null && !poAnalysisHM.get(mrpDetailNVL.getItemCode()).isEmpty())) {
//                        for (ItemQuantityWithDate checkDate : prAnalysisHM.get(mrpDetailNVL.getItemCode())) {
//                            log.info("CheckDate: {}", checkDate.getDate());
//                            log.info("calender: {}", simpleDateFormat.format(totalCheckStartTime.getTime()));
//
//                            if (simpleDateFormatByMonth.format(totalCheckStartTime.getTime()).equals(checkDate.getDate())) {
//                                mrpResultDTO.setRequiredQuantity(checkDate.getQuantity());
//                                break;
//                            } else {
//                                mrpResultDTO.setRequiredQuantity((double) 0);
//                            }
//                        }
//                        for (ItemQuantityWithDate checkDate : poAnalysisHM.get(mrpDetailNVL.getItemCode())) {
//                            if (simpleDateFormatByMonth.format(totalCheckStartTime.getTime()).equals(checkDate.getDate())) {
//                                mrpResultDTO.setExpectedQuantity(checkDate.getQuantity());
//                                mrpResultDTO.setFinalInventory(currentInventoryHM.getOrDefault(mrpDetailNVL.getItemCode(), 0.0) + checkDate.getQuantity());
//                                break;
//                            } else {
//                                mrpResultDTO.setExpectedQuantity((double) 0);
//                                mrpResultDTO.setFinalInventory(currentInventoryHM.get(mrpDetailNVL.getItemCode()));
//                            }
//                        }
//                    } else {
//                        mrpResultDTO.setRequiredQuantity((double) 0);
//                        mrpResultDTO.setExpectedQuantity((double) 0);
//                        mrpResultDTO.setFinalInventory(currentInventoryHM.get(mrpDetailNVL.getItemCode()));
//                    }

                    //Check số lượng yêu cầu của NVl theo ngày phân tích của mức cha
                    for (String exactTime : listAnalysisTime.keySet()) {
                        if (exactTime.equals(simpleDateFormat.format(totalCheckStartTime.getTime()))) {
                            mrpResultDTO.setOriginQuantity(listAnalysisTime.get(exactTime) * mrpDetailNVL.getQuota());
                            if (isFirstDate) {
                                isFirstDate = false;
                                isPassFirstDate = true;
                                MrpAnalysisUtil.setPrPoQuantity(mrpDetailNVL.getItemCode(), mrpResultDTO, prQuantity, poQuantity, grpoQuantity);
                            }
                            break;
                        } else {
                            mrpResultDTO.setOriginQuantity((double) 0);
                        }
                    }

                    MrpAnalysisUtil.setAnalysisDetail(mrpDetailNVL, mrpResultDTO, beforeDate, totalCheckStartTime, itemOnOrderQuantityMap, poQuantity, prQuantity, grpoQuantity, itemHoldQuantity, itemHoldQuantityByWhs, currentInventoryHM, simpleDateFormat);
                    if (isPassFirstDate) {
                        MrpAnalysisUtil.setHoldQuantity(mrpDetailNVL, mrpResultDTO, holdBeforeDate, totalCheckStartTime, itemHoldQuantity, simpleDateFormat);
                        MrpAnalysisUtil.setHoldQuantity(mrpDetailNVL, mrpResultDTO, holdBeforeDate, totalCheckStartTime, itemHoldQuantityByWhs, simpleDateFormat);
                        holdBeforeDate.setTime(totalCheckStartTime.getTime().getTime());
                    }

                    mrpResultDTO.setLandmark(simpleDateFormat.format(totalCheckStartTime.getTime()));

                    mrpResultDTOList.add(mrpResultDTO);
                    beforeDate.setTime(totalCheckStartTime.getTime().getTime());
                }
                totalCheckStartTime.add(Calendar.DATE, 1);

            } else if (analysisPeriod.equalsIgnoreCase("1/2 tuần")) {
//                if (totalCheckStartTime.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY || totalCheckStartTime.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || simpleDateFormat.format(totalCheckStartTime.getTime()).equals(simpleDateFormat.format(totalCheckEndTime.getTime()))) {
//                    if ((prAnalysisHM.get(mrpDetailNVL.getItemCode()) != null && !prAnalysisHM.get(mrpDetailNVL.getItemCode()).isEmpty()) || (poAnalysisHM.get(mrpDetailNVL.getItemCode()) != null && !poAnalysisHM.get(mrpDetailNVL.getItemCode()).isEmpty())) {
//                        for (ItemQuantityWithDate checkDate : prAnalysisHM.get(mrpDetailNVL.getItemCode())) {
//                            log.info("CheckDate: {}", checkDate.getDate());
//                            log.info("calender: {}", simpleDateFormat.format(totalCheckStartTime.getTime()));
//
//                            if (simpleDateFormat.format(totalCheckStartTime.getTime()).equals(checkDate.getDate())) {
//                                mrpResultDTO.setRequiredQuantity(checkDate.getQuantity());
//                                break;
//                            } else {
//                                mrpResultDTO.setRequiredQuantity((double) 0);
//                            }
//                        }
//                        for (ItemQuantityWithDate checkDate : poAnalysisHM.get(mrpDetailNVL.getItemCode())) {
//                            if (simpleDateFormat.format(totalCheckStartTime.getTime()).equals(checkDate.getDate())) {
//                                mrpResultDTO.setExpectedQuantity(checkDate.getQuantity());
//                                mrpResultDTO.setFinalInventory(currentInventoryHM.getOrDefault(mrpDetailNVL.getItemCode(), 0.0) + checkDate.getQuantity());
//                                break;
//                            } else {
//                                mrpResultDTO.setExpectedQuantity((double) 0);
//                                mrpResultDTO.setFinalInventory(currentInventoryHM.get(mrpDetailNVL.getItemCode()));
//                            }
//                        }
//                    } else {
//                        mrpResultDTO.setRequiredQuantity((double) 0);
//                        mrpResultDTO.setExpectedQuantity((double) 0);
//                        mrpResultDTO.setFinalInventory(currentInventoryHM.get(mrpDetailNVL.getItemCode()));
//                    }

                    //Check số lượng yêu cầu của NVl theo ngày phân tích của mức cha
//                    for (String exactTime : listAnalysisTime.keySet()) {
//                        log.info("exactTime: {}", exactTime);
//                        log.info("exactTime.value: {}", listAnalysisTime.get(exactTime));
//                        log.info("mrpDetailNVL.getQuota(): {}", mrpDetailNVL.getQuota());
//                        if (exactTime.equals(simpleDateFormat.format(totalCheckStartTime.getTime()))) {
//                            mrpResultDTO.setOriginQuantity(listAnalysisTime.get(exactTime) * mrpDetailNVL.getQuota());
//                            if (isFirstDate) {
//                                isFirstDate = false;
//                                MrpAnalysisUtil.setPrPoQuantity(mrpDetailNVL.getItemCode(), mrpResultDTO, prQuantity, poQuantity, grpoQuantity);
//                            }
//                            break;
//                        } else {
//                            mrpResultDTO.setOriginQuantity((double) 0);
//                        }
//                    }
//
//                    MrpAnalysisUtil.setAnalysisDetail(mrpDetailNVL, mrpResultDTO, beforeDate, totalCheckStartTime, itemOnOrderQuantityMap, poQuantity, prQuantity, grpoQuantity, itemHoldQuantity, itemHoldQuantityByWhs, currentInventoryHM, simpleDateFormat);
//
//                    mrpResultDTO.setLandmark(simpleDateFormat.format(totalCheckStartTime.getTime()));
//
//                    mrpResultDTOList.add(mrpResultDTO);
//
//                    beforeDate.setTime(totalCheckStartTime.getTime().getTime());
//                }
//                totalCheckStartTime.add(Calendar.DATE, 1);
////////////////////////////////////////////////////////////////////////////////////////////////
                calCheck.setTime(totalCheckStartTime.getTime());
                if (calCheck.getTime().before(totalCheckEndTime.getTime())){
                    for (String exactTimeTp : listAnalysisTime.keySet()) {
                        if (exactTimeTp.equals(simpleDateFormat.format(totalCheckStartTime.getTime()))) {
                            mrpResultDTO.setLandmark(exactTimeTp);
                            mrpResultDTO.setOriginQuantity(listAnalysisTime.get(exactTimeTp) * mrpDetailNVL.getQuota());
                            if (isFirstDate) {
                                isFirstDate = false;
                                isPassFirstDate = true;
                                MrpAnalysisUtil.setPrPoQuantity(mrpDetailNVL.getItemCode(), mrpResultDTO, prQuantity, poQuantity, grpoQuantity);
                            }
                            break;
                        } else {
                            mrpResultDTO.setLandmark(simpleDateFormat.format(totalCheckStartTime.getTime()));
                            mrpResultDTO.setOriginQuantity((double) 0);
                        }
                    }

                    MrpAnalysisUtil.setAnalysisDetail(mrpDetailNVL, mrpResultDTO, beforeDate, totalCheckStartTime, itemOnOrderQuantityMap, poQuantity, prQuantity, grpoQuantity, itemHoldQuantity, itemHoldQuantityByWhs, currentInventoryHM, simpleDateFormat);
                    if (isPassFirstDate) {
                        MrpAnalysisUtil.setHoldQuantity(mrpDetailNVL, mrpResultDTO, holdBeforeDate, totalCheckStartTime, itemHoldQuantity, simpleDateFormat);
                        MrpAnalysisUtil.setHoldQuantity(mrpDetailNVL, mrpResultDTO, holdBeforeDate, totalCheckStartTime, itemHoldQuantityByWhs, simpleDateFormat);
                        holdBeforeDate.setTime(totalCheckStartTime.getTime().getTime());
                    }

                    mrpResultDTOList.add(mrpResultDTO);
                    beforeDate.setTime(totalCheckStartTime.getTime().getTime());

                    calCheck.add(Calendar.DATE, 3);
                    if (calCheck.getTime().after(totalCheckEndTime.getTime()) || simpleDateFormat.format(calCheck.getTime()).equals(simpleDateFormat.format(totalCheckEndTime.getTime()))){
                        mrpResultDTO = new MrpResultDTO();
                        for (String exactTimeTp : listAnalysisTime.keySet()) {
                            if (exactTimeTp.equals(simpleDateFormat.format(totalCheckEndTime.getTime()))) {
                                mrpResultDTO.setLandmark(exactTimeTp);
                                mrpResultDTO.setOriginQuantity(listAnalysisTime.get(exactTimeTp) * mrpDetailNVL.getQuota());
                                if (isFirstDate) {
                                    isFirstDate = false;
                                    isPassFirstDate = true;
                                    MrpAnalysisUtil.setPrPoQuantity(mrpDetailNVL.getItemCode(), mrpResultDTO, prQuantity, poQuantity, grpoQuantity);
                                }
                                break;
                            } else {
                                mrpResultDTO.setLandmark(simpleDateFormat.format(totalCheckEndTime.getTime()));
                                mrpResultDTO.setOriginQuantity((double) 0);
                            }
                        }
                        MrpAnalysisUtil.setAnalysisDetail(mrpDetailNVL, mrpResultDTO, beforeDate, totalCheckEndTime, itemOnOrderQuantityMap, poQuantity, prQuantity, grpoQuantity, itemHoldQuantity, itemHoldQuantityByWhs, currentInventoryHM, simpleDateFormat);
                        if (isPassFirstDate) {
                            MrpAnalysisUtil.setHoldQuantity(mrpDetailNVL, mrpResultDTO, holdBeforeDate, totalCheckStartTime, itemHoldQuantity, simpleDateFormat);
                            MrpAnalysisUtil.setHoldQuantity(mrpDetailNVL, mrpResultDTO, holdBeforeDate, totalCheckStartTime, itemHoldQuantityByWhs, simpleDateFormat);
                            holdBeforeDate.setTime(totalCheckStartTime.getTime().getTime());
                        }

                        mrpResultDTOList.add(mrpResultDTO);
                        beforeDate.setTime(totalCheckEndTime.getTime().getTime());
                        break;
                    }
                    totalCheckStartTime.add(Calendar.DATE, 3);
                }
            } else if (analysisPeriod.equalsIgnoreCase("2 tuần")) {
//                if (totalCheckStartTime.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || simpleDateFormat.format(totalCheckStartTime.getTime()).equals(simpleDateFormat.format(totalCheckEndTime.getTime()))) {
//                    if ((prAnalysisHM.get(mrpDetailNVL.getItemCode()) != null && !prAnalysisHM.get(mrpDetailNVL.getItemCode()).isEmpty()) || (poAnalysisHM.get(mrpDetailNVL.getItemCode()) != null && !poAnalysisHM.get(mrpDetailNVL.getItemCode()).isEmpty())) {
//                        for (ItemQuantityWithDate checkDate : prAnalysisHM.get(mrpDetailNVL.getItemCode())) {
//                            log.info("CheckDate: {}", checkDate.getDate());
//                            log.info("calender: {}", simpleDateFormat.format(totalCheckStartTime.getTime()));
//
//                            if (simpleDateFormat.format(totalCheckStartTime.getTime()).equals(checkDate.getDate())) {
//                                mrpResultDTO.setRequiredQuantity(checkDate.getQuantity());
//                                break;
//                            } else {
//                                mrpResultDTO.setRequiredQuantity((double) 0);
//                            }
//                        }
//                        for (ItemQuantityWithDate checkDate : poAnalysisHM.get(mrpDetailNVL.getItemCode())) {
//                            if (simpleDateFormat.format(totalCheckStartTime.getTime()).equals(checkDate.getDate())) {
//                                mrpResultDTO.setExpectedQuantity(checkDate.getQuantity());
//                                mrpResultDTO.setFinalInventory(currentInventoryHM.getOrDefault(mrpDetailNVL.getItemCode(), 0.0) + checkDate.getQuantity());
//                                break;
//                            } else {
//                                mrpResultDTO.setExpectedQuantity((double) 0);
//                                mrpResultDTO.setFinalInventory(currentInventoryHM.get(mrpDetailNVL.getItemCode()));
//                            }
//                        }
//                    } else {
//                        mrpResultDTO.setRequiredQuantity((double) 0);
//                        mrpResultDTO.setExpectedQuantity((double) 0);
//                        mrpResultDTO.setFinalInventory(currentInventoryHM.get(mrpDetailNVL.getItemCode()));
//                    }

                    //Check số lượng yêu cầu của NVl theo ngày phân tích của mức cha
//                    for (String exactTime : listAnalysisTime.keySet()) {
//                        if (exactTime.equals(simpleDateFormat.format(totalCheckStartTime.getTime()))) {
//                            mrpResultDTO.setOriginQuantity(listAnalysisTime.get(exactTime) * mrpDetailNVL.getQuota());
//                            if (isFirstDate) {
//                                isFirstDate = false;
//                                MrpAnalysisUtil.setPrPoQuantity(mrpDetailNVL.getItemCode(), mrpResultDTO, prQuantity, poQuantity, grpoQuantity);
//                            }
//                            break;
//                        } else {
//                            mrpResultDTO.setOriginQuantity((double) 0);
//                        }
//                    }
//
//                    MrpAnalysisUtil.setAnalysisDetail(mrpDetailNVL, mrpResultDTO, beforeDate, totalCheckStartTime, itemOnOrderQuantityMap, poQuantity, prQuantity, grpoQuantity, itemHoldQuantity, itemHoldQuantityByWhs, currentInventoryHM, simpleDateFormat);
//
//                    mrpResultDTO.setLandmark(simpleDateFormat.format(totalCheckStartTime.getTime()));
//
//                    mrpResultDTOList.add(mrpResultDTO);
//
//                    beforeDate.setTime(totalCheckStartTime.getTime().getTime());
//
//                    calCheck.setTime(totalCheckStartTime.getTime());
//                    calCheck.add(Calendar.DATE, 14);
//                    if (calCheck.getTime().after(totalCheckEndTime.getTime())) {
//                        totalCheckStartTime.add(Calendar.DATE, 1);
//                    } else {
//                        totalCheckStartTime.add(Calendar.DATE, 14);
//                    }
//                } else {
//                    totalCheckStartTime.add(Calendar.DATE, 1);
//                }
//////////////////////////////////////////////////////////////////////////////////////////
                calCheck.setTime(totalCheckStartTime.getTime());
                if (calCheck.getTime().before(totalCheckEndTime.getTime())){
                    for (String exactTimeTp : listAnalysisTime.keySet()) {
                        if (exactTimeTp.equals(simpleDateFormat.format(totalCheckStartTime.getTime()))) {
                            mrpResultDTO.setLandmark(exactTimeTp);
                            mrpResultDTO.setOriginQuantity(listAnalysisTime.get(exactTimeTp) * mrpDetailNVL.getQuota());
                            if (isFirstDate) {
                                isFirstDate = false;
                                isPassFirstDate = true;
                                MrpAnalysisUtil.setPrPoQuantity(mrpDetailNVL.getItemCode(), mrpResultDTO, prQuantity, poQuantity, grpoQuantity);
                            }
                            break;
                        } else {
                            mrpResultDTO.setLandmark(simpleDateFormat.format(totalCheckStartTime.getTime()));
                            mrpResultDTO.setOriginQuantity((double) 0);
                        }
                    }

                    MrpAnalysisUtil.setAnalysisDetail(mrpDetailNVL, mrpResultDTO, beforeDate, totalCheckStartTime, itemOnOrderQuantityMap, poQuantity, prQuantity, grpoQuantity, itemHoldQuantity, itemHoldQuantityByWhs, currentInventoryHM, simpleDateFormat);
                    if (isPassFirstDate) {
                        MrpAnalysisUtil.setHoldQuantity(mrpDetailNVL, mrpResultDTO, holdBeforeDate, totalCheckStartTime, itemHoldQuantity, simpleDateFormat);
                        MrpAnalysisUtil.setHoldQuantity(mrpDetailNVL, mrpResultDTO, holdBeforeDate, totalCheckStartTime, itemHoldQuantityByWhs, simpleDateFormat);
                        holdBeforeDate.setTime(totalCheckStartTime.getTime().getTime());
                    }

                    mrpResultDTOList.add(mrpResultDTO);
                    beforeDate.setTime(totalCheckStartTime.getTime().getTime());

                    calCheck.add(Calendar.DATE, 14);
                    if (calCheck.getTime().after(totalCheckEndTime.getTime()) || simpleDateFormat.format(calCheck.getTime()).equals(simpleDateFormat.format(totalCheckEndTime.getTime()))){
                        mrpResultDTO = new MrpResultDTO();
                        for (String exactTimeTp : listAnalysisTime.keySet()) {
                            if (exactTimeTp.equals(simpleDateFormat.format(totalCheckEndTime.getTime()))) {
                                mrpResultDTO.setLandmark(exactTimeTp);
                                mrpResultDTO.setOriginQuantity(listAnalysisTime.get(exactTimeTp) * mrpDetailNVL.getQuota());
                                if (isFirstDate) {
                                    isFirstDate = false;
                                    MrpAnalysisUtil.setPrPoQuantity(mrpDetailNVL.getItemCode(), mrpResultDTO, prQuantity, poQuantity, grpoQuantity);
                                }
                                break;
                            } else {
                                mrpResultDTO.setLandmark(simpleDateFormat.format(totalCheckEndTime.getTime()));
                                mrpResultDTO.setOriginQuantity((double) 0);
                            }
                        }

                        MrpAnalysisUtil.setAnalysisDetail(mrpDetailNVL, mrpResultDTO, beforeDate, totalCheckEndTime, itemOnOrderQuantityMap, poQuantity, prQuantity, grpoQuantity, itemHoldQuantity, itemHoldQuantityByWhs, currentInventoryHM, simpleDateFormat);
                        if (isPassFirstDate) {
                            MrpAnalysisUtil.setHoldQuantity(mrpDetailNVL, mrpResultDTO, holdBeforeDate, totalCheckStartTime, itemHoldQuantity, simpleDateFormat);
                            MrpAnalysisUtil.setHoldQuantity(mrpDetailNVL, mrpResultDTO, holdBeforeDate, totalCheckStartTime, itemHoldQuantityByWhs, simpleDateFormat);
                            holdBeforeDate.setTime(totalCheckStartTime.getTime().getTime());
                        }

                        mrpResultDTOList.add(mrpResultDTO);
                        beforeDate.setTime(totalCheckEndTime.getTime().getTime());
                        break;
                    }
                    totalCheckStartTime.add(Calendar.DATE, 14);
                }
            }

            mrpResultDTO.setInStockQuantity(lastInStockQuantity);
            mrpResultDTO.setReadyQuantity(
                mrpResultDTO.getInStockQuantity() // Warehouse
//                    + mrpResultDTO.getExpectedQuantity() // PR
                    + mrpResultDTO.getSumPoAndDeliveringQuantity() // PR
                    - mrpResultDTO.getRequiredQuantity() // Hold quantity
            );

//            if (mrpResultDTO.getReadyQuantity() > 0) {
                if (mrpResultDTO.getReadyQuantity() > mrpResultDTO.getOriginQuantity()) {
                    lastInStockQuantity = mrpResultDTO.getReadyQuantity() - mrpResultDTO.getOriginQuantity();
                } else {
                    mrpResultDTO.setOrderQuantity(
                        mrpResultDTO.getOriginQuantity() - mrpResultDTO.getReadyQuantity()
                    );
                    lastInStockQuantity = 0.0;
                }
//            } else {
//                mrpResultDTO.setOrderQuantity(mrpResultDTO.getOriginQuantity());
//                lastInStockQuantity = 0.0;
//            }
        }
    }

    /**
     * @param detailDTOList
     * @param whsCode
     * @param mrpItemQuantityHM
     */
    private void getCurrentInventory(
        List<MrpDetailDTO> detailDTOList,
        HashMap<String, List<MrpResultDTO>> currentInvenHM,
        List<String> whsCode,
        HashMap<String, Double> mrpItemQuantityHM,
        List<String> analysisOptions
    ) {

        List<String> productCodeList = new ArrayList<>();
        List<CurrentInventory> currentInventory;
        List<MrpResultDTO> mrpResultDTOS = new ArrayList<>();

        MrpResultDTO mrpResultDTO;


        log.info("goi vao ham nay getCurrentInventory");
        for (MrpDetailDTO detailDTO : detailDTOList) {
            //Lấy input cho query hiện trạng tồn kho
            productCodeList.add(detailDTO.getItemCode());
            mrpItemQuantityHM.put(detailDTO.getItemCode(), detailDTO.getRequiredQuantity());
        }

        //Lấy số lượng hiện trạng tồn kho của list sản phẩm
        if (analysisOptions.contains("kho")) {
            currentInventory = oitwRepository.getAllCurrentInventoryByWhs(productCodeList, whsCode);
            log.info("currentInventory.size(): {}", currentInventory.size());
            log.info("productCodeList: {}", productCodeList);
            log.info("whsCodes: {}", whsCode);

            if (!currentInventory.isEmpty()) {
                for (MrpDetailDTO detailDTO : detailDTOList) {
                    mrpResultDTO = new MrpResultDTO();
                    mrpResultDTOS = new ArrayList<>();
                    for (CurrentInventory dto : currentInventory) {
                        if (detailDTO.getItemCode().equals(dto.getItemCode())) {
                            //Lấy số lượng hiện trạng tồn kho của sản phẩm
                            mrpResultDTO.setLandmark(Constants.MrpAnalysis.SAP_QUANTITY_STRING);
                            mrpResultDTO.setOriginQuantity(dto.getCurrentQuantity());
                            mrpResultDTOS.add(mrpResultDTO);

                            currentInvenHM.put(dto.getItemCode(), mrpResultDTOS);
                            log.info("dto.getItemCode()): {}", dto.getItemCode());
                            break;
                        }
                    }
                }
            }
        } else {
            for (MrpDetailDTO detailDTO : detailDTOList) {
                mrpResultDTO = new MrpResultDTO();
                mrpResultDTOS = new ArrayList<>();

                mrpResultDTO.setLandmark(Constants.MrpAnalysis.SAP_QUANTITY_STRING);
                mrpResultDTO.setOriginQuantity(0.0);
                mrpResultDTOS.add(mrpResultDTO);

                currentInvenHM.put(detailDTO.getItemCode(), mrpResultDTOS);
                log.info("dto.getItemCode()): {}", detailDTO.getItemCode());
            }
        }


    }

    /**
     * @param detailDTOS
     * @param parentPath
     * @param soCode
     */
    private void mergeDetailNVLList(List<MrpDetailDTO> detailDTOS, String parentPath, String soCode) {

        List<MrpDetailDTO> detailDTOsInMrpDB = mrpBomDetailRepository.getMrpDetailNVLNotInBom(soCode, parentPath);
        if (!CollectionUtils.isEmpty(detailDTOsInMrpDB)) {
            detailDTOS.addAll(detailDTOsInMrpDB);
        }
    }

    /**
     * @param startTime
     * @param mrpDetailDTO
     * @throws ParseException
     */
    public void getStartTimeOfMrpAnalytic(Calendar startTime, MrpDetailDTO mrpDetailDTO) throws ParseException {
        Calendar checkTime;
        // Skip first landmark
        for (MrpDetailDTO detailBTP : mrpDetailDTO.getChildren()) {
            List<MrpResultDTO> detailResult = detailBTP.getDetailResult();
//            for (int i = 0; i < detailResult.size(); i++) {
                MrpResultDTO date = detailResult.get(0);
                checkTime = Calendar.getInstance();
                checkTime.setTime(parseStringToCalendar(date.getLandmark()).getTime());
                log.info("checkTime: {}", simpleDateFormat.format(checkTime.getTime()));
                if (startTime.after(checkTime)) {
                    startTime.setTime(checkTime.getTime());
                }
                if (!detailBTP.getChildren().isEmpty() && detailBTP.getChildren() != null) {
                    log.info("level: {}", detailBTP.getLevel());
                    getStartTimeOfMrpAnalytic(startTime, detailBTP);
                }
//            }
        }
    }

    /**
     * @param dateFormat
     * @return
     * @throws ParseException
     */
    public Calendar parseStringToCalendar(String dateFormat) throws ParseException {
        log.info("dateFormat: {}", dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(simpleDateFormat.parse(dateFormat));
        log.info("parseStringToCalendar: {}", calendar.getTime());
        return calendar;
    }

    /**
     * @param input
     * @return
     * @throws ParseException
     */
    public List<String> choseExactAnalyticTime(CurrentScheduleTimeInput input) throws ParseException {
        Calendar currentTime = Calendar.getInstance();
        List<String> listExactTime = new ArrayList<>();

        currentTime.setTime(parseStringToCalendar(input.getCurrentTime()).getTime());
        if (input.getAnalysisPeriod().equalsIgnoreCase("1/2 tuần")) {
            for (int i = 0; i < 4; i++) {
                currentTime.add(Calendar.DATE, -(input.getMaterialPreparationTime() * 3));
                listExactTime.add(simpleDateFormat.format(currentTime.getTime()));
//                if (currentTime.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
//                    currentTime.add(Calendar.DATE, -(input.getMaterialPreparationTime() * 3 + (input.getMaterialPreparationTime() / 2)));
//
//                    listExactTime.add(simpleDateFormat.format(currentTime.getTime()));
//                } else if (currentTime.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
//                    if (input.getMaterialPreparationTime() % 2 == 0) {
//                        currentTime.add(Calendar.DATE, -(input.getMaterialPreparationTime() * 3 + (input.getMaterialPreparationTime() / 2)));
//                    } else {
//                        currentTime.add(Calendar.DATE, -(input.getMaterialPreparationTime() * 3 + (input.getMaterialPreparationTime() / 2) + 1));
//                    }
//                    listExactTime.add(simpleDateFormat.format(currentTime.getTime()));
//                }
                //lùi ngay mà không phai ngày chu nhat hay thu 4
//                else {
//                    int newPreparationTime = input.getMaterialPreparationTime() - 1;
//                    if (currentTime.get(Calendar.DAY_OF_WEEK) > Calendar.SUNDAY && currentTime.get(Calendar.DAY_OF_WEEK) < Calendar.WEDNESDAY) {
//                        int subtractNearestSunday = Calendar.SUNDAY - currentTime.get(Calendar.DAY_OF_WEEK);
//                        System.out.println("subtractNearestSunday: " + subtractNearestSunday);
//
//                        if (newPreparationTime % 2 == 0) {
//                            currentTime.add(Calendar.DATE, subtractNearestSunday - (newPreparationTime * 3 + (newPreparationTime / 2)));
//                        } else {
//                            currentTime.add(Calendar.DATE, -(input.getMaterialPreparationTime() * 3 + (input.getMaterialPreparationTime() / 2) + 1));
//                        }
//                        listExactTime.add(simpleDateFormat.format(currentTime.getTime()));
//
//                    } else {
//                        int subtractNearestWednesday = Calendar.WEDNESDAY - currentTime.get(Calendar.DAY_OF_WEEK);
//                        System.out.println("subtractNearestWednesday: " + subtractNearestWednesday);
//
//                        currentTime.add(Calendar.DATE, subtractNearestWednesday - (newPreparationTime * 3 + (newPreparationTime / 2)));
//
//                        listExactTime.add(simpleDateFormat.format(currentTime.getTime()));
//                    }
//                    currentTime.add(Calendar.DATE, -(input.getMaterialPreparationTime() * 3));
//                    listExactTime.add(simpleDateFormat.format(currentTime.getTime()));

//                }

            }
        } else if (input.getAnalysisPeriod().equalsIgnoreCase("tuần")) {
            for (int i = 0; i < 4; i++) {

                currentTime.add(Calendar.DATE, -(input.getMaterialPreparationTime() * 7));
                listExactTime.add(simpleDateFormat.format(currentTime.getTime()));

//                if (currentTime.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
//                    currentTime.add(Calendar.DATE, -(input.getMaterialPreparationTime() * 7));
//
//                    listExactTime.add(simpleDateFormat.format(currentTime.getTime()));
//
//                } else {
//                    int newPreparationTime = input.getMaterialPreparationTime() - 1;
//                    int subtractNearestSunday = Calendar.SUNDAY - currentTime.get(Calendar.DAY_OF_WEEK);
//
//                    currentTime.add(Calendar.DATE, subtractNearestSunday - newPreparationTime * 7);
//
//                    listExactTime.add(simpleDateFormat.format(currentTime.getTime()));
//                }
            }

        } else if (input.getAnalysisPeriod().equalsIgnoreCase("2 tuần")) {
            for (int i = 0; i < 4; i++) {
                currentTime.add(Calendar.DATE, -(input.getMaterialPreparationTime() * 14));

                listExactTime.add(simpleDateFormat.format(currentTime.getTime()));
//                if (currentTime.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
//                    currentTime.add(Calendar.DATE, -(input.getMaterialPreparationTime() * 14));
//
//                    listExactTime.add(simpleDateFormat.format(currentTime.getTime()));
//
//                } else {
//                    int subtractNearestSunday = Calendar.SUNDAY - currentTime.get(Calendar.DAY_OF_WEEK) - 7;
//                    int newPreparationTime = input.getMaterialPreparationTime() - 1;
//                    currentTime.add(Calendar.DATE, subtractNearestSunday - (newPreparationTime * 14));
//
//                    listExactTime.add(simpleDateFormat.format(currentTime.getTime()));
//                }
            }

        } else if (input.getAnalysisPeriod().equalsIgnoreCase("tháng")) {
            for (int i = 0; i < 3; i++) {

                for (int j = 0; j < input.getMaterialPreparationTime(); j++) {
                    currentTime.add(Calendar.DATE, -(currentTime.getActualMaximum(Calendar.DATE)));
                }

                listExactTime.add(simpleDateFormat.format(currentTime.getTime()));

//                if (currentTime.get(Calendar.DATE) == currentTime.getActualMaximum(Calendar.DATE)) {
//                    for (int j = 0; j < input.getMaterialPreparationTime(); j++) {
//                        currentTime.add(Calendar.DATE, -(currentTime.getActualMaximum(Calendar.DATE)));
//                    }
//
//                    listExactTime.add(simpleDateFormat.format(currentTime.getTime()));
//                } else {
//                    int newPreparationTime = input.getMaterialPreparationTime() - 1;
//                    int subtractNearestEndOfMonth = currentTime.getActualMinimum(Calendar.DATE) - currentTime.get(Calendar.DATE) - 1;
//
//                    currentTime.add(Calendar.DATE, subtractNearestEndOfMonth);
//                    for (int k = 0; k < newPreparationTime; k++) {
//                        currentTime.add(Calendar.DATE, -(currentTime.getActualMaximum(Calendar.DATE)));
//                    }
//
//                    listExactTime.add(simpleDateFormat.format(currentTime.getTime()));
//                }
            }
        }


        return listExactTime;
    }

    public List<MrpImportExcelModel> mrpAdvancedImportExcel(MultipartFile file) throws IOException {

        MrpImportExcelModel mrpImportExcelModel;
        List<MrpImportExcelModel> mrpImportExcelModels = new ArrayList<>();
        MrpDateQuantity mrpDateQuantity;
        List<MrpDateQuantity> mrpDateQuantityList;

        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

        //lấy sheet đầu ra ấy dữ liệu
        XSSFSheet sheet = workbook.getSheetAt(0);

        //Lấy ra header row
        Row headerRow = sheet.getRow(0);

        //Vòng lặp đầu lấy thông tin chi tiết của TP
        for (Row row : sheet) {
            //Skip header row
            if (row.getRowNum() == sheet.getFirstRowNum()) {
                log.info("Skip row 0");
                continue;
            }
            mrpDateQuantityList = new ArrayList<>();

            ExcelUtils.validateRow(row, 0, 3);

            //add giá tri
            mrpImportExcelModel = new MrpImportExcelModel();

            mrpImportExcelModel.setProductCode(row.getCell(0).getStringCellValue());
            mrpImportExcelModel.setProductName(row.getCell(1).getStringCellValue());
            mrpImportExcelModel.setBomVersion(row.getCell(2).getStringCellValue());
            mrpImportExcelModel.setRequiredQuantity(row.getCell(3).getNumericCellValue());

            for (int i = 4; i < row.getLastCellNum(); i++) {
                mrpDateQuantity = new MrpDateQuantity();
                mrpDateQuantity.setLandmark(sheet.getRow(0).getCell(i).getStringCellValue());
                mrpDateQuantity.setQuantity(row.getCell(i).getNumericCellValue());

                mrpDateQuantityList.add(mrpDateQuantity);
            }
            mrpImportExcelModel.setLandMarkDTOs(mrpDateQuantityList);

            mrpImportExcelModels.add(mrpImportExcelModel);
        }

        return mrpImportExcelModels;
    }
}


