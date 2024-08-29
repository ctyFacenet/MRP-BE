package com.facenet.mrp.service.dto;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.facenet.mrp.domain.mrp.VendorsCombineEntity}
 */
@AllArgsConstructor
@NoArgsConstructor
public class VendorsCombineEntityDto implements Serializable {
    private Integer id;
    private String code;
    private String name;
    private Integer active;
    private String email;
    private String address;
    private String taxCode;
    private Integer sap;
    private String phone;
    private String otherName;
    private String fax;
    private String currency;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        return taxCode;
    }

    public void setTaxcode(String taxcode) {
        this.taxCode = taxcode;
    }

    public Integer getSap() {
        return sap;
    }

    public void setSap(Integer sap) {
        this.sap = sap;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
