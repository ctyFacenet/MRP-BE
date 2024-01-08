package com.facenet.mrp.domain.sap;

import javax.persistence.*;

@Entity
@Table(name = "OCRD")
public class OcrdEntity {
    private String cardCode;
    private String cardName;
    private String cardType;
    private String cartFName;
    private String eMail;
    private String address;
    private String phone1;
    private String fax;
    private String curency;
    private String addId;
    private String validFor;
    private String frozenFor;

    @Id
    @Basic
    @Column(name = "CardCode")
    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    @Basic
    @Column(name = "CardName")
    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    @Basic
    @Column(name = "CardFName")
    public String getCartFName() {
        return cartFName;
    }

    public void setCartFName(String cartFName) {
        this.cartFName = cartFName;
    }

    @Basic
    @Column(name = "E_Mail")
    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
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
    @Column(name = "Phone1")
    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
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
    @Column(name = "Currency")
    public String getCurrency() {
        return curency;
    }

    public void setCurrency(String currency) {
        this.curency = currency;
    }

    @Basic
    @Column(name = "AddID")
    public String getAddId() {
        return addId;
    }

    public void setAddId(String addId) {
        this.addId = addId;
    }

    @Basic
    @Column(name = "ValidFor")
    public String getValidFor() {
        return validFor;
    }

    public void setValidFor(String validFor) {
        this.validFor = validFor;
    }

    @Basic
    @Column(name = "FrozenFor")
    public String getFrozenFor() {
        return frozenFor;
    }

    public void setFrozenFor(String frozenFor) {
        this.frozenFor = frozenFor;
    }

    @Basic
    @Column(name = "CardType")
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
