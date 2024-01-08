package com.facenet.mrp.service.dto.scada;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseAsset {
    private AssetId id;
    private String name;
    private String type;
    private String label;
    private Long createdTime;
    @JsonIgnore
    private AssetDetail assetDetail;

    public AssetId getId() {
        return id;
    }

    public void setId(AssetId id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public AssetDetail getAssetDetail() {
        return assetDetail;
    }

    public void setAssetDetail(AssetDetail assetDetail) {
        this.assetDetail = assetDetail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
