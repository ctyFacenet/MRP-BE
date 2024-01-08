package com.facenet.mrp.domain.mrp;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "purchase_recommendation_batch", schema = "material_requirements_planning", catalog = "")
@EntityListeners(AuditingEntityListener.class)
public class PurchaseRecommendationBatch {
    @Id
    @Column(name = "purchase_recommendation_batch_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
    @GenericGenerator(name = "seqGen", strategy = "increment")
    private Integer purchaseRecommendationBatchId;

    @Column(name = "purchase_recommendation_detail_id")
    private Integer purchaseRecommendationDetailId;

    @Column(name = "batch")
    private Integer batch;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "receive_date")
    private Date receiveDate;

    @Column(name = "moq_price_id")
    private Integer moqPriceId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "note")
    private String note;

    @Column(name = "plan_note")
    private String planNote;

    @Column(name = "analysis_result")
    private String analysisResult;
    @Column(name = "created_at")
    @CreatedDate
    private Instant createdAt;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;

    @Column(name = "updated_by")
    @LastModifiedBy
    private String updatedBy;

    @Transient
    private Double price = Double.MAX_VALUE;

    public Integer getPurchaseRecommendationBatchId() {
        return purchaseRecommendationBatchId;
    }

    public void setPurchaseRecommendationBatchId(Integer purchaseRecommendationBatchId) {
        this.purchaseRecommendationBatchId = purchaseRecommendationBatchId;
    }

    public Integer getPurchaseRecommendationDetailId() {
        return purchaseRecommendationDetailId;
    }

    public void setPurchaseRecommendationDetailId(Integer purchaseRecommendationDetailId) {
        this.purchaseRecommendationDetailId = purchaseRecommendationDetailId;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Integer getMoqPriceId() {
        return moqPriceId;
    }

    public void setMoqPriceId(Integer moqPriceId) {
        this.moqPriceId = moqPriceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPlanNote() {
        return planNote;
    }

    public void setPlanNote(String planNote) {
        this.planNote = planNote;
    }

    public String getAnalysisResult() {
        return analysisResult;
    }

    public void setAnalysisResult(String analysisResult) {
        this.analysisResult = analysisResult;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
