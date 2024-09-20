package com.facenet.mrp.service.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO for {@link com.facenet.mrp.domain.mrp.PurchaseRequestDetailEntity}
 */
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequestDetailEntityDto implements Serializable {
    Integer id;
    String prCode;
    String soCode;
    String itemCode;
    String itemName;
    Double requiredQuantity;
    Timestamp dueDate;
    String note;
    String mrpCode;
    Integer isActive;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrCode() {
        return prCode;
    }

    public void setPrCode(String prCode) {
        this.prCode = prCode;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
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

    public Double getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(Double requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public Timestamp getDueDate() {
        return dueDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMrpCode() {
        return mrpCode;
    }

    public void setMrpCode(String mrpCode) {
        this.mrpCode = mrpCode;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
}
