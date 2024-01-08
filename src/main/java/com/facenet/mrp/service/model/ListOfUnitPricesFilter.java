package com.facenet.mrp.service.model;

public class ListOfUnitPricesFilter {

    private String productCode;
    private String productDescription;
    private String vendorCode;
    private String vendorName;

    public ListOfUnitPricesFilter() {
    }

    public ListOfUnitPricesFilter(String productCode, String productDescription, String vendorCode, String vendorName) {
        this.productCode = productCode;
        this.productDescription = productDescription;
        this.vendorCode = vendorCode;
        this.vendorName = vendorName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
}
