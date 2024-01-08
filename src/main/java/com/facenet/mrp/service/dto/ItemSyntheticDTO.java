package com.facenet.mrp.service.dto;

import com.facenet.mrp.service.dto.mrp.CurrentWarehouseInventory;

import java.util.ArrayList;
import java.util.List;

public class ItemSyntheticDTO {
    private String itemCode;
    private String itemName;
    private String altItemCode;
    private Double requestNumber;
    private Double requiredQuantity;
    private Double prNumber;
    private Double readyQuantity;
    private String type;
    private String bomVersion;
    private List<DetailItemSyntheticDTO> detailData;
    private List<CurrentWarehouseInventory> currentWarehouseInventoryList;
    private ArrayList<ViewBtpTpDTO> viewBtpTp;

    public String getBomVersion() {
        return bomVersion;
    }

    public void setBomVersion(String bomVersion) {
        this.bomVersion = bomVersion;
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

    public Double getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(Double requestNumber) {
        this.requestNumber = requestNumber;
    }

    public List<DetailItemSyntheticDTO> getDetailData() {
        return detailData;
    }

    public void setDetailData(List<DetailItemSyntheticDTO> detailData) {
        this.detailData = detailData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ItemSyntheticDTO() {

    }

    public Double getPrNumber() {
        return prNumber;
    }

    public void setPrNumber(Double prNumber) {
        this.prNumber = prNumber;
    }

    public ArrayList<ViewBtpTpDTO> getViewBtpTp() {
        return viewBtpTp;
    }

    public void setViewBtpTp(ArrayList<ViewBtpTpDTO> viewBtpTp) {
        this.viewBtpTp = viewBtpTp;
    }

    public Double getReadyQuantity() {
        return readyQuantity;
    }

    public void setReadyQuantity(Double readyQuantity) {
        this.readyQuantity = readyQuantity;
    }

    public List<CurrentWarehouseInventory> getCurrentWarehouseInventoryList() {
        return currentWarehouseInventoryList;
    }

    public void setCurrentWarehouseInventoryList(List<CurrentWarehouseInventory> currentWarehouseInventoryList) {
        this.currentWarehouseInventoryList = currentWarehouseInventoryList;
    }

    public Double getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(Double requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public String getAltItemCode() {
        return altItemCode;
    }

    public void setAltItemCode(String altItemCode) {
        this.altItemCode = altItemCode;
    }
}
