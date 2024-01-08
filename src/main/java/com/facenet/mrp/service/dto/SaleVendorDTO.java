package com.facenet.mrp.service.dto;

import com.querydsl.core.annotations.QueryProjection;

public class SaleVendorDTO {
    private String saleCode;
    private String saleName;
    private String vendorCode;

    public String getSaleCode() {
        return saleCode;
    }

    public void setSaleCode(String saleCode) {
        this.saleCode = saleCode;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public SaleVendorDTO(){

    }

    @QueryProjection
    public SaleVendorDTO(String saleCode, String saleName, String vendorCode) {
        this.saleCode = saleCode;
        this.saleName = saleName;
        this.vendorCode = vendorCode;
    }

    @Override
    public String toString() {
        return "ListVendorDTO{" +
            "vendorCode=" + vendorCode +
            ", saleCode='" + saleCode + '\'' +
            ", saleName='" + saleName + '\'' +
            '}';
    }
}
