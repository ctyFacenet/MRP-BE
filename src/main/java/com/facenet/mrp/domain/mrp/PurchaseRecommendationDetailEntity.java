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
@Table(name = "purchase_recommendation_detail", schema = "material_requirements_planning", catalog = "")
@EntityListeners(AuditingEntityListener.class)
public class PurchaseRecommendationDetailEntity {
    @Id
    @Column(name = "purchase_recommendation_detail_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
    @GenericGenerator(name = "seqGen", strategy = "increment")
    private Integer purchaseRecommendationDetailId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "purchase_recommendation_id", nullable = false, referencedColumnName = "purchase_recommendation_id")
    private PurchaseRecommendationEntity purchaseRecommendation;

    @Column(name = "purchase_recommendation_id", insertable = false, updatable = false)
    private Integer purchaseRecommendationId;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "required_quantity")
    private Double requiredQuantity;

    @Column(name = "receive_date")
    private Date receiveDate;

//    @OneToOne
//    @JoinColumn(name = "moq_price_id", referencedColumnName = "item_price_id")
    @Column(name = "moq_price_id")
    private Integer moqPriceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moq_price_id", referencedColumnName = "item_price_id", insertable = false, updatable = false)
    private MqqPriceEntity moqPriceEntity;

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

    public Integer getPurchaseRecommendationDetailId() {
        return purchaseRecommendationDetailId;
    }

    public void setPurchaseRecommendationDetailId(Integer purchaseRecommendationDetailId) {
        this.purchaseRecommendationDetailId = purchaseRecommendationDetailId;
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

    public PurchaseRecommendationEntity getPurchaseRecommendation() {
        return purchaseRecommendation;
    }

    public void setPurchaseRecommendation(PurchaseRecommendationEntity purchaseRecommendation) {
        this.purchaseRecommendation = purchaseRecommendation;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPlanNote() {
        return planNote;
    }

    public void setPlanNote(String planNote) {
        this.planNote = planNote;
    }

    public Integer getPurchaseRecommendationId() {
        return purchaseRecommendationId;
    }

    public void setPurchaseRecommendationId(Integer purchaseRecommendationId) {
        this.purchaseRecommendationId = purchaseRecommendationId;
    }

    public Double getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(Double requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public MqqPriceEntity getMoqPriceEntity() {
        return moqPriceEntity;
    }

    public void setMoqPriceEntity(MqqPriceEntity moqPriceEntity) {
        this.moqPriceEntity = moqPriceEntity;
    }
}
