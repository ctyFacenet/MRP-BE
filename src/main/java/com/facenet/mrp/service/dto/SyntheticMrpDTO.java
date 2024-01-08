package com.facenet.mrp.service.dto;

import java.util.Date;
import java.util.List;

public class SyntheticMrpDTO {
    private String sessionId;
    private String mrpCode;
    private String mrpSubCode;
    private String mrpDescription;
    private String soCode;
    private Date timeStart;
    private Date timeEnd;
    private String analysisPeriod;
    private Date createdAt;
    private List<String> warehouseAnalysis;
    private List<ItemSyntheticDTO> resultData;

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

    public String getMrpDescription() {
        return mrpDescription;
    }

    public void setMrpDescription(String mrpDescription) {
        this.mrpDescription = mrpDescription;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<ItemSyntheticDTO> getResultData() {
        return resultData;
    }

    public void setResultData(List<ItemSyntheticDTO> resultData) {
        this.resultData = resultData;
    }

    public List<String> getWarehouseAnalysis() {
        return warehouseAnalysis;
    }

    public void setWarehouseAnalysis(List<String> warehouseAnalysis) {
        this.warehouseAnalysis = warehouseAnalysis;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
