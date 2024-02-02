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

    private String productOrderChild;

    private String customerCode;

    private String customerName;

    private String saleCode;

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

    public String getProductOrderChild() {
        return productOrderChild;
    }

    public void setProductOrderChild(String productOrderChild) {
        this.productOrderChild = productOrderChild;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSaleCode() {
        return saleCode;
    }

    public void setSaleCode(String saleCode) {
        this.saleCode = saleCode;
    }
}
