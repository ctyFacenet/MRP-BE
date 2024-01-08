package com.facenet.mrp.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

public class ItemHoldDTO {
    private String mrpSubCode;
    private String soCode;
    private Double quantity;
    private Date receiveDate;
    private String warehouseCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double currentInventory;

    public ItemHoldDTO() {
    }

    public ItemHoldDTO(String mrpSubCode, String soCode, Double quantity, String warehouseCode, Date receiveDate) {
        this.mrpSubCode = mrpSubCode;
        this.soCode = soCode;
        this.quantity = quantity;
        this.warehouseCode = warehouseCode;
        this.receiveDate = receiveDate;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getMrpSubCode() {
        return mrpSubCode;
    }

    public void setMrpSubCode(String mrpSubCode) {
        this.mrpSubCode = mrpSubCode;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public Double getCurrentInventory() {
        return currentInventory;
    }

    public void setCurrentInventory(Double currentInventory) {
        this.currentInventory = currentInventory;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }
}
