package com.facenet.mrp.service.dto.mrp;

public class VendorCodeForDetailReport{

    private String vendorCode;
    private String vendorName;

    private String mrpSubCode;
    private String itemCode;


    public VendorCodeForDetailReport(String mrpSubCode ,String itemCode ,String vendorCode) {
        this.vendorCode = vendorCode;
        this.mrpSubCode = mrpSubCode;
        this.itemCode = itemCode;
    }

    public VendorCodeForDetailReport(String vendorCode, String vendorName) {
        this.vendorCode = vendorCode;
        this.vendorName = vendorName;
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

    public String getMrpSubCode() {
        return mrpSubCode;
    }

    public void setMrpSubCode(String mrpSubCode) {
        this.mrpSubCode = mrpSubCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
}
