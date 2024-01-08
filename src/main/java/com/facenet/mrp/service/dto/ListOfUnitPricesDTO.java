package com.facenet.mrp.service.dto;

import com.querydsl.core.annotations.QueryProjection;

import java.util.Date;


public class ListOfUnitPricesDTO {

    private String productCode;
    private String productDescription;
    private String group;
    private String vendorCode;
    private String vendorName;
    private String unit;
    private Integer leadTime;
    private Double unitPrice;
    private Integer minQuantity;
    private Integer maxQuantity;
    private String transactionMoney;
    private Date billDate;
    private String status;

    public ListOfUnitPricesDTO() {
    }

    @QueryProjection
    public ListOfUnitPricesDTO(String productCode, String productDescription, String group, String vendorCode, String vendorName, String unit) {
        this.productCode = productCode;
        this.productDescription = productDescription;
        this.group = group;
        this.vendorCode = vendorCode;
        this.vendorName = vendorName;
        this.unit = unit;
    }

    @QueryProjection
    public ListOfUnitPricesDTO(String productCode, String productDescription, String vendorCode, String vendorName) {
        this.productCode = productCode;
        this.productDescription = productDescription;
        this.vendorCode = vendorCode;
        this.vendorName = vendorName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(Integer leadTime) {
        this.leadTime = leadTime;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Integer getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Integer maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public String getTransactionMoney() {
        return transactionMoney;
    }

    public void setTransactionMoney(String transactionMoney) {
        this.transactionMoney = transactionMoney;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
