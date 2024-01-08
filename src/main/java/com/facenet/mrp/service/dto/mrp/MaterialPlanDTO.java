package com.facenet.mrp.service.dto.mrp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

public class MaterialPlanDTO {

    private Integer id;
    private String itemCode;
    private String itemName;
    private String groupItem;
    private Integer levelPriorityItem;
    private String note;
    private Date itemStartTime;
    private Date itemEndTime;
    private Integer status;
    private String bomVersion;
    private Double currentInventory;
    private Double totalRequest;
    private Boolean checked; // cho phần gửi khuyến nghị/phân tích
    private List<LandMarkDTO> listLandMark;
    @JsonIgnore
    private boolean isValidItem = true;

    public Double getTotalRequest() {
        return totalRequest;
    }

    public void setTotalRequest(Double totalRequest) {
        this.totalRequest = totalRequest;
    }

    public Double getCurrentInventory() {
        return currentInventory;
    }

    public void setCurrentInventory(Double currentInventory) {
        this.currentInventory = currentInventory;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getGroupItem() {
        return groupItem;
    }

    public void setGroupItem(String groupItem) {
        this.groupItem = groupItem;
    }

    public Integer getLevelPriorityItem() {
        return levelPriorityItem;
    }

    public void setLevelPriorityItem(Integer levelPriorityItem) {
        this.levelPriorityItem = levelPriorityItem;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<LandMarkDTO> getListLandMark() {
        return listLandMark;
    }

    public void setListLandMark(List<LandMarkDTO> listLandMark) {
        this.listLandMark = listLandMark;
    }

    public Date getItemStartTime() {
        return itemStartTime;
    }

    public void setItemStartTime(Date itemStartTime) {
        this.itemStartTime = itemStartTime;
    }

    public Date getItemEndTime() {
        return itemEndTime;
    }

    public void setItemEndTime(Date itemEndTime) {
        this.itemEndTime = itemEndTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public boolean isValidItem() {
        return isValidItem;
    }

    public void setValidItem(boolean validItem) {
        isValidItem = validItem;
    }
}
