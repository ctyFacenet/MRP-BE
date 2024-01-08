package com.facenet.mrp.service.dto.mrp;

import java.util.Date;

public class AnalysisDetailReportByTimeModel {


    private String dueDate;

    private Double quantity;

    public AnalysisDetailReportByTimeModel() {
    }

    public AnalysisDetailReportByTimeModel(String dueDate, Double quantity) {
        this.dueDate = dueDate;
        this.quantity = quantity;
    }


    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}
