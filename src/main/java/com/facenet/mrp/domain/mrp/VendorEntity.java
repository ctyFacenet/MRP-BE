package com.facenet.mrp.domain.mrp;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "vendor_2", schema = "material_requirements_planning", catalog = "")
public class VendorEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendorSeqGen")
    @GenericGenerator(name = "vendorSeqGen", strategy = "increment")
    @Id
    @Column(name = "vendor_id")
    private Integer vendorId;
    @Basic
    @Column(name = "vendor_code")
    private String vendorCode;
    @Basic
    @Column(name = "vendor_name")
    private String vendorName;
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

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VendorEntity that = (VendorEntity) o;

        if (vendorId != null ? !vendorId.equals(that.vendorId) : that.vendorId != null) return false;
        if (vendorCode != null ? !vendorCode.equals(that.vendorCode) : that.vendorCode != null) return false;
        if (vendorName != null ? !vendorName.equals(that.vendorName) : that.vendorName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = vendorId != null ? vendorId.hashCode() : 0;
        result = 31 * result + (vendorCode != null ? vendorCode.hashCode() : 0);
        result = 31 * result + (vendorName != null ? vendorName.hashCode() : 0);
        return result;
    }
}
