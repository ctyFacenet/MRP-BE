package com.facenet.mrp.domain.sap;

import javax.persistence.*;
@Entity
@Table(name = "ITT1")
public class Itt1Entity {
    private String father;
    private Long childNum;
    private String code;
    private Double quantity;
    private String warehouse;
    private Double price;
    private String currency;
    private Long priceList;
    private Double origPrice;
    private String origCurr;
    private String issueMthd;
    private String uom;
    private String comment;
    private Long logInstanc;
    private String object;
    private String ocrCode;
    private String ocrCode2;
    private String ocrCode3;
    private String ocrCode4;
    private String ocrCode5;
    private String prncpInput;
    private String project;
    private Long type;
    private String wipActCode;
    private Long addQuantit;
    private String lineText;
    private String uRdCode;
    private String uVTTT;
    private Long uThreshold;

    @Basic
    @Column (name = "Father")
    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    @Id
    @Basic
    @Column (name = "ChildNum")
    public Long getChildNum() {
        return childNum;
    }

    public void setChildNum(Long childNum) {
        this.childNum = childNum;
    }

    @Basic
    @Column (name = "Code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column (name = "Quantity")
    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
    @Basic
    @Column (name = "Warehouse")
    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    @Basic
    @Column (name = "Price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Basic
    @Column (name = "Currency")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Basic
    @Column (name = "PriceList")
    public Long getPriceList() {
        return priceList;
    }

    public void setPriceList(Long priceList) {
        this.priceList = priceList;
    }

    @Basic
    @Column (name = "OrigPrice")
    public Double getOrigPrice() {
        return origPrice;
    }

    public void setOrigPrice(Double origPrice) {
        this.origPrice = origPrice;
    }

    @Basic
    @Column (name = "OrigCurr")
    public String getOrigCurr() {
        return origCurr;
    }

    public void setOrigCurr(String origCurr) {
        this.origCurr = origCurr;
    }

    @Basic
    @Column (name = "IssueMthd")
    public String getIssueMthd() {
        return issueMthd;
    }

    public void setIssueMthd(String issueMthd) {
        this.issueMthd = issueMthd;
    }

    @Basic
    @Column (name = "Uom")
    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }
    @Basic
    @Column (name = "Comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    @Basic
    @Column (name = "LogInstanc")
    public Long getLogInstanc() {
        return logInstanc;
    }

    public void setLogInstanc(Long logInstanc) {
        this.logInstanc = logInstanc;
    }
    @Basic
    @Column (name = "Object")
    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
    @Basic
    @Column (name = "OcrCode")
    public String getOcrCode() {
        return ocrCode;
    }

    public void setOcrCode(String ocrCode) {
        this.ocrCode = ocrCode;
    }
    @Basic
    @Column (name = "OcrCode2")
    public String getOcrCode2() {
        return ocrCode2;
    }

    public void setOcrCode2(String ocrCode2) {
        this.ocrCode2 = ocrCode2;
    }
    @Basic
    @Column (name = "OcrCode3")
    public String getOcrCode3() {
        return ocrCode3;
    }

    public void setOcrCode3(String ocrCode3) {
        this.ocrCode3 = ocrCode3;
    }
    @Basic
    @Column (name = "OcrCode4")
    public String getOcrCode4() {
        return ocrCode4;
    }

    public void setOcrCode4(String ocrCode4) {
        this.ocrCode4 = ocrCode4;
    }
    @Basic
    @Column (name = "OcrCode5")
    public String getOcrCode5() {
        return ocrCode5;
    }

    public void setOcrCode5(String ocrCode5) {
        this.ocrCode5 = ocrCode5;
    }
    @Basic
    @Column (name = "PrncpInput")
    public String getPrncpInput() {
        return prncpInput;
    }

    public void setPrncpInput(String prncpInput) {
        this.prncpInput = prncpInput;
    }
    @Basic
    @Column (name = "Project")
    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }
    @Basic
    @Column (name = "Type")
    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }
    @Basic
    @Column (name = "WipActCode")
    public String getWipActCode() {
        return wipActCode;
    }

    public void setWipActCode(String wipActCode) {
        this.wipActCode = wipActCode;
    }
    @Basic
    @Column (name = "AddQuantit")
    public Long getAddQuantit() {
        return addQuantit;
    }

    public void setAddQuantit(Long addQuantit) {
        this.addQuantit = addQuantit;
    }
    @Basic
    @Column (name = "LineText")
    public String getLineText() {
        return lineText;
    }

    public void setLineText(String lineText) {
        this.lineText = lineText;
    }
    @Basic
    @Column (name = "U_RDCODE")
    public String getuRdCode() {
        return uRdCode;
    }

    public void setuRdCode(String uRdCode) {
        this.uRdCode = uRdCode;
    }
    @Basic
    @Column (name = "U_VTTT")
    public String getuVTTT() {
        return uVTTT;
    }

    public void setuVTTT(String uVTTT) {
        this.uVTTT = uVTTT;
    }
    @Basic
    @Column (name = "U_Threshold")
    public Long getuThreshold() {
        return uThreshold;
    }

    public void setuThreshold(Long uThreshold) {
        this.uThreshold = uThreshold;
    }
}
