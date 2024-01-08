package com.facenet.mrp.service.model;

import com.facenet.mrp.service.dto.ForecastOrderDetailDTO;
import com.facenet.mrp.service.dto.ProductOrderDetailDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class ProductOrderDetailResponse {

    private ResultCode result;
    private long dataCount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ForecastOrderDetailDTO> fcData;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ProductOrderDetailDto> data;

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

    public List<ProductOrderDetailDto> getData() {
        return data;
    }

    public void setData(List<ProductOrderDetailDto> data) {
        this.data = data;
    }

    public List<ForecastOrderDetailDTO> getFcData() {
        return fcData;
    }

    public void setFcData(List<ForecastOrderDetailDTO> fcData) {
        this.fcData = fcData;
    }
}
