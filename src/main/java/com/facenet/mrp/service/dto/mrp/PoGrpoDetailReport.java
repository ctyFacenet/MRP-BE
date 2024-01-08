package com.facenet.mrp.service.dto.mrp;

public class PoGrpoDetailReport extends PrGrpoAnalysisReport{
    private String mrpSubCode;

    public PoGrpoDetailReport() {
    }

    public PoGrpoDetailReport(String itemCode, String typePrGrpo, Double quantity, String mrpSubCode) {
        super(itemCode, typePrGrpo, quantity);
        this.mrpSubCode = mrpSubCode;
    }

    public String getMrpSubCode() {
        return mrpSubCode;
    }

    public void setMrpSubCode(String mrpSubCode) {
        this.mrpSubCode = mrpSubCode;
    }
}
