package com.facenet.mrp.service.dto;

import com.facenet.mrp.domain.mrp.ExecutionPlanReportDetailQuantityEntity;
import com.facenet.mrp.domain.mrp.ExecutionPlanReportEntity;

import java.util.List;

public class ReportDetailPlanDTO {
    private Long id;
    private String productCode;
    private String productName;
    private String version;
    private Integer totalQuantity;
    private Boolean isActive;
    private List<ExecutionPlanReportDetailQuantityEntity> planReportDetail;
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public List<ExecutionPlanReportDetailQuantityEntity> getPlanReportDetail() {
        return planReportDetail;
    }

    public void setPlanReportDetail(List<ExecutionPlanReportDetailQuantityEntity> planReportDetail) {
        this.planReportDetail = planReportDetail;
    }
}
