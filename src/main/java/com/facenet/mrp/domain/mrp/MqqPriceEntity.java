package com.facenet.mrp.domain.mrp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "mqq_price", schema = "material_requirements_planning", catalog = "")
@EntityListeners(AuditingEntityListener.class)
public class MqqPriceEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "item_price_id", nullable = false)
    private Integer itemPriceId;

    @Column(name = "vendor_code", nullable = true, length = 20)
    private String vendorCode;

    @Column(name = "item_code", nullable = true, length = 20)
    private String itemCode;

    @Column(name = "updated_at", nullable = true)
    @UpdateTimestamp
    private Timestamp updatedAt;

    @Column(name = "time_start", nullable = true)
    private Date timeStart;

    @Column(name = "time_end", nullable = true)
    private Date timeEnd;

    @Column(name = "range_start", nullable = true)
    private Integer rangeStart;

    @Column(name = "range_end", nullable = true)
    private Integer rangeEnd;

    @Column(name = "price", nullable = true)
    private Double price;

    @Column(name = "currency", nullable = true, length = 20)
    private String currency;

    @Column(name = "note", nullable = true, length = -1)
    private String note;

    @Column(name = "is_active")
    private Byte isActive = 1;

    @Column(name = "is_promotion", nullable = true)
    private Boolean isPromotion;

    @Column(name = "new", nullable = true)
    private Boolean checkNew;

    @Column(name = "created_by", nullable = true, length = 255)
    @CreatedBy
    private String createdBy;

    @Column(name = "created_at", nullable = true, updatable = false)
    @CreatedDate
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_by", nullable = true, length = 255)
    @LastModifiedBy
    private String updatedBy;

    @Transient
    private Integer leadTime;

    @Transient
    private String leadTimeNote;

    public Integer getItemPriceId() {
        return itemPriceId;
    }

    public void setItemPriceId(Integer itemPriceId) {
        this.itemPriceId = itemPriceId;
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

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        if (currency.equals("VNĐ")){
            this.currency = "VND";
        }else {
            this.currency = currency;
        }
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Boolean getCheckNew() {
        return checkNew;
    }

    public void setCheckNew(Boolean checkNew) {
        this.checkNew = checkNew;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MqqPriceEntity that = (MqqPriceEntity) o;
        return Objects.equals(itemPriceId, that.itemPriceId) && Objects.equals(vendorCode, that.vendorCode) && Objects.equals(itemCode, that.itemCode) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(timeStart, that.timeStart) && Objects.equals(timeEnd, that.timeEnd) && Objects.equals(rangeStart, that.rangeStart) && Objects.equals(rangeEnd, that.rangeEnd) && Objects.equals(price, that.price) && Objects.equals(currency, that.currency) && Objects.equals(note, that.note) && Objects.equals(isActive, that.isActive) && Objects.equals(createdBy, that.createdBy) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedBy, that.updatedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemPriceId, vendorCode, itemCode, updatedAt, timeStart, timeEnd, rangeStart, rangeEnd, price, currency, note, isActive, createdBy, createdAt, updatedBy);
    }

    public Boolean getIsPromotion() {
        return isPromotion;
    }

    public void setIsPromotion(Boolean isPromotion) {
        this.isPromotion = isPromotion;
    }

    public Integer getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(Integer leadTime) {
        this.leadTime = leadTime;
    }

    public String getLeadTimeNote() {
        return leadTimeNote;
    }

    public void setLeadTimeNote(String leadTimeNote) {
        this.leadTimeNote = leadTimeNote;
    }
}
