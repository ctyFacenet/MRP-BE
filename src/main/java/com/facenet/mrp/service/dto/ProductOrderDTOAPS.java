package com.facenet.mrp.service.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductOrderDTOAPS {

    private String productOrderId;

    private String customerCode;
    private String customerName;
    private String saleCode;
    private String saleName;
    private String partCode;
    private String partName;
    private String productOrderType;
    private Date orderDate;
    private Date completeDate;
    private String externalPoId;
    private Integer state;
    private String note;
    private String planner;
    private String planningMode;

    private List<ProductOrderDetailDTOAPS> productOrderItem = new ArrayList<>();

    public String getProductOrderId() {
        return productOrderId;
    }

    public void setProductOrderId(String productOrderId) {
        this.productOrderId = productOrderId;
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

    public String getProductOrderType() {
        return productOrderType;
    }

    public void setProductOrderType(String productOrderType) {
        this.productOrderType = productOrderType;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public String getExternalPoId() {
        return externalPoId;
    }

    public void setExternalPoId(String externalPoId) {
        this.externalPoId = externalPoId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPlanner() {
        return planner;
    }

    public void setPlanner(String planner) {
        this.planner = planner;
    }

    public String getSaleCode() {
        return saleCode;
    }

    public void setSaleCode(String saleCode) {
        this.saleCode = saleCode;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public String getPartCode() {
        return partCode;
    }

    public void setPartCode(String partCode) {
        this.partCode = partCode;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getPlanningMode() {
        return planningMode;
    }

    public void setPlanningMode(String planningMode) {
        this.planningMode = planningMode;
    }

    public List<ProductOrderDetailDTOAPS> getProductOrderItem() {
        return productOrderItem;
    }

    public void setProductOrderItem(List<ProductOrderDetailDTOAPS> productOrderItem) {
        this.productOrderItem = productOrderItem;
    }
}
