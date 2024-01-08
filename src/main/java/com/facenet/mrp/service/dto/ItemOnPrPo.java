package com.facenet.mrp.service.dto;

import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

import java.sql.Timestamp;
import java.util.Date;

import static org.aspectj.runtime.internal.Conversions.doubleValue;

public class ItemOnPrPo {
    private String poCode;
    private String prCode;
    private String mrpSubCode;
    private String itemCode;
    private String itemName;
    private Double quantity;
    private Date createdAt;
    private Date dueDate;
    private String createdBy;
    private String vendorCode;
    private String vendorName;
    private Double price;
    private String currency;


    public String getPoCode() {
        return poCode;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    public String getPrCode() {
        return prCode;
    }

    public void setPrCode(String prCode) {
        this.prCode = prCode;
    }

    public String getMrpSubCode() {
        return mrpSubCode;
    }

    public void setMrpSubCode(String mrpSubCode) {
        this.mrpSubCode = mrpSubCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public ItemOnPrPo() {
    }

    public ItemOnPrPo(String poCode, String prCode, String mrpSubCode, String itemCode, String itemName, Double quantity, Date createdAt, Date dueDate, String createdBy, String vendorCode, String vendorName, Double price, String currency) {
        this.poCode = poCode;
        this.prCode = prCode;
        this.mrpSubCode = mrpSubCode;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.dueDate = dueDate;
        this.createdBy = createdBy;
        this.vendorCode = vendorCode;
        this.vendorName = vendorName;
        this.price = price;
        this.currency = currency;
    }

    public ItemOnPrPo(String poCode, String prCode, String mrpSubCode, String itemCode, String itemName, Double quantity, Date createdAt, Date dueDate, String createdBy, String vendorCode, String vendorName) {
        this.poCode = poCode;
        this.prCode = prCode;
        this.mrpSubCode = mrpSubCode;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.dueDate = dueDate;
        this.createdBy = createdBy;
        this.vendorCode = vendorCode;
        this.vendorName = vendorName;
    }
}
