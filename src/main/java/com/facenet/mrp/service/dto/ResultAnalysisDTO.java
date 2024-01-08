package com.facenet.mrp.service.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class ResultAnalysisDTO {
    private String mrpCode;
    private String soCode;
    private String mrpDescription;
    private String mrpSubCode;
    private Date timeStart;
    private Date timeEnd;
    private String sortedBy;
    private String analysisSource;
    private String analysisWhs;
    private Timestamp analysisDate;
    private String analysisBy;
    private ArrayList<ItemAnalysisDTO> resultData;

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

    public String getSortedBy() {
        return sortedBy;
    }

    public void setSortedBy(String sortedBy) {
        this.sortedBy = sortedBy;
    }

    public String getAnalysisSource() {
        return analysisSource;
    }

    public void setAnalysisSource(String analysisSource) {
        this.analysisSource = analysisSource;
    }

    public String getAnalysisWhs() {
        return analysisWhs;
    }

    public void setAnalysisWhs(String analysisWhs) {
        this.analysisWhs = analysisWhs;
    }

    public Timestamp getAnalysisDate() {
        return analysisDate;
    }

    public void setAnalysisDate(Timestamp analysisDate) {
        this.analysisDate = analysisDate;
    }

    public String getAnalysisBy() {
        return analysisBy;
    }

    public void setAnalysisBy(String analysisBy) {
        this.analysisBy = analysisBy;
    }

    public ArrayList<ItemAnalysisDTO> getResultData() {
        return resultData;
    }

    public void setResultData(ArrayList<ItemAnalysisDTO> resultData) {
        this.resultData = resultData;
    }

    public String getMrpSubCode() {
        return mrpSubCode;
    }

    public void setMrpSubCode(String mrpSubCode) {
        this.mrpSubCode = mrpSubCode;
    }

    public ResultAnalysisDTO() {
    }

    public ResultAnalysisDTO(String mrpCode, String soCode, String mrpDescription, String mrpSubCode, Date timeStart, Date timeEnd, String sortedBy, String analysisSource, String analysisWhs, Timestamp analysisDate, String analysisBy, ArrayList<ItemAnalysisDTO> resultData) {
        this.mrpCode = mrpCode;
        this.soCode = soCode;
        this.mrpDescription = mrpDescription;
        this.mrpSubCode = mrpSubCode;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.sortedBy = sortedBy;
        this.analysisSource = analysisSource;
        this.analysisWhs = analysisWhs;
        this.analysisDate = analysisDate;
        this.analysisBy = analysisBy;
        this.resultData = resultData;
    }
}
