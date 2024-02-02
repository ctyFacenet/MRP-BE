package com.facenet.mrp.domain.mrp;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "product_order")
@EntityListeners(AuditingEntityListener.class)
public class ProductOrder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_order_id", nullable = false)
    private Integer id;

    @Column(name = "mrp_po_id")
    private String mrpPoId;

    @Size(max = 50)
    @Column(name = "product_order_code", nullable = false)
    private String productOrderCode;

    @Transient
    private String productCodeChild;
    @Transient
    private String productCode;
    @Transient
    private String productName;

    @Transient
    private String bomVersion;

    @Transient
    private Integer quantity;

    @Transient
    private Date startDate;
    @Transient
    private Date endDate;
    @Transient
    private String supplyType;
    @Transient
    private Integer priorityProduct;

    @Transient
    private String saleCode;

    @Size(max = 20)
    //@NotEmpty(message = "customer.id.can.not.empty")
    @Column(name = "customer_id", nullable = false, length = 20)
    private String customerId;

    //@NotBlank(message = "customer_name.can.not.empty")
    @Size(max = 255)
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Size(max = 255)
    @NotBlank(message = "product_order_type.can.not.empty")
    @Column(name = "product_order_type", nullable = false)
    private String productOrderType;

//    @NotBlank(message = "order_date.is.must.not.empty")
    @Column(name = "order_date", nullable = false)
    private Date orderDate;

//    @NotBlank(message = "deliver_date.is.must.not.empty")
    @Column(name = "deliver_date", nullable = false)
    private Date deliverDate;

    @Size(max = 20)
    @Column(name = "part_code", nullable = false, length = 20)
    private String partCode;

    @Size(max = 255)
    @Column(name = "part_name", nullable = false)
    private String partName;

    @Range(min = 1, max = 3, message = "order.priority.must.from.1.to.3")
    @Column(name = "priority", nullable = false)
    private Integer priority;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "state")
    private String state;

    @Column(name = "forecast_name", nullable = false)
    private String forecastName;

    @Column(name = "forecast_source", nullable = false)
    private String forecastSource;

    @Column(name = "forecast_mode", nullable = false)
    private String forecastMode;

    @Column(name = "warehouse_list")
    private String warehouseList;

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

    @Size(max = 255)
    @Column(name = "type")
    private String type;

    @Column(name = "forecast_land_mark", nullable = true, length = -1)
    private String forecastLandMark;

    public String getForecastLandMark() {
        return forecastLandMark;
    }

    public void setForecastLandMark(String forecastLandMark) {
        this.forecastLandMark = forecastLandMark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @OneToMany(mappedBy = "productOrderCode",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductOrderDetail> productOrderDetails = new ArrayList<>();

    public String getMrpPoId() {
        return mrpPoId;
    }

    public void setMrpPoId(String mrpPoId) {
        this.mrpPoId = mrpPoId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductOrderCode() {
        return productOrderCode;
    }

    public Integer getPriorityProduct() {
        return priorityProduct;
    }

    public void setPriorityProduct(Integer priorityProduct) {
        this.priorityProduct = priorityProduct == null ? 1 : priorityProduct;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getSupplyType() {
        return supplyType;
    }

    public void setSupplyType(String supplyType) {
        this.supplyType = StringUtils.isEmpty(supplyType) ? "MRP" : supplyType;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getBomVersion() {
        return bomVersion;
    }

    public void setBomVersion(String bomVersion) {
        this.bomVersion = bomVersion;
    }

    public void setProductOrderCode(String productOrderCode) {
        this.productOrderCode = productOrderCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductOrderType() {
        return productOrderType;
    }

    public void setProductOrderType(String productOrderType) {
        this.productOrderType = productOrderType;
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

    public String getPartCode() {
        return partCode;
    }

    public void setPartCode(String partCode) {
        this.partCode = partCode;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority == null ? 1 : priority;
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

    public List<ProductOrderDetail> getProductOrderDetails() {
        return productOrderDetails;
    }

    public void setProductOrderDetails(List<ProductOrderDetail> productOrderDetails) {
        this.productOrderDetails = productOrderDetails;
    }

    public String getForecastName() {
        return forecastName;
    }

    public void setForecastName(String forecastName) {
        this.forecastName = forecastName;
    }

    public String getForecastSource() {
        return forecastSource;
    }

    public void setForecastSource(String forecastSource) {
        this.forecastSource = forecastSource;
    }

    public String getForecastMode() {
        return forecastMode;
    }

    public void setForecastMode(String forecastMode) {
        this.forecastMode = forecastMode;
    }

    public String getWarehouseList() {
        return warehouseList;
    }

    public void setWarehouseList(String warehouseList) {
        this.warehouseList = warehouseList;
    }

    public String getProductCodeChild() {
        return productCodeChild;
    }

    public void setProductCodeChild(String productCodeChild) {
        this.productCodeChild = productCodeChild;
    }

    public String getSaleCode() {
        return saleCode;
    }

    public void setSaleCode(String saleCode) {
        this.saleCode = saleCode;
    }
}
