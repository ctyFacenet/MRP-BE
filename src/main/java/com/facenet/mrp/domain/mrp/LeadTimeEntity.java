package com.facenet.mrp.domain.mrp;

import org.hibernate.annotations.Formula;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "leadtime")
@EntityListeners(AuditingEntityListener.class)
public class LeadTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leadtime_id", nullable = false)
    private Integer id;

    @Size(max = 20)
    @Column(name = "vendor_code", nullable = false, length = 20)
    private String vendorCode;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "leadtime")
    private Integer leadTime;

    @Formula(value = "(select m.price from mqq_price as m " +
        "join leadtime l on m.vendor_code = l.vendor_code " +
        "where m.vendor_code = vendor_code  " +
        "and m.item_code = item_code  " +
        "and m.is_promotion = 0  " +
        "and m.is_active= 1 " +
        "and cast(m.time_start as DATE) <= current_date order by m.time_start desc, m.price asc limit 1 )")
    private Double mqqPriceMin;

    @Formula(value = "(select m.currency from mqq_price as m join leadtime as l on m.vendor_code = l.vendor_code where m.vendor_code = vendor_code and m.item_code = item_code and m.is_promotion = 0 and m.is_active=1 order by MIN(m.price))")
    private String currency;

    @Formula(value = "(select m.time_end from mqq_price as m join leadtime as l on m.vendor_code = l.vendor_code where m.vendor_code = vendor_code and m.item_code = item_code and m.is_active = 1 limit 1 )")
    private Date timeEnd;

    @Column(name = "note")
    private String note;

    @Column(name = "is_active", nullable = false)
    private Byte isActive = 1;

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Size(max = 255)
    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;

    @Size(max = 255)
    @Column(name = "updated_by")
    @LastModifiedBy
    private String updatedBy;

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(Integer leadTime) {
        this.leadTime = leadTime;
    }

    public Double getMqqPriceMin() {
        return mqqPriceMin;
    }

    public void setMqqPriceMin(Double mqqPriceMin) {
        this.mqqPriceMin = mqqPriceMin;
    }

    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
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


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
