package com.facenet.mrp.service.dto;

import java.io.Serializable;
import java.util.Date;

public class ProductOrderDetailDto implements Serializable {

    private Integer id;
    private String mrpPoId;
    private String productCode;
    private String productName;
    private String itemGroup;
    private String bomVersion;
    private Integer orderQuantity;

    private Double instockQuantity;
    private String missingQuantity;
    private Date orderedTime;
    private Date deliveryTime;
    private String supplyMethod;
    private String priority;
    private String note;
    private Byte bomStatus;
    private Integer status;
    private Byte isActive;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBomVersion() {
        return bomVersion;
    }

    public void setBomVersion(String bomVersion) {
        this.bomVersion = bomVersion;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public Date getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(Date orderedTime) {
        this.orderedTime = orderedTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getSupplyMethod() {
        return supplyMethod;
    }

    public void setSupplyMethod(String supplyMethod) {
        if (supplyMethod.equals("")){
            this.supplyMethod = "MRP";
            return;
        }

        this.supplyMethod = supplyMethod;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {

        if (priority.equals("")){
            this.priority = "1";
            return;
        }

        this.priority = priority;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Byte getBomStatus() {
        return bomStatus;
    }

    public void setBomStatus(Byte bomStatus) {
        this.bomStatus = bomStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    public Double getInstockQuantity() {
        return instockQuantity;
    }

    public void setInstockQuantity(Double instockQuantity) {
        this.instockQuantity = instockQuantity;
    }

    public String getMissingQuantity() {
        return missingQuantity;
    }

    public void setMissingQuantity(String missingQuantity) {
        this.missingQuantity = missingQuantity;
    }

    public String getMrpPoId() {
        return mrpPoId;
    }

    public void setMrpPoId(String mrpPoId) {
        this.mrpPoId = mrpPoId;
    }

    public String getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(String itemGroup) {
        this.itemGroup = itemGroup;
    }
}
