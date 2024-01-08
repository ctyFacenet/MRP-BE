package com.facenet.mrp.service.dto.mrp;

import com.querydsl.core.annotations.QueryProjection;

import java.util.Date;
import java.util.List;

public class ItemInPlanDurationWithPoDTO {
    private Integer id;
    private String prCode;
    private String soCode;
    private String mrpCode;
    private String poCode;
    private String itemCode;
    private String itemName;
    private String vendorCode;
    private String vendorName;
    private Double requiredQuantity;
    private Double estimateQuantity;
    private Double arrivedQuantity;
    private Double notArrivedQuantity;
    private Double onPoQuantity;
    private Date dueDate;
    private Integer percent;
    private String note;
    private List<MonitoringDetailDTO> monitoringDetailDTOS;

    public ItemInPlanDurationWithPoDTO() {
    }

    @QueryProjection
    public ItemInPlanDurationWithPoDTO(Integer id, String prCode, String soCode, String mrpCode, String poCode, String itemCode, String itemName, String vendorCode, String vendorName, Date dueDate) {
        this.id = id;
        this.prCode = prCode;
        this.soCode = soCode;
        this.mrpCode = mrpCode;
        this.poCode = poCode;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.vendorCode = vendorCode;
        this.vendorName = vendorName;
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

    public String getPoCode() {
        return poCode;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
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

    public Double getEstimateQuantity() {
        return estimateQuantity;
    }

    public void setEstimateQuantity(Double estimateQuantity) {
        this.estimateQuantity = estimateQuantity;
    }

    public Double getArrivedQuantity() {
        return arrivedQuantity;
    }

    public void setArrivedQuantity(Double arrivedQuantity) {
        this.arrivedQuantity = arrivedQuantity;
    }

    public Double getNotArrivedQuantity() {
        return notArrivedQuantity;
    }

    public void setNotArrivedQuantity(Double notArrivedQuantity) {
        this.notArrivedQuantity = notArrivedQuantity;
    }

    public Double getOnPoQuantity() {
        return onPoQuantity;
    }

    public void setOnPoQuantity(Double onPoQuantity) {
        this.onPoQuantity = onPoQuantity;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
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

    public List<MonitoringDetailDTO> getMonitoringDetailDTOS() {
        return monitoringDetailDTOS;
    }

    public void setMonitoringDetailDTOS(List<MonitoringDetailDTO> monitoringDetailDTOS) {
        this.monitoringDetailDTOS = monitoringDetailDTOS;
    }
}
