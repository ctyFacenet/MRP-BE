package com.facenet.mrp.service.dto;

import com.querydsl.core.annotations.QueryProjection;

public class InvoiceDTO {
    private String soCode;
    private String mrpId;
    private String poId;
    private String grpoId;
    private String invoiceId;
    private String productId;
    private String description;
    private String vendorCode;
    private String vendor;
    private String createdAt;
    private String invCode;
    private Double quantity;
    private Double price;
    private String currency;
    private String status;
    private String uDocnum;
    private String uDeclareStat;
    public InvoiceDTO() {
    }
    @QueryProjection
    public InvoiceDTO(String currency,String soCode, String mrpId, String poId, String grpoId, String invoiceId, String productId, String description, String vendorCode, String vendor, String createdAt, String invCode, Double quantity, Double price, String status) {
        this.currency = currency;
        this.soCode = soCode;
        this.mrpId = mrpId;
        this.poId = poId;
        this.grpoId = grpoId;
        this.invoiceId = invoiceId;
        this.productId = productId;
        this.description = description;
        this.vendorCode = vendorCode;
        this.vendor = vendor;
        this.createdAt = createdAt;
        this.invCode = invCode;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSoCode() {
        return soCode;
    }

    public String getMrpId() {
        return mrpId;
    }

    public String getPoId() {
        return poId;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public String getProductId() {
        return productId;
    }

    public String getDescription() {
        return description;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public String getVendor() {
        return vendor;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public void setMrpId(String mrpId) {
        this.mrpId = mrpId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGrpoId() {
        return grpoId;
    }

    public void setGrpoId(String grpoId) {
        this.grpoId = grpoId;
    }

    public String getInvCode() {
        return invCode;
    }

    public void setInvCode(String invCode) {
        this.invCode = invCode;
    }
}
