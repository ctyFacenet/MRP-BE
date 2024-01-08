package com.facenet.mrp.domain.sap;

import javax.persistence.*;

@Entity
@Table(name = "OITB")
public class OitbEntity {
    @Id
    @Basic
    @Column(name = "ItmsGrpCod")
    private Integer itmsGrpCode;


    @Basic
    @Column(name = "ItmsGrpNam")
    private String itmsGrpName;

    public Integer getItmsGrpCode() {
        return itmsGrpCode;
    }

    public void setItmsGrpCode(Integer itmsGrpCode) {
        this.itmsGrpCode = itmsGrpCode;
    }

    public String getItmsGrpName() {
        return itmsGrpName;
    }

    public void setItmsGrpName(String itmsGrpName) {
        this.itmsGrpName = itmsGrpName;
    }

}
