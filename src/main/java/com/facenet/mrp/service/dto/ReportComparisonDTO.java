package com.facenet.mrp.service.dto;

import java.util.ArrayList;
import java.util.List;

public class ReportComparisonDTO {
    private String soCode;
    private String mrpCode;
    private String itemCode;
    private String itemName;
    private String version;
    private Double totalRequiredQuantity;
    private Integer status;
    private List<ComparisonQuantityDTO> compareStats = new ArrayList<>();

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getMrpCode() {
        return mrpCode;
    }

    public void setMrpCode(String mrpCode) {
        this.mrpCode = mrpCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Double getTotalRequiredQuantity() {
        return totalRequiredQuantity;
    }

    public void setTotalRequiredQuantity(Double totalRequiredQuantity) {
        this.totalRequiredQuantity = totalRequiredQuantity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ComparisonQuantityDTO> getCompareStats() {
        return compareStats;
    }

    public void setCompareStats(List<ComparisonQuantityDTO> compareStats) {
        this.compareStats = compareStats;
    }
}
