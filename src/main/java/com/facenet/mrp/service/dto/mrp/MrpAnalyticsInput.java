package com.facenet.mrp.service.dto.mrp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class MrpAnalyticsInput {
    private String sessionId;
    private String soCode;
    private String mrpCode;
    private String mrpSubCode;
    private String mrpDescription;
    private Date timeStart;
    private Date timeEnd;
    private String analysisPeriod;
    private String sortedBy;
    private String analysisMode;
    private Integer analysisType;
    private String type;
    private Integer materialPreparationTime;
    private List<String> analysisOption;
    @JsonProperty("listItemCode")
    private List<Integer> listItemId;
    private List<String> listAnalysisWhs;

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

    public String getAnalysisPeriod() {
        return analysisPeriod;
    }

    public void setAnalysisPeriod(String analysisPeriod) {
        this.analysisPeriod = analysisPeriod;
    }

    public String getSortedBy() {
        return sortedBy;
    }

    public void setSortedBy(String sortedBy) {
        this.sortedBy = sortedBy;
    }

    public String getAnalysisMode() {
        return analysisMode;
    }

    public void setAnalysisMode(String analysisMode) {
        this.analysisMode = analysisMode;
    }

    public List<String> getAnalysisOption() {
        return analysisOption;
    }

    public void setAnalysisOption(List<String> analysisOption) {
        this.analysisOption = analysisOption;
    }

    public List<Integer> getListItemId() {
        return listItemId;
    }

    public void setListItemId(List<Integer> listItemCode) {
        this.listItemId = listItemCode;
    }

    public List<String> getListAnalysisWhs() {
        return listAnalysisWhs;
    }

    public void setListAnalysisWhs(List<String> listAnalysisWhs) {
        this.listAnalysisWhs = listAnalysisWhs;
    }

    public String getMrpCode() {
        return mrpCode;
    }

    public void setMrpCode(String mrpCode) {
        this.mrpCode = mrpCode;
    }

    public String getMrpSubCode() {
        return mrpSubCode;
    }

    public void setMrpSubCode(String mrpSubCode) {
        this.mrpSubCode = mrpSubCode;
    }

    public Integer getMaterialPreparationTime() {
        return materialPreparationTime;
    }

    public void setMaterialPreparationTime(Integer materialPreparationTime) {
        this.materialPreparationTime = materialPreparationTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(Integer analysisType) {
        this.analysisType = analysisType;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
