package com.facenet.mrp.service.model;

import java.util.Date;

public class ComparisonReportFilter {
    private Integer analysisMode;
    private Date startDate;
    private Date endDate;

    public Integer getAnalysisMode() {
        return analysisMode;
    }

    public void setAnalysisMode(Integer analysisMode) {
        this.analysisMode = analysisMode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
