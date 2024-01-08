package com.facenet.mrp.service.dto;

public class SaveRecommendationResponse {
    private Integer purchaseRecommendationId;
    private String mrpSubCode;
    private String soCode;

    public SaveRecommendationResponse() {
    }

    public SaveRecommendationResponse(Integer purchaseRecommendationId, String mrpSubCode, String soCode) {
        this.purchaseRecommendationId = purchaseRecommendationId;
        this.mrpSubCode = mrpSubCode;
        this.soCode = soCode;
    }

    public Integer getPurchaseRecommendationId() {
        return purchaseRecommendationId;
    }

    public void setPurchaseRecommendationId(Integer purchaseRecommendationId) {
        this.purchaseRecommendationId = purchaseRecommendationId;
    }

    public String getMrpSubCode() {
        return mrpSubCode;
    }

    public void setMrpSubCode(String mrpSubCode) {
        this.mrpSubCode = mrpSubCode;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }
}
