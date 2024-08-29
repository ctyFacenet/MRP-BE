package com.facenet.mrp.domain.mrp;

import javax.persistence.*;

@Entity
@Table(name = "vendors_combine", schema = "material_requirements_planning", catalog = "")
public class VendorsCombineEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "code")
    private String code;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "active")
    private Integer active;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "address")
    private String address;

    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "currency")
    private String currency;
    @Basic
    @Column(name = "other_name")
    private String otherName;
    @Basic
    @Column(name = "fax")
    private String fax;
    @Basic
    @Column(name = "taxcode")
    private String taxcode;
    @Basic
    @Column(name = "sap")
    private Integer sap;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTaxcode() {
        return taxcode;
    }

    public void setTaxcode(String taxcode) {
        this.taxcode = taxcode;
    }

    public Integer getSap() {
        return sap;
    }

    public void setSap(Integer sap) {
        this.sap = sap;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
}
