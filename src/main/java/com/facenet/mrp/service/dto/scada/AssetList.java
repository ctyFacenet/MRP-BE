package com.facenet.mrp.service.dto.scada;

import java.util.List;

public class AssetList {
    private List<ProductionLineDTO> data;
    private Long totalElements;

    public List<ProductionLineDTO> getData() {
        return data;
    }

    public void setData(List<ProductionLineDTO> data) {
        this.data = data;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }
}
