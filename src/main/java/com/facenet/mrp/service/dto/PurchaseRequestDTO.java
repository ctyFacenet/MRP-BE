package com.facenet.mrp.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;
import java.util.Date;

public class PurchaseRequestDTO {
    private String soCode;
    private String mrpId;
    private String prId;
    private String poId;
    private String grpoId;
    private String productId;
    private String description;
    private String technicalName;
    private String vendorCode;
    private String vendor;
    private Date createdAt;
    private Date deliverAt;
    private Long quantity;
    private String createdBy;
    private String status;
    private Double pr_po;
    private Integer lineNumber;
    private Long pr;
    private Long po;
    @JsonIgnore
    private LocalDate sapUpdatedAt;

    public PurchaseRequestDTO() {
    }

    @QueryProjection
    public PurchaseRequestDTO(String soCode,
                              String mrpId,
                              String prId,
                              String poId,
                              String grpoId,
                              String productId,
                              String description,
                              String technicalName,
                              String vendorCode,
                              String vendor,
                              Date createdAt,
                              Date deliverAt,
                              Long quantity,
                              String createdBy, String status, Double pr_po,
                              Integer lineNumber) {
        this.soCode = soCode;
        this.mrpId = mrpId;
        this.poId = poId;
        this.prId = prId.trim();
        this.grpoId = grpoId;
        this.productId = productId;
        this.description = description;
        this.technicalName = technicalName;
        this.vendorCode = vendorCode;
        this.vendor = vendor;
        this.createdAt = createdAt;
        this.deliverAt = deliverAt;
        this.quantity = quantity;
        this.createdBy = createdBy;
        this.status = status;
        this.pr_po = pr_po;
        this.lineNumber = lineNumber;
    }
    @QueryProjection
    public PurchaseRequestDTO(String prId,String productId, Long quantity, String description) {
//        this.soCode = soCode;
//        this.mrpId = mrpId;
//        this.poId = poId;
        this.prId = prId.trim();
//        this.grpoId = grpoId;
        this.productId = productId;
        this.description = description;
        this.technicalName = technicalName;
//        this.vendorCode = vendorCode;
//        this.vendor = vendor;
//        this.createdAt = createdAt;
//        this.deliverAt = deliverAt;
        this.quantity = quantity;
//        this.createdBy = createdBy;
//        this.status = status;
    }

    public Long getPo() {
        return po;
    }

    public void setPo(Long po) {
        this.po = po;
    }

    public Long getPr() {
        return pr;
    }

    public void setPr(Long pr) {
        this.pr = pr;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getMrpId() {
        return mrpId;
    }

    public void setMrpId(String mrpId) {
        this.mrpId = mrpId;
    }

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public String getPrId() {
        return prId;
    }

    public void setPrId(String prId) {
        this.prId = prId;
    }

    public String getGrpoId() {
        return grpoId;
    }

    public void setGrpoId(String grpoId) {
        this.grpoId = grpoId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Date getDeliverAt() {
        return deliverAt;
    }

    public void setDeliverAt(Date deliverAt) {
        this.deliverAt = deliverAt;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public Double getPr_po() {
        return pr_po;
    }


    public void setPr_po(Double pr_po) {
        this.pr_po = pr_po;
    }


    @Override
    public String toString() {
        return "PurchaseRequestDTO{" +
            "prId='" + prId + '\'' +
            ", poId='" + poId + '\'' +
            ", grpoId='" + grpoId + '\'' +
            ", productId='" + productId + '\'' +
            ", description='" + description + '\'' +
            ", vendor='" + vendor + '\'' +
            ", createdAt=" + createdAt +
            ", deliverAt=" + deliverAt +
            ", quantity=" + quantity +
            ", createdBy='" + createdBy + '\'' +
            ", status='" + status + '\'' +
            '}';
    }

    public String getTechnicalName() {
        return technicalName;
    }

    public void setTechnicalName(String technicalName) {
        this.technicalName = technicalName;
    }

    public LocalDate getSapUpdatedAt() {
        return sapUpdatedAt;
    }

    public void setSapUpdatedAt(LocalDate sapUpdatedAt) {
        this.sapUpdatedAt = sapUpdatedAt;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }
}
