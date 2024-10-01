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
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    @Transactional
    public PageResponse<List<ReportXPDTO>> getPOReport(PageFilterInput<ReportXPDTO> input, Pageable pageable) {

        Date startDate = Date.from(input.getFilter().getStartTime());
        Date endDate = Date.from(input.getFilter().getEndTime());

        StringBuilder sql = new StringBuilder("SELECT pr.so_code as soCode, po.po_code as poCode, " +
            "poi.item_code as itemCode, poi.item_name as itemDescription, po.vendor_name as vendorName, " +
            "pred.required_quantity, " +
            "pred.quantity, pr.pr_create_date, sum(poip.quantity) as receivedQty, " +
            "po.delivery_date as arriveDate, po.status as status, " +
            "poi.price as price, MAX(poip.date) " +
            "FROM purchase_order_item poi " +
            "LEFT JOIN purchase_order po ON poi.purchase_order_id = po.id " +
            "LEFT JOIN purchase_order_purchase_request popr ON po.id = popr.purchase_order_id " +
            "LEFT JOIN purchase_request pr ON popr.purchase_request_code LIKE CONCAT('%', pr.pr_code, '%') " +
            "LEFT JOIN product_order so ON pr.so_code = so.product_order_code " +
            "LEFT JOIN purchase_request_detail prd ON prd.item_code = poi.item_code AND prd.pr_code = pr.pr_code " +
            "LEFT JOIN purchase_order_item_progress poip ON poi.id = poip.purchase_order_item_id " +
            "LEFT JOIN purchase_recommendation pre ON pre.mrp_sub_code = pr.mrp_code " +
            "LEFT JOIN purchase_recommendation_detail pred ON pred.purchase_recommendation_id = pre.purchase_recommendation_id AND pred.item_code = poi.item_code " +
            "WHERE poi.item_code IS NOT NULL " +
            "AND po.created_at BETWEEN ?1 AND ?2 ");

        int paramIndex = 3;

        // Dynamic filters based on the DTO input
        if (input.getFilter().getSoCode() != null && !input.getFilter().getSoCode().isEmpty()) {
            sql.append(" AND LOWER(pr.so_code) LIKE LOWER(?" + paramIndex + ")");
            paramIndex++;
        }
        if (input.getFilter().getPoCode() != null && !input.getFilter().getPoCode().isEmpty()) {
            sql.append(" AND LOWER(po.po_code) LIKE LOWER(?" + paramIndex + ")");
            paramIndex++;
        }
        if (input.getFilter().getItemCode() != null && !input.getFilter().getItemCode().isEmpty()) {
            sql.append(" AND LOWER(poi.item_code) LIKE LOWER(?" + paramIndex + ")");
            paramIndex++;
        }
        if (input.getFilter().getVendorName() != null && !input.getFilter().getVendorName().isEmpty()) {
            sql.append(" AND LOWER(po.vendor_name) LIKE LOWER(?" + paramIndex + ")");
            paramIndex++;
        }
        if (input.getFilter().getStatus() != null) {
            sql.append(" AND po.status = ?" + paramIndex );
        }
        if(input.getFilter().getActualArrivalDate() != null) {
            sql.append(" AND Date(MAX(poip.date)) = ?" + paramIndex );
            paramIndex++;
        }
        if(input.getFilter().getPRCreatedDate() != null) {
            sql.append(" AND Date(pr.pr_create_date) = ?" + paramIndex );
            paramIndex++;
        }

         sql.append(" GROUP BY poi.item_code, po.po_code, po.vendor_code ORDER BY po.created_at DESC");

        // Create the query
        Query query = entityManager.createNativeQuery(sql.toString());

        // Set parameters for the query
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);

        paramIndex--;
        if (input.getFilter().getSoCode() != null && !input.getFilter().getSoCode().isEmpty()) {
            query.setParameter(paramIndex, "%" + input.getFilter().getSoCode() + "%");
        }

        if (input.getFilter().getPoCode() != null && !input.getFilter().getPoCode().isEmpty()) {
            query.setParameter(paramIndex, "%" + input.getFilter().getPoCode() + "%");
        }

        if (input.getFilter().getItemCode() != null && !input.getFilter().getItemCode().isEmpty()) {
            query.setParameter(paramIndex, "%" + input.getFilter().getItemCode() + "%");
        }

        if (input.getFilter().getVendorName() != null && !input.getFilter().getVendorName().isEmpty()) {
            query.setParameter(paramIndex, "%" + input.getFilter().getVendorName() + "%");
        }

        if (input.getFilter().getStatus() != null) {
            query.setParameter(paramIndex, input.getFilter().getStatus());
        }

        if (input.getFilter().getApprovalDate() != null ) {
            java.sql.Date approvalDate = new java.sql.Date(input.getFilter().getApprovalDate().toInstant().toEpochMilli());
            query.setParameter(paramIndex, approvalDate);
        }

        if (input.getFilter().getArrivalDate() != null ) {
            java.sql.Date arrivalDate = new java.sql.Date(input.getFilter().getArrivalDate().toInstant().toEpochMilli());
            query.setParameter(paramIndex, arrivalDate);
        }

        if (input.getFilter().getActualArrivalDate() != null ) {
            java.sql.Date arrivalDate = new java.sql.Date(input.getFilter().getActualArrivalDate().toInstant().toEpochMilli());
            query.setParameter(paramIndex, arrivalDate);
        }

        if (input.getFilter().getPRCreatedDate() != null ) {
            java.sql.Date arrivalDate = new java.sql.Date(input.getFilter().getPRCreatedDate().toInstant().toEpochMilli());
            query.setParameter(paramIndex, arrivalDate);
        }

        // Retrieve and map results
        List<Object[]> results;

        int totalRows = query.getResultList().size(); // Count the total rows
        if (pageable.isPaged()) {
            results = query.setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        } else {
            results = query.getResultList(); // No pagination
        }

        List<ReportXPDTO> reportList = new ArrayList<>();

        for (Object[] result : results) {
            ReportXPDTO dto = new ReportXPDTO();
            dto.setSoCode((String) result[0]);
            dto.setPoCode((String) result[1]);
            dto.setItemCode((String) result[2]);
            dto.setItemDescription((String) result[3]);
            dto.setVendorName((String) result[4]);
            dto.setRequiredPurchaseQty(result[5]!=null ? ((Number) result[5]).intValue() : 0);
            dto.setApprovedPurchaseQty(result[6]!=null ? ((Number) result[6]).intValue() : 0);
            dto.setPRCreatedDate((Date) result[7]);
            dto.setApprovalDate((Date) result[7]);
            dto.setReceivedQty(result[8]!=null ? ((Number) result[8]).intValue() : 0);
            dto.setArrivalDate((Date) result[9]);
            dto.setStatus((String) result[10]);
            dto.setPrice((Double) result[11]);
            dto.setActualArrivalDate((Date) result[12]);
            dto.setUnreceivedQty(dto.getApprovedPurchaseQty() - dto.getReceivedQty());
            dto.setCompletionRate(dto.getApprovedPurchaseQty() != 0 ? (double) (dto.getReceivedQty()*100.0/(dto.getApprovedPurchaseQty())) : 0);
            reportList.add(dto);
        }

        return new PageResponse<List<ReportXPDTO>>()
            .errorCode("00")
            .message("Thành công")
            .isOk(true)
            .dataCount(totalRows)
            .data(reportList);
    }

    @Transactional
    public PageResponse<List<ReportXPDTO>> getSOReport(PageFilterInput<ReportXPDTO> input, Pageable pageable)
    {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ")
            .append("    pr.so_code AS MaSO, ")
            .append("    GROUP_CONCAT(DISTINCT(pur.po_code) ORDER BY pur.po_code SEPARATOR ', ') as MaPO, ")
            .append("    GROUP_CONCAT(DISTINCT(pr_detail.ncc_name) ORDER BY pr_detail.ncc_name SEPARATOR ', ') as TenKhachHang, ")
            .append("    GROUP_CONCAT(DISTINCT(pr.pr_create_user) ORDER BY pr.pr_create_user SEPARATOR ', ') as NguoiDatHang, ")
            .append("    GROUP_CONCAT(DISTINCT(pr.approval_user) ORDER BY pr.approval_user SEPARATOR ', ') as NguoiMuaHang, ")
            .append("    pur.created_at AS ThoiGianDatHang, ")
            .append("    pur.delivery_date AS ThoiGianTraHang ")
            .append("FROM product_order pro ")
            .append("JOIN purchase_request pr ON pro.product_order_code = pr.so_code ")
            .append("JOIN purchase_request_detail pr_detail ON pr.pr_code = pr_detail.pr_code ")
            .append("JOIN purchase_order_purchase_request pur_pr ON FIND_IN_SET(pr.pr_code, pur_pr.purchase_request_code) ")
            .append("JOIN purchase_order pur ON pur.id = pur_pr.purchase_order_id ")
            .append("WHERE pro.created_at >= ?1 ")
            .append("  AND pro.created_at <= ?2")
            .append(" GROUP BY pr.so_code");

        int paramIndex = 3;

        if(input.getFilter().getSoCode() != null && !input.getFilter().getSoCode().isEmpty()) {
            sql.append(" AND LOWER(pr.so_code) LIKE LOWER(? " + paramIndex + ")");
            paramIndex++;
        }
        if(input.getFilter().getPoCode() != null && !input.getFilter().getPoCode().isEmpty()) {
            sql.append(" AND LOWER(pur.po_code) LIKE LOWER(?" + paramIndex + ")");
            paramIndex++;
        }
        if(input.getFilter().getCustomerName() != null && !input.getFilter().getCustomerName().isEmpty()) {
            sql.append(" AND LOWER(pr_detail.ncc_name) LIKE LOWER(?" + paramIndex + ")");
            paramIndex++;
        }
        if(input.getFilter().getOrderer() != null && !input.getFilter().getOrderer().isEmpty()) {
            sql.append(" AND LOWER(pr.pr_create_user) LIKE LOWER(?" + paramIndex + ")");
            paramIndex++;
        }
        if(input.getFilter().getBuyer() != null && !input.getFilter().getBuyer().isEmpty()) {
            sql.append(" AND LOWER(pr.approval_user) LIKE LOWER(?" + paramIndex + ")");
            paramIndex++;
        }
        if(input.getFilter().getOrderTime() != null) {
            sql.append(" AND Date(pur.created_at) = ?" + paramIndex );
            paramIndex++;
        }
        if(input.getFilter().getDeliveryTime() != null) {
            sql.append(" AND Date(pur.delivery_date) = ?"+ paramIndex );
            paramIndex++;
        }

        paramIndex--;
        Query query = entityManager.createNativeQuery(sql.toString());

        // Set parameters
        query.setParameter(1, Date.from(input.getFilter().getStartTime()));
        query.setParameter(2, Date.from(input.getFilter().getEndTime()));

        if (input.getFilter().getSoCode() != null && !input.getFilter().getSoCode().isEmpty()) {
            query.setParameter(paramIndex, "%" + input.getFilter().getSoCode() + "%");
        }

        if (input.getFilter().getPoCode() != null && !input.getFilter().getPoCode().isEmpty()) {
            query.setParameter(paramIndex, "%" + input.getFilter().getPoCode() + "%");
        }

        if (input.getFilter().getCustomerName() != null && !input.getFilter().getCustomerName().isEmpty()) {
            query.setParameter(paramIndex, "%" + input.getFilter().getCustomerName() + "%");
        }

        if (input.getFilter().getOrderer() != null && !input.getFilter().getOrderer().isEmpty()) {
            query.setParameter(paramIndex, "%" + input.getFilter().getOrderer() + "%");
        }

        if (input.getFilter().getBuyer() != null && !input.getFilter().getBuyer().isEmpty()) {
            query.setParameter(paramIndex, "%" + input.getFilter().getBuyer() + "%");
        }

        if (input.getFilter().getOrderTime() != null ) {
            java.sql.Date orderDate = new java.sql.Date(input.getFilter().getOrderTime().toInstant().toEpochMilli());
            query.setParameter(paramIndex, orderDate);
        }

        if (input.getFilter().getDeliveryTime() != null ) {
            java.sql.Date deliveryTime = new java.sql.Date(input.getFilter().getDeliveryTime().toInstant().toEpochMilli());
            query.setParameter(paramIndex, deliveryTime);
        }

        // Retrieve and map results
        List<Object[]> results;

        int totalRows = query.getResultList().size(); // Count the total rows
        if (pageable.isPaged()) {
            results = query.setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        } else {
            results = query.getResultList(); // No pagination
        }

        List<ReportXPDTO> reportList = new ArrayList<>();

        int totalRequiredItemQty;
        int totalPrItemQty;

        for(Object[] item : results)
        {
            ReportXPDTO report = new ReportXPDTO();
            List<String> soCodes = new ArrayList<>();
            List<String> fcCodes = new ArrayList<>();
            report.setSoCode((String)item[0]);
            soCodes.add((String)item[0]);

            report.setPoCode((String)item[1]);
            report.setCustomerName((String)item[2]);
            report.setOrderer((String)item[3]);
            report.setBuyer((String)item[4]);
            report.setOrderTime((Date)item[5]);
            report.setDeliveryTime((Date)item[6]);

            totalRequiredItemQty = getSuppliesCount(soCodes, fcCodes).size();
            totalPrItemQty = getPrCount(soCodes, fcCodes).size();
            logger.info("Number: {} - {}", totalRequiredItemQty, totalPrItemQty);
            report.setTotalRequiredItemQty(totalRequiredItemQty);
            report.setTotalPrItemQty(totalPrItemQty);
            report.setUnreceivedQty(totalRequiredItemQty - totalPrItemQty);
            report.setCompletionRate(totalRequiredItemQty !=0 ? (totalPrItemQty * 100.0) / totalRequiredItemQty : 0);
            reportList.add(report);
        }

        return new PageResponse<List<ReportXPDTO>>()
            .errorCode("00")
            .message("Thành công")
            .isOk(true)
            .dataCount(totalRows)
            .data(reportList);
    }

    // FORM 1
    @Transactional
    public byte[] exportSOReportToExcel(PageFilterInput<ReportXPDTO> input, Pageable pageable)
    {
        List<ReportXPDTO> data;
        data = getSOReport(input, pageable).getData();
        String[] columns = { "STT", "MÃ SO/FC", "MÃ PO", "Tên khách hàng", "Người đặt hàng",
            "Người mua hàng", "Thời gian đặt hàng", "Thời gian trả hàng",
            "Tổng số lượng vật tư cần mua", "Tổng số lượng vật tư đã lên PR",
            "Tổng số lượng hàng chưa về", "Tỉ lệ hoàn thành (%)"
        };
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try (Workbook workbook = new XSSFWorkbook())
        {
            Sheet sheet = workbook.createSheet("BÁO CÁO TỔNG HỢP MRP");

            CellStyle titleStyle = workbook.createCellStyle();
            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 16);
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);

            CellStyle styleDouble = workbook.createCellStyle();
            DataFormat format = workbook.createDataFormat();
            styleDouble.setDataFormat(format.getFormat("0.0"));

            // Title
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("BÁO CÁO TỔNG HỢP MRP");
            //titleCell.setCellStyle(titleStyle);
            titleCell.setCellStyle(initTitleStyle(workbook));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columns.length-1));

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);

            Row headerRow = sheet.createRow(1);
            for(int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                // cell.setCellStyle(cellStyle);
                cell.setCellStyle(initHeaderStyle(workbook));
                cell.setCellValue(columns[i]);
            }

            // Write data rows
            int rowNum = 2; int stt = 1;
            for(ReportXPDTO dto : data) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(stt++);
                row.createCell(1).setCellValue(dto.getSoCode());
                row.createCell(2).setCellValue(dto.getPoCode());
                row.createCell(3).setCellValue(dto.getCustomerName());
                row.createCell(4).setCellValue(dto.getOrderer());
                row.createCell(5).setCellValue(dto.getBuyer());
                row.createCell(6).setCellValue(dateFormat.format(dto.getOrderTime()));
                row.createCell(7).setCellValue(dateFormat.format(dto.getDeliveryTime()));
                row.createCell(8).setCellValue(dto.getTotalRequiredItemQty());
                row.createCell(9).setCellValue(dto.getTotalPrItemQty());
                row.createCell(10).setCellValue(dto.getUnreceivedQty());

                Cell cell = row.createCell(11);
                cell.setCellValue(dto.getCompletionRate());
                cell.setCellStyle(styleDouble);

            }
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

