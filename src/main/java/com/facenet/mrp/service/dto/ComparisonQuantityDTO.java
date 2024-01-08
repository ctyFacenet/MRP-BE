package com.facenet.mrp.service.dto;

public class ComparisonQuantityDTO {
    private String landmark;
    private Double mrpQuantity;
    private Double importQuantity;
    private Double importWhsQMS;

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public Double getMrpQuantity() {
        return mrpQuantity;
    }

    public void setMrpQuantity(Double mrpQuantity) {
        this.mrpQuantity = mrpQuantity;
    }

    public Double getImportQuantity() {
        return importQuantity;
    }

    public void setImportQuantity(Double importQuantity) {
        this.importQuantity = importQuantity;
    }

    public Double getImportWhsQMS() {
        return importWhsQMS;
    }

    public void setImportWhsQMS(Double importWhsQMS) {
        this.importWhsQMS = importWhsQMS;
    }
}
