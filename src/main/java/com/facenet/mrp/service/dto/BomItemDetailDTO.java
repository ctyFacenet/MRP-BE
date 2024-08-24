package com.facenet.mrp.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public class BomItemDetailDTO {
    private Integer materialGroup;
    private String materialCode;
    private String materialDescription;
    private String technicalName;
    private String controlLevel;
    private String partNumber;
    private String alternativeMaterial;
    private String location;
    private Double quantity;
    private String unit;
    private String warehouse;
    private String issueMethod;
    private String vendor;
    private String version;
    private String remark;
    private Integer level;
    private String rootMaterial;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public BomItemDetailDTO(){

    }
    public BomItemDetailDTO(Integer materialGroup, String materialCode, String materialDescription, String technicalName, String controlLevel, String partNumber, String alternativeMaterial, String location, Double quantity, String unit, String warehouse, String issueMethod, String vendor, String version, String remark) {
        this.materialGroup = materialGroup;
        this.materialCode = materialCode;
        this.materialDescription = materialDescription;
        this.technicalName = technicalName;
        this.controlLevel = controlLevel;
        this.partNumber = partNumber;
        this.alternativeMaterial = alternativeMaterial;
        this.location = location;
        this.quantity = quantity;
        this.unit = unit;
        this.warehouse = warehouse;
        this.issueMethod = issueMethod;
        this.vendor = vendor;
        this.version = version;
        this.remark = remark;
    }

    public BomItemDetailDTO(Integer materialGroup, String materialCode, String materialDescription, Double quantity, String unit, String warehouse, String issueMethod) {
        this.materialGroup = materialGroup;
        this.materialCode = materialCode;
        this.materialDescription = materialDescription;
        this.quantity = quantity;
        this.unit = unit;
        this.warehouse = warehouse;
        this.issueMethod = issueMethod;
    }

    public String getMaterialGroup() {
        return materialGroup == 101 ? "BTP" : "NVL";
    }

    @JsonIgnore
    public Integer getMaterialGroupInt() {
        return materialGroup;
    }

    public void setMaterialGroup(Integer materialGroup) {
        this.materialGroup = materialGroup;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public String getTechnicalName() {
        return technicalName;
    }

    public void setTechnicalName(String technicalName) {
        this.technicalName = technicalName;
    }

    public String getControlLevel() {
        return controlLevel;
    }

    public void setControlLevel(String controlLevel) {
        this.controlLevel = controlLevel;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getAlternativeMaterial() {
        return alternativeMaterial;
    }

    public void setAlternativeMaterial(String alternativeMaterial) {
        this.alternativeMaterial = alternativeMaterial;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getIssueMethod() {
        return issueMethod;
    }

    public void setIssueMethod(String issueMethod) {
        this.issueMethod = issueMethod;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getRootMaterial() {
        return rootMaterial;
    }

    public void setRootMaterial(String rootMaterial) {
        this.rootMaterial = rootMaterial;
    }
}
