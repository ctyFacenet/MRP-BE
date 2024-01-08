package com.facenet.mrp.service.dto;

import com.querydsl.core.annotations.QueryProjection;

public class CheckSumDTO {
    private String itemCode;
    private Long count;

    public CheckSumDTO() {
    }

    @QueryProjection
    public CheckSumDTO(String itemCode, Long count) {
        this.itemCode = itemCode;
        this.count = count;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
