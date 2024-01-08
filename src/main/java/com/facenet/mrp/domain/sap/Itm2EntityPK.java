package com.facenet.mrp.domain.sap;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class Itm2EntityPK implements Serializable {
    private String itemCode;
    private String vendorCode;

    @Column(name = "ItemCode")
    @Id
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Column(name = "VendorCode")
    @Id
    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Itm2EntityPK that = (Itm2EntityPK) o;
        return Objects.equals(itemCode, that.itemCode) && Objects.equals(vendorCode, that.vendorCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemCode, vendorCode);
    }
}
