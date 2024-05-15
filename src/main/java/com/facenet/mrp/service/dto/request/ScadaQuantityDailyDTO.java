package com.facenet.mrp.service.dto.request;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;

public class ScadaQuantityDailyDTO {
    private String productCode;
    private String productName;
    private String woName;
    private String sapWO;
    private LocalDate date;
    private Double packingQuantity;
    private String mrpCode;

    public String getMrpCode() {
        return mrpCode;
    }

    public void setMrpCode(String mrpCode) {
        this.mrpCode = mrpCode;
    }

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

    public String getWoName() {
        return woName;
    }

    public void setWoName(String woName) {
        this.woName = woName;
    }

    public String getSapWO() {
        return sapWO;
    }

    public void setSapWO(String sapWO) {
        this.sapWO = sapWO;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getPackingQuantity() {
        return packingQuantity;
    }

    public void setPackingQuantity(Double packingQuantity) {
        this.packingQuantity = packingQuantity;
    }

    @QueryProjection
    public ScadaQuantityDailyDTO(String productCode, String productName, String woName, String sapWO, LocalDate date, Double packingQuantity, String mrpCode) {
        this.productCode = productCode;
        this.productName = productName;
        this.woName = woName;
        this.sapWO = sapWO;
        this.date = date;
        this.packingQuantity = packingQuantity;
        this.mrpCode = mrpCode;
    }

    public ScadaQuantityDailyDTO() {
    }
}
