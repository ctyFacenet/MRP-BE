package com.facenet.mrp.service;

import com.facenet.mrp.repository.mrp.PurchaseRecommendationPlanRepository;
import com.facenet.mrp.repository.mrp.SapOnOrderDurationDetailRepository;
import com.facenet.mrp.repository.mrp.SapOnOrderSummaryRepository;
import com.facenet.mrp.service.dto.mrp.*;
import com.facenet.mrp.service.exception.CustomException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AnalysisDetailReportService {

    private static final Logger log = LogManager.getLogger(AnalysisDetailReportService.class);

    private final SapOnOrderDurationDetailRepository durationDetailRepository;
    private final SapOnOrderSummaryRepository sapOnOrderSummaryRepository;
    private final PurchaseRecommendationPlanRepository purchaseRecommendationPlanRepository;

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public AnalysisDetailReportService(SapOnOrderDurationDetailRepository durationDetailRepository, SapOnOrderSummaryRepository sapOnOrderSummaryRepository, PurchaseRecommendationPlanRepository purchaseRecommendationPlanRepository) {
        this.durationDetailRepository = durationDetailRepository;
        this.sapOnOrderSummaryRepository = sapOnOrderSummaryRepository;
        this.purchaseRecommendationPlanRepository = purchaseRecommendationPlanRepository;
    }

    public List<AnalysisDetailReport> getAnalysisDetailReport(AnalysisDetailReportFilter filter){

        List<AnalysisDetailReport> detailReports = new ArrayList<>();
        List<OnOrderDurationReport> durationReportsById;

        HashMap<Integer, List<OnOrderDurationReport>> durationDetailHashmap = new HashMap<>();
        Set<Integer> onOrderId = new LinkedHashSet<>();

        AnalysisDetailReport analysisDetailReport ;


        //Tìm các bản ghi theo range cụ thể của chi tiết GSTD
        List<OnOrderDurationReport> durationReports = durationDetailRepository.getDetailReportByRangeTime(filter.getStartDate(), filter.getEndDate());
        if (durationReports == null && durationReports.isEmpty()){
            throw new CustomException("record.notfound");
        }else {
            //Lay danh sach cac id trong bang sapOrderSummary
            for (OnOrderDurationReport item : durationReports) onOrderId.add(item.getId());

            //Cho vao hashmap cac  ban ghi tuong ung voi ma id
            for (int summaryid : onOrderId) {
                durationReportsById = new ArrayList<>();
                for (OnOrderDurationReport item : durationReports) {
                    if (summaryid == item.getId()){
                        durationReportsById.add(item);
                    }
                }
                durationDetailHashmap.put(summaryid, durationReportsById);
            }

            //tao vong for de ghep vao trong list result
            for (int id : durationDetailHashmap.keySet()) {

                //Lay thong tin so luong yeu cau, số lượng dự kiến về  trong bang purchaseRecommendDetail
                QuantityModelForReport quantityForReport = purchaseRecommendationPlanRepository.getAllQuantityForReport(
                    durationDetailHashmap.get(id).get(0).getMrpCode(),
                    durationDetailHashmap.get(id).get(0).getSoCode(),
                    durationDetailHashmap.get(id).get(0).getProductCode(),
                    filter.getEndDate(),
                    filter.getStartDate()
                );

                //Lay so lương GRPO , PO trong SApOnOrderSummary
                System.err.println("durationDetailHashmap.get(id).get(0).getMrpCode(): " + durationDetailHashmap.get(id).get(0).getMrpCode());
                System.err.println("durationDetailHashmap.get(id).get(0).getSoCode(): " + durationDetailHashmap.get(id).get(0).getSoCode());
                System.err.println("durationDetailHashmap.get(id).get(0).getProductCode(): " + durationDetailHashmap.get(id).get(0).getProductCode());
                List<PrGrpoAnalysisReport> poGrpoReports = sapOnOrderSummaryRepository.getPoGrpoReport(
                    durationDetailHashmap.get(id).get(0).getMrpCode(),
                    durationDetailHashmap.get(id).get(0).getSoCode(),
                    durationDetailHashmap.get(id).get(0).getProductCode(),
                    filter.getStartDate(),
                    filter.getEndDate());

                //so luong chưa về = số lượng dự kiến về - số lượng grpo
                analysisDetailReport = new AnalysisDetailReport();
                analysisDetailReport.setId(durationDetailHashmap.get(id).get(0).getId());
                analysisDetailReport.setSoCode(durationDetailHashmap.get(id).get(0).getSoCode());
                analysisDetailReport.setMrpCode(durationDetailHashmap.get(id).get(0).getMrpCode());
                analysisDetailReport.setProductCode(durationDetailHashmap.get(id).get(0).getProductCode());
                analysisDetailReport.setProductName(durationDetailHashmap.get(id).get(0).getProductName());
                analysisDetailReport.setVendorCode(durationDetailHashmap.get(id).get(0).getVendorCode());
                analysisDetailReport.setVendorName(durationDetailHashmap.get(id).get(0).getVendorName());
                analysisDetailReport.setReqQuantity(quantityForReport != null?quantityForReport.getReqQuantity() : 0.0);
                analysisDetailReport.setExpectedQuantity(quantityForReport != null?quantityForReport.getExpectedQuantity() : 0.0);

                if (poGrpoReports != null && !poGrpoReports.isEmpty()){
                    for (PrGrpoAnalysisReport  item : poGrpoReports){
                        if (item.getTypePrGrpo().equalsIgnoreCase("grpo") && item.getQuantity() != null){
                            analysisDetailReport.setOnHandQuantity(item.getQuantity());
                        } else if (item.getTypePrGrpo().equalsIgnoreCase("po") && item.getQuantity() != null) {
                            analysisDetailReport.setPoQuantity(item.getQuantity());
                        }else {
                            analysisDetailReport.setOnHandQuantity(0.0);
                            analysisDetailReport.setPoQuantity(0.0);
                        }
                    }
                }else {
                    analysisDetailReport.setOnHandQuantity(0.0);
                    analysisDetailReport.setPoQuantity(0.0);
                }

                analysisDetailReport.setNeededQuantity(analysisDetailReport.getExpectedQuantity() - analysisDetailReport.getOnHandQuantity());
                if (analysisDetailReport.getReqQuantity() == 0.0){
                    analysisDetailReport.setCompletionRate(0.0);
                }else analysisDetailReport.setCompletionRate((analysisDetailReport.getOnHandQuantity() / analysisDetailReport.getReqQuantity()) * 100);

                if (analysisDetailReport.getExpectedQuantity() == 0.0){
                    analysisDetailReport.setPurchaseRate(0.0);
                }else analysisDetailReport.setPurchaseRate((analysisDetailReport.getOnHandQuantity() / analysisDetailReport.getExpectedQuantity()) * 100);


                //tong hop chi tiet thong tin mua hang by mode bao cao
                if (filter.getAnalysisMode().equalsIgnoreCase("0")){
                    getDetailReportByDay(analysisDetailReport, filter, durationDetailHashmap.get(id));
                } else if (filter.getAnalysisMode().equalsIgnoreCase("1")){
                    getDetailReportByWeek(analysisDetailReport, filter, durationDetailHashmap.get(id));
                } else if (filter.getAnalysisMode().equalsIgnoreCase("2")){
                    getDetailReportByMonth(analysisDetailReport, filter, durationDetailHashmap.get(id));
                } else if (filter.getAnalysisMode().equalsIgnoreCase("3")) {
                    getDetailReportByQuarter(analysisDetailReport, filter, durationDetailHashmap.get(id));
                }

                detailReports.add(analysisDetailReport);
            }


        }

        return detailReports;
    }

    public void getDetailReportByDay(AnalysisDetailReport detailReport, AnalysisDetailReportFilter filter, List<OnOrderDurationReport> durationReportsById){
        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        AnalysisDetailReportByTimeModel detailReportByTimeModel;

        List<AnalysisDetailReportByTimeModel> AnalysisDetailReportByTimeModelList = new ArrayList<>();

        startTime.setTime(filter.getStartDate());
        endTime.setTime(filter.getEndDate());
        while (startTime.before(endTime) || simpleDateFormat.format(startTime.getTime()).equals(simpleDateFormat.format(endTime.getTime()))){
            detailReportByTimeModel = new AnalysisDetailReportByTimeModel();
//            System.err.println("durationReportsById: " + durationReportsById.size());
            for (OnOrderDurationReport item : durationReportsById){
//                System.err.println("item.getDueDate(): " + simpleDateFormat.format(item.getDueDate()));
//                System.err.println("startTime.getTime(): "+simpleDateFormat.format(startTime.getTime()));
//                System.err.println("item.getQuantity(): " + item.getQuantity());
                if (simpleDateFormat.format(item.getDueDate()).equals(simpleDateFormat.format(startTime.getTime()))){
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
        detailReport.setPurchaseDetail(AnalysisDetailReportByTimeModelList);
    }

    public void getDetailReportByWeek(AnalysisDetailReport detailReport, AnalysisDetailReportFilter filter, List<OnOrderDurationReport>durationReportsById){
        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        int countWeek = 0;
        AnalysisDetailReportByTimeModel detailReportByTimeModel;
        double quantity;
        int index = 0;

        List<AnalysisDetailReportByTimeModel> AnalysisDetailReportByTimeModelList = new ArrayList<>();

        startTime.setTime(filter.getStartDate());
        endTime.setTime(filter.getEndDate());
        if (startTime.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
            startTime.add(Calendar.DATE, 7 - startTime.get(Calendar.DAY_OF_WEEK) + 1);
        }
        if (endTime.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
            endTime.add(Calendar.DATE, 7 - endTime.get(Calendar.DAY_OF_WEEK) + 1);
        }

        System.err.println("durationReportsById: " + durationReportsById.size());
        System.err.println("durationReportsById.getId: " + durationReportsById.get(0).getId());
        while (startTime.before(endTime) || simpleDateFormat.format(startTime.getTime()).equals(simpleDateFormat.format(endTime.getTime()))){
            quantity = 0.0;
            detailReportByTimeModel = new AnalysisDetailReportByTimeModel();
//            System.err.println("i: " + i);
//            System.err.println("startTime.getTime(): "+simpleDateFormat.format(startTime.getTime()));
            for (; index < durationReportsById.size(); index++) {
//                System.err.println("item.getDueDate(): " + simpleDateFormat.format(durationReportsById.get(i).getDueDate()));
//                System.err.println("item.getQuantity(): " + durationReportsById.get(i).getQuantity());
                if (durationReportsById.get(index).getDueDate().before(startTime.getTime()) || simpleDateFormat.format(durationReportsById.get(index).getDueDate()).equals(simpleDateFormat.format(startTime.getTime()))) {
//                    System.err.println("item.getDueDate()1111: " + simpleDateFormat.format(durationReportsById.get(i).getDueDate()));
//                    System.err.println("item.getQuantity()1111: " + durationReportsById.get(i).getQuantity());
                    quantity += durationReportsById.get(index).getQuantity();
                }else break;
            }

            countWeek += 1;
            detailReportByTimeModel.setDueDate("Tuần " + countWeek);
            detailReportByTimeModel.setQuantity(quantity);
            AnalysisDetailReportByTimeModelList.add(detailReportByTimeModel);

            startTime.add(Calendar.DATE, 1);
            startTime.add(Calendar.DATE, 7 - startTime.get(Calendar.DAY_OF_WEEK) + 1);
        }
        detailReport.setPurchaseDetail(AnalysisDetailReportByTimeModelList);
    }

    public void getDetailReportByMonth(AnalysisDetailReport detailReport, AnalysisDetailReportFilter filter,List<OnOrderDurationReport> durationReportsById){
        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        int countMonth = 0;
        Calendar calCheck = Calendar.getInstance();
        AnalysisDetailReportByTimeModel detailReportByTimeModel;
        double quantity;
        int index = 0;

        List<AnalysisDetailReportByTimeModel> AnalysisDetailReportByTimeModelList = new ArrayList<>();

        startTime.setTime(filter.getStartDate());
        endTime.setTime(filter.getEndDate());
        endTime.set(Calendar.DATE, endTime.getActualMaximum(Calendar.DAY_OF_MONTH));
        startTime.set(Calendar.DATE, startTime.getActualMaximum(Calendar.DAY_OF_MONTH));
        calCheck.setTime(durationReportsById.get(0).getDueDate());
        while (startTime.before(endTime) || simpleDateFormat.format(startTime.getTime()).equals(simpleDateFormat.format(endTime.getTime()))){
            quantity = 0.0;
            detailReportByTimeModel = new AnalysisDetailReportByTimeModel();

            for (; index < durationReportsById.size(); index++) {
//                System.err.println("item.getDueDate(): " + simpleDateFormat.format(durationReportsById.get(i).getDueDate()));
//                System.err.println("item.getQuantity(): " + durationReportsById.get(i).getQuantity());
                if (durationReportsById.get(index).getDueDate().before(startTime.getTime()) || simpleDateFormat.format(durationReportsById.get(index).getDueDate()).equals(simpleDateFormat.format(startTime.getTime()))) {
//                    System.err.println("item.getDueDate()1111: " + simpleDateFormat.format(durationReportsById.get(i).getDueDate()));
//                    System.err.println("item.getQuantity()1111: " + durationReportsById.get(i).getQuantity());
                    quantity += durationReportsById.get(index).getQuantity();
                }else break;
            }

            countMonth += 1;
            detailReportByTimeModel.setDueDate("Tháng " + countMonth);
            detailReportByTimeModel.setQuantity(quantity);
            AnalysisDetailReportByTimeModelList.add(detailReportByTimeModel);

            startTime.add(Calendar.DATE, 1);
            startTime.set(Calendar.DATE, startTime.getActualMaximum(Calendar.DAY_OF_MONTH));

        }
        detailReport.setPurchaseDetail(AnalysisDetailReportByTimeModelList);
    }

    public void getDetailReportByQuarter(AnalysisDetailReport detailReport, AnalysisDetailReportFilter filter, List<OnOrderDurationReport> durationReportsById){
        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        int countQuarter = 0;
        Calendar calCheck = Calendar.getInstance();
        int index = 0;
        AnalysisDetailReportByTimeModel detailReportByTimeModel;
        double quantity;

        List<AnalysisDetailReportByTimeModel> AnalysisDetailReportByTimeModelList = new ArrayList<>();

        startTime.setTime(getLastDayOfQuarter(filter.getStartDate()));
        endTime.setTime(getLastDayOfQuarter(filter.getEndDate()));

        calCheck.setTime(durationReportsById.get(0).getDueDate());

        while (startTime.before(endTime) || simpleDateFormat.format(startTime.getTime()).equals(simpleDateFormat.format(endTime.getTime()))){
            quantity = 0.0;
            detailReportByTimeModel = new AnalysisDetailReportByTimeModel();

            for (; index < durationReportsById.size(); index++) {
//                System.err.println("item.getDueDate(): " + simpleDateFormat.format(durationReportsById.get(i).getDueDate()));
//                System.err.println("item.getQuantity(): " + durationReportsById.get(i).getQuantity());
                if (durationReportsById.get(index).getDueDate().before(startTime.getTime()) || simpleDateFormat.format(durationReportsById.get(index).getDueDate()).equals(simpleDateFormat.format(startTime.getTime()))) {
//                    System.err.println("item.getDueDate()1111: " + simpleDateFormat.format(durationReportsById.get(i).getDueDate()));
//                    System.err.println("item.getQuantity()1111: " + durationReportsById.get(i).getQuantity());
                    quantity += durationReportsById.get(index).getQuantity();
                }else break;
            }
            countQuarter += 1;
            detailReportByTimeModel.setDueDate("Quý " + countQuarter);
            detailReportByTimeModel.setQuantity(quantity);
            AnalysisDetailReportByTimeModelList.add(detailReportByTimeModel);

            startTime.add(Calendar.DATE, 1);
            startTime.setTime(getLastDayOfQuarter(startTime.getTime()));

        }
        detailReport.setPurchaseDetail(AnalysisDetailReportByTimeModelList);
    }

    public static Date getLastDayOfQuarter(Date date) {
        int quarter = whichQuarter(date);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, quarter + 2);
        int maximum = cal.getMaximum(Calendar.DAY_OF_MONTH);
        System.err.println(simpleDateFormat.format(cal.getTime()));
        cal.set(Calendar.DAY_OF_MONTH, maximum);
        System.err.println(maximum);
        date = cal.getTime();
        return date;
    }

    public static int whichQuarter(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int quarter = 0;
        switch (month) {
            case 0:
            case 1:
            case 2:
                quarter = 0;
                break;
            case 3:
            case 4:
            case 5:
                quarter = 3;
                break;
            case 6:
            case 7:
            case 8:
                quarter = 6;
                break;
            case 9:
            case 10:
            case 11:
                quarter = 9;
                break;
        }
        return quarter;
    }
}
