package com.facenet.mrp.service.dto.mrp;

import java.util.Date;

public class MonitoringDetailDTO implements Comparable<MonitoringDetailDTO>{

    public MonitoringDetailDTO(Integer id,Date dueDate, Double quantity, String note) {
        this.id = id;
        this.dueDate = dueDate;
        this.quantity = quantity;
        this.note = note;
    }

    public MonitoringDetailDTO(Date dueDate, Double quantity, String note) {
        this.dueDate = dueDate;
        this.quantity = quantity;
        this.note = note;
    }

    public MonitoringDetailDTO(Integer id, Date dueDate, Double quantity, String note, String poCode) {
        this.id = id;
        this.dueDate = dueDate;
        this.quantity = quantity;
        this.note = note;
        this.poCode = poCode;
    }

    public MonitoringDetailDTO() {
    }

    private Integer id;

    private Date dueDate;

    private Double quantity;

    private String note;

    private String poCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPoCode() {
        return poCode;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    @Override
    public int compareTo( MonitoringDetailDTO o) {
        return this.getDueDate().compareTo(o.getDueDate());
    }
}
