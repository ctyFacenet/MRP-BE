package com.facenet.mrp.service.model;

import java.util.Date;

public class ProductOrderDetailFilter {

    private String productCode;
    private String productName;
    private String bomVersion;
    private String orderQuantity;
    private Date orderedTime;
    private Date deliveryTime;
    private String supplyMethod;

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

    public String getOrderQuantity() {

        return orderQuantity;
    }

    public void setOrderQuantity(String orderQuantity) {

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
        this.supplyMethod = supplyMethod;
    }
}
