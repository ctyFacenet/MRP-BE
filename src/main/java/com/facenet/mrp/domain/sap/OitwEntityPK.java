package com.facenet.mrp.domain.sap;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class OitwEntityPK implements Serializable {
    private String itemCode;
    private String whsCode;

    @Column(name = "ItemCode")
    @Id
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Column(name = "WhsCode")
    @Id
    public String getWhsCode() {
        return whsCode;
    }

    public void setWhsCode(String whsCode) {
        this.whsCode = whsCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OitwEntityPK that = (OitwEntityPK) o;
        return Objects.equals(itemCode, that.itemCode) && Objects.equals(whsCode, that.whsCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemCode, whsCode);
    }
}
