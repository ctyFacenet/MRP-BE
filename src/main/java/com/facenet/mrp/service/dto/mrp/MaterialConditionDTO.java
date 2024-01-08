package com.facenet.mrp.service.dto.mrp;

import com.querydsl.core.annotations.QueryProjection;

import java.util.Date;

public class MaterialConditionDTO {
    private String soCode;
    private String mrpSubCode;
    private Double quantityHold;
    private Date requestDate;

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getMrpSubCode() {
        return mrpSubCode;
    }

    public void setMrpSubCode(String mrpSubCode) {
        this.mrpSubCode = mrpSubCode;
    }

    public Double getQuantityHold() {
        return quantityHold;
    }

    public void setQuantityHold(Double quantityHold) {
        this.quantityHold = quantityHold;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public MaterialConditionDTO() {
    }


    @QueryProjection
    public MaterialConditionDTO(String soCode, String mrpSubCode, Double quantityHold) {
        this.soCode = soCode;
        this.mrpSubCode = mrpSubCode;
        this.quantityHold = quantityHold;
    }

    @Override
    public String toString() {
        return "ReceiptDTO{" +
            "soCode=" + soCode +
            ", mrpSubCode='" + mrpSubCode + '\'' +
            ", quantityHold='" + quantityHold + '\'' +
            '}';
    }
}
