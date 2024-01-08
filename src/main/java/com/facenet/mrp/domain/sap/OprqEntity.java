package com.facenet.mrp.domain.sap;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "OPRQ")
public class OprqEntity {
    private int docEntry;
    private int docNum;
    private String docType;
    private String canceled;
    private String handwrtten;
    private String printed;
    private String docStatus;
    private String invntSttus;
    private String transfered;
    private String objType;
    private Date docDate;
    private Date docDueDate;
    private String cardCode;
    private String cardName;
    private String address;
    private String numAtCard;
    private Long vatPercent;
    private Long vatSum;
    private Long vatSumFc;
    private Long discPrcnt;
    private Long discSum;
    private Long discSumFc;
    private String docCur;
    private Long docRate;
    private Long docTotal;
    private Long docTotalFc;
    private Long paidToDate;
    private Long paidFc;
    private Long grosProfit;
    private Long grosProfFc;
    private String ref1;
    private String ref2;
    private String comments;
    private String jrnlMemo;
    private Integer transId;
    private Integer receiptNum;
    private Short groupNum;
    private Short docTime;
    private Integer slpCode;
    private Short trnspCode;
    private String partSupply;
    private String confirmed;
    private Short grossBase;
    private Integer importEnt;
    private String createTran;
    private String summryType;
    private String updInvnt;
    private String updCardBal;
    private short instance;
    private Integer flags;
    private String invntDirec;
    private Integer cntctCode;
    private String showScn;
    private String fatherCard;
    private Long sysRate;
    private String curSource;
    private Long vatSumSy;
    private Long discSumSy;
    private Long docTotalSy;
    private Long paidSys;
    private String fatherType;
    private Long grosProfSy;
    private LocalDate updateDate;
    private String isIct;
    private Date createDate;
    private Long volume;
    private Short volUnit;
    private Long weight;
    private Short weightUnit;
    private Short series;
    private Date taxDate;
    private String filler;
    private String dataSource;
    private String stampNum;
    private String isCrin;
    private Integer finncPriod;
    private Short userSign;
    private String selfInv;
    private Long vatPaid;
    private Long vatPaidFc;
    private Long vatPaidSys;
    private Short userSign2;
    private String wddStatus;
    private Integer draftKey;
    private Long totalExpns;
    private Long totalExpFc;
    private Long totalExpSc;
    private Integer dunnLevel;
    private String address2;
    private Integer logInstanc;
    private String exported;
    private Integer stationId;
    private String indicator;
    private String netProc;
    private Long aqcsTax;
    private Long aqcsTaxFc;
    private Long aqcsTaxSc;
    private Long cashDiscPr;
    private Long cashDiscnt;
    private Long cashDiscFc;
    private Long cashDiscSc;
    private String shipToCode;
    private String licTradNum;
    private String paymentRef;
    private Long wtSum;
    private Long wtSumFc;
    private Long wtSumSc;
    private Long roundDif;
    private Long roundDifFc;
    private Long roundDifSy;
    private String checkDigit;
    private Integer form1099;
    private String box1099;
    private String submitted;
    private String poPrss;
    private String rounding;
    private String revisionPo;
    private short segment;
    private Date reqDate;
    private Date cancelDate;
    private String pickStatus;
    private String pick;
    private String blockDunn;
    private String peyMethod;
    private String payBlock;
    private Integer payBlckRef;
    private String maxDscn;
    private String reserve;
    private Long max1099;
    private String cntrlBnk;
    private String pickRmrk;
    private String isrCodLine;
    private Long expAppl;
    private Long expApplFc;
    private Long expApplSc;
    private String project;
    private String deferrTax;
    private String letterNum;
    private Date fromDate;
    private Date toDate;
    private Long wtApplied;
    private Long wtAppliedF;
    private String boeReserev;
    private String agentCode;
    private Long wtAppliedS;
    private Long equVatSum;
    private Long equVatSumF;
    private Long equVatSumS;
    private Short installmnt;
    private String vatFirst;
    private Long nnSbAmnt;
    private Long nnSbAmntSc;
    private Long nbSbAmntFc;
    private Long exepAmnt;
    private Long exepAmntSc;
    private Long exepAmntFc;
    private Date vatDate;
    private String corrExt;
    private Integer corrInv;
    private Integer nCorrInv;
    private String ceecFlag;
    private Long baseAmnt;
    private Long baseAmntSc;
    private Long baseAmntFc;
    private String ctlAccount;
    private Integer bplId;
    private String bplName;
    private String vatRegNum;
    private String txInvRptNo;
    private Date txInvRptDt;
    private String kvvatCode;
    private String wtDetails;
    private Integer sumAbsId;
    private Date sumRptDate;
    private String pIndicator;
    private String manualNum;
    private String useShpdGd;
    private Long baseVtAt;
    private Long baseVtAtSc;
    private Long baseVtAtFc;
    private Long nnSbVAt;
    private Long nnSbVAtSc;
    private Long nbSbVAtFc;
    private Long exptVAt;
    private Long exptVAtSc;
    private Long exptVAtFc;
    private Long lyPmtAt;
    private Long lyPmtAtSc;
    private Long lyPmtAtFc;
    private Long expAnSum;
    private Long expAnSys;
    private Long expAnFrgn;
    private String docSubType;
    private String dpmStatus;
    private Long dpmAmnt;
    private Long dpmAmntSc;
    private Long dpmAmntFc;
    private String dpmDrawn;
    private Long dpmPrcnt;
    private Long paidSum;
    private Long paidSumFc;
    private Long paidSumSc;
    private String folioPref;
    private Integer folioNum;
    private Long dpmAppl;
    private Long dpmApplFc;
    private Long dpmApplSc;
    private Integer lPgFolioN;
    private String header;
    private String footer;
    private String posted;
    private Integer ownerCode;
    private String bpChCode;
    private Integer bpChCntc;
    private String payToCode;
    private String isPaytoBnk;
    private String bnkCntry;
    private String bankCode;
    private String bnkAccount;
    private String bnkBranch;
    private String isIns;
    private String trackNo;
    private String versionNum;
    private Integer langCode;
    private String bpNameOw;
    private String billToOw;
    private String shipToOw;
    private String retInvoice;
    private Date clsDate;
    private Integer mInvNum;
    private Date mInvDate;
    private Short seqCode;
    private Integer serial;
    private String seriesStr;
    private String subStr;
    private String model;
    private Long taxOnExp;
    private Long taxOnExpFc;
    private Long taxOnExpSc;
    private Long taxOnExAp;
    private Long taxOnExApF;
    private Long taxOnExApS;
    private String lastPmnTyp;
    private Integer lndCstNum;
    private String useCorrVat;
    private String blkCredMmo;
    private String openForLaC;
    private String excised;
    private Date excRefDate;
    private String excRmvTime;
    private Long srvGpPrcnt;
    private Integer depositNum;
    private String certNum;
    private String dutyStatus;
    private String autoCrtFlw;
    private Date flwRefDate;
    private String flwRefNum;
    private Integer vatJeNum;
    private Long dpmVat;
    private Long dpmVatFc;
    private Long dpmVatSc;
    private Long dpmAppVat;
    private Long dpmAppVatF;
    private Long dpmAppVatS;
    private String insurOp347;
    private String ignRelDoc;
    private String buildDesc;
    private String residenNum;
    private Integer checker;
    private Integer payee;
    private Integer copyNumber;
    private String ssiExmpt;
    private Short pqtGrpSer;
    private Integer pqtGrpNum;
    private String pqtGrpHw;
    private String reopOriDoc;
    private String reopManCls;
    private String docManClsd;
    private Short closingOpt;
    private Date specDate;
    private String ordered;
    private String ntsApprov;
    private Short ntsWebSite;
    private String ntSeTaxNo;
    private String ntsApprNo;
    private String payDuMonth;
    private Short extraMonth;
    private Short extraDays;
    private Short cdcOffset;
    private String signMsg;
    private String signDigest;
    private String certifNum;
    private Integer keyVersion;
    private String eDocGenTyp;
    private Short eSeries;
    private String eDocNum;
    private Integer eDocExpFrm;
    private String onlineQuo;
    private String posEqNum;
    private String posManufSn;
    private Integer posCashN;
    private String eDocStatus;
    private String eDocCntnt;
    private String eDocProces;
    private String eDocErrCod;
    private String eDocErrMsg;
    private String eDocCancel;
    private String eDocTest;
    private String eDocPrefix;
    private Integer cup;
    private Integer cig;
    private String dpmAsDscnt;
    private String attachment;
    private Integer atcEntry;
    private String supplCode;
    private String gtsRlvnt;
    private Long baseDisc;
    private Long baseDiscSc;
    private Long baseDiscFc;
    private Long baseDiscPr;
    private Integer createTs;
    private Integer updateTs;
    private String srvTaxRule;
    private Integer annInvDecR;
    private String supplier;
    private Integer releaser;
    private Integer receiver;
    private String toWhsCode;
    private Date assetDate;
    private String requester;
    private String reqName;
    private Short branch;
    private Short department;
    private String email;
    private String notify;
    private Integer reqType;
    private String originType;
    private String isReuseNum;
    private String isReuseNfn;
    private String docDlvry;
    private Long paidDpm;
    private Long paidDpmF;
    private Long paidDpmS;
    private Integer envTypeNFe;
    private Integer agrNo;
    private String isAlt;
    private Integer altBaseTyp;
    private Integer altBaseEnt;
    private String authCode;
    private Date stDlvDate;
    private Integer stDlvTime;
    private Date endDlvDate;
    private Integer endDlvTime;
    private String vclPlate;
    private String elCoStatus;
    private String atDocType;
    private String elCoMsg;
    private String printSepa;
    private Long freeChrg;
    private Long freeChrgFc;
    private Long freeChrgSc;
    private Long nfeValue;
    private String fiscDocNum;
    private Integer relatedTyp;
    private Integer relatedEnt;
    private Integer ccdEntry;
    private Integer nfePrntFo;
    private Integer zrdAbs;
    private Integer posRcptNo;
    private Long foCTax;
    private Long foCTaxFc;
    private Long foCTaxSc;
    private Integer tpCusPres;
    private Date excDocDate;
    private Long foCFrght;
    private Long foCFrghtFc;
    private Long foCFrghtSc;
    private Short interimTyp;
    private String ptiCode;
    private String letter;
    private Integer folNumFrom;
    private Integer folNumTo;
    private Integer folSeries;
    private Long splitTax;
    private Long splitTaxFc;
    private Long splitTaxSc;
    private String toBinCode;
    private String permitNo;
    private String myFtype;
    private String poDropPrss;
    private String docTaxId;
    private Date dateReport;
    private String repSection;
    private String exclTaxRep;
    private Integer posCashReg;
    private String dmpTransId;
    private String eCommerBp;
    private String eComerGstn;
    private String revision;
    private String revRefNo;
    private Date revRefDate;
    private String revCreRefN;
    private Date revCreRefD;
    private String taxInvNo;
    private Date frmBpDate;
    private String gstTranTyp;
    private Integer baseType;
    private Integer baseEntry;
    private String uCodeSerial;
    private String uCodeInv;
    private String uInvSerial;
    private String uInvCode;
    private String uDeclareStat;
    private String uCoName;
    private String uCoAdd;
    private String uCoTaxNo;
    private String uDocnum;
    private String uDescriptionVn;
    private String uPurNvGiao;
    private Short uDeliveryTime;
    private String uInvRemark;
    private String uHrcode;
    private String uHrname;
    private String uHt;
    private String uVc;
    private Integer uGrpo;
    private String uPayment;
    private Integer uProduction;
    private String uBatch;
    private String uDChuyen;
    private String uInvCode2;
    private String uManufacturer;
    private String uApproval;
    private Integer uGoodReceipt;
    private Integer uCheckQc;
    private String uPurNvNhan;
    private String uPrinted;
    private String uCknb;
    private String uCategory;
    private String uTransNum;
    private String uDeclarePd;
    private String uSoPx;
    private String uContractDate;
    private String uLine;
    private String uWhscode;
    private Integer uSendToHo;
    private Long uDpm;
    private String uDocOrg;
    private Date uDateDeb;

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
    public int getDocNum() {
        return docNum;
    }

    public void setDocNum(int docNum) {
        this.docNum = docNum;
    }

    @Basic
    @Column(name = "DocType")
    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    @Basic
    @Column(name = "CANCELED")
    public String getCanceled() {
        return canceled;
    }

    public void setCanceled(String canceled) {
        this.canceled = canceled;
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
    @Column(name = "Printed")
    public String getPrinted() {
        return printed;
    }

    public void setPrinted(String printed) {
        this.printed = printed;
    }

    @Basic
    @Column(name = "DocStatus")
    public String getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }

    @Basic
    @Column(name = "InvntSttus")
    public String getInvntSttus() {
        return invntSttus;
    }

