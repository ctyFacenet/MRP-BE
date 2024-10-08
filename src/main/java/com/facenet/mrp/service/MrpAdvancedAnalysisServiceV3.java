package com.facenet.mrp.service;

import com.facenet.mrp.repository.MrpAnalysisCache;
import com.facenet.mrp.repository.mrp.*;
import com.facenet.mrp.repository.sap.OitwRepository;
import com.facenet.mrp.repository.sap.Por1Repository;
import com.facenet.mrp.repository.sap.Prq1Repository;
import com.facenet.mrp.service.dto.AdvancedMrpDTO;
import com.facenet.mrp.service.dto.mrp.CurrentInventory;
import com.facenet.mrp.service.dto.mrp.CurrentWarehouseInventory;
import com.facenet.mrp.service.dto.mrp.ItemQuantityWithDate;
import com.facenet.mrp.service.dto.mrp.MrpDetailDTO;
import com.facenet.mrp.service.dto.mrp.MrpResultDTO;
import com.facenet.mrp.service.dto.request.AnalyticsSearchRequest;
import com.facenet.mrp.service.utils.Constants;
import com.facenet.mrp.service.utils.MrpAnalysisUtil;
import com.facenet.mrp.service.utils.Utils;
import com.facenet.mrp.thread.CloneBomService;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MrpAdvancedAnalysisServiceV3 {
    private static final Logger log = LogManager.getLogger(MrpAdvancedAnalysisServiceV2.class);
    private final CloneBomService bomService;
    private final MrpAnalysisCache mrpAnalysisCache;
    private final ItemHoldRepository itemHoldRepository;
    private final OitwRepository oitwRepository;
    private final MrpBomDetailRepository mrpBomDetailRepository;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final PurchaseRecommendationPlanRepository purchaseRecommendationPlanRepository;
    private final Prq1Repository prq1Repository;
    private final Por1Repository por1Repository;
    private final MrpOrderQuantityRepository mrpOrderQuantityRepository;

    private final WarehouseEntityRepository warehouseEntityRepository;
    private final PurchaseRequestDetailEntityRepository purchaseRequestDetailEntityRepository;
    private final PurchaseOrderItemRepository purchaseOrderItemRepository;


    public MrpAdvancedAnalysisServiceV3(CloneBomService bomService, MrpAnalysisCache mrpAnalysisCache, ItemHoldRepository itemHoldRepository, OitwRepository oitwRepository, MrpBomDetailRepository mrpBomDetailRepository, PurchaseRecommendationPlanRepository purchaseRecommendationPlanRepository, Prq1Repository prq1Repository, Por1Repository por1Repository, MrpOrderQuantityRepository mrpOrderQuantityRepository, WarehouseEntityRepository warehouseEntityRepository,
                                        PurchaseRequestDetailEntityRepository purchaseRequestDetailEntityRepository,
                                        PurchaseOrderItemRepository purchaseOrderItemRepository) {
        this.bomService = bomService;
        this.mrpAnalysisCache = mrpAnalysisCache;
        this.itemHoldRepository = itemHoldRepository;
        this.oitwRepository = oitwRepository;
        this.mrpBomDetailRepository = mrpBomDetailRepository;
        this.purchaseRecommendationPlanRepository = purchaseRecommendationPlanRepository;
        this.prq1Repository = prq1Repository;
        this.por1Repository = por1Repository;
        this.mrpOrderQuantityRepository = mrpOrderQuantityRepository;
        this.warehouseEntityRepository = warehouseEntityRepository;
        this.purchaseRequestDetailEntityRepository = purchaseRequestDetailEntityRepository;
        this.purchaseOrderItemRepository = purchaseOrderItemRepository;
    }

    public AdvancedMrpDTO pagingCache(String ssId, int page, int pageSize, AnalyticsSearchRequest filter) {

        List<MrpDetailDTO> listDetailSearch = new ArrayList<>();

        //Paginated for the cache
        AdvancedMrpDTO resultOfAnalytics = mrpAnalysisCache.getMrpResult(ssId);

        AdvancedMrpDTO newPaging = new AdvancedMrpDTO(resultOfAnalytics);

        //Search
        if (filter.getProductCode() == null && filter.getProductName() == null) {
            newPaging.setResultData(paginate(resultOfAnalytics.getResultData(), page, pageSize));
        } else {
            for (MrpDetailDTO dto : newPaging.getResultData()) {
                if (dto != null) {
                    searching(listDetailSearch, filter, dto, dto);
                }
            }
            newPaging.setCountData(listDetailSearch.size());
            newPaging.setResultData(paginate(listDetailSearch, page, pageSize));
        }

        return newPaging;
    }

    public AdvancedMrpDTO pagingResult(AdvancedMrpDTO advancedMrpDTO, int page, int pageSize) throws ParseException {
        //Paginated for the response
        AdvancedMrpDTO resultOfAnalytics = advancedMrpAnalysis(advancedMrpDTO);

        AdvancedMrpDTO newPaging = new AdvancedMrpDTO(resultOfAnalytics);
        newPaging.setResultData(paginate(resultOfAnalytics.getResultData(), page, pageSize));

        return newPaging;
    }

    public void searching(List<MrpDetailDTO> listDetailSearch, AnalyticsSearchRequest filter, MrpDetailDTO firstDto, MrpDetailDTO childDto) {
        if (filter.getProductCode() != null) {
            if (firstDto.getItemCode().contains(filter.getProductCode())) {
                listDetailSearch.add(firstDto);
            } else {
                for (MrpDetailDTO child : childDto.getChildren()) {
                    if (child.getItemCode().contains(filter.getProductCode())) {
                        listDetailSearch.add(firstDto);
                        break;
                    }
                    if (child.getChildren() != null) {
                        searching(listDetailSearch, filter, firstDto, child);
                    }
                    if (listDetailSearch.contains(child)) {
                        break;
                    }
                }
            }
        } else if (filter.getProductName() != null) {
            if (firstDto.getItemName().contains(filter.getProductName())) {
                listDetailSearch.add(firstDto);
            } else {
                for (MrpDetailDTO child : childDto.getChildren()) {
                    if (child.getItemName().contains(filter.getProductName())) {
                        listDetailSearch.add(firstDto);
                        break;
                    }
                    if (child.getChildren() != null) {
                        searching(listDetailSearch, filter, firstDto, child);
                    }
                    if (listDetailSearch.contains(child)) {
                        break;
                    }
                }
            }
        } else if (filter.getProductName() != null && filter.getProductCode() != null) {
            if (firstDto.getItemName().contains(filter.getProductName()) && firstDto.getItemCode().contains(filter.getProductCode())) {

                listDetailSearch.add(firstDto);
            } else {
                for (MrpDetailDTO child : childDto.getChildren()) {
                    if (child.getItemName().contains(filter.getProductName()) && child.getItemCode().contains(filter.getProductCode())) {
                        listDetailSearch.add(firstDto);
                        break;
                    }
                    if (child.getChildren() != null) {
                        searching(listDetailSearch, filter, firstDto, child);
                    }
                    if (listDetailSearch.contains(child)) {
                        break;
                    }
                }
            }
        }

    }

    public AdvancedMrpDTO advancedMrpAnalysis(AdvancedMrpDTO advancedMrpDTO) throws ParseException {

        Map<String, List<List<MrpResultDTO>>> itemOnDuplicateMap = new HashMap<>();
        Map<String, Double> poQuantityMap = new HashMap<>();
        Map<String, Double> prQuantityMap = new HashMap<>();
        Map<String, List<ItemQuantityWithDate>> itemHoldQuantityByWhs = new HashMap<>();
        Map<String, List<ItemQuantityWithDate>> itemHoldQuantity = new HashMap<>();
        Map<String, List<ItemQuantityWithDate>> itemOnOrderQuantityMap = new HashMap<>();
        Map<String, Double> inStockQuantityMap = new HashMap<>();
        Map<String, List<CurrentWarehouseInventory>> inStockQuantityMapWithWhsCode = new HashMap<>();

        Calendar totalStartTime = Calendar.getInstance();
        Calendar totalEndTime = Calendar.getInstance();
        String parentPath;

        // xác định khoảng thời gian phân tích tổng ( startTime -> endTime )
        totalEndTime.setTime(advancedMrpDTO.getTimeEnd());

        long start = System.currentTimeMillis();
        TreeSet<Date> listTimeSet = new TreeSet<>();
        for (MrpDetailDTO detailDTO : advancedMrpDTO.getResultData()) {
            getStartTimeOfMrpAnalytic(listTimeSet, detailDTO, advancedMrpDTO.getAnalysisPeriod());
        }
        List<Date> listTime = new ArrayList<>(listTimeSet);
        totalStartTime.setTime(listTime.get(0));
        List<Date> allTime = generateDate(totalStartTime, totalEndTime, listTime, advancedMrpDTO.getAnalysisPeriod());
        log.info("Finish gen date for {} ms", (System.currentTimeMillis() - start));

        // Get MRP Bom
        Map<String, List<MrpDetailDTO>> additionalItemInBomMap = new HashMap<>();
        List<MrpDetailDTO> additionalItemInBom = mrpBomDetailRepository.getMrpDetailNotInBom(advancedMrpDTO.getSoCode());
        if (!CollectionUtils.isEmpty(additionalItemInBom))
            additionalItemInBomMap = additionalItemInBom.stream().collect(Collectors.groupingBy(MrpDetailDTO::getParentPath));

        boolean isFirstTime = advancedMrpDTO.getMrpSubCode().split("\\.")[1].equals("1");

        // Lấy số lượng item bị hold
//            itemHoldQuantityByWhs = itemHoldRepository.getAllHoldQuantityItemMapByWhs(
//                advancedMrpDTO.getWarehouseAnalysis(),
//                DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH),
//                totalEndTime.getTime(),
//                advancedMrpDTO.getAnalysisPeriod()
//            );
        if (advancedMrpDTO.getAnalysisType() == Constants.AnalysisType.NEW) {
            if (isFirstTime) {
                itemHoldQuantity = itemHoldRepository.getAllHoldQuantityItemMap(advancedMrpDTO.getWarehouseAnalysis(), DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH), totalEndTime.getTime());
            } else {
                itemHoldQuantity = itemHoldRepository.getAllHoldQuantityItemMapExceptMrpCode(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH), totalEndTime.getTime(), advancedMrpDTO.getMrpCode());
            }
        }

        // Lấy tổng số lượng PR ở MRP
        if (advancedMrpDTO.getAnalysisWhs().contains("PR/PO")) {
            itemOnOrderQuantityMap = purchaseRecommendationPlanRepository.sumAllQuantityOfItemsByDayMap(totalStartTime.getTime(), totalEndTime.getTime());
            // Từ kỳ 2 sẽ ko lấy             System.err.println("FIRST TIME");
//            prQuantityMap = prq1Repository.getOpenQuantityMap();
//            poQuantityMap = por1Repository.getOpenQuantityMap();

            prQuantityMap = purchaseRequestDetailEntityRepository.getOpenQuantityMap();
            poQuantityMap = purchaseOrderItemRepository.getOpenQuantityMap();
        }


        if (advancedMrpDTO.getAnalysisWhs().contains("kho")) {
            //Lấy số lượng hiện trạng tồn kho của list NVL
            List<CurrentInventory> inStockQuantity = oitwRepository.getAllCurrentInventoryByWhs(advancedMrpDTO.getWarehouseAnalysis());
            // Define the warehouseList to hold warehouse types (e.g., 2 for KHA, 3 for KVTCT)
            List<Integer> warehouseList = new ArrayList<>();

            // Check for "KVTCT" and "KHA" in input.getListAnalysisWhs()
            if (advancedMrpDTO.getWarehouseAnalysis().contains("KVTCT")) {
                warehouseList.add(Constants.Warehouse.Cty); // Add type 3 for "KVTCT"
            }
            if (advancedMrpDTO.getWarehouseAnalysis().contains("KHA")) {
                warehouseList.add(Constants.Warehouse.Hoa_an); // Add type 2 for "KHA"
            }
            if (advancedMrpDTO.getWarehouseAnalysis().contains("KTP")) {
                warehouseList.add(Constants.Warehouse.Tp);
            }

            // Proceed with fetching the inventory data if types are available
            if (!warehouseList.isEmpty()) {
                List<CurrentInventory> inStockQuantity2 = warehouseEntityRepository.getAllCurrentInventory(warehouseList);

                // Merge the two lists based on item code
                inStockQuantityMap = Stream.concat(inStockQuantity.stream(), inStockQuantity2.stream())
                    .collect(Collectors.toMap(
                        CurrentInventory::getItemCode,
                        CurrentInventory::getCurrentQuantity,
                        Double::sum // Merge quantities by summing them if they have the same item code
                    ));
            } else {
                inStockQuantityMap = inStockQuantity.stream()
                    .collect(Collectors.toMap(CurrentInventory::getItemCode, CurrentInventory::getCurrentQuantity));
            }

            List<CurrentWarehouseInventory> currentWarehouseInventories = oitwRepository.getAllCurrentInventoryWithWhs();
            List<CurrentWarehouseInventory> currentWarehouseInventories2 = warehouseEntityRepository.getAllCurrentInventoryWithWhs();
            List<CurrentWarehouseInventory> combinedInventory = new ArrayList<>(currentWarehouseInventories);
            combinedInventory.addAll(currentWarehouseInventories2);
            inStockQuantityMapWithWhsCode = combinedInventory.stream().collect(Collectors.groupingBy(CurrentInventory::getItemCode));
        }

        //Phân tích từng sản phẩm
        for (MrpDetailDTO detailDTO : advancedMrpDTO.getResultData()) {
            parentPath = "_" + detailDTO.getItemCode();
            long startTime = System.currentTimeMillis();
            analysisSingleProduct(detailDTO, itemOnDuplicateMap, additionalItemInBomMap, inStockQuantityMap, inStockQuantityMapWithWhsCode, prQuantityMap, poQuantityMap, itemHoldQuantity, itemHoldQuantityByWhs, itemOnOrderQuantityMap, allTime, totalStartTime, totalEndTime, advancedMrpDTO.getAnalysisPeriod(), advancedMrpDTO.getSoCode(), 1, parentPath);
//            System.err.println("Time to analysis " + detailDTO.getItemCode() + ": " + (System.currentTimeMillis() - startTime) + "ms");
            log.info("Time to analysis " + detailDTO.getItemCode() + ": " + (System.currentTimeMillis() - startTime) + "ms");
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
//                        if (mrpResult.get(i).getDeliveringQuantity() > 0.0
//                            || mrpResult.get(i).getPoQuantity() > 0.0
//                            || mrpResult.get(i).getOriginQuantity() > 0.0) {
                        if (mrpResult.get(i).getIsStart()) {
                            Date currentResultDate = simpleDateFormat.parse(mrpResult.get(i).getLandmark());
                            if (minDate.compareTo(currentResultDate) >= 0) {
                                minDate = currentResultDate;
                                deliveringQuantity = mrpResult.get(i).getDeliveringQuantity();
                                poQuantity = mrpResult.get(i).getPoQuantity();
                            }
                        }
                    }
                }
                boolean isPassMinDate = false;
                boolean isZero = false;
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

                    // Find last date
                    Date currentDate = simpleDateFormat.parse(mrpResultDTO.getLandmark());

                    if (currentDate.equals(minDate)) {
                        mrpResultDTO.setDeliveringQuantity(deliveringQuantity);
                        mrpResultDTO.setPoQuantity(poQuantity);
                        if (mrpResultDTO.getSumPoAndDeliveringQuantity() == 0.0) {
                            isZero = true;
                        }
                    } else if (currentDate.compareTo(minDate) > 0) {
                        if (!isZero) {
                            mrpResultDTO.setSumPoAndDeliveringQuantity(Math.max(result.get(i - 1).getReadyQuantity() - result.get(i - 1).getTotalOriginQuantity(), 0));
                        } else {
                            mrpResultDTO.setSumPoAndDeliveringQuantity(0.0);
                        }
                    }

                    Date lastDate = new Date();
                    switch (advancedMrpDTO.getAnalysisPeriod()) {
                        case "Ngày":
                            lastDate = DateUtils.addDays(currentDate, -1);
                            break;
                        case "Tuần":
                            lastDate = DateUtils.addDays(currentDate, -7);
                            break;
                        case "1/2 tuần":
                            lastDate = DateUtils.addDays(currentDate, -3);
                            break;
                        case "Quý":
                            lastDate = DateUtils.addDays(currentDate, -90);
                            break;
                        case "Tháng":
                            lastDate = DateUtils.addMonths(currentDate, -1);
                            break;
                    }
                    String lastDateStr = simpleDateFormat.format(lastDate);
                    MrpResultDTO lastResult = null;
                    // Find lastResult (date - 1)
                    for (int j = i; j > 1; j--) {
                        if (result.get(j).getLandmark().equals(lastDateStr)) {
                            lastResult = result.get(j);
                            break;
                        }
                    }

                    // min Date of Pr Po
