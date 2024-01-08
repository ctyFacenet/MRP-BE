package com.facenet.mrp.service.dto;

public class ViewBtpTpDTO {
    private String productCode;
    private String productName;
    private String productBomVer;
    private String semiProductCode;
    private String semiProductName;
    private String semiProductBomVer;
    private String level;

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

    public String getProductBomVer() {
        return productBomVer;
    }

    public void setProductBomVer(String productBomVer) {
        this.productBomVer = productBomVer;
    }

    public String getSemiProductCode() {
        return semiProductCode;
    }

    public void setSemiProductCode(String semiProductCode) {
        this.semiProductCode = semiProductCode;
    }

    public String getSemiProductName() {
        return semiProductName;
    }

    public void setSemiProductName(String semiProductName) {
        this.semiProductName = semiProductName;
    }

    public String getSemiProductBomVer() {
        return semiProductBomVer;
    }

    public void setSemiProductBomVer(String semiProductBomVer) {
        this.semiProductBomVer = semiProductBomVer;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
