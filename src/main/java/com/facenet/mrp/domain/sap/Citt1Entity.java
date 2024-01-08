package com.facenet.mrp.domain.sap;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "\"@CITT1\"", schema = "dbo")
@IdClass(Citt1EntityPK.class)
public class Citt1Entity {
    private int docEntry;
    private int lineId;
    private Integer visOrder;
    private String object;
    private Integer logInst;
    private String uType;
    private String uItemCode;
    private String uPartNumber;
    private String uLocation;
    private String uItemName;
    private String uWhsCod;
    private Double uQuantity;
    private String uIssue;
    private String uRemarks;
    private String uVersions;
    private String uDocEntry;
    private String uVendor;
    private String uCtrLevel;
    private String uOtherNam;
    private String uSelect;
    private String uUom;
    private String uSlect;
    private String uAlter;
    private String uItmTech;

    @Id
    @Column(name = "DocEntry")
    public int getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(int docEntry) {
        this.docEntry = docEntry;
    }

    @Id
    @Column(name = "LineId")
    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    @Basic
    @Column(name = "VisOrder")
    public Integer getVisOrder() {
        return visOrder;
    }

    public void setVisOrder(Integer visOrder) {
        this.visOrder = visOrder;
    }

    @Basic
    @Column(name = "Object")
    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    @Basic
    @Column(name = "LogInst")
    public Integer getLogInst() {
        return logInst;
    }

    public void setLogInst(Integer logInst) {
        this.logInst = logInst;
    }

    @Basic
    @Column(name = "U_Type")
    public String getuType() {
        return uType;
    }

    public void setuType(String uType) {
        this.uType = uType;
    }

    @Basic
    @Column(name = "U_ItemCode")
    public String getuItemCode() {
        return uItemCode;
    }

    public void setuItemCode(String uItemCode) {
        this.uItemCode = uItemCode;
    }

    @Basic
    @Column(name = "U_PartNumber")
    public String getuPartNumber() {
        return uPartNumber;
    }

    public void setuPartNumber(String uPartNumber) {
        this.uPartNumber = uPartNumber;
    }

    @Basic
    @Column(name = "U_Location")
    public String getuLocation() {
        return uLocation;
    }

    public void setuLocation(String uLocation) {
        this.uLocation = uLocation;
    }

    @Basic
    @Column(name = "U_ItemName")
    public String getuItemName() {
        return uItemName;
    }

    public void setuItemName(String uItemName) {
        this.uItemName = uItemName;
    }

    @Basic
    @Column(name = "U_WhsCod")
    public String getuWhsCod() {
        return uWhsCod;
    }

    public void setuWhsCod(String uWhsCod) {
        this.uWhsCod = uWhsCod;
    }

    @Basic
    @Column(name = "U_Quantity")
    public Double getuQuantity() {
        return uQuantity;
    }

    public void setuQuantity(Double uQuantity) {
        this.uQuantity = uQuantity;
    }

    @Basic
    @Column(name = "U_Issue")
    public String getuIssue() {
        return uIssue;
    }

    public void setuIssue(String uIssue) {
        this.uIssue = uIssue;
    }

    @Basic
    @Column(name = "U_Remarks")
    public String getuRemarks() {
        return uRemarks;
    }

    public void setuRemarks(String uRemarks) {
        this.uRemarks = uRemarks;
    }

    @Basic
    @Column(name = "U_Versions")
    public String getuVersions() {
        return uVersions;
    }

    public void setuVersions(String uVersions) {
        this.uVersions = uVersions;
    }

    @Basic
    @Column(name = "U_DocEntry")
    public String getuDocEntry() {
        return uDocEntry;
    }

    public void setuDocEntry(String uDocEntry) {
        this.uDocEntry = uDocEntry;
    }

    @Basic
    @Column(name = "U_Vendor")
    public String getuVendor() {
        return uVendor;
    }

    public void setuVendor(String uVendor) {
        this.uVendor = uVendor;
    }

    @Basic
    @Column(name = "U_CtrLevel")
    public String getuCtrLevel() {
        return uCtrLevel;
    }

    public void setuCtrLevel(String uCtrLevel) {
        this.uCtrLevel = uCtrLevel;
    }

    @Basic
    @Column(name = "U_OtherNam")
    public String getuOtherNam() {
        return uOtherNam;
    }

    public void setuOtherNam(String uOtherNam) {
        this.uOtherNam = uOtherNam;
    }

    @Basic
    @Column(name = "U_Select")
    public String getuSelect() {
        return uSelect;
    }

    public void setuSelect(String uSelect) {
        this.uSelect = uSelect;
    }

    @Basic
    @Column(name = "U_Uom")
    public String getuUom() {
        return uUom;
    }

    public void setuUom(String uUom) {
        this.uUom = uUom;
    }

    @Basic
    @Column(name = "U_Slect")
    public String getuSlect() {
        return uSlect;
    }

    public void setuSlect(String uSlect) {
        this.uSlect = uSlect;
    }

    @Basic
    @Column(name = "U_Alter")
    public String getuAlter() {
        return uAlter;
    }

    public void setuAlter(String uAlter) {
        this.uAlter = uAlter;
    }

    @Basic
    @Column(name = "U_ItmTech")
    public String getuItmTech() {
        return uItmTech;
    }

    public void setuItmTech(String uItmTech) {
        this.uItmTech = uItmTech;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Citt1Entity that = (Citt1Entity) o;
        return docEntry == that.docEntry && lineId == that.lineId && uQuantity == that.uQuantity && Objects.equals(visOrder, that.visOrder) && Objects.equals(object, that.object) && Objects.equals(logInst, that.logInst) && Objects.equals(uType, that.uType) && Objects.equals(uItemCode, that.uItemCode) && Objects.equals(uPartNumber, that.uPartNumber) && Objects.equals(uLocation, that.uLocation) && Objects.equals(uItemName, that.uItemName) && Objects.equals(uWhsCod, that.uWhsCod) && Objects.equals(uIssue, that.uIssue) && Objects.equals(uRemarks, that.uRemarks) && Objects.equals(uVersions, that.uVersions) && Objects.equals(uDocEntry, that.uDocEntry) && Objects.equals(uVendor, that.uVendor) && Objects.equals(uCtrLevel, that.uCtrLevel) && Objects.equals(uOtherNam, that.uOtherNam) && Objects.equals(uSelect, that.uSelect) && Objects.equals(uUom, that.uUom) && Objects.equals(uSlect, that.uSlect) && Objects.equals(uAlter, that.uAlter) && Objects.equals(uItmTech, that.uItmTech);
    }

    @Override
    public int hashCode() {
        return Objects.hash(docEntry, lineId, visOrder, object, logInst, uType, uItemCode, uPartNumber, uLocation, uItemName, uWhsCod, uQuantity, uIssue, uRemarks, uVersions, uDocEntry, uVendor, uCtrLevel, uOtherNam, uSelect, uUom, uSlect, uAlter, uItmTech);
    }
}
