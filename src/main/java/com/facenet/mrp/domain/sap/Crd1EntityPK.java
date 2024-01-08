package com.facenet.mrp.domain.sap;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class Crd1EntityPK implements Serializable {
    private String address;
    private String cardCode;
    private String adresType;

    @Column(name = "Address")
    @Id
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "CardCode")
    @Id
    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    @Column(name = "AdresType")
    @Id
    public String getAdresType() {
        return adresType;
    }

    public void setAdresType(String adresType) {
        this.adresType = adresType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Crd1EntityPK that = (Crd1EntityPK) o;
        return Objects.equals(address, that.address) && Objects.equals(cardCode, that.cardCode) && Objects.equals(adresType, that.adresType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, cardCode, adresType);
    }
}
