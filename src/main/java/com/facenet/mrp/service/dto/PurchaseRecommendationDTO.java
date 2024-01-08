package com.facenet.mrp.service.dto;

import com.querydsl.core.annotations.QueryProjection;

import java.time.Instant;
import java.util.Date;

public class PurchaseRecommendationDTO {
    private Integer purchaseRecommendationId;
    private String soCode;
    private String mrpSubCode;
    private Date startTime;
    private Date endTime;
    private Integer totalItems;
    private Integer priority;
    private String type;
    private String note;
    private Instant createdAt;
    private String createdBy;
    private Integer status;
    private Integer batch;

    public PurchaseRecommendationDTO() {
    }

    @QueryProjection
    public PurchaseRecommendationDTO(Integer purchaseRecommendationId, String soCode, String mrpSubCode, Date startTime, Date endTime, Integer totalItems, Integer priority, String type, String note, Instant createdAt, String createdBy, Integer status) {
        this.soCode = soCode;
        this.purchaseRecommendationId = purchaseRecommendationId;
        this.mrpSubCode = mrpSubCode;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalItems = totalItems;
        this.priority = priority;
        this.type = type;
        this.note = note;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.status = status;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getMrpSubCode() {
        return mrpSubCode;
    }

    public void setMrpSubCode(String mrpSubCode) {
        this.mrpSubCode = mrpSubCode;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPurchaseRecommendationId() {
        return purchaseRecommendationId;
    }

    public void setPurchaseRecommendationId(Integer purchaseRecommendationId) {
        this.purchaseRecommendationId = purchaseRecommendationId;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }
}
