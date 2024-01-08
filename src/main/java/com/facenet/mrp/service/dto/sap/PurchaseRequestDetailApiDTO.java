package com.facenet.mrp.service.dto.sap;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PurchaseRequestDetailApiDTO {
//    {
//        "SapCode": "00002298",
//            "VendorCode": "V900000013",
//            "QuantityReq": 1,
//            "RegDate": "01/03/2023",
//            "WhCode":"IQC"
//    }
    @JsonProperty("SapCode")
    private String sapCode;

    @JsonProperty("VendorCode")
    private String vendorCode;

    @JsonProperty("QuantityReq")
    private Double requiredQuantity;

    @JsonProperty("RegDate")
    private String regDate;

    @JsonProperty("WhCode")
    private String whsCode;

    public PurchaseRequestDetailApiDTO() {
    }

    public PurchaseRequestDetailApiDTO(String sapCode, String vendorCode, Double requiredQuantity, Date regDate) {
        this.sapCode = sapCode;
        this.vendorCode = vendorCode;
        this.requiredQuantity = requiredQuantity;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        this.regDate = simpleDateFormat.format(regDate);
        System.err.println("RegDate from DB " + regDate);
        System.err.println("RegDate after format " + this.regDate);
        this.whsCode = "IQC";
    }

    @Override
    public String toString() {
        return "PurchaseRequestDetailApiDTO{" +
            "sapCode='" + sapCode + '\'' +
            ", vendorCode='" + vendorCode + '\'' +
            ", requiredQuantity=" + requiredQuantity +
            ", regDate='" + regDate + '\'' +
            ", whsCode='" + whsCode + '\'' +
            '}';
    }

    public String getSapCode() {
        return sapCode;
    }

    public void setSapCode(String sapCode) {
        this.sapCode = sapCode;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public Double getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(Double requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getWhsCode() {
        return whsCode;
    }

    public void setWhsCode(String whsCode) {
        this.whsCode = whsCode;
    }
}
