package com.facenet.mrp.service.dto;

import java.util.HashMap;

public class MrpCheckReportIntegrationOutputDTO {

    String splitString;
    HashMap<String, MrpActualOutputDTO> hashMap;
    public MrpCheckReportIntegrationOutputDTO() {
    }

    public MrpCheckReportIntegrationOutputDTO(HashMap<String, MrpActualOutputDTO> hashMap) {
        this.hashMap = hashMap;
    }

    public MrpCheckReportIntegrationOutputDTO(HashMap<String, MrpActualOutputDTO> hashMap, String splitString) {
        this.hashMap = hashMap;
        this.splitString = splitString;
    }

    public HashMap<String, MrpActualOutputDTO> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, MrpActualOutputDTO> hashMap) {
        this.hashMap = hashMap;
    }

    public String getSplitString() {
        return splitString;
    }

    public void setSplitString(String splitString) {
        this.splitString = splitString;
    }
}