    public void setInvntSttus(String invntSttus) {
        this.invntSttus = invntSttus;
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
    @Column(name = "ObjType")
    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    @Basic
    @Column(name = "DocDate")
    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    @Basic
    @Column(name = "DocDueDate")
    public Date getDocDueDate() {
        return docDueDate;
    }

    public void setDocDueDate(Date docDueDate) {
        this.docDueDate = docDueDate;
    }

    @Basic
    @Column(name = "CardCode")
    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    @Basic
    @Column(name = "CardName")
    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    @Basic
    @Column(name = "Address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "NumAtCard")
    public String getNumAtCard() {
        return numAtCard;
    }

    public void setNumAtCard(String numAtCard) {
        this.numAtCard = numAtCard;
    }

    @Basic
    @Column(name = "VatPercent")
    public Long getVatPercent() {
        return vatPercent;
    }

    public void setVatPercent(Long vatPercent) {
        this.vatPercent = vatPercent;
    }

    @Basic
    @Column(name = "VatSum")
    public Long getVatSum() {
        return vatSum;
    }

    public void setVatSum(Long vatSum) {
        this.vatSum = vatSum;
    }

    @Basic
    @Column(name = "VatSumFC")
    public Long getVatSumFc() {
        return vatSumFc;
    }

    public void setVatSumFc(Long vatSumFc) {
        this.vatSumFc = vatSumFc;
    }

    @Basic
    @Column(name = "DiscPrcnt")
    public Long getDiscPrcnt() {
        return discPrcnt;
    }

    public void setDiscPrcnt(Long discPrcnt) {
        this.discPrcnt = discPrcnt;
    }

    @Basic
    @Column(name = "DiscSum")
    public Long getDiscSum() {
        return discSum;
    }

    public void setDiscSum(Long discSum) {
        this.discSum = discSum;
    }

    @Basic
    @Column(name = "DiscSumFC")
    public Long getDiscSumFc() {
        return discSumFc;
    }

    public void setDiscSumFc(Long discSumFc) {
        this.discSumFc = discSumFc;
    }

    @Basic
    @Column(name = "DocCur")
    public String getDocCur() {
        return docCur;
    }

    public void setDocCur(String docCur) {
        this.docCur = docCur;
    }

    @Basic
    @Column(name = "DocRate")
    public Long getDocRate() {
        return docRate;
    }

    public void setDocRate(Long docRate) {
        this.docRate = docRate;
    }

    @Basic
    @Column(name = "DocTotal")
    public Long getDocTotal() {
        return docTotal;
    }

    public void setDocTotal(Long docTotal) {
        this.docTotal = docTotal;
    }

    @Basic
    @Column(name = "DocTotalFC")
    public Long getDocTotalFc() {
        return docTotalFc;
    }

    public void setDocTotalFc(Long docTotalFc) {
        this.docTotalFc = docTotalFc;
    }

    @Basic
    @Column(name = "PaidToDate")
    public Long getPaidToDate() {
        return paidToDate;
    }

    public void setPaidToDate(Long paidToDate) {
        this.paidToDate = paidToDate;
    }

    @Basic
    @Column(name = "PaidFC")
    public Long getPaidFc() {
        return paidFc;
    }

    public void setPaidFc(Long paidFc) {
        this.paidFc = paidFc;
    }

    @Basic
    @Column(name = "GrosProfit")
    public Long getGrosProfit() {
        return grosProfit;
    }

    public void setGrosProfit(Long grosProfit) {
        this.grosProfit = grosProfit;
    }

    @Basic
    @Column(name = "GrosProfFC")
    public Long getGrosProfFc() {
        return grosProfFc;
    }

    public void setGrosProfFc(Long grosProfFc) {
        this.grosProfFc = grosProfFc;
    }

    @Basic
    @Column(name = "Ref1")
    public String getRef1() {
        return ref1;
    }

    public void setRef1(String ref1) {
        this.ref1 = ref1;
    }

    @Basic
    @Column(name = "Ref2")
    public String getRef2() {
        return ref2;
    }

    public void setRef2(String ref2) {
        this.ref2 = ref2;
    }

    @Basic
    @Column(name = "Comments")
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Basic
    @Column(name = "JrnlMemo")
    public String getJrnlMemo() {
        return jrnlMemo;
    }

    public void setJrnlMemo(String jrnlMemo) {
        this.jrnlMemo = jrnlMemo;
    }

    @Basic
    @Column(name = "TransId")
    public Integer getTransId() {
        return transId;
    }

    public void setTransId(Integer transId) {
        this.transId = transId;
    }

    @Basic
    @Column(name = "ReceiptNum")
    public Integer getReceiptNum() {
        return receiptNum;
    }

    public void setReceiptNum(Integer receiptNum) {
        this.receiptNum = receiptNum;
    }

    @Basic
    @Column(name = "GroupNum")
    public Short getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(Short groupNum) {
        this.groupNum = groupNum;
    }

    @Basic
    @Column(name = "DocTime")
    public Short getDocTime() {
        return docTime;
    }

    public void setDocTime(Short docTime) {
        this.docTime = docTime;
    }

    @Basic
    @Column(name = "SlpCode")
    public Integer getSlpCode() {
        return slpCode;
    }

    public void setSlpCode(Integer slpCode) {
        this.slpCode = slpCode;
    }

    @Basic
    @Column(name = "TrnspCode")
    public Short getTrnspCode() {
        return trnspCode;
    }

    public void setTrnspCode(Short trnspCode) {
        this.trnspCode = trnspCode;
    }

    @Basic
    @Column(name = "PartSupply")
    public String getPartSupply() {
        return partSupply;
    }

    public void setPartSupply(String partSupply) {
        this.partSupply = partSupply;
    }

    @Basic
    @Column(name = "Confirmed")
    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    @Basic
    @Column(name = "GrossBase")
    public Short getGrossBase() {
        return grossBase;
    }

    public void setGrossBase(Short grossBase) {
        this.grossBase = grossBase;
    }

    @Basic
    @Column(name = "ImportEnt")
    public Integer getImportEnt() {
        return importEnt;
    }

    public void setImportEnt(Integer importEnt) {
        this.importEnt = importEnt;
    }

    @Basic
    @Column(name = "CreateTran")
    public String getCreateTran() {
        return createTran;
    }

    public void setCreateTran(String createTran) {
        this.createTran = createTran;
    }

    @Basic
    @Column(name = "SummryType")
    public String getSummryType() {
        return summryType;
    }

    public void setSummryType(String summryType) {
        this.summryType = summryType;
    }

    @Basic
    @Column(name = "UpdInvnt")
    public String getUpdInvnt() {
        return updInvnt;
    }

    public void setUpdInvnt(String updInvnt) {
        this.updInvnt = updInvnt;
    }

    @Basic
    @Column(name = "UpdCardBal")
    public String getUpdCardBal() {
        return updCardBal;
    }

    public void setUpdCardBal(String updCardBal) {
        this.updCardBal = updCardBal;
    }

    @Basic
    @Column(name = "Instance")
    public short getInstance() {
        return instance;
    }

    public void setInstance(short instance) {
        this.instance = instance;
    }

    @Basic
    @Column(name = "Flags")
    public Integer getFlags() {
        return flags;
    }

    public void setFlags(Integer flags) {
        this.flags = flags;
    }

    @Basic
    @Column(name = "InvntDirec")
    public String getInvntDirec() {
        return invntDirec;
    }

    public void setInvntDirec(String invntDirec) {
        this.invntDirec = invntDirec;
    }

    @Basic
    @Column(name = "CntctCode")
    public Integer getCntctCode() {
        return cntctCode;
    }

    public void setCntctCode(Integer cntctCode) {
        this.cntctCode = cntctCode;
    }

    @Basic
    @Column(name = "ShowSCN")
    public String getShowScn() {
        return showScn;
    }

    public void setShowScn(String showScn) {
        this.showScn = showScn;
    }

    @Basic
    @Column(name = "FatherCard")
    public String getFatherCard() {
        return fatherCard;
    }

    public void setFatherCard(String fatherCard) {
        this.fatherCard = fatherCard;
    }

    @Basic
    @Column(name = "SysRate")
    public Long getSysRate() {
        return sysRate;
    }

    public void setSysRate(Long sysRate) {
        this.sysRate = sysRate;
    }

    @Basic
    @Column(name = "CurSource")
    public String getCurSource() {
        return curSource;
    }

    public void setCurSource(String curSource) {
        this.curSource = curSource;
    }

    @Basic
    @Column(name = "VatSumSy")
    public Long getVatSumSy() {
        return vatSumSy;
    }

    public void setVatSumSy(Long vatSumSy) {
        this.vatSumSy = vatSumSy;
    }

    @Basic
    @Column(name = "DiscSumSy")
    public Long getDiscSumSy() {
        return discSumSy;
    }

    public void setDiscSumSy(Long discSumSy) {
        this.discSumSy = discSumSy;
    }

    @Basic
    @Column(name = "DocTotalSy")
    public Long getDocTotalSy() {
        return docTotalSy;
    }

    public void setDocTotalSy(Long docTotalSy) {
        this.docTotalSy = docTotalSy;
    }

    @Basic
    @Column(name = "PaidSys")
    public Long getPaidSys() {
        return paidSys;
    }

    public void setPaidSys(Long paidSys) {
        this.paidSys = paidSys;
    }

    @Basic
    @Column(name = "FatherType")
    public String getFatherType() {
        return fatherType;
    }

    public void setFatherType(String fatherType) {
        this.fatherType = fatherType;
    }

    @Basic
    @Column(name = "GrosProfSy")
    public Long getGrosProfSy() {
        return grosProfSy;
    }

    public void setGrosProfSy(Long grosProfSy) {
        this.grosProfSy = grosProfSy;
    }

    @Basic
    @Column(name = "UpdateDate")
    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    @Basic
    @Column(name = "IsICT")
    public String getIsIct() {
        return isIct;
    }

    public void setIsIct(String isIct) {
        this.isIct = isIct;
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
    @Column(name = "Volume")
    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    @Basic
    @Column(name = "VolUnit")
    public Short getVolUnit() {
        return volUnit;
    }

    public void setVolUnit(Short volUnit) {
        this.volUnit = volUnit;
    }

    @Basic
    @Column(name = "Weight")
    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "WeightUnit")
    public Short getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(Short weightUnit) {
        this.weightUnit = weightUnit;
    }

    @Basic
    @Column(name = "Series")
    public Short getSeries() {
        return series;
    }

    public void setSeries(Short series) {
        this.series = series;
    }

    @Basic
    @Column(name = "TaxDate")
    public Date getTaxDate() {
        return taxDate;
    }

    public void setTaxDate(Date taxDate) {
        this.taxDate = taxDate;
    }

    @Basic
    @Column(name = "Filler")
    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
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
    @Column(name = "StampNum")
    public String getStampNum() {
        return stampNum;
    }

    public void setStampNum(String stampNum) {
        this.stampNum = stampNum;
    }

    @Basic
    @Column(name = "isCrin")
    public String getIsCrin() {
        return isCrin;
    }

    public void setIsCrin(String isCrin) {
        this.isCrin = isCrin;
    }

    @Basic
    @Column(name = "FinncPriod")
    public Integer getFinncPriod() {
        return finncPriod;
    }

    public void setFinncPriod(Integer finncPriod) {
        this.finncPriod = finncPriod;
    }

    @Basic
    @Column(name = "UserSign")
    public Short getUserSign() {
        return userSign;
    }

    public void setUserSign(Short userSign) {
        this.userSign = userSign;
    }

    @Basic
    @Column(name = "selfInv")
    public String getSelfInv() {
        return selfInv;
    }

    public void setSelfInv(String selfInv) {
        this.selfInv = selfInv;
    }

    @Basic
    @Column(name = "VatPaid")
    public Long getVatPaid() {
        return vatPaid;
    }

    public void setVatPaid(Long vatPaid) {
        this.vatPaid = vatPaid;
    }

    @Basic
    @Column(name = "VatPaidFC")
    public Long getVatPaidFc() {
        return vatPaidFc;
    }

    public void setVatPaidFc(Long vatPaidFc) {
        this.vatPaidFc = vatPaidFc;
    }

    @Basic
    @Column(name = "VatPaidSys")
    public Long getVatPaidSys() {
        return vatPaidSys;
    }

    public void setVatPaidSys(Long vatPaidSys) {
        this.vatPaidSys = vatPaidSys;
    }

    @Basic
    @Column(name = "UserSign2")
    public Short getUserSign2() {
        return userSign2;
    }

    public void setUserSign2(Short userSign2) {
        this.userSign2 = userSign2;
    }

    @Basic
    @Column(name = "WddStatus")
    public String getWddStatus() {
        return wddStatus;
    }

    public void setWddStatus(String wddStatus) {
        this.wddStatus = wddStatus;
    }

    @Basic
    @Column(name = "draftKey")
    public Integer getDraftKey() {
        return draftKey;
    }

    public void setDraftKey(Integer draftKey) {
        this.draftKey = draftKey;
    }

    @Basic
    @Column(name = "TotalExpns")
    public Long getTotalExpns() {
        return totalExpns;
    }

    public void setTotalExpns(Long totalExpns) {
        this.totalExpns = totalExpns;
    }

    @Basic
    @Column(name = "TotalExpFC")
    public Long getTotalExpFc() {
        return totalExpFc;
    }

    public void setTotalExpFc(Long totalExpFc) {
        this.totalExpFc = totalExpFc;
    }

    @Basic
    @Column(name = "TotalExpSC")
    public Long getTotalExpSc() {
        return totalExpSc;
    }

    public void setTotalExpSc(Long totalExpSc) {
        this.totalExpSc = totalExpSc;
    }

    @Basic
    @Column(name = "DunnLevel")
    public Integer getDunnLevel() {
        return dunnLevel;
    }

    public void setDunnLevel(Integer dunnLevel) {
        this.dunnLevel = dunnLevel;
    }

    @Basic
    @Column(name = "Address2")
    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @Basic
    @Column(name = "LogInstanc")
    public Integer getLogInstanc() {
        return logInstanc;
    }

    public void setLogInstanc(Integer logInstanc) {
        this.logInstanc = logInstanc;
    }

    @Basic
    @Column(name = "Exported")
    public String getExported() {
        return exported;
    }

    public void setExported(String exported) {
        this.exported = exported;
    }

    @Basic
    @Column(name = "StationID")
    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    @Basic
    @Column(name = "Indicator")
    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    @Basic
    @Column(name = "NetProc")
    public String getNetProc() {
        return netProc;
    }

    public void setNetProc(String netProc) {
        this.netProc = netProc;
    }

    @Basic
    @Column(name = "AqcsTax")
    public Long getAqcsTax() {
        return aqcsTax;
    }

    public void setAqcsTax(Long aqcsTax) {
        this.aqcsTax = aqcsTax;
    }

    @Basic
    @Column(name = "AqcsTaxFC")
    public Long getAqcsTaxFc() {
        return aqcsTaxFc;
    }

    public void setAqcsTaxFc(Long aqcsTaxFc) {
        this.aqcsTaxFc = aqcsTaxFc;
    }

    @Basic
    @Column(name = "AqcsTaxSC")
    public Long getAqcsTaxSc() {
        return aqcsTaxSc;
    }

    public void setAqcsTaxSc(Long aqcsTaxSc) {
        this.aqcsTaxSc = aqcsTaxSc;
    }

    @Basic
    @Column(name = "CashDiscPr")
    public Long getCashDiscPr() {
        return cashDiscPr;
    }

    public void setCashDiscPr(Long cashDiscPr) {
        this.cashDiscPr = cashDiscPr;
    }

    @Basic
    @Column(name = "CashDiscnt")
    public Long getCashDiscnt() {
        return cashDiscnt;
    }

    public void setCashDiscnt(Long cashDiscnt) {
        this.cashDiscnt = cashDiscnt;
    }

    @Basic
    @Column(name = "CashDiscFC")
    public Long getCashDiscFc() {
        return cashDiscFc;
    }

    public void setCashDiscFc(Long cashDiscFc) {
        this.cashDiscFc = cashDiscFc;
    }

    @Basic
    @Column(name = "CashDiscSC")
    public Long getCashDiscSc() {
        return cashDiscSc;
    }

    public void setCashDiscSc(Long cashDiscSc) {
        this.cashDiscSc = cashDiscSc;
    }

    @Basic
    @Column(name = "ShipToCode")
    public String getShipToCode() {
        return shipToCode;
    }

    public void setShipToCode(String shipToCode) {
        this.shipToCode = shipToCode;
    }

    @Basic
    @Column(name = "LicTradNum")
    public String getLicTradNum() {
        return licTradNum;
    }

    public void setLicTradNum(String licTradNum) {
        this.licTradNum = licTradNum;
    }

    @Basic
    @Column(name = "PaymentRef")
    public String getPaymentRef() {
        return paymentRef;
    }

    public void setPaymentRef(String paymentRef) {
        this.paymentRef = paymentRef;
    }

    @Basic
    @Column(name = "WTSum")
    public Long getWtSum() {
        return wtSum;
    }

    public void setWtSum(Long wtSum) {
        this.wtSum = wtSum;
    }

    @Basic
    @Column(name = "WTSumFC")
    public Long getWtSumFc() {
        return wtSumFc;
    }

    public void setWtSumFc(Long wtSumFc) {
        this.wtSumFc = wtSumFc;
    }

    @Basic
    @Column(name = "WTSumSC")
    public Long getWtSumSc() {
        return wtSumSc;
    }

    public void setWtSumSc(Long wtSumSc) {
        this.wtSumSc = wtSumSc;
    }

    @Basic
    @Column(name = "RoundDif")
    public Long getRoundDif() {
        return roundDif;
    }

    public void setRoundDif(Long roundDif) {
        this.roundDif = roundDif;
    }

    @Basic
    @Column(name = "RoundDifFC")
    public Long getRoundDifFc() {
        return roundDifFc;
    }

    public void setRoundDifFc(Long roundDifFc) {
        this.roundDifFc = roundDifFc;
    }

    @Basic
    @Column(name = "RoundDifSy")
    public Long getRoundDifSy() {
        return roundDifSy;
    }

    public void setRoundDifSy(Long roundDifSy) {
        this.roundDifSy = roundDifSy;
    }

    @Basic
    @Column(name = "CheckDigit")
    public String getCheckDigit() {
        return checkDigit;
    }

    public void setCheckDigit(String checkDigit) {
        this.checkDigit = checkDigit;
    }

    @Basic
    @Column(name = "Form1099")
    public Integer getForm1099() {
        return form1099;
    }

    public void setForm1099(Integer form1099) {
        this.form1099 = form1099;
    }

    @Basic
    @Column(name = "Box1099")
    public String getBox1099() {
        return box1099;
    }

    public void setBox1099(String box1099) {
        this.box1099 = box1099;
    }

    @Basic
    @Column(name = "submitted")
    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    @Basic
    @Column(name = "PoPrss")
    public String getPoPrss() {
        return poPrss;
    }

    public void setPoPrss(String poPrss) {
        this.poPrss = poPrss;
    }

    @Basic
    @Column(name = "Rounding")
    public String getRounding() {
        return rounding;
    }

    public void setRounding(String rounding) {
        this.rounding = rounding;
    }

    @Basic
    @Column(name = "RevisionPo")
    public String getRevisionPo() {
        return revisionPo;
    }

    public void setRevisionPo(String revisionPo) {
        this.revisionPo = revisionPo;
    }

    @Basic
    @Column(name = "Segment")
    public short getSegment() {
        return segment;
    }

    public void setSegment(short segment) {
        this.segment = segment;
    }

    @Basic
    @Column(name = "ReqDate")
    public Date getReqDate() {
        return reqDate;
    }

    public void setReqDate(Date reqDate) {
        this.reqDate = reqDate;
    }

    @Basic
    @Column(name = "CancelDate")
    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    @Basic
    @Column(name = "PickStatus")
    public String getPickStatus() {
        return pickStatus;
    }

    public void setPickStatus(String pickStatus) {
        this.pickStatus = pickStatus;
    }

    @Basic
    @Column(name = "Pick")
    public String getPick() {
        return pick;
    }

    public void setPick(String pick) {
        this.pick = pick;
    }

    @Basic
    @Column(name = "BlockDunn")
    public String getBlockDunn() {
        return blockDunn;
    }

    public void setBlockDunn(String blockDunn) {
        this.blockDunn = blockDunn;
    }

    @Basic
    @Column(name = "PeyMethod")
    public String getPeyMethod() {
        return peyMethod;
    }

    public void setPeyMethod(String peyMethod) {
        this.peyMethod = peyMethod;
    }

    @Basic
    @Column(name = "PayBlock")
    public String getPayBlock() {
        return payBlock;
    }

    public void setPayBlock(String payBlock) {
        this.payBlock = payBlock;
    }

    @Basic
    @Column(name = "PayBlckRef")
    public Integer getPayBlckRef() {
        return payBlckRef;
    }

    public void setPayBlckRef(Integer payBlckRef) {
        this.payBlckRef = payBlckRef;
    }

    @Basic
    @Column(name = "MaxDscn")
    public String getMaxDscn() {
        return maxDscn;
    }

    public void setMaxDscn(String maxDscn) {
        this.maxDscn = maxDscn;
    }

    @Basic
    @Column(name = "Reserve")
    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }

    @Basic
    @Column(name = "Max1099")
    public Long getMax1099() {
        return max1099;
    }

    public void setMax1099(Long max1099) {
        this.max1099 = max1099;
    }

    @Basic
    @Column(name = "CntrlBnk")
    public String getCntrlBnk() {
        return cntrlBnk;
    }

    public void setCntrlBnk(String cntrlBnk) {
        this.cntrlBnk = cntrlBnk;
    }

    @Basic
    @Column(name = "PickRmrk")
    public String getPickRmrk() {
        return pickRmrk;
    }

    public void setPickRmrk(String pickRmrk) {
        this.pickRmrk = pickRmrk;
    }

    @Basic
    @Column(name = "ISRCodLine")
    public String getIsrCodLine() {
        return isrCodLine;
    }

    public void setIsrCodLine(String isrCodLine) {
        this.isrCodLine = isrCodLine;
    }

    @Basic
    @Column(name = "ExpAppl")
    public Long getExpAppl() {
        return expAppl;
    }

    public void setExpAppl(Long expAppl) {
        this.expAppl = expAppl;
    }

    @Basic
    @Column(name = "ExpApplFC")
    public Long getExpApplFc() {
        return expApplFc;
    }

    public void setExpApplFc(Long expApplFc) {
        this.expApplFc = expApplFc;
    }

    @Basic
    @Column(name = "ExpApplSC")
    public Long getExpApplSc() {
        return expApplSc;
    }

    public void setExpApplSc(Long expApplSc) {
        this.expApplSc = expApplSc;
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
    @Column(name = "DeferrTax")
    public String getDeferrTax() {
        return deferrTax;
    }

    public void setDeferrTax(String deferrTax) {
        this.deferrTax = deferrTax;
    }

    @Basic
    @Column(name = "LetterNum")
    public String getLetterNum() {
        return letterNum;
    }

    public void setLetterNum(String letterNum) {
        this.letterNum = letterNum;
    }

    @Basic
    @Column(name = "FromDate")
    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    @Basic
    @Column(name = "ToDate")
    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Basic
    @Column(name = "WTApplied")
    public Long getWtApplied() {
        return wtApplied;
    }

    public void setWtApplied(Long wtApplied) {
        this.wtApplied = wtApplied;
    }

    @Basic
    @Column(name = "WTAppliedF")
    public Long getWtAppliedF() {
        return wtAppliedF;
    }

    public void setWtAppliedF(Long wtAppliedF) {
        this.wtAppliedF = wtAppliedF;
    }

    @Basic
    @Column(name = "BoeReserev")
    public String getBoeReserev() {
        return boeReserev;
    }

    public void setBoeReserev(String boeReserev) {
        this.boeReserev = boeReserev;
    }

    @Basic
    @Column(name = "AgentCode")
    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    @Basic
    @Column(name = "WTAppliedS")
    public Long getWtAppliedS() {
        return wtAppliedS;
    }

    public void setWtAppliedS(Long wtAppliedS) {
        this.wtAppliedS = wtAppliedS;
    }

    @Basic
    @Column(name = "EquVatSum")
    public Long getEquVatSum() {
        return equVatSum;
    }

    public void setEquVatSum(Long equVatSum) {
        this.equVatSum = equVatSum;
    }

    @Basic
    @Column(name = "EquVatSumF")
    public Long getEquVatSumF() {
        return equVatSumF;
    }

    public void setEquVatSumF(Long equVatSumF) {
        this.equVatSumF = equVatSumF;
    }

    @Basic
    @Column(name = "EquVatSumS")
    public Long getEquVatSumS() {
        return equVatSumS;
    }

    public void setEquVatSumS(Long equVatSumS) {
        this.equVatSumS = equVatSumS;
    }

    @Basic
    @Column(name = "Installmnt")
    public Short getInstallmnt() {
        return installmnt;
    }

    public void setInstallmnt(Short installmnt) {
        this.installmnt = installmnt;
    }

    @Basic
    @Column(name = "VATFirst")
    public String getVatFirst() {
        return vatFirst;
    }

    public void setVatFirst(String vatFirst) {
        this.vatFirst = vatFirst;
    }

    @Basic
    @Column(name = "NnSbAmnt")
    public Long getNnSbAmnt() {
        return nnSbAmnt;
    }

    public void setNnSbAmnt(Long nnSbAmnt) {
        this.nnSbAmnt = nnSbAmnt;
    }

    @Basic
    @Column(name = "NnSbAmntSC")
    public Long getNnSbAmntSc() {
        return nnSbAmntSc;
    }

    public void setNnSbAmntSc(Long nnSbAmntSc) {
        this.nnSbAmntSc = nnSbAmntSc;
    }

    @Basic
    @Column(name = "NbSbAmntFC")
    public Long getNbSbAmntFc() {
        return nbSbAmntFc;
    }

    public void setNbSbAmntFc(Long nbSbAmntFc) {
        this.nbSbAmntFc = nbSbAmntFc;
    }

    @Basic
    @Column(name = "ExepAmnt")
    public Long getExepAmnt() {
        return exepAmnt;
    }

    public void setExepAmnt(Long exepAmnt) {
        this.exepAmnt = exepAmnt;
    }

    @Basic
    @Column(name = "ExepAmntSC")
    public Long getExepAmntSc() {
        return exepAmntSc;
    }

    public void setExepAmntSc(Long exepAmntSc) {
        this.exepAmntSc = exepAmntSc;
    }

    @Basic
    @Column(name = "ExepAmntFC")
    public Long getExepAmntFc() {
        return exepAmntFc;
    }

    public void setExepAmntFc(Long exepAmntFc) {
        this.exepAmntFc = exepAmntFc;
    }

    @Basic
    @Column(name = "VatDate")
    public Date getVatDate() {
        return vatDate;
    }

    public void setVatDate(Date vatDate) {
        this.vatDate = vatDate;
    }

    @Basic
    @Column(name = "CorrExt")
    public String getCorrExt() {
        return corrExt;
    }

    public void setCorrExt(String corrExt) {
        this.corrExt = corrExt;
    }

    @Basic
    @Column(name = "CorrInv")
    public Integer getCorrInv() {
        return corrInv;
    }

    public void setCorrInv(Integer corrInv) {
        this.corrInv = corrInv;
    }

    @Basic
    @Column(name = "NCorrInv")
    public Integer getnCorrInv() {
        return nCorrInv;
    }

    public void setnCorrInv(Integer nCorrInv) {
        this.nCorrInv = nCorrInv;
    }

    @Basic
    @Column(name = "CEECFlag")
    public String getCeecFlag() {
        return ceecFlag;
    }

    public void setCeecFlag(String ceecFlag) {
        this.ceecFlag = ceecFlag;
    }

    @Basic
    @Column(name = "BaseAmnt")
    public Long getBaseAmnt() {
        return baseAmnt;
    }

    public void setBaseAmnt(Long baseAmnt) {
        this.baseAmnt = baseAmnt;
    }

    @Basic
    @Column(name = "BaseAmntSC")
    public Long getBaseAmntSc() {
        return baseAmntSc;
    }

    public void setBaseAmntSc(Long baseAmntSc) {
        this.baseAmntSc = baseAmntSc;
    }

    @Basic
    @Column(name = "BaseAmntFC")
    public Long getBaseAmntFc() {
        return baseAmntFc;
    }

    public void setBaseAmntFc(Long baseAmntFc) {
        this.baseAmntFc = baseAmntFc;
    }

    @Basic
    @Column(name = "CtlAccount")
    public String getCtlAccount() {
        return ctlAccount;
    }

    public void setCtlAccount(String ctlAccount) {
        this.ctlAccount = ctlAccount;
    }

    @Basic
    @Column(name = "BPLId")
    public Integer getBplId() {
        return bplId;
    }

    public void setBplId(Integer bplId) {
        this.bplId = bplId;
    }

    @Basic
    @Column(name = "BPLName")
    public String getBplName() {
        return bplName;
    }

    public void setBplName(String bplName) {
        this.bplName = bplName;
    }

    @Basic
    @Column(name = "VATRegNum")
    public String getVatRegNum() {
        return vatRegNum;
    }

    public void setVatRegNum(String vatRegNum) {
        this.vatRegNum = vatRegNum;
    }

    @Basic
    @Column(name = "TxInvRptNo")
    public String getTxInvRptNo() {
        return txInvRptNo;
    }

    public void setTxInvRptNo(String txInvRptNo) {
        this.txInvRptNo = txInvRptNo;
    }

    @Basic
    @Column(name = "TxInvRptDt")
    public Date getTxInvRptDt() {
        return txInvRptDt;
    }

    public void setTxInvRptDt(Date txInvRptDt) {
        this.txInvRptDt = txInvRptDt;
    }

    @Basic
    @Column(name = "KVVATCode")
    public String getKvvatCode() {
        return kvvatCode;
    }

    public void setKvvatCode(String kvvatCode) {
        this.kvvatCode = kvvatCode;
    }

    @Basic
    @Column(name = "WTDetails")
    public String getWtDetails() {
        return wtDetails;
    }

    public void setWtDetails(String wtDetails) {
        this.wtDetails = wtDetails;
    }

    @Basic
    @Column(name = "SumAbsId")
    public Integer getSumAbsId() {
        return sumAbsId;
    }

    public void setSumAbsId(Integer sumAbsId) {
        this.sumAbsId = sumAbsId;
    }

    @Basic
    @Column(name = "SumRptDate")
    public Date getSumRptDate() {
        return sumRptDate;
    }

    public void setSumRptDate(Date sumRptDate) {
        this.sumRptDate = sumRptDate;
    }

    @Basic
    @Column(name = "PIndicator")
    public String getpIndicator() {
        return pIndicator;
    }

    public void setpIndicator(String pIndicator) {
        this.pIndicator = pIndicator;
    }

    @Basic
    @Column(name = "ManualNum")
    public String getManualNum() {
        return manualNum;
    }

    public void setManualNum(String manualNum) {
        this.manualNum = manualNum;
    }

    @Basic
    @Column(name = "UseShpdGd")
    public String getUseShpdGd() {
        return useShpdGd;
    }

    public void setUseShpdGd(String useShpdGd) {
        this.useShpdGd = useShpdGd;
    }

    @Basic
    @Column(name = "BaseVtAt")
    public Long getBaseVtAt() {
        return baseVtAt;
    }

    public void setBaseVtAt(Long baseVtAt) {
        this.baseVtAt = baseVtAt;
    }

    @Basic
    @Column(name = "BaseVtAtSC")
    public Long getBaseVtAtSc() {
        return baseVtAtSc;
    }

    public void setBaseVtAtSc(Long baseVtAtSc) {
        this.baseVtAtSc = baseVtAtSc;
    }

    @Basic
    @Column(name = "BaseVtAtFC")
    public Long getBaseVtAtFc() {
        return baseVtAtFc;
    }

    public void setBaseVtAtFc(Long baseVtAtFc) {
        this.baseVtAtFc = baseVtAtFc;
    }

    @Basic
    @Column(name = "NnSbVAt")
    public Long getNnSbVAt() {
        return nnSbVAt;
    }

    public void setNnSbVAt(Long nnSbVAt) {
        this.nnSbVAt = nnSbVAt;
    }

    @Basic
    @Column(name = "NnSbVAtSC")
    public Long getNnSbVAtSc() {
        return nnSbVAtSc;
    }

    public void setNnSbVAtSc(Long nnSbVAtSc) {
        this.nnSbVAtSc = nnSbVAtSc;
    }

    @Basic
    @Column(name = "NbSbVAtFC")
    public Long getNbSbVAtFc() {
        return nbSbVAtFc;
    }

    public void setNbSbVAtFc(Long nbSbVAtFc) {
        this.nbSbVAtFc = nbSbVAtFc;
    }

    @Basic
    @Column(name = "ExptVAt")
    public Long getExptVAt() {
        return exptVAt;
    }

    public void setExptVAt(Long exptVAt) {
        this.exptVAt = exptVAt;
    }

    @Basic
    @Column(name = "ExptVAtSC")
    public Long getExptVAtSc() {
        return exptVAtSc;
    }

    public void setExptVAtSc(Long exptVAtSc) {
        this.exptVAtSc = exptVAtSc;
    }

    @Basic
    @Column(name = "ExptVAtFC")
    public Long getExptVAtFc() {
        return exptVAtFc;
    }

    public void setExptVAtFc(Long exptVAtFc) {
        this.exptVAtFc = exptVAtFc;
    }

    @Basic
    @Column(name = "LYPmtAt")
    public Long getLyPmtAt() {
        return lyPmtAt;
    }

    public void setLyPmtAt(Long lyPmtAt) {
        this.lyPmtAt = lyPmtAt;
    }

    @Basic
    @Column(name = "LYPmtAtSC")
    public Long getLyPmtAtSc() {
        return lyPmtAtSc;
    }

    public void setLyPmtAtSc(Long lyPmtAtSc) {
        this.lyPmtAtSc = lyPmtAtSc;
    }

    @Basic
    @Column(name = "LYPmtAtFC")
    public Long getLyPmtAtFc() {
        return lyPmtAtFc;
    }

    public void setLyPmtAtFc(Long lyPmtAtFc) {
        this.lyPmtAtFc = lyPmtAtFc;
    }

    @Basic
    @Column(name = "ExpAnSum")
    public Long getExpAnSum() {
        return expAnSum;
    }

    public void setExpAnSum(Long expAnSum) {
        this.expAnSum = expAnSum;
    }

    @Basic
    @Column(name = "ExpAnSys")
    public Long getExpAnSys() {
        return expAnSys;
    }

    public void setExpAnSys(Long expAnSys) {
        this.expAnSys = expAnSys;
    }

    @Basic
    @Column(name = "ExpAnFrgn")
    public Long getExpAnFrgn() {
        return expAnFrgn;
    }

    public void setExpAnFrgn(Long expAnFrgn) {
        this.expAnFrgn = expAnFrgn;
    }

    @Basic
    @Column(name = "DocSubType")
    public String getDocSubType() {
        return docSubType;
    }

    public void setDocSubType(String docSubType) {
        this.docSubType = docSubType;
    }

    @Basic
    @Column(name = "DpmStatus")
    public String getDpmStatus() {
        return dpmStatus;
    }

    public void setDpmStatus(String dpmStatus) {
        this.dpmStatus = dpmStatus;
    }

    @Basic
    @Column(name = "DpmAmnt")
    public Long getDpmAmnt() {
        return dpmAmnt;
    }

    public void setDpmAmnt(Long dpmAmnt) {
        this.dpmAmnt = dpmAmnt;
    }

    @Basic
    @Column(name = "DpmAmntSC")
    public Long getDpmAmntSc() {
        return dpmAmntSc;
    }

    public void setDpmAmntSc(Long dpmAmntSc) {
        this.dpmAmntSc = dpmAmntSc;
    }

    @Basic
    @Column(name = "DpmAmntFC")
    public Long getDpmAmntFc() {
        return dpmAmntFc;
    }

    public void setDpmAmntFc(Long dpmAmntFc) {
        this.dpmAmntFc = dpmAmntFc;
    }

    @Basic
    @Column(name = "DpmDrawn")
    public String getDpmDrawn() {
        return dpmDrawn;
    }

    public void setDpmDrawn(String dpmDrawn) {
        this.dpmDrawn = dpmDrawn;
    }

    @Basic
    @Column(name = "DpmPrcnt")
    public Long getDpmPrcnt() {
        return dpmPrcnt;
    }

    public void setDpmPrcnt(Long dpmPrcnt) {
        this.dpmPrcnt = dpmPrcnt;
    }

    @Basic
    @Column(name = "PaidSum")
    public Long getPaidSum() {
        return paidSum;
    }

    public void setPaidSum(Long paidSum) {
        this.paidSum = paidSum;
    }

    @Basic
    @Column(name = "PaidSumFc")
    public Long getPaidSumFc() {
        return paidSumFc;
    }

    public void setPaidSumFc(Long paidSumFc) {
        this.paidSumFc = paidSumFc;
    }

    @Basic
    @Column(name = "PaidSumSc")
    public Long getPaidSumSc() {
        return paidSumSc;
    }

    public void setPaidSumSc(Long paidSumSc) {
        this.paidSumSc = paidSumSc;
    }

    @Basic
    @Column(name = "FolioPref")
    public String getFolioPref() {
        return folioPref;
    }

    public void setFolioPref(String folioPref) {
        this.folioPref = folioPref;
    }

    @Basic
    @Column(name = "FolioNum")
    public Integer getFolioNum() {
        return folioNum;
    }

    public void setFolioNum(Integer folioNum) {
        this.folioNum = folioNum;
    }

    @Basic
    @Column(name = "DpmAppl")
    public Long getDpmAppl() {
        return dpmAppl;
    }

    public void setDpmAppl(Long dpmAppl) {
        this.dpmAppl = dpmAppl;
    }

    @Basic
    @Column(name = "DpmApplFc")
    public Long getDpmApplFc() {
        return dpmApplFc;
    }

    public void setDpmApplFc(Long dpmApplFc) {
        this.dpmApplFc = dpmApplFc;
    }

    @Basic
    @Column(name = "DpmApplSc")
    public Long getDpmApplSc() {
        return dpmApplSc;
    }

    public void setDpmApplSc(Long dpmApplSc) {
        this.dpmApplSc = dpmApplSc;
    }

    @Basic
    @Column(name = "LPgFolioN")
    public Integer getlPgFolioN() {
        return lPgFolioN;
    }

    public void setlPgFolioN(Integer lPgFolioN) {
        this.lPgFolioN = lPgFolioN;
    }

    @Basic
    @Column(name = "Header")
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @Basic
    @Column(name = "Footer")
    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    @Basic
    @Column(name = "Posted")
    public String getPosted() {
        return posted;
    }

    public void setPosted(String posted) {
        this.posted = posted;
    }

    @Basic
    @Column(name = "OwnerCode")
    public Integer getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(Integer ownerCode) {
        this.ownerCode = ownerCode;
    }

    @Basic
    @Column(name = "BPChCode")
    public String getBpChCode() {
        return bpChCode;
    }

    public void setBpChCode(String bpChCode) {
        this.bpChCode = bpChCode;
    }

    @Basic
    @Column(name = "BPChCntc")
    public Integer getBpChCntc() {
        return bpChCntc;
    }

    public void setBpChCntc(Integer bpChCntc) {
        this.bpChCntc = bpChCntc;
    }

    @Basic
    @Column(name = "PayToCode")
    public String getPayToCode() {
        return payToCode;
    }

    public void setPayToCode(String payToCode) {
        this.payToCode = payToCode;
    }

    @Basic
    @Column(name = "IsPaytoBnk")
    public String getIsPaytoBnk() {
        return isPaytoBnk;
    }

    public void setIsPaytoBnk(String isPaytoBnk) {
        this.isPaytoBnk = isPaytoBnk;
    }

    @Basic
    @Column(name = "BnkCntry")
    public String getBnkCntry() {
        return bnkCntry;
    }

    public void setBnkCntry(String bnkCntry) {
        this.bnkCntry = bnkCntry;
    }

    @Basic
    @Column(name = "BankCode")
    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    @Basic
    @Column(name = "BnkAccount")
    public String getBnkAccount() {
        return bnkAccount;
    }

    public void setBnkAccount(String bnkAccount) {
        this.bnkAccount = bnkAccount;
    }

    @Basic
    @Column(name = "BnkBranch")
    public String getBnkBranch() {
        return bnkBranch;
    }

    public void setBnkBranch(String bnkBranch) {
        this.bnkBranch = bnkBranch;
    }

    @Basic
    @Column(name = "isIns")
    public String getIsIns() {
        return isIns;
    }

    public void setIsIns(String isIns) {
        this.isIns = isIns;
    }

    @Basic
    @Column(name = "TrackNo")
    public String getTrackNo() {
        return trackNo;
    }

    public void setTrackNo(String trackNo) {
        this.trackNo = trackNo;
    }

    @Basic
    @Column(name = "VersionNum")
    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    @Basic
    @Column(name = "LangCode")
    public Integer getLangCode() {
        return langCode;
    }

    public void setLangCode(Integer langCode) {
        this.langCode = langCode;
    }

    @Basic
    @Column(name = "BPNameOW")
    public String getBpNameOw() {
        return bpNameOw;
    }

    public void setBpNameOw(String bpNameOw) {
        this.bpNameOw = bpNameOw;
    }

    @Basic
    @Column(name = "BillToOW")
    public String getBillToOw() {
        return billToOw;
    }

    public void setBillToOw(String billToOw) {
        this.billToOw = billToOw;
    }

    @Basic
    @Column(name = "ShipToOW")
    public String getShipToOw() {
        return shipToOw;
    }

    public void setShipToOw(String shipToOw) {
        this.shipToOw = shipToOw;
    }

    @Basic
    @Column(name = "RetInvoice")
    public String getRetInvoice() {
        return retInvoice;
    }

    public void setRetInvoice(String retInvoice) {
        this.retInvoice = retInvoice;
    }

    @Basic
    @Column(name = "ClsDate")
    public Date getClsDate() {
        return clsDate;
    }

    public void setClsDate(Date clsDate) {
        this.clsDate = clsDate;
    }

    @Basic
    @Column(name = "MInvNum")
    public Integer getmInvNum() {
        return mInvNum;
    }

    public void setmInvNum(Integer mInvNum) {
        this.mInvNum = mInvNum;
    }

    @Basic
    @Column(name = "MInvDate")
    public Date getmInvDate() {
        return mInvDate;
    }

    public void setmInvDate(Date mInvDate) {
        this.mInvDate = mInvDate;
    }

    @Basic
    @Column(name = "SeqCode")
    public Short getSeqCode() {
        return seqCode;
    }

    public void setSeqCode(Short seqCode) {
        this.seqCode = seqCode;
    }

    @Basic
    @Column(name = "Serial")
    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    @Basic
    @Column(name = "SeriesStr")
    public String getSeriesStr() {
        return seriesStr;
    }

    public void setSeriesStr(String seriesStr) {
        this.seriesStr = seriesStr;
    }

    @Basic
    @Column(name = "SubStr")
    public String getSubStr() {
        return subStr;
    }

    public void setSubStr(String subStr) {
        this.subStr = subStr;
    }

    @Basic
    @Column(name = "Model")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "TaxOnExp")
    public Long getTaxOnExp() {
        return taxOnExp;
    }

    public void setTaxOnExp(Long taxOnExp) {
        this.taxOnExp = taxOnExp;
    }

    @Basic
    @Column(name = "TaxOnExpFc")
    public Long getTaxOnExpFc() {
        return taxOnExpFc;
    }

    public void setTaxOnExpFc(Long taxOnExpFc) {
        this.taxOnExpFc = taxOnExpFc;
    }

    @Basic
    @Column(name = "TaxOnExpSc")
    public Long getTaxOnExpSc() {
        return taxOnExpSc;
    }

    public void setTaxOnExpSc(Long taxOnExpSc) {
        this.taxOnExpSc = taxOnExpSc;
    }

    @Basic
    @Column(name = "TaxOnExAp")
    public Long getTaxOnExAp() {
        return taxOnExAp;
    }

    public void setTaxOnExAp(Long taxOnExAp) {
        this.taxOnExAp = taxOnExAp;
    }

    @Basic
    @Column(name = "TaxOnExApF")
    public Long getTaxOnExApF() {
        return taxOnExApF;
    }

    public void setTaxOnExApF(Long taxOnExApF) {
        this.taxOnExApF = taxOnExApF;
    }

    @Basic
    @Column(name = "TaxOnExApS")
    public Long getTaxOnExApS() {
        return taxOnExApS;
    }

    public void setTaxOnExApS(Long taxOnExApS) {
        this.taxOnExApS = taxOnExApS;
    }

    @Basic
    @Column(name = "LastPmnTyp")
    public String getLastPmnTyp() {
        return lastPmnTyp;
    }

    public void setLastPmnTyp(String lastPmnTyp) {
        this.lastPmnTyp = lastPmnTyp;
    }

    @Basic
    @Column(name = "LndCstNum")
    public Integer getLndCstNum() {
        return lndCstNum;
    }

    public void setLndCstNum(Integer lndCstNum) {
        this.lndCstNum = lndCstNum;
    }

    @Basic
    @Column(name = "UseCorrVat")
    public String getUseCorrVat() {
        return useCorrVat;
    }

    public void setUseCorrVat(String useCorrVat) {
        this.useCorrVat = useCorrVat;
    }

    @Basic
    @Column(name = "BlkCredMmo")
    public String getBlkCredMmo() {
        return blkCredMmo;
    }

    public void setBlkCredMmo(String blkCredMmo) {
        this.blkCredMmo = blkCredMmo;
    }

    @Basic
    @Column(name = "OpenForLaC")
    public String getOpenForLaC() {
        return openForLaC;
    }

    public void setOpenForLaC(String openForLaC) {
        this.openForLaC = openForLaC;
    }

    @Basic
    @Column(name = "Excised")
    public String getExcised() {
        return excised;
    }

    public void setExcised(String excised) {
        this.excised = excised;
    }

    @Basic
    @Column(name = "ExcRefDate")
    public Date getExcRefDate() {
        return excRefDate;
    }

    public void setExcRefDate(Date excRefDate) {
        this.excRefDate = excRefDate;
    }

    @Basic
    @Column(name = "ExcRmvTime")
    public String getExcRmvTime() {
        return excRmvTime;
    }

    public void setExcRmvTime(String excRmvTime) {
        this.excRmvTime = excRmvTime;
    }

    @Basic
    @Column(name = "SrvGpPrcnt")
    public Long getSrvGpPrcnt() {
        return srvGpPrcnt;
    }

    public void setSrvGpPrcnt(Long srvGpPrcnt) {
        this.srvGpPrcnt = srvGpPrcnt;
    }

    @Basic
    @Column(name = "DepositNum")
    public Integer getDepositNum() {
        return depositNum;
    }

    public void setDepositNum(Integer depositNum) {
        this.depositNum = depositNum;
    }

    @Basic
    @Column(name = "CertNum")
    public String getCertNum() {
        return certNum;
    }

    public void setCertNum(String certNum) {
        this.certNum = certNum;
    }

    @Basic
    @Column(name = "DutyStatus")
    public String getDutyStatus() {
        return dutyStatus;
    }

    public void setDutyStatus(String dutyStatus) {
        this.dutyStatus = dutyStatus;
    }

    @Basic
    @Column(name = "AutoCrtFlw")
    public String getAutoCrtFlw() {
        return autoCrtFlw;
    }

    public void setAutoCrtFlw(String autoCrtFlw) {
        this.autoCrtFlw = autoCrtFlw;
    }

    @Basic
    @Column(name = "FlwRefDate")
    public Date getFlwRefDate() {
        return flwRefDate;
    }

    public void setFlwRefDate(Date flwRefDate) {
        this.flwRefDate = flwRefDate;
    }

    @Basic
    @Column(name = "FlwRefNum")
    public String getFlwRefNum() {
        return flwRefNum;
    }

    public void setFlwRefNum(String flwRefNum) {
        this.flwRefNum = flwRefNum;
    }

    @Basic
    @Column(name = "VatJENum")
    public Integer getVatJeNum() {
        return vatJeNum;
    }

    public void setVatJeNum(Integer vatJeNum) {
        this.vatJeNum = vatJeNum;
    }

    @Basic
    @Column(name = "DpmVat")
    public Long getDpmVat() {
        return dpmVat;
    }

    public void setDpmVat(Long dpmVat) {
        this.dpmVat = dpmVat;
    }

    @Basic
    @Column(name = "DpmVatFc")
    public Long getDpmVatFc() {
        return dpmVatFc;
    }

    public void setDpmVatFc(Long dpmVatFc) {
        this.dpmVatFc = dpmVatFc;
    }

    @Basic
    @Column(name = "DpmVatSc")
    public Long getDpmVatSc() {
        return dpmVatSc;
    }

    public void setDpmVatSc(Long dpmVatSc) {
        this.dpmVatSc = dpmVatSc;
    }

    @Basic
    @Column(name = "DpmAppVat")
    public Long getDpmAppVat() {
        return dpmAppVat;
    }

    public void setDpmAppVat(Long dpmAppVat) {
        this.dpmAppVat = dpmAppVat;
    }

    @Basic
    @Column(name = "DpmAppVatF")
    public Long getDpmAppVatF() {
        return dpmAppVatF;
    }

    public void setDpmAppVatF(Long dpmAppVatF) {
        this.dpmAppVatF = dpmAppVatF;
    }

    @Basic
    @Column(name = "DpmAppVatS")
    public Long getDpmAppVatS() {
        return dpmAppVatS;
    }

    public void setDpmAppVatS(Long dpmAppVatS) {
        this.dpmAppVatS = dpmAppVatS;
    }

    @Basic
    @Column(name = "InsurOp347")
    public String getInsurOp347() {
        return insurOp347;
    }

    public void setInsurOp347(String insurOp347) {
        this.insurOp347 = insurOp347;
    }

    @Basic
    @Column(name = "IgnRelDoc")
    public String getIgnRelDoc() {
        return ignRelDoc;
    }

    public void setIgnRelDoc(String ignRelDoc) {
        this.ignRelDoc = ignRelDoc;
    }

    @Basic
    @Column(name = "BuildDesc")
    public String getBuildDesc() {
        return buildDesc;
    }

    public void setBuildDesc(String buildDesc) {
        this.buildDesc = buildDesc;
    }

    @Basic
    @Column(name = "ResidenNum")
    public String getResidenNum() {
        return residenNum;
    }

    public void setResidenNum(String residenNum) {
        this.residenNum = residenNum;
    }

    @Basic
    @Column(name = "Checker")
    public Integer getChecker() {
        return checker;
    }

    public void setChecker(Integer checker) {
        this.checker = checker;
    }

    @Basic
    @Column(name = "Payee")
    public Integer getPayee() {
        return payee;
    }

    public void setPayee(Integer payee) {
        this.payee = payee;
    }

    @Basic
    @Column(name = "CopyNumber")
    public Integer getCopyNumber() {
        return copyNumber;
    }

    public void setCopyNumber(Integer copyNumber) {
        this.copyNumber = copyNumber;
    }

    @Basic
    @Column(name = "SSIExmpt")
    public String getSsiExmpt() {
        return ssiExmpt;
    }

    public void setSsiExmpt(String ssiExmpt) {
        this.ssiExmpt = ssiExmpt;
    }

    @Basic
    @Column(name = "PQTGrpSer")
    public Short getPqtGrpSer() {
        return pqtGrpSer;
    }

    public void setPqtGrpSer(Short pqtGrpSer) {
        this.pqtGrpSer = pqtGrpSer;
    }

    @Basic
    @Column(name = "PQTGrpNum")
    public Integer getPqtGrpNum() {
        return pqtGrpNum;
    }

    public void setPqtGrpNum(Integer pqtGrpNum) {
        this.pqtGrpNum = pqtGrpNum;
    }

    @Basic
    @Column(name = "PQTGrpHW")
    public String getPqtGrpHw() {
        return pqtGrpHw;
    }

    public void setPqtGrpHw(String pqtGrpHw) {
        this.pqtGrpHw = pqtGrpHw;
    }

    @Basic
    @Column(name = "ReopOriDoc")
    public String getReopOriDoc() {
        return reopOriDoc;
    }

    public void setReopOriDoc(String reopOriDoc) {
        this.reopOriDoc = reopOriDoc;
    }

    @Basic
    @Column(name = "ReopManCls")
    public String getReopManCls() {
        return reopManCls;
    }

    public void setReopManCls(String reopManCls) {
        this.reopManCls = reopManCls;
    }

    @Basic
    @Column(name = "DocManClsd")
    public String getDocManClsd() {
        return docManClsd;
    }

    public void setDocManClsd(String docManClsd) {
        this.docManClsd = docManClsd;
    }

    @Basic
    @Column(name = "ClosingOpt")
    public Short getClosingOpt() {
        return closingOpt;
    }

    public void setClosingOpt(Short closingOpt) {
        this.closingOpt = closingOpt;
    }

    @Basic
    @Column(name = "SpecDate")
    public Date getSpecDate() {
        return specDate;
    }

    public void setSpecDate(Date specDate) {
        this.specDate = specDate;
    }

    @Basic
    @Column(name = "Ordered")
    public String getOrdered() {
        return ordered;
    }

    public void setOrdered(String ordered) {
        this.ordered = ordered;
    }

    @Basic
    @Column(name = "NTSApprov")
    public String getNtsApprov() {
        return ntsApprov;
    }

    public void setNtsApprov(String ntsApprov) {
        this.ntsApprov = ntsApprov;
    }

    @Basic
    @Column(name = "NTSWebSite")
    public Short getNtsWebSite() {
        return ntsWebSite;
    }

    public void setNtsWebSite(Short ntsWebSite) {
        this.ntsWebSite = ntsWebSite;
    }

    @Basic
    @Column(name = "NTSeTaxNo")
    public String getNtSeTaxNo() {
        return ntSeTaxNo;
    }

    public void setNtSeTaxNo(String ntSeTaxNo) {
        this.ntSeTaxNo = ntSeTaxNo;
    }

    @Basic
    @Column(name = "NTSApprNo")
    public String getNtsApprNo() {
        return ntsApprNo;
    }

    public void setNtsApprNo(String ntsApprNo) {
        this.ntsApprNo = ntsApprNo;
    }

    @Basic
    @Column(name = "PayDuMonth")
    public String getPayDuMonth() {
        return payDuMonth;
    }

    public void setPayDuMonth(String payDuMonth) {
        this.payDuMonth = payDuMonth;
    }

    @Basic
    @Column(name = "ExtraMonth")
    public Short getExtraMonth() {
        return extraMonth;
    }

    public void setExtraMonth(Short extraMonth) {
        this.extraMonth = extraMonth;
    }

    @Basic
    @Column(name = "ExtraDays")
    public Short getExtraDays() {
        return extraDays;
    }

    public void setExtraDays(Short extraDays) {
        this.extraDays = extraDays;
    }

    @Basic
    @Column(name = "CdcOffset")
    public Short getCdcOffset() {
        return cdcOffset;
    }

    public void setCdcOffset(Short cdcOffset) {
        this.cdcOffset = cdcOffset;
    }

    @Basic
    @Column(name = "SignMsg")
    public String getSignMsg() {
        return signMsg;
    }

    public void setSignMsg(String signMsg) {
        this.signMsg = signMsg;
    }

    @Basic
    @Column(name = "SignDigest")
    public String getSignDigest() {
        return signDigest;
    }

    public void setSignDigest(String signDigest) {
        this.signDigest = signDigest;
    }

    @Basic
    @Column(name = "CertifNum")
    public String getCertifNum() {
        return certifNum;
    }

    public void setCertifNum(String certifNum) {
        this.certifNum = certifNum;
    }

    @Basic
    @Column(name = "KeyVersion")
    public Integer getKeyVersion() {
        return keyVersion;
    }

    public void setKeyVersion(Integer keyVersion) {
        this.keyVersion = keyVersion;
    }

    @Basic
    @Column(name = "EDocGenTyp")
    public String geteDocGenTyp() {
        return eDocGenTyp;
    }

    public void seteDocGenTyp(String eDocGenTyp) {
        this.eDocGenTyp = eDocGenTyp;
    }

    @Basic
    @Column(name = "ESeries")
    public Short geteSeries() {
        return eSeries;
    }

    public void seteSeries(Short eSeries) {
        this.eSeries = eSeries;
    }

    @Basic
    @Column(name = "EDocNum")
    public String geteDocNum() {
        return eDocNum;
    }

    public void seteDocNum(String eDocNum) {
        this.eDocNum = eDocNum;
    }

    @Basic
    @Column(name = "EDocExpFrm")
    public Integer geteDocExpFrm() {
        return eDocExpFrm;
    }

    public void seteDocExpFrm(Integer eDocExpFrm) {
        this.eDocExpFrm = eDocExpFrm;
    }

    @Basic
    @Column(name = "OnlineQuo")
    public String getOnlineQuo() {
        return onlineQuo;
    }

    public void setOnlineQuo(String onlineQuo) {
        this.onlineQuo = onlineQuo;
    }

    @Basic
    @Column(name = "POSEqNum")
    public String getPosEqNum() {
        return posEqNum;
    }

    public void setPosEqNum(String posEqNum) {
        this.posEqNum = posEqNum;
    }

    @Basic
    @Column(name = "POSManufSN")
    public String getPosManufSn() {
        return posManufSn;
    }

    public void setPosManufSn(String posManufSn) {
        this.posManufSn = posManufSn;
    }

    @Basic
    @Column(name = "POSCashN")
    public Integer getPosCashN() {
        return posCashN;
    }

    public void setPosCashN(Integer posCashN) {
        this.posCashN = posCashN;
    }

    @Basic
    @Column(name = "EDocStatus")
    public String geteDocStatus() {
        return eDocStatus;
    }

    public void seteDocStatus(String eDocStatus) {
        this.eDocStatus = eDocStatus;
    }

    @Basic
    @Column(name = "EDocCntnt")
    public String geteDocCntnt() {
        return eDocCntnt;
    }

    public void seteDocCntnt(String eDocCntnt) {
        this.eDocCntnt = eDocCntnt;
    }

    @Basic
    @Column(name = "EDocProces")
    public String geteDocProces() {
        return eDocProces;
    }

    public void seteDocProces(String eDocProces) {
        this.eDocProces = eDocProces;
    }

    @Basic
    @Column(name = "EDocErrCod")
    public String geteDocErrCod() {
        return eDocErrCod;
    }

    public void seteDocErrCod(String eDocErrCod) {
        this.eDocErrCod = eDocErrCod;
    }

    @Basic
    @Column(name = "EDocErrMsg")
    public String geteDocErrMsg() {
        return eDocErrMsg;
    }

    public void seteDocErrMsg(String eDocErrMsg) {
        this.eDocErrMsg = eDocErrMsg;
    }

    @Basic
    @Column(name = "EDocCancel")
    public String geteDocCancel() {
        return eDocCancel;
    }

    public void seteDocCancel(String eDocCancel) {
        this.eDocCancel = eDocCancel;
    }

    @Basic
    @Column(name = "EDocTest")
    public String geteDocTest() {
        return eDocTest;
    }

    public void seteDocTest(String eDocTest) {
        this.eDocTest = eDocTest;
    }

    @Basic
    @Column(name = "EDocPrefix")
    public String geteDocPrefix() {
        return eDocPrefix;
    }

    public void seteDocPrefix(String eDocPrefix) {
        this.eDocPrefix = eDocPrefix;
    }

    @Basic
    @Column(name = "CUP")
    public Integer getCup() {
        return cup;
    }

    public void setCup(Integer cup) {
        this.cup = cup;
    }

    @Basic
    @Column(name = "CIG")
    public Integer getCig() {
        return cig;
    }

    public void setCig(Integer cig) {
        this.cig = cig;
    }

    @Basic
    @Column(name = "DpmAsDscnt")
    public String getDpmAsDscnt() {
        return dpmAsDscnt;
    }

    public void setDpmAsDscnt(String dpmAsDscnt) {
        this.dpmAsDscnt = dpmAsDscnt;
    }

    @Basic
    @Column(name = "Attachment")
    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    @Basic
    @Column(name = "AtcEntry")
    public Integer getAtcEntry() {
        return atcEntry;
    }

    public void setAtcEntry(Integer atcEntry) {
        this.atcEntry = atcEntry;
    }

    @Basic
    @Column(name = "SupplCode")
    public String getSupplCode() {
        return supplCode;
    }

    public void setSupplCode(String supplCode) {
        this.supplCode = supplCode;
    }

    @Basic
    @Column(name = "GTSRlvnt")
    public String getGtsRlvnt() {
        return gtsRlvnt;
    }

    public void setGtsRlvnt(String gtsRlvnt) {
        this.gtsRlvnt = gtsRlvnt;
    }

    @Basic
    @Column(name = "BaseDisc")
    public Long getBaseDisc() {
        return baseDisc;
    }

    public void setBaseDisc(Long baseDisc) {
        this.baseDisc = baseDisc;
    }

    @Basic
    @Column(name = "BaseDiscSc")
    public Long getBaseDiscSc() {
        return baseDiscSc;
    }

    public void setBaseDiscSc(Long baseDiscSc) {
        this.baseDiscSc = baseDiscSc;
    }

    @Basic
    @Column(name = "BaseDiscFc")
    public Long getBaseDiscFc() {
        return baseDiscFc;
    }

    public void setBaseDiscFc(Long baseDiscFc) {
        this.baseDiscFc = baseDiscFc;
    }

    @Basic
    @Column(name = "BaseDiscPr")
    public Long getBaseDiscPr() {
        return baseDiscPr;
    }

    public void setBaseDiscPr(Long baseDiscPr) {
        this.baseDiscPr = baseDiscPr;
    }

    @Basic
    @Column(name = "CreateTS")
    public Integer getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Integer createTs) {
        this.createTs = createTs;
    }