//            FileOutputStream out = new FileOutputStream(new File("E:/test.xlsx"));
//            workbook.write(out);
            // Convert workbook to byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // FORM 2
    @Transactional
    public byte[] exportPurchaseByNCCReportToExcel(PageFilterInput<ReportXPDTO> input, Pageable pageable) throws IOException {
        List<ReportXPDTO> data;
        data = getPOReport(input, pageable).getData();
        String[] columns = { "STT", "MÃ SO/FC", "MÃ PO", "Mã vật tư", "Mô tả vật tư",
            "Tên NCC", "Số lượng cần mua", "Số lượng đã được phê duyệt đặt hàng", "Ngày duyệt",
            "Số lượng đã về", "Ngày hàng về", "Số lượng chưa về", "Tỉ lệ hoàn thành (%)", "Tình trạng"
        };
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("BÁO CÁO CHI TIẾT GIAO DỊCH ĐẶT HÀNG");

            CellStyle titleStyle = workbook.createCellStyle();
            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 16);
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);

            CellStyle styleDouble = workbook.createCellStyle();
            DataFormat format = workbook.createDataFormat();
            styleDouble.setDataFormat(format.getFormat("0.0"));

            // Title
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("BÁO CÁO CHI TIẾT GIAO DỊCH ĐẶT HÀNG");
            // titleCell.setCellStyle(titleStyle);
            titleCell.setCellStyle(initTitleStyle(workbook));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columns.length-1));

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);

            Row headerRow = sheet.createRow(1);
            for(int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                // cell.setCellStyle(cellStyle);
                cell.setCellStyle(initHeaderStyle(workbook));
                cell.setCellValue(columns[i]);
            }

            // Write data rows
            int rowNum = 2; int stt = 1;
            for(ReportXPDTO dto : data) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(stt++);
                row.createCell(1).setCellValue(dto.getSoCode());
                row.createCell(2).setCellValue(dto.getPoCode());
                row.createCell(3).setCellValue(dto.getItemCode());
                row.createCell(4).setCellValue(dto.getItemDescription());
                row.createCell(5).setCellValue(dto.getVendorName());
                row.createCell(6).setCellValue(dto.getRequiredPurchaseQty());
                row.createCell(7).setCellValue(dto.getApprovedPurchaseQty());
                row.createCell(8).setCellValue(dateFormat.format(dto.getApprovalDate()));
                row.createCell(9).setCellValue(dto.getReceivedQty());
                row.createCell(10).setCellValue(dateFormat.format(dto.getArrivalDate()));
                row.createCell(11).setCellValue(dto.getUnreceivedQty());

                Cell cell = row.createCell(12);
                cell.setCellValue(dto.getCompletionRate());
                cell.setCellStyle(styleDouble);

                row.createCell(13).setCellValue(dto.getStatus());
            }
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

