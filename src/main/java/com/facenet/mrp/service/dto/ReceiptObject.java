package com.facenet.mrp.service.dto;

import java.util.Date;

public class ReceiptObject {
    private String vendor;

    private Date createdAt;

    private String productId;

    private String description;

    private long quantity;

    private long price;

    private String currency;

    private String receiptId;

    //private String status;

    public ReceiptObject(){

    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }

    public ReceiptObject(String vendor, Date createdAt, String productId, String description, Long quantity, Long price, String currency, String receiptId){
        this.vendor = vendor;
        this.createdAt = createdAt;
        this.productId = productId;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.currency = currency;
        this.receiptId = receiptId;
        //this.status = status;
    }


    @Override
    public String toString() {
        return "ReceiptDTO{" +
            "vendor=" + vendor +
            ", createdAt='" + createdAt + '\'' +
            ", productId='" + productId + '\'' +
            ", description=" + description +
            ", quantity='" + quantity + '\'' +
            ", price='" + price + '\'' +
            ", currency=" + currency +
            ", receiptId='" + receiptId + '\'' +
            //", status='" + status + '\'' +
            '}';
    }
}
