package com.facenet.mrp.service.dto;

import java.sql.Timestamp;
import java.util.Date;

public class ListMrpDTO {
    private String mrpCode;

    private String soCode;

    private String mrpDescription;

    private Date timeStart;

    private Date timeEnd;

    private Date updateTime;

    private String updateBy;

    private Date lastAccess;

    private Byte status;

    public String getMrpCode() {
        return mrpCode;
    }

    public void setMrpCode(String mrpCode) {
        this.mrpCode = mrpCode;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getMrpDescription() {
        return mrpDescription;
    }

    public void setMrpDescription(String mrpDescription) {
        this.mrpDescription = mrpDescription;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public ListMrpDTO() {
    }

    public ListMrpDTO(String mrpCode, String soCode, String mrpDescription, Date timeStart, Date timeEnd, Timestamp updateTime, String updateBy, Date lastAccess, Byte status) {
        this.mrpCode = mrpCode;
        this.soCode = soCode;
        this.mrpDescription = mrpDescription;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.lastAccess = lastAccess;
        this.status = status;
    }
}
