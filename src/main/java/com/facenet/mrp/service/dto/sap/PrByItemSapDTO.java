package com.facenet.mrp.service.dto.sap;

import com.querydsl.core.annotations.QueryProjection;

public class PrByItemSapDTO {
    private String itemCode;
    private Long quantity;
    private String itemName;

    public PrByItemSapDTO() {
    }

    @QueryProjection
    public PrByItemSapDTO(String itemCode, Long quantity, String itemName) {
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.itemName = itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
