package com.facenet.mrp.service.dto;

import java.io.Serializable;

public class DetailBomVersionDTO implements Serializable {

    private String itemCode;

    private String itemDescription;

    private String itemOtherName;

    private String controlLevel;

    private String partNumber;

    private Double basicQuantity;

    private Double quantity;
    private String missingQuantity;
    private Double instockQuantity;
    private String itemGroup;

    private String location;

    private String whsCode;

    private String supplier;

    private String replaceItem;

    private String version;

    private String remark;

    private String status;

    public DetailBomVersionDTO(){

    }

    public DetailBomVersionDTO(String itemCode, String itemDescription, String itemOtherName, String controlLevel, Double basicQuantity,
                               String partNumber, String wareHouse, String location,String supplier,String replaceItem,String version,String remark){
        this.itemDescription = itemDescription;
        this.itemCode = itemCode;
        this.itemOtherName = itemOtherName;
        this.controlLevel = controlLevel;
        this.partNumber = partNumber;
        this.whsCode = wareHouse;
        this.supplier = supplier;
        this.replaceItem = replaceItem;
        this.version = version;
        this.location = location;
        this.basicQuantity= basicQuantity;
        this.remark = remark;
    }

    public DetailBomVersionDTO(String itemCode, String itemDescription, String itemOtherName, String controlLevel, Double basicQuantity,
                               String partNumber, String wareHouse, String location,String supplier,String replaceItem,String version,String remark,String status){
        this.itemDescription = itemDescription;
        this.itemCode = itemCode;
        this.itemOtherName = itemOtherName;
        this.controlLevel = controlLevel;
        this.partNumber = partNumber;
        this.whsCode = wareHouse;
        this.supplier = supplier;
        this.replaceItem = replaceItem;
        this.version = version;
        this.location = location;
        this.basicQuantity= basicQuantity;
        this.remark = remark;
        this.status = status;
    }

    public DetailBomVersionDTO(String itemCode, String itemDescription, Double quantity, String whsCode, String status) {
        this.itemCode = itemCode;
        this.itemDescription = itemDescription;
        this.quantity = quantity;
        this.whsCode = whsCode;
        this.status = status;
    }

    public DetailBomVersionDTO(String itemCode, String itemDescription, String whsCode) {
        this.itemCode = itemCode;
        this.itemDescription = itemDescription;
        this.whsCode = whsCode;
    }

    public String getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(String itemGroup) {
        this.itemGroup = itemGroup;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemOtherName() {
        return itemOtherName;
    }

    public void setItemOtherName(String itemOtherName) {
        this.itemOtherName = itemOtherName;
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

    public Double getBasicQuantity() {
        return basicQuantity;
    }

    public void setBasicQuantity(Double basicQuantity) {
        this.basicQuantity = basicQuantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWhsCode() {
        return whsCode;
    }

    public void setWhsCode(String wareHouse) {
        this.whsCode = wareHouse;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getReplaceItem() {
        return replaceItem;
    }

    public void setReplaceItem(String replaceItem) {
        this.replaceItem = replaceItem;
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

    public String getMissingQuantity() {
        return missingQuantity;
    }

    public void setMissingQuantity(String missingQuantity) {
        this.missingQuantity = missingQuantity;
    }

    public Double getInstockQuantity() {
        return instockQuantity;
    }

    public void setInstockQuantity(Double instockQuantity) {
        this.instockQuantity = instockQuantity;
    }

    @Override
    public String toString() {
        return "DetailBomVersionDTO{" +
            "itemCode=" + itemCode +
            ", remark='" + remark + '\'' +
            ", version='" + version + '\'' +
            ", itemCode=" + itemCode +
            ", replaceItem='" + replaceItem + '\'' +
            ", supplier='" + supplier + '\'' +
            ", wareHouse=" + whsCode +
            ", location='" + location + '\'' +
            ", basicQuantity='" + basicQuantity + '\'' +
            ", partNumber='" + partNumber + '\'' +
            ", controlLevel=" + controlLevel +
            ", itemOtherName='" + itemOtherName + '\'' +
            '}';
    }
}
