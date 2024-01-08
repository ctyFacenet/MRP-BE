package com.facenet.mrp.service.dto;

import java.util.Date;
import java.util.List;

public class DetailVendorDTO {
    private String id;

    private String name;

    private String gender;

    private String position;

    private String title;

    private String email;

    private String phoneNumber;

    private String fax;

    private String address;

    private String nationality;

    private Date dateOfBirth;

    private List<DataItemInVendor> productInfoList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<DataItemInVendor> getProductInfoList() {
        return productInfoList;
    }

    public void setProductInfoList(List<DataItemInVendor> productInfoList) {
        this.productInfoList = productInfoList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public DetailVendorDTO(String id, String name, String gender, String position, String title, String email, String phoneNumber, String fax, String address, String nationality, Date dateOfBirth, List<DataItemInVendor> productInfoList) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.position = position;
        this.title = title;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.fax = fax;
        this.address = address;
        this.nationality = nationality;
        this.dateOfBirth = dateOfBirth;
        this.productInfoList = productInfoList;
    }

    public DetailVendorDTO(){

    }
    @Override
    public String toString() {
        return "DetailVendorDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", gender='" + gender + '\'' +
            ", position=" + position +
            ", title='" + title + '\'' +
            ", email='" + email + '\'' +
            ", phone=" + phoneNumber +
            ", fax='" + fax + '\'' +
            ", address='" + address + '\'' +
            ", national=" + nationality +
            ", birthDay='" + dateOfBirth + '\'' +
            '}';
    }
}
