package com.facenet.mrp.service.dto;

import java.io.Serializable;
import java.util.List;

public class ViewBomDTO implements Serializable {

    private String productCode;

    private String productName;

    private String versionBom;

    private String remark;

    private String speciality;

    private List<DetailBomVersionDTO> list;

    public List<DetailBomVersionDTO> getList() {
        return list;
    }

    public void setList(List<DetailBomVersionDTO> list) {
        this.list = list;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVersionBom() {
        return versionBom;
    }

    public void setVersionBom(String versionBom) {
        this.versionBom = versionBom;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public ViewBomDTO(){

    }

    public ViewBomDTO(String productCode, String productName, String versionBom, String remark, String speciality, List<DetailBomVersionDTO> list) {
        this.productCode = productCode;
        this.productName = productName;
        this.versionBom = versionBom;
        this.remark = remark;
        this.speciality = speciality;
        this.list = list;
    }
}
