package com.facenet.mrp.service.model;

import java.util.Calendar;
import java.util.Date;

public class PurchaseRecommendationFilter {
    private String soCode;
    private String mrpSubCode;
    private Date createdStartDate;
    private Date createdEndDate;
    private Integer status;

    public Date getCreatedEndDate() {
        return createdEndDate;
    }

    public void setCreatedEndDate(Date createdEndDate) {
        Calendar cal = Calendar.getInstance();
        if (createdEndDate != null){
            cal.setTime(createdEndDate);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            this.createdEndDate = cal.getTime();
        }else{
            this.createdEndDate = createdEndDate;
        }

    }

    public Date getCreatedStartDate() {
        return createdStartDate;
    }

    public void setCreatedStartDate(Date createdStartDate) {
        Calendar cal = Calendar.getInstance();
        if (createdStartDate != null){
            cal.setTime(createdStartDate);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            this.createdStartDate = cal.getTime();
        }else{
            this.createdStartDate = createdStartDate;
        }
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
