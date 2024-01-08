package com.facenet.mrp.domain.mrp;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "purchase_recommendation_purchase_plan", schema = "material_requirements_planning", catalog = "")
@EntityListeners(AuditingEntityListener.class)
public class PurchaseRecommendationPurchasePlanEntity {
    @Id
    @Column(name = "recommendation_purchase_plan_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "planSeqGen")
    @GenericGenerator(name = "planSeqGen", strategy = "increment")
    private int recommendationPurchasePlanId;
    @Basic
    @Column(name = "purchase_recommendation_detail_id", nullable = true)
    private Integer purchaseRecommendationDetailId;
    @Basic
    @Column(name = "item_code", nullable = true, length = 100)
    private String itemCode;
    @Basic
    @Column(name = "batch", nullable = true, length = 100)
    private Integer batch;
    @Basic
    @Column(name = "required_quantity", nullable = true, precision = 0)
    private Double requiredQuantity;
    @Basic
    @Column(name = "due_date", nullable = true)
    private Date dueDate;
    @Basic
    @Column(name = "status", nullable = true)
    private Integer status;
    @Basic
    @Column(name = "is_active", nullable = true)
    private boolean isActive = true;
    @CreatedBy
    @Column(name = "created_by", nullable = true, length = 100)
    private String createdBy;
    @LastModifiedDate
    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;
    @LastModifiedBy
    @Column(name = "updated_by", nullable = true, length = 100)
    private String updatedBy;
    @CreatedDate
    @Column(name = "created_at", nullable = true)
    private Date createdAt;

    @Column(name = "note",nullable = true)
    private String note;

    public int getRecommendationPurchasePlanId() {
        return recommendationPurchasePlanId;
    }

    public void setRecommendationPurchasePlanId(int recommendationPurchasePlanId) {
        this.recommendationPurchasePlanId = recommendationPurchasePlanId;
    }

    public Integer getPurchaseRecommendationDetailId() {
        return purchaseRecommendationDetailId;
    }

    public void setPurchaseRecommendationDetailId(Integer purchaseRecommendationDetailId) {
        this.purchaseRecommendationDetailId = purchaseRecommendationDetailId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Double getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(Double requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseRecommendationPurchasePlanEntity that = (PurchaseRecommendationPurchasePlanEntity) o;
        return recommendationPurchasePlanId == that.recommendationPurchasePlanId && Objects.equals(purchaseRecommendationDetailId, that.purchaseRecommendationDetailId) && Objects.equals(itemCode, that.itemCode) && Objects.equals(requiredQuantity, that.requiredQuantity) && Objects.equals(dueDate, that.dueDate) && Objects.equals(isActive, that.isActive) && Objects.equals(createdBy, that.createdBy) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(updatedBy, that.updatedBy) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recommendationPurchasePlanId, purchaseRecommendationDetailId, itemCode, requiredQuantity, dueDate, isActive, createdBy, updatedAt, updatedBy, createdAt);
    }
}
