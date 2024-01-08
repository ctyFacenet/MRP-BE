package com.facenet.mrp.service.dto.mrp;

public class PrGrpoAnalysisReport {

    private Double quantity;
    private String typePrGrpo;

    private String itemCode;

    public PrGrpoAnalysisReport() {
    }

    public PrGrpoAnalysisReport(String itemCode , String typePrGrpo, Double quantity) {
        this.itemCode = itemCode;
        this.typePrGrpo = typePrGrpo;
        this.quantity = quantity;
    }


    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getTypePrGrpo() {
        return typePrGrpo;
    }

    public void setTypePrGrpo(String typePrGrpo) {
        this.typePrGrpo = typePrGrpo;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

}
