package com.facenet.mrp.service.dto.mrp;

public class CloneBomDTO {

    private Integer parentGroupItem;
    private String parentItemCode;
    private String parentItemName;
    private Double parentQuota;
    private String parentBomVersion;
    private Integer childGroupItem;
    private String childItemCode;
    private String childItemName;
    private String altItemCode;
    private Double childQuota;
    private String childBomVersion;



    public Integer getParentGroupItem() {
        return parentGroupItem;
    }

    public void setParentGroupItem(Integer parentGroupItem) {
        this.parentGroupItem = parentGroupItem;
    }

    public CloneBomDTO(Integer parentGroupItem, String parentItemCode, String parentItemName, Double parentQuota, String parentBomVersion, Integer childGroupItem, String childItemCode, String childItemName, String altItemCode, Double childQuota, String childBomVersion) {
        this.parentGroupItem = parentGroupItem;
        this.parentItemCode = parentItemCode;
        this.parentItemName = parentItemName;
        this.parentQuota = parentQuota;
        this.parentBomVersion = parentBomVersion;
        this.childGroupItem = childGroupItem;
        this.childItemCode = childItemCode;
        this.childItemName = childItemName;
        this.altItemCode = altItemCode;
        this.childQuota = childQuota;
        this.childBomVersion = childBomVersion;
    }

    public CloneBomDTO(Integer parentGroupItem, String parentItemCode, String parentItemName, Double parentQuota, Integer childGroupItem, String childItemCode, String childItemName, Double childQuota) {
        this.parentGroupItem = parentGroupItem;
        this.parentItemCode = parentItemCode;
        this.parentItemName = parentItemName;
        this.parentQuota = parentQuota;
        this.childGroupItem = childGroupItem;
        this.childItemCode = childItemCode;
        this.childItemName = childItemName;
        this.childQuota = childQuota;
    }

    public Integer getChildGroupItem() {
        return childGroupItem;
    }

    public void setChildGroupItem(Integer childGroupItem) {
        this.childGroupItem = childGroupItem;
    }

    public String getParentItemCode() {
        return parentItemCode;
    }

    public void setParentItemCode(String parentItemCode) {
        this.parentItemCode = parentItemCode;
    }

    public String getParentItemName() {
        return parentItemName;
    }

    public void setParentItemName(String parentItemName) {
        this.parentItemName = parentItemName;
    }

    public Double getParentQuota() {
        return parentQuota;
    }

    public void setParentQuota(Double parentQuota) {
        this.parentQuota = parentQuota;
    }

    public String getParentBomVersion() {
        return parentBomVersion;
    }

    public void setParentBomVersion(String parentBomVersion) {
        this.parentBomVersion = parentBomVersion;
    }

    public String getChildItemCode() {
        return childItemCode;
    }

    public void setChildItemCode(String childItemCode) {
        this.childItemCode = childItemCode;
    }

    public String getChildItemName() {
        return childItemName;
    }

    public void setChildItemName(String childItemName) {
        this.childItemName = childItemName;
    }

    public String getAltItemCode() {
        return altItemCode;
    }

    public void setAltItemCode(String altItemCode) {
        this.altItemCode = altItemCode;
    }

    public Double getChildQuota() {
        return childQuota;
    }

    public void setChildQuota(Double childQuota) {
        this.childQuota = childQuota;
    }

    public String getChildBomVersion() {
        return childBomVersion;
    }

    public void setChildBomVersion(String childBomVersion) {
        this.childBomVersion = childBomVersion;
    }
}
