package com.facenet.mrp.service.dto;

public class OitwDTO {

    private Long onHand;
    private String whsCode;
    private String whsName;

    public String getWhsName() {
        return whsName;
    }

    public void setWhsName(String whsName) {
        this.whsName = whsName;
    }

    public Long getOnHand() {
        return onHand;
    }

    public void setOnHand(Long onHand) {
        this.onHand = onHand;
    }

    public String getWhsCode() {
        return whsCode;
    }

    public void setWhsCode(String whsCode) {
        this.whsCode = whsCode;
    }
}
