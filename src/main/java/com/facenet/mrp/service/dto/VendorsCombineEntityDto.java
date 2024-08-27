package com.facenet.mrp.service.dto;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.facenet.mrp.domain.mrp.VendorsCombineEntity}
 */
@AllArgsConstructor
@NoArgsConstructor
public class VendorsCombineEntityDto implements Serializable {
    int id;
    String code;
    String name;
    Integer active;
    String email;
    String address;
    String taxCode;
    Integer sap;

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
}
