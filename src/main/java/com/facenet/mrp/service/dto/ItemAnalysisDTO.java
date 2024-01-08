package com.facenet.mrp.service.dto;

import java.util.ArrayList;

public class ItemAnalysisDTO {
    private String mrpSubCode;
    private String groupItem;
    private String itemCode;
    private String itemName;
    private Double requestNumber;
    private Double currentNumber;
    private Double quota;
    private String whsName;
    private String bomVersion;
    private String level;
    private String status;
    private String parentPath;
    private ArrayList<IndexAnalysisDTO> detailResult;
    private ArrayList<ItemAnalysisDTO> children;

    public ArrayList<ItemAnalysisDTO> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<ItemAnalysisDTO> children) {
        this.children = children;
    }

    public String getMrpSubCode() {
        return mrpSubCode;
    }

    public void setMrpSubCode(String mrpSubCode) {
        this.mrpSubCode = mrpSubCode;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public String getGroupItem() {
        return groupItem;
    }

    public void setGroupItem(String groupItem) {
        this.groupItem = groupItem;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(Double requestNumber) {
        this.requestNumber = requestNumber;
    }

    public Double getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(Double currentNumber) {
        this.currentNumber = currentNumber;
    }

    public Double getQuota() {
        return quota;
    }

    public void setQuota(Double quota) {
        this.quota = quota;
    }

    public String getWhsName() {
        return whsName;
    }

    public void setWhsName(String whsName) {
        this.whsName = whsName;
    }

    public String getBomVersion() {
        return bomVersion;
    }

    public void setBomVersion(String bomVersion) {
        this.bomVersion = bomVersion;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<IndexAnalysisDTO> getDetailResult() {
        return detailResult;
    }

    public void setDetailResult(ArrayList<IndexAnalysisDTO> detailResult) {
        this.detailResult = detailResult;
    }

    public ItemAnalysisDTO() {
    }

    public ItemAnalysisDTO(String mrpSubCode,String groupItem, String itemCode, String itemName, Double requestNumber, Double currentNumber, Double quota, String whsName, String bomVersion, String level, String status,String parentPath) {
        this.mrpSubCode = mrpSubCode;
        this.groupItem = groupItem;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.requestNumber = requestNumber;
        this.currentNumber = currentNumber;
        this.quota = quota;
        this.whsName = whsName;
        this.bomVersion = bomVersion;
        this.level = level;
        this.status = status;
        this.parentPath = parentPath;
    }
}
