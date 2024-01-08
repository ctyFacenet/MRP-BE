package com.facenet.mrp.domain.sap;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "OCPR")
public class OcprEntity {
    private int cntctCode;
    private String cardCode;
    private String name;
    private String position;
    private String address;
    private String tel1;
    private String tel2;
    private String cellolar;
    private String fax;
    private String eMailL;
    private String pager;
    private String notes1;
    private String notes2;
    private String dataSource;
    private Short userSign;
    private String password;
    private Integer logInstanc;
    private String objType;
    private String birthPlace;
    private Date birthDate;
    private String gender;
    private String profession;
    private Date updateDate;
    private Integer updateTime;
    private String title;
    private String birthCity;
    private String active;
    private String firstName;
    private String middleName;
    private String lastName;
    private String birthState;
    private String residCity;
    private String residCntry;
    private String residState;
    private String nFeRcpn;
    private String emlGrpCode;
    private String blockComm;
    private String fiscalCode;
    private String ctyPrvsYr;
    private String sttPrvsYr;
    private String ctyCdPrvsY;
    private String ctyCurYr;
    private String sttCurYr;
    private String ctyCdCurYr;
    private String notResdSch;
    private String ctyFsnCode;

    @Id
    @Column(name = "CntctCode")
    public int getCntctCode() {
        return cntctCode;
    }

    public void setCntctCode(int cntctCode) {
        this.cntctCode = cntctCode;
    }

