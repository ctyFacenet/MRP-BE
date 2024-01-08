package com.facenet.mrp.service.dto;

import java.util.Date;

public class ContactPersonDTO {

    private String addId;

    private String name;

    private String gender;

    private String position;

    private String title;

    private String email;

    private String phone;

    private String fax;

    private String address;

    private String national;

    private Date birthDay;

    public String getAddId() {
        return addId;
    }

    public void setAddId(String addId) {
        this.addId = addId;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public ContactPersonDTO(){

    }

    public ContactPersonDTO(String addId, String name, String gender, String position, String title, String email, String phone, String fax, String address, String national, Date birthDay) {
        this.addId = addId;
        this.name = name;
        this.gender = gender;
        this.position = position;
        this.title = title;
        this.email = email;
        this.phone = phone;
        this.fax = fax;
        this.address = address;
        this.national = national;
        this.birthDay = birthDay;
    }

    @Override
    public String toString() {
        return "ContactPersonDTO{" +
            "addId=" + addId +
            ", name='" + name + '\'' +
            ", gender='" + gender + '\'' +
            ", position=" + position +
            ", title='" + title + '\'' +
            ", email='" + email + '\'' +
            ", phone=" + phone +
            ", fax='" + fax + '\'' +
            ", address='" + address + '\'' +
            ", national=" + national +
            ", birthDay='" + birthDay + '\'' +
            '}';
    }
}