    @Basic
    @Column(name = "UpdateTS")
    public Integer getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(Integer updateTs) {
        this.updateTs = updateTs;
    }

    @Basic
    @Column(name = "SrvTaxRule")
    public String getSrvTaxRule() {
        return srvTaxRule;
    }

    public void setSrvTaxRule(String srvTaxRule) {
        this.srvTaxRule = srvTaxRule;
    }

    @Basic
    @Column(name = "AnnInvDecR")
    public Integer getAnnInvDecR() {
        return annInvDecR;
    }

    public void setAnnInvDecR(Integer annInvDecR) {
        this.annInvDecR = annInvDecR;
    }

    @Basic
    @Column(name = "Supplier")
    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    @Basic
    @Column(name = "Releaser")
    public Integer getReleaser() {
        return releaser;
    }

    public void setReleaser(Integer releaser) {
        this.releaser = releaser;
    }

    @Basic
    @Column(name = "Receiver")
    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }

    @Basic
    @Column(name = "ToWhsCode")
    public String getToWhsCode() {
        return toWhsCode;
    }

    public void setToWhsCode(String toWhsCode) {
        this.toWhsCode = toWhsCode;
    }

    @Basic
    @Column(name = "AssetDate")
    public Date getAssetDate() {
        return assetDate;
    }

    public void setAssetDate(Date assetDate) {
        this.assetDate = assetDate;
    }

    @Basic
    @Column(name = "Requester")
    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    @Basic
    @Column(name = "ReqName")
    public String getReqName() {
        return reqName;
    }

    public void setReqName(String reqName) {
        this.reqName = reqName;
    }

    @Basic
    @Column(name = "Branch")
    public Short getBranch() {
        return branch;
    }

    public void setBranch(Short branch) {
        this.branch = branch;
    }

    @Basic
    @Column(name = "Department")
    public Short getDepartment() {
        return department;
    }

    public void setDepartment(Short department) {
        this.department = department;
    }

    @Basic
    @Column(name = "Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "Notify")
    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
        this.notify = notify;
    }

    @Basic
    @Column(name = "ReqType")
    public Integer getReqType() {
        return reqType;
    }

    public void setReqType(Integer reqType) {
        this.reqType = reqType;
    }

    @Basic
    @Column(name = "OriginType")
    public String getOriginType() {
        return originType;
    }

    public void setOriginType(String originType) {
        this.originType = originType;
    }

    @Basic
    @Column(name = "IsReuseNum")
    public String getIsReuseNum() {
        return isReuseNum;
    }

    public void setIsReuseNum(String isReuseNum) {
        this.isReuseNum = isReuseNum;
    }

    @Basic
    @Column(name = "IsReuseNFN")
    public String getIsReuseNfn() {
        return isReuseNfn;
    }

    public void setIsReuseNfn(String isReuseNfn) {
        this.isReuseNfn = isReuseNfn;
    }

    @Basic
    @Column(name = "DocDlvry")
    public String getDocDlvry() {
        return docDlvry;
    }

    public void setDocDlvry(String docDlvry) {
        this.docDlvry = docDlvry;
    }

    @Basic
    @Column(name = "PaidDpm")
    public Long getPaidDpm() {
        return paidDpm;
    }

    public void setPaidDpm(Long paidDpm) {
        this.paidDpm = paidDpm;
    }

    @Basic
    @Column(name = "PaidDpmF")
    public Long getPaidDpmF() {
        return paidDpmF;
    }

    public void setPaidDpmF(Long paidDpmF) {
        this.paidDpmF = paidDpmF;
    }

    @Basic
    @Column(name = "PaidDpmS")
    public Long getPaidDpmS() {
        return paidDpmS;
    }

    public void setPaidDpmS(Long paidDpmS) {
        this.paidDpmS = paidDpmS;
    }

    @Basic
    @Column(name = "EnvTypeNFe")
    public Integer getEnvTypeNFe() {
        return envTypeNFe;
    }

    public void setEnvTypeNFe(Integer envTypeNFe) {
        this.envTypeNFe = envTypeNFe;
    }

    @Basic
    @Column(name = "AgrNo")
    public Integer getAgrNo() {
        return agrNo;
    }

    public void setAgrNo(Integer agrNo) {
        this.agrNo = agrNo;
    }

    @Basic
    @Column(name = "IsAlt")
    public String getIsAlt() {
        return isAlt;
    }

    public void setIsAlt(String isAlt) {
        this.isAlt = isAlt;
    }

    @Basic
    @Column(name = "AltBaseTyp")
    public Integer getAltBaseTyp() {
        return altBaseTyp;
    }

    public void setAltBaseTyp(Integer altBaseTyp) {
        this.altBaseTyp = altBaseTyp;
    }

    @Basic
    @Column(name = "AltBaseEnt")
    public Integer getAltBaseEnt() {
        return altBaseEnt;
    }

    public void setAltBaseEnt(Integer altBaseEnt) {
        this.altBaseEnt = altBaseEnt;
    }

    @Basic
    @Column(name = "AuthCode")
    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    @Basic
    @Column(name = "StDlvDate")
    public Date getStDlvDate() {
        return stDlvDate;
    }

    public void setStDlvDate(Date stDlvDate) {
        this.stDlvDate = stDlvDate;
    }

    @Basic
    @Column(name = "StDlvTime")
    public Integer getStDlvTime() {
        return stDlvTime;
    }

    public void setStDlvTime(Integer stDlvTime) {
        this.stDlvTime = stDlvTime;
    }

    @Basic
    @Column(name = "EndDlvDate")
    public Date getEndDlvDate() {
        return endDlvDate;
    }

    public void setEndDlvDate(Date endDlvDate) {
        this.endDlvDate = endDlvDate;
    }

    @Basic
    @Column(name = "EndDlvTime")
    public Integer getEndDlvTime() {
        return endDlvTime;
    }

    public void setEndDlvTime(Integer endDlvTime) {
        this.endDlvTime = endDlvTime;
    }

    @Basic
    @Column(name = "VclPlate")
    public String getVclPlate() {
        return vclPlate;
    }

    public void setVclPlate(String vclPlate) {
        this.vclPlate = vclPlate;
    }

    @Basic
    @Column(name = "ElCoStatus")
    public String getElCoStatus() {
        return elCoStatus;
    }

    public void setElCoStatus(String elCoStatus) {
        this.elCoStatus = elCoStatus;
    }

    @Basic
    @Column(name = "AtDocType")
    public String getAtDocType() {
        return atDocType;
    }

    public void setAtDocType(String atDocType) {
        this.atDocType = atDocType;
    }

    @Basic
    @Column(name = "ElCoMsg")
    public String getElCoMsg() {
        return elCoMsg;
    }

    public void setElCoMsg(String elCoMsg) {
        this.elCoMsg = elCoMsg;
    }

    @Basic
    @Column(name = "PrintSEPA")
    public String getPrintSepa() {
        return printSepa;
    }

    public void setPrintSepa(String printSepa) {
        this.printSepa = printSepa;
    }

    @Basic
    @Column(name = "FreeChrg")
    public Long getFreeChrg() {
        return freeChrg;
    }

    public void setFreeChrg(Long freeChrg) {
        this.freeChrg = freeChrg;
    }

    @Basic
    @Column(name = "FreeChrgFC")
    public Long getFreeChrgFc() {
        return freeChrgFc;
    }

    public void setFreeChrgFc(Long freeChrgFc) {
        this.freeChrgFc = freeChrgFc;
    }

    @Basic
    @Column(name = "FreeChrgSC")
    public Long getFreeChrgSc() {
        return freeChrgSc;
    }

    public void setFreeChrgSc(Long freeChrgSc) {
        this.freeChrgSc = freeChrgSc;
    }

    @Basic
    @Column(name = "NfeValue")
    public Long getNfeValue() {
        return nfeValue;
    }

    public void setNfeValue(Long nfeValue) {
        this.nfeValue = nfeValue;
    }

    @Basic
    @Column(name = "FiscDocNum")
    public String getFiscDocNum() {
        return fiscDocNum;
    }

    public void setFiscDocNum(String fiscDocNum) {
        this.fiscDocNum = fiscDocNum;
    }

    @Basic
    @Column(name = "RelatedTyp")
    public Integer getRelatedTyp() {
        return relatedTyp;
    }

    public void setRelatedTyp(Integer relatedTyp) {
        this.relatedTyp = relatedTyp;
    }

    @Basic
    @Column(name = "RelatedEnt")
    public Integer getRelatedEnt() {
        return relatedEnt;
    }

    public void setRelatedEnt(Integer relatedEnt) {
        this.relatedEnt = relatedEnt;
    }

    @Basic
    @Column(name = "CCDEntry")
    public Integer getCcdEntry() {
        return ccdEntry;
    }

    public void setCcdEntry(Integer ccdEntry) {
        this.ccdEntry = ccdEntry;
    }

    @Basic
    @Column(name = "NfePrntFo")
    public Integer getNfePrntFo() {
        return nfePrntFo;
    }

    public void setNfePrntFo(Integer nfePrntFo) {
        this.nfePrntFo = nfePrntFo;
    }

    @Basic
    @Column(name = "ZrdAbs")
    public Integer getZrdAbs() {
        return zrdAbs;
    }

    public void setZrdAbs(Integer zrdAbs) {
        this.zrdAbs = zrdAbs;
    }

    @Basic
    @Column(name = "POSRcptNo")
    public Integer getPosRcptNo() {
        return posRcptNo;
    }

    public void setPosRcptNo(Integer posRcptNo) {
        this.posRcptNo = posRcptNo;
    }

    @Basic
    @Column(name = "FoCTax")
    public Long getFoCTax() {
        return foCTax;
    }

    public void setFoCTax(Long foCTax) {
        this.foCTax = foCTax;
    }

    @Basic
    @Column(name = "FoCTaxFC")
    public Long getFoCTaxFc() {
        return foCTaxFc;
    }

    public void setFoCTaxFc(Long foCTaxFc) {
        this.foCTaxFc = foCTaxFc;
    }

    @Basic
    @Column(name = "FoCTaxSC")
    public Long getFoCTaxSc() {
        return foCTaxSc;
    }

    public void setFoCTaxSc(Long foCTaxSc) {
        this.foCTaxSc = foCTaxSc;
    }

    @Basic
    @Column(name = "TpCusPres")
    public Integer getTpCusPres() {
        return tpCusPres;
    }

    public void setTpCusPres(Integer tpCusPres) {
        this.tpCusPres = tpCusPres;
    }

    @Basic
    @Column(name = "ExcDocDate")
    public Date getExcDocDate() {
        return excDocDate;
    }

    public void setExcDocDate(Date excDocDate) {
        this.excDocDate = excDocDate;
    }

    @Basic
    @Column(name = "FoCFrght")
    public Long getFoCFrght() {
        return foCFrght;
    }

    public void setFoCFrght(Long foCFrght) {
        this.foCFrght = foCFrght;
    }

    @Basic
    @Column(name = "FoCFrghtFC")
    public Long getFoCFrghtFc() {
        return foCFrghtFc;
    }

    public void setFoCFrghtFc(Long foCFrghtFc) {
        this.foCFrghtFc = foCFrghtFc;
    }

    @Basic
    @Column(name = "FoCFrghtSC")
    public Long getFoCFrghtSc() {
        return foCFrghtSc;
    }

    public void setFoCFrghtSc(Long foCFrghtSc) {
        this.foCFrghtSc = foCFrghtSc;
    }

    @Basic
    @Column(name = "InterimTyp")
    public Short getInterimTyp() {
        return interimTyp;
    }

    public void setInterimTyp(Short interimTyp) {
        this.interimTyp = interimTyp;
    }

    @Basic
    @Column(name = "PTICode")
    public String getPtiCode() {
        return ptiCode;
    }

    public void setPtiCode(String ptiCode) {
        this.ptiCode = ptiCode;
    }

    @Basic
    @Column(name = "Letter")
    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    @Basic
    @Column(name = "FolNumFrom")
    public Integer getFolNumFrom() {
        return folNumFrom;
    }

    public void setFolNumFrom(Integer folNumFrom) {
        this.folNumFrom = folNumFrom;
    }

    @Basic
    @Column(name = "FolNumTo")
    public Integer getFolNumTo() {
        return folNumTo;
    }

    public void setFolNumTo(Integer folNumTo) {
        this.folNumTo = folNumTo;
    }

    @Basic
    @Column(name = "FolSeries")
    public Integer getFolSeries() {
        return folSeries;
    }

    public void setFolSeries(Integer folSeries) {
        this.folSeries = folSeries;
    }

    @Basic
    @Column(name = "SplitTax")
    public Long getSplitTax() {
        return splitTax;
    }

    public void setSplitTax(Long splitTax) {
        this.splitTax = splitTax;
    }

    @Basic
    @Column(name = "SplitTaxFC")
    public Long getSplitTaxFc() {
        return splitTaxFc;
    }

    public void setSplitTaxFc(Long splitTaxFc) {
        this.splitTaxFc = splitTaxFc;
    }

    @Basic
    @Column(name = "SplitTaxSC")
    public Long getSplitTaxSc() {
        return splitTaxSc;
    }

    public void setSplitTaxSc(Long splitTaxSc) {
        this.splitTaxSc = splitTaxSc;
    }

    @Basic
    @Column(name = "ToBinCode")
    public String getToBinCode() {
        return toBinCode;
    }

    public void setToBinCode(String toBinCode) {
        this.toBinCode = toBinCode;
    }

    @Basic
    @Column(name = "PermitNo")
    public String getPermitNo() {
        return permitNo;
    }

    public void setPermitNo(String permitNo) {
        this.permitNo = permitNo;
    }

    @Basic
    @Column(name = "MYFtype")
    public String getMyFtype() {
        return myFtype;
    }

    public void setMyFtype(String myFtype) {
        this.myFtype = myFtype;
    }

    @Basic
    @Column(name = "PoDropPrss")
    public String getPoDropPrss() {
        return poDropPrss;
    }

    public void setPoDropPrss(String poDropPrss) {
        this.poDropPrss = poDropPrss;
    }

    @Basic
    @Column(name = "DocTaxID")
    public String getDocTaxId() {
        return docTaxId;
    }

    public void setDocTaxId(String docTaxId) {
        this.docTaxId = docTaxId;
    }

    @Basic
    @Column(name = "DateReport")
    public Date getDateReport() {
        return dateReport;
    }

    public void setDateReport(Date dateReport) {
        this.dateReport = dateReport;
    }

    @Basic
    @Column(name = "RepSection")
    public String getRepSection() {
        return repSection;
    }

    public void setRepSection(String repSection) {
        this.repSection = repSection;
    }

    @Basic
    @Column(name = "ExclTaxRep")
    public String getExclTaxRep() {
        return exclTaxRep;
    }

    public void setExclTaxRep(String exclTaxRep) {
        this.exclTaxRep = exclTaxRep;
    }

    @Basic
    @Column(name = "PosCashReg")
    public Integer getPosCashReg() {
        return posCashReg;
    }

    public void setPosCashReg(Integer posCashReg) {
        this.posCashReg = posCashReg;
    }

    @Basic
    @Column(name = "DmpTransID")
    public String getDmpTransId() {
        return dmpTransId;
    }

    public void setDmpTransId(String dmpTransId) {
        this.dmpTransId = dmpTransId;
    }

    @Basic
    @Column(name = "ECommerBP")
    public String geteCommerBp() {
        return eCommerBp;
    }

    public void seteCommerBp(String eCommerBp) {
        this.eCommerBp = eCommerBp;
    }

    @Basic
    @Column(name = "EComerGSTN")
    public String geteComerGstn() {
        return eComerGstn;
    }

    public void seteComerGstn(String eComerGstn) {
        this.eComerGstn = eComerGstn;
    }

    @Basic
    @Column(name = "Revision")
    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    @Basic
    @Column(name = "RevRefNo")
    public String getRevRefNo() {
        return revRefNo;
    }

    public void setRevRefNo(String revRefNo) {
        this.revRefNo = revRefNo;
    }

    @Basic
    @Column(name = "RevRefDate")
    public Date getRevRefDate() {
        return revRefDate;
    }

    public void setRevRefDate(Date revRefDate) {
        this.revRefDate = revRefDate;
    }

    @Basic
    @Column(name = "RevCreRefN")
    public String getRevCreRefN() {
        return revCreRefN;
    }

    public void setRevCreRefN(String revCreRefN) {
        this.revCreRefN = revCreRefN;
    }

    @Basic
    @Column(name = "RevCreRefD")
    public Date getRevCreRefD() {
        return revCreRefD;
    }

    public void setRevCreRefD(Date revCreRefD) {
        this.revCreRefD = revCreRefD;
    }

    @Basic
    @Column(name = "TaxInvNo")
    public String getTaxInvNo() {
        return taxInvNo;
    }

    public void setTaxInvNo(String taxInvNo) {
        this.taxInvNo = taxInvNo;
    }

    @Basic
    @Column(name = "FrmBpDate")
    public Date getFrmBpDate() {
        return frmBpDate;
    }

    public void setFrmBpDate(Date frmBpDate) {
        this.frmBpDate = frmBpDate;
    }

    @Basic
    @Column(name = "GSTTranTyp")
    public String getGstTranTyp() {
        return gstTranTyp;
    }

    public void setGstTranTyp(String gstTranTyp) {
        this.gstTranTyp = gstTranTyp;
    }

    @Basic
    @Column(name = "BaseType")
    public Integer getBaseType() {
        return baseType;
    }

    public void setBaseType(Integer baseType) {
        this.baseType = baseType;
    }

    @Basic
    @Column(name = "BaseEntry")
    public Integer getBaseEntry() {
        return baseEntry;
    }

    public void setBaseEntry(Integer baseEntry) {
        this.baseEntry = baseEntry;
    }

    @Basic
    @Column(name = "U_CodeSerial")
    public String getuCodeSerial() {
        return uCodeSerial;
    }

    public void setuCodeSerial(String uCodeSerial) {
        this.uCodeSerial = uCodeSerial;
    }

    @Basic
    @Column(name = "U_CodeInv")
    public String getuCodeInv() {
        return uCodeInv;
    }

    public void setuCodeInv(String uCodeInv) {
        this.uCodeInv = uCodeInv;
    }

    @Basic
    @Column(name = "U_InvSerial")
    public String getuInvSerial() {
        return uInvSerial;
    }

    public void setuInvSerial(String uInvSerial) {
        this.uInvSerial = uInvSerial;
    }

    @Basic
    @Column(name = "U_InvCode")
    public String getuInvCode() {
        return uInvCode;
    }

    public void setuInvCode(String uInvCode) {
        this.uInvCode = uInvCode;
    }

    @Basic
    @Column(name = "U_DeclareStat")
    public String getuDeclareStat() {
        return uDeclareStat;
    }

    public void setuDeclareStat(String uDeclareStat) {
        this.uDeclareStat = uDeclareStat;
    }

    @Basic
    @Column(name = "U_CoName")
    public String getuCoName() {
        return uCoName;
    }

    public void setuCoName(String uCoName) {
        this.uCoName = uCoName;
    }

    @Basic
    @Column(name = "U_CoAdd")
    public String getuCoAdd() {
        return uCoAdd;
    }

    public void setuCoAdd(String uCoAdd) {
        this.uCoAdd = uCoAdd;
    }

    @Basic
    @Column(name = "U_CoTaxNo")
    public String getuCoTaxNo() {
        return uCoTaxNo;
    }

    public void setuCoTaxNo(String uCoTaxNo) {
        this.uCoTaxNo = uCoTaxNo;
    }

    @Basic
    @Column(name = "U_Docnum")
    public String getuDocnum() {
        return uDocnum;
    }

    public void setuDocnum(String uDocnum) {
        this.uDocnum = uDocnum;
    }

    @Basic
    @Column(name = "U_Description_vn")
    public String getuDescriptionVn() {
        return uDescriptionVn;
    }

    public void setuDescriptionVn(String uDescriptionVn) {
        this.uDescriptionVn = uDescriptionVn;
    }

    @Basic
    @Column(name = "U_Pur_NVGiao")
    public String getuPurNvGiao() {
        return uPurNvGiao;
    }

    public void setuPurNvGiao(String uPurNvGiao) {
        this.uPurNvGiao = uPurNvGiao;
    }

    @Basic
    @Column(name = "U_DeliveryTime")
    public Short getuDeliveryTime() {
        return uDeliveryTime;
    }

    public void setuDeliveryTime(Short uDeliveryTime) {
        this.uDeliveryTime = uDeliveryTime;
    }

    @Basic
    @Column(name = "U_InvRemark")
    public String getuInvRemark() {
        return uInvRemark;
    }

    public void setuInvRemark(String uInvRemark) {
        this.uInvRemark = uInvRemark;
    }

    @Basic
    @Column(name = "U_hrcode")
    public String getuHrcode() {
        return uHrcode;
    }

    public void setuHrcode(String uHrcode) {
        this.uHrcode = uHrcode;
    }

    @Basic
    @Column(name = "U_hrname")
    public String getuHrname() {
        return uHrname;
    }

    public void setuHrname(String uHrname) {
        this.uHrname = uHrname;
    }

    @Basic
    @Column(name = "U_HT")
    public String getuHt() {
        return uHt;
    }

    public void setuHt(String uHt) {
        this.uHt = uHt;
    }

    @Basic
    @Column(name = "U_VC")
    public String getuVc() {
        return uVc;
    }

    public void setuVc(String uVc) {
        this.uVc = uVc;
    }

    @Basic
    @Column(name = "U_GRPO")
    public Integer getuGrpo() {
        return uGrpo;
    }

    public void setuGrpo(Integer uGrpo) {
        this.uGrpo = uGrpo;
    }

    @Basic
    @Column(name = "U_Payment")
    public String getuPayment() {
        return uPayment;
    }

    public void setuPayment(String uPayment) {
        this.uPayment = uPayment;
    }

    @Basic
    @Column(name = "U_Production")
    public Integer getuProduction() {
        return uProduction;
    }

    public void setuProduction(Integer uProduction) {
        this.uProduction = uProduction;
    }

    @Basic
    @Column(name = "U_Batch")
    public String getuBatch() {
        return uBatch;
    }

    public void setuBatch(String uBatch) {
        this.uBatch = uBatch;
    }

    @Basic
    @Column(name = "U_DChuyen")
    public String getuDChuyen() {
        return uDChuyen;
    }

    public void setuDChuyen(String uDChuyen) {
        this.uDChuyen = uDChuyen;
    }

    @Basic
    @Column(name = "U_InvCode2")
    public String getuInvCode2() {
        return uInvCode2;
    }

    public void setuInvCode2(String uInvCode2) {
        this.uInvCode2 = uInvCode2;
    }

    @Basic
    @Column(name = "U_Manufacturer")
    public String getuManufacturer() {
        return uManufacturer;
    }

    public void setuManufacturer(String uManufacturer) {
        this.uManufacturer = uManufacturer;
    }

    @Basic
    @Column(name = "U_approval")
    public String getuApproval() {
        return uApproval;
    }

    public void setuApproval(String uApproval) {
        this.uApproval = uApproval;
    }

    @Basic
    @Column(name = "U_GoodReceipt")
    public Integer getuGoodReceipt() {
        return uGoodReceipt;
    }

    public void setuGoodReceipt(Integer uGoodReceipt) {
        this.uGoodReceipt = uGoodReceipt;
    }

    @Basic
    @Column(name = "U_CheckQC")
    public Integer getuCheckQc() {
        return uCheckQc;
    }

    public void setuCheckQc(Integer uCheckQc) {
        this.uCheckQc = uCheckQc;
    }

    @Basic
    @Column(name = "U_Pur_NVNhan")
    public String getuPurNvNhan() {
        return uPurNvNhan;
    }

    public void setuPurNvNhan(String uPurNvNhan) {
        this.uPurNvNhan = uPurNvNhan;
    }

    @Basic
    @Column(name = "U_printed")
    public String getuPrinted() {
        return uPrinted;
    }

    public void setuPrinted(String uPrinted) {
        this.uPrinted = uPrinted;
    }

    @Basic
    @Column(name = "U_cknb")
    public String getuCknb() {
        return uCknb;
    }

    public void setuCknb(String uCknb) {
        this.uCknb = uCknb;
    }

    @Basic
    @Column(name = "U_Category")
    public String getuCategory() {
        return uCategory;
    }

    public void setuCategory(String uCategory) {
        this.uCategory = uCategory;
    }

    @Basic
    @Column(name = "U_TransNum")
    public String getuTransNum() {
        return uTransNum;
    }

    public void setuTransNum(String uTransNum) {
        this.uTransNum = uTransNum;
    }

    @Basic
    @Column(name = "U_DeclarePd")
    public String getuDeclarePd() {
        return uDeclarePd;
    }

    public void setuDeclarePd(String uDeclarePd) {
        this.uDeclarePd = uDeclarePd;
    }

    @Basic
    @Column(name = "U_So_PX")
    public String getuSoPx() {
        return uSoPx;
    }

    public void setuSoPx(String uSoPx) {
        this.uSoPx = uSoPx;
    }

    @Basic
    @Column(name = "U_ContractDate")
    public String getuContractDate() {
        return uContractDate;
    }

    public void setuContractDate(String uContractDate) {
        this.uContractDate = uContractDate;
    }

    @Basic
    @Column(name = "U_Line")
    public String getuLine() {
        return uLine;
    }

    public void setuLine(String uLine) {
        this.uLine = uLine;
    }

    @Basic
    @Column(name = "U_Whscode")
    public String getuWhscode() {
        return uWhscode;
    }

    public void setuWhscode(String uWhscode) {
        this.uWhscode = uWhscode;
    }

    @Basic
    @Column(name = "U_SendToHO")
    public Integer getuSendToHo() {
        return uSendToHo;
    }

    public void setuSendToHo(Integer uSendToHo) {
        this.uSendToHo = uSendToHo;
    }

    @Basic
    @Column(name = "U_DPM")
    public Long getuDpm() {
        return uDpm;
    }

    public void setuDpm(Long uDpm) {
        this.uDpm = uDpm;
    }

    @Basic
    @Column(name = "U_DocOrg")
    public String getuDocOrg() {
        return uDocOrg;
    }

    public void setuDocOrg(String uDocOrg) {
        this.uDocOrg = uDocOrg;
    }

    @Basic
    @Column(name = "U_DateDeb")
    public Date getuDateDeb() {
        return uDateDeb;
    }

    public void setuDateDeb(Date uDateDeb) {
        this.uDateDeb = uDateDeb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OprqEntity that = (OprqEntity) o;
        return docEntry == that.docEntry && docNum == that.docNum && instance == that.instance && segment == that.segment && Objects.equals(docType, that.docType) && Objects.equals(canceled, that.canceled) && Objects.equals(handwrtten, that.handwrtten) && Objects.equals(printed, that.printed) && Objects.equals(docStatus, that.docStatus) && Objects.equals(invntSttus, that.invntSttus) && Objects.equals(transfered, that.transfered) && Objects.equals(objType, that.objType) && Objects.equals(docDate, that.docDate) && Objects.equals(docDueDate, that.docDueDate) && Objects.equals(cardCode, that.cardCode) && Objects.equals(cardName, that.cardName) && Objects.equals(address, that.address) && Objects.equals(numAtCard, that.numAtCard) && Objects.equals(vatPercent, that.vatPercent) && Objects.equals(vatSum, that.vatSum) && Objects.equals(vatSumFc, that.vatSumFc) && Objects.equals(discPrcnt, that.discPrcnt) && Objects.equals(discSum, that.discSum) && Objects.equals(discSumFc, that.discSumFc) && Objects.equals(docCur, that.docCur) && Objects.equals(docRate, that.docRate) && Objects.equals(docTotal, that.docTotal) && Objects.equals(docTotalFc, that.docTotalFc) && Objects.equals(paidToDate, that.paidToDate) && Objects.equals(paidFc, that.paidFc) && Objects.equals(grosProfit, that.grosProfit) && Objects.equals(grosProfFc, that.grosProfFc) && Objects.equals(ref1, that.ref1) && Objects.equals(ref2, that.ref2) && Objects.equals(comments, that.comments) && Objects.equals(jrnlMemo, that.jrnlMemo) && Objects.equals(transId, that.transId) && Objects.equals(receiptNum, that.receiptNum) && Objects.equals(groupNum, that.groupNum) && Objects.equals(docTime, that.docTime) && Objects.equals(slpCode, that.slpCode) && Objects.equals(trnspCode, that.trnspCode) && Objects.equals(partSupply, that.partSupply) && Objects.equals(confirmed, that.confirmed) && Objects.equals(grossBase, that.grossBase) && Objects.equals(importEnt, that.importEnt) && Objects.equals(createTran, that.createTran) && Objects.equals(summryType, that.summryType) && Objects.equals(updInvnt, that.updInvnt) && Objects.equals(updCardBal, that.updCardBal) && Objects.equals(flags, that.flags) && Objects.equals(invntDirec, that.invntDirec) && Objects.equals(cntctCode, that.cntctCode) && Objects.equals(showScn, that.showScn) && Objects.equals(fatherCard, that.fatherCard) && Objects.equals(sysRate, that.sysRate) && Objects.equals(curSource, that.curSource) && Objects.equals(vatSumSy, that.vatSumSy) && Objects.equals(discSumSy, that.discSumSy) && Objects.equals(docTotalSy, that.docTotalSy) && Objects.equals(paidSys, that.paidSys) && Objects.equals(fatherType, that.fatherType) && Objects.equals(grosProfSy, that.grosProfSy) && Objects.equals(updateDate, that.updateDate) && Objects.equals(isIct, that.isIct) && Objects.equals(createDate, that.createDate) && Objects.equals(volume, that.volume) && Objects.equals(volUnit, that.volUnit) && Objects.equals(weight, that.weight) && Objects.equals(weightUnit, that.weightUnit) && Objects.equals(series, that.series) && Objects.equals(taxDate, that.taxDate) && Objects.equals(filler, that.filler) && Objects.equals(dataSource, that.dataSource) && Objects.equals(stampNum, that.stampNum) && Objects.equals(isCrin, that.isCrin) && Objects.equals(finncPriod, that.finncPriod) && Objects.equals(userSign, that.userSign) && Objects.equals(selfInv, that.selfInv) && Objects.equals(vatPaid, that.vatPaid) && Objects.equals(vatPaidFc, that.vatPaidFc) && Objects.equals(vatPaidSys, that.vatPaidSys) && Objects.equals(userSign2, that.userSign2) && Objects.equals(wddStatus, that.wddStatus) && Objects.equals(draftKey, that.draftKey) && Objects.equals(totalExpns, that.totalExpns) && Objects.equals(totalExpFc, that.totalExpFc) && Objects.equals(totalExpSc, that.totalExpSc) && Objects.equals(dunnLevel, that.dunnLevel) && Objects.equals(address2, that.address2) && Objects.equals(logInstanc, that.logInstanc) && Objects.equals(exported, that.exported) && Objects.equals(stationId, that.stationId) && Objects.equals(indicator, that.indicator) && Objects.equals(netProc, that.netProc) && Objects.equals(aqcsTax, that.aqcsTax) && Objects.equals(aqcsTaxFc, that.aqcsTaxFc) && Objects.equals(aqcsTaxSc, that.aqcsTaxSc) && Objects.equals(cashDiscPr, that.cashDiscPr) && Objects.equals(cashDiscnt, that.cashDiscnt) && Objects.equals(cashDiscFc, that.cashDiscFc) && Objects.equals(cashDiscSc, that.cashDiscSc) && Objects.equals(shipToCode, that.shipToCode) && Objects.equals(licTradNum, that.licTradNum) && Objects.equals(paymentRef, that.paymentRef) && Objects.equals(wtSum, that.wtSum) && Objects.equals(wtSumFc, that.wtSumFc) && Objects.equals(wtSumSc, that.wtSumSc) && Objects.equals(roundDif, that.roundDif) && Objects.equals(roundDifFc, that.roundDifFc) && Objects.equals(roundDifSy, that.roundDifSy) && Objects.equals(checkDigit, that.checkDigit) && Objects.equals(form1099, that.form1099) && Objects.equals(box1099, that.box1099) && Objects.equals(submitted, that.submitted) && Objects.equals(poPrss, that.poPrss) && Objects.equals(rounding, that.rounding) && Objects.equals(revisionPo, that.revisionPo) && Objects.equals(reqDate, that.reqDate) && Objects.equals(cancelDate, that.cancelDate) && Objects.equals(pickStatus, that.pickStatus) && Objects.equals(pick, that.pick) && Objects.equals(blockDunn, that.blockDunn) && Objects.equals(peyMethod, that.peyMethod) && Objects.equals(payBlock, that.payBlock) && Objects.equals(payBlckRef, that.payBlckRef) && Objects.equals(maxDscn, that.maxDscn) && Objects.equals(reserve, that.reserve) && Objects.equals(max1099, that.max1099) && Objects.equals(cntrlBnk, that.cntrlBnk) && Objects.equals(pickRmrk, that.pickRmrk) && Objects.equals(isrCodLine, that.isrCodLine) && Objects.equals(expAppl, that.expAppl) && Objects.equals(expApplFc, that.expApplFc) && Objects.equals(expApplSc, that.expApplSc) && Objects.equals(project, that.project) && Objects.equals(deferrTax, that.deferrTax) && Objects.equals(letterNum, that.letterNum) && Objects.equals(fromDate, that.fromDate) && Objects.equals(toDate, that.toDate) && Objects.equals(wtApplied, that.wtApplied) && Objects.equals(wtAppliedF, that.wtAppliedF) && Objects.equals(boeReserev, that.boeReserev) && Objects.equals(agentCode, that.agentCode) && Objects.equals(wtAppliedS, that.wtAppliedS) && Objects.equals(equVatSum, that.equVatSum) && Objects.equals(equVatSumF, that.equVatSumF) && Objects.equals(equVatSumS, that.equVatSumS) && Objects.equals(installmnt, that.installmnt) && Objects.equals(vatFirst, that.vatFirst) && Objects.equals(nnSbAmnt, that.nnSbAmnt) && Objects.equals(nnSbAmntSc, that.nnSbAmntSc) && Objects.equals(nbSbAmntFc, that.nbSbAmntFc) && Objects.equals(exepAmnt, that.exepAmnt) && Objects.equals(exepAmntSc, that.exepAmntSc) && Objects.equals(exepAmntFc, that.exepAmntFc) && Objects.equals(vatDate, that.vatDate) && Objects.equals(corrExt, that.corrExt) && Objects.equals(corrInv, that.corrInv) && Objects.equals(nCorrInv, that.nCorrInv) && Objects.equals(ceecFlag, that.ceecFlag) && Objects.equals(baseAmnt, that.baseAmnt) && Objects.equals(baseAmntSc, that.baseAmntSc) && Objects.equals(baseAmntFc, that.baseAmntFc) && Objects.equals(ctlAccount, that.ctlAccount) && Objects.equals(bplId, that.bplId) && Objects.equals(bplName, that.bplName) && Objects.equals(vatRegNum, that.vatRegNum) && Objects.equals(txInvRptNo, that.txInvRptNo) && Objects.equals(txInvRptDt, that.txInvRptDt) && Objects.equals(kvvatCode, that.kvvatCode) && Objects.equals(wtDetails, that.wtDetails) && Objects.equals(sumAbsId, that.sumAbsId) && Objects.equals(sumRptDate, that.sumRptDate) && Objects.equals(pIndicator, that.pIndicator) && Objects.equals(manualNum, that.manualNum) && Objects.equals(useShpdGd, that.useShpdGd) && Objects.equals(baseVtAt, that.baseVtAt) && Objects.equals(baseVtAtSc, that.baseVtAtSc) && Objects.equals(baseVtAtFc, that.baseVtAtFc) && Objects.equals(nnSbVAt, that.nnSbVAt) && Objects.equals(nnSbVAtSc, that.nnSbVAtSc) && Objects.equals(nbSbVAtFc, that.nbSbVAtFc) && Objects.equals(exptVAt, that.exptVAt) && Objects.equals(exptVAtSc, that.exptVAtSc) && Objects.equals(exptVAtFc, that.exptVAtFc) && Objects.equals(lyPmtAt, that.lyPmtAt) && Objects.equals(lyPmtAtSc, that.lyPmtAtSc) && Objects.equals(lyPmtAtFc, that.lyPmtAtFc) && Objects.equals(expAnSum, that.expAnSum) && Objects.equals(expAnSys, that.expAnSys) && Objects.equals(expAnFrgn, that.expAnFrgn) && Objects.equals(docSubType, that.docSubType) && Objects.equals(dpmStatus, that.dpmStatus) && Objects.equals(dpmAmnt, that.dpmAmnt) && Objects.equals(dpmAmntSc, that.dpmAmntSc) && Objects.equals(dpmAmntFc, that.dpmAmntFc) && Objects.equals(dpmDrawn, that.dpmDrawn) && Objects.equals(dpmPrcnt, that.dpmPrcnt) && Objects.equals(paidSum, that.paidSum) && Objects.equals(paidSumFc, that.paidSumFc) && Objects.equals(paidSumSc, that.paidSumSc) && Objects.equals(folioPref, that.folioPref) && Objects.equals(folioNum, that.folioNum) && Objects.equals(dpmAppl, that.dpmAppl) && Objects.equals(dpmApplFc, that.dpmApplFc) && Objects.equals(dpmApplSc, that.dpmApplSc) && Objects.equals(lPgFolioN, that.lPgFolioN) && Objects.equals(header, that.header) && Objects.equals(footer, that.footer) && Objects.equals(posted, that.posted) && Objects.equals(ownerCode, that.ownerCode) && Objects.equals(bpChCode, that.bpChCode) && Objects.equals(bpChCntc, that.bpChCntc) && Objects.equals(payToCode, that.payToCode) && Objects.equals(isPaytoBnk, that.isPaytoBnk) && Objects.equals(bnkCntry, that.bnkCntry) && Objects.equals(bankCode, that.bankCode) && Objects.equals(bnkAccount, that.bnkAccount) && Objects.equals(bnkBranch, that.bnkBranch) && Objects.equals(isIns, that.isIns) && Objects.equals(trackNo, that.trackNo) && Objects.equals(versionNum, that.versionNum) && Objects.equals(langCode, that.langCode) && Objects.equals(bpNameOw, that.bpNameOw) && Objects.equals(billToOw, that.billToOw) && Objects.equals(shipToOw, that.shipToOw) && Objects.equals(retInvoice, that.retInvoice) && Objects.equals(clsDate, that.clsDate) && Objects.equals(mInvNum, that.mInvNum) && Objects.equals(mInvDate, that.mInvDate) && Objects.equals(seqCode, that.seqCode) && Objects.equals(serial, that.serial) && Objects.equals(seriesStr, that.seriesStr) && Objects.equals(subStr, that.subStr) && Objects.equals(model, that.model) && Objects.equals(taxOnExp, that.taxOnExp) && Objects.equals(taxOnExpFc, that.taxOnExpFc) && Objects.equals(taxOnExpSc, that.taxOnExpSc) && Objects.equals(taxOnExAp, that.taxOnExAp) && Objects.equals(taxOnExApF, that.taxOnExApF) && Objects.equals(taxOnExApS, that.taxOnExApS) && Objects.equals(lastPmnTyp, that.lastPmnTyp) && Objects.equals(lndCstNum, that.lndCstNum) && Objects.equals(useCorrVat, that.useCorrVat) && Objects.equals(blkCredMmo, that.blkCredMmo) && Objects.equals(openForLaC, that.openForLaC) && Objects.equals(excised, that.excised) && Objects.equals(excRefDate, that.excRefDate) && Objects.equals(excRmvTime, that.excRmvTime) && Objects.equals(srvGpPrcnt, that.srvGpPrcnt) && Objects.equals(depositNum, that.depositNum) && Objects.equals(certNum, that.certNum) && Objects.equals(dutyStatus, that.dutyStatus) && Objects.equals(autoCrtFlw, that.autoCrtFlw) && Objects.equals(flwRefDate, that.flwRefDate) && Objects.equals(flwRefNum, that.flwRefNum) && Objects.equals(vatJeNum, that.vatJeNum) && Objects.equals(dpmVat, that.dpmVat) && Objects.equals(dpmVatFc, that.dpmVatFc) && Objects.equals(dpmVatSc, that.dpmVatSc) && Objects.equals(dpmAppVat, that.dpmAppVat) && Objects.equals(dpmAppVatF, that.dpmAppVatF) && Objects.equals(dpmAppVatS, that.dpmAppVatS) && Objects.equals(insurOp347, that.insurOp347) && Objects.equals(ignRelDoc, that.ignRelDoc) && Objects.equals(buildDesc, that.buildDesc) && Objects.equals(residenNum, that.residenNum) && Objects.equals(checker, that.checker) && Objects.equals(payee, that.payee) && Objects.equals(copyNumber, that.copyNumber) && Objects.equals(ssiExmpt, that.ssiExmpt) && Objects.equals(pqtGrpSer, that.pqtGrpSer) && Objects.equals(pqtGrpNum, that.pqtGrpNum) && Objects.equals(pqtGrpHw, that.pqtGrpHw) && Objects.equals(reopOriDoc, that.reopOriDoc) && Objects.equals(reopManCls, that.reopManCls) && Objects.equals(docManClsd, that.docManClsd) && Objects.equals(closingOpt, that.closingOpt) && Objects.equals(specDate, that.specDate) && Objects.equals(ordered, that.ordered) && Objects.equals(ntsApprov, that.ntsApprov) && Objects.equals(ntsWebSite, that.ntsWebSite) && Objects.equals(ntSeTaxNo, that.ntSeTaxNo) && Objects.equals(ntsApprNo, that.ntsApprNo) && Objects.equals(payDuMonth, that.payDuMonth) && Objects.equals(extraMonth, that.extraMonth) && Objects.equals(extraDays, that.extraDays) && Objects.equals(cdcOffset, that.cdcOffset) && Objects.equals(signMsg, that.signMsg) && Objects.equals(signDigest, that.signDigest) && Objects.equals(certifNum, that.certifNum) && Objects.equals(keyVersion, that.keyVersion) && Objects.equals(eDocGenTyp, that.eDocGenTyp) && Objects.equals(eSeries, that.eSeries) && Objects.equals(eDocNum, that.eDocNum) && Objects.equals(eDocExpFrm, that.eDocExpFrm) && Objects.equals(onlineQuo, that.onlineQuo) && Objects.equals(posEqNum, that.posEqNum) && Objects.equals(posManufSn, that.posManufSn) && Objects.equals(posCashN, that.posCashN) && Objects.equals(eDocStatus, that.eDocStatus) && Objects.equals(eDocCntnt, that.eDocCntnt) && Objects.equals(eDocProces, that.eDocProces) && Objects.equals(eDocErrCod, that.eDocErrCod) && Objects.equals(eDocErrMsg, that.eDocErrMsg) && Objects.equals(eDocCancel, that.eDocCancel) && Objects.equals(eDocTest, that.eDocTest) && Objects.equals(eDocPrefix, that.eDocPrefix) && Objects.equals(cup, that.cup) && Objects.equals(cig, that.cig) && Objects.equals(dpmAsDscnt, that.dpmAsDscnt) && Objects.equals(attachment, that.attachment) && Objects.equals(atcEntry, that.atcEntry) && Objects.equals(supplCode, that.supplCode) && Objects.equals(gtsRlvnt, that.gtsRlvnt) && Objects.equals(baseDisc, that.baseDisc) && Objects.equals(baseDiscSc, that.baseDiscSc) && Objects.equals(baseDiscFc, that.baseDiscFc) && Objects.equals(baseDiscPr, that.baseDiscPr) && Objects.equals(createTs, that.createTs) && Objects.equals(updateTs, that.updateTs) && Objects.equals(srvTaxRule, that.srvTaxRule) && Objects.equals(annInvDecR, that.annInvDecR) && Objects.equals(supplier, that.supplier) && Objects.equals(releaser, that.releaser) && Objects.equals(receiver, that.receiver) && Objects.equals(toWhsCode, that.toWhsCode) && Objects.equals(assetDate, that.assetDate) && Objects.equals(requester, that.requester) && Objects.equals(reqName, that.reqName) && Objects.equals(branch, that.branch) && Objects.equals(department, that.department) && Objects.equals(email, that.email) && Objects.equals(notify, that.notify) && Objects.equals(reqType, that.reqType) && Objects.equals(originType, that.originType) && Objects.equals(isReuseNum, that.isReuseNum) && Objects.equals(isReuseNfn, that.isReuseNfn) && Objects.equals(docDlvry, that.docDlvry) && Objects.equals(paidDpm, that.paidDpm) && Objects.equals(paidDpmF, that.paidDpmF) && Objects.equals(paidDpmS, that.paidDpmS) && Objects.equals(envTypeNFe, that.envTypeNFe) && Objects.equals(agrNo, that.agrNo) && Objects.equals(isAlt, that.isAlt) && Objects.equals(altBaseTyp, that.altBaseTyp) && Objects.equals(altBaseEnt, that.altBaseEnt) && Objects.equals(authCode, that.authCode) && Objects.equals(stDlvDate, that.stDlvDate) && Objects.equals(stDlvTime, that.stDlvTime) && Objects.equals(endDlvDate, that.endDlvDate) && Objects.equals(endDlvTime, that.endDlvTime) && Objects.equals(vclPlate, that.vclPlate) && Objects.equals(elCoStatus, that.elCoStatus) && Objects.equals(atDocType, that.atDocType) && Objects.equals(elCoMsg, that.elCoMsg) && Objects.equals(printSepa, that.printSepa) && Objects.equals(freeChrg, that.freeChrg) && Objects.equals(freeChrgFc, that.freeChrgFc) && Objects.equals(freeChrgSc, that.freeChrgSc) && Objects.equals(nfeValue, that.nfeValue) && Objects.equals(fiscDocNum, that.fiscDocNum) && Objects.equals(relatedTyp, that.relatedTyp) && Objects.equals(relatedEnt, that.relatedEnt) && Objects.equals(ccdEntry, that.ccdEntry) && Objects.equals(nfePrntFo, that.nfePrntFo) && Objects.equals(zrdAbs, that.zrdAbs) && Objects.equals(posRcptNo, that.posRcptNo) && Objects.equals(foCTax, that.foCTax) && Objects.equals(foCTaxFc, that.foCTaxFc) && Objects.equals(foCTaxSc, that.foCTaxSc) && Objects.equals(tpCusPres, that.tpCusPres) && Objects.equals(excDocDate, that.excDocDate) && Objects.equals(foCFrght, that.foCFrght) && Objects.equals(foCFrghtFc, that.foCFrghtFc) && Objects.equals(foCFrghtSc, that.foCFrghtSc) && Objects.equals(interimTyp, that.interimTyp) && Objects.equals(ptiCode, that.ptiCode) && Objects.equals(letter, that.letter) && Objects.equals(folNumFrom, that.folNumFrom) && Objects.equals(folNumTo, that.folNumTo) && Objects.equals(folSeries, that.folSeries) && Objects.equals(splitTax, that.splitTax) && Objects.equals(splitTaxFc, that.splitTaxFc) && Objects.equals(splitTaxSc, that.splitTaxSc) && Objects.equals(toBinCode, that.toBinCode) && Objects.equals(permitNo, that.permitNo) && Objects.equals(myFtype, that.myFtype) && Objects.equals(poDropPrss, that.poDropPrss) && Objects.equals(docTaxId, that.docTaxId) && Objects.equals(dateReport, that.dateReport) && Objects.equals(repSection, that.repSection) && Objects.equals(exclTaxRep, that.exclTaxRep) && Objects.equals(posCashReg, that.posCashReg) && Objects.equals(dmpTransId, that.dmpTransId) && Objects.equals(eCommerBp, that.eCommerBp) && Objects.equals(eComerGstn, that.eComerGstn) && Objects.equals(revision, that.revision) && Objects.equals(revRefNo, that.revRefNo) && Objects.equals(revRefDate, that.revRefDate) && Objects.equals(revCreRefN, that.revCreRefN) && Objects.equals(revCreRefD, that.revCreRefD) && Objects.equals(taxInvNo, that.taxInvNo) && Objects.equals(frmBpDate, that.frmBpDate) && Objects.equals(gstTranTyp, that.gstTranTyp) && Objects.equals(baseType, that.baseType) && Objects.equals(baseEntry, that.baseEntry) && Objects.equals(uCodeSerial, that.uCodeSerial) && Objects.equals(uCodeInv, that.uCodeInv) && Objects.equals(uInvSerial, that.uInvSerial) && Objects.equals(uInvCode, that.uInvCode) && Objects.equals(uDeclareStat, that.uDeclareStat) && Objects.equals(uCoName, that.uCoName) && Objects.equals(uCoAdd, that.uCoAdd) && Objects.equals(uCoTaxNo, that.uCoTaxNo) && Objects.equals(uDocnum, that.uDocnum) && Objects.equals(uDescriptionVn, that.uDescriptionVn) && Objects.equals(uPurNvGiao, that.uPurNvGiao) && Objects.equals(uDeliveryTime, that.uDeliveryTime) && Objects.equals(uInvRemark, that.uInvRemark) && Objects.equals(uHrcode, that.uHrcode) && Objects.equals(uHrname, that.uHrname) && Objects.equals(uHt, that.uHt) && Objects.equals(uVc, that.uVc) && Objects.equals(uGrpo, that.uGrpo) && Objects.equals(uPayment, that.uPayment) && Objects.equals(uProduction, that.uProduction) && Objects.equals(uBatch, that.uBatch) && Objects.equals(uDChuyen, that.uDChuyen) && Objects.equals(uInvCode2, that.uInvCode2) && Objects.equals(uManufacturer, that.uManufacturer) && Objects.equals(uApproval, that.uApproval) && Objects.equals(uGoodReceipt, that.uGoodReceipt) && Objects.equals(uCheckQc, that.uCheckQc) && Objects.equals(uPurNvNhan, that.uPurNvNhan) && Objects.equals(uPrinted, that.uPrinted) && Objects.equals(uCknb, that.uCknb) && Objects.equals(uCategory, that.uCategory) && Objects.equals(uTransNum, that.uTransNum) && Objects.equals(uDeclarePd, that.uDeclarePd) && Objects.equals(uSoPx, that.uSoPx) && Objects.equals(uContractDate, that.uContractDate) && Objects.equals(uLine, that.uLine) && Objects.equals(uWhscode, that.uWhscode) && Objects.equals(uSendToHo, that.uSendToHo) && Objects.equals(uDpm, that.uDpm) && Objects.equals(uDocOrg, that.uDocOrg) && Objects.equals(uDateDeb, that.uDateDeb);
    }

    @Override
    public int hashCode() {
        return Objects.hash(docEntry, docNum, docType, canceled, handwrtten, printed, docStatus, invntSttus, transfered, objType, docDate, docDueDate, cardCode, cardName, address, numAtCard, vatPercent, vatSum, vatSumFc, discPrcnt, discSum, discSumFc, docCur, docRate, docTotal, docTotalFc, paidToDate, paidFc, grosProfit, grosProfFc, ref1, ref2, comments, jrnlMemo, transId, receiptNum, groupNum, docTime, slpCode, trnspCode, partSupply, confirmed, grossBase, importEnt, createTran, summryType, updInvnt, updCardBal, instance, flags, invntDirec, cntctCode, showScn, fatherCard, sysRate, curSource, vatSumSy, discSumSy, docTotalSy, paidSys, fatherType, grosProfSy, updateDate, isIct, createDate, volume, volUnit, weight, weightUnit, series, taxDate, filler, dataSource, stampNum, isCrin, finncPriod, userSign, selfInv, vatPaid, vatPaidFc, vatPaidSys, userSign2, wddStatus, draftKey, totalExpns, totalExpFc, totalExpSc, dunnLevel, address2, logInstanc, exported, stationId, indicator, netProc, aqcsTax, aqcsTaxFc, aqcsTaxSc, cashDiscPr, cashDiscnt, cashDiscFc, cashDiscSc, shipToCode, licTradNum, paymentRef, wtSum, wtSumFc, wtSumSc, roundDif, roundDifFc, roundDifSy, checkDigit, form1099, box1099, submitted, poPrss, rounding, revisionPo, segment, reqDate, cancelDate, pickStatus, pick, blockDunn, peyMethod, payBlock, payBlckRef, maxDscn, reserve, max1099, cntrlBnk, pickRmrk, isrCodLine, expAppl, expApplFc, expApplSc, project, deferrTax, letterNum, fromDate, toDate, wtApplied, wtAppliedF, boeReserev, agentCode, wtAppliedS, equVatSum, equVatSumF, equVatSumS, installmnt, vatFirst, nnSbAmnt, nnSbAmntSc, nbSbAmntFc, exepAmnt, exepAmntSc, exepAmntFc, vatDate, corrExt, corrInv, nCorrInv, ceecFlag, baseAmnt, baseAmntSc, baseAmntFc, ctlAccount, bplId, bplName, vatRegNum, txInvRptNo, txInvRptDt, kvvatCode, wtDetails, sumAbsId, sumRptDate, pIndicator, manualNum, useShpdGd, baseVtAt, baseVtAtSc, baseVtAtFc, nnSbVAt, nnSbVAtSc, nbSbVAtFc, exptVAt, exptVAtSc, exptVAtFc, lyPmtAt, lyPmtAtSc, lyPmtAtFc, expAnSum, expAnSys, expAnFrgn, docSubType, dpmStatus, dpmAmnt, dpmAmntSc, dpmAmntFc, dpmDrawn, dpmPrcnt, paidSum, paidSumFc, paidSumSc, folioPref, folioNum, dpmAppl, dpmApplFc, dpmApplSc, lPgFolioN, header, footer, posted, ownerCode, bpChCode, bpChCntc, payToCode, isPaytoBnk, bnkCntry, bankCode, bnkAccount, bnkBranch, isIns, trackNo, versionNum, langCode, bpNameOw, billToOw, shipToOw, retInvoice, clsDate, mInvNum, mInvDate, seqCode, serial, seriesStr, subStr, model, taxOnExp, taxOnExpFc, taxOnExpSc, taxOnExAp, taxOnExApF, taxOnExApS, lastPmnTyp, lndCstNum, useCorrVat, blkCredMmo, openForLaC, excised, excRefDate, excRmvTime, srvGpPrcnt, depositNum, certNum, dutyStatus, autoCrtFlw, flwRefDate, flwRefNum, vatJeNum, dpmVat, dpmVatFc, dpmVatSc, dpmAppVat, dpmAppVatF, dpmAppVatS, insurOp347, ignRelDoc, buildDesc, residenNum, checker, payee, copyNumber, ssiExmpt, pqtGrpSer, pqtGrpNum, pqtGrpHw, reopOriDoc, reopManCls, docManClsd, closingOpt, specDate, ordered, ntsApprov, ntsWebSite, ntSeTaxNo, ntsApprNo, payDuMonth, extraMonth, extraDays, cdcOffset, signMsg, signDigest, certifNum, keyVersion, eDocGenTyp, eSeries, eDocNum, eDocExpFrm, onlineQuo, posEqNum, posManufSn, posCashN, eDocStatus, eDocCntnt, eDocProces, eDocErrCod, eDocErrMsg, eDocCancel, eDocTest, eDocPrefix, cup, cig, dpmAsDscnt, attachment, atcEntry, supplCode, gtsRlvnt, baseDisc, baseDiscSc, baseDiscFc, baseDiscPr, createTs, updateTs, srvTaxRule, annInvDecR, supplier, releaser, receiver, toWhsCode, assetDate, requester, reqName, branch, department, email, notify, reqType, originType, isReuseNum, isReuseNfn, docDlvry, paidDpm, paidDpmF, paidDpmS, envTypeNFe, agrNo, isAlt, altBaseTyp, altBaseEnt, authCode, stDlvDate, stDlvTime, endDlvDate, endDlvTime, vclPlate, elCoStatus, atDocType, elCoMsg, printSepa, freeChrg, freeChrgFc, freeChrgSc, nfeValue, fiscDocNum, relatedTyp, relatedEnt, ccdEntry, nfePrntFo, zrdAbs, posRcptNo, foCTax, foCTaxFc, foCTaxSc, tpCusPres, excDocDate, foCFrght, foCFrghtFc, foCFrghtSc, interimTyp, ptiCode, letter, folNumFrom, folNumTo, folSeries, splitTax, splitTaxFc, splitTaxSc, toBinCode, permitNo, myFtype, poDropPrss, docTaxId, dateReport, repSection, exclTaxRep, posCashReg, dmpTransId, eCommerBp, eComerGstn, revision, revRefNo, revRefDate, revCreRefN, revCreRefD, taxInvNo, frmBpDate, gstTranTyp, baseType, baseEntry, uCodeSerial, uCodeInv, uInvSerial, uInvCode, uDeclareStat, uCoName, uCoAdd, uCoTaxNo, uDocnum, uDescriptionVn, uPurNvGiao, uDeliveryTime, uInvRemark, uHrcode, uHrname, uHt, uVc, uGrpo, uPayment, uProduction, uBatch, uDChuyen, uInvCode2, uManufacturer, uApproval, uGoodReceipt, uCheckQc, uPurNvNhan, uPrinted, uCknb, uCategory, uTransNum, uDeclarePd, uSoPx, uContractDate, uLine, uWhscode, uSendToHo, uDpm, uDocOrg, uDateDeb);
    }
}
