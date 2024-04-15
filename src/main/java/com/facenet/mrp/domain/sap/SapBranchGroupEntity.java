package com.facenet.mrp.domain.sap;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "\"@SAP_BRANCH_GROUP\"", schema = "dbo", catalog = "Thang4")
public class SapBranchGroupEntity implements Serializable {
    @Column(name = "code")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int code;
    @Basic
    @Column(name = "Name")
    private String name;
    @Basic
    @Column(name = "U_BranchCode")
    private String uBranchCode;
    @Basic
    @Column(name = "U_BranchName")
    private String uBranchName;
    @Basic
    @Column(name = "U_GroupCode")
    private String uGroupCode;
    @Basic
    @Column(name = "U_GroupName")
    private String uGroupName;
    @Basic
    @Column(name = "U_FactoryCode")
    private String uFactoryCode;
    @Basic
    @Column(name = "U_FactoryName")
    private String uFactoryName;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getuBranchCode() {
        return uBranchCode;
    }

    public void setuBranchCode(String uBranchCode) {
        this.uBranchCode = uBranchCode;
    }

    public String getuBranchName() {
        return uBranchName;
    }

    public void setuBranchName(String uBranchName) {
        this.uBranchName = uBranchName;
    }

    public String getuGroupCode() {
        return uGroupCode;
    }

    public void setuGroupCode(String uGroupCode) {
        this.uGroupCode = uGroupCode;
    }

    public String getuGroupName() {
        return uGroupName;
    }

    public void setuGroupName(String uGroupName) {
        this.uGroupName = uGroupName;
    }

    public String getuFactoryCode() {
        return uFactoryCode;
    }

    public void setuFactoryCode(String uFactoryCode) {
        this.uFactoryCode = uFactoryCode;
    }

    public String getuFactoryName() {
        return uFactoryName;
    }

    public void setuFactoryName(String uFactoryName) {
        this.uFactoryName = uFactoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SapBranchGroupEntity that = (SapBranchGroupEntity) o;

        if (code != that.code) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (uBranchCode != null ? !uBranchCode.equals(that.uBranchCode) : that.uBranchCode != null) return false;
        if (uBranchName != null ? !uBranchName.equals(that.uBranchName) : that.uBranchName != null) return false;
        if (uGroupCode != null ? !uGroupCode.equals(that.uGroupCode) : that.uGroupCode != null) return false;
        if (uGroupName != null ? !uGroupName.equals(that.uGroupName) : that.uGroupName != null) return false;
        if (uFactoryCode != null ? !uFactoryCode.equals(that.uFactoryCode) : that.uFactoryCode != null) return false;
        if (uFactoryName != null ? !uFactoryName.equals(that.uFactoryName) : that.uFactoryName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (uBranchCode != null ? uBranchCode.hashCode() : 0);
        result = 31 * result + (uBranchName != null ? uBranchName.hashCode() : 0);
        result = 31 * result + (uGroupCode != null ? uGroupCode.hashCode() : 0);
        result = 31 * result + (uGroupName != null ? uGroupName.hashCode() : 0);
        result = 31 * result + (uFactoryCode != null ? uFactoryCode.hashCode() : 0);
        result = 31 * result + (uFactoryName != null ? uFactoryName.hashCode() : 0);
        return result;
    }
}
