package com.facenet.mrp.service.dto;

public class GroupDTO {
    private String uGroupCode;
    private String uGroupName;
    private String uFactoryCode;
    private String uFactoryName;

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
}
