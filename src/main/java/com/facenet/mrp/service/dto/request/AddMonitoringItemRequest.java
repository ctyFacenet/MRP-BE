package com.facenet.mrp.service.dto.request;

import com.facenet.mrp.service.dto.mrp.ItemInPlanDurationWithPoDTO;

import java.util.Date;
import java.util.List;

public class AddMonitoringItemRequest {
    private Date dueDate;
    private Double quantity;
    private String note;
    private List<ItemInPlanDurationWithPoDTO> data;

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

    public List<ItemInPlanDurationWithPoDTO> getData() {
        return data;
    }

    public void setData(List<ItemInPlanDurationWithPoDTO> data) {
        this.data = data;
    }
}
