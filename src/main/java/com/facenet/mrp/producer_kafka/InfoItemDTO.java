package com.facenet.mrp.producer_kafka;

import com.querydsl.core.annotations.QueryProjection;

import java.util.Date;

public class InfoItemDTO {
    private String soCode;
    private String mrpCode;
    private String itemCode;
    private String itemName;
    private Date startTime;
    private Date endTime;

    public InfoItemDTO() {
    }

    @QueryProjection
    public InfoItemDTO(String soCode, String mrpCode, String itemCode, String itemName, Date startTime, Date endTime) {
        this.soCode = soCode;
        this.mrpCode = mrpCode;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
