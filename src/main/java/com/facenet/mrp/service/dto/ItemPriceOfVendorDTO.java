package com.facenet.mrp.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

public class ItemPriceOfVendorDTO implements Comparable<ItemPriceOfVendorDTO>{
    private Integer moqPriceId;
    private String vendorCode;
    private String vendorName;
    private Integer leadtime;
    private Integer rangeStart;
    private Integer rangeEnd;
    private Double price;
    private Date timeStart;
    private String currency;
    private String status;
    private Boolean isPromotion;

    @JsonIgnore
    private Double actualPrice;

    public ItemPriceOfVendorDTO(Integer moqPriceId, String vendorCode,String vendorName, Integer leadtime, Integer rangeStart, Integer rangeEnd, Double price, Date timeStart, String currency, Boolean isPromotion) {
        this.moqPriceId = moqPriceId;
        this.vendorCode = vendorCode;
        this.vendorName = vendorName;
        this.leadtime = leadtime;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.price = price;
        this.timeStart = timeStart;
        this.currency = currency;
        this.isPromotion = isPromotion;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Integer getLeadtime() {
        return leadtime;
    }

    public void setLeadtime(Integer leadtime) {
        this.leadtime = leadtime;
    }

    public Integer getRangeStart() {
        return rangeStart;
    }

    public void setRangeStart(Integer rangeStart) {
        this.rangeStart = rangeStart;
    }

    public Integer getRangeEnd() {
        return rangeEnd;
    }

    public void setRangeEnd(Integer rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return isPromotion ? "Đang khuyễn mãi" : "Giá MOQ";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMoqPriceId() {
        return moqPriceId;
    }

    public void setMoqPriceId(Integer moqPriceId) {
        this.moqPriceId = moqPriceId;
    }

    public Boolean getPromotion() {
        return isPromotion;
    }

    public void setPromotion(Boolean promotion) {
        isPromotion = promotion;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    @Override
    public int compareTo(ItemPriceOfVendorDTO e) {
        return this.getPrice().compareTo(e.getPrice());
    }

    public Double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(Double actualPrice) {
        this.actualPrice = actualPrice;
    }
}
