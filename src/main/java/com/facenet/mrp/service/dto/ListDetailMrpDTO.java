package com.facenet.mrp.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Setter
@Getter
public class ListDetailMrpDTO {
    private String mrpSubCode;

    private String mrpCode;

    private String soCode;

    private String mrpDescription;

    private String sortType;

    private Date timeStart;

    private Date timeEnd;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Timestamp createdTime;

    private String createdBy;

    private String analysisType;
    private Integer analysisKind;
    private String items;
    private String warehouseAnalysis;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Byte status;

    private String analysisSource;

    private String analysisMode;

    private Date lastAccess;

    public ListDetailMrpDTO() {
    }

    public ListDetailMrpDTO(String mrpSubCode, String mrpCode, String soCode, String mrpDescription, String sortType, Date timeStart, Date timeEnd, String analysisType, Integer analysisKind, String analysisSource, String items, String warehouseAnalysis, String analysisMode, Date lastAccess, Byte status) {
        this.mrpSubCode = mrpSubCode;
        this.mrpCode = mrpCode;
        this.soCode = soCode;
        this.mrpDescription = mrpDescription;
        this.sortType = sortType;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.analysisType = analysisType;
        this.analysisKind = analysisKind;
        this.analysisSource = analysisSource;
        this.items = items;
        this.warehouseAnalysis = warehouseAnalysis;
        this.analysisMode = analysisMode;
        this.lastAccess = lastAccess;
        this.status = status;
    }

}
