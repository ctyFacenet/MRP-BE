package com.facenet.mrp.service.dto.mrp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ItemQuantityWithDate {

    private String date;
    private String itemCode;
    private Double quantity;
    private String type;

    public ItemQuantityWithDate(String date, String itemCode, Double quantity, String type) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(java.sql.Date.valueOf(date));
        this.date = date;
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.type = type;
    }

    public ItemQuantityWithDate(String date, String itemCode, Double quantity) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(java.sql.Date.valueOf(date));
        this.date = date;
        this.itemCode = itemCode;
        this.quantity = quantity;
    }

    public ItemQuantityWithDate(Date date, String itemCode, Double quantity) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(java.sql.Date.valueOf(date));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.date = simpleDateFormat.format(date);
        this.itemCode = itemCode;
        this.quantity = quantity;
    }

    public ItemQuantityWithDate() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
