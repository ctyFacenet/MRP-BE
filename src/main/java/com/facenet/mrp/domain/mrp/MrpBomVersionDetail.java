package com.facenet.mrp.domain.mrp;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "mrp_bom_version_detail", schema = "material_requirements_planning", catalog = "")
@EntityListeners(AuditingEntityListener.class)
public class MrpBomVersionDetail {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "bom_id", nullable = false)
    private Integer itemPriceId;

    @Column(name = "mrp_po_id")
    private String mrpPoId;

    @Column(name = "parent_path")
    private String parentPath;

    @Column(name = "item_group")
    private String itemGroup;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "warehouse")
    private String wareHouse;

    @Column(name = "level")
    private Integer level;

    @Column(name = "version")
    private String version;

    @Column(name = "item_code", length = 20)
    private String itemCode;

    @Column(name = "status")
    private String status;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_by", nullable = true, length = 255)
    @CreatedBy
    private String createdBy;

    @Column(name = "created_at", nullable = true)
    @CreatedDate
    private Timestamp createdAt;

    @Column(name = "updated_by", nullable = true, length = 255)
    @LastModifiedBy
    private String updatedBy;

    @Column(name = "updated_at", nullable = true)
    @LastModifiedDate
    private Timestamp updateAt;

    public Integer getItemPriceId() {
        return itemPriceId;
    }

    public void setItemPriceId(Integer itemPriceId) {
        this.itemPriceId = itemPriceId;
    }

    public String getMrpPoId() {
        return mrpPoId;
    }

    public void setMrpPoId(String mrpPoId) {
        this.mrpPoId = mrpPoId;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public String getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(String itemGroup) {
        this.itemGroup = itemGroup;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getWareHouse() {
        return wareHouse;
    }

    public void setWareHouse(String warehouse) {
        this.wareHouse = warehouse;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if ( status == null || StringUtils.isBlank(status) || StringUtils.isEmpty(status)){
            this.status = "O";
            return;
        }
        this.status = status;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        if (StringUtils.isEmpty(String.valueOf(active)) || active == null){
            this.isActive = true;
            return;
        }
        isActive = active;
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

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }
}
