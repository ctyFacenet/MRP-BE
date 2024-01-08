package com.facenet.mrp.service.dto;

import java.util.Objects;

public class DetailItemSyntheticDTO {
    private String landMark;
    private Double requiredQuantity;
    private Double expectedQuantity;
    private Double originQuantity;
    private Double originalRequiredQuantity;
    private Double sumPoAndDeliveringQuantity;

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public Double getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(Double requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public Double getExpectedQuantity() {
        return expectedQuantity;
    }

    public void setExpectedQuantity(Double expectedQuantity) {
        this.expectedQuantity = Objects.requireNonNullElse(expectedQuantity, 0.0);
    }

    public Double getOriginQuantity() {
        return originQuantity;
    }

    public void setOriginQuantity(Double originQuantity) {
        this.originQuantity = originQuantity;
    }

    public Double getOriginalRequiredQuantity() {
        return originalRequiredQuantity;
    }

    public void setOriginalRequiredQuantity(Double originalRequiredQuantity) {
        this.originalRequiredQuantity = originalRequiredQuantity;
    }

    public Double getSumPoAndDeliveringQuantity() {
        return sumPoAndDeliveringQuantity;
    }

    public void setSumPoAndDeliveringQuantity(Double sumPoAndDeliveringQuantity) {
        this.sumPoAndDeliveringQuantity = sumPoAndDeliveringQuantity;
    }

    @Override
    public String toString() {
        return "DetailItemSyntheticDTO{" +
            "landMark='" + landMark + '\'' +
            ", needQuantity=" + originQuantity +
            ", holdQuantity=" + originalRequiredQuantity +
            ", prQuantity=" + sumPoAndDeliveringQuantity +
            '}';
    }
}
