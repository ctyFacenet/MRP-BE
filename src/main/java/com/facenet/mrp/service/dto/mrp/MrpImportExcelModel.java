package com.facenet.mrp.service.dto.mrp;

import java.util.ArrayList;
import java.util.List;

public class MrpImportExcelModel {

    private String productCode;
    private String productName;
    private String bomVersion;
    private Integer level;
    private double requiredQuantity;
    private List<MrpDateQuantity> LandMarkDTOs;
    private String parent;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBomVersion() {
        return bomVersion;
    }

    public void setBomVersion(String bomVersion) {
        this.bomVersion = bomVersion;
    }

    public double getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(double requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public List<MrpDateQuantity> getLandMarkDTOs() {
        return LandMarkDTOs;
    }

    public void setLandMarkDTOs(List<MrpDateQuantity> landMarkDTOs) {
        LandMarkDTOs = landMarkDTOs;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
