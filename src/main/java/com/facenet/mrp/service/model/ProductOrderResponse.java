package com.facenet.mrp.service.model;

import com.facenet.mrp.service.dto.ProductOrderDto;

import java.util.List;

public class ProductOrderResponse {

    private ResultCode result;
    private long dataCount;
    private List<ProductOrderDto> data;

    public ResultCode getResult() {
        return result;
    }

    public void setResult(ResultCode result) {
        this.result = result;
    }

    public long getDataCount() {
        return dataCount;
    }

    public void setDataCount(long dataCount) {
        this.dataCount = dataCount;
    }

    public List<ProductOrderDto> getData() {
        return data;
    }

    public void setData(List<ProductOrderDto> data) {
        this.data = data;
    }
}
