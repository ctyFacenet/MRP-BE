package com.facenet.mrp.service.dto.mrp;

import com.querydsl.core.annotations.QueryProjection;

import java.util.Date;

public class MonitorListPrDTO {
    private Integer id;
    private String prCode;
    private String soCode;
    private String mrpCode;
    private String prCreateUser;
    private Date prCreateDate;
    private Date asignDate;
    private String status;

    public MonitorListPrDTO() {
    }

    @QueryProjection
    public MonitorListPrDTO(Integer id, String prCode, String soCode, String mrpCode, String prCreateUser, Date prCreateDate) {
        this.id = id;
        this.prCode = prCode;
        this.soCode = soCode;
        this.mrpCode = mrpCode;
        this.prCreateUser = prCreateUser;
        this.prCreateDate = prCreateDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrCode() {
        return prCode;
    }

    public void setPrCode(String prCode) {
        this.prCode = prCode;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getMrpCode() {
        return mrpCode;
    }

    public void setMrpCode(String mrpCode) {
        this.mrpCode = mrpCode;
    }

    public String getPrCreateUser() {
        return prCreateUser;
    }

    public void setPrCreateUser(String prCreateUser) {
        this.prCreateUser = prCreateUser;
    }

    public Date getPrCreateDate() {
        return prCreateDate;
    }

    public void setPrCreateDate(Date prCreateDate) {
        this.prCreateDate = prCreateDate;
    }

    public Date getAsignDate() {
        return asignDate;
    }

    public void setAsignDate(Date asignDate) {
        this.asignDate = asignDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
