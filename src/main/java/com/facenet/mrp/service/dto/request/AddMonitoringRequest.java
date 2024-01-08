package com.facenet.mrp.service.dto.request;

import java.util.Date;
import java.util.List;

public class AddMonitoringRequest {

    private Date dueDate;
    private Double quantity;
    private String note;
    private List<InternalRequest> poList;

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

    public List<InternalRequest> getPoList() {
        return poList;
    }

    public void setPoList(List<InternalRequest> poList) {
        this.poList = poList;
    }
}
