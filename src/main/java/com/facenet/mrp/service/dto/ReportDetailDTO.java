package com.facenet.mrp.service.dto;

import com.facenet.mrp.service.dto.mrp.AnalysisDetailReportByTimeModel;

import java.util.List;

public class ReportDetailDTO {

    private String mrpCode;
    private String itemCode;
    private String itemName;
    private String vendorCode;
    private String vendorName;
    private Double totalRequiredQuantity;
    private Double totalExpectedQuantity;
    private Double totalGRPO;
    private Double totalPO;
    private Double completionRate;
    private Double purchasingProgress;
    private List<AnalysisDetailReportByTimeModel> resultSAP;
    private List<AnalysisDetailReportByTimeModel> resultMrp;

    public ReportDetailDTO() {
    }

    public ReportDetailDTO(String mrpCode, String itemCode, String itemName, Double totalRequiredQuantity) {
        this.mrpCode = mrpCode;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.totalRequiredQuantity = totalRequiredQuantity;
    }

    public String getMrpCode() {
        return mrpCode;
    }

    public void setMrpCode(String mrpCode) {
        this.mrpCode = mrpCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Double getTotalRequiredQuantity() {
        return totalRequiredQuantity;
    }

    public void setTotalRequiredQuantity(Double totalRequiredQuantity) {
        this.totalRequiredQuantity = totalRequiredQuantity;
    }

    public Double getTotalExpectedQuantity() {
        return totalExpectedQuantity;
    }

    public void setTotalExpectedQuantity(Double totalExpectedQuantity) {
        this.totalExpectedQuantity = totalExpectedQuantity;
    }

    public Double getTotalGRPO() {
        return totalGRPO;
    }

    public void setTotalGRPO(Double totalGRPO) {
        this.totalGRPO = totalGRPO;
    }

    public Double getTotalPO() {
        return totalPO;
    }

    public void setTotalPO(Double totalPO) {
        this.totalPO = totalPO;
    }

    public Double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(Double completionRate) {
        this.completionRate = completionRate;
    }

    public Double getPurchasingProgress() {
        return purchasingProgress;
    }

    public void setPurchasingProgress(Double purchasingProgress) {
        this.purchasingProgress = purchasingProgress;
    }

    public List<AnalysisDetailReportByTimeModel> getResultSAP() {
        return resultSAP;
    }

    public void setResultSAP(List<AnalysisDetailReportByTimeModel> resultSAP) {
        this.resultSAP = resultSAP;
    }

    public List<AnalysisDetailReportByTimeModel> getResultMrp() {
        return resultMrp;
    }

    public void setResultMrp(List<AnalysisDetailReportByTimeModel> resultMrp) {
        this.resultMrp = resultMrp;
    }
}
