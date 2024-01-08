package com.facenet.mrp.service.dto;

import com.querydsl.core.annotations.QueryProjection;

import java.util.Date;

public class PoDto {
    private String prCode;
    private String poCode;
    private String grpoCode;
    private String soCode;
    private String mrpCode;
    private String contractCode;
    private String contractDate;
    private String productCode;
    private String productName;
    private String technicalName;
    private String vendorCode;
    private String vendor;
    private Date createdAt;
    private Date poOverDueDate;
    private Date receivedDate;
    private String personInCharge;
    private long quantity;
    private long price;
    private String status;
    private Double po_grpo;
    private Long grpo;
    private Long po;

    private Integer lineNumber;

    public PoDto() {
    }

    @QueryProjection
    public PoDto(String prCode,
                 String poCode,
                 String grpoCode,
                 String soCode,
                 String mrpCode,
                 String contractCode,
                 String contractDate,
                 String productCode,
                 String productName,
                 String technicalName,
                 String vendorCode,
                 String vendor,
                 String personInCharge,
                 Date createdAt,
                 Date receivedDate,
                 long quantity, long price, String status) {
        this.prCode = prCode;
        this.poCode = poCode;
        this.grpoCode = grpoCode;
        this.soCode = soCode;
        this.mrpCode = mrpCode;
        this.contractCode = contractCode;
        this.contractDate = contractDate;
        this.productCode = productCode;
        this.productName = productName;
        this.technicalName = technicalName;
        this.vendorCode = vendorCode;
        this.vendor = vendor;
        this.createdAt = createdAt;
//        this.poOverDueDate = poOverDueDate;
        this.receivedDate = receivedDate;
        this.personInCharge = personInCharge;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }
    @QueryProjection
    public PoDto(String prCode,
                 String poCode,
                 String grpoCode,
                 String soCode,
                 String mrpCode,
                 String contractCode,
                 String contractDate,
                 String productCode,
                 String productName,
                 String technicalName,
                 String vendorCode,
                 String vendor,
                 String personInCharge,
                 Date createdAt,
                 Date receivedDate,
                 long quantity, long price, String status, Double po_grpo,
                 Integer lineNumber) {
        this.prCode = prCode;
        this.poCode = poCode;
        this.grpoCode = grpoCode;
        this.soCode = soCode;
        this.mrpCode = mrpCode;
        this.contractCode = contractCode;
        this.contractDate = contractDate;
        this.productCode = productCode;
        this.productName = productName;
        this.technicalName = technicalName;
        this.vendorCode = vendorCode;
        this.vendor = vendor;
        this.createdAt = createdAt;
//        this.poOverDueDate = poOverDueDate;
        this.receivedDate = receivedDate;
        this.personInCharge = personInCharge;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.po_grpo = po_grpo;
        this.lineNumber = lineNumber;
    }

    public String getPrCode() {
        return prCode;
    }

    public void setPrCode(String prCode) {
        this.prCode = prCode;
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

    public String getGrpoCode() {
        return grpoCode;
    }

    public void setGrpoCode(String grpoCode) {
        this.grpoCode = grpoCode;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getContractDate() {
        return contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
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

    public String getTechnicalName() {
        return technicalName;
    }

    public void setTechnicalName(String technicalName) {
        this.technicalName = technicalName;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getPoOverDueDate() {
        return poOverDueDate;
    }

    public void setPoOverDueDate(Date poOverDueDate) {
        this.poOverDueDate = poOverDueDate;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getPersonInCharge() {
        return personInCharge;
    }

    public void setPersonInCharge(String personInCharge) {
        this.personInCharge = personInCharge;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public Double getPo_grpo() {
        return po_grpo;
    }

    public Long getGrpo() {
        return grpo;
    }

    public Long getPo() {
        return po;
    }

    public void setPo_grpo(Double po_grpo) {
        this.po_grpo = po_grpo;
    }

    public void setGrpo(Long grpo) {
        this.grpo = grpo;
    }

    public void setPo(Long po) {
        this.po = po;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }
}
