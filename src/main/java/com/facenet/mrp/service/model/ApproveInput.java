package com.facenet.mrp.service.model;

import com.facenet.mrp.service.dto.PurchaseRequestEntityDto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ApproveInput {
    @NotNull(message = "invalid.param")
    private Boolean isApproval;

    @NotNull(message = "invalid.param")
    private List<String> items;

    private String note;

    private PurchaseRequestEntityDto purchaseRequestEntityDto;

    public Boolean getIsApproval() {
        return isApproval;
    }

    public void setApproval(Boolean approval) {
        isApproval = approval;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getApproval() {
        return isApproval;
    }

    public PurchaseRequestEntityDto getPurchaseRequestEntityDto() {
        return purchaseRequestEntityDto;
    }

    public void setPurchaseRequestEntityDto(PurchaseRequestEntityDto purchaseRequestEntityDto) {
        this.purchaseRequestEntityDto = purchaseRequestEntityDto;
    }
}
