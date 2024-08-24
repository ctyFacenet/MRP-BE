package com.facenet.mrp.service.dto.sap;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.facenet.mrp.domain.sap.Itt1Entity}
 */
@Value
public class Itt1Dto implements Serializable {
    String father;
    Long childNum;
    String code;
    Double quantity;
    String warehouse;
    Double price;
    String currency;
    Long priceList;
    Double origPrice;
    String origCurr;
    String issueMthd;
    String uom;
    String comment;
    Long logInstanc;
    String object;
    String ocrCode;
    String ocrCode2;
    String ocrCode3;
    String ocrCode4;
    String ocrCode5;
    String prncpInput;
    String project;
    Long type;
    String wipActCode;
    Long addQuantit;
    String lineText;
    String uRdCode;
    String uVTTT;
    Long uThreshold;
}
