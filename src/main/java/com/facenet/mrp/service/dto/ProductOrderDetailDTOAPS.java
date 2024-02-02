package com.facenet.mrp.service.dto;

import com.facenet.mrp.service.dto.mrp.MrpResultDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductOrderDetailDTOAPS {

    private String productCode;
    private String productName;
    private Integer quantity;
    private String bomVersion;
    private Date startDate;
    private Date endDate;
    private String branchCode;
    private String groupCode;
    private String note;

    private List<MrpResultDTO> detailResult = new ArrayList<>();

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getBomVersion() {
        return bomVersion;
    }

    public void setBomVersion(String bomVersion) {
        this.bomVersion = bomVersion;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<MrpResultDTO> getDetailResult() {
        return detailResult;
    }

    public void setDetailResult(List<MrpResultDTO> detailResult) {
        this.detailResult = detailResult;
    }
}
