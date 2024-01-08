package com.facenet.mrp.service.dto;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

public class PurchaseRecommendationDetailDTO implements Serializable {
    private String itemCode;
    private String itemDescription;
    private Double quantity;
    private Date receiveDate;
    private String vendorCode;
    private String vendorName;
    private Double price;
    private String currency;
    private Boolean isPromotion;
    private Date endTime;
    private Integer status;
    private String note;
    private Instant createdAt;
    private Double sumRequestQuantity;
    private Double sumPrQuantity;
    private String techName;
    private String unit;
    private Integer groupType;
    private String groupName;
    private String group;

    public PurchaseRecommendationDetailDTO() {
    }

    @QueryProjection
    public PurchaseRecommendationDetailDTO(String itemCode, String itemDescription, Double quantity, Date receiveDate, String vendorCode, String vendorName, Double price, String currency, Boolean isPromotion, Date endTime, Integer status, String note, Instant createdAt) {
        this.itemCode = itemCode;
        this.itemDescription = itemDescription;
        this.quantity = quantity;
        this.receiveDate = receiveDate;
        this.vendorCode = vendorCode;
        this.vendorName = vendorName;
        this.price = price;
        this.currency = currency;
        this.isPromotion = isPromotion;
        this.endTime = endTime;
        this.status = status;
        this.note = note;
        this.createdAt = createdAt;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getPromotion() {
        return isPromotion;
    }

    public void setPromotion(Boolean promotion) {
        isPromotion = promotion;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getSumRequestQuantity() {
        return sumRequestQuantity;
    }

    public void setSumRequestQuantity(Double sumRequestQuantity) {
        this.sumRequestQuantity = sumRequestQuantity;
    }

    public Double getSumPrQuantity() {
        return sumPrQuantity;
    }

    public void setSumPrQuantity(Double sumPrQuantity) {
        this.sumPrQuantity = sumPrQuantity;
    }

    public String getTechName() {
        return techName;
    }

    public String getUnit() {
        return unit;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setTechName(String techName) {
        this.techName = techName;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
