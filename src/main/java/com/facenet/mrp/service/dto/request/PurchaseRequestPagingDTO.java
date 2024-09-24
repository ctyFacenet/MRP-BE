package com.facenet.mrp.service.dto.request;

import java.sql.Timestamp;

public class PurchaseRequestPagingDTO
{
    String prCode;
    String mrpCode;
    String prCreateUser;
    String soCode;
    String period;
    String prCreateDate;
    String approvalDate;
    String approvalUser;
    String status;

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

    public String getPrCreateDate() {
        return prCreateDate;
    }

    public void setPrCreateDate(String prCreateDate) {
        this.prCreateDate = prCreateDate;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
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
}
