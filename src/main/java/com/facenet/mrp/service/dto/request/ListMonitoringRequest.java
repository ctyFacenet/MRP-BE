package com.facenet.mrp.service.dto.request;

import java.util.Date;
import java.util.List;

public class ListMonitoringRequest {
    private String prCode;
    private String poCode;
    private Date dueDate;
    private Double quantity;
    private String note;
    private List<ListInternalRequest> poList;

    public String getPrCode() {
        return prCode;
    }

    public void setPrCode(String prCode) {
        this.prCode = prCode;
    }

    public String getPoCode() {
        return poCode;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<ListInternalRequest> getPoList() {
        return poList;
    }

    public void setPoList(List<ListInternalRequest> poList) {
        this.poList = poList;
    }
}
