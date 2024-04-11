package com.facenet.mrp.service.dto.request;

public class SendAnalysisRequest {
    private String poCode;
    private String productCode;
    private Byte status;

    public SendAnalysisRequest() {
    }

    public String getPoCode() {
        return poCode;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
