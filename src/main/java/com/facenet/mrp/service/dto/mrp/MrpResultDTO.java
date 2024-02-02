package com.facenet.mrp.service.dto.mrp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MrpResultDTO {

    private String landmark;
    private Double originQuantity;
    private Double readyQuantity;
    private Double requiredQuantity;
    private Double expectedQuantity;
    private Double inStockQuantity;
    private Double orderQuantity;
    private Double poQuantity;
    private Double deliveringQuantity;
    private Double sumPoAndDeliveringQuantity;
    private Double totalOriginQuantity;
    private Double needQuantity;

    public MrpResultDTO() {
    }

    public MrpResultDTO(Double originQuantity, Double readyQuantity, Double requiredQuantity, Double expectedQuantity) {
        this.originQuantity = originQuantity;
        this.readyQuantity = readyQuantity;
        this.requiredQuantity = requiredQuantity;
        this.expectedQuantity = expectedQuantity;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public Double getOriginQuantity() {
        return originQuantity == null ? 0.0 : originQuantity;
    }

    public void setOriginQuantity(Double originQuantity) {
        this.originQuantity = originQuantity;
    }

    public Double getReadyQuantity() {
        return readyQuantity == null ? 0.0 : readyQuantity;
    }

    public void setReadyQuantity(Double readyQuantity) {
        this.readyQuantity = readyQuantity;
    }

    public Double getRequiredQuantity() {
        return requiredQuantity == null ? 0.0 : requiredQuantity;
    }

    public void setRequiredQuantity(Double requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public Double getExpectedQuantity() {
        return expectedQuantity == null ? 0.0 : expectedQuantity;
    }

    public void setExpectedQuantity(Double expectedQuantity) {
        this.expectedQuantity = expectedQuantity;
    }

    public Double getInStockQuantity() {
        return inStockQuantity == null ? 0.0 : inStockQuantity;
    }

    public void setInStockQuantity(Double inStockQuantity) {
        this.inStockQuantity = inStockQuantity;
    }

    public Double getOrderQuantity() {
        return orderQuantity == null ? 0.0 : orderQuantity;
    }

    public void setOrderQuantity(Double orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public Double getPoQuantity() {
        return poQuantity == null ? 0.0 : poQuantity;
    }

    public void setPoQuantity(Double poQuantity) {
        this.poQuantity = poQuantity;
    }

    public Double getDeliveringQuantity() {
        return deliveringQuantity == null ? 0.0 : deliveringQuantity;
    }

    public void setDeliveringQuantity(Double deliveringQuantity) {
        this.deliveringQuantity = deliveringQuantity;
    }

    public Double getSumPoAndDeliveringQuantity() {
        if (this.sumPoAndDeliveringQuantity != null) return this.sumPoAndDeliveringQuantity;
        return getPoQuantity() + getDeliveringQuantity();
    }

    public void setSumPoAndDeliveringQuantity(Double sumPoAndDeliveringQuantity) {
        this.sumPoAndDeliveringQuantity = sumPoAndDeliveringQuantity;
    }

    public Double getTotalOriginQuantity() {
        return totalOriginQuantity == null ? getOriginQuantity() : totalOriginQuantity;
    }

    public void setTotalOriginQuantity(Double totalOriginQuantity) {
        this.totalOriginQuantity = totalOriginQuantity;
    }

    public Double getNeedQuantity() {
        return this.needQuantity == null ? 0.0 : this.needQuantity;
    }

    public void setNeedQuantity(Double needQuantity) {
        this.needQuantity = needQuantity;
    }
}
