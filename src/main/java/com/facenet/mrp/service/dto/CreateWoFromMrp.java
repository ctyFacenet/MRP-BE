package com.facenet.mrp.service.dto;

import java.util.Date;
import java.util.List;

public class CreateWoFromMrp {
    private String kbsxType;
    private String soCode;
    private String productCode;
    private String productName;
    private Integer quantity;
    private Date startTime;
    private Date endTime;
    private String branch;
    private String group;
    private List<CreateWoFromMrp> children;

    public CreateWoFromMrp() {
    }

    public String getKbsxType() {
        return kbsxType;
    }

    public void setKbsxType(String kbsxType) {
        this.kbsxType = kbsxType;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<CreateWoFromMrp> getChildren() {
        return children;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public void setChildren(List<CreateWoFromMrp> children) {
        this.children = children;
    }
}
