package com.facenet.mrp.service.dto;

import com.querydsl.core.annotations.QueryProjection;

public class ListVendorDTO {
    private String vendorCode;
    private String vendorName;
    private String otherName;
    private String email;
    private String address;
    private String transactionMoney;
    private String phoneNumber;
    private String faxNumber;
    private String taxCode;
    private String validFor;
    private String frozenFor;

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

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
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

    public String getTransactionMoney() {
        return transactionMoney;
    }

    public void setTransactionMoney(String transactionMoney) {
        this.transactionMoney = transactionMoney;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public ListVendorDTO(){

    }

    public String getValidFor() {
        return validFor;
    }

    public void setValidFor(String validFor) {
        this.validFor = validFor;
    }

    public String getFrozenFor() {
        return frozenFor;
    }

    public void setFrozenFor(String frozenFor) {
        this.frozenFor = frozenFor;
    }

    @QueryProjection
    public ListVendorDTO(String vendorCode, String vendorName, String otherName, String email, String address, String transactionMoney, String phoneNumber, String faxNumber, String taxCode, String validFor, String frozenFor) {
        this.vendorCode = vendorCode;
        this.vendorName = vendorName;
        this.otherName = otherName;
        this.email = email;
        this.address = address;
        this.transactionMoney = transactionMoney;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
        this.taxCode = taxCode;
        this.validFor = validFor;
        this.frozenFor = frozenFor;
    }

    @QueryProjection
    public ListVendorDTO(String vendorCode, String vendorName, String otherName, String email, String address, String transactionMoney, String phoneNumber, String faxNumber, String validFor, String frozenFor) {
        this.vendorCode = vendorCode;
        this.vendorName = vendorName;
        this.otherName = otherName;
        this.email = email;
        this.address = address;
        this.transactionMoney = transactionMoney;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
        this.validFor = validFor;
        this.frozenFor = frozenFor;
    }

    @Override
    public String toString() {
        return "ListVendorDTO{" +
            "vendorCode=" + vendorCode +
            ", vendorName='" + vendorName + '\'' +
            ", otherName='" + otherName + '\'' +
            ", email=" + email +
            ", address='" + address + '\'' +
            ", transactionMoney=" + transactionMoney +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", faxNumber='" + faxNumber + '\'' +
            ", taxCode='" + taxCode + '\'' +
            '}';
    }
}
