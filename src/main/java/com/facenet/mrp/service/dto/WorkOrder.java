package com.facenet.mrp.service.dto;

import java.util.Date;

public class WorkOrder {
    public String get_entityName() {
        return _entityName;
    }

    public void set_entityName(String _entityName) {
        this._entityName = _entityName;
    }

    public String get_instanceName() {
        return _instanceName;
    }

    public void set_instanceName(String _instanceName) {
        this._instanceName = _instanceName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkOrderType() {
        return workOrderType;
    }

    public void setWorkOrderType(String workOrderType) {
        this.workOrderType = workOrderType;
    }

    public Integer getPlanningWorkOrderId() {
        return planningWorkOrderId;
    }

    public void setPlanningWorkOrderId(Integer planningWorkOrderId) {
        this.planningWorkOrderId = planningWorkOrderId;
    }

    public String getProductOrder() {
        return productOrder;
    }

    public void setProductOrder(String productOrder) {
        this.productOrder = productOrder;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getWoId() {
        return woId;
    }

    public void setWoId(String woId) {
        this.woId = woId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getBomVersion() {
        return bomVersion;
    }

    public void setBomVersion(String bomVersion) {
        this.bomVersion = bomVersion;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getQuantityPlan() {
        return quantityPlan;
    }

    public void setQuantityPlan(Integer quantityPlan) {
        this.quantityPlan = quantityPlan;
    }

    public String getWorkOrderTypeName() {
        return workOrderTypeName;
    }

    public void setWorkOrderTypeName(String workOrderTypeName) {
        this.workOrderTypeName = workOrderTypeName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public WorkOrder() {
    }

    private String _entityName;
    private String _instanceName;
    private String id;
    private String workOrderType;
    private Integer planningWorkOrderId;
    private String productOrder;
    private String productName;
    private String state;
    private String groupCode;
    private String woId;
    private Integer priority;
    private String bomVersion;
    private String branchCode;
    private String groupName;
    private Integer quantityPlan;
    private String workOrderTypeName;
    private Date startTime;
    private Integer productType;
    private String branchName;
    private Integer isNew;
    private String productCode;
    private Date createTime;
    private Date endTime;

}
