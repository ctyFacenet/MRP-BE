package com.facenet.mrp.service.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * DTO for {@link com.facenet.mrp.domain.mrp.PurchaseRequestEntity}
 */
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequestEntityDto implements Serializable {
    int id;
    String prCode;
    String mrpCode;
    String prCreateUser;
    String soCode;
    String period;
    Timestamp prCreateDate;
    Timestamp approvalDate;
    String approvalUser;
    String status;
    Timestamp deletedAt;
    String deletedBy;
    List<PurchaseRequestDetailEntityDto> purchaseRequestDetailEntityDtos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPrCreateUser() {
        return prCreateUser;
    }

    public void setPrCreateUser(String prCreateUser) {
        this.prCreateUser = prCreateUser;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Timestamp getPrCreateDate() {
        return prCreateDate;
    }

    public void setPrCreateDate(Timestamp prCreateDate) {
        this.prCreateDate = prCreateDate;
    }

    public Timestamp getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Timestamp approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getApprovalUser() {
        return approvalUser;
    }

    public void setApprovalUser(String approvalUser) {
        this.approvalUser = approvalUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public List<PurchaseRequestDetailEntityDto> getPurchaseRequestDetailEntityDtos() {
        return purchaseRequestDetailEntityDtos;
    }

    public void setPurchaseRequestDetailEntityDtos(List<PurchaseRequestDetailEntityDto> purchaseRequestDetailEntityDtos) {
        this.purchaseRequestDetailEntityDtos = purchaseRequestDetailEntityDtos;
    }
}
