package com.facenet.mrp.domain.sap;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "OWHS")
public class OwhsEntity {
    private String whsCode;
    private String whsName;

    @Id
    @Column(name = "WhsCode")
    public String getWhsCode() {
        return whsCode;
    }

    public void setWhsCode(String whsCode) {
        this.whsCode = whsCode;
    }

    @Basic
    @Column(name = "WhsName")
    public String getWhsName() {
        return whsName;
    }

    public void setWhsName(String whsName) {
        this.whsName = whsName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwhsEntity that = (OwhsEntity) o;
        return Objects.equals(whsCode, that.whsCode) && Objects.equals(whsName, that.whsName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(whsCode, whsName);
    }
}
