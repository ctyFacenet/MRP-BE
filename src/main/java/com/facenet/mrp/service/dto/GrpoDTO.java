package com.facenet.mrp.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;
import java.util.Date;

public class GrpoDTO {

    private String receiptId;

    private String soId;

    private String poId;

    private String prId;

    private String grpoId;

    private String productId;

    private String mrpCode;

    private String description;

    private String vendorCode;

    private String vendor;

    private String status;

    private String techName;

    private Date wareHouseDate;

    private String wareHouser;

    private String taxType;

    private String tax;

    private long deliveredNum;

    private long moneyBill;

    private long price;
    private String currency;
    private Integer lineNumber;

    private java.sql.Date createdAt;

    @JsonIgnore
    private LocalDate sapUpdatedAt;

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public String getSoId() {
        return soId;
    }

    public void setSoId(String soId) {
        this.soId = soId;
    }

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public String getPrId() {
        return prId;
    }

    public void setPrId(String prId) {
        this.prId = prId;
    }

    public String getGrpoId() {
        return grpoId;
    }

    public void setGrpoId(String grpoId) {
        this.grpoId = grpoId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getMrpCode() {
        return mrpCode;
    }

    public void setMrpCode(String mrpCode) {
        this.mrpCode = mrpCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTechName() {
        return techName;
    }

    public void setTechName(String techName) {
        this.techName = techName;
    }

    public Date getWareHouseDate() {
        return wareHouseDate;
    }

    public void setWareHouseDate(Date wareHouseDate) {
        this.wareHouseDate = wareHouseDate;
    }

    public String getWareHouser() {
        return wareHouser;
    }

    public void setWareHouser(String wareHouser) {
        this.wareHouser = wareHouser;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public long getDeliveredNum() {
        return deliveredNum;
    }

    public void setDeliveredNum(long deliveredNum) {
        this.deliveredNum = deliveredNum;
    }

    public long getMoneyBill() {
        return moneyBill;
    }

    public void setMoneyBill(long moneyBill) {
        this.moneyBill = moneyBill;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public java.sql.Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.sql.Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public GrpoDTO(){

    }

    @QueryProjection
    public GrpoDTO(String currency,String receiptId,
                   String soId,
                   String poId,
                   String prId,
                   String grpoId,
                   String productId,
                   String mrpCode,
                   String description,
                   String vendorCode,
                   String vendor,
                   String status,
                   String techName,
                   Date wareHouseDate,
                   String taxType, String tax, long deliveredNum, long moneyBill, long price,
                   Integer lineNumber) {
        this.currency = currency;
        this.receiptId = receiptId;
        this.soId = soId;
        this.poId = poId;
        this.prId = prId;
        this.grpoId = grpoId;
        this.productId = productId;
        this.mrpCode = mrpCode;
        this.description = description;
        this.vendorCode = vendorCode;
        this.vendor = vendor;
        this.status = status;
        this.techName = techName;
        this.wareHouseDate = wareHouseDate;
        //this.wareHouser = wareHouser;
        this.taxType = taxType;
        this.tax = tax;
        this.deliveredNum = deliveredNum;
        this.moneyBill = moneyBill;
        this.price = price;
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return "ReceiptDTO{" +
            "vendor=" + vendor +
            ", soId='" + soId + '\'' +
            ", productId='" + productId + '\'' +
            ", description=" + description +
            ", grpoId='" + grpoId + '\'' +
            ", prId=" + prId +
            ", receiptId='" + receiptId + '\'' +
            ", status='" + status + '\'' +
            ", poId='" + poId + '\'' +
            ", wareHouseDate='" + wareHouseDate + '\'' +
            '}';
    }

    public LocalDate getSapUpdatedAt() {
        return sapUpdatedAt;
    }

    public void setSapUpdatedAt(LocalDate sapUpdatedAt) {
        this.sapUpdatedAt = sapUpdatedAt;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }
}
