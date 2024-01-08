package com.facenet.mrp.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SaleVendorForm {
    private String saleCode;

    private String saleName;

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

    @JsonCreator
    public SaleVendorForm(@JsonProperty("saleCode") String saleCode, @JsonProperty("saleName") String saleName) {
        this.saleCode = saleCode;
        this.saleName = saleName;
    }
}
