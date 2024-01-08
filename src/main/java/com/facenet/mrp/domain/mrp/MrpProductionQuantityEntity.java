package com.facenet.mrp.domain.mrp;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "mrp_production_quantity", schema = "material_requirements_planning", catalog = "")
@EntityListeners(AuditingEntityListener.class)
public class MrpProductionQuantityEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
    @GenericGenerator(name = "seqGen", strategy = "increment")
    @Id
    @Column(name = "mrp_production_id")
    private Integer mrpProductionId;
    @Basic
    @Column(name = "so_code")
    private String soCode;
    @Basic
    @Column(name = "mrp_sub_code")
    private String mrpSubCode;
    @Basic
    @Column(name = "item_code")
    private String itemCode;

    @Basic
    @Column(name = "item_name")
    private String itemName;
    @Basic
    @Column(name = "bom_version")
    private String bomVersion;
    @Basic
    @Column(name = "item_group")
    private String itemGroup;
    @Basic
    @Column(name = "due_date")
    private Date dueDate;
    @Basic
    @Column(name = "production_quantity")
    private Double productionQuantity;
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
    @Column(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;
    @Basic
    @Column(name = "updated_by")
    @LastModifiedBy
    private String updatedBy;

    public Integer getMrpProductionId() {
        return mrpProductionId;
    }

    public void setMrpProductionId(Integer mrpProductionId) {
        this.mrpProductionId = mrpProductionId;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getMrpSubCode() {
        return mrpSubCode;
    }

    public void setMrpSubCode(String mrpSubCode) {
        this.mrpSubCode = mrpSubCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getBomVersion() {
        return bomVersion;
    }

    public void setBomVersion(String bomVersion) {
        this.bomVersion = bomVersion;
    }

    public String getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(String itemGroup) {
        this.itemGroup = itemGroup;
    }

    public Double getProductionQuantity() {
        return productionQuantity;
    }

    public void setProductionQuantity(Double productionQuantity) {
        this.productionQuantity = productionQuantity;
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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
