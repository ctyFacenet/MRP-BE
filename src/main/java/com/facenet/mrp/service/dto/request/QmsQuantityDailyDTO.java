package com.facenet.mrp.service.dto.request;

import com.querydsl.core.annotations.QueryProjection;

import java.util.Date;

public class QmsQuantityDailyDTO {
    private String productionCode;
    private String productionName;
    private String purchaseOrderCode;
    private String workOrderId;
    private String quantityPlan;
    private String checkDate;
    private String quatityStore;
    private Date importDate;

    @QueryProjection
    public QmsQuantityDailyDTO(String productionCode, String productionName, String purchaseOrderCode, String workOrderId, String quantityPlan, String checkDate, String quatityStore,Date importDate) {
        this.productionCode = productionCode;
        this.productionName = productionName;
        this.purchaseOrderCode = purchaseOrderCode;
        this.workOrderId = workOrderId;
        this.quantityPlan = quantityPlan;
        this.checkDate = checkDate;
        this.quatityStore = quatityStore;
        this.importDate = importDate;
    }

    public QmsQuantityDailyDTO() {
    }

    public String getProductionCode() {
        return productionCode;
    }

    public void setProductionCode(String productionCode) {
        this.productionCode = productionCode;
    }

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }

    public String getPurchaseOrderCode() {
        return purchaseOrderCode;
    }

    public void setPurchaseOrderCode(String purchaseOrderCode) {
        this.purchaseOrderCode = purchaseOrderCode;
    }

    public String getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getQuantityPlan() {
        return quantityPlan;
    }

    public void setQuantityPlan(String quantityPlan) {
        this.quantityPlan = quantityPlan;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getQuatityStore() {
        return quatityStore;
    }

    public void setQuatityStore(String quatityStore) {
        this.quatityStore = quatityStore;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }
}
