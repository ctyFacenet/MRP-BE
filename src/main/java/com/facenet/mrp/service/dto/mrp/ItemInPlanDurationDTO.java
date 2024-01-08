package com.facenet.mrp.service.dto.mrp;

import com.querydsl.core.annotations.QueryProjection;

import java.util.Date;
import java.util.List;

public class ItemInPlanDurationDTO {
    private Integer id;
    private String prCode;
    private String soCode;
    private String mrpCode;
    private String itemCode;
    private String itemName;
    private String vendorCode;
    private String vendorName;
    private Double requiredQuantity;
    private Date dueDate;
    private Integer sumPo;
    private Integer percent;
    private String note;
    private List<MonitoringDetailDTO> monitorOnMrp;

    public ItemInPlanDurationDTO() {
    }

    @QueryProjection
    public ItemInPlanDurationDTO(Integer id, String prCode, String soCode, String mrpCode, String itemCode, String itemName, String vendorCode, String vendorName, Double requiredQuantity, Date dueDate) {
        this.id = id;
        this.prCode = prCode;
        this.soCode = soCode;
        this.mrpCode = mrpCode;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.vendorCode = vendorCode;
        this.vendorName = vendorName;
        this.requiredQuantity = requiredQuantity;
        this.dueDate = dueDate;
   }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
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

    public Integer getSumPo() {
        return sumPo;
    }

    public void setSumPo(Integer sumPo) {
        this.sumPo = sumPo;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<MonitoringDetailDTO> getMonitorOnMrp() {
        return monitorOnMrp;
    }

    public void setMonitorOnMrp(List<MonitoringDetailDTO> monitorOnMrp) {
        this.monitorOnMrp = monitorOnMrp;
    }
}
