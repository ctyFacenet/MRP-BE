package com.facenet.mrp.service.dto;

public class MrpActualInputDTO {

    private String soCode;
    private String productCode;
    private String bomVersion;

    public MrpActualInputDTO() {
    }

    public MrpActualInputDTO(String soCode, String productCode, String bomVersion) {
        this.soCode = soCode;
        this.productCode = productCode;
        this.bomVersion = bomVersion;
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
}
