package com.facenet.mrp.service.dto.mrp;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
public class ForecastDTO {
    private Integer id;
    private String fcCode;
    private String fcName;
    private Date timeStart;
    private Date timeEnd;
    private String fcMode;
    private String warehouse;
    private String fcSource;
    private Integer priority;
    private String note;
    private Integer status;
    private String createdBy;
    private Timestamp createdAt;
    private String updatedBy;
    private Timestamp updatedAt;
    private Integer parentFc;
    private String result;
    private Integer isActive;
    private ForecastDTO subForecast;

    public ForecastDTO getSubForecast() {
        return subForecast;
    }

    public void setSubForecast(ForecastDTO subForecast) {
        this.subForecast = subForecast;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFcCode() {
        return fcCode;
    }

    public void setFcCode(String fcCode) {
        this.fcCode = fcCode;
    }

    public String getFcName() {
        return fcName;
    }

    public void setFcName(String fcName) {
        this.fcName = fcName;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getFcMode() {
        return fcMode;
    }

    public void setFcMode(String fcMode) {
        this.fcMode = fcMode;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getFcSource() {
        return fcSource;
    }

    public void setFcSource(String fcSource) {
        this.fcSource = fcSource;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getParentFc() {
        return parentFc;
    }

    public void setParentFc(Integer parentFc) {
        this.parentFc = parentFc;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    @QueryProjection
    public ForecastDTO(Integer id, String fcCode, String fcName, Date timeStart, Date timeEnd, String fcMode, String warehouse, String fcSource, Integer priority, String note, Integer status, String createdBy, Timestamp createdAt, String updatedBy, Timestamp updatedAt, Integer parentFc) {
        this.id = id;
        this.fcCode = fcCode;
        this.fcName = fcName;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.fcMode = fcMode;
        this.warehouse = warehouse;
        this.fcSource = fcSource;
        this.priority = priority;
        this.note = note;
        this.status = status;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
        this.parentFc = parentFc;
    }
}
