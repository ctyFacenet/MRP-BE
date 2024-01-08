package com.facenet.mrp.service.dto.mrp;

import java.util.Date;

public class prMonitorFilter {
    private String prCode;
    private String soCode;
    private String mrpCode;
    private String itemCode;
    private String itemName;
    private String prCreateUser;
    private Date prCreateDate;
    private Date asignDate;

    public String getPrCode() {
        return prCode;
    }

    public void setPrCode(String prCode) {
        this.prCode = prCode;
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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPrCreateUser() {
        return prCreateUser;
    }

    public void setPrCreateUser(String prCreateUser) {
        this.prCreateUser = prCreateUser;
    }

    public Date getPrCreateDate() {
        return prCreateDate;
    }

    public void setPrCreateDate(Date prCreateDate) {
        this.prCreateDate = prCreateDate;
    }

    public Date getAsignDate() {
        return asignDate;
    }

    public void setAsignDate(Date asignDate) {
        this.asignDate = asignDate;
    }
}
