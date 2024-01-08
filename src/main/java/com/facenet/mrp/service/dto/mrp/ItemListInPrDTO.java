package com.facenet.mrp.service.dto.mrp;

import com.querydsl.core.annotations.QueryProjection;

import java.util.Date;
import java.util.List;

public class ItemListInPrDTO{
    private Integer id;
    private String prCode;
    private String soCode;
    private String mrpCode;
    private String itemCode;
    private String itemName;
    private Double requiredQuantity;
    private Date dueDate;
    private Integer sumPoInSap;
    private String note;
    private Integer sumPoInMrp;
    private List<MonitoringDetailDTO> monitorOnSap;
    private List<MonitoringDetailDTO> monitorOnMrp;

    @QueryProjection
    public ItemListInPrDTO(Integer id, String prCode, String soCode, String mrpCode, String itemCode, String itemName, Date dueDate,Double requiredQuantity) {
        this.id = id;
        this.prCode = prCode;
        this.soCode = soCode;
        this.mrpCode = mrpCode;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.dueDate = dueDate;
        this.requiredQuantity = requiredQuantity;
    }

    public String getPrCode() {
        return prCode;
    }

    public void setPrCode(String prCode) {
        this.prCode = prCode;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getMrpCode() {
        return mrpCode;
    }

    public void setMrpCode(String mrpCode) {
        this.mrpCode = mrpCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(Double requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSumPoInSap() {
        return sumPoInSap;
    }

    public void setSumPoInSap(Integer sumPoInSap) {
        this.sumPoInSap = sumPoInSap;
    }

    public Integer getSumPoInMrp() {
        return sumPoInMrp;
    }

    public void setSumPoInMrp(Integer sumPoInMrp) {
        this.sumPoInMrp = sumPoInMrp;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<MonitoringDetailDTO> getMonitorOnSap() {
        return monitorOnSap;
    }

    public void setMonitorOnSap(List<MonitoringDetailDTO> monitorOnSap) {
        this.monitorOnSap = monitorOnSap;
    }

    public List<MonitoringDetailDTO> getMonitorOnMrp() {
        return monitorOnMrp;
    }

    public void setMonitorOnMrp(List<MonitoringDetailDTO> monitorOnMrp) {
        this.monitorOnMrp = monitorOnMrp;
    }
}
