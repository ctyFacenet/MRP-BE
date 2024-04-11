package com.facenet.mrp.service.model;

public class ProductionLineFilter {
    private String productionLineName;
    private String computingProductivity;
    private String group;
    private String assetType;
    private String cycleTime;
    private String label;

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

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(String cycleTime) {
        this.cycleTime = cycleTime;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getProductionLineName() {
        return productionLineName;
    }

    public void setProductionLineName(String productionLineName) {
        this.productionLineName = productionLineName;
    }
}
