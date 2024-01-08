package com.facenet.mrp.service.dto.mrp;

import com.querydsl.core.annotations.QueryProjection;

public class DurationPlanDTO {
    private Integer id;

    private String planCode;

    private String planName;

    private Integer quantityItem;

    private String listItem;

    public DurationPlanDTO() {
    }

    @QueryProjection
    public DurationPlanDTO(Integer id, String planCode, String planName, Integer quantityItem,String listItem) {
        this.id = id;
        this.planCode = planCode;
        this.planName = planName;
        this.quantityItem = quantityItem;
        this.listItem = listItem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Integer getQuantityItem() {
        return quantityItem;
    }

    public void setQuantityItem(Integer quantityItem) {
        this.quantityItem = quantityItem;
    }

    public String getListItem() {
        return listItem;
    }

    public void setListItem(String listItem) {
        this.listItem = listItem;
    }
}
