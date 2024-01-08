package com.facenet.mrp.domain.mrp;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "sale", schema = "material_requirements_planning", catalog = "")
@EntityListeners(AuditingEntityListener.class)
public class SaleEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "sale_id", nullable = false)
    private Integer saleId;

    @Column(name = "vendor_code", nullable = true, length = 20)
    private String vendorCode;

    @Column(name = "sale_code", nullable = true, length = 20)
    private String saleCode;

    @Column(name = "sale_name", nullable = true, length = 255)
    private String saleName;

    @Column(name = "is_active", nullable = true)
    private Byte isActive = 1;

    @Column(name = "created_at", nullable = true, updatable = false)
    @CreatedDate
    private Timestamp createdAt;

    @Column(name = "created_by", nullable = true, length = 255)
    @CreatedBy
    private String createdBy;

    @Column(name = "updated_at", nullable = true)
    @LastModifiedDate
    private Timestamp updatedAt;

    @Column(name = "updated_by", nullable = true, length = 255)
    @LastModifiedBy
    private String updatedBy;

    public SaleEntity(String vendorCode, String saleCode, String saleName) {
        this.vendorCode = vendorCode;
        this.saleCode = saleCode;
        this.saleName = saleName;
    }

    public SaleEntity() {
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getSaleCode() {
        return saleCode;
    }

    public void setSaleCode(String saleCode) {
        this.saleCode = saleCode;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleEntity that = (SaleEntity) o;
        return Objects.equals(saleId, that.saleId) && Objects.equals(vendorCode, that.vendorCode) && Objects.equals(saleCode, that.saleCode) && Objects.equals(saleName, that.saleName) && Objects.equals(isActive, that.isActive) && Objects.equals(createdAt, that.createdAt) && Objects.equals(createdBy, that.createdBy) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(updatedBy, that.updatedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saleId, vendorCode, saleCode, saleName, isActive, createdAt, createdBy, updatedAt, updatedBy);
    }
}
