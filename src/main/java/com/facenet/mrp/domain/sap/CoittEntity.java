package com.facenet.mrp.domain.sap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"@COITT\"")
public class CoittEntity {
    private int docEntry;
    private Integer docNum;
    private Integer period;
    private Short instance;
    private Integer series;
    private String handwrtten;
    private String canceled;
    private String object;
    private Integer logInst;
    private Integer userSign;
    private String transfered;
    private String status;
    private Date createDate;
    private Short createTime;
    private Date updateDate;
    private Short updateTime;
    private String dataSource;
    private String requestStatus;
    private String creator;
    private String remark;
    private String uProNo;
    private String uProNam;
    private String uWhsCod;
    private Double uQuantity;
    private String uRemark;
    private String uVersions;
    private String uSpec;
    private String uPrefix;
    private String uStatus;
    private String uUom;
    private String uActive;
    private String uInact;
    private Date uFromDate;
    private Date uToDate;
    private String uDocUrl;
    private String uDocUrl2;
    private String uProMode;

    @Id
    @Column(name = "DocEntry")
    public int getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(int docEntry) {
        this.docEntry = docEntry;
    }

    @Basic
    @Column(name = "DocNum")
    public Integer getDocNum() {
        return docNum;
    }

    public void setDocNum(Integer docNum) {
        this.docNum = docNum;
    }

    @Basic
    @Column(name = "Period")
    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    @Basic
    @Column(name = "Instance")
    public Short getInstance() {
        return instance;
    }

    public void setInstance(Short instance) {
        this.instance = instance;
    }

    @Basic
    @Column(name = "Series")
    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    @Basic
    @Column(name = "Handwrtten")
    public String getHandwrtten() {
        return handwrtten;
    }

    public void setHandwrtten(String handwrtten) {
        this.handwrtten = handwrtten;
    }

    @Basic
    @Column(name = "Canceled")
    public String getCanceled() {
        return canceled;
    }

    public void setCanceled(String canceled) {
        this.canceled = canceled;
    }

    @Basic
    @Column(name = "Object")
    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    @Basic
    @Column(name = "LogInst")
    public Integer getLogInst() {
        return logInst;
    }

    public void setLogInst(Integer logInst) {
        this.logInst = logInst;
    }

    @Basic
    @Column(name = "UserSign")
    public Integer getUserSign() {
        return userSign;
    }

    public void setUserSign(Integer userSign) {
        this.userSign = userSign;
    }

    @Basic
    @Column(name = "Transfered")
    public String getTransfered() {
        return transfered;
    }

    public void setTransfered(String transfered) {
        this.transfered = transfered;
    }

