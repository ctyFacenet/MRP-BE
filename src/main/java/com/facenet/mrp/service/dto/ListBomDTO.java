package com.facenet.mrp.service.dto;

import com.facenet.mrp.service.utils.Constants;

import java.util.ArrayList;

public class ListBomDTO {

    private ArrayList<String> bomVersions;
    private String proName;

    private String itemGroup;

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public ArrayList<String> getBomVersions() {
        return bomVersions;
    }

    public void setBomVersions(ArrayList<String> bomVersions) {
        this.bomVersions = bomVersions;
    }

    public String getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(Integer itemGroup) {
        if (itemGroup == null) return;
        switch (itemGroup) {
            case Constants.BTP:
                this.itemGroup = "BTP";
                break;
            case Constants.TP:
                this.itemGroup = "TP";
                break;
            default:
                this.itemGroup = "NVL";
                break;
        }
    }
}
