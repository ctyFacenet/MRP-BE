package com.facenet.mrp.service.dto;

public class IndexAnalysisDTO {
    private String landmark;

    private Double originQuantity;

    private Double readyQuantity;

    private Double requiedQuantity;

    private Double expectedQuantity;

    private Double finalInventory;

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public Double getOriginQuantity() {
        return originQuantity;
    }

    public void setOriginQuantity(Double originQuantity) {
        this.originQuantity = originQuantity;
    }

    public Double getReadyQuantity() {
        return readyQuantity;
    }

    public void setReadyQuantity(Double readyQuantity) {
        this.readyQuantity = readyQuantity;
    }

    public Double getRequiedQuantity() {
        return requiedQuantity;
    }

    public void setRequiedQuantity(Double requiedQuantity) {
        this.requiedQuantity = requiedQuantity;
    }

    public Double getExpectedQuantity() {
        return expectedQuantity;
    }

    public void setExpectedQuantity(Double expectedQuantity) {
        this.expectedQuantity = expectedQuantity;
    }

    public Double getFinalInventory() {
        return finalInventory;
    }

    public void setFinalInventory(Double finalInventory) {
        this.finalInventory = finalInventory;
    }

    public IndexAnalysisDTO() {
    }

    public IndexAnalysisDTO(String landmark, Double originQuantity, Double readyQuantity, Double requiedQuantity, Double expectedQuantity, Double finalInventory) {
        this.landmark = landmark;
        this.originQuantity = originQuantity;
        this.readyQuantity = readyQuantity;
        this.requiedQuantity = requiedQuantity;
        this.expectedQuantity = expectedQuantity;
        this.finalInventory = finalInventory;
    }
}