//                    if (simpleDateFormat.parse(mrpResultDTO.getLandmark()).equals(minDate)) {
//                        mrpResultDTO.setDeliveringQuantity(1.0);
//                        mrpResultDTO.setPoQuantity(2.0);
//                    } else {
//                        mrpResultDTO.setDeliveringQuantity(1.0);
//                        mrpResultDTO.setPoQuantity(2.0);
//                    }

                    mrpResultDTO.setInStockQuantity(lastInStockQuantity);
                    // Tính toán readyQuantity
                    double readyQuantity = mrpResultDTO.getInStockQuantity() // Warehouse
//                    + mrpResultDTO.getExpectedQuantity() // PR
                        + mrpResultDTO.getSumPoAndDeliveringQuantity() // PR
                        - mrpResultDTO.getRequiredQuantity(); // Hold quantity

                    // Kiểm tra nếu readyQuantity âm thì set bằng 0
                    mrpResultDTO.setReadyQuantity(Math.max(readyQuantity, 0));

//                    if (mrpResultDTO.getReadyQuantity() > 0) {
//                    if (mrpResultDTO.getReadyQuantity() > sumOriginQuantity) {
//                        lastInStockQuantity = mrpResultDTO.getReadyQuantity() - sumOriginQuantity;
//                    } else {
//                        if (lastResult != null) {
//                            lastResult.setOrderQuantity(
//                                sumOriginQuantity - mrpResultDTO.getReadyQuantity()
//                            );
//                        }
//                        lastInStockQuantity = 0.0;
//                    }

                    lastInStockQuantity = Math.max(lastInStockQuantity - mrpResultDTO.getTotalOriginQuantity() - mrpResultDTO.getRequiredQuantity(), 0);

                    if (lastResult != null) {
                        lastResult.setOrderQuantity(Math.min(mrpResultDTO.getReadyQuantity() - mrpResultDTO.getTotalOriginQuantity(), 0));

                        if (result.get(i).getSumPoAndDeliveringQuantity() > 0) {
                            lastResult.setNeedQuantity(
                                // Always < 0, ignore > 0
                                Math.min(mrpResultDTO.getInStockQuantity() - mrpResultDTO.getTotalOriginQuantity(), 0));
                        }
                    }
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
                        mrpResultDetail.setNeedQuantity(0.0);
                    }
                }
            }
        }

        if (!StringUtils.isEmpty(advancedMrpDTO.getSessionId())) {
            mrpAnalysisCache.putMrpResult(advancedMrpDTO.getSessionId(), advancedMrpDTO);
        }

        return advancedMrpDTO;
    }

    public void analysisSingleProduct(MrpDetailDTO mrpDetailDTO, Map<String, List<List<MrpResultDTO>>> itemOnDuplicateMap, Map<String, List<MrpDetailDTO>> additionalItemInBomMap, Map<String, Double> inStockQuantityMap, Map<String, List<CurrentWarehouseInventory>> inStockQuantityMapWithWhsCode, Map<String, Double> prQuantityMap, Map<String, Double> poQuantityMap, Map<String, List<ItemQuantityWithDate>> itemHoldQuantity, Map<String, List<ItemQuantityWithDate>> itemHoldQuantityByWhs, Map<String, List<ItemQuantityWithDate>> itemOnOrderQuantityMap, List<Date> allTime, Calendar totalStartTime, Calendar totalEndTime, String analysisPeriod, String soCode, Integer countLevel, String parentPath) throws ParseException {
        String whsName = mrpDetailDTO.getWhsName();
        Calendar totalCheckStartTime = Calendar.getInstance();
        Calendar beforeDate = Calendar.getInstance();
        Calendar totalCheckEndTime = Calendar.getInstance();
        List<MrpResultDTO> detailResult = new ArrayList<>();
        String itemStartDate = mrpDetailDTO.getDetailResult().get(0).getLandmark();

        totalCheckStartTime.setTime(totalStartTime.getTime());
        beforeDate.setTime(totalStartTime.getTime());
        totalCheckEndTime.setTime(totalEndTime.getTime());

        // Set in stock quantiy
        MrpResultDTO resultTpDTO = new MrpResultDTO();
        resultTpDTO.setLandmark(Constants.MrpAnalysis.SAP_QUANTITY_STRING);
        resultTpDTO.setOriginQuantity(inStockQuantityMap.getOrDefault(mrpDetailDTO.getItemCode(), 0.0));
        detailResult.add(resultTpDTO);

        int analysisDateIndex = 0;
        for (Date date : allTime) {
            resultTpDTO = new MrpResultDTO();
            String landMark = simpleDateFormat.format(date);
            resultTpDTO.setLandmark(landMark);
            if (analysisDateIndex < mrpDetailDTO.getDetailResult().size() && landMark.equals(mrpDetailDTO.getDetailResult().get(analysisDateIndex).getLandmark())) {
                resultTpDTO.setOriginQuantity(mrpDetailDTO.getDetailResult().get(analysisDateIndex++).getOriginQuantity());
            }
            detailResult.add(resultTpDTO);
        }

        for (MrpDetailDTO mrpDetailBtpDTO : mrpDetailDTO.getChildren()) {
            String parentPathBtp = mrpDetailBtpDTO.getParentPath() + "_" + mrpDetailBtpDTO.getItemCode();
//            System.err.println("parent " + mrpDetailDTO.getItemCode() + " " + parentPathBtp);
//            System.err.println(mrpDetailBtpDTO);
            analysisSingleProduct(mrpDetailBtpDTO, itemOnDuplicateMap, additionalItemInBomMap, inStockQuantityMap, inStockQuantityMapWithWhsCode, prQuantityMap, poQuantityMap, itemHoldQuantity, itemHoldQuantityByWhs, itemOnOrderQuantityMap, allTime, totalStartTime, totalEndTime, analysisPeriod, soCode, countLevel + 1, parentPathBtp);
        }
        //TODO lấy bm ở đây
        //query lấy children mức NVL của sản phẩm
        List<MrpDetailDTO> bomItems = bomService.getBomTree().get(Utils.toItemKey(mrpDetailDTO.getItemCode(), mrpDetailDTO.getBomVersion()));
        List<MrpDetailDTO> itemList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(bomItems)) {
            for (MrpDetailDTO bomItem : bomItems) {
                if (bomItem.getGroupItemInt() != Constants.TP && bomItem.getGroupItemInt() != Constants.BTP) {
                    itemList.add(new MrpDetailDTO(bomItem));
                }
            }
        }

        log.info("children của sản phẩm: {}", itemList);
        //Merge children NVL trong DB MRP để thêm các NVL không theo bom
        log.info("parentPath: {}", parentPath);
        mrpDetailDTO.getChildren().addAll(itemList);
        MrpAnalysisUtil.addMrpBomToItem(mrpDetailDTO, additionalItemInBomMap.get(parentPath), MrpDetailDTO.NVL);

        mrpDetailDTO.setDetailResult(detailResult);

        long start = System.currentTimeMillis();
        //Phân tích ở level2 cho mức NVL của sản phẩm

        analysisLevelNvl(mrpDetailDTO, itemOnDuplicateMap, itemStartDate, inStockQuantityMap, inStockQuantityMapWithWhsCode, prQuantityMap, poQuantityMap, itemHoldQuantity, itemHoldQuantityByWhs, itemOnOrderQuantityMap, soCode, totalStartTime, totalEndTime, whsName, countLevel, parentPath, analysisPeriod);
