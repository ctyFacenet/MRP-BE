package com.facenet.mrp.service.dto;

public class ItemWithTypeDTO {
    private String itemCode;
    private String itemName;
    private String groupItem;
    private String unit;

    public ItemWithTypeDTO() {
    }

    public ItemWithTypeDTO(String itemCode, String itemName, String groupItem, String unit) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.groupItem = groupItem;
        this.unit = unit;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getGroupItem() {
        return groupItem;
    }

    public void setGroupItem(String groupItem) {
        this.groupItem = groupItem;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
