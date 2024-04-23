package com.facenet.mrp.service.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.sql.Date;
public class BomDTO {
    private String productCode;
    private String description;
    private String version;
    private String speciality;
    private String remark;
    private String docUrl;
    private Date fromDate;
    private Date toDate;
    private Date createTime;
    private String status;
    private Long quantity;
    private String warehouse;
    private Double quota;
    private Integer groupItem;
    private Integer level;
    private String root;

    public Double getQuota() {
        return quota;
    }

    public void setQuota(Double quota) {
        this.quota = quota;
    }

    public BomDTO() {
    }

    @QueryProjection
    public BomDTO(Double quota,String productCode, String description, String version, String speciality, String remark, String docUrl, Date fromDate, Date toDate, Date createTime, String status, Long quantity, String warehouse,Integer groupItem) {
        this.productCode = productCode;
        this.description = description;
        this.version = version;
        this.speciality = speciality;
        this.remark = remark;
        this.docUrl = docUrl;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.createTime = createTime;
        this.status = status;
        this.quantity = quantity;
        this.warehouse = warehouse;
        this.quota = quota;
        this.groupItem = groupItem;
    }

    public BomDTO(Double quota,String productCode, String description, String version, String speciality, String remark, String docUrl, Date fromDate, Date toDate, Date createTime, String status, String warehouse,Integer groupItem,Integer level) {
        this.productCode = productCode;
        this.description = description;
        this.version = version;
        this.speciality = speciality;
        this.remark = remark;
        this.docUrl = docUrl;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.createTime = createTime;
        this.status = status;
        this.warehouse = warehouse;
        this.quota = quota;
        this.groupItem = groupItem;
        this.level = level;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public Integer getGroupItem() {
        return groupItem;
    }

    public void setGroupItem(Integer groupItem) {
        this.groupItem = groupItem;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    @Override
    public String toString() {
        return "ReceiptDTO{" +
            "vendor=" + productCode +
            ", soId='" + level + '\'' +
            ", productId='" + docUrl + '\'' +
            ", description=" + description +
            '}';
    }
}
