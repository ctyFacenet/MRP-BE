package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.ProductOrder;
import com.facenet.mrp.repository.mrp.*;
import com.facenet.mrp.repository.sap.CoittRepository;
import com.facenet.mrp.repository.sap.OcrdRepository;
import com.facenet.mrp.service.dto.ReportDTO;
import com.facenet.mrp.service.dto.ProductDetail;
import com.facenet.mrp.service.dto.ReportDetailDTO;
import com.facenet.mrp.service.dto.mrp.*;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.model.ReportFilter;
import com.facenet.mrp.service.utils.Constants;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

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

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ReportService(ProductOrderRepository productOrderRepository, ForecastOrderDetailRepository forecastOrderDetailRepository, ProductOrderDetailRepository productOrderDetailRepository, CoittRepository coittRepository, SapOnOrderDurationDetailRepository durationDetailRepository, PurchaseRecommendationPlanRepository purchaseRecommendationPlanRepository, PurchaseRecommendationDetailRepository purchaseRecommendationDetailRepository, MrpBomDetailRepository mrpBomDetailRepository, OcrdRepository ocrdRepository, MrpRequiredQuantityRepository mrpRequiredQuantityRepository, SapOnOrderSummaryRepository sapOnOrderSummaryRepository) {
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
                dto.setSaleCode(item.getSaleId());
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

    public List<ReportDetailDTO> getDetailReport(Integer reportMode, String soCode){

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
            reportDetailDTOS = mrpRequiredQuantityRepository.getAllMaterialRequiredQuantity(soCode);
            System.err.println("reportDetailDTOS: " + reportDetailDTOS.size());
            System.err.println("reportDetailDTOS: " + reportDetailDTOS.isEmpty());

            //Check null vật tư
            if (reportDetailDTOS.isEmpty()){
                throw new CustomException("analysis.item.had.not.exist");
            }
        }

        //lấy mã NCC được chọn của các vật tư được khuyến nghị
        vendorCodeForDetailReports = purchaseRecommendationDetailRepository.getVendorBySoCodeForDetailReport(soCode);

        if (vendorCodeForDetailReports != null && !vendorCodeForDetailReports.isEmpty()){
            for (VendorCodeForDetailReport item : vendorCodeForDetailReports){
                vendorCodeSet.add(item.getVendorCode());
            }
        }
        //Lấy tên NCC từ mã NCC vừa lấy được và ghep vào cùng một danh sách
        if (!vendorCodeSet.isEmpty()){
            vendorInfoList = ocrdRepository.getVendorList(vendorCodeSet);

            if (vendorInfoList != null && !vendorInfoList.isEmpty()){
                for (VendorCodeForDetailReport vendorCodeForDetailReport : vendorCodeForDetailReports){
                    for (VendorCodeForDetailReport ocrdItem : vendorInfoList){
                        if (vendorCodeForDetailReport.getVendorCode().equals(ocrdItem.getVendorCode())){
                            vendorCodeForDetailReport.setVendorName(ocrdItem.getVendorName());
                            break;
                        }
                    }
                }
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

}
