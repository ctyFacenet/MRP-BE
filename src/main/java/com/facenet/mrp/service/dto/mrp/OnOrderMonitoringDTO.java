package com.facenet.mrp.service.dto.mrp;

import com.facenet.mrp.service.dto.ItemPriceOfVendorDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OnOrderMonitoringDTO implements Comparable<OnOrderMonitoringDTO> {

    private Integer id;
    private String itemCode;
    private String itemName;
    private String poCode;
    private String prCode;
    private Integer lineNumber;
    private String contractNumber;
    private String vendorCode;
    private String vendorName;
    private Date poDueDate;
    private Date poCreateDate;
    private String poCreateUser;
    private Double quantity;
    private Double remainQuantity;
    private Double warehouseQuantity;
    private Integer orderProgressPercent;
    private Integer overDateNumber;
    private String type;
    private String note;
    private String state;
    private Double percentRate;
    private String mrpCode;
    private String soCode;
    private Double purchaseQuantity;
    private Double comingQuantity;

    public OnOrderMonitoringDTO() {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MonitoringDetailDTO monitoringDetailDTO;

    @QueryProjection
    public OnOrderMonitoringDTO( String poCode, String prCode, String contractNumber, String vendorCode, String vendorName, Date poDueDate, Date poCreateDate, String poCreateUser,String type,String mrpCode,String soCode) {
        this.poCode = poCode;
        this.prCode = prCode;
        this.contractNumber = contractNumber;
        this.vendorCode = vendorCode;
        this.vendorName = vendorName;
        this.poDueDate = poDueDate;
        this.poCreateDate = poCreateDate;
        this.poCreateUser = poCreateUser;
        this.type = type;
        this.mrpCode = mrpCode;
        this.soCode = soCode;
    }

    @QueryProjection
    public OnOrderMonitoringDTO(Integer id,String itemCode, String itemName, String poCode, Integer lineNumber, Date poDueDate, Date poCreateDate, String poCreateUser,String type) {
        this.id = id;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.poCode = poCode;
        this.lineNumber = lineNumber;
        this.poDueDate = poDueDate;
        this.poCreateDate = poCreateDate;
        this.poCreateUser = poCreateUser;
        this.type = type;
    }

    public OnOrderMonitoringDTO(String itemCode, String itemName, String poCode, String prCode, String contractNumber, String vendorCode, String vendorName, Date poDueDate, Date poCreateDate, String poCreateUser, Double quantity, Integer orderProgressPercent, Integer overDateNumber,String type,String mrpCode,String soCode) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.poCode = poCode;
        this.prCode = prCode;
        this.contractNumber = contractNumber;
        this.vendorCode = vendorCode;
        this.vendorName = vendorName;
        this.poDueDate = poDueDate;
        this.poCreateDate = poCreateDate;
        this.poCreateUser = poCreateUser;
        this.quantity = quantity;
        this.orderProgressPercent = orderProgressPercent;
        this.overDateNumber = overDateNumber;
        this.type = type;
        this.mrpCode = mrpCode;
        this.soCode = soCode;
    }
    @Override
    public String toString() {
        return "ReceiptDTO{" +
            "poCode=" + poCode +
            '}';
    }

    public OnOrderMonitoringDTO(Integer id,String itemCode, String itemName, String poCode, String prCode, String contractNumber, String vendorCode, String vendorName, Date poDueDate, Date poCreateDate, String poCreateUser, Double quantity, Double remainQuantity, Double warehouseQuantity,Integer monitorId,Date detailDueDate, Double detailQuantity,String type, String note) {
        this.id = id;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.poCode = poCode;
        this.prCode = prCode;
        this.contractNumber = contractNumber;
        this.vendorCode = vendorCode;
        this.vendorName = vendorName;
        this.poDueDate = poDueDate;
        this.poCreateDate = poCreateDate;
        this.poCreateUser = poCreateUser;
        this.quantity = quantity;
        this.remainQuantity = remainQuantity;
        this.warehouseQuantity = warehouseQuantity;
        this.monitoringDetailDTO = new MonitoringDetailDTO(monitorId,detailDueDate, detailQuantity, note);
        this.type = type;
    }

    public Double getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(Double purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public Double getComingQuantity() {
        return comingQuantity;
    }

    public void setComingQuantity(Double comingQuantity) {
        this.comingQuantity = comingQuantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getPoCreateDate() {
        return poCreateDate;
    }

    public void setPoCreateDate(Date poCreateDate) {
        this.poCreateDate = poCreateDate;
    }

    public String getPoCreateUser() {
        return poCreateUser;
    }

    public void setPoCreateUser(String poCreateUser) {
        this.poCreateUser = poCreateUser;
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

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
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

    public Date getPoDueDate() {
        return poDueDate;
    }

    public void setPoDueDate(Date poDueDate) {
        this.poDueDate = poDueDate;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Integer getOrderProgressPercent() {
        return orderProgressPercent;
    }

    public void setOrderProgressPercent(Integer orderProgressPercent) {
        this.orderProgressPercent = orderProgressPercent;
    }

    public Integer getOverDateNumber() {
        return overDateNumber;
    }

    public void setOverDateNumber(Integer overDateNumber) {
        this.overDateNumber = overDateNumber;
    }

    public MonitoringDetailDTO getMonitoringDetailDTO() {
        return monitoringDetailDTO;
    }

    public void setMonitoringDetailDTO(MonitoringDetailDTO monitoringDetailDTO) {
        this.monitoringDetailDTO = monitoringDetailDTO;
    }

    public Double getRemainQuantity() {
        return remainQuantity;
    }

    public void setRemainQuantity(Double remainQuantity) {
        this.remainQuantity = remainQuantity;
    }

    public Double getWarehouseQuantity() {
        return warehouseQuantity;
    }

    public void setWarehouseQuantity(Double warehouseQuantity) {
        this.warehouseQuantity = warehouseQuantity;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPercentRate() {
        return percentRate;
    }

    public void setPercentRate(Double percentRate) {
        this.percentRate = percentRate;
    }

    public String getMrpCode() {
        return mrpCode;
    }

    public void setMrpCode(String mrpCode) {
        this.mrpCode = mrpCode;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    @Override
    public int compareTo( OnOrderMonitoringDTO o) {
        return this.getId().compareTo(o.getId());
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }
}
