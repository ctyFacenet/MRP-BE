package com.facenet.mrp.service.model;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ApproveInput {
    @NotNull(message = "invalid.param")
    private Boolean isApproval;

    @NotNull(message = "invalid.param")
    private List<String> items;

    private String note;

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
}
