package com.facenet.mrp.service.dto.mrp;

public class ItemQuantityWithType {
    private String itemCode;
    private Double quantity;
    private String type;

    public ItemQuantityWithType() {
    }

    public ItemQuantityWithType(String itemCode, Double quantity, String type) {
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.type = type;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
