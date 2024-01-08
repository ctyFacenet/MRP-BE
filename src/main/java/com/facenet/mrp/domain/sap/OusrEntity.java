package com.facenet.mrp.domain.sap;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Arrays;

@Entity
@Table(name = "OUSR")
public class OusrEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "USERID")
    private Short userid;
    @Basic
    @Column(name = "PASSWORD")
    private String password;
    @Basic
    @Column(name = "PASSWORD1")
    private String password1;
    @Basic
    @Column(name = "PASSWORD2")
    private String password2;
    @Basic
    @Column(name = "INTERNAL_K")
    private Short internalK;
    @Basic
    @Column(name = "USER_CODE")
    private String userCode;
    @Basic
    @Column(name = "U_NAME")
    private String uName;
    @Basic
    @Column(name = "GROUPS")
    private Short groups;
    @Basic
    @Column(name = "PASSWORD4")
    private String password4;
    @Basic
    @Column(name = "ALLOWENCES")
    private String allowences;
    @Basic
    @Column(name = "SUPERUSER")
    private String superuser;
    @Basic
    @Column(name = "DISCOUNT")
    private BigDecimal discount;
    @Basic
    @Column(name = "PASSWORD3")
    private String password3;
    @Basic
    @Column(name = "Info1File")
    private String info1File;
    @Basic
    @Column(name = "Info1Field")
    private Short info1Field;
    @Basic
    @Column(name = "Info2File")
    private String info2File;
    @Basic
    @Column(name = "Info2Field")
    private Short info2Field;
    @Basic
    @Column(name = "Info3File")
    private String info3File;
    @Basic
    @Column(name = "Info3Field")
    private Short info3Field;
    @Basic
    @Column(name = "Info4File")
    private String info4File;
    @Basic
    @Column(name = "Info4Field")
    private Short info4Field;
    @Basic
    @Column(name = "dType")
    private String dType;
    @Basic
    @Column(name = "E_Mail")
    private String eMail;
    @Basic
    @Column(name = "PortNum")
    private String portNum;
    @Basic
    @Column(name = "OutOfOffic")
    private String outOfOffic;
    @Basic
    @Column(name = "SendEMail")
    private String sendEMail;
    @Basic
    @Column(name = "SendSMS")
    private String sendSms;
    @Basic
    @Column(name = "DfltsGroup")
    private String dfltsGroup;
    @Basic
    @Column(name = "CashLimit")
    private String cashLimit;
    @Basic
    @Column(name = "MaxCashSum")
    private BigDecimal maxCashSum;
    @Basic
    @Column(name = "Fax")
    private String fax;
    @Basic
    @Column(name = "SendFax")
    private String sendFax;
    @Basic
    @Column(name = "Locked")
    private String locked;
    @Basic
    @Column(name = "Department")
    private Short department;
    @Basic
    @Column(name = "Branch")
    private Short branch;
    @Basic
    @Column(name = "UserPrefs")
    private byte[] userPrefs;
    @Basic
    @Column(name = "Language")
    private Integer language;
    @Basic
    @Column(name = "Charset")
    private Short charset;
    @Basic
    @Column(name = "OpenCdt")
    private String openCdt;
    @Basic
    @Column(name = "CdtPrvDays")
    private Integer cdtPrvDays;
    @Basic
    @Column(name = "DsplyRates")
    private String dsplyRates;
    @Basic
    @Column(name = "AuImpRates")
    private String auImpRates;
    @Basic
    @Column(name = "OpenDps")
    private String openDps;
    @Basic
    @Column(name = "RcrFlag")
    private String rcrFlag;
    @Basic
    @Column(name = "CheckFiles")
    private String checkFiles;
    @Basic
    @Column(name = "OpenCredit")
    private String openCredit;
    @Basic
    @Column(name = "CreditDay1")
    private Short creditDay1;
    @Basic
    @Column(name = "CreditDay2")
    private Short creditDay2;
    @Basic
    @Column(name = "WallPaper")
    private String wallPaper;
    @Basic
    @Column(name = "WllPprDsp")
    private Short wllPprDsp;
    @Basic
    @Column(name = "AdvImagePr")
    private String advImagePr;
    @Basic
    @Column(name = "ContactLog")
    private String contactLog;
    @Basic
    @Column(name = "LastWarned")
    private Date lastWarned;
    @Basic
    @Column(name = "AlertPolFr")
    private Short alertPolFr;
    @Basic
    @Column(name = "ScreenLock")
    private Short screenLock;
    @Basic
    @Column(name = "ShowNewMsg")
    private String showNewMsg;
    @Basic
    @Column(name = "Picture")
    private String picture;
    @Basic
    @Column(name = "Position")
    private String position;
    @Basic
    @Column(name = "Address")
    private String address;
    @Basic
    @Column(name = "Country")
    private String country;
    @Basic
    @Column(name = "Tel1")
    private String tel1;
    @Basic
    @Column(name = "Tel2")
    private String tel2;
    @Basic
    @Column(name = "GENDER")
    private String gender;
    @Basic
    @Column(name = "Birthday")
    private Date birthday;
    @Basic
    @Column(name = "EnbMenuFlt")
    private String enbMenuFlt;
    @Basic
    @Column(name = "objType")
    private String objType;
    @Basic
    @Column(name = "logInstanc")
    private Integer logInstanc;
    @Basic
    @Column(name = "userSign")
    private Short userSign;
    @Basic
    @Column(name = "createDate")
    private Date createDate;
    @Basic
    @Column(name = "userSign2")
    private Short userSign2;
    @Basic
    @Column(name = "updateDate")
    private Date updateDate;
    @Basic
    @Column(name = "OneLogPwd")
    private String oneLogPwd;
    @Basic
    @Column(name = "lastLogin")
    private Date lastLogin;
    @Basic
    @Column(name = "LastPwds")
    private String lastPwds;
    @Basic
    @Column(name = "LastPwds2")
    private String lastPwds2;
    @Basic
    @Column(name = "LastPwdSet")
    private Date lastPwdSet;
    @Basic
    @Column(name = "FailedLog")
    private Integer failedLog;
    @Basic
    @Column(name = "PwdNeverEx")
    private String pwdNeverEx;
    @Basic
    @Column(name = "SalesDisc")
    private BigDecimal salesDisc;
    @Basic
    @Column(name = "PurchDisc")
    private BigDecimal purchDisc;
    @Basic
    @Column(name = "LstLogoutD")
    private Date lstLogoutD;
    @Basic
    @Column(name = "LstLoginT")
    private Integer lstLoginT;
    @Basic
    @Column(name = "LstLogoutT")
    private Integer lstLogoutT;
    @Basic
    @Column(name = "LstPwdChT")
    private Integer lstPwdChT;
    @Basic
    @Column(name = "LstPwdChB")
    private String lstPwdChB;
    @Basic
    @Column(name = "RclFlag")
    private String rclFlag;
    @Basic
    @Column(name = "MobileUser")
    private String mobileUser;
    @Basic
    @Column(name = "MobileIMEI")
    private String mobileImei;
    @Basic
    @Column(name = "PrsWkCntEb")
    private String prsWkCntEb;
    @Basic
    @Column(name = "SnapShotId")
    private Integer snapShotId;
    @Basic
    @Column(name = "STData")
    private String stData;
    @Basic
    @Column(name = "SupportUsr")
    private String supportUsr;
    @Basic
    @Column(name = "NoSTPwdNum")
    private Short noStPwdNum;
    @Basic
    @Column(name = "DomainUser")
    private String domainUser;
    @Basic
    @Column(name = "CUSAgree")
    private String cusAgree;
    @Basic
    @Column(name = "EmailSig")
    private String emailSig;
    @Basic
    @Column(name = "TPLId")
    private Short tplId;
    @Basic
    @Column(name = "DigCrtPath")
    private String digCrtPath;
    @Basic
    @Column(name = "ShowNewTsk")
    private String showNewTsk;
    @Basic
    @Column(name = "IntgrtEb")
    private String intgrtEb;
    @Basic
    @Column(name = "AllBrnchF")
    private String allBrnchF;
    @Basic
    @Column(name = "EvtNotify")
    private String evtNotify;

    public Short getUserid() {
        return userid;
    }

    public void setUserid(Short userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public Short getInternalK() {
        return internalK;
    }

    public void setInternalK(Short internalK) {
        this.internalK = internalK;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public Short getGroups() {
        return groups;
    }

    public void setGroups(Short groups) {
        this.groups = groups;
    }

    public String getPassword4() {
        return password4;
    }

    public void setPassword4(String password4) {
        this.password4 = password4;
    }

    public String getAllowences() {
        return allowences;
    }

    public void setAllowences(String allowences) {
        this.allowences = allowences;
    }

    public String getSuperuser() {
        return superuser;
    }

    public void setSuperuser(String superuser) {
        this.superuser = superuser;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getPassword3() {
        return password3;
    }

    public void setPassword3(String password3) {
        this.password3 = password3;
    }

    public String getInfo1File() {
        return info1File;
    }

    public void setInfo1File(String info1File) {
        this.info1File = info1File;
    }

    public Short getInfo1Field() {
        return info1Field;
    }

    public void setInfo1Field(Short info1Field) {
        this.info1Field = info1Field;
    }

    public String getInfo2File() {
        return info2File;
    }

    public void setInfo2File(String info2File) {
        this.info2File = info2File;
    }

    public Short getInfo2Field() {
        return info2Field;
    }

    public void setInfo2Field(Short info2Field) {
        this.info2Field = info2Field;
    }

    public String getInfo3File() {
        return info3File;
    }

    public void setInfo3File(String info3File) {
        this.info3File = info3File;
    }

    public Short getInfo3Field() {
        return info3Field;
    }

    public void setInfo3Field(Short info3Field) {
        this.info3Field = info3Field;
    }

    public String getInfo4File() {
        return info4File;
    }

    public void setInfo4File(String info4File) {
        this.info4File = info4File;
    }

    public Short getInfo4Field() {
        return info4Field;
    }

    public void setInfo4Field(Short info4Field) {
        this.info4Field = info4Field;
    }

    public String getdType() {
        return dType;
    }

    public void setdType(String dType) {
        this.dType = dType;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPortNum() {
        return portNum;
    }

    public void setPortNum(String portNum) {
        this.portNum = portNum;
    }

    public String getOutOfOffic() {
        return outOfOffic;
    }

    public void setOutOfOffic(String outOfOffic) {
        this.outOfOffic = outOfOffic;
    }

    public String getSendEMail() {
        return sendEMail;
    }

    public void setSendEMail(String sendEMail) {
        this.sendEMail = sendEMail;
    }

    public String getSendSms() {
        return sendSms;
    }

    public void setSendSms(String sendSms) {
        this.sendSms = sendSms;
    }

    public String getDfltsGroup() {
        return dfltsGroup;
    }

    public void setDfltsGroup(String dfltsGroup) {
        this.dfltsGroup = dfltsGroup;
    }

    public String getCashLimit() {
        return cashLimit;
    }

    public void setCashLimit(String cashLimit) {
        this.cashLimit = cashLimit;
    }

    public BigDecimal getMaxCashSum() {
        return maxCashSum;
    }

    public void setMaxCashSum(BigDecimal maxCashSum) {
        this.maxCashSum = maxCashSum;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getSendFax() {
        return sendFax;
    }

    public void setSendFax(String sendFax) {
        this.sendFax = sendFax;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public Short getDepartment() {
        return department;
    }

    public void setDepartment(Short department) {
        this.department = department;
    }

    public Short getBranch() {
        return branch;
    }

    public void setBranch(Short branch) {
        this.branch = branch;
    }

    public byte[] getUserPrefs() {
        return userPrefs;
    }

    public void setUserPrefs(byte[] userPrefs) {
        this.userPrefs = userPrefs;
    }

    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }

    public Short getCharset() {
        return charset;
    }

    public void setCharset(Short charset) {
        this.charset = charset;
    }

    public String getOpenCdt() {
        return openCdt;
    }

    public void setOpenCdt(String openCdt) {
        this.openCdt = openCdt;
    }

    public Integer getCdtPrvDays() {
        return cdtPrvDays;
    }

    public void setCdtPrvDays(Integer cdtPrvDays) {
        this.cdtPrvDays = cdtPrvDays;
    }

    public String getDsplyRates() {
        return dsplyRates;
    }

    public void setDsplyRates(String dsplyRates) {
        this.dsplyRates = dsplyRates;
    }

    public String getAuImpRates() {
        return auImpRates;
    }

    public void setAuImpRates(String auImpRates) {
        this.auImpRates = auImpRates;
    }

    public String getOpenDps() {
        return openDps;
    }

    public void setOpenDps(String openDps) {
        this.openDps = openDps;
    }

    public String getRcrFlag() {
        return rcrFlag;
    }

    public void setRcrFlag(String rcrFlag) {
        this.rcrFlag = rcrFlag;
    }

    public String getCheckFiles() {
        return checkFiles;
    }

    public void setCheckFiles(String checkFiles) {
        this.checkFiles = checkFiles;
    }

    public String getOpenCredit() {
        return openCredit;
    }

    public void setOpenCredit(String openCredit) {
        this.openCredit = openCredit;
    }

    public Short getCreditDay1() {
        return creditDay1;
    }

    public void setCreditDay1(Short creditDay1) {
        this.creditDay1 = creditDay1;
    }

    public Short getCreditDay2() {
        return creditDay2;
    }

    public void setCreditDay2(Short creditDay2) {
        this.creditDay2 = creditDay2;
    }

    public String getWallPaper() {
        return wallPaper;
    }

    public void setWallPaper(String wallPaper) {
        this.wallPaper = wallPaper;
    }

    public Short getWllPprDsp() {
        return wllPprDsp;
    }

    public void setWllPprDsp(Short wllPprDsp) {
        this.wllPprDsp = wllPprDsp;
    }

    public String getAdvImagePr() {
        return advImagePr;
    }

    public void setAdvImagePr(String advImagePr) {
        this.advImagePr = advImagePr;
    }

    public String getContactLog() {
        return contactLog;
    }

    public void setContactLog(String contactLog) {
        this.contactLog = contactLog;
    }

    public Date getLastWarned() {
        return lastWarned;
    }

    public void setLastWarned(Date lastWarned) {
        this.lastWarned = lastWarned;
    }

    public Short getAlertPolFr() {
        return alertPolFr;
    }

    public void setAlertPolFr(Short alertPolFr) {
        this.alertPolFr = alertPolFr;
    }

    public Short getScreenLock() {
        return screenLock;
    }

    public void setScreenLock(Short screenLock) {
        this.screenLock = screenLock;
    }

    public String getShowNewMsg() {
        return showNewMsg;
    }

    public void setShowNewMsg(String showNewMsg) {
        this.showNewMsg = showNewMsg;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEnbMenuFlt() {
        return enbMenuFlt;
    }

    public void setEnbMenuFlt(String enbMenuFlt) {
        this.enbMenuFlt = enbMenuFlt;
    }

    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    public Integer getLogInstanc() {
        return logInstanc;
    }

    public void setLogInstanc(Integer logInstanc) {
        this.logInstanc = logInstanc;
    }

    public Short getUserSign() {
        return userSign;
    }

    public void setUserSign(Short userSign) {
        this.userSign = userSign;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Short getUserSign2() {
        return userSign2;
    }

    public void setUserSign2(Short userSign2) {
        this.userSign2 = userSign2;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getOneLogPwd() {
        return oneLogPwd;
    }

    public void setOneLogPwd(String oneLogPwd) {
        this.oneLogPwd = oneLogPwd;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getLastPwds() {
        return lastPwds;
    }

    public void setLastPwds(String lastPwds) {
        this.lastPwds = lastPwds;
    }

    public String getLastPwds2() {
        return lastPwds2;
    }

    public void setLastPwds2(String lastPwds2) {
        this.lastPwds2 = lastPwds2;
    }

    public Date getLastPwdSet() {
        return lastPwdSet;
    }

    public void setLastPwdSet(Date lastPwdSet) {
        this.lastPwdSet = lastPwdSet;
    }

    public Integer getFailedLog() {
        return failedLog;
    }

    public void setFailedLog(Integer failedLog) {
        this.failedLog = failedLog;
    }

    public String getPwdNeverEx() {
        return pwdNeverEx;
    }

    public void setPwdNeverEx(String pwdNeverEx) {
        this.pwdNeverEx = pwdNeverEx;
    }

    public BigDecimal getSalesDisc() {
        return salesDisc;
    }

    public void setSalesDisc(BigDecimal salesDisc) {
        this.salesDisc = salesDisc;
    }

    public BigDecimal getPurchDisc() {
        return purchDisc;
    }

    public void setPurchDisc(BigDecimal purchDisc) {
        this.purchDisc = purchDisc;
    }

    public Date getLstLogoutD() {
        return lstLogoutD;
    }

    public void setLstLogoutD(Date lstLogoutD) {
        this.lstLogoutD = lstLogoutD;
    }

    public Integer getLstLoginT() {
        return lstLoginT;
    }

    public void setLstLoginT(Integer lstLoginT) {
        this.lstLoginT = lstLoginT;
    }

    public Integer getLstLogoutT() {
        return lstLogoutT;
    }

    public void setLstLogoutT(Integer lstLogoutT) {
        this.lstLogoutT = lstLogoutT;
    }

    public Integer getLstPwdChT() {
        return lstPwdChT;
    }

    public void setLstPwdChT(Integer lstPwdChT) {
        this.lstPwdChT = lstPwdChT;
    }

    public String getLstPwdChB() {
        return lstPwdChB;
    }

    public void setLstPwdChB(String lstPwdChB) {
        this.lstPwdChB = lstPwdChB;
    }

    public String getRclFlag() {
        return rclFlag;
    }

    public void setRclFlag(String rclFlag) {
        this.rclFlag = rclFlag;
    }

    public String getMobileUser() {
        return mobileUser;
    }

    public void setMobileUser(String mobileUser) {
        this.mobileUser = mobileUser;
    }

    public String getMobileImei() {
        return mobileImei;
    }

    public void setMobileImei(String mobileImei) {
        this.mobileImei = mobileImei;
    }

    public String getPrsWkCntEb() {
        return prsWkCntEb;
    }

    public void setPrsWkCntEb(String prsWkCntEb) {
        this.prsWkCntEb = prsWkCntEb;
    }

    public Integer getSnapShotId() {
        return snapShotId;
    }

    public void setSnapShotId(Integer snapShotId) {
        this.snapShotId = snapShotId;
    }

    public String getStData() {
        return stData;
    }

    public void setStData(String stData) {
        this.stData = stData;
    }

    public String getSupportUsr() {
        return supportUsr;
    }

    public void setSupportUsr(String supportUsr) {
        this.supportUsr = supportUsr;
    }

    public Short getNoStPwdNum() {
        return noStPwdNum;
    }

    public void setNoStPwdNum(Short noStPwdNum) {
        this.noStPwdNum = noStPwdNum;
    }

    public String getDomainUser() {
        return domainUser;
    }

    public void setDomainUser(String domainUser) {
        this.domainUser = domainUser;
    }

    public String getCusAgree() {
        return cusAgree;
    }

    public void setCusAgree(String cusAgree) {
        this.cusAgree = cusAgree;
    }

    public String getEmailSig() {
        return emailSig;
    }

    public void setEmailSig(String emailSig) {
        this.emailSig = emailSig;
    }

    public Short getTplId() {
        return tplId;
    }

    public void setTplId(Short tplId) {
        this.tplId = tplId;
    }

    public String getDigCrtPath() {
        return digCrtPath;
    }

    public void setDigCrtPath(String digCrtPath) {
        this.digCrtPath = digCrtPath;
    }

    public String getShowNewTsk() {
        return showNewTsk;
    }

    public void setShowNewTsk(String showNewTsk) {
        this.showNewTsk = showNewTsk;
    }

    public String getIntgrtEb() {
        return intgrtEb;
    }

    public void setIntgrtEb(String intgrtEb) {
        this.intgrtEb = intgrtEb;
    }

    public String getAllBrnchF() {
        return allBrnchF;
    }

    public void setAllBrnchF(String allBrnchF) {
        this.allBrnchF = allBrnchF;
    }

    public String getEvtNotify() {
        return evtNotify;
    }

    public void setEvtNotify(String evtNotify) {
        this.evtNotify = evtNotify;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OusrEntity that = (OusrEntity) o;

        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (password1 != null ? !password1.equals(that.password1) : that.password1 != null) return false;
        if (password2 != null ? !password2.equals(that.password2) : that.password2 != null) return false;
        if (internalK != null ? !internalK.equals(that.internalK) : that.internalK != null) return false;
        if (userCode != null ? !userCode.equals(that.userCode) : that.userCode != null) return false;
        if (uName != null ? !uName.equals(that.uName) : that.uName != null) return false;
        if (groups != null ? !groups.equals(that.groups) : that.groups != null) return false;
        if (password4 != null ? !password4.equals(that.password4) : that.password4 != null) return false;
        if (allowences != null ? !allowences.equals(that.allowences) : that.allowences != null) return false;
        if (superuser != null ? !superuser.equals(that.superuser) : that.superuser != null) return false;
        if (discount != null ? !discount.equals(that.discount) : that.discount != null) return false;
        if (password3 != null ? !password3.equals(that.password3) : that.password3 != null) return false;
        if (info1File != null ? !info1File.equals(that.info1File) : that.info1File != null) return false;
        if (info1Field != null ? !info1Field.equals(that.info1Field) : that.info1Field != null) return false;
        if (info2File != null ? !info2File.equals(that.info2File) : that.info2File != null) return false;
        if (info2Field != null ? !info2Field.equals(that.info2Field) : that.info2Field != null) return false;
        if (info3File != null ? !info3File.equals(that.info3File) : that.info3File != null) return false;
        if (info3Field != null ? !info3Field.equals(that.info3Field) : that.info3Field != null) return false;
        if (info4File != null ? !info4File.equals(that.info4File) : that.info4File != null) return false;
        if (info4Field != null ? !info4Field.equals(that.info4Field) : that.info4Field != null) return false;
        if (dType != null ? !dType.equals(that.dType) : that.dType != null) return false;
        if (eMail != null ? !eMail.equals(that.eMail) : that.eMail != null) return false;
        if (portNum != null ? !portNum.equals(that.portNum) : that.portNum != null) return false;
        if (outOfOffic != null ? !outOfOffic.equals(that.outOfOffic) : that.outOfOffic != null) return false;
        if (sendEMail != null ? !sendEMail.equals(that.sendEMail) : that.sendEMail != null) return false;
        if (sendSms != null ? !sendSms.equals(that.sendSms) : that.sendSms != null) return false;
        if (dfltsGroup != null ? !dfltsGroup.equals(that.dfltsGroup) : that.dfltsGroup != null) return false;
        if (cashLimit != null ? !cashLimit.equals(that.cashLimit) : that.cashLimit != null) return false;
        if (maxCashSum != null ? !maxCashSum.equals(that.maxCashSum) : that.maxCashSum != null) return false;
        if (fax != null ? !fax.equals(that.fax) : that.fax != null) return false;
        if (sendFax != null ? !sendFax.equals(that.sendFax) : that.sendFax != null) return false;
        if (locked != null ? !locked.equals(that.locked) : that.locked != null) return false;
        if (department != null ? !department.equals(that.department) : that.department != null) return false;
        if (branch != null ? !branch.equals(that.branch) : that.branch != null) return false;
        if (!Arrays.equals(userPrefs, that.userPrefs)) return false;
        if (language != null ? !language.equals(that.language) : that.language != null) return false;
        if (charset != null ? !charset.equals(that.charset) : that.charset != null) return false;
        if (openCdt != null ? !openCdt.equals(that.openCdt) : that.openCdt != null) return false;
        if (cdtPrvDays != null ? !cdtPrvDays.equals(that.cdtPrvDays) : that.cdtPrvDays != null) return false;
        if (dsplyRates != null ? !dsplyRates.equals(that.dsplyRates) : that.dsplyRates != null) return false;
        if (auImpRates != null ? !auImpRates.equals(that.auImpRates) : that.auImpRates != null) return false;
        if (openDps != null ? !openDps.equals(that.openDps) : that.openDps != null) return false;
        if (rcrFlag != null ? !rcrFlag.equals(that.rcrFlag) : that.rcrFlag != null) return false;
        if (checkFiles != null ? !checkFiles.equals(that.checkFiles) : that.checkFiles != null) return false;
        if (openCredit != null ? !openCredit.equals(that.openCredit) : that.openCredit != null) return false;
        if (creditDay1 != null ? !creditDay1.equals(that.creditDay1) : that.creditDay1 != null) return false;
        if (creditDay2 != null ? !creditDay2.equals(that.creditDay2) : that.creditDay2 != null) return false;
        if (wallPaper != null ? !wallPaper.equals(that.wallPaper) : that.wallPaper != null) return false;
        if (wllPprDsp != null ? !wllPprDsp.equals(that.wllPprDsp) : that.wllPprDsp != null) return false;
        if (advImagePr != null ? !advImagePr.equals(that.advImagePr) : that.advImagePr != null) return false;
        if (contactLog != null ? !contactLog.equals(that.contactLog) : that.contactLog != null) return false;
        if (lastWarned != null ? !lastWarned.equals(that.lastWarned) : that.lastWarned != null) return false;
        if (alertPolFr != null ? !alertPolFr.equals(that.alertPolFr) : that.alertPolFr != null) return false;
        if (screenLock != null ? !screenLock.equals(that.screenLock) : that.screenLock != null) return false;
        if (showNewMsg != null ? !showNewMsg.equals(that.showNewMsg) : that.showNewMsg != null) return false;
        if (picture != null ? !picture.equals(that.picture) : that.picture != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (tel1 != null ? !tel1.equals(that.tel1) : that.tel1 != null) return false;
        if (tel2 != null ? !tel2.equals(that.tel2) : that.tel2 != null) return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) return false;
        if (birthday != null ? !birthday.equals(that.birthday) : that.birthday != null) return false;
        if (enbMenuFlt != null ? !enbMenuFlt.equals(that.enbMenuFlt) : that.enbMenuFlt != null) return false;
        if (objType != null ? !objType.equals(that.objType) : that.objType != null) return false;
        if (logInstanc != null ? !logInstanc.equals(that.logInstanc) : that.logInstanc != null) return false;
        if (userSign != null ? !userSign.equals(that.userSign) : that.userSign != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (userSign2 != null ? !userSign2.equals(that.userSign2) : that.userSign2 != null) return false;
        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;
        if (oneLogPwd != null ? !oneLogPwd.equals(that.oneLogPwd) : that.oneLogPwd != null) return false;
        if (lastLogin != null ? !lastLogin.equals(that.lastLogin) : that.lastLogin != null) return false;
        if (lastPwds != null ? !lastPwds.equals(that.lastPwds) : that.lastPwds != null) return false;
        if (lastPwds2 != null ? !lastPwds2.equals(that.lastPwds2) : that.lastPwds2 != null) return false;
        if (lastPwdSet != null ? !lastPwdSet.equals(that.lastPwdSet) : that.lastPwdSet != null) return false;
        if (failedLog != null ? !failedLog.equals(that.failedLog) : that.failedLog != null) return false;
        if (pwdNeverEx != null ? !pwdNeverEx.equals(that.pwdNeverEx) : that.pwdNeverEx != null) return false;
        if (salesDisc != null ? !salesDisc.equals(that.salesDisc) : that.salesDisc != null) return false;
        if (purchDisc != null ? !purchDisc.equals(that.purchDisc) : that.purchDisc != null) return false;
        if (lstLogoutD != null ? !lstLogoutD.equals(that.lstLogoutD) : that.lstLogoutD != null) return false;
        if (lstLoginT != null ? !lstLoginT.equals(that.lstLoginT) : that.lstLoginT != null) return false;
        if (lstLogoutT != null ? !lstLogoutT.equals(that.lstLogoutT) : that.lstLogoutT != null) return false;
        if (lstPwdChT != null ? !lstPwdChT.equals(that.lstPwdChT) : that.lstPwdChT != null) return false;
        if (lstPwdChB != null ? !lstPwdChB.equals(that.lstPwdChB) : that.lstPwdChB != null) return false;
        if (rclFlag != null ? !rclFlag.equals(that.rclFlag) : that.rclFlag != null) return false;
        if (mobileUser != null ? !mobileUser.equals(that.mobileUser) : that.mobileUser != null) return false;
        if (mobileImei != null ? !mobileImei.equals(that.mobileImei) : that.mobileImei != null) return false;
        if (prsWkCntEb != null ? !prsWkCntEb.equals(that.prsWkCntEb) : that.prsWkCntEb != null) return false;
        if (snapShotId != null ? !snapShotId.equals(that.snapShotId) : that.snapShotId != null) return false;
        if (stData != null ? !stData.equals(that.stData) : that.stData != null) return false;
        if (supportUsr != null ? !supportUsr.equals(that.supportUsr) : that.supportUsr != null) return false;
        if (noStPwdNum != null ? !noStPwdNum.equals(that.noStPwdNum) : that.noStPwdNum != null) return false;
        if (domainUser != null ? !domainUser.equals(that.domainUser) : that.domainUser != null) return false;
        if (cusAgree != null ? !cusAgree.equals(that.cusAgree) : that.cusAgree != null) return false;
        if (emailSig != null ? !emailSig.equals(that.emailSig) : that.emailSig != null) return false;
        if (tplId != null ? !tplId.equals(that.tplId) : that.tplId != null) return false;
        if (digCrtPath != null ? !digCrtPath.equals(that.digCrtPath) : that.digCrtPath != null) return false;
        if (showNewTsk != null ? !showNewTsk.equals(that.showNewTsk) : that.showNewTsk != null) return false;
        if (intgrtEb != null ? !intgrtEb.equals(that.intgrtEb) : that.intgrtEb != null) return false;
        if (allBrnchF != null ? !allBrnchF.equals(that.allBrnchF) : that.allBrnchF != null) return false;
        if (evtNotify != null ? !evtNotify.equals(that.evtNotify) : that.evtNotify != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userid != null ? userid.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (password1 != null ? password1.hashCode() : 0);
        result = 31 * result + (password2 != null ? password2.hashCode() : 0);
        result = 31 * result + (internalK != null ? internalK.hashCode() : 0);
        result = 31 * result + (userCode != null ? userCode.hashCode() : 0);
        result = 31 * result + (uName != null ? uName.hashCode() : 0);
        result = 31 * result + (groups != null ? groups.hashCode() : 0);
        result = 31 * result + (password4 != null ? password4.hashCode() : 0);
        result = 31 * result + (allowences != null ? allowences.hashCode() : 0);
        result = 31 * result + (superuser != null ? superuser.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        result = 31 * result + (password3 != null ? password3.hashCode() : 0);
        result = 31 * result + (info1File != null ? info1File.hashCode() : 0);
        result = 31 * result + (info1Field != null ? info1Field.hashCode() : 0);
        result = 31 * result + (info2File != null ? info2File.hashCode() : 0);
        result = 31 * result + (info2Field != null ? info2Field.hashCode() : 0);
        result = 31 * result + (info3File != null ? info3File.hashCode() : 0);
        result = 31 * result + (info3Field != null ? info3Field.hashCode() : 0);
        result = 31 * result + (info4File != null ? info4File.hashCode() : 0);
        result = 31 * result + (info4Field != null ? info4Field.hashCode() : 0);
        result = 31 * result + (dType != null ? dType.hashCode() : 0);
        result = 31 * result + (eMail != null ? eMail.hashCode() : 0);
        result = 31 * result + (portNum != null ? portNum.hashCode() : 0);
        result = 31 * result + (outOfOffic != null ? outOfOffic.hashCode() : 0);
        result = 31 * result + (sendEMail != null ? sendEMail.hashCode() : 0);
        result = 31 * result + (sendSms != null ? sendSms.hashCode() : 0);
        result = 31 * result + (dfltsGroup != null ? dfltsGroup.hashCode() : 0);
        result = 31 * result + (cashLimit != null ? cashLimit.hashCode() : 0);
        result = 31 * result + (maxCashSum != null ? maxCashSum.hashCode() : 0);
        result = 31 * result + (fax != null ? fax.hashCode() : 0);
        result = 31 * result + (sendFax != null ? sendFax.hashCode() : 0);
        result = 31 * result + (locked != null ? locked.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (branch != null ? branch.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(userPrefs);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (charset != null ? charset.hashCode() : 0);
        result = 31 * result + (openCdt != null ? openCdt.hashCode() : 0);
        result = 31 * result + (cdtPrvDays != null ? cdtPrvDays.hashCode() : 0);
        result = 31 * result + (dsplyRates != null ? dsplyRates.hashCode() : 0);
        result = 31 * result + (auImpRates != null ? auImpRates.hashCode() : 0);
        result = 31 * result + (openDps != null ? openDps.hashCode() : 0);
        result = 31 * result + (rcrFlag != null ? rcrFlag.hashCode() : 0);
        result = 31 * result + (checkFiles != null ? checkFiles.hashCode() : 0);
        result = 31 * result + (openCredit != null ? openCredit.hashCode() : 0);
        result = 31 * result + (creditDay1 != null ? creditDay1.hashCode() : 0);
        result = 31 * result + (creditDay2 != null ? creditDay2.hashCode() : 0);
        result = 31 * result + (wallPaper != null ? wallPaper.hashCode() : 0);
        result = 31 * result + (wllPprDsp != null ? wllPprDsp.hashCode() : 0);
        result = 31 * result + (advImagePr != null ? advImagePr.hashCode() : 0);
        result = 31 * result + (contactLog != null ? contactLog.hashCode() : 0);
        result = 31 * result + (lastWarned != null ? lastWarned.hashCode() : 0);
        result = 31 * result + (alertPolFr != null ? alertPolFr.hashCode() : 0);
        result = 31 * result + (screenLock != null ? screenLock.hashCode() : 0);
        result = 31 * result + (showNewMsg != null ? showNewMsg.hashCode() : 0);
        result = 31 * result + (picture != null ? picture.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (tel1 != null ? tel1.hashCode() : 0);
        result = 31 * result + (tel2 != null ? tel2.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (enbMenuFlt != null ? enbMenuFlt.hashCode() : 0);
        result = 31 * result + (objType != null ? objType.hashCode() : 0);
        result = 31 * result + (logInstanc != null ? logInstanc.hashCode() : 0);
        result = 31 * result + (userSign != null ? userSign.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (userSign2 != null ? userSign2.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        result = 31 * result + (oneLogPwd != null ? oneLogPwd.hashCode() : 0);
        result = 31 * result + (lastLogin != null ? lastLogin.hashCode() : 0);
        result = 31 * result + (lastPwds != null ? lastPwds.hashCode() : 0);
        result = 31 * result + (lastPwds2 != null ? lastPwds2.hashCode() : 0);
        result = 31 * result + (lastPwdSet != null ? lastPwdSet.hashCode() : 0);
        result = 31 * result + (failedLog != null ? failedLog.hashCode() : 0);
        result = 31 * result + (pwdNeverEx != null ? pwdNeverEx.hashCode() : 0);
        result = 31 * result + (salesDisc != null ? salesDisc.hashCode() : 0);
        result = 31 * result + (purchDisc != null ? purchDisc.hashCode() : 0);
        result = 31 * result + (lstLogoutD != null ? lstLogoutD.hashCode() : 0);
        result = 31 * result + (lstLoginT != null ? lstLoginT.hashCode() : 0);
        result = 31 * result + (lstLogoutT != null ? lstLogoutT.hashCode() : 0);
        result = 31 * result + (lstPwdChT != null ? lstPwdChT.hashCode() : 0);
        result = 31 * result + (lstPwdChB != null ? lstPwdChB.hashCode() : 0);
        result = 31 * result + (rclFlag != null ? rclFlag.hashCode() : 0);
        result = 31 * result + (mobileUser != null ? mobileUser.hashCode() : 0);
        result = 31 * result + (mobileImei != null ? mobileImei.hashCode() : 0);
        result = 31 * result + (prsWkCntEb != null ? prsWkCntEb.hashCode() : 0);
        result = 31 * result + (snapShotId != null ? snapShotId.hashCode() : 0);
        result = 31 * result + (stData != null ? stData.hashCode() : 0);
        result = 31 * result + (supportUsr != null ? supportUsr.hashCode() : 0);
        result = 31 * result + (noStPwdNum != null ? noStPwdNum.hashCode() : 0);
        result = 31 * result + (domainUser != null ? domainUser.hashCode() : 0);
        result = 31 * result + (cusAgree != null ? cusAgree.hashCode() : 0);
        result = 31 * result + (emailSig != null ? emailSig.hashCode() : 0);
        result = 31 * result + (tplId != null ? tplId.hashCode() : 0);
        result = 31 * result + (digCrtPath != null ? digCrtPath.hashCode() : 0);
        result = 31 * result + (showNewTsk != null ? showNewTsk.hashCode() : 0);
        result = 31 * result + (intgrtEb != null ? intgrtEb.hashCode() : 0);
        result = 31 * result + (allBrnchF != null ? allBrnchF.hashCode() : 0);
        result = 31 * result + (evtNotify != null ? evtNotify.hashCode() : 0);
        return result;
    }
}
