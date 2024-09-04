package com.facenet.mrp.service.dto;

public class InventoryDetailDTO {
    private String vendorId;

    private String vendorName;

    private long inStockTotal;

    private long onOrder;

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public long getInStockTotal() {
        return inStockTotal;
    }

    public void setInStockTotal(long inStockTotal) {
        this.inStockTotal = inStockTotal;
    }

    public long getOnOrder() {
        return onOrder;
    }

    public void setOnOrder(long onOrder) {
        this.onOrder = onOrder;
    }

    public long getInStockMinimum() {
        return inStockMinimum;
    }

    public void setInStockMinimum(long inStockMinimum) {
        this.inStockMinimum = inStockMinimum;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    private long inStockMinimum;

    private long total;

    public InventoryDetailDTO(){

    }

    public InventoryDetailDTO(String vendorId, String vendorName, Long inStockTotal, Long onOrder, Long inStockMinimum, Long total){
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.inStockTotal = inStockTotal;
        this.onOrder = onOrder;
        this.inStockMinimum = inStockMinimum;
        this.total = total;
    }

    public InventoryDetailDTO(long inStockTotal) {
        this.inStockTotal = inStockTotal;
    }

    @Override
    public String toString() {
        return "ReceiptDTO{" +
            "vendorId=" + vendorId +
            ", vendorName='" + vendorName + '\'' +
            ", inStockTotal='" + inStockTotal + '\'' +
            ", onOrder=" + onOrder +
            ", inStockMinimum='" + inStockMinimum + '\'' +
            ", total='" + total + '\'' +
            '}';
    }
}
