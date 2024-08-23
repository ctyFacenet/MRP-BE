package com.facenet.mrp.domain.sap;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "OITT")
public class OittEntity {
    private String code;
    private String treeType;
    private Long priceList;
    private Long qauntity;
    private String useFthrWhs;
    private Date createDate;
    private Date updateDate;
    private String transfered;
    private String dataSource;
    private Long userSign;
    private Long scnCounter;
    private String dispCurr;
    private String toWH;
    private String object;
    private Long logInstac;
    private Long userSign2;
    private String ocrCode;
    private String hideComp;
    private String ocrCode2;
    private String ocrCode3;
    private String ocrCode4;
    private String ocrCode5;
    private Long updateTime;
    private String project;
    private Long plAvgSize;
    private String uPerType;
    private Long uPercent;
    private String uStatus;
    private String uRdCode;
    private String uChungLoai;
    private String uTennhom;
    @Id
    @Column(name = "Code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    @Basic
    @Column(name = "TreeType")
    public String getTreeType() {
        return treeType;
    }

    public void setTreeType(String treeType) {
        this.treeType = treeType;
    }
    @Basic
    @Column(name = "PriceList")
    public Long getPriceList() {
        return priceList;
    }

    public void setPriceList(Long priceList) {
        this.priceList = priceList;
    }
    @Basic
    @Column(name = "Qauntity")
    public Long getQauntity() {
        return qauntity;
    }

    public void setQauntity(Long qauntity) {
        this.qauntity = qauntity;
    }

    @Basic
    @Column(name = "UseFthrWhs")
    public String getUseFthrWhs() {
        return useFthrWhs;
    }

    public void setUseFthrWhs(String useFthrWhs) {
        this.useFthrWhs = useFthrWhs;
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
    @Column(name = "UpdateDate")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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
    @Column(name = "DataSource")
    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
    @Basic
    @Column(name = "UserSign")
    public Long getUserSign() {
        return userSign;
    }

    public void setUserSign(Long userSign) {
        this.userSign = userSign;
    }
    @Basic
    @Column(name = "SCNCounter")
    public Long getScnCounter() {
        return scnCounter;
    }

    public void setScnCounter(Long scnCounter) {
        this.scnCounter = scnCounter;
    }
    @Basic
    @Column(name = "DispCurr")
    public String getDispCurr() {
        return dispCurr;
    }

    public void setDispCurr(String dispCurr) {
        this.dispCurr = dispCurr;
    }
    @Basic
    @Column(name = "ToWH")
    public String getToWH() {
        return toWH;
    }

    public void setToWH(String toWH) {
        this.toWH = toWH;
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
    @Column(name = "LogInstac")
    public Long getLogInstac() {
        return logInstac;
    }

    public void setLogInstac(Long logInstac) {
        this.logInstac = logInstac;
    }
    @Basic
    @Column(name = "UserSign2")
    public Long getUserSign2() {
        return userSign2;
    }

    public void setUserSign2(Long userSign2) {
        this.userSign2 = userSign2;
    }
    @Basic
    @Column(name = "OcrCode")
    public String getOcrCode() {
        return ocrCode;
    }

    public void setOcrCode(String ocrCode) {
        this.ocrCode = ocrCode;
    }
    @Basic
    @Column(name = "HideComp")
    public String getHideComp() {
        return hideComp;
    }

    public void setHideComp(String hideComp) {
        this.hideComp = hideComp;
    }
    @Basic
    @Column(name = "OcrCode2")
    public String getOcrCode2() {
        return ocrCode2;
    }

    public void setOcrCode2(String ocrCode2) {
        this.ocrCode2 = ocrCode2;
    }
    @Basic
    @Column(name = "OcrCode3")
    public String getOcrCode3() {
        return ocrCode3;
    }

    public void setOcrCode3(String ocrCode3) {
        this.ocrCode3 = ocrCode3;
    }
    @Basic
    @Column(name = "OcrCode4")
    public String getOcrCode4() {
        return ocrCode4;
    }

    public void setOcrCode4(String ocrCode4) {
        this.ocrCode4 = ocrCode4;
    }
    @Basic
    @Column(name = "OcrCode5")
    public String getOcrCode5() {
        return ocrCode5;
    }

    public void setOcrCode5(String ocrCode5) {
        this.ocrCode5 = ocrCode5;
    }
    @Basic
    @Column(name = "UpdateTime")
    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
    @Basic
    @Column(name = "Project")
    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }
    @Basic
    @Column(name = "PlAvgSize")
    public Long getPlAvgSize() {
        return plAvgSize;
    }

    public void setPlAvgSize(Long plAvgSize) {
        this.plAvgSize = plAvgSize;
    }
    @Basic
    @Column(name = "U_PerType")
    public String getuPerType() {
        return uPerType;
    }

    public void setuPerType(String uPerType) {
        this.uPerType = uPerType;
    }
    @Basic
    @Column(name = "U_Percent")
    public Long getuPercent() {
        return uPercent;
    }

    public void setuPercent(Long uPercent) {
        this.uPercent = uPercent;
    }
    @Basic
    @Column(name = "U_status")
    public String getuStatus() {
        return uStatus;
    }

    public void setuStatus(String uStatus) {
        this.uStatus = uStatus;
    }
    @Basic
    @Column(name = "U_RDCODE")
    public String getuRdCode() {
        return uRdCode;
    }

    public void setuRdCode(String uRdCode) {
        this.uRdCode = uRdCode;
    }
    @Basic
    @Column(name = "U_Chungloai")
    public String getuChungLoai() {
        return uChungLoai;
    }

    public void setuChungLoai(String uChungLoai) {
        this.uChungLoai = uChungLoai;
    }
    @Basic
    @Column(name = "U_Tennhom")
    public String getuTennhom() {
        return uTennhom;
    }

    public void setuTennhom(String uTennhom) {
        this.uTennhom = uTennhom;
    }
}
