package com.facenet.mrp.domain.mrp;

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
@Table(name = "promotion", schema = "material_requirements_planning", catalog = "")
@EntityListeners(AuditingEntityListener.class)
public class PromotionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "promotion_id", nullable = false)
    private Integer promotionId;

    @Column(name = "vendor_code", nullable = true, length = 20)
    private String vendorCode;

    @Column(name = "item_code", nullable = true, length = 20)
    private String itemCode;

    @Column(name = "time_start", nullable = true)
    private Date timeStart;

    @Column(name = "time_end", nullable = true)
    private Date timeEnd;

    @Column(name = "range_start", nullable = true)
    private Integer rangeStart;

    @Column(name = "range_end", nullable = true)
    private Integer rangeEnd;

    @Column(name = "price", nullable = true)
    private Integer price;

    @Column(name = "currency", nullable = true, length = 10)
    private String currency;

    @Column(name = "note", nullable = true, length = -1)
    private String note;

    @Column(name = "unit", nullable = true, length = 20)
    private String unit;

    @Column(name = "is_active", nullable = true)
    private Byte isActive;

    @Column(name = "created_by", nullable = true, length = 255)
    @CreatedBy
    private String createdBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_by", nullable = true, length = 255)
    @LastModifiedBy
    private String updatedBy;

    @Column(name = "updated_at", nullable = true)
    @LastModifiedDate
    private Instant updatedAt;

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Integer getRangeStart() {
        return rangeStart;
    }

    public void setRangeStart(Integer rangeStart) {
        this.rangeStart = rangeStart;
    }

    public Integer getRangeEnd() {
        return rangeEnd;
    }

    public void setRangeEnd(Integer rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromotionEntity that = (PromotionEntity) o;
        return Objects.equals(promotionId, that.promotionId) && Objects.equals(vendorCode, that.vendorCode) && Objects.equals(itemCode, that.itemCode) && Objects.equals(timeStart, that.timeStart) && Objects.equals(timeEnd, that.timeEnd) && Objects.equals(rangeStart, that.rangeStart) && Objects.equals(rangeEnd, that.rangeEnd) && Objects.equals(price, that.price) && Objects.equals(currency, that.currency) && Objects.equals(note, that.note) && Objects.equals(unit, that.unit) && Objects.equals(isActive, that.isActive) && Objects.equals(createdBy, that.createdBy) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedBy, that.updatedBy) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(promotionId, vendorCode, itemCode, timeStart, timeEnd, rangeStart, rangeEnd, price, currency, note, unit, isActive, createdBy, createdAt, updatedBy, updatedAt);
    }
}
