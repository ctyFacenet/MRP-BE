package com.facenet.mrp.service.dto.mrp;

public class MrpItemQuantityDTO {
    private Integer itemId;
    private String itemCode;
    private Integer quantity;
    private String bomVersion;

    public MrpItemQuantityDTO(Integer itemId, String itemCode, Integer quantity, String bomVersion) {
        this.itemId = itemId;
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.bomVersion = bomVersion;
    }

    public MrpItemQuantityDTO(Integer itemId, String itemCode, Double quantity, String bomVersion) {
        this.itemCode = itemCode;
        this.quantity = quantity.intValue();
        this.bomVersion = bomVersion;
    }

    public MrpItemQuantityDTO() {
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getBomVersion() {
        return bomVersion;
    }

    public void setBomVersion(String bomVersion) {
        this.bomVersion = bomVersion;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
}
