package com.facenet.mrp.service.dto.mrp;

import com.querydsl.core.annotations.QueryProjection;

import java.util.Date;
import java.util.List;

public class AnalysisDetailReport{

    private Integer id;
    private String soCode;
    private String mrpCode;
    private String productCode;
    private String productName;
    private String vendorCode;
    private String vendorName;
    private double reqQuantity;
    private double expectedQuantity;
    private double onHandQuantity;
    private double neededQuantity;
    private double poQuantity;
    private double completionRate;
    private double purchaseRate;
    private List<AnalysisDetailReportByTimeModel> purchaseDetail;
    private Date dueDate;
    private String note;
    private List<MonitoringDetailDTO> durationDetail;

    @QueryProjection
    public AnalysisDetailReport(Integer id, String soCode, String mrpCode, String productCode, String productName, String vendorCode, String vendorName, Date dueDate) {
        this.id = id;
        this.soCode = soCode;
        this.mrpCode = mrpCode;
        this.productCode = productCode;
        this.productName = productName;
        this.vendorCode = vendorCode;
        this.vendorName = vendorName;
        this.dueDate = dueDate;
    }

    public AnalysisDetailReport() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getMrpCode() {
        return mrpCode;
    }

    public void setMrpCode(String mrpCode) {
        this.mrpCode = mrpCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public double getReqQuantity() {
        return reqQuantity;
    }

    public void setReqQuantity(double reqQuantity) {
        this.reqQuantity = reqQuantity;
    }

    public double getExpectedQuantity() {
        return expectedQuantity;
    }

    public void setExpectedQuantity(double expectedQuantity) {
        this.expectedQuantity = expectedQuantity;
    }

    public double getOnHandQuantity() {
        return onHandQuantity;
    }

    public void setOnHandQuantity(double onHandQuantity) {
        this.onHandQuantity = onHandQuantity;
    }

    public double getNeededQuantity() {
        return neededQuantity;
    }

    public void setNeededQuantity(double neededQuantity) {
        this.neededQuantity = neededQuantity;
    }

    public double getPoQuantity() {
        return poQuantity;
    }

    public void setPoQuantity(double poQuantity) {
        this.poQuantity = poQuantity;
    }

    public double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(double completionRate) {
        this.completionRate = completionRate;
    }

    public double getPurchaseRate() {
        return purchaseRate;
    }

    public void setPurchaseRate(double purchaseRate) {
        this.purchaseRate = purchaseRate;
    }

    public List<AnalysisDetailReportByTimeModel> getPurchaseDetail() {
        return purchaseDetail;
    }


    public void setPurchaseDetail(List<AnalysisDetailReportByTimeModel> purchaseDetail) {
        this.purchaseDetail = purchaseDetail;
    }
    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<MonitoringDetailDTO> getDurationDetail() {
        return durationDetail;
    }

    public void setDurationDetail(List<MonitoringDetailDTO> durationDetail) {
        this.durationDetail = durationDetail;
    }
}
