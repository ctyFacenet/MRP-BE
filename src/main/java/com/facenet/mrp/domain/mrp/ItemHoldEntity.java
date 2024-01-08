package com.facenet.mrp.domain.mrp;

import com.facenet.mrp.service.utils.Constants;
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
@Table(name = "item_hold", schema = "material_requirements_planning", catalog = "")
@EntityListeners(AuditingEntityListener.class)
public class ItemHoldEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemHoldSeqGen")
    @GenericGenerator(name = "itemHoldSeqGen", strategy = "increment")
    @Id
    @Column(name = "item_hold_id")
    private Integer itemHoldId;

    @Basic
    @Column(name = "purchase_recommendation_id")
    private Integer purchaseRecommendationId;

    @Basic
    @Column(name = "item_code")
    private String itemCode;
    @Basic
    @Column(name = "mrp_sub_code")
    private String mrpSubCode;

    @Basic
    @Column(name = "product_order_code")
    private String productOrderCode;

    @Basic
    @Column(name = "quantity")
    private Double quantity;

    @Basic
    @Column(name = "warehouse_code")
    private String warehouseCode;

    @Basic
    @Column(name = "analysis_period")
    private String analysisPeriod;

    @Basic
    @Column(name = "status")
    private Integer status = Constants.ItemHold.NEW;

    @Basic
    @Column(name = "hold_date")
    private Date holdDate;

    @Basic
    @Column(name = "is_active")
    private Boolean isActive = true;

    @Basic
    @Column(name = "created_at")
    @CreatedDate
    private Instant createdAt;
    @Basic
    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;
    @Basic
    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;
    @Basic
    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    public Integer getItemHoldId() {
        return itemHoldId;
    }

    public void setItemHoldId(Integer itemHoldId) {
        this.itemHoldId = itemHoldId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getMrpSubCode() {
        return mrpSubCode;
    }

    public void setMrpSubCode(String mrpSubCode) {
        this.mrpSubCode = mrpSubCode;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getProductOrderCode() {
        return productOrderCode;
    }

    public void setProductOrderCode(String productOrderCode) {
        this.productOrderCode = productOrderCode;
    }

    public Integer getPurchaseRecommendationId() {
        return purchaseRecommendationId;
    }

    public void setPurchaseRecommendationId(Integer purchaseRecommendationId) {
        this.purchaseRecommendationId = purchaseRecommendationId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getHoldDate() {
        return holdDate;
    }

    public void setHoldDate(Date holdDate) {
        this.holdDate = holdDate;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getAnalysisPeriod() {
        return analysisPeriod;
    }

    public void setAnalysisPeriod(String analysisPeriod) {
        this.analysisPeriod = analysisPeriod;
    }
}