//            FileOutputStream out = new FileOutputStream(new File("E:/test.xlsx"));
//            workbook.write(out);
            // Convert workbook to byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // FORM 3

    @Transactional
    public byte[] exportNCCReportToExcel(PageFilterInput<ReportXPDTO> input, Pageable pageable) throws IOException
    {
        List<ReportXPDTO> data;
        data = getPOReport(input, pageable).getData();
        String[] columns = { "STT", "Mã vật tư", "Mô tả vật tư",
            "Tên NCC", "Số lượng hàng đã duyệt đặt hàng", "Đơn giá",
            "Ngày đặt hàng", "YC ngày hàng về", "Ngày thực tế hàng về"
        };
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("BÁO CÁO THỐNG KÊ NHÀ CUNG CẤP");

            CellStyle titleStyle = workbook.createCellStyle();
            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 16);
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);

            CellStyle styleDouble = workbook.createCellStyle();
            DataFormat format = workbook.createDataFormat();
            styleDouble.setDataFormat(format.getFormat("0.0"));

            // Title
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("BÁO CÁO THỐNG KÊ NHÀ CUNG CẤP");
            // titleCell.setCellStyle(titleStyle);
            titleCell.setCellStyle(initTitleStyle(workbook));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columns.length-1));

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);

            Row headerRow = sheet.createRow(1);
            for(int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                // cell.setCellStyle(cellStyle);
                cell.setCellStyle(initHeaderStyle(workbook));
                cell.setCellValue(columns[i]);
            }

            // Write data rows
            int rowNum = 2; int stt = 1;
            for(ReportXPDTO dto : data) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(stt++);
                row.createCell(1).setCellValue(dto.getItemCode());
                row.createCell(2).setCellValue(dto.getItemDescription());
                row.createCell(3).setCellValue(dto.getVendorName());
                row.createCell(4).setCellValue(dto.getApprovedPurchaseQty());
                row.createCell(5).setCellValue(dto.getPrice());

                if (dto.getPRCreatedDate() != null) {
                    row.createCell(6).setCellValue(dateFormat.format(dto.getPRCreatedDate()));
                } else {
                    row.createCell(6).setCellValue("");
                }

                if (dto.getArrivalDate() != null) {
                    row.createCell(7).setCellValue(dateFormat.format(dto.getArrivalDate()));
                } else {
                    row.createCell(7).setCellValue("");
                }

                if (dto.getActualArrivalDate() != null) {
                    row.createCell(8).setCellValue(dateFormat.format(dto.getActualArrivalDate()));
                } else {
                    row.createCell(8).setCellValue("");
                }
            }
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

//            FileOutputStream out = new FileOutputStream(new File("E:/test.xlsx"));
//            workbook.write(out);
            // Convert workbook to byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private CellStyle initTitleStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setWrapText(true);
        cellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);

        cellStyle.setFont(titleFont);

        return cellStyle;
    }

    private CellStyle initHeaderStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setWrapText(true);
        cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }
}
