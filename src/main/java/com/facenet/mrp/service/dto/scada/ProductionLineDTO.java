package com.facenet.mrp.service.dto.scada;

import com.fasterxml.jackson.annotation.*;

import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductionLineDTO {
    private AssetId id;
    private String productionLineCode;
    private String productionLineName;
    private String assetType;
    private String label;
    private String cycleTime;
    private String computingProductivity;
    private String group;
    private Instant createTime;
    private String status;

    public String getProductionLineCode() {
        return productionLineCode;
    }

    public void setProductionLineCode(String productionLineCode) {
        this.productionLineCode = productionLineCode;
    }

    @JsonGetter("productionLineName")
    public String getProductionLineName() {
        return productionLineName;
    }

    @JsonSetter("name")
    public void setProductionLineName(String productionLineName) {
        this.productionLineName = productionLineName;
    }

    @JsonGetter("assetType")
    public String getAssetType() {
        return assetType;
    }

    @JsonSetter("type")
    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(String cycleTime) {
        this.cycleTime = cycleTime;
    }

    public String getComputingProductivity() {
        return computingProductivity;
    }

    public void setComputingProductivity(String computingProductivity) {
        this.computingProductivity = computingProductivity;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @JsonGetter("createTime")
    public Instant getCreateTime() {
        return createTime;
    }

    @JsonSetter("createdTime")
    public void setCreateTime(Long createTime) {
        if (createTime == null) return;
        this.createTime = Instant.ofEpochMilli(createTime);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AssetId getId() {
        return id;
    }

    public void setId(AssetId id) {
        this.id = id;
    }
}
