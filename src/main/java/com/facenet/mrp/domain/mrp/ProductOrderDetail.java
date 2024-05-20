package com.facenet.mrp.domain.mrp;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "product_order_detail")
@EntityListeners(AuditingEntityListener.class)
public class ProductOrderDetail {
    @Id
    @Column(name = "product_order_detail_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "soSeqGen")
    @GenericGenerator(name = "soSeqGen", strategy = "increment")
    private Integer id;

    @Size(max = 20)
    @Column(name = "product_code", nullable = false, length = 20)
    private String productCode;

    @Column(name = "product_name")
    private String productName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_order_code", nullable = false, referencedColumnName = "mrp_po_id")
    private ProductOrder productOrderCode;

    @Column(name = "product_order_child")
    private String productOrderChild;
    @Column(name = "item_index")
    private Integer itemIndex;

    @Column(name = "material_children_count")
    private Integer materialChildrenCount;

    @NotNull
    @Min(value = 1, message = "order.detail.quantity.is.must.not.empty")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "order_date", nullable = false)
    private Date orderDate;

    @Column(name = "deliver_date", nullable = false)
    private Date deliverDate;

    @Size(max = 255)
    @Column(name = "supply_type")
    private String supplyType;

    @Range(min = 1, max = 3, message = "order.detail.priority.invalid")
    @Column(name = "priority")
    private Integer priority;

    @Size(max = 20)
    @Column(name = "bom_version", length = 20)
    private String bomVersion;

    @Column(name = "bom_status", nullable = false)
    private Byte bomStatus;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "status_planning", nullable = false)
    private Integer statusPlanning;

    @Column(name = "customer_code")
    private String customerCode;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "sale_code")
    private String saleCode;

    @Lob
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getItemIndex() {
        return itemIndex;
    }

    public void setItemIndex(Integer itemIndex) {
        this.itemIndex = itemIndex;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductOrder getProductOrderCode() {
        return productOrderCode;
    }

    public void setProductOrderCode(ProductOrder productOrderCode) {
        this.productOrderCode = productOrderCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
    }

    public String getSupplyType() {
        return supplyType;
    }

    public void setSupplyType(String supplyType) {
        if (StringUtils.isEmpty(supplyType)){
            this.supplyType = "MRP";
            return;
        }
        this.supplyType = supplyType;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        if (priority == null || priority.toString().isEmpty()){
            this.priority = 1;
            return;
        }
        this.priority = priority;
    }

    public String getBomVersion() {
        return bomVersion;
    }

    public void setBomVersion(String bomVersion) {
        this.bomVersion = bomVersion;
    }

    public Integer getStatusPlanning() {
        return statusPlanning;
    }

    public void setStatusPlanning(Integer statusPlanning) {
        this.statusPlanning = statusPlanning;
    }

    public Byte getBomStatus() {
        return bomStatus;
    }

    public void setBomStatus(Byte bomStatus) {
        this.bomStatus = bomStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getMaterialChildrenCount() {
        return materialChildrenCount;
    }

    public void setMaterialChildrenCount(Integer materialChildrenCount) {
        this.materialChildrenCount = materialChildrenCount;
    }

    public String getProductOrderChild() {
        return productOrderChild;
    }

    public void setProductOrderChild(String productOrderChild) {
        this.productOrderChild = productOrderChild;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSaleCode() {
        return saleCode;
    }

    public void setSaleCode(String saleCode) {
        this.saleCode = saleCode;
    }
}