    @Basic
    @Column(name = "Status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "CreateDate")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "CreateTime")
    public Short getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Short createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "UpdateDate")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Basic
    @Column(name = "UpdateTime")
    public Short getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Short updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "DataSource")
    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    @Basic
    @Column(name = "RequestStatus")
    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    @Basic
    @Column(name = "Creator")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Basic
    @Column(name = "Remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "U_ProNo")
    public String getuProNo() {
        return uProNo;
    }

    public void setuProNo(String uProNo) {
        this.uProNo = uProNo;
    }

    @Basic
    @Column(name = "U_ProNam")
    public String getuProNam() {
        return uProNam;
    }

    public void setuProNam(String uProNam) {
        this.uProNam = uProNam;
    }

    @Basic
    @Column(name = "U_WhsCod")
    public String getuWhsCod() {
        return uWhsCod;
    }

    public void setuWhsCod(String uWhsCod) {
        this.uWhsCod = uWhsCod;
    }

    @Basic
    @Column(name = "U_Quantity")
    public Double getuQuantity() {
        return uQuantity;
    }

    public void setuQuantity(Double uQuantity) {
        this.uQuantity = uQuantity;
    }

    @Basic
    @Column(name = "U_Remark")
    public String getuRemark() {
        return uRemark;
    }

    public void setuRemark(String uRemark) {
        this.uRemark = uRemark;
    }

    @Basic
    @Column(name = "U_Versions")
    public String getuVersions() {
        return uVersions;
    }

    public void setuVersions(String uVersions) {
        this.uVersions = uVersions;
    }

    @Basic
    @Column(name = "U_Spec")
    public String getuSpec() {
        return uSpec;
    }

    public void setuSpec(String uSpec) {
        this.uSpec = uSpec;
    }

    @Basic
    @Column(name = "U_Prefix")
    public String getuPrefix() {
        return uPrefix;
    }

    public void setuPrefix(String uPrefix) {
        this.uPrefix = uPrefix;
    }

    @Basic
    @Column(name = "U_Status")
    public String getuStatus() {
        return uStatus;
    }

    public void setuStatus(String uStatus) {
        this.uStatus = uStatus;
    }

    @Basic
    @Column(name = "U_Uom")
    public String getuUom() {
        return uUom;
    }

    public void setuUom(String uUom) {
        this.uUom = uUom;
    }

    @Basic
    @Column(name = "U_Active")
    public String getuActive() {
        return uActive;
    }

    public void setuActive(String uActive) {
        this.uActive = uActive;
    }

    @Basic
    @Column(name = "U_Inact")
    public String getuInact() {
        return uInact;
    }

    public void setuInact(String uInact) {
        this.uInact = uInact;
    }

    @Basic
    @Column(name = "U_FromDate")
    public Date getuFromDate() {
        return uFromDate;
    }

    public void setuFromDate(Date uFromDate) {
        this.uFromDate = uFromDate;
    }

    @Basic
    @Column(name = "U_ToDate")
    public Date getuToDate() {
        return uToDate;
    }

    public void setuToDate(Date uToDate) {
        this.uToDate = uToDate;
    }

    @Basic
    @Column(name = "U_DocURL")
    public String getuDocUrl() {
        return uDocUrl;
    }

    public void setuDocUrl(String uDocUrl) {
        this.uDocUrl = uDocUrl;
    }

    @Basic
    @Column(name = "U_DocURL2")
    public String getuDocUrl2() {
        return uDocUrl2;
    }

    public void setuDocUrl2(String uDocUrl2) {
        this.uDocUrl2 = uDocUrl2;
    }

    @Basic
    @Column(name = "U_ProMode")
    public String getuProMode() {
        return uProMode;
    }

    public void setuProMode(String uProMode) {
        this.uProMode = uProMode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoittEntity that = (CoittEntity) o;
        return docEntry == that.docEntry && uQuantity == that.uQuantity && Objects.equals(docNum, that.docNum) && Objects.equals(period, that.period) && Objects.equals(instance, that.instance) && Objects.equals(series, that.series) && Objects.equals(handwrtten, that.handwrtten) && Objects.equals(canceled, that.canceled) && Objects.equals(object, that.object) && Objects.equals(logInst, that.logInst) && Objects.equals(userSign, that.userSign) && Objects.equals(transfered, that.transfered) && Objects.equals(status, that.status) && Objects.equals(createDate, that.createDate) && Objects.equals(createTime, that.createTime) && Objects.equals(updateDate, that.updateDate) && Objects.equals(updateTime, that.updateTime) && Objects.equals(dataSource, that.dataSource) && Objects.equals(requestStatus, that.requestStatus) && Objects.equals(creator, that.creator) && Objects.equals(remark, that.remark) && Objects.equals(uProNo, that.uProNo) && Objects.equals(uProNam, that.uProNam) && Objects.equals(uWhsCod, that.uWhsCod) && Objects.equals(uRemark, that.uRemark) && Objects.equals(uVersions, that.uVersions) && Objects.equals(uSpec, that.uSpec) && Objects.equals(uPrefix, that.uPrefix) && Objects.equals(uStatus, that.uStatus) && Objects.equals(uUom, that.uUom) && Objects.equals(uActive, that.uActive) && Objects.equals(uInact, that.uInact) && Objects.equals(uFromDate, that.uFromDate) && Objects.equals(uToDate, that.uToDate) && Objects.equals(uDocUrl, that.uDocUrl) && Objects.equals(uDocUrl2, that.uDocUrl2) && Objects.equals(uProMode, that.uProMode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(docEntry, docNum, period, instance, series, handwrtten, canceled, object, logInst, userSign, transfered, status, createDate, createTime, updateDate, updateTime, dataSource, requestStatus, creator, remark, uProNo, uProNam, uWhsCod, uQuantity, uRemark, uVersions, uSpec, uPrefix, uStatus, uUom, uActive, uInact, uFromDate, uToDate, uDocUrl, uDocUrl2, uProMode);
    }
}
