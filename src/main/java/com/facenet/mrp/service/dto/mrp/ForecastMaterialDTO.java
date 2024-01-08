package com.facenet.mrp.service.dto.mrp;

import com.facenet.mrp.service.utils.Constants;

import java.util.Date;
import java.util.List;

public class ForecastMaterialDTO {
    private Integer id;
    private String forecastCode;
    private String forecastName;
    private Date startDate;
    private Date endDate;
    private String forecastMode;
    private Integer levelPriority;
    private String createdBy;
    private Date createdAt;
    private String forecastWhs;
    private String forecastSource;
    private String note;
    private Integer forecastOrderStatus = Constants.ForecastOrder.STATUS_NEW;
    private List<String> landMark;
    private List<MaterialPlanDTO> listData;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getForecastCode() {
        return forecastCode;
    }

    public void setForecastCode(String forecastCode) {
        this.forecastCode = forecastCode;
    }

    public String getForecastName() {
        return forecastName;
    }

    public void setForecastName(String forecastName) {
        this.forecastName = forecastName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getForecastMode() {
        return forecastMode;
    }

    public void setForecastMode(String forecastMode) {
        this.forecastMode = forecastMode;
    }

    public Integer getLevelPriority() {
        return levelPriority;
    }

    public void setLevelPriority(Integer levelPriority) {
        this.levelPriority = levelPriority;
    }

    public String getForecastSource() {
        return forecastSource;
    }

    public void setForecastSource(String forecastSource) {
        this.forecastSource = forecastSource;
    }

    public List<String> getLandMark() {
        return landMark;
    }

    public void setLandMark(List<String> landMark) {
        this.landMark = landMark;
    }

    public List<MaterialPlanDTO> getListData() {
        return listData;
    }

    public void setListData(List<MaterialPlanDTO> listData) {
        this.listData = listData;
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

    public Integer getForecastOrderStatus() {
        return forecastOrderStatus;
    }

    public void setForecastOrderStatus(Integer forecastOrderStatus) {
        this.forecastOrderStatus = forecastOrderStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getForecastWhs() {
        return forecastWhs;
    }

    public void setForecastWhs(String forecastWhs) {
        this.forecastWhs = forecastWhs;
    }
}
