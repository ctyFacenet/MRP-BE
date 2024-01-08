package com.facenet.mrp.service.dto.mrp;

public class CurrentWarehouseInventory extends CurrentInventory {
    private String warehouseCode;

    public CurrentWarehouseInventory() {
    }

    public CurrentWarehouseInventory(String itemCode, Double currentQuantity, String warehouseCode) {
        super(itemCode, currentQuantity);
        this.warehouseCode = warehouseCode;
    }

    public CurrentWarehouseInventory(String itemCode, Long currentQuantity, String warehouseCode) {
        super(itemCode, currentQuantity);
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }
}
