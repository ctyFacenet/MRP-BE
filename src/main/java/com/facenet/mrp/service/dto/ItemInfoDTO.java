package com.facenet.mrp.service.dto;

import com.facenet.mrp.domain.sap.OitmEntity;
import com.facenet.mrp.service.utils.Constants;

public class ItemInfoDTO {
    private String itemCode;
    private String itemName;
    private String technicalName;
    private String unit;
    private String group;
    private String groupName;

    public ItemInfoDTO() {
    }

    public ItemInfoDTO(OitmEntity item) {
        this.itemCode = item.getItemCode();
        this.itemName = item.getItemName();
        this.technicalName = item.getuTechName();
        this.unit = item.getInvntryUom();
        switch (item.getItmsGrpCod().getItmsGrpCode()) {
            case Constants.BTP:
                this.group = "BTP";
                break;
            case Constants.TP:
                this.group = "TP";
                break;
            default:
                this.group = "NVL";
        }
        this.groupName = item.getItmsGrpCod().getItmsGrpName();
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

    public String getTechnicalName() {
        return technicalName;
    }

    public void setTechnicalName(String technicalName) {
        this.technicalName = technicalName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
