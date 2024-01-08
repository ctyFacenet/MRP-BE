package com.facenet.mrp.domain.sap;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ITM2")
@IdClass(Itm2EntityPK.class)
public class Itm2Entity {
    private String itemCode;
    private String vendorCode;
    private Integer logInstanc;
    private String objType;

    @Id
    @Column(name = "ItemCode")
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Id
    @Column(name = "VendorCode")
    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Itm2Entity that = (Itm2Entity) o;
        return Objects.equals(itemCode, that.itemCode) && Objects.equals(vendorCode, that.vendorCode) && Objects.equals(logInstanc, that.logInstanc) && Objects.equals(objType, that.objType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemCode, vendorCode, logInstanc, objType);
    }
}
