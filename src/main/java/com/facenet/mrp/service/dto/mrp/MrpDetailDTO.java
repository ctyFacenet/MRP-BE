package com.facenet.mrp.service.dto.mrp;

import com.facenet.mrp.service.utils.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MrpDetailDTO {
    @JsonIgnore
    public static final String BTP = "BTP";
    @JsonIgnore
    public static final String NVL = "NVL";
    @JsonIgnore
    public static final String TP = "TP";
    private Integer groupItem;
    private String itemCode;
    private String itemName;
    private String altItemCode;
    private Double quota;
    private String whsName;
    private String bomVersion;
    private String level;
    private String status;
    private String parentPath;
    private Boolean isHold = false;
    private Double requiredQuantity;
    private Double inStockQuantity;
    private List<CurrentWarehouseInventory> currentWarehouseInventoryList;
    private List<MrpResultDTO> detailResult = new ArrayList<>();
    private List<MrpDetailDTO> children = new ArrayList<>();

    public MrpDetailDTO() {
    }

    public MrpDetailDTO(MrpDetailDTO that) {
        this.itemCode = that.getItemCode();
        this.itemName = that.getItemName();
        this.altItemCode = that.getAltItemCode();
        this.quota = that.getQuota();
        this.bomVersion = that.getBomVersion();
        this.groupItem = that.getGroupItemInt();
    }

    public MrpDetailDTO( String itemCode, String itemName, Double quota, String bomVersion, String itemGroup) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.quota = quota;
        this.bomVersion = bomVersion;
        if ("BTP".equals(itemGroup)) this.groupItem = Constants.BTP;
        else this.groupItem = -1;
    }

    public MrpDetailDTO( String itemCode, String itemName, Double quota, String bomVersion, String itemGroup, String parentPath) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.quota = quota;
        this.bomVersion = bomVersion;
        this.parentPath = parentPath;
        if ("BTP".equals(itemGroup)) this.groupItem = Constants.BTP;
        else this.groupItem = -1;
    }

    public MrpDetailDTO( String itemCode, String itemName, String altItemCode,Double quota, String bomVersion, Integer groupItem) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.altItemCode = altItemCode;
        this.quota = quota;
        this.bomVersion = bomVersion;
        this.groupItem = groupItem;
    }

    public MrpDetailDTO( String itemCode, String itemName, Long quota, String bomVersion) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.quota = Double.valueOf(quota);
        this.bomVersion = bomVersion;
    }

    public MrpDetailDTO( String itemCode, String itemName, Double quota, String bomVersion, Integer groupItem) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.quota = quota;
        this.bomVersion = bomVersion;
        this.groupItem = groupItem;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public String getGroupItem() {
        if (groupItem == null) return "NVL";
        switch (groupItem) {
            case Constants.BTP:
                return "BTP";
            case Constants.TP:
                return "TP";
            default:
                return "NVL";
        }
    }



    @JsonIgnore
    public Integer getGroupItemInt() {
        return groupItem;
    }

    public void setGroupItem(Integer groupItem) {
        this.groupItem = groupItem;
    }

    public void setGroupItem(String groupItem) {
        switch (groupItem) {
            case "TP":
                this.groupItem = Constants.TP;
                break;
            case "BTP":
                this.groupItem = Constants.BTP;
                break;
            default:
                this.groupItem = -1;
                break;
        }
    }

    @Override
    public String toString() {
        return "MrpDetailDTO{" +
            "groupItem=" + groupItem +
            ", itemCode='" + itemCode + '\'' +
            ", bomVersion='" + bomVersion + '\'' +
            '}';
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


    public Double getQuota() {
        return quota;
    }

    public void setQuota(Double quota) {
        this.quota = quota;
    }

    public String getWhsName() {
        return whsName;
    }

    public void setWhsName(String whsName) {
        this.whsName = whsName;
    }

    public String getBomVersion() {
        return bomVersion;
    }

    public void setBomVersion(String bomVersion) {
        this.bomVersion = bomVersion;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStatus() {
        return groupItem == Constants.TP || groupItem == Constants.BTP ? "Sản xuất" : "Mua hàng";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(Double requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public Double getInStockQuantity() {
        return inStockQuantity == null ? 0.0 : inStockQuantity;
    }

    public void setInStockQuantity(Double inStockQuantity) {
        this.inStockQuantity = inStockQuantity;
    }

    public List<MrpResultDTO> getDetailResult() {
        return detailResult;
    }

    public void setDetailResult(List<MrpResultDTO> detailResult) {
        this.detailResult = detailResult;
    }

    public List<MrpDetailDTO> getChildren() {
        return children;
    }

    public void setChildren(List<MrpDetailDTO> children) {
        this.children = children;
    }

    public Boolean getIsHold() {
        return isHold;
    }

    public void setIsHold(Boolean hold) {
        isHold = hold;
    }

    public List<CurrentWarehouseInventory> getCurrentWarehouseInventoryList() {
        return currentWarehouseInventoryList;
    }

    public void setCurrentWarehouseInventoryList(List<CurrentWarehouseInventory> currentWarehouseInventoryList) {
        this.currentWarehouseInventoryList = currentWarehouseInventoryList;
    }

    public String getAltItemCode() {
        return altItemCode;
    }

    public void setAltItemCode(String altItemCode) {
        this.altItemCode = altItemCode;
    }
}
