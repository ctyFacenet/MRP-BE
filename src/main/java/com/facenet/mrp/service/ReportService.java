package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.*;
import com.facenet.mrp.domain.sap.SapBranchGroupEntity;
import com.facenet.mrp.repository.mrp.*;
import com.facenet.mrp.repository.sap.BranchGroupRepository;
import com.facenet.mrp.repository.sap.CoittRepository;
import com.facenet.mrp.repository.sap.OcrdRepository;
import com.facenet.mrp.service.dto.*;
import com.facenet.mrp.service.dto.mrp.*;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.model.ItemFilter;
import com.facenet.mrp.service.model.PageFilterInput;
import com.facenet.mrp.service.model.ReportFilter;
import com.facenet.mrp.service.utils.Constants;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private static final Logger logger = LogManager.getLogger(ReportService.class);
    private final ProductOrderRepository productOrderRepository;
    private final ForecastOrderDetailRepository forecastOrderDetailRepository;
    private final ProductOrderDetailRepository productOrderDetailRepository;
    private final CoittRepository coittRepository;
    private final SapOnOrderDurationDetailRepository durationDetailRepository;
    private final PurchaseRecommendationPlanRepository purchaseRecommendationPlanRepository;
    private final PurchaseRecommendationDetailRepository purchaseRecommendationDetailRepository;
    private final MrpBomDetailRepository mrpBomDetailRepository;
    private final OcrdRepository ocrdRepository;
    private final MrpRequiredQuantityRepository mrpRequiredQuantityRepository;
    private final SapOnOrderSummaryRepository sapOnOrderSummaryRepository;
    private final EntityManager entityManager;
    private final SapOnOrderDurationDetailRepository sapOnOrderDurationDetailRepository;
    private final PurchaseRecommendationPlanRepository planRepository;
    private final BranchGroupRepository branchGroupRepository;
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ReportService(ProductOrderRepository productOrderRepository,
                         PurchaseRecommendationRepository purchaseRecommendationRepository,
                         ForecastOrderDetailRepository forecastOrderDetailRepository,
                         ProductOrderDetailRepository productOrderDetailRepository,
                         CoittRepository coittRepository,
                         SapOnOrderDurationDetailRepository durationDetailRepository,
                         PurchaseRecommendationPlanRepository purchaseRecommendationPlanRepository,
                         PurchaseRecommendationDetailRepository purchaseRecommendationDetailRepository,
                         MrpBomDetailRepository mrpBomDetailRepository, OcrdRepository ocrdRepository,
                         MrpRequiredQuantityRepository mrpRequiredQuantityRepository,
                         SapOnOrderSummaryRepository sapOnOrderSummaryRepository,
                         PurchaseRecommendationPlanRepository planRepository,
                         EntityManager entityManager,
                         SapOnOrderDurationDetailRepository sapOnOrderDurationDetailRepository,
                         BranchGroupRepository branchGroupRepository) {
        this.productOrderRepository = productOrderRepository;
        this.forecastOrderDetailRepository = forecastOrderDetailRepository;
        this.productOrderDetailRepository = productOrderDetailRepository;
        this.coittRepository = coittRepository;
        this.durationDetailRepository = durationDetailRepository;
        this.purchaseRecommendationPlanRepository = purchaseRecommendationPlanRepository;
        this.purchaseRecommendationDetailRepository = purchaseRecommendationDetailRepository;
        this.mrpBomDetailRepository = mrpBomDetailRepository;
        this.ocrdRepository = ocrdRepository;
        this.mrpRequiredQuantityRepository = mrpRequiredQuantityRepository;
        this.sapOnOrderSummaryRepository = sapOnOrderSummaryRepository;
        this.sapOnOrderDurationDetailRepository = sapOnOrderDurationDetailRepository;
        this.planRepository = planRepository;
        this.entityManager = entityManager;
        this.branchGroupRepository = branchGroupRepository;
    }

    public List<ReportDTO> getReport(ReportFilter filter){

        Set<ProductDetail> materialSet = new HashSet<>();
        Set<ProductDetail> productSet = new HashSet<>();
        List<ReportDTO> reportDTOS = new ArrayList<>();
        List<String> fcCodeList = new ArrayList<>();
        List<String> soCodeList = new ArrayList<>();
        List<ProductDetail> countsByFc = new ArrayList<>();
        List<ProductDetail> countsBySo = new ArrayList<>();

        HashMap<String, Set<ProductDetail>> totalProductCounts = new HashMap<>();
        HashMap<String, Set<ProductDetail>> totalMaterialCounts = new HashMap<>();
        HashMap<String, Long> suppliesCountHM;
        HashMap<String, Long> prCountHM;

        ReportDTO dto;

        //Vào csdl MRP  lấy thông tin đơn hàng
        List<ProductOrder> productOrders = productOrderRepository.getReportBySoCode(filter);

        if (productOrders != null && !productOrders.isEmpty()){
            for (ProductOrder item : productOrders){

                if (item.getProductOrderCode().contains("RAL-FC-")){
                    fcCodeList.add(item.getProductOrderCode());
                }else {
                    soCodeList.add(item.getProductOrderCode());
                }

                //**************************************
                dto = new ReportDTO();
                dto.setSoCode(item.getProductOrderCode());
                dto.setCustomerCode(item.getCustomerId());
                dto.setCustomerName(item.getCustomerName());
                dto.setSaleCode(item.getPartCode());
                dto.setOrderDate(item.getOrderDate());
                dto.setDeliveryDate(item.getDeliverDate());
                dto.setStatus(item.getStatus());

                reportDTOS.add(dto);
            }
        }else {
            throw new CustomException("record.notfound");
        }
        logger.info("reportDTOS: {}", reportDTOS.size());
        logger.info("fcCodeList: {}", fcCodeList.size());
        logger.info("soCodeList: {}", soCodeList.size());

        //Lấy count số lượng TP/BTP trong danh sách đơn hàng
        if (fcCodeList != null && !fcCodeList.isEmpty()){
            countsByFc = forecastOrderDetailRepository.countFcBySoCode(fcCodeList);
            logger.info("countsByFc: {}", countsByFc.size());
            if (countsByFc != null && !countsByFc.isEmpty()){
                for (String soCode : fcCodeList){
                    productSet = new LinkedHashSet<>();
                    materialSet = new LinkedHashSet<>();
                    for (ProductDetail item : countsByFc){
                        if (item.getSoCode().equals(soCode) && item.getItemGroupCode().equals("BTP")){
                            productSet.add(item);
                        }else if (item.getSoCode().equals(soCode) && item.getItemGroupCode().equals("NVL")){
                            materialSet.add(item);
                        }
                    }
                    totalMaterialCounts.put(soCode, materialSet);
                    totalProductCounts.put(soCode, productSet);
                }
            }
            else {
                throw new CustomException("record.notfound");
            }
        }
        if (soCodeList != null && !soCodeList.isEmpty()){
            countsBySo = productOrderDetailRepository.countSoBySoCode(soCodeList);
            logger.info("countsBySo: {}", countsBySo.size());
            if (countsBySo != null && !countsBySo.isEmpty()){
                for (String soCode : soCodeList){
                    productSet = new LinkedHashSet<>();
                    for (ProductDetail item : countsBySo){
                        if (item.getSoCode().equals(soCode)){
                            productSet.add(item);
                        }
                    }
                    totalProductCounts.put(soCode, productSet);
                }
            }
            else {
                throw new CustomException("record.notfound");
            }
        }

        //Lấy Count NVL thé So và FC
        suppliesCountHM = getSuppliesCount(soCodeList, fcCodeList);
        prCountHM = getPrCount(soCodeList, fcCodeList);

        //******************************************************

        for (ReportDTO report : reportDTOS){
            System.err.println(report.getSoCode());

            report.setTotalProductCount(totalProductCounts.get(report.getSoCode()).size());
            report.setTotalSuppliesCount(suppliesCountHM.get(report.getSoCode()));
            report.setTotalPrCount(prCountHM.get(report.getSoCode()));
            report.setRemainingQuantity((int) (suppliesCountHM.get(report.getSoCode()) - prCountHM.get(report.getSoCode())));
            report.setCompletionRate( suppliesCountHM.get(report.getSoCode()) == 0? 0 : prCountHM.get(report.getSoCode())/suppliesCountHM.get(report.getSoCode()) * 100);

        }

        return reportDTOS;
    }

    public HashMap<String, Long> getSuppliesCount(List<String> soCodeList, List<String> fcCodeList){

        List<CountPo> countBySoCode = productOrderDetailRepository.getCountMaterialByPo(soCodeList);
        List<CountPo> countByFcCode = forecastOrderDetailRepository.getCountMaterialByPo(fcCodeList);

        HashMap<String, Long> suppliesCountHM = new HashMap<>();

        for (String poCode : soCodeList){
            if (countBySoCode != null && !countBySoCode.isEmpty()){
                for (CountPo item : countBySoCode){
                    if (item.getMrpPoId().equals(poCode)){
                        suppliesCountHM.put(item.getMrpPoId(), (item.getCountMaterial() == null)? 0 : item.getCountMaterial());
                        break;
                    }else {
                        suppliesCountHM.put(poCode, Long.valueOf(0));
                    }
                }
            }else {
                suppliesCountHM.put(poCode, Long.valueOf(0));
            }
        }

        for (String poCode : fcCodeList){
            if (countByFcCode != null && !countByFcCode.isEmpty()){
                for (CountPo item : countByFcCode){
                    if (item.getMrpPoId().equals(poCode)){
                        suppliesCountHM.put(item.getMrpPoId(), (item.getCountMaterial() == null)? 0 : item.getCountMaterial());
                        break;
                    }else {
                        suppliesCountHM.put(poCode, Long.valueOf(0));
                    }
                }
            }else {
                suppliesCountHM.put(poCode, Long.valueOf(0));
            }
        }


        return suppliesCountHM;
    }

    public HashMap<String , Long> getPrCount(List<String> soCodeList, List<String> fcCodeList){
        HashMap<String, Long> prCountHM = new HashMap<>();

        List<CountPo> countPrList;

        soCodeList.addAll(fcCodeList);

        countPrList = purchaseRecommendationDetailRepository.countPrBySoCode(soCodeList);

        for (String poCode : soCodeList){
            if (countPrList != null && !countPrList.isEmpty()){
                for (CountPo item : countPrList){
                    if (item.getMrpPoId().equals(poCode)){
                        prCountHM.put(item.getMrpPoId(), (item.getCountMaterial() == null)? 0 : item.getCountMaterial());
                        break;
                    }else {
                        prCountHM.put(poCode, Long.valueOf(0));
                    }
                }
            }else {
                prCountHM.put(poCode, Long.valueOf(0));
            }
        }
        return prCountHM;
    }

    public List<ReportDetailDTO> getDetailReport(Integer reportMode, String soCode, DetailReportDTO reportDetailDTO){

        List<ReportDetailDTO> reportDetailDTOS = new ArrayList<>();
        List<PlanPrForSo> planPrForSoList;
        List<PoGrpoDetailReport> poGrpoReports;
        List<VendorCodeForDetailReport> vendorCodeForDetailReports;
        Set<String> vendorCodeSet = new LinkedHashSet<>();
        List<VendorCodeForDetailReport> vendorInfoList;

        //Lấy soCode query ra thông tin các vật tư
        if (soCode.contains("RAL-FC")){
            //Todo: query voi don hang FC

        }else {
            reportDetailDTOS = mrpRequiredQuantityRepository.getAllMaterialRequiredQuantityV2(
                soCode,
                '%' + reportDetailDTO.getMrpCode().trim() + '%',
                '%' + reportDetailDTO.getItemCode().trim() + '%',
                '%' + reportDetailDTO.getItemName().trim() + '%'
            );
            System.err.println("reportDetailDTOS: " + reportDetailDTOS.size());
            System.err.println("reportDetailDTOS: " + reportDetailDTOS.isEmpty());

            //Check null vật tư
            if (reportDetailDTOS.isEmpty()){
                throw new CustomException("analysis.item.had.not.exist");
            }
        }

        //lấy mã NCC được chọn của các vật tư được khuyến nghị
        vendorCodeForDetailReports = purchaseRecommendationDetailRepository.getVendorBySoCodeForDetailReport(
            soCode,
            '%' + reportDetailDTO.getVendorCode() + '%'
        );

        if (vendorCodeForDetailReports != null && !vendorCodeForDetailReports.isEmpty()){
            for (VendorCodeForDetailReport item : vendorCodeForDetailReports){
                vendorCodeSet.add(item.getVendorCode());
            }
        }
        //Lấy tên NCC từ mã NCC vừa lấy được và ghep vào cùng một danh sách
        if (!vendorCodeSet.isEmpty()){
            vendorInfoList = ocrdRepository.getVendorList(vendorCodeSet);
            vendorInfoList = vendorInfoList.stream().filter(v-> v.getVendorName().contains(reportDetailDTO.getVendorName())).toList();
            Map<String, VendorCodeForDetailReport> hashMap = vendorInfoList.stream().collect(Collectors.toMap(VendorCodeForDetailReport::getVendorCode, v->v));

            if (!vendorInfoList.isEmpty()){
                for (VendorCodeForDetailReport vendorCodeForDetailReport : vendorCodeForDetailReports){
                    if (hashMap.containsKey(vendorCodeForDetailReport.getVendorCode())) {
                        vendorCodeForDetailReport.setVendorName(hashMap.get(vendorCodeForDetailReport.getVendorCode()).getVendorName());
                    }
                }
                vendorCodeForDetailReports = vendorCodeForDetailReports.stream().filter(v->v.getVendorName()!= null).toList();
            }
        }


        //1. lấy số lượng dự kiến về PR đã đc phê duyệt trên MRP
        planPrForSoList = purchaseRecommendationPlanRepository.getTotalExpectedQuantityForDetailReport(soCode);
        if (planPrForSoList != null && !planPrForSoList.isEmpty()){
            for (ReportDetailDTO report : reportDetailDTOS){
                for (PlanPrForSo prForSo : planPrForSoList){
                    if (report.getMrpCode().equals(prForSo.getMrpSubCode())  && report.getItemCode().equals(prForSo.getItemCode())){
                        report.setTotalExpectedQuantity(prForSo.getTotalExpectedQuantity());
                        break;
                    }else {
                        report.setTotalExpectedQuantity(0.0);
                    }
                }

                //2. Ghép thông tin NCC
                for (VendorCodeForDetailReport vendorCodeItem : vendorCodeForDetailReports){
                    if (report.getItemCode().equals(vendorCodeItem.getItemCode()) && report.getMrpCode().equals(vendorCodeItem.getMrpSubCode())){
                        report.setVendorCode(vendorCodeItem.getVendorCode());
                        report.setVendorName(vendorCodeItem.getVendorName());
                    }
                }
            }
        }else {
            for (ReportDetailDTO report : reportDetailDTOS){
                report.setTotalExpectedQuantity(0.0);
            }
        }

        //Lây số lượng da ve GRPO va so luong dat hang po
        poGrpoReports = sapOnOrderSummaryRepository.getPoGrpoDetailReport(soCode);

        if (poGrpoReports != null && !poGrpoReports.isEmpty()){
            for (ReportDetailDTO report : reportDetailDTOS){
                for (PoGrpoDetailReport poGrpoReport : poGrpoReports){
                    if (report.getMrpCode().equals(poGrpoReport.getMrpSubCode()) && report.getItemCode().equals(poGrpoReport.getItemCode()) && poGrpoReport.getTypePrGrpo().equalsIgnoreCase("grpo")){
                        report.setTotalGRPO(poGrpoReport.getQuantity());
                        break;
                    }else {
                        report.setTotalGRPO(0.0);
                    }
                }
            }
        }else {
            for (ReportDetailDTO report : reportDetailDTOS){
                report.setTotalGRPO(0.0);
            }
        }

        //Lấy tổng số lượng dã lên PO
        if (poGrpoReports != null && !poGrpoReports.isEmpty()){
            for (ReportDetailDTO report : reportDetailDTOS){
                for (PoGrpoDetailReport poGrpoReport : poGrpoReports){
                    if (report.getMrpCode().equals(poGrpoReport.getMrpSubCode()) && report.getItemCode().equals(poGrpoReport.getItemCode()) && poGrpoReport.getTypePrGrpo().equalsIgnoreCase("po")){
                        report.setTotalPO(poGrpoReport.getQuantity());
                        break;
                    }else {
                        report.setTotalPO(0.0);
                    }
                }
            }
        }else {
            for (ReportDetailDTO report : reportDetailDTOS){
                report.setTotalPO(0.0);
            }
        }

        // Tỉ lệ hoàn thành = GRPO / slyc
        // Tiến độ mua hàng = GRPO / PR phê duyệt
        for (ReportDetailDTO report : reportDetailDTOS){
            report.setCompletionRate(report.getTotalGRPO() == 0.0 ? 0.0 : (report.getTotalGRPO() / report.getTotalRequiredQuantity()));
            report.setPurchasingProgress(report.getTotalGRPO() == 0.0 ? 0.0 : (report.getTotalGRPO() / report.getTotalExpectedQuantity()));
        }

        //Todo: lấy thông tin tổng họp mua hàng - chia theo mode báo cáo
        List<OnOrderDurationReport> durationReportList = durationDetailRepository.getAllDurationBySoCode(soCode);
        Calendar totalStartDate = Calendar.getInstance();
        Calendar totalEndDate = Calendar.getInstance();

        if (durationReportList != null && !durationReportList.isEmpty()){
            totalStartDate.setTime(durationReportList.get(0).getDueDate());
            totalEndDate.setTime(durationReportList.get(durationReportList.size() - 1).getDueDate());
            for (ReportDetailDTO detailDTO : reportDetailDTOS) {
                if (reportMode == Constants.DAY_MODE){
                    getDetailReportByDay(detailDTO, durationReportList, totalStartDate, totalEndDate);
                }else if (reportMode == Constants.WEEK_MODE){
                    getDetailReportByWeek(detailDTO, durationReportList, totalStartDate, totalEndDate);
                }else if (reportMode == Constants.MONTH_MODE){
                    getDetailReportByMonth(detailDTO, durationReportList, totalStartDate, totalEndDate);
                }else if (reportMode == Constants.QUARTER_MODE){
                    getDetailReportByQuarter(detailDTO, durationReportList, totalStartDate, totalEndDate);
                }else {
                    throw new CustomException("wrong.report.mode");
                }
            }
        }


        return reportDetailDTOS;
    }

    public void getDetailReportByDay(ReportDetailDTO reportDetailDTO,
                                     List<OnOrderDurationReport> durationReportList,
                                     Calendar startTime,
                                     Calendar endTime) {
        AnalysisDetailReportByTimeModel detailReportByTimeModel;
        List<AnalysisDetailReportByTimeModel> AnalysisDetailReportByTimeModelList = new ArrayList<>();

        while (startTime.before(endTime) || simpleDateFormat.format(startTime.getTime()).equals(simpleDateFormat.format(endTime.getTime()))){
            detailReportByTimeModel = new AnalysisDetailReportByTimeModel();
            for (OnOrderDurationReport item : durationReportList){
                if (reportDetailDTO.getItemCode().equals(item.getProductCode())
                    && reportDetailDTO.getMrpCode().equals(item.getMrpCode())
                    && simpleDateFormat.format(item.getDueDate()).equals(simpleDateFormat.format(startTime.getTime()))
                ){
                    detailReportByTimeModel.setQuantity(item.getQuantity());
                    detailReportByTimeModel.setDueDate(simpleDateFormat.format(startTime.getTime()));
                    break;
                }else {
                    detailReportByTimeModel.setQuantity(0.0);
                    detailReportByTimeModel.setDueDate(simpleDateFormat.format(startTime.getTime()));
                }
            }
            AnalysisDetailReportByTimeModelList.add(detailReportByTimeModel);
            startTime.add(Calendar.DATE, 1);
        }
        reportDetailDTO.setResultMrp(AnalysisDetailReportByTimeModelList);
    }
    public void getDetailReportByWeek(ReportDetailDTO reportDetailDTO,
                                      List<OnOrderDurationReport> durationReportList,
                                      Calendar startTime,
                                      Calendar endTime ){
        AnalysisDetailReportByTimeModel detailReportByTimeModel;
        List<AnalysisDetailReportByTimeModel> AnalysisDetailReportByTimeModelList = new ArrayList<>();

        int countWeek = 0;
        double quantity;
        int index = 0;

        if (startTime.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
            startTime.add(Calendar.DATE, 7 - startTime.get(Calendar.DAY_OF_WEEK) + 1);
        }
        if (endTime.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
            endTime.add(Calendar.DATE, 7 - endTime.get(Calendar.DAY_OF_WEEK) + 1);
        }

        while (startTime.before(endTime) || simpleDateFormat.format(startTime.getTime()).equals(simpleDateFormat.format(endTime.getTime()))){
            quantity = 0.0;
            detailReportByTimeModel = new AnalysisDetailReportByTimeModel();
//            System.err.println("i: " + i);
//            System.err.println("startTime.getTime(): "+simpleDateFormat.format(startTime.getTime()));
            for (; index < durationReportList.size(); index++) {
//                System.err.println("item.getDueDate(): " + simpleDateFormat.format(durationReportsById.get(i).getDueDate()));
//                System.err.println("item.getQuantity(): " + durationReportsById.get(i).getQuantity());
                if (durationReportList.get(index).getDueDate().before(startTime.getTime()) || simpleDateFormat.format(durationReportList.get(index).getDueDate()).equals(simpleDateFormat.format(startTime.getTime()))) {
//                    System.err.println("item.getDueDate()1111: " + simpleDateFormat.format(durationReportsById.get(i).getDueDate()));
//                    System.err.println("item.getQuantity()1111: " + durationReportsById.get(i).getQuantity());
                    quantity += durationReportList.get(index).getQuantity();
                }else break;
            }

            countWeek += 1;
            detailReportByTimeModel.setDueDate("Tuần " + countWeek);
            detailReportByTimeModel.setQuantity(quantity);
            AnalysisDetailReportByTimeModelList.add(detailReportByTimeModel);

            startTime.add(Calendar.DATE, 1);
            startTime.add(Calendar.DATE, 7 - startTime.get(Calendar.DAY_OF_WEEK) + 1);

        }

        reportDetailDTO.setResultMrp(AnalysisDetailReportByTimeModelList);

    }

    public void getDetailReportByMonth(ReportDetailDTO reportDetailDTO,
                                       List<OnOrderDurationReport> durationReportList,
                                       Calendar startTime,
                                       Calendar endTime ){

        int countMonth = 0;
        Calendar calCheck = Calendar.getInstance();
        AnalysisDetailReportByTimeModel detailReportByTimeModel;
        double quantity;
        int index = 0;

        List<AnalysisDetailReportByTimeModel> AnalysisDetailReportByTimeModelList = new ArrayList<>();

        endTime.set(Calendar.DATE, endTime.getActualMaximum(Calendar.DAY_OF_MONTH));
        startTime.set(Calendar.DATE, startTime.getActualMaximum(Calendar.DAY_OF_MONTH));
        calCheck.setTime(durationReportList.get(0).getDueDate());

        while (startTime.before(endTime) || simpleDateFormat.format(startTime.getTime()).equals(simpleDateFormat.format(endTime.getTime()))) {
            quantity = 0.0;
            detailReportByTimeModel = new AnalysisDetailReportByTimeModel();

            for (; index < durationReportList.size(); index++) {
//                System.err.println("item.getDueDate(): " + simpleDateFormat.format(durationReportsById.get(i).getDueDate()));
//                System.err.println("item.getQuantity(): " + durationReportsById.get(i).getQuantity());
                if (durationReportList.get(index).getDueDate().before(startTime.getTime()) || simpleDateFormat.format(durationReportList.get(index).getDueDate()).equals(simpleDateFormat.format(startTime.getTime()))) {
//                    System.err.println("item.getDueDate()1111: " + simpleDateFormat.format(durationReportsById.get(i).getDueDate()));
//                    System.err.println("item.getQuantity()1111: " + durationReportsById.get(i).getQuantity());
                    quantity += durationReportList.get(index).getQuantity();
                }else break;
            }

            countMonth += 1;
            detailReportByTimeModel.setDueDate("Tháng " + countMonth);
            detailReportByTimeModel.setQuantity(quantity);
            AnalysisDetailReportByTimeModelList.add(detailReportByTimeModel);

            startTime.add(Calendar.DATE, 1);
            startTime.set(Calendar.DATE, startTime.getActualMaximum(Calendar.DAY_OF_MONTH));
        }
        reportDetailDTO.setResultMrp(AnalysisDetailReportByTimeModelList);

        }

    public void getDetailReportByQuarter(ReportDetailDTO reportDetailDTO,
                                         List<OnOrderDurationReport> durationReportList,
                                         Calendar startTime,
                                         Calendar endTime ){

        int countQuarter = 0;
        Calendar calCheck = Calendar.getInstance();
        int index = 0;
        AnalysisDetailReportByTimeModel detailReportByTimeModel;
        double quantity;

        List<AnalysisDetailReportByTimeModel> AnalysisDetailReportByTimeModelList = new ArrayList<>();

        startTime.setTime(AnalysisDetailReportService.getLastDayOfQuarter(startTime.getTime()));
        endTime.setTime(AnalysisDetailReportService.getLastDayOfQuarter(endTime.getTime()));

        calCheck.setTime(durationReportList.get(0).getDueDate());

        while (startTime.before(endTime) || simpleDateFormat.format(startTime.getTime()).equals(simpleDateFormat.format(endTime.getTime()))){

            quantity = 0.0;
            detailReportByTimeModel = new AnalysisDetailReportByTimeModel();

            for (; index < durationReportList.size(); index++) {
//                System.err.println("item.getDueDate(): " + simpleDateFormat.format(durationReportsById.get(i).getDueDate()));
//                System.err.println("item.getQuantity(): " + durationReportsById.get(i).getQuantity());
                if (durationReportList.get(index).getDueDate().before(startTime.getTime()) || simpleDateFormat.format(durationReportList.get(index).getDueDate()).equals(simpleDateFormat.format(startTime.getTime()))) {
//                    System.err.println("item.getDueDate()1111: " + simpleDateFormat.format(durationReportsById.get(i).getDueDate()));
//                    System.err.println("item.getQuantity()1111: " + durationReportsById.get(i).getQuantity());
                    quantity += durationReportList.get(index).getQuantity();
                }else break;
            }

            countQuarter += 1;
            detailReportByTimeModel.setDueDate("Quý " + countQuarter);
            detailReportByTimeModel.setQuantity(quantity);
            AnalysisDetailReportByTimeModelList.add(detailReportByTimeModel);

            startTime.add(Calendar.DATE, 1);
            startTime.setTime(AnalysisDetailReportService.getLastDayOfQuarter(startTime.getTime()));

        }
        reportDetailDTO.setResultMrp(AnalysisDetailReportByTimeModelList);

    }

    public PageResponse<List<ReportItemDTO>> getAllItemReport(PageFilterInput<ItemFilter> input) {
        ItemFilter filter = input.getFilter();
        JPAQuery<ReportItemDTO> query = buildQueryGetItemHasRecommendation(filter);
        List<ReportItemDTO> resultList = query.fetch();
        long count = query.fetchCount();
        Double po = 0.0;
        Double pr = 0.0;
        List<String> stringList = new ArrayList<>();
        for (ReportItemDTO result : resultList) {
            if(!stringList.contains(result.getMrpCode())){
                stringList.add(result.getMrpCode());
            }
        }
        List<SapOnOrderSummary> onOrderSummaryList = sapOnOrderDurationDetailRepository.catchSapOnOrderSummary(stringList);
        for (ReportItemDTO result : resultList) {
            for (SapOnOrderSummary onOrderSummary: onOrderSummaryList){
                if(onOrderSummary.getMrpCode().equals(result.getMrpCode()) && onOrderSummary.getItemCode().equals(result.getItemCode())){
                    if(onOrderSummary.getType().equals("PO")){
                        po += onOrderSummary.getQuantity() ;
                    } else {
                        pr += onOrderSummary.getQuantity();
                    }
                }
            }
            result.setPoQuantity(po);
            result.setSumPrQuantity(pr);
            if(pr > 0.0){
                result.setPercent((po/pr)*100);
            }else {
                result.setPercent(0.0);
            }
            po = 0.0;
            pr = 0.0;
            result.setMrpQuantity(sapOnOrderDurationDetailRepository.getSum(result.getItemCode(),result.getMrpCode()));
//            result.setSumPrQuantity(planRepository.sumPrQuantity(result.getItemCode(), result.getPurchaseRecommendationId()));
            result.setSumRequestQuantity(planRepository.sumRequestQuantity(result.getItemCode(), result.getPurchaseRecommendationId()));
        }

        return new PageResponse<List<ReportItemDTO>>()
            .result("00", "Thành công", true)
            .data(resultList)
            .dataCount(count);
    }

    private JPAQuery<ReportItemDTO> buildQueryGetItemHasRecommendation( ItemFilter filter) {
        QPurchaseRecommendationBatch qPurchaseRecommendationBatch = QPurchaseRecommendationBatch.purchaseRecommendationBatch;
        QPurchaseRecommendationDetailEntity qPurchaseRecommendationDetail = QPurchaseRecommendationDetailEntity.purchaseRecommendationDetailEntity;
        QVendorEntity qVendorEntity = QVendorEntity.vendorEntity;
        QMqqPriceEntity qMqqPriceEntity = QMqqPriceEntity.mqqPriceEntity;
        QProductOrder qProductOrder = QProductOrder.productOrder;
        QPurchaseRecommendationEntity qPurchaseRecommendation = QPurchaseRecommendationEntity.purchaseRecommendationEntity;
        JPAQuery<ReportItemDTO> query = new JPAQueryFactory(entityManager)
            .select(new QReportItemDTO(
                qPurchaseRecommendationBatch.itemCode,
                qPurchaseRecommendationBatch.itemDescription,
                qPurchaseRecommendationBatch.quantity,
                qPurchaseRecommendationBatch.receiveDate,
                qMqqPriceEntity.vendorCode,
                qVendorEntity.vendorName,
                qMqqPriceEntity.price,
                qMqqPriceEntity.currency,
                qMqqPriceEntity.isPromotion,
                qMqqPriceEntity.timeEnd,
                qPurchaseRecommendationBatch.status,
                qPurchaseRecommendationBatch.note,
                qPurchaseRecommendationBatch.createdAt,
                qProductOrder.productOrderCode,
                qPurchaseRecommendation.mrpSubCode,
                qPurchaseRecommendation.purchaseRecommendationId
            )).from(qPurchaseRecommendation)
            .leftJoin(qProductOrder).on(qPurchaseRecommendation.mrpPoId.eq(qProductOrder.productOrderCode))
            .leftJoin(qPurchaseRecommendationDetail).on(qPurchaseRecommendationDetail.purchaseRecommendationId.eq(qPurchaseRecommendation.purchaseRecommendationId))
            .leftJoin(qPurchaseRecommendationBatch).on(qPurchaseRecommendationBatch.purchaseRecommendationDetailId.eq(qPurchaseRecommendationDetail.purchaseRecommendationDetailId))
            .leftJoin(qMqqPriceEntity).on(qPurchaseRecommendationBatch.moqPriceId.eq(qMqqPriceEntity.itemPriceId))
            .leftJoin(qVendorEntity).on(qMqqPriceEntity.vendorCode.eq(qVendorEntity.vendorCode));
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (filter != null) {
            if (filter.getStart() != null && filter.getEnd() != null) {
                booleanBuilder.and(qPurchaseRecommendation.startTime.after(filter.getStart()).and(qPurchaseRecommendation.startTime.before(filter.getEnd())));
                booleanBuilder.or(qPurchaseRecommendation.endTime.after(filter.getStart()).and(qPurchaseRecommendation.endTime.before(filter.getEnd())));
            }
            if (!StringUtils.isEmpty(filter.getItemCode())) {
                booleanBuilder.and(qPurchaseRecommendationBatch.itemCode.containsIgnoreCase(filter.getItemCode().trim()));
            }
            if (!StringUtils.isEmpty(filter.getItemDescription())) {
                booleanBuilder.and(qPurchaseRecommendationBatch.itemDescription.containsIgnoreCase(filter.getItemDescription()));
            }
            if (!StringUtils.isEmpty(filter.getMrpCode())) {
                booleanBuilder.and(qPurchaseRecommendation.mrpSubCode.eq(filter.getMrpCode().trim()));
            }
            if (!StringUtils.isEmpty(filter.getSoCode())) {
                booleanBuilder.and(qProductOrder.productOrderCode.eq(filter.getSoCode().trim()));
            }
            if (!CollectionUtils.isEmpty(filter.getStatus())) {
                booleanBuilder.and(qPurchaseRecommendationBatch.status.in(filter.getStatus()));
            }
            if (!StringUtils.isEmpty(filter.getSupplierCode())) {
                booleanBuilder.and(qVendorEntity.vendorCode.eq(filter.getSupplierCode().trim()));
            }
            if (!StringUtils.isEmpty(filter.getSupplierName())) {
                booleanBuilder.and(qVendorEntity.vendorName.eq(filter.getSupplierName().trim()));
            }
        }

        booleanBuilder.and(qPurchaseRecommendationBatch.status.eq(3));
        query.where(booleanBuilder);
        return query;
    }

    public CommonResponse getBranch(){

        List<String> sapBranchGroupEntities = branchGroupRepository.getDistinctBranchCodes();
        List<BranchGroupDTO> branchGroupDTOS = new ArrayList<>();
        for (String sapBranchGroup: sapBranchGroupEntities){
            BranchGroupDTO branchGroupDTO = new BranchGroupDTO();
            branchGroupDTO.setuBranchCode(sapBranchGroup);
            branchGroupDTO.setGroupDTOList(new ArrayList<>());
            List<SapBranchGroupEntity> sapGroupEntities = branchGroupRepository.getSapGroupEntities(sapBranchGroup);
            for (SapBranchGroupEntity sapGroup: sapGroupEntities){
                GroupDTO groupDTO = new GroupDTO();

                groupDTO.setuGroupName(sapGroup.getuGroupName());
                groupDTO.setuGroupCode(sapGroup.getuGroupCode());
                branchGroupDTO.getGroupDTOList().add(groupDTO);
            }
            branchGroupDTOS.add(branchGroupDTO);
        }
        return new PageResponse<List<BranchGroupDTO>>()
            .result("00", "Thành công", true)
            .data(branchGroupDTOS);
    }

    public List<ReportXPDTO> getPOReport(ReportXPDTO reportXPDTO) {

        Date startDate = Date.from(reportXPDTO.getStartTime());
        Date endDate = Date.from(reportXPDTO.getEndTime());

        StringBuilder sql = new StringBuilder("SELECT pr.so_code as soCode, po.po_code as poCode, " +
            "poi.item_code as itemCode, poi.item_name as itemDescription, po.vendor_name as vendorName, " +
            "SUM(sod.quantity) as requiredPurchaseQty, " +
            "SUM(poip.quantity) as approvedPurchaseQty, pr.pr_create_date, sum(poip.quantity) as receivedQty, " +
            "po.delivery_date as arriveDate, po.status as status " +
            "FROM purchase_order_item poi " +
            "LEFT JOIN purchase_order po ON poi.purchase_order_id = po.id " +
            "LEFT JOIN purchase_order_purchase_request popr ON po.id = popr.purchase_order_id " +
            "LEFT JOIN purchase_request pr ON popr.purchase_request_code LIKE CONCAT('%', pr.pr_code, '%') " +
            "LEFT JOIN product_order so ON pr.so_code = so.product_order_code " +
            "JOIN product_order_detail sod ON so.product_order_code = sod.product_order_code AND poi.item_code = sod.product_code " +
            "LEFT JOIN purchase_request_detail prd ON prd.item_code = poi.item_code AND prd.pr_code = pr.pr_code " +
            "LEFT JOIN purchase_order_item_progress poip ON poi.id = poip.purchase_order_item_id " +
            "WHERE poi.item_code IS NOT NULL");

        // Dynamic filters based on the DTO input
        if (reportXPDTO.getSoCode() != null && !reportXPDTO.getSoCode().isEmpty()) {
            sql.append(" AND LOWER(pr.so_code) LIKE LOWER(?3)");
        }
        if (reportXPDTO.getPoCode() != null && !reportXPDTO.getPoCode().isEmpty()) {
            sql.append(" AND LOWER(po.po_code) LIKE LOWER(?4)");
        }
        if (reportXPDTO.getItemCode() != null && !reportXPDTO.getItemCode().isEmpty()) {
            sql.append(" AND LOWER(poi.item_code) LIKE LOWER(?5)");
        }
        if (reportXPDTO.getVendorName() != null && !reportXPDTO.getVendorName().isEmpty()) {
            sql.append(" AND LOWER(po.vendor_name) LIKE LOWER(?6)");
        }
        if (reportXPDTO.getStatus() != null) {
            sql.append(" AND po.status = ?7");
        }

        sql.append(" GROUP BY pr.so_code, po.po_code, poi.item_code, po.vendor_name, prd.required_quantity, po.approval_date, po.status ORDER BY po.approval_date DESC");

        // Create the query
        Query query = entityManager.createNativeQuery(sql.toString());

        // Set parameters for the query
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);

        int paramIndex = 3;
        if (reportXPDTO.getSoCode() != null && !reportXPDTO.getSoCode().isEmpty()) {
            query.setParameter(paramIndex, "%" + reportXPDTO.getSoCode() + "%");
        }
        paramIndex++;
        if (reportXPDTO.getPoCode() != null && !reportXPDTO.getPoCode().isEmpty()) {
            query.setParameter(paramIndex, "%" + reportXPDTO.getPoCode() + "%");
        }
        paramIndex++;
        if (reportXPDTO.getItemCode() != null && !reportXPDTO.getItemCode().isEmpty()) {
            query.setParameter(paramIndex, "%" + reportXPDTO.getItemCode() + "%");
        }
        paramIndex++;
        if (reportXPDTO.getVendorName() != null && !reportXPDTO.getVendorName().isEmpty()) {
            query.setParameter(paramIndex, "%" + reportXPDTO.getVendorName() + "%");
        }
        paramIndex++;
        if (reportXPDTO.getStatus() != null) {
            query.setParameter(paramIndex, reportXPDTO.getStatus());
        }

        // Retrieve and map results
        List<Object[]> results = query.getResultList();
        List<ReportXPDTO> reportList = new ArrayList<>();

        for (Object[] result : results) {
            ReportXPDTO dto = new ReportXPDTO();
            dto.setSoCode((String) result[0]);
            dto.setPoCode((String) result[1]);
            dto.setItemCode((String) result[2]);
            dto.setItemDescription((String) result[3]);
            dto.setVendorName((String) result[4]);
            dto.setRequiredPurchaseQty(((Number) result[5]).intValue());
            dto.setApprovedPurchaseQty(((Number) result[6]).intValue());
            dto.setApprovalDate(Date.from((Instant) result[7]));
            dto.setReceivedQty(((Number) result[8]).intValue());
            dto.setArrivalDate(Date.from((Instant) result[9]));
            dto.setStatus((String) result[10]);
            dto.setUnreceivedQty(((Number) result[6]).intValue() - ((Number) result[8]).intValue());
            dto.setCompletionRate((double) (((Number) result[8]).intValue()/((Number) result[6]).intValue()));
            reportList.add(dto);
        }

        return reportList;
    }

}
