package com.facenet.mrp.service.dto;


import java.util.List;

public class BranchGroupDTO {
    private String uBranchCode;
    private String uBranchName;
    private List<GroupDTO> groupDTOList;
    private String uFactoryCode;
    private String uFactoryName;

    public List<GroupDTO> getGroupDTOList() {
        return groupDTOList;
    }

    public void setGroupDTOList(List<GroupDTO> groupDTOList) {
        this.groupDTOList = groupDTOList;
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
