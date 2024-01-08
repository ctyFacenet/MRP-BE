package com.facenet.mrp.service.dto;

import com.querydsl.core.annotations.QueryProjection;

import java.time.Instant;
import java.util.Date;


public class ForecastOrderDTO {
    private String forecastOrderCode;
    private String forecastOrderName;
    private Date startTime;
    private Date endTime;
    private String forecastMode;
    private String warehouse;
    private String source;
    private Integer priority;
    private String note;
    private Integer status;
    private String createdBy;
    private Instant createdAt;

    public ForecastOrderDTO() {
    }

    @QueryProjection
    public ForecastOrderDTO(String forecastOrderCode, String forecastOrderName, Date startTime, Date endTime, String forecastMode, String warehouse, String source, Integer priority, String note, Integer status, String createdBy, Instant createdAt) {
        this.forecastOrderCode = forecastOrderCode;
        this.forecastOrderName = forecastOrderName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.forecastMode = forecastMode;
        this.warehouse = warehouse;
        this.source = source;
        this.priority = priority;
        this.note = note;
        this.status = status;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public String getForecastOrderCode() {
        return forecastOrderCode;
    }

    public void setForecastOrderCode(String forecastOrderCode) {
        this.forecastOrderCode = forecastOrderCode;
    }

    public String getForecastOrderName() {
        return forecastOrderName;
    }

    public void setForecastOrderName(String forecastOrderName) {
        this.forecastOrderName = forecastOrderName;
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

    public String getForecastMode() {
        return forecastMode;
    }

    public void setForecastMode(String forecastMode) {
        this.forecastMode = forecastMode;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
