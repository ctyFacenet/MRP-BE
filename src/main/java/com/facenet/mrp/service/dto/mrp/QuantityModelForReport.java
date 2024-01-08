package com.facenet.mrp.service.dto.mrp;

public class QuantityModelForReport {

    private double reqQuantity;
    private double expectedQuantity;

    public QuantityModelForReport(double reqQuantity, double expectedQuantity) {
        this.reqQuantity = reqQuantity;
        this.expectedQuantity = expectedQuantity;
    }


    public double getReqQuantity() {
        return reqQuantity;
    }

    public void setReqQuantity(double reqQuantity) {
        this.reqQuantity = reqQuantity;
    }

    public double getExpectedQuantity() {
        return expectedQuantity;
    }

    public void setExpectedQuantity(double expectedQuantity) {
        this.expectedQuantity = expectedQuantity;
    }
}
