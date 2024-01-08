package com.facenet.mrp.service.dto.mrp;

import java.util.Date;

public class GenForecastCodeDTO {
    private String forecastCode;
    private String createdBy;
    private Date createdAt;

    public String getForecastCode() {
        return forecastCode;
    }

    public void setForecastCode(String forecastCode) {
        this.forecastCode = forecastCode;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
