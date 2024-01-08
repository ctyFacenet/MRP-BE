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
import java.util.Objects;

@Entity
@Table(name = "forecast_order_detail", schema = "material_requirements_planning", catalog = "")
@EntityListeners(AuditingEntityListener.class)
public class ForecastOrderDetailEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
    @GenericGenerator(name = "seqGen", strategy = "increment")
    @Id
    @Column(name = "forecast_order_detail_id", nullable = false)
    private Integer forecastOrderDetailId;

    @Column(name = "product_order_code", nullable = false, length = 50)
    private String productOrderCode;

    @Column(name = "item_code", nullable = true, length = 20)
    private String itemCode;

    @Column(name = "item_description", nullable = true, length = 255)
    private String itemDescription;

    @Column(name = "item_group_code", nullable = true, length = 20)
    private String itemGroupCode;

    @Column(name = "bom_version", nullable = true, length = 20)
    private String bomVersion;

    @Column(name = "quantity", nullable = true, precision = 0)
    private Double quantity;

    @Column(name = "total_request", nullable = true, precision = 0)
    private Double totalRequest;

    @Column(name = "start_time", nullable = true)
    private Date startTime;

    @Column(name = "end_time", nullable = true)
    private Date endTime;

    @Column(name = "priority", nullable = true)
    private Integer priority;

    @Column(name = "detail_result", nullable = true, length = -1)
    private String detailResult;

    @Column(name = "material_children_count")
    private Integer materialChildrenCount;

    @Column(name = "note", nullable = true, length = -1)
    private String note;

    @Column(name = "status", nullable = true)
    private Integer status;

    @Column(name = "is_active", nullable = true)
    private Boolean isActive;

    @CreatedBy
    @Column(name = "created_by", nullable = true, length = 255)
    private String createdBy;

    @CreatedDate
    @Column(name = "created_at", nullable = true)
    private Instant createdAt;

    @LastModifiedBy
    @Column(name = "updated_by", nullable = true, length = 255)
    private String updatedBy;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = true)
    private Instant updatedAt;

    public Double getTotalRequest() {
        return totalRequest;
    }

    public void setTotalRequest(Double totalRequest) {
        this.totalRequest = totalRequest;
    }

    public Integer getForecastOrderDetailId() {
        return forecastOrderDetailId;
    }

    public void setForecastOrderDetailId(Integer forecastOrderDetailId) {
        this.forecastOrderDetailId = forecastOrderDetailId;
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

    public String getItemGroupCode() {
        return itemGroupCode;
    }

    public void setItemGroupCode(String itemGroupCode) {
        this.itemGroupCode = itemGroupCode;
    }

    public String getBomVersion() {
        return bomVersion;
    }

    public void setBomVersion(String bomVersion) {
        this.bomVersion = bomVersion;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getDetailResult() {
        return detailResult;
    }

    public void setDetailResult(String detailResult) {
        this.detailResult = detailResult;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getProductOrderCode() {
        return productOrderCode;
    }

    public void setProductOrderCode(String productOrderCode) {
        this.productOrderCode = productOrderCode;
    }

    public Integer getMaterialChildrenCount() {
        return materialChildrenCount;
    }

    public void setMaterialChildrenCount(Integer materialChildrenCount) {
        this.materialChildrenCount = materialChildrenCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForecastOrderDetailEntity that = (ForecastOrderDetailEntity) o;
        return Objects.equals(forecastOrderDetailId, that.forecastOrderDetailId) && Objects.equals(itemCode, that.itemCode) && Objects.equals(itemDescription, that.itemDescription) && Objects.equals(itemGroupCode, that.itemGroupCode) && Objects.equals(bomVersion, that.bomVersion) && Objects.equals(quantity, that.quantity) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(priority, that.priority) && Objects.equals(detailResult, that.detailResult) && Objects.equals(note, that.note) && Objects.equals(status, that.status) && Objects.equals(createdBy, that.createdBy) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedBy, that.updatedBy) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(forecastOrderDetailId, itemCode, itemDescription, itemGroupCode, bomVersion, quantity, startTime, endTime, priority, detailResult, note, status, createdBy, createdAt, updatedBy, updatedAt);
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }
}
