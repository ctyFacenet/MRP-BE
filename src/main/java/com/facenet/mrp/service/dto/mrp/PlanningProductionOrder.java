package com.facenet.mrp.service.dto.mrp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;


public class PlanningProductionOrder {
    private UUID id;
    private String productOrderId;
    private Date completeDate;
    private Date createdDate;
    private Date expectedCompleteDate;
    private Date lastModifiedDate;
    private String note;
    private Date orderDate;
    private String productOrderType;
    private String orderItem;
    private String status;
    private String processStatus;
    private Integer reasonId;
    private String employeeCode;
    private String priority;
    private Integer quantity;
    private Integer quantityOut;
    private Date requestedStartDate;
    private String state;
    private String customerName;
    private String customerCode;
    private String externalPoId;
    private Integer scadaQuantityOut;
    private String typeOrder;
    private String original;
    private Integer scadaQuantityOut1;
    private String bomVersion;
    private String poCode;
    private Integer itemPriority;
    private Integer classify;
    private String productCode;
    private String productName;
    private String branchCode;
    private String groupCode;
    private Date startDate;
    private Date endDate;
    private Integer productType;
    private String partCode;
    private String partName;

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

    public String getPoCode() {
        return poCode;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public Integer getItemPriority() {
        return itemPriority;
    }

    public void setItemPriority(Integer itemPriority) {
        this.itemPriority = itemPriority;
    }

    public Integer getClassify() {
        return classify;
    }

    public void setClassify(Integer classify) {
        this.classify = classify;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public Integer getScadaQuantityOut() {
        return scadaQuantityOut;
    }

    public void setScadaQuantityOut(Integer scadaQuantityOut) {
        this.scadaQuantityOut = scadaQuantityOut;
    }

    public Integer getScadaQuantityOut1() {
        return scadaQuantityOut1;
    }

    public void setScadaQuantityOut1(Integer scadaQuantityOut1) {
        this.scadaQuantityOut1 = scadaQuantityOut1;
    }

    public String getTypeOrder() {
        return typeOrder;
    }

    public void setTypeOrder(String typeOrder) {
        this.typeOrder = typeOrder;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public Date getStartDate() {
        return startDate;
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

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getRequestedStartDate() {
        return requestedStartDate;
    }

    public void setRequestedStartDate(Date requestedStartDate) {
        this.requestedStartDate = requestedStartDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Date getExpectedCompleteDate() {
        return expectedCompleteDate;
    }

    public void setExpectedCompleteDate(Date expectedCompleteDate) {
        this.expectedCompleteDate = expectedCompleteDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public String getBomVersion() {
        return bomVersion;
    }

    public void setBomVersion(String bomVersion) {
        this.bomVersion = bomVersion;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProductOrderId() {
        return productOrderId;
    }

    public void setProductOrderId(String productOrderId) {
        this.productOrderId = productOrderId;
    }

    public String getExternalPoId() {
        return externalPoId;
    }

    public void setExternalPoId(String externalPoId) {
        this.externalPoId = externalPoId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getProductOrderType() {
        return productOrderType;
    }

    public void setProductOrderType(String productOrderType) {
        this.productOrderType = productOrderType;
    }

    public Integer getQuantityOut() {
        return quantityOut;
    }

    public void setQuantityOut(Integer quantityOut) {
        this.quantityOut = quantityOut;
    }

    public Integer getReasonId() {
        return reasonId;
    }

    public void setReasonId(Integer reasonId) {
        this.reasonId = reasonId;
    }

    @Override
    public String toString() {
        return "PlanningProductionOrder{" +
                "id=" + id +
                ", productOrderId='" + productOrderId + '\'' +
                ", bomVersion='" + bomVersion + '\'' +
                ", completeDate=" + completeDate +
                ", createdDate=" + createdDate +
                ", productOrderType='" + productOrderType + '\'' +
                ", status='" + status + '\'' +
                ", processStatus='" + processStatus + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", priority='" + priority + '\'' +
                ", quantity=" + quantity +
                ", branchCode='" + branchCode + '\'' +
                ", groupCode='" + groupCode + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", scadaQuantityOut=" + scadaQuantityOut +
                ", scadaQuantityOut1=" + scadaQuantityOut1 +
                ", original='" + original + '\'' +
                ", typeOrder='" + typeOrder + '\'' +
                '}';
    }
}
