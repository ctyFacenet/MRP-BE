package com.facenet.mrp.service.dto.mrp;

public class CountPo {

    private String mrpPoId;
    private Long countMaterial;

    public CountPo(String mrpPoId, Long countMaterial) {
        this.mrpPoId = mrpPoId;
        this.countMaterial = countMaterial;
    }

    public String getMrpPoId() {
        return mrpPoId;
    }

    public void setMrpPoId(String mrpPoId) {
        this.mrpPoId = mrpPoId;
    }

    public Long getCountMaterial() {
        return countMaterial;
    }

    public void setCountMaterial(Long countMaterial) {
        this.countMaterial = countMaterial;
    }
}
