package com.facenet.mrp.service.dto.mrp;

public class CurrentInventory {

    protected String itemCode;
    protected Double currentQuantity;

    public CurrentInventory() {
    }

    public CurrentInventory(String itemCode, Long currentQuantity) {
        this.itemCode = itemCode;
        this.currentQuantity = Double.valueOf(currentQuantity);
    }

    public CurrentInventory(String itemCode, Double currentQuantity) {
        this.itemCode = itemCode;
        this.currentQuantity = currentQuantity;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Double getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(Double currentQuantity) {
        this.currentQuantity = (currentQuantity);
    }
}
