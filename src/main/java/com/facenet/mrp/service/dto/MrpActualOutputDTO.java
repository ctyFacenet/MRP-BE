package com.facenet.mrp.service.dto;

public class MrpActualOutputDTO {

    private String soCode;
    private String productCode;
    private String bomVersion;
    private Double actualQuantity;
    private Double qmsQuantity;

    public MrpActualOutputDTO() {
    }

    public MrpActualOutputDTO(String soCode, String productCode, String bomVersion) {
        this.soCode = soCode;
        this.productCode = productCode;
        this.bomVersion = bomVersion;
    }

    public MrpActualOutputDTO(String soCode, String productCode, String bomVersion, Double actualQuantity) {
        this.soCode = soCode;
        this.productCode = productCode;
        this.bomVersion = bomVersion;
        this.actualQuantity = actualQuantity;
    }

    public MrpActualOutputDTO(String soCode, String productCode, String bomVersion, Double actualQuantity, Double qmsQuantity) {
        this.soCode = soCode;
        this.productCode = productCode;
        this.bomVersion = bomVersion;
        this.actualQuantity = actualQuantity;
        this.qmsQuantity = qmsQuantity;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getBomVersion() {
        return bomVersion;
    }

    public void setBomVersion(String bomVersion) {
        this.bomVersion = bomVersion;
    }

    public Double getActualQuantity() {
        return actualQuantity;
    }

    public void setActualQuantity(Double actualQuantity) {
        this.actualQuantity = actualQuantity;
    }

    public Double getQmsQuantity() {
        return qmsQuantity;
    }

    public void setQmsQuantity(Double qmsQuantity) {
        this.qmsQuantity = qmsQuantity;
    }
}
