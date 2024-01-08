package com.facenet.mrp.domain.sap;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CRD1")
@IdClass(Crd1EntityPK.class)
public class Crd1Entity {
    private String address;
    private String cardCode;
    private String street;
    private String block;
    private String zipCode;
    private String city;
    private String county;
    private String country;
    private String state;
    private Short userSign;
    private Integer logInstanc;
    private String objType;
    private String licTradNum;
    private Integer lineNum;
    private String taxCode;
    private String building;
    private String adresType;
    private String address2;
    private String address3;
    private String addrType;
    private String streetNo;
    private String altCrdName;
    private String altTaxId;
    private String taxOffice;
    private String glblLocNum;
    private String ntnlty;
    private String diotNat;
    private String gstRegnNo;
    private Integer gstType;

    @Id
    @Column(name = "Address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Id
    @Column(name = "CardCode")
    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    @Basic
    @Column(name = "Street")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Basic
    @Column(name = "Block")
    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    @Basic
    @Column(name = "ZipCode")
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Basic
    @Column(name = "City")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "County")
    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Basic
    @Column(name = "Country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "State")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Column(name = "UserSign")
    public Short getUserSign() {
        return userSign;
    }

    public void setUserSign(Short userSign) {
        this.userSign = userSign;
    }

    @Basic
    @Column(name = "LogInstanc")
    public Integer getLogInstanc() {
        return logInstanc;
    }

    public void setLogInstanc(Integer logInstanc) {
        this.logInstanc = logInstanc;
    }

    @Basic
    @Column(name = "ObjType")
    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    @Basic
    @Column(name = "LicTradNum")
    public String getLicTradNum() {
        return licTradNum;
    }

    public void setLicTradNum(String licTradNum) {
        this.licTradNum = licTradNum;
    }

    @Basic
    @Column(name = "LineNum")
    public Integer getLineNum() {
        return lineNum;
    }

    public void setLineNum(Integer lineNum) {
        this.lineNum = lineNum;
    }

    @Basic
    @Column(name = "TaxCode")
    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    @Basic
    @Column(name = "Building")
    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    @Id
    @Column(name = "AdresType")
    public String getAdresType() {
        return adresType;
    }

    public void setAdresType(String adresType) {
        this.adresType = adresType;
    }

    @Basic
    @Column(name = "Address2")
    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @Basic
    @Column(name = "Address3")
    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    @Basic
    @Column(name = "AddrType")
    public String getAddrType() {
        return addrType;
    }

    public void setAddrType(String addrType) {
        this.addrType = addrType;
    }

    @Basic
    @Column(name = "StreetNo")
    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }

    @Basic
    @Column(name = "AltCrdName")
    public String getAltCrdName() {
        return altCrdName;
    }

    public void setAltCrdName(String altCrdName) {
        this.altCrdName = altCrdName;
    }

    @Basic
    @Column(name = "AltTaxId")
    public String getAltTaxId() {
        return altTaxId;
    }

    public void setAltTaxId(String altTaxId) {
        this.altTaxId = altTaxId;
    }

    @Basic
    @Column(name = "TaxOffice")
    public String getTaxOffice() {
        return taxOffice;
    }

    public void setTaxOffice(String taxOffice) {
        this.taxOffice = taxOffice;
    }

    @Basic
    @Column(name = "GlblLocNum")
    public String getGlblLocNum() {
        return glblLocNum;
    }

    public void setGlblLocNum(String glblLocNum) {
        this.glblLocNum = glblLocNum;
    }

    @Basic
    @Column(name = "Ntnlty")
    public String getNtnlty() {
        return ntnlty;
    }

    public void setNtnlty(String ntnlty) {
        this.ntnlty = ntnlty;
    }

    @Basic
    @Column(name = "DIOTNat")
    public String getDiotNat() {
        return diotNat;
    }

    public void setDiotNat(String diotNat) {
        this.diotNat = diotNat;
    }

    @Basic
    @Column(name = "GSTRegnNo")
    public String getGstRegnNo() {
        return gstRegnNo;
    }

    public void setGstRegnNo(String gstRegnNo) {
        this.gstRegnNo = gstRegnNo;
    }

    @Basic
    @Column(name = "GSTType")
    public Integer getGstType() {
        return gstType;
    }

    public void setGstType(Integer gstType) {
        this.gstType = gstType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Crd1Entity that = (Crd1Entity) o;
        return Objects.equals(address, that.address) && Objects.equals(cardCode, that.cardCode) && Objects.equals(street, that.street) && Objects.equals(block, that.block) && Objects.equals(zipCode, that.zipCode) && Objects.equals(city, that.city) && Objects.equals(county, that.county) && Objects.equals(country, that.country) && Objects.equals(state, that.state) && Objects.equals(userSign, that.userSign) && Objects.equals(logInstanc, that.logInstanc) && Objects.equals(objType, that.objType) && Objects.equals(licTradNum, that.licTradNum) && Objects.equals(lineNum, that.lineNum) && Objects.equals(taxCode, that.taxCode) && Objects.equals(building, that.building) && Objects.equals(adresType, that.adresType) && Objects.equals(address2, that.address2) && Objects.equals(address3, that.address3) && Objects.equals(addrType, that.addrType) && Objects.equals(streetNo, that.streetNo) && Objects.equals(altCrdName, that.altCrdName) && Objects.equals(altTaxId, that.altTaxId) && Objects.equals(taxOffice, that.taxOffice) && Objects.equals(glblLocNum, that.glblLocNum) && Objects.equals(ntnlty, that.ntnlty) && Objects.equals(diotNat, that.diotNat) && Objects.equals(gstRegnNo, that.gstRegnNo) && Objects.equals(gstType, that.gstType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, cardCode, street, block, zipCode, city, county, country, state, userSign, logInstanc, objType, licTradNum, lineNum, taxCode, building, adresType, address2, address3, addrType, streetNo, altCrdName, altTaxId, taxOffice, glblLocNum, ntnlty, diotNat, gstRegnNo, gstType);
    }
}
