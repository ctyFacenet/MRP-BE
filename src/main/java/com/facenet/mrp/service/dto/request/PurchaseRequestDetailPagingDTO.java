package com.facenet.mrp.service.dto.request;

public class PurchaseRequestDetailPagingDTO
{
    String prCode;
    String soCode;
    String itemCode;
    String itemName;
    Double requiredQuantity;
    Double poPostedQuantity;
    String nccCode;
    String nccName;
    String dueDate;
    Double itemPrice;
    String unit;
    String status;

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

    public Double getPoPostedQuantity() {
        return poPostedQuantity;
    }

    public void setPoPostedQuantity(Double poPostedQuantity) {
        this.poPostedQuantity = poPostedQuantity;
    }

    public String getNccCode() {
        return nccCode;
    }

    public void setNccCode(String nccCode) {
        this.nccCode = nccCode;
    }

    public String getNccName() {
        return nccName;
    }

    public void setNccName(String nccName) {
        this.nccName = nccName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
