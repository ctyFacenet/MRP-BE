package com.facenet.mrp.service.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
public class MaterialDTO {
    @JsonProperty("_RowNumber")
    private String rowNumber;

    @JsonProperty("Ghep_chungloai_mausac")
    private String ghepChungloaiMausac;

    @JsonProperty("Chủng loại")
    private String chungLoai;

    @JsonProperty("Màu sắc")
    private String mauSac;

    @JsonProperty("Tồn")
    private String ton;

    // Getters và Setters
    public String getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(String rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getGhepChungloaiMausac() {
        return ghepChungloaiMausac;
    }

    public void setGhepChungloaiMausac(String ghepChungloaiMausac) {
        this.ghepChungloaiMausac = ghepChungloaiMausac;
    }

    public String getChungLoai() {
        return chungLoai;
    }

    public void setChungLoai(String chungLoai) {
        this.chungLoai = chungLoai;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getTon() {
        return ton;
    }

    public void setTon(String ton) {
        this.ton = ton;
    }
}