    @Basic
    @Column(name = "CardCode")
    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    @Basic
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Basic
    @Column(name = "Address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "Tel1")
    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    @Basic
    @Column(name = "Tel2")
    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    @Basic
    @Column(name = "Cellolar")
    public String getCellolar() {
        return cellolar;
    }

    public void setCellolar(String cellolar) {
        this.cellolar = cellolar;
    }

    @Basic
    @Column(name = "Fax")
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Basic
    @Column(name = "E_MailL")
    public String geteMailL() {
        return eMailL;
    }

    public void seteMailL(String eMailL) {
        this.eMailL = eMailL;
    }

    @Basic
    @Column(name = "Pager")
    public String getPager() {
        return pager;
    }

    public void setPager(String pager) {
        this.pager = pager;
    }

    @Basic
    @Column(name = "Notes1")
    public String getNotes1() {
        return notes1;
    }

    public void setNotes1(String notes1) {
        this.notes1 = notes1;
    }

    @Basic
    @Column(name = "Notes2")
    public String getNotes2() {
        return notes2;
    }

    public void setNotes2(String notes2) {
        this.notes2 = notes2;
    }

    @Basic
    @Column(name = "DataSource")
    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
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
    @Column(name = "Password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    @Column(name = "BirthPlace")
    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    @Basic
    @Column(name = "BirthDate")
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Basic
    @Column(name = "Gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "Profession")
    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Basic
    @Column(name = "updateDate")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Basic
    @Column(name = "updateTime")
    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "Title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "BirthCity")
    public String getBirthCity() {
        return birthCity;
    }

    public void setBirthCity(String birthCity) {
        this.birthCity = birthCity;
    }

    @Basic
    @Column(name = "Active")
    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Basic
    @Column(name = "FirstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "MiddleName")
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Basic
    @Column(name = "LastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "BirthState")
    public String getBirthState() {
        return birthState;
    }

    public void setBirthState(String birthState) {
        this.birthState = birthState;
    }

    @Basic
    @Column(name = "ResidCity")
    public String getResidCity() {
        return residCity;
    }

    public void setResidCity(String residCity) {
        this.residCity = residCity;
    }

    @Basic
    @Column(name = "ResidCntry")
    public String getResidCntry() {
        return residCntry;
    }

    public void setResidCntry(String residCntry) {
        this.residCntry = residCntry;
    }

    @Basic
    @Column(name = "ResidState")
    public String getResidState() {
        return residState;
    }

    public void setResidState(String residState) {
        this.residState = residState;
    }

    @Basic
    @Column(name = "NFeRcpn")
    public String getnFeRcpn() {
        return nFeRcpn;
    }

    public void setnFeRcpn(String nFeRcpn) {
        this.nFeRcpn = nFeRcpn;
    }

    @Basic
    @Column(name = "EmlGrpCode")
    public String getEmlGrpCode() {
        return emlGrpCode;
    }

    public void setEmlGrpCode(String emlGrpCode) {
        this.emlGrpCode = emlGrpCode;
    }

    @Basic
    @Column(name = "BlockComm")
    public String getBlockComm() {
        return blockComm;
    }

    public void setBlockComm(String blockComm) {
        this.blockComm = blockComm;
    }

    @Basic
    @Column(name = "FiscalCode")
    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    @Basic
    @Column(name = "CtyPrvsYr")
    public String getCtyPrvsYr() {
        return ctyPrvsYr;
    }

    public void setCtyPrvsYr(String ctyPrvsYr) {
        this.ctyPrvsYr = ctyPrvsYr;
    }

    @Basic
    @Column(name = "SttPrvsYr")
    public String getSttPrvsYr() {
        return sttPrvsYr;
    }

    public void setSttPrvsYr(String sttPrvsYr) {
        this.sttPrvsYr = sttPrvsYr;
    }

    @Basic
    @Column(name = "CtyCdPrvsY")
    public String getCtyCdPrvsY() {
        return ctyCdPrvsY;
    }

    public void setCtyCdPrvsY(String ctyCdPrvsY) {
        this.ctyCdPrvsY = ctyCdPrvsY;
    }

    @Basic
    @Column(name = "CtyCurYr")
    public String getCtyCurYr() {
        return ctyCurYr;
    }

    public void setCtyCurYr(String ctyCurYr) {
        this.ctyCurYr = ctyCurYr;
    }

    @Basic
    @Column(name = "SttCurYr")
    public String getSttCurYr() {
        return sttCurYr;
    }

    public void setSttCurYr(String sttCurYr) {
        this.sttCurYr = sttCurYr;
    }

    @Basic
    @Column(name = "CtyCdCurYr")
    public String getCtyCdCurYr() {
        return ctyCdCurYr;
    }

    public void setCtyCdCurYr(String ctyCdCurYr) {
        this.ctyCdCurYr = ctyCdCurYr;
    }

    @Basic
    @Column(name = "NotResdSch")
    public String getNotResdSch() {
        return notResdSch;
    }

    public void setNotResdSch(String notResdSch) {
        this.notResdSch = notResdSch;
    }

    @Basic
    @Column(name = "CtyFsnCode")
    public String getCtyFsnCode() {
        return ctyFsnCode;
    }

    public void setCtyFsnCode(String ctyFsnCode) {
        this.ctyFsnCode = ctyFsnCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OcprEntity that = (OcprEntity) o;
        return cntctCode == that.cntctCode && Objects.equals(cardCode, that.cardCode) && Objects.equals(name, that.name) && Objects.equals(position, that.position) && Objects.equals(address, that.address) && Objects.equals(tel1, that.tel1) && Objects.equals(tel2, that.tel2) && Objects.equals(cellolar, that.cellolar) && Objects.equals(fax, that.fax) && Objects.equals(eMailL, that.eMailL) && Objects.equals(pager, that.pager) && Objects.equals(notes1, that.notes1) && Objects.equals(notes2, that.notes2) && Objects.equals(dataSource, that.dataSource) && Objects.equals(userSign, that.userSign) && Objects.equals(password, that.password) && Objects.equals(logInstanc, that.logInstanc) && Objects.equals(objType, that.objType) && Objects.equals(birthPlace, that.birthPlace) && Objects.equals(birthDate, that.birthDate) && Objects.equals(gender, that.gender) && Objects.equals(profession, that.profession) && Objects.equals(updateDate, that.updateDate) && Objects.equals(updateTime, that.updateTime) && Objects.equals(title, that.title) && Objects.equals(birthCity, that.birthCity) && Objects.equals(active, that.active) && Objects.equals(firstName, that.firstName) && Objects.equals(middleName, that.middleName) && Objects.equals(lastName, that.lastName) && Objects.equals(birthState, that.birthState) && Objects.equals(residCity, that.residCity) && Objects.equals(residCntry, that.residCntry) && Objects.equals(residState, that.residState) && Objects.equals(nFeRcpn, that.nFeRcpn) && Objects.equals(emlGrpCode, that.emlGrpCode) && Objects.equals(blockComm, that.blockComm) && Objects.equals(fiscalCode, that.fiscalCode) && Objects.equals(ctyPrvsYr, that.ctyPrvsYr) && Objects.equals(sttPrvsYr, that.sttPrvsYr) && Objects.equals(ctyCdPrvsY, that.ctyCdPrvsY) && Objects.equals(ctyCurYr, that.ctyCurYr) && Objects.equals(sttCurYr, that.sttCurYr) && Objects.equals(ctyCdCurYr, that.ctyCdCurYr) && Objects.equals(notResdSch, that.notResdSch) && Objects.equals(ctyFsnCode, that.ctyFsnCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cntctCode, cardCode, name, position, address, tel1, tel2, cellolar, fax, eMailL, pager, notes1, notes2, dataSource, userSign, password, logInstanc, objType, birthPlace, birthDate, gender, profession, updateDate, updateTime, title, birthCity, active, firstName, middleName, lastName, birthState, residCity, residCntry, residState, nFeRcpn, emlGrpCode, blockComm, fiscalCode, ctyPrvsYr, sttPrvsYr, ctyCdPrvsY, ctyCurYr, sttCurYr, ctyCdCurYr, notResdSch, ctyFsnCode);
    }
}
