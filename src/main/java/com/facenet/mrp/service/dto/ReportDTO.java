package com.facenet.mrp.service.dto;

import java.util.Date;

public class ReportDTO {

    private String soCode;
    private String partCode;
    private String partName;
    private String saleCode;
    private Date orderDate;
    private Date deliveryDate;
    private long totalProductCount;
    private String saleName;
    private long totalSuppliesCount;
    private long totalPrCount;
    private int remainingQuantity;
    private double completionRate;
    private int status;

    public ReportDTO() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
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

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public String getSaleCode() {
        return saleCode;
    }

    public void setSaleCode(String saleCode) {
        this.saleCode = saleCode;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public long getTotalProductCount() {
        return totalProductCount;
    }

    public void setTotalProductCount(long totalProductCount) {
        this.totalProductCount = totalProductCount;
    }

    public long getTotalSuppliesCount() {
        return totalSuppliesCount;
    }

    public void setTotalSuppliesCount(long totalSuppliesCount) {
        this.totalSuppliesCount = totalSuppliesCount;
    }

    public long getTotalPrCount() {
        return totalPrCount;
    }

    public void setTotalPrCount(long totalPrCount) {
        this.totalPrCount = totalPrCount;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(int remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(double completionRate) {
        this.completionRate = completionRate;
    }
}
