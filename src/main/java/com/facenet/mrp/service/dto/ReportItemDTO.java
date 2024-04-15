package com.facenet.mrp.service.dto;

import com.querydsl.core.annotations.QueryProjection;

import java.time.Instant;
import java.util.Date;

public class ReportItemDTO extends PurchaseRecommendationDetailDTO{
    private String soCode;
    private String mrpCode;
    private Double percent;
    private Double poQuantity;
    private Double mrpQuantity;

    public ReportItemDTO() {
    }
    private Integer purchaseRecommendationId;

    public Integer getPurchaseRecommendationId() {
        return purchaseRecommendationId;
    }

    public void setPurchaseRecommendationId(Integer purchaseRecommendationId) {
        this.purchaseRecommendationId = purchaseRecommendationId;
    }

    @QueryProjection
    public ReportItemDTO(String itemCode, String itemDescription, Double quantity, Date receiveDate, String vendorCode, String vendorName, Double price, String currency, Boolean isPromotion, Date endTime, Integer status, String note, Instant createdAt, String soCode, String mrpCode, Integer purchaseRecommendationId) {
        super(itemCode, itemDescription, quantity, receiveDate, vendorCode, vendorName, price, currency, isPromotion, endTime, status, note, createdAt);
        this.soCode = soCode;
        this.mrpCode = mrpCode;
        this.purchaseRecommendationId = purchaseRecommendationId;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getMrpCode() {
        return mrpCode;
    }

    public void setMrpCode(String mrpCode) {
        this.mrpCode = mrpCode;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Double getPoQuantity() {
        return poQuantity;
    }

    public void setPoQuantity(Double poQuantity) {
        this.poQuantity = poQuantity;
    }

    public Double getMrpQuantity() {
        return mrpQuantity;
    }

    public void setMrpQuantity(Double mrpQuantity) {
        this.mrpQuantity = mrpQuantity;
    }
}
