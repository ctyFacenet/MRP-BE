package com.facenet.mrp.service.dto;

import com.facenet.mrp.domain.sap.OitmEntity;

public class OitmDTO {

    private String productId;
    private String name;
    private String group;
    private String type;
    private String unit;
    private String technicalName;
    private Long totalInStock;
    private String groupName;
    private String status;

    public OitmDTO(OitmEntity oitmEntity){
        this.productId = oitmEntity.getItemCode();
        this.name = oitmEntity.getItemName();
        this.groupName = oitmEntity.getuIGroupName();
        this.group = oitmEntity.getItmsGrpCod() == null ? null : oitmEntity.getItmsGrpCod().getItmsGrpName();
        this.type = oitmEntity.getuSubgr();
        this.unit = oitmEntity.getBuyUnitMsr();
        this.technicalName = oitmEntity.getuTechName();
        this.status = oitmEntity.getAsstStatus();
        this.totalInStock = oitmEntity.getSum() == null ? 0 : oitmEntity.getSum();
    }
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTechnicalName() {
        return technicalName;
    }

    public void setTechnicalName(String technicalName) {
        this.technicalName = technicalName;
    }

    public Long getTotalInStock() {
        return totalInStock;
    }

    public void setTotalInStock(Long totalInStock) {
        this.totalInStock = totalInStock;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
