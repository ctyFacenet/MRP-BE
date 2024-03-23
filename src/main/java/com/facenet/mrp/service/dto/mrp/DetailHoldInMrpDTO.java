package com.facenet.mrp.service.dto.mrp;

import com.facenet.mrp.service.dto.DetailItemSyntheticDTO;
import com.facenet.mrp.service.dto.ViewBtpTpDTO;

import java.util.ArrayList;
import java.util.List;

public class DetailHoldInMrpDTO {
    private String itemCode;
    private String itemName;
    private String altItemCode;
    private Double requestNumber;
    private Double requiredQuantity;
    private Double prNumber;
    private Double readyQuantity;
    private String type;
    private String bomVersion;
    private Double requestQuantity;
    private Double totalHoldQuantity;
    private List<DetailItemSyntheticDTO> detailData;
    private List<CurrentWarehouseInventory> currentWarehouseInventoryList;
    private ArrayList<ViewBtpTpDTO> viewBtpTp;

    public List<DetailItemSyntheticDTO> getDetailData() {
        return detailData;
    }

    public void setDetailData(List<DetailItemSyntheticDTO> detailData) {
        this.detailData = detailData;
    }

    public List<CurrentWarehouseInventory> getCurrentWarehouseInventoryList() {
        return currentWarehouseInventoryList;
    }

    public void setCurrentWarehouseInventoryList(List<CurrentWarehouseInventory> currentWarehouseInventoryList) {
        this.currentWarehouseInventoryList = currentWarehouseInventoryList;
    }

    public ArrayList<ViewBtpTpDTO> getViewBtpTp() {
        return viewBtpTp;
    }

    public void setViewBtpTp(ArrayList<ViewBtpTpDTO> viewBtpTp) {
        this.viewBtpTp = viewBtpTp;
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

    public Double getRequestQuantity() {
        return requestQuantity;
    }

    public void setRequestQuantity(Double requestQuantity) {
        this.requestQuantity = requestQuantity;
    }

    public Double getTotalHoldQuantity() {
        return totalHoldQuantity;
    }

    public void setTotalHoldQuantity(Double totalHoldQuantity) {
        this.totalHoldQuantity = totalHoldQuantity;
    }

    public DetailHoldInMrpDTO() {
    }


    public String getAltItemCode() {
        return altItemCode;
    }

    public void setAltItemCode(String altItemCode) {
        this.altItemCode = altItemCode;
    }

    public Double getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(Double requestNumber) {
        this.requestNumber = requestNumber;
    }

    public Double getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(Double requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public Double getPrNumber() {
        return prNumber;
    }

    public void setPrNumber(Double prNumber) {
        this.prNumber = prNumber;
    }

    public Double getReadyQuantity() {
        return readyQuantity;
    }

    public void setReadyQuantity(Double readyQuantity) {
        this.readyQuantity = readyQuantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBomVersion() {
        return bomVersion;
    }

    public void setBomVersion(String bomVersion) {
        this.bomVersion = bomVersion;
    }
}
