package com.facenet.mrp.service.dto;

import java.util.Date;

public class DataItemInVendor {
    private String productCode;

    private String productDescription;

    private String productTechName;

    private String group;

    private String groupName;

    private long totalInventory;

    private String unit;

    private String productType;

    private Integer leadTime;

    private Double priceMQQ;

    private Date dueDate;

    private String status;

    private String currency;
    private String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public String getProductTechName() {
        return productTechName;
    }

    public void setProductTechName(String productTechName) {
        this.productTechName = productTechName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public long getTotalInventory() {
        return totalInventory;
    }

    public void setTotalInventory(long totalInventory) {
        this.totalInventory = totalInventory;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Integer getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(Integer leadTime) {
        this.leadTime = leadTime;
    }

    public Double getPriceMQQ() {
        return priceMQQ;
    }

    public void setPriceMQQ(Double priceMQQ) {
        this.priceMQQ = priceMQQ;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public DataItemInVendor() {
    }

    public DataItemInVendor(String productCode, String productDescription, String productTechName, String group, String groupName, long totalInventory, String unit, String productType, Integer leadTime, Double priceMQQ, Date dueDate, String status,String currency) {
        this.productCode = productCode;
        this.productDescription = productDescription;
        this.productTechName = productTechName;
        this.group = group;
        this.groupName = groupName;
        this.totalInventory = totalInventory;
        this.unit = unit;
        this.productType = productType;
        this.leadTime = leadTime;
        this.priceMQQ = priceMQQ;
        this.dueDate = dueDate;
        this.status = status;
        this.currency = currency;
    }
}
