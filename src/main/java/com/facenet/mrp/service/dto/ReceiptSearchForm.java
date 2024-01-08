package com.facenet.mrp.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReceiptSearchForm {
    private String uDocnum;

    private String cardName;

    private String itemCode;

    private String dscription;

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDscription() {
        return dscription;
    }

    public void setDscription(String dscription) {
        this.dscription = dscription;
    }
    private int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    private int size;

    @JsonCreator
    public ReceiptSearchForm(@JsonProperty("uDocnum") String uDocnum,@JsonProperty("cardName") String cardName,@JsonProperty("itemCode") String itemCode,@JsonProperty("dscription") String dscription,@JsonProperty("page") Integer page, @JsonProperty("size") Integer size) {
        this.uDocnum = uDocnum;
        this.cardName = cardName;
        this.itemCode = itemCode;
        this.dscription = dscription;
        this.page = page;
        this.size = size;
    }

    public String getuDocnum() {
        return uDocnum;
    }

    public void setuDocnum(String uDocnum) {
        this.uDocnum = uDocnum;
    }
}
