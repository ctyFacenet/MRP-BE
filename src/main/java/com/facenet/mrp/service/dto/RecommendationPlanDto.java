package com.facenet.mrp.service.dto;

import java.util.Date;

public class RecommendationPlanDto {
    private String mrpSub;
    private int recommendationPurchasePlanId;
    private String itemCode;
    private double requiredQuantity;
    private Date requiredDueDate;
    private String note;
    private Integer status;
    private Integer batch;
    private Boolean checked;

    public RecommendationPlanDto() {
    }

    public RecommendationPlanDto(String mrpSub, int recommendationPurchasePlanId, String itemCode, double requiredQuantity, Date requiredDueDate, String note, Integer status, Integer batch) {
        this.mrpSub = mrpSub;
        this.recommendationPurchasePlanId = recommendationPurchasePlanId;
        this.itemCode = itemCode;
        this.requiredQuantity = requiredQuantity;
        this.requiredDueDate = requiredDueDate;
        this.note = note;
        this.status = status;
        this.batch = batch;
    }

    public double getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(double requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public Date getRequiredDueDate() {
        return requiredDueDate;
    }

    public void setRequiredDueDate(Date requiredDueDate) {
        this.requiredDueDate = requiredDueDate;
    }

    public int getRecommendationPurchasePlanId() {
        return recommendationPurchasePlanId;
    }

    public void setRecommendationPurchasePlanId(int recommendationPurchasePlanId) {
        this.recommendationPurchasePlanId = recommendationPurchasePlanId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getMrpSub() {
        return mrpSub;
    }

    public void setMrpSubCode(String mrpSubCode) {
        this.mrpSub = mrpSubCode;
    }
}
