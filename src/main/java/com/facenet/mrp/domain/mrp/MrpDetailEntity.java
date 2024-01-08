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
@Table(name = "mrp_detail", schema = "material_requirements_planning", catalog = "")
@EntityListeners(AuditingEntityListener.class)
public class MrpDetailEntity {
    private int mrpDetailId;
    private String mrpSubCode;
    private String itemGroup;
    private String itemCode;
    private String itemDescription;
    private Double baseQuantity;
    private Double requiredQuantity;
    private Double inStockQuantity;
    private Double prQuantity;
    private Double poQuantity;
    private Double estimateQuantity;
    private String warehouseName;
    private String bomVersion;
    private String level;
    private String status;
    private Byte isActive;
    private String createdBy;
    private Timestamp createdAt;
    private String updatedBy;
    private Timestamp updatedAt;
    private String parentPath;

    @Id
    @Column(name = "mrp_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getMrpDetailId() {
        return mrpDetailId;
    }

    public void setMrpDetailId(int mrpDetailId) {
        this.mrpDetailId = mrpDetailId;
    }

    @Basic
    @Column(name = "mrp_sub_code")
    public String getMrpSubCode() {
        return mrpSubCode;
    }

    public void setMrpSubCode(String mrpSubCode) {
        this.mrpSubCode = mrpSubCode;
    }

    @Basic
    @Column(name = "item_group")
    public String getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(String itemGroup) {
        this.itemGroup = itemGroup;
    }

    @Basic
    @Column(name = "item_code")
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Basic
    @Column(name = "item_description")
    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    @Basic
    @Column(name = "base_quantity")
    public Double getBaseQuantity() {
        return baseQuantity;
    }

    public void setBaseQuantity(Double baseQuantity) {
        this.baseQuantity = baseQuantity;
    }

    @Basic
    @Column(name = "required_quantity")
    public Double getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(Double requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    @Basic
    @Column(name = "in_stock_quantity")
    public Double getInStockQuantity() {
        return inStockQuantity;
    }

    public void setInStockQuantity(Double inStockQuantity) {
        this.inStockQuantity = inStockQuantity;
    }

    @Basic
    @Column(name = "pr_quantity")
    public Double getPrQuantity() {
        return prQuantity;
    }

    public void setPrQuantity(Double prQuantity) {
        this.prQuantity = prQuantity;
    }

    @Basic
    @Column(name = "po_quantity")
    public Double getPoQuantity() {
        return poQuantity;
    }

    public void setPoQuantity(Double poQuantity) {
        this.poQuantity = poQuantity;
    }

    @Basic
    @Column(name = "estimate_quantity")
    public Double getEstimateQuantity() {
        return estimateQuantity;
    }

    public void setEstimateQuantity(Double estimateQuantity) {
        this.estimateQuantity = estimateQuantity;
    }

    @Basic
    @Column(name = "warehouse_name")
    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    @Basic
    @Column(name = "bom_version")
    public String getBomVersion() {
        return bomVersion;
    }

    public void setBomVersion(String bomVersion) {
        this.bomVersion = bomVersion;
    }

    @Basic
    @Column(name = "level")
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "is_active")
    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    @Basic
    @Column(name = "created_by")
    @CreatedBy
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "created_at")
    @CreatedDate
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "updated_by")
    @LastModifiedBy
    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Basic
    @Column(name = "updated_at")
    @LastModifiedDate
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Basic
    @Column(name = "parent_path")
    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MrpDetailEntity that = (MrpDetailEntity) o;
        return mrpDetailId == that.mrpDetailId && Objects.equals(mrpSubCode, that.mrpSubCode) && Objects.equals(itemGroup, that.itemGroup) && Objects.equals(itemCode, that.itemCode) && Objects.equals(itemDescription, that.itemDescription) && Objects.equals(baseQuantity, that.baseQuantity) && Objects.equals(requiredQuantity, that.requiredQuantity) && Objects.equals(inStockQuantity, that.inStockQuantity) && Objects.equals(prQuantity, that.prQuantity) && Objects.equals(poQuantity, that.poQuantity) && Objects.equals(estimateQuantity, that.estimateQuantity) && Objects.equals(warehouseName, that.warehouseName) && Objects.equals(bomVersion, that.bomVersion) && Objects.equals(level, that.level) && Objects.equals(status, that.status) && Objects.equals(isActive, that.isActive) && Objects.equals(createdBy, that.createdBy) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedBy, that.updatedBy) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(parentPath, that.parentPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mrpDetailId, mrpSubCode, itemGroup, itemCode, itemDescription, baseQuantity, requiredQuantity, inStockQuantity, prQuantity, poQuantity, estimateQuantity, warehouseName, bomVersion, level, status, isActive, createdBy, createdAt, updatedBy, updatedAt, parentPath);
    }
}
