package com.facenet.mrp.service.dto.mrp;

public class PlanPrForSo {

    private String mrpSubCode;

    private String itemCode;

    private Double totalExpectedQuantity;

    public PlanPrForSo() {
    }

    public PlanPrForSo(String mrpSubCode, String itemCode, Double totalExpectedQuantity) {
        this.mrpSubCode = mrpSubCode;
        this.itemCode = itemCode;
        this.totalExpectedQuantity = totalExpectedQuantity;
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

    public Double getTotalExpectedQuantity() {
        return totalExpectedQuantity;
    }

    public void setTotalExpectedQuantity(Double totalExpectedQuantity) {
        this.totalExpectedQuantity = totalExpectedQuantity;
    }
}
