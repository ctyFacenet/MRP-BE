package com.facenet.mrp.service.dto;

import com.facenet.mrp.domain.mrp.Param;

import java.io.Serializable;

/**
 * A DTO for the {@link Param} entity
 */
public class ParamDto implements Serializable {
    private String paramValue;
    private String paramDesc;

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamDesc() {
        return paramDesc;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }
}
