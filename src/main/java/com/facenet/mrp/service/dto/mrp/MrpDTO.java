package com.facenet.mrp.service.dto.mrp;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MrpDTO {
    private String sessionId;
    private String mrpCode;
    private String mrpSubCode;
    private String soCode;
    private String mrpDescription;
    private Date timeStart;
    private Date timeEnd;
    private String analysisPeriod;
    private Integer analysisType;
    private Date analysisDate;
    private String analysisMode;
    private List<Integer> listItemCode;
    private List<String> analysisWhs;
    private String sortType;
    private List<String> warehouseAnalysis;
    private int countData;
    private List<MrpDetailDTO> resultData;

    public List<String> getAnalysisWhs() {
        return analysisWhs;
    }

    public void setAnalysisWhs(List<String> analysisWhs) {
        this.analysisWhs = analysisWhs;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public List<String> getWarehouseAnalysis() {
        return warehouseAnalysis;
    }

    public void setWarehouseAnalysis(List<String> warehouseAnalysis) {
        this.warehouseAnalysis = warehouseAnalysis;
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



    public Date getAnalysisDate() {
        return analysisDate;
    }

    public void setAnalysisDate(Date analysisDate) {
        this.analysisDate = analysisDate;
    }


    public List<MrpDetailDTO> getResultData() {
        return resultData;
    }

    public void setResultData(List<MrpDetailDTO> resultData) {
        this.resultData = resultData;
    }

    public String getAnalysisMode() {
        return analysisMode;
    }

    public void setAnalysisMode(String analysisMode) {
        this.analysisMode = analysisMode;
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

    public List<Integer> getListItemCode() {
        return listItemCode;
    }

    public void setListItemCode(List<Integer> listItemCode) {
        this.listItemCode = listItemCode;
    }

    public int getCountData() {
        return countData;
    }

    public void setCountData(int countData) {
        this.countData = countData;
    }
}
