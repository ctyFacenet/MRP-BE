package com.facenet.mrp.service.dto.sap;

import com.querydsl.core.annotations.QueryProjection;

public class CoittCitt1DTO {
    private String uProNo;
    private String uVersions;
    private int docEntry;
    private String uItemCode;
    private String uVersionsCitt1;

    public CoittCitt1DTO() {
    }

    @QueryProjection
    public CoittCitt1DTO(String uProNo, String uVersions, int docEntry, String uItemCode, String uVersionsCitt1) {
        this.uProNo = uProNo;
        this.uVersions = uVersions;
        this.docEntry = docEntry;
        this.uItemCode = uItemCode;
        this.uVersionsCitt1 = uVersionsCitt1;
    }

    public String getuProNo() {
        return uProNo;
    }

    public void setuProNo(String uProNo) {
        this.uProNo = uProNo;
    }

    public String getuVersions() {
        return uVersions;
    }

    public void setuVersions(String uVersions) {
        this.uVersions = uVersions;
    }

    public int getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(int docEntry) {
        this.docEntry = docEntry;
    }

    public String getuItemCode() {
        return uItemCode;
    }

    public void setuItemCode(String uItemCode) {
        this.uItemCode = uItemCode;
    }

    public String getuVersionsCitt1() {
        return uVersionsCitt1;
    }

    public void setuVersionsCitt1(String uVersionsCitt1) {
        this.uVersionsCitt1 = uVersionsCitt1;
    }
}