//        System.err.println("Time to analysis NVL " + mrpDetailDTO.getItemCode() + ": " + (System.currentTimeMillis() - start));
        log.info("Time to analysis NVL " + mrpDetailDTO.getItemCode() + ": " + (System.currentTimeMillis() - start));
        //*******************************************************************
        //Set list result theo ngay va list children cho sản phẩm
//        mrpDetailDTO.getDetailResult().clear();
    }

    public void analysisLevelNvl(MrpDetailDTO parentItem, Map<String, List<List<MrpResultDTO>>> itemOnDuplicateMap,
                                 String itemStartDate, Map<String, Double> inStockQuantityMap,
                                 Map<String, List<CurrentWarehouseInventory>> inStockQuantityMapWithWhsCode,
                                 Map<String, Double> prQuantityMap, Map<String, Double> poQuantityMap,
                                 Map<String, List<ItemQuantityWithDate>> itemHoldQuantity,
                                 Map<String, List<ItemQuantityWithDate>> itemHoldQuantityByWhs,
                                 Map<String, List<ItemQuantityWithDate>> itemOnOrderQuantityMap, String soCode,
                                 Calendar totalStartTime, Calendar totalEndTime, String whsName, Integer level,
                                 String parentPath, String analysisPeriod) throws ParseException {
        MrpResultDTO mrpResultDTO;
        int countLevel = level + 1;
        double requiredQuantity;

        //Query tìm số lượng PR/PO theo ds mã sản phẩm trong khoảng thời gian phân tích

        //Vòng lặp để set các thông tin của từng NVL
        for (MrpDetailDTO childItem : parentItem.getChildren()) {
            if (childItem.getGroupItemInt() == Constants.BTP || childItem.getGroupItemInt() == Constants.TP) continue;

            mrpResultDTO = new MrpResultDTO();

            //Từ số lượng yêu cầu của mức cha => số lượng yêu cầu mức con
            requiredQuantity = parentItem.getRequiredQuantity() * childItem.getQuota();

            //Lấy số lượng hiện trạng cho tung NVL
            mrpResultDTO.setLandmark(Constants.MrpAnalysis.SAP_QUANTITY_STRING);
            mrpResultDTO.setOriginQuantity(inStockQuantityMap.getOrDefault(childItem.getItemCode(), 0.0));
            childItem.getDetailResult().add(mrpResultDTO);

            //lấy result phân tích theo thời gian ở mức NVl
            analysisNvlByDayWeekMonth(childItem, parentItem, itemStartDate, totalStartTime, totalEndTime, itemOnOrderQuantityMap, poQuantityMap, prQuantityMap, itemHoldQuantity, itemHoldQuantityByWhs, analysisPeriod);

            //******************************

            //Fill Dữ liệu cho từng NVL
            childItem.setIsHold(itemHoldQuantity.containsKey(childItem.getItemCode()) || itemHoldQuantityByWhs.containsKey(childItem.getItemCode()));
            childItem.setRequiredQuantity(requiredQuantity);
            // SL sẵn sàng
//            mrpDetailNVL.setInStockQuantity(mrpResultDTOList.get(0).getReadyQuantity());

            childItem.setWhsName(whsName);
            childItem.setLevel(String.valueOf(countLevel));
            childItem.setParentPath(parentPath);
//            mrpDetailNVL.setDetailResult(mrpResultDTOList);
            childItem.setCurrentWarehouseInventoryList(inStockQuantityMapWithWhsCode.get(childItem.getItemCode()));

            if (!itemOnDuplicateMap.containsKey(childItem.getItemCode()))
                itemOnDuplicateMap.put(childItem.getItemCode(), new ArrayList<>());
            itemOnDuplicateMap.get(childItem.getItemCode()).add(childItem.getDetailResult());
        }
    }


    public void analysisNvlByDayWeekMonth(MrpDetailDTO currentItem, MrpDetailDTO parentItem, String itemStartDate, Calendar totalStartTime, Calendar totalEndTime, Map<String, List<ItemQuantityWithDate>> itemOnOrderQuantityMap, Map<String, Double> poQuantity, Map<String, Double> prQuantity, Map<String, List<ItemQuantityWithDate>> itemHoldQuantity, Map<String, List<ItemQuantityWithDate>> itemHoldQuantityByWhs, String analysisPeriod) throws ParseException {
        Calendar totalCheckStartTime = Calendar.getInstance();
        Calendar totalCheckEndTime = Calendar.getInstance();

        totalCheckStartTime.setTime(totalStartTime.getTime());
        totalCheckEndTime.setTime(totalEndTime.getTime());

        MrpResultDTO parentIte;
        MrpResultDTO itemResult;
        double lastInStockQuantity = currentItem.getDetailResult().get(0).getOriginQuantity();
        boolean isPassFirstDate = false;
        boolean isStart = false;
        boolean isZero = false;
        Date beforeDate = new Date(Long.MIN_VALUE);
        Date holdBeforeDate = new Date(Long.MIN_VALUE);

        for (int i = 1; i < parentItem.getDetailResult().size(); i++) {
            parentIte = parentItem.getDetailResult().get(i);
            Date currentDate = simpleDateFormat.parse(parentIte.getLandmark());

            itemResult = new MrpResultDTO();
            itemResult.setLandmark(parentIte.getLandmark());
            itemResult.setOriginQuantity(parentIte.getOriginQuantity() * currentItem.getQuota());

            // Find last date
            Date lastDate = new Date();
            switch (analysisPeriod) {
                case "Ngày":
                    lastDate = DateUtils.addDays(currentDate, -1);
                    break;
                case "Tuần":
                    lastDate = DateUtils.addDays(currentDate, -7);
                    break;
                case "1/2 tuần":
                    lastDate = DateUtils.addDays(currentDate, -3);
                    break;
                case "Quý":
                    lastDate = DateUtils.addDays(currentDate, -90);
                    break;
                case "Tháng":
                    lastDate = DateUtils.addMonths(currentDate, -1);
                    break;
            }
            String lastDateStr = simpleDateFormat.format(lastDate);
            MrpResultDTO lastResult = null;
            // Find lastResult (date - 1)
            for (int j = i; j > 1; j--) {
                if (parentItem.getDetailResult().get(j).getLandmark().equals(lastDateStr)) {
                    lastResult = currentItem.getDetailResult().get(j);
                    break;
                }
            }

            MrpAnalysisUtil.setAnalysisDetailV2(currentItem, itemResult, beforeDate, totalCheckStartTime, itemOnOrderQuantityMap, simpleDateFormat);
            if (!isPassFirstDate && parentIte.getLandmark().equals(itemStartDate)) {
                isStart = true;
                itemResult.setIsStart(true);
                MrpAnalysisUtil.setPrPoQuantityV3(currentItem.getItemCode(), itemResult, prQuantity, poQuantity);
            }

            if (isStart) {
//                MrpAnalysisUtil.setHoldQuantityV3(
//                    currentItem, itemResult, holdBeforeDate, currentDate, itemHoldQuantityByWhs, simpleDateFormat);
                MrpAnalysisUtil.setHoldQuantityV3(currentItem, itemResult, holdBeforeDate, currentDate, itemHoldQuantity, simpleDateFormat);
                holdBeforeDate.setTime(currentDate.getTime());
            }

            // isPassFirstDate Kỳ đầu tiên
            if (isPassFirstDate) {
                if (isZero) {
                    itemResult.setSumPoAndDeliveringQuantity(0.0);
                } else {
                    itemResult.setSumPoAndDeliveringQuantity(Math.max(currentItem.getDetailResult().get(i - 1).getReadyQuantity() - currentItem.getDetailResult().get(i - 1).getOriginQuantity(), 0.0));
                }


            }

            if (parentIte.getLandmark().equals(itemStartDate)) {
                isPassFirstDate = true;
                if (itemResult.getSumPoAndDeliveringQuantity() == 0.0) {
                    isZero = true;
                }
            }


            itemResult.setInStockQuantity(lastInStockQuantity);
            // Tính toán readyQuantity
            double readyQuantity = itemResult.getInStockQuantity() // Warehouse
//                    + mrpResultDTO.getExpectedQuantity() // PR
                + itemResult.getSumPoAndDeliveringQuantity() // PR
                - itemResult.getRequiredQuantity(); // Hold quantity

            // Kiểm tra nếu readyQuantity âm thì set bằng 0
            itemResult.setReadyQuantity(Math.max(readyQuantity, 0));

//            if (mrpResultDTO.getReadyQuantity() > 0) {
//            if (itemResult.getReadyQuantity() > itemResult.getOriginQuantity()) {
//                lastInStockQuantity = itemResult.getReadyQuantity() - itemResult.getOriginQuantity();
//            } else {
//                if (lastResult != null) {
//                    lastResult.setOrderQuantity(
//                        itemResult.getOriginQuantity() - itemResult.getReadyQuantity()
//                    );
//                }
//                lastInStockQuantity = 0.0;
//            }
            lastInStockQuantity = Math.max(lastInStockQuantity - itemResult.getOriginQuantity() - itemResult.getRequiredQuantity(), 0);
            if (lastResult != null) {
                lastResult.setOrderQuantity(Math.min(itemResult.getReadyQuantity() - itemResult.getOriginQuantity(), 0.0));

                if (itemResult.getSumPoAndDeliveringQuantity() > 0) {
                    lastResult.setNeedQuantity(
                        // Always < 0, ignore > 0
                        Math.min(itemResult.getInStockQuantity() - itemResult.getTotalOriginQuantity(), 0));
                }
            }

            currentItem.getDetailResult().add(itemResult);
        }
    }


    public void getStartTimeOfMrpAnalytic(TreeSet<Date> listTime, MrpDetailDTO mrpDetailDTO, String analysisPeriod) throws ParseException {
        // Skip first landmark
        for (MrpResultDTO detail : mrpDetailDTO.getDetailResult()) {
            Date date = simpleDateFormat.parse(detail.getLandmark());
            listTime.add(date);
            switch (analysisPeriod) {
                case "Ngày":
                    listTime.add(DateUtils.addDays(date, -1));
                    break;
                case "Tuần":
                    listTime.add(DateUtils.addDays(date, -7));
                    break;
                case "1/2 tuần":
                    listTime.add(DateUtils.addDays(date, -3));
                    break;
                case "Quý":
                    listTime.add(DateUtils.addDays(date, -90));
                    break;
                case "Tháng":
                    listTime.add(DateUtils.addMonths(date, -1));
                    break;
            }
        }
        if (!CollectionUtils.isEmpty(mrpDetailDTO.getChildren())) {
            for (MrpDetailDTO child : mrpDetailDTO.getChildren()) {
                getStartTimeOfMrpAnalytic(listTime, child, analysisPeriod);
            }
        }
    }

    /**
     * Adpater for generate date
     *
     * @param startTime
     * @param endTime
     * @param listTime
     * @param analysisPeriod
     * @return
     */
    public List<Date> generateDate(Calendar startTime, Calendar endTime, List<Date> listTime, String analysisPeriod) {
        List<Date> allTime = new ArrayList<>();
        Calendar dateIte = Calendar.getInstance();
        dateIte.setTime(startTime.getTime());
        int currentListTimeIndex = generateDate(allTime, dateIte, endTime, listTime, analysisPeriod, 0);
        if (currentListTimeIndex != listTime.size()) {
            dateIte.setTime(listTime.get(currentListTimeIndex));
            generateDate(allTime, dateIte, endTime, listTime, analysisPeriod, currentListTimeIndex);
        }
        return allTime;
    }

    /**
     * Tạo list ngày
     *
     * @param allTime        List kết quả
     * @param dateIte        ngày đang xét
     * @param endTime        ngày kết thúc
     * @param listTime       ngày của tp/btp
     * @param analysisPeriod chu kì
     * @param listTimeIndex  index hiện tại cuả listTime
     * @return listTimeIndex để xác định ngày cuối
     */
    private int generateDate(List<Date> allTime, Calendar dateIte, Calendar endTime, List<Date> listTime, String analysisPeriod, int listTimeIndex) {
        while (dateIte.compareTo(endTime) <= 0) {
//            System.err.println("\nCurrent date " + simpleDateFormat.format(dateIte.getTime()));
            if (dateIte.getTime().after(listTime.get(listTimeIndex))) {
                dateIte.setTime(listTime.get(listTimeIndex));
//                System.err.println("Over reset to " + simpleDateFormat.format(listTime.get(listTimeIndex)));
            }

            if (dateIte.getTime().equals(listTime.get(listTimeIndex))) {
                listTimeIndex++;
//                System.err.println("Hit analysis time, increment index to " + listTimeIndex);
            }
            allTime.add(dateIte.getTime());

            switch (analysisPeriod) {
                case "Ngày":
                    dateIte.add(Calendar.DATE, 1);
                    break;
                case "Tuần":
                    dateIte.add(Calendar.DATE, 7);
                    break;
                case "1/2 tuần":
                    dateIte.add(Calendar.DATE, 3);
                    break;
                case "Quý":
                    dateIte.add(Calendar.DATE, 90);
                    break;
                case "Tháng":
                    dateIte.add(Calendar.MONTH, 1);
                    break;
            }
        }
        return listTimeIndex;
    }

    // Helper method to manually paginate the data
    private List<MrpDetailDTO> paginate(List<MrpDetailDTO> items, int page, int pageSize) {
        if (pageSize == 0) return items;
        int fromIndex = page * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, items.size());
        return items.subList(fromIndex, toIndex);
    }

    private String getKeyForOrderQuantityMap(String itemCode, String date) {
        return itemCode + "-" + date;
    }
}
