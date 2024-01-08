package com.facenet.mrp.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Timestamp;
import java.util.Date;

public class ListDetailMrpDTO {
    private String mrpSubCode;

    private String mrpCode;

    private String soCode;

    private String mrpDescription;

    private String sortType;

    private Date timeStart;

    private Date timeEnd;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Timestamp createdTime;

    private String createdBy;

    private String analysisType;
    private Integer analysisKind;
    private String items;
    private String warehouseAnalysis;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Byte status;

    private String analysisSource;

    private String analysisMode;

    private Date lastAccess;

    public String getMrpSubCode() {
        return mrpSubCode;
    }

    public void setMrpSubCode(String mrpSubCode) {
        this.mrpSubCode = mrpSubCode;
    }

    public String getMrpCode() {
        return mrpCode;
    }

    public void setMrpCode(String mrpCode) {
        this.mrpCode = mrpCode;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getMrpDescription() {
        return mrpDescription;
    }

    public void setMrpDescription(String mrpDescription) {
        this.mrpDescription = mrpDescription;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(String analysisType) {
        this.analysisType = analysisType;
    }

    public ListDetailMrpDTO() {
    }

    public ListDetailMrpDTO(String mrpSubCode, String mrpCode, String soCode, String mrpDescription, String sortType, Date timeStart, Date timeEnd, String analysisType, Integer analysisKind, String analysisSource, String items, String warehouseAnalysis, String analysisMode, Date lastAccess, Byte status) {
        this.mrpSubCode = mrpSubCode;
        this.mrpCode = mrpCode;
        this.soCode = soCode;
        this.mrpDescription = mrpDescription;
        this.sortType = sortType;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.analysisType = analysisType;
        this.analysisKind = analysisKind;
        this.analysisSource = analysisSource;
        this.items = items;
        this.warehouseAnalysis = warehouseAnalysis;
        this.analysisMode = analysisMode;
        this.lastAccess = lastAccess;
        this.status = status;
    }

    public String getAnalysisMode() {
        return analysisMode;
    }

    public void setAnalysisMode(String analysisMode) {
        this.analysisMode = analysisMode;
    }

    public String getAnalysisSource() {
        return analysisSource;
    }

    public void setAnalysisSource(String analysisSource) {
        this.analysisSource = analysisSource;
    }

    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    public Integer getAnalysisKind() {
        return analysisKind;
    }

    public void setAnalysisKind(Integer analysisKind) {
        this.analysisKind = analysisKind;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getWarehouseAnalysis() {
        return warehouseAnalysis;
    }

    public void setWarehouseAnalysis(String warehouseAnalysis) {
        this.warehouseAnalysis = warehouseAnalysis;
    }
}
