package com.facenet.mrp.service.model;

import java.sql.Date;
import java.time.LocalDate;

public class PoFilter {

    private String poCode;
    private String grpoCode;
    private String soCode;
    private String contractCode;
    private Date contractDate;
    private String productCode;
    private String productName;
    private String technicalName;
    private String vendor;
    private Date createdAt;
    private Date poOverDueDate;
    private Date receivedDate;
    private String personInCharge;
    private String status;
    private LocalDate sapUpdatedAt;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
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

    public LocalDate getSapUpdatedAt() {
        return sapUpdatedAt;
    }

    public void setSapUpdatedAt(LocalDate sapUpdatedAt) {
        this.sapUpdatedAt = sapUpdatedAt;
    }
}
