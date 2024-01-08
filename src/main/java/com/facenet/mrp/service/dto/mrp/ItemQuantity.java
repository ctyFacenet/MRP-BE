package com.facenet.mrp.service.dto.mrp;

public class ItemQuantity {
    private String itemCode;
    private Double quantity;

    public ItemQuantity() {
    }

    public ItemQuantity(String itemCode, Double quantity) {
        this.itemCode = itemCode;
        this.quantity = quantity;
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
}
