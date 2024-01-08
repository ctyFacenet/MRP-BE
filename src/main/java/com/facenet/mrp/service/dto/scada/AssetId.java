package com.facenet.mrp.service.dto.scada;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AssetId {
    @JsonProperty("id")
    private String actualId;

    public String getActualId() {
        return actualId;
    }

    public void setActualId(String actualId) {
        this.actualId = actualId;
    }
}
