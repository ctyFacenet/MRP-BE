package com.facenet.mrp.service.model;

import java.util.Calendar;
import java.util.Date;

public class MonitoringFilter {

    private String soCode;
    private String mrpSubCode;
    private String itemCode;
    private String poCode;
    private String prCode;
    private String mrpCode;
    private String contractNumber;
    private String vendorCode;
    private String vendorName;
    private String poCreateUser;
    private Date poCreateDate;
    private Date dueDate;
    private String itemName;
    private Boolean getDetail;

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getMrpSubCode() {
        return mrpSubCode;
    }

    public void setMrpSubCode(String mrpSubCode) {
        this.mrpSubCode = mrpSubCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getPoCreateUser() {
        return poCreateUser;
    }

    public void setPoCreateUser(String poCreateUser) {
        this.poCreateUser = poCreateUser;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getPoCode() {
        return poCode;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
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

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public Boolean getGetDetail() {
        return getDetail;
    }

    public void setGetDetail(Boolean getDetail) {
        this.getDetail = getDetail;
    }

    public Date getPoCreateDate() {
        return poCreateDate;
    }

    public void setPoCreateDate(Date poCreateDate) {
        this.poCreateDate = poCreateDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
