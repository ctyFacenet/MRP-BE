package com.facenet.mrp.service.dto;

public class ProductDetail {

    private String soCode;
    private String itemCode;
    private String bomVersion;
    private String itemGroupCode;

    public ProductDetail(String soCode, String itemCode, String bomVersion) {
        this.soCode = soCode;
        this.itemCode = itemCode;
        this.bomVersion = bomVersion;
    }

    public ProductDetail(String soCode, String itemCode, String bomVersion, String itemGroupCode) {
        this.soCode = soCode;
        this.itemCode = itemCode;
        this.bomVersion = bomVersion;
        this.itemGroupCode = itemGroupCode;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getBomVersion() {
        return bomVersion;
    }

    public void setBomVersion(String bomVersion) {
        this.bomVersion = bomVersion;
    }

    public String getItemGroupCode() {
        return itemGroupCode;
    }

    public void setItemGroupCode(String itemGroupCode) {
        this.itemGroupCode = itemGroupCode;
    }
}
