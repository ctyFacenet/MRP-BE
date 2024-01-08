package com.facenet.mrp.producer_kafka;

import java.util.Date;

public class ContextAlert {
    private String soCode;
    private String costomerName;
    private Date timeStart;
    private Date endStart;
    private String status;
    private Double percent;
    private Integer level;
    private String warningLevel;

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getCostomerName() {
        return costomerName;
    }

    public void setCostomerName(String costomerName) {
        this.costomerName = costomerName;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getEndStart() {
        return endStart;
    }

    public void setEndStart(Date endStart) {
        this.endStart = endStart;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getWarningLevel() {
        return warningLevel;
    }

    public void setWarningLevel(String warningLevel) {
        this.warningLevel = warningLevel;
    }
}
