package com.facenet.mrp.service.dto.mrp;

import java.util.Date;

public class OnOrderDurationReport {

    private Integer id;
    private String soCode;
    private String mrpCode;
    private String productCode;
    private String productName;
    private String vendorCode;
    private String vendorName;
    private Integer detailId;
    private Date dueDate;
    private Double quantity;

    public OnOrderDurationReport(Integer id, String soCode, String mrpCode, String productCode, String productName, String vendorCode, String vendorName, Integer detailId, Date dueDate, Double quantity) {
        this.id = id;
        this.soCode = soCode;
        this.mrpCode = mrpCode;
        this.productCode = productCode;
        this.productName = productName;
        this.vendorCode = vendorCode;
        this.vendorName = vendorName;
        this.detailId = detailId;
        this.dueDate = dueDate;
        this.quantity = quantity;
    }

    public OnOrderDurationReport(Integer id, String soCode, String mrpCode, String productCode, Integer detailId, Date dueDate, Double quantity) {
        this.id = id;
        this.soCode = soCode;
        this.mrpCode = mrpCode;
        this.productCode = productCode;
        this.detailId = detailId;
        this.dueDate = dueDate;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getDetailId() {
        return detailId;
    }

    public void setDetailId(Integer detailId) {
        this.detailId = detailId;
    }
}
