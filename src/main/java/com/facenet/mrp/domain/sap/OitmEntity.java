package com.facenet.mrp.domain.sap;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
@Entity
@Table(name = "OITM")
public class OitmEntity {
    private String itemCode;
    private String itemName;
    private String frgnName;


    private Long sum;
    private OitbEntity itmsGrpCod;

    private Set<OitwEntity> oitwEntities;
    private Short cstGrpCode;
    private String vatGourpSa;
    private String codeBars;
    private String vatLiable;
    private String prchseItem;
    private String sellItem;
    private String invntItem;
    private Long onHand;
    private Long isCommited;
    private Long onOrder;
    private String incomeAcct;
    private String exmptIncom;
    private Long maxLevel;
    private String dfltWh;
    private String cardCode;
    private String suppCatNum;
    private String buyUnitMsr;
    private Long numInBuy;
    private Long reorderQty;
    private Long minLevel;
    private Long lstEvlPric;
    private Date lstEvlDate;
    private Long customPer;
    private String canceled;
    private Integer mnufctTime;
    private String wholSlsTax;
    private String retilrTax;
    private Long spcialDisc;
    private Short dscountCod;
    private String trackSales;
    private String salUnitMsr;
    private Long numInSale;
    private Long consig;
    private Integer queryGroup;
    private Long counted;
    private Long openBlnc;
    private String evalSystem;
    private Short userSign;
    private String free;
    private String picturName;
    private String transfered;
    private String blncTrnsfr;
    private String userText;
    private String serialNum;
    private Long commisPcnt;
    private Long commisSum;
    private Short commisGrp;
    private String treeType;
    private Long treeQty;
    private Long lastPurPrc;
    private String lastPurCur;
    private Date lastPurDat;
    private String exitCur;
    private Long exitPrice;
    private String exitWh;
    private String assetItem;
    private String wasCounted;
    private String manSerNum;
    private Long sHeight1;
    private Short sHght1Unit;
    private Long sHeight2;
    private Short sHght2Unit;
    private Long sWidth1;
    private Short sWdth1Unit;
    private Long sWidth2;
    private Short sWdth2Unit;
    private Long sLength1;
    private Short sLen1Unit;
    private Long slength2;
    private Short sLen2Unit;
    private Long sVolume;
    private Short sVolUnit;
    private Long sWeight1;
    private Short sWght1Unit;
    private Long sWeight2;
    private Short sWght2Unit;
    private Long bHeight1;
    private Short bHght1Unit;
    private Long bHeight2;
    private Short bHght2Unit;
    private Long bWidth1;
    private Short bWdth1Unit;
    private Long bWidth2;
    private Short bWdth2Unit;
    private Long bLength1;
    private Short bLen1Unit;
    private Long blength2;
    private Short bLen2Unit;
    private Long bVolume;
    private Short bVolUnit;
    private Long bWeight1;
    private Short bWght1Unit;
    private Long bWeight2;
    private Short bWght2Unit;
    private String fixCurrCms;
    private Short firmCode;
    private Date lstSalDate;
    private String qryGroup1;
    private String qryGroup2;
    private String qryGroup3;
    private String qryGroup4;
    private String qryGroup5;
    private String qryGroup6;
    private String qryGroup7;
    private String qryGroup8;
    private String qryGroup9;
    private String qryGroup10;
    private String qryGroup11;
    private String qryGroup12;
    private String qryGroup13;
    private String qryGroup14;
    private String qryGroup15;
    private String qryGroup16;
    private String qryGroup17;
    private String qryGroup18;
    private String qryGroup19;
    private String qryGroup20;
    private String qryGroup21;
    private String qryGroup22;
    private String qryGroup23;
    private String qryGroup24;
    private String qryGroup25;
    private String qryGroup26;
    private String qryGroup27;
    private String qryGroup28;
    private String qryGroup29;
    private String qryGroup30;
    private String qryGroup31;
    private String qryGroup32;
    private String qryGroup33;
    private String qryGroup34;
    private String qryGroup35;
    private String qryGroup36;
    private String qryGroup37;
    private String qryGroup38;
    private String qryGroup39;
    private String qryGroup40;
    private String qryGroup41;
    private String qryGroup42;
    private String qryGroup43;
    private String qryGroup44;
    private String qryGroup45;
    private String qryGroup46;
    private String qryGroup47;
    private String qryGroup48;
    private String qryGroup49;
    private String qryGroup50;
    private String qryGroup51;
    private String qryGroup52;
    private String qryGroup53;
    private String qryGroup54;
    private String qryGroup55;
    private String qryGroup56;
    private String qryGroup57;
    private String qryGroup58;
    private String qryGroup59;
    private String qryGroup60;
    private String qryGroup61;
    private String qryGroup62;
    private String qryGroup63;
    private String qryGroup64;
    private Date createDate;
    private Date updateDate;
    private String exportCode;
    private Long salFactor1;
    private Long salFactor2;
    private Long salFactor3;
    private Long salFactor4;
    private Long purFactor1;
    private Long purFactor2;
    private Long purFactor3;
    private Long purFactor4;
    private String salFormula;
    private String purFormula;
    private String vatGroupPu;
    private Long avgPrice;
    private String purPackMsr;
    private Long purPackUn;
    private String salPackMsr;
    private Long salPackUn;
    private Short scnCounter;
    private String manBtchNum;
    private String manOutOnly;
    private String dataSource;
    private String validFor;
    private Date validFrom;
    private Date validTo;
    private String frozenFor;
    private Date frozenFrom;
    private Date frozenTo;
    private String blockOut;
    private String validComm;
    private String frozenComm;
    private Integer logInstanc;
    private String objType;
    private String sww;
    private String deleted;
    private Integer docEntry;
    private String expensAcct;
    private String frgnInAcct;
    private Short shipType;
    private String glMethod;
    private String ecInAcct;
    private String frgnExpAcc;
    private String ecExpAcc;
    private String taxType;
    private String byWh;
    private String wtLiable;
    private String itemType;
    private String warrntTmpl;
    private String baseUnit;
    private String countryOrg;
    private Long stockValue;
    private String phantom;
    private String issueMthd;
    private String free1;
    private Long pricingPrc;
    private String mngMethod;
    private Long reorderPnt;
    private String invntryUom;
    private String planingSys;
    private String prcrmntMtd;
    private Short ordrIntrvl;
    private Long ordrMulti;
    private Long minOrdrQty;
    private Integer leadTime;
    private String indirctTax;
    private String taxCodeAr;
    private String taxCodeAp;
    private Integer oSvcCode;
    private Integer iSvcCode;
    private Integer serviceGrp;
    private Integer ncmCode;
    private String matType;
    private Integer matGrp;
    private String productSrc;
    private Integer serviceCtg;
    private String itemClass;
    private String excisable;
    private Integer chapterId;
    private String notifyAsn;
    private String proAssNum;
    private Long assblValue;
    private Integer dnfEntry;
    private Short userSign2;
    private String spec;
    private String taxCtg;
    private Short series;
    private Integer number;
    private Integer fuelCode;
    private String beverTblC;
    private String beverGrpC;
    private Integer beverTm;
    private String attachment;
    private Integer atcEntry;
    private Integer toleranDay;
    private String ugpEntry;
    private Integer pUoMEntry;
    private Integer sUoMEntry;
    private Integer iUoMEntry;
    private Short issuePriBy;
    private String assetClass;
    private String assetGroup;
    private String inventryNo;
    private Integer technician;
    private Integer employee;
    private Integer location;
    private String statAsset;
    private String cession;
    private String deacAftUl;
    private String asstStatus;
    private Date capDate;
    private Date acqDate;
    private Date retDate;
    private String glPickMeth;
    private String noDiscount;
    private String mgrByQty;
    private String assetRmk1;
    private String assetRmk2;
    private Long assetAmnt1;
    private Long assetAmnt2;
    private String deprGroup;
    private String assetSerNo;
    private String cntUnitMsr;
    private Long numInCnt;
    private Integer inUoMEntry;
    private String oneBOneRec;
    private String ruleCode;
    private String scsCode;
    private String spProdType;
    private Long iWeight1;
    private Short iWght1Unit;
    private Long iWeight2;
    private Short iWght2Unit;
    private String compoWh;
    private Integer createTs;
    private Integer updateTs;
    private String virtAstItm;
    private String souVirAsst;
    private String inCostRoll;
    private Long prdStdCst;
    private String enAstSeri;
    private String linkRsc;
    private Long onHldPert;
    private Long onHldLimt;
    private String gstRelevnt;
    private Integer sacEntry;
    private String gstTaxCtg;
    private String uIGroup;
    private String uSubgr;
    private String uRdcode;
    private String uIGroupName;
    private String uSubgrName;
    private String uProductBranch;
    private String uProductGroup;
    private String uForcast;
    private String uShortName;
    private String uTechName;
    private String uDGroup;
    private String uDGroupName;
    private Long uPackage;
//    private String uMsl;
//    private String uPartNumber;

    private String uFactory;
    private String uCDGT;
    private String uType;
    private String uCLVL;
    private Long uDaiOng;
    private Long uDaiOng1;
    private Long uHeSo;
    private String uKH;
    private String uSoThe;

    public OitmEntity() {
    }

    public OitmEntity(String itemCode, String itemName, Long sum) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.sum = sum;
    }

    public OitmEntity(OitmEntity oitmEntity,Long sum) {
        this.itemCode = oitmEntity.getItemCode();
        this.itemName = oitmEntity.getItemName();
        this.uIGroup = oitmEntity.getuIGroupName();
        this.itmsGrpCod = oitmEntity.getItmsGrpCod();
        this.uSubgr = oitmEntity.getuSubgr();
        this.buyUnitMsr = oitmEntity.getBuyUnitMsr();
        this.uTechName = oitmEntity.getuTechName();
        this.asstStatus = oitmEntity.getAsstStatus();
        this.sum = sum;
    }

    @Id
    @Column(name = "ItemCode")
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Basic
    @Column(name = "ItemName")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Basic
    @Column(name = "FrgnName")
    public String getFrgnName() {
        return frgnName;
    }

    public void setFrgnName(String frgnName) {
        this.frgnName = frgnName;
    }


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ItmsGrpCod")
    public OitbEntity getItmsGrpCod() {
        return itmsGrpCod;
    }



    public void setItmsGrpCod(OitbEntity itmsGrpCod) {
        this.itmsGrpCod = itmsGrpCod;
    }

    @Basic
    @Column(name = "CstGrpCode")
    public Short getCstGrpCode() {
        return cstGrpCode;
    }

    public void setCstGrpCode(Short cstGrpCode) {
        this.cstGrpCode = cstGrpCode;
    }

    @Basic
    @Column(name = "VatGourpSa")
    public String getVatGourpSa() {
        return vatGourpSa;
    }

    public void setVatGourpSa(String vatGourpSa) {
        this.vatGourpSa = vatGourpSa;
    }

    @Basic
    @Column(name = "CodeBars")
    public String getCodeBars() {
        return codeBars;
    }

    public void setCodeBars(String codeBars) {
        this.codeBars = codeBars;
    }

    @Basic
    @Column(name = "VATLiable")
    public String getVatLiable() {
        return vatLiable;
    }

    public void setVatLiable(String vatLiable) {
        this.vatLiable = vatLiable;
    }

    @Basic
    @Column(name = "PrchseItem")
    public String getPrchseItem() {
        return prchseItem;
    }

    public void setPrchseItem(String prchseItem) {
        this.prchseItem = prchseItem;
    }

    @Basic
    @Column(name = "SellItem")
    public String getSellItem() {
        return sellItem;
    }

    public void setSellItem(String sellItem) {
        this.sellItem = sellItem;
    }

    @Basic
    @Column(name = "InvntItem")
    public String getInvntItem() {
        return invntItem;
    }

    public void setInvntItem(String invntItem) {
        this.invntItem = invntItem;
    }

    @Basic
    @Column(name = "OnHand")
    public Long getOnHand() {
        return onHand;
    }

    public void setOnHand(Long onHand) {
        this.onHand = onHand;
    }

    @Basic
    @Column(name = "IsCommited")
    public Long getIsCommited() {
        return isCommited;
    }

    public void setIsCommited(Long isCommited) {
        this.isCommited = isCommited;
    }

    @Basic
    @Column(name = "OnOrder")
    public Long getOnOrder() {
        return onOrder;
    }

    public void setOnOrder(Long onOrder) {
        this.onOrder = onOrder;
    }

    @Basic
    @Column(name = "IncomeAcct")
    public String getIncomeAcct() {
        return incomeAcct;
    }

    public void setIncomeAcct(String incomeAcct) {
        this.incomeAcct = incomeAcct;
    }

    @Basic
    @Column(name = "ExmptIncom")
    public String getExmptIncom() {
        return exmptIncom;
    }

    public void setExmptIncom(String exmptIncom) {
        this.exmptIncom = exmptIncom;
    }

    @Basic
    @Column(name = "MaxLevel")
    public Long getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(Long maxLevel) {
        this.maxLevel = maxLevel;
    }

    @Basic
    @Column(name = "DfltWH")
    public String getDfltWh() {
        return dfltWh;
    }

    public void setDfltWh(String dfltWh) {
        this.dfltWh = dfltWh;
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
    @Column(name = "SuppCatNum")
    public String getSuppCatNum() {
        return suppCatNum;
    }

    public void setSuppCatNum(String suppCatNum) {
        this.suppCatNum = suppCatNum;
    }

    @Basic
    @Column(name = "BuyUnitMsr")
    public String getBuyUnitMsr() {
        return buyUnitMsr;
    }

    public void setBuyUnitMsr(String buyUnitMsr) {
        this.buyUnitMsr = buyUnitMsr;
    }

    @Basic
    @Column(name = "NumInBuy")
    public Long getNumInBuy() {
        return numInBuy;
    }

    public void setNumInBuy(Long numInBuy) {
        this.numInBuy = numInBuy;
    }

    @Basic
    @Column(name = "ReorderQty")
    public Long getReorderQty() {
        return reorderQty;
    }

    public void setReorderQty(Long reorderQty) {
        this.reorderQty = reorderQty;
    }

    @Basic
    @Column(name = "MinLevel")
    public Long getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(Long minLevel) {
        this.minLevel = minLevel;
    }

    @Basic
    @Column(name = "LstEvlPric")
    public Long getLstEvlPric() {
        return lstEvlPric;
    }

    public void setLstEvlPric(Long lstEvlPric) {
        this.lstEvlPric = lstEvlPric;
    }

    @Basic
    @Column(name = "LstEvlDate")
    public Date getLstEvlDate() {
        return lstEvlDate;
    }

    public void setLstEvlDate(Date lstEvlDate) {
        this.lstEvlDate = lstEvlDate;
    }

    @Basic
    @Column(name = "CustomPer")
    public Long getCustomPer() {
        return customPer;
    }

    public void setCustomPer(Long customPer) {
        this.customPer = customPer;
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
    @Column(name = "MnufctTime")
    public Integer getMnufctTime() {
        return mnufctTime;
    }

    public void setMnufctTime(Integer mnufctTime) {
        this.mnufctTime = mnufctTime;
    }

    @Basic
    @Column(name = "WholSlsTax")
    public String getWholSlsTax() {
        return wholSlsTax;
    }

    public void setWholSlsTax(String wholSlsTax) {
        this.wholSlsTax = wholSlsTax;
    }

    @Basic
    @Column(name = "RetilrTax")
    public String getRetilrTax() {
        return retilrTax;
    }

    public void setRetilrTax(String retilrTax) {
        this.retilrTax = retilrTax;
    }

    @Basic
    @Column(name = "SpcialDisc")
    public Long getSpcialDisc() {
        return spcialDisc;
    }

    public void setSpcialDisc(Long spcialDisc) {
        this.spcialDisc = spcialDisc;
    }

    @Basic
    @Column(name = "DscountCod")
    public Short getDscountCod() {
        return dscountCod;
    }

    public void setDscountCod(Short dscountCod) {
        this.dscountCod = dscountCod;
    }

    @Basic
    @Column(name = "TrackSales")
    public String getTrackSales() {
        return trackSales;
    }

    public void setTrackSales(String trackSales) {
        this.trackSales = trackSales;
    }

    @Basic
    @Column(name = "SalUnitMsr")
    public String getSalUnitMsr() {
        return salUnitMsr;
    }

    public void setSalUnitMsr(String salUnitMsr) {
        this.salUnitMsr = salUnitMsr;
    }

    @Basic
    @Column(name = "NumInSale")
    public Long getNumInSale() {
        return numInSale;
    }

    public void setNumInSale(Long numInSale) {
        this.numInSale = numInSale;
    }

    @Basic
    @Column(name = "Consig")
    public Long getConsig() {
        return consig;
    }

    public void setConsig(Long consig) {
        this.consig = consig;
    }

    @Basic
    @Column(name = "QueryGroup")
    public Integer getQueryGroup() {
        return queryGroup;
    }

    public void setQueryGroup(Integer queryGroup) {
        this.queryGroup = queryGroup;
    }

    @Basic
    @Column(name = "Counted")
    public Long getCounted() {
        return counted;
    }

    public void setCounted(Long counted) {
        this.counted = counted;
    }

    @Basic
    @Column(name = "OpenBlnc")
    public Long getOpenBlnc() {
        return openBlnc;
    }

    public void setOpenBlnc(Long openBlnc) {
        this.openBlnc = openBlnc;
    }

    @Basic
    @Column(name = "EvalSystem")
    public String getEvalSystem() {
        return evalSystem;
    }

    public void setEvalSystem(String evalSystem) {
        this.evalSystem = evalSystem;
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
    @Column(name = "FREE")
    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    @Basic
    @Column(name = "PicturName")
    public String getPicturName() {
        return picturName;
    }

    public void setPicturName(String picturName) {
        this.picturName = picturName;
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
    @Column(name = "BlncTrnsfr")
    public String getBlncTrnsfr() {
        return blncTrnsfr;
    }

    public void setBlncTrnsfr(String blncTrnsfr) {
        this.blncTrnsfr = blncTrnsfr;
    }

    @Basic
    @Column(name = "UserText")
    public String getUserText() {
        return userText;
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    @Basic
    @Column(name = "SerialNum")
    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    @Basic
    @Column(name = "CommisPcnt")
    public Long getCommisPcnt() {
        return commisPcnt;
    }

    public void setCommisPcnt(Long commisPcnt) {
        this.commisPcnt = commisPcnt;
    }

    @Basic
    @Column(name = "CommisSum")
    public Long getCommisSum() {
        return commisSum;
    }

    public void setCommisSum(Long commisSum) {
        this.commisSum = commisSum;
    }

    @Basic
    @Column(name = "CommisGrp")
    public Short getCommisGrp() {
        return commisGrp;
    }

    public void setCommisGrp(Short commisGrp) {
        this.commisGrp = commisGrp;
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
    @Column(name = "TreeQty")
    public Long getTreeQty() {
        return treeQty;
    }

    public void setTreeQty(Long treeQty) {
        this.treeQty = treeQty;
    }

    @Basic
    @Column(name = "LastPurPrc")
    public Long getLastPurPrc() {
        return lastPurPrc;
    }

    public void setLastPurPrc(Long lastPurPrc) {
        this.lastPurPrc = lastPurPrc;
    }

    @Basic
    @Column(name = "LastPurCur")
    public String getLastPurCur() {
        return lastPurCur;
    }

    public void setLastPurCur(String lastPurCur) {
        this.lastPurCur = lastPurCur;
    }

    @Basic
    @Column(name = "LastPurDat")
    public Date getLastPurDat() {
        return lastPurDat;
    }

    public void setLastPurDat(Date lastPurDat) {
        this.lastPurDat = lastPurDat;
    }

    @Basic
    @Column(name = "ExitCur")
    public String getExitCur() {
        return exitCur;
    }

    public void setExitCur(String exitCur) {
        this.exitCur = exitCur;
    }

    @Basic
    @Column(name = "ExitPrice")
    public Long getExitPrice() {
        return exitPrice;
    }

    public void setExitPrice(Long exitPrice) {
        this.exitPrice = exitPrice;
    }

    @Basic
    @Column(name = "ExitWH")
    public String getExitWh() {
        return exitWh;
    }

    public void setExitWh(String exitWh) {
        this.exitWh = exitWh;
    }

    @Basic
    @Column(name = "AssetItem")
    public String getAssetItem() {
        return assetItem;
    }

    public void setAssetItem(String assetItem) {
        this.assetItem = assetItem;
    }

    @Basic
    @Column(name = "WasCounted")
    public String getWasCounted() {
        return wasCounted;
    }

    public void setWasCounted(String wasCounted) {
        this.wasCounted = wasCounted;
    }

    @Basic
    @Column(name = "ManSerNum")
    public String getManSerNum() {
        return manSerNum;
    }

    public void setManSerNum(String manSerNum) {
        this.manSerNum = manSerNum;
    }

    @Basic
    @Column(name = "SHeight1")
    public Long getsHeight1() {
        return sHeight1;
    }

    public void setsHeight1(Long sHeight1) {
        this.sHeight1 = sHeight1;
    }

    @Basic
    @Column(name = "SHght1Unit")
    public Short getsHght1Unit() {
        return sHght1Unit;
    }

    public void setsHght1Unit(Short sHght1Unit) {
        this.sHght1Unit = sHght1Unit;
    }

    @Basic
    @Column(name = "SHeight2")
    public Long getsHeight2() {
        return sHeight2;
    }

    public void setsHeight2(Long sHeight2) {
        this.sHeight2 = sHeight2;
    }

    @Basic
    @Column(name = "SHght2Unit")
    public Short getsHght2Unit() {
        return sHght2Unit;
    }

    public void setsHght2Unit(Short sHght2Unit) {
        this.sHght2Unit = sHght2Unit;
    }

    @Basic
    @Column(name = "SWidth1")
    public Long getsWidth1() {
        return sWidth1;
    }

    public void setsWidth1(Long sWidth1) {
        this.sWidth1 = sWidth1;
    }

    @Basic
    @Column(name = "SWdth1Unit")
    public Short getsWdth1Unit() {
        return sWdth1Unit;
    }

    public void setsWdth1Unit(Short sWdth1Unit) {
        this.sWdth1Unit = sWdth1Unit;
    }

    @Basic
    @Column(name = "SWidth2")
    public Long getsWidth2() {
        return sWidth2;
    }

    public void setsWidth2(Long sWidth2) {
        this.sWidth2 = sWidth2;
    }

    @Basic
    @Column(name = "SWdth2Unit")
    public Short getsWdth2Unit() {
        return sWdth2Unit;
    }

    public void setsWdth2Unit(Short sWdth2Unit) {
        this.sWdth2Unit = sWdth2Unit;
    }

    @Basic
    @Column(name = "SLength1")
    public Long getsLength1() {
        return sLength1;
    }

    public void setsLength1(Long sLength1) {
        this.sLength1 = sLength1;
    }

    @Basic
    @Column(name = "SLen1Unit")
    public Short getsLen1Unit() {
        return sLen1Unit;
    }

    public void setsLen1Unit(Short sLen1Unit) {
        this.sLen1Unit = sLen1Unit;
    }

    @Basic
    @Column(name = "Slength2")
    public Long getSlength2() {
        return slength2;
    }

    public void setSlength2(Long slength2) {
        this.slength2 = slength2;
    }

    @Basic
    @Column(name = "SLen2Unit")
    public Short getsLen2Unit() {
        return sLen2Unit;
    }

    public void setsLen2Unit(Short sLen2Unit) {
        this.sLen2Unit = sLen2Unit;
    }

    @Basic
    @Column(name = "SVolume")
    public Long getsVolume() {
        return sVolume;
    }

    public void setsVolume(Long sVolume) {
        this.sVolume = sVolume;
    }

    @Basic
    @Column(name = "SVolUnit")
    public Short getsVolUnit() {
        return sVolUnit;
    }

    public void setsVolUnit(Short sVolUnit) {
        this.sVolUnit = sVolUnit;
    }

    @Basic
    @Column(name = "SWeight1")
    public Long getsWeight1() {
        return sWeight1;
    }

    public void setsWeight1(Long sWeight1) {
        this.sWeight1 = sWeight1;
    }

    @Basic
    @Column(name = "SWght1Unit")
    public Short getsWght1Unit() {
        return sWght1Unit;
    }

    public void setsWght1Unit(Short sWght1Unit) {
        this.sWght1Unit = sWght1Unit;
    }

    @Basic
    @Column(name = "SWeight2")
    public Long getsWeight2() {
        return sWeight2;
    }

    public void setsWeight2(Long sWeight2) {
        this.sWeight2 = sWeight2;
    }

    @Basic
    @Column(name = "SWght2Unit")
    public Short getsWght2Unit() {
        return sWght2Unit;
    }

    public void setsWght2Unit(Short sWght2Unit) {
        this.sWght2Unit = sWght2Unit;
    }

    @Basic
    @Column(name = "BHeight1")
    public Long getbHeight1() {
        return bHeight1;
    }

    public void setbHeight1(Long bHeight1) {
        this.bHeight1 = bHeight1;
    }

    @Basic
    @Column(name = "BHght1Unit")
    public Short getbHght1Unit() {
        return bHght1Unit;
    }

    public void setbHght1Unit(Short bHght1Unit) {
        this.bHght1Unit = bHght1Unit;
    }

    @Basic
    @Column(name = "BHeight2")
    public Long getbHeight2() {
        return bHeight2;
    }

    public void setbHeight2(Long bHeight2) {
        this.bHeight2 = bHeight2;
    }

    @Basic
    @Column(name = "BHght2Unit")
    public Short getbHght2Unit() {
        return bHght2Unit;
    }

    public void setbHght2Unit(Short bHght2Unit) {
        this.bHght2Unit = bHght2Unit;
    }

    @Basic
    @Column(name = "BWidth1")
    public Long getbWidth1() {
        return bWidth1;
    }

    public void setbWidth1(Long bWidth1) {
        this.bWidth1 = bWidth1;
    }

    @Basic
    @Column(name = "BWdth1Unit")
    public Short getbWdth1Unit() {
        return bWdth1Unit;
    }

    public void setbWdth1Unit(Short bWdth1Unit) {
        this.bWdth1Unit = bWdth1Unit;
    }

    @Basic
    @Column(name = "BWidth2")
    public Long getbWidth2() {
        return bWidth2;
    }

    public void setbWidth2(Long bWidth2) {
        this.bWidth2 = bWidth2;
    }

    @Basic
    @Column(name = "BWdth2Unit")
    public Short getbWdth2Unit() {
        return bWdth2Unit;
    }

    public void setbWdth2Unit(Short bWdth2Unit) {
        this.bWdth2Unit = bWdth2Unit;
    }

    @Basic
    @Column(name = "BLength1")
    public Long getbLength1() {
        return bLength1;
    }

    public void setbLength1(Long bLength1) {
        this.bLength1 = bLength1;
    }

    @Basic
    @Column(name = "BLen1Unit")
    public Short getbLen1Unit() {
        return bLen1Unit;
    }

    public void setbLen1Unit(Short bLen1Unit) {
        this.bLen1Unit = bLen1Unit;
    }

    @Basic
    @Column(name = "Blength2")
    public Long getBlength2() {
        return blength2;
    }

    public void setBlength2(Long blength2) {
        this.blength2 = blength2;
    }

    @Basic
    @Column(name = "BLen2Unit")
    public Short getbLen2Unit() {
        return bLen2Unit;
    }

    public void setbLen2Unit(Short bLen2Unit) {
        this.bLen2Unit = bLen2Unit;
    }

    @Basic
    @Column(name = "BVolume")
    public Long getbVolume() {
        return bVolume;
    }

    public void setbVolume(Long bVolume) {
        this.bVolume = bVolume;
    }

    @Basic
    @Column(name = "BVolUnit")
    public Short getbVolUnit() {
        return bVolUnit;
    }

    public void setbVolUnit(Short bVolUnit) {
        this.bVolUnit = bVolUnit;
    }

    @Basic
    @Column(name = "BWeight1")
    public Long getbWeight1() {
        return bWeight1;
    }

    public void setbWeight1(Long bWeight1) {
        this.bWeight1 = bWeight1;
    }

    @Basic
    @Column(name = "BWght1Unit")
    public Short getbWght1Unit() {
        return bWght1Unit;
    }

    public void setbWght1Unit(Short bWght1Unit) {
        this.bWght1Unit = bWght1Unit;
    }

    @Basic
    @Column(name = "BWeight2")
    public Long getbWeight2() {
        return bWeight2;
    }

    public void setbWeight2(Long bWeight2) {
        this.bWeight2 = bWeight2;
    }

    @Basic
    @Column(name = "BWght2Unit")
    public Short getbWght2Unit() {
        return bWght2Unit;
    }

    public void setbWght2Unit(Short bWght2Unit) {
        this.bWght2Unit = bWght2Unit;
    }

    @Basic
    @Column(name = "FixCurrCms")
    public String getFixCurrCms() {
        return fixCurrCms;
    }

    public void setFixCurrCms(String fixCurrCms) {
        this.fixCurrCms = fixCurrCms;
    }

    @Basic
    @Column(name = "FirmCode")
    public Short getFirmCode() {
        return firmCode;
    }

    public void setFirmCode(Short firmCode) {
        this.firmCode = firmCode;
    }

    @Basic
    @Column(name = "LstSalDate")
    public Date getLstSalDate() {
        return lstSalDate;
    }

    public void setLstSalDate(Date lstSalDate) {
        this.lstSalDate = lstSalDate;
    }

    @Basic
    @Column(name = "QryGroup1")
    public String getQryGroup1() {
        return qryGroup1;
    }

    public void setQryGroup1(String qryGroup1) {
        this.qryGroup1 = qryGroup1;
    }

    @Basic
    @Column(name = "QryGroup2")
    public String getQryGroup2() {
        return qryGroup2;
    }

    public void setQryGroup2(String qryGroup2) {
        this.qryGroup2 = qryGroup2;
    }

    @Basic
    @Column(name = "QryGroup3")
    public String getQryGroup3() {
        return qryGroup3;
    }

    public void setQryGroup3(String qryGroup3) {
        this.qryGroup3 = qryGroup3;
    }

    @Basic
    @Column(name = "QryGroup4")
    public String getQryGroup4() {
        return qryGroup4;
    }

    public void setQryGroup4(String qryGroup4) {
        this.qryGroup4 = qryGroup4;
    }

    @Basic
    @Column(name = "QryGroup5")
    public String getQryGroup5() {
        return qryGroup5;
    }

    public void setQryGroup5(String qryGroup5) {
        this.qryGroup5 = qryGroup5;
    }

    @Basic
    @Column(name = "QryGroup6")
    public String getQryGroup6() {
        return qryGroup6;
    }

    public void setQryGroup6(String qryGroup6) {
        this.qryGroup6 = qryGroup6;
    }

    @Basic
    @Column(name = "QryGroup7")
    public String getQryGroup7() {
        return qryGroup7;
    }

    public void setQryGroup7(String qryGroup7) {
        this.qryGroup7 = qryGroup7;
    }

    @Basic
    @Column(name = "QryGroup8")
    public String getQryGroup8() {
        return qryGroup8;
    }

    public void setQryGroup8(String qryGroup8) {
        this.qryGroup8 = qryGroup8;
    }

    @Basic
    @Column(name = "QryGroup9")
    public String getQryGroup9() {
        return qryGroup9;
    }

    public void setQryGroup9(String qryGroup9) {
        this.qryGroup9 = qryGroup9;
    }

    @Basic
    @Column(name = "QryGroup10")
    public String getQryGroup10() {
        return qryGroup10;
    }

    public void setQryGroup10(String qryGroup10) {
        this.qryGroup10 = qryGroup10;
    }

    @Basic
    @Column(name = "QryGroup11")
    public String getQryGroup11() {
        return qryGroup11;
    }

    public void setQryGroup11(String qryGroup11) {
        this.qryGroup11 = qryGroup11;
    }

    @Basic
    @Column(name = "QryGroup12")
    public String getQryGroup12() {
        return qryGroup12;
    }

    public void setQryGroup12(String qryGroup12) {
        this.qryGroup12 = qryGroup12;
    }

    @Basic
    @Column(name = "QryGroup13")
    public String getQryGroup13() {
        return qryGroup13;
    }

    public void setQryGroup13(String qryGroup13) {
        this.qryGroup13 = qryGroup13;
    }

    @Basic
    @Column(name = "QryGroup14")
    public String getQryGroup14() {
        return qryGroup14;
    }

    public void setQryGroup14(String qryGroup14) {
        this.qryGroup14 = qryGroup14;
    }

    @Basic
    @Column(name = "QryGroup15")
    public String getQryGroup15() {
        return qryGroup15;
    }

    public void setQryGroup15(String qryGroup15) {
        this.qryGroup15 = qryGroup15;
    }

    @Basic
    @Column(name = "QryGroup16")
    public String getQryGroup16() {
        return qryGroup16;
    }

    public void setQryGroup16(String qryGroup16) {
        this.qryGroup16 = qryGroup16;
    }

    @Basic
    @Column(name = "QryGroup17")
    public String getQryGroup17() {
        return qryGroup17;
    }

    public void setQryGroup17(String qryGroup17) {
        this.qryGroup17 = qryGroup17;
    }

    @Basic
    @Column(name = "QryGroup18")
    public String getQryGroup18() {
        return qryGroup18;
    }

    public void setQryGroup18(String qryGroup18) {
        this.qryGroup18 = qryGroup18;
    }

    @Basic
    @Column(name = "QryGroup19")
    public String getQryGroup19() {
        return qryGroup19;
    }

    public void setQryGroup19(String qryGroup19) {
        this.qryGroup19 = qryGroup19;
    }

    @Basic
    @Column(name = "QryGroup20")
    public String getQryGroup20() {
        return qryGroup20;
    }

    public void setQryGroup20(String qryGroup20) {
        this.qryGroup20 = qryGroup20;
    }

    @Basic
    @Column(name = "QryGroup21")
    public String getQryGroup21() {
        return qryGroup21;
    }

    public void setQryGroup21(String qryGroup21) {
        this.qryGroup21 = qryGroup21;
    }

    @Basic
    @Column(name = "QryGroup22")
    public String getQryGroup22() {
        return qryGroup22;
    }

    public void setQryGroup22(String qryGroup22) {
        this.qryGroup22 = qryGroup22;
    }

    @Basic
    @Column(name = "QryGroup23")
    public String getQryGroup23() {
        return qryGroup23;
    }

    public void setQryGroup23(String qryGroup23) {
        this.qryGroup23 = qryGroup23;
    }

    @Basic
    @Column(name = "QryGroup24")
    public String getQryGroup24() {
        return qryGroup24;
    }

    public void setQryGroup24(String qryGroup24) {
        this.qryGroup24 = qryGroup24;
    }

    @Basic
    @Column(name = "QryGroup25")
    public String getQryGroup25() {
        return qryGroup25;
    }

    public void setQryGroup25(String qryGroup25) {
        this.qryGroup25 = qryGroup25;
    }

    @Basic
    @Column(name = "QryGroup26")
    public String getQryGroup26() {
        return qryGroup26;
    }

    public void setQryGroup26(String qryGroup26) {
        this.qryGroup26 = qryGroup26;
    }

    @Basic
    @Column(name = "QryGroup27")
    public String getQryGroup27() {
        return qryGroup27;
    }

    public void setQryGroup27(String qryGroup27) {
        this.qryGroup27 = qryGroup27;
    }

    @Basic
    @Column(name = "QryGroup28")
    public String getQryGroup28() {
        return qryGroup28;
    }

    public void setQryGroup28(String qryGroup28) {
        this.qryGroup28 = qryGroup28;
    }

    @Basic
    @Column(name = "QryGroup29")
    public String getQryGroup29() {
        return qryGroup29;
    }

    public void setQryGroup29(String qryGroup29) {
        this.qryGroup29 = qryGroup29;
    }

    @Basic
    @Column(name = "QryGroup30")
    public String getQryGroup30() {
        return qryGroup30;
    }

    public void setQryGroup30(String qryGroup30) {
        this.qryGroup30 = qryGroup30;
    }

    @Basic
    @Column(name = "QryGroup31")
    public String getQryGroup31() {
        return qryGroup31;
    }

    public void setQryGroup31(String qryGroup31) {
        this.qryGroup31 = qryGroup31;
    }

    @Basic
    @Column(name = "QryGroup32")
    public String getQryGroup32() {
        return qryGroup32;
    }

    public void setQryGroup32(String qryGroup32) {
        this.qryGroup32 = qryGroup32;
    }

    @Basic
    @Column(name = "QryGroup33")
    public String getQryGroup33() {
        return qryGroup33;
    }

    public void setQryGroup33(String qryGroup33) {
        this.qryGroup33 = qryGroup33;
    }

    @Basic
    @Column(name = "QryGroup34")
    public String getQryGroup34() {
        return qryGroup34;
    }

    public void setQryGroup34(String qryGroup34) {
        this.qryGroup34 = qryGroup34;
    }

    @Basic
    @Column(name = "QryGroup35")
    public String getQryGroup35() {
        return qryGroup35;
    }

    public void setQryGroup35(String qryGroup35) {
        this.qryGroup35 = qryGroup35;
    }

    @Basic
    @Column(name = "QryGroup36")
    public String getQryGroup36() {
        return qryGroup36;
    }

    public void setQryGroup36(String qryGroup36) {
        this.qryGroup36 = qryGroup36;
    }

    @Basic
    @Column(name = "QryGroup37")
    public String getQryGroup37() {
        return qryGroup37;
    }

    public void setQryGroup37(String qryGroup37) {
        this.qryGroup37 = qryGroup37;
    }

    @Basic
    @Column(name = "QryGroup38")
    public String getQryGroup38() {
        return qryGroup38;
    }

    public void setQryGroup38(String qryGroup38) {
        this.qryGroup38 = qryGroup38;
    }

    @Basic
    @Column(name = "QryGroup39")
    public String getQryGroup39() {
        return qryGroup39;
    }

    public void setQryGroup39(String qryGroup39) {
        this.qryGroup39 = qryGroup39;
    }

    @Basic
    @Column(name = "QryGroup40")
    public String getQryGroup40() {
        return qryGroup40;
    }

    public void setQryGroup40(String qryGroup40) {
        this.qryGroup40 = qryGroup40;
    }

    @Basic
    @Column(name = "QryGroup41")
    public String getQryGroup41() {
        return qryGroup41;
    }

    public void setQryGroup41(String qryGroup41) {
        this.qryGroup41 = qryGroup41;
    }

    @Basic
    @Column(name = "QryGroup42")
    public String getQryGroup42() {
        return qryGroup42;
    }

    public void setQryGroup42(String qryGroup42) {
        this.qryGroup42 = qryGroup42;
    }

    @Basic
    @Column(name = "QryGroup43")
    public String getQryGroup43() {
        return qryGroup43;
    }

    public void setQryGroup43(String qryGroup43) {
        this.qryGroup43 = qryGroup43;
    }

    @Basic
    @Column(name = "QryGroup44")
    public String getQryGroup44() {
        return qryGroup44;
    }

    public void setQryGroup44(String qryGroup44) {
        this.qryGroup44 = qryGroup44;
    }

    @Basic
    @Column(name = "QryGroup45")
    public String getQryGroup45() {
        return qryGroup45;
    }

    public void setQryGroup45(String qryGroup45) {
        this.qryGroup45 = qryGroup45;
    }

    @Basic
    @Column(name = "QryGroup46")
    public String getQryGroup46() {
        return qryGroup46;
    }

    public void setQryGroup46(String qryGroup46) {
        this.qryGroup46 = qryGroup46;
    }

    @Basic
    @Column(name = "QryGroup47")
    public String getQryGroup47() {
        return qryGroup47;
    }

    public void setQryGroup47(String qryGroup47) {
        this.qryGroup47 = qryGroup47;
    }

    @Basic
    @Column(name = "QryGroup48")
    public String getQryGroup48() {
        return qryGroup48;
    }

    public void setQryGroup48(String qryGroup48) {
        this.qryGroup48 = qryGroup48;
    }

    @Basic
    @Column(name = "QryGroup49")
    public String getQryGroup49() {
        return qryGroup49;
    }

    public void setQryGroup49(String qryGroup49) {
        this.qryGroup49 = qryGroup49;
    }

    @Basic
    @Column(name = "QryGroup50")
    public String getQryGroup50() {
        return qryGroup50;
    }

    public void setQryGroup50(String qryGroup50) {
        this.qryGroup50 = qryGroup50;
    }

    @Basic
    @Column(name = "QryGroup51")
    public String getQryGroup51() {
        return qryGroup51;
    }

    public void setQryGroup51(String qryGroup51) {
        this.qryGroup51 = qryGroup51;
    }

    @Basic
    @Column(name = "QryGroup52")
    public String getQryGroup52() {
        return qryGroup52;
    }

    public void setQryGroup52(String qryGroup52) {
        this.qryGroup52 = qryGroup52;
    }

    @Basic
    @Column(name = "QryGroup53")
    public String getQryGroup53() {
        return qryGroup53;
    }

    public void setQryGroup53(String qryGroup53) {
        this.qryGroup53 = qryGroup53;
    }

    @Basic
    @Column(name = "QryGroup54")
    public String getQryGroup54() {
        return qryGroup54;
    }

    public void setQryGroup54(String qryGroup54) {
        this.qryGroup54 = qryGroup54;
    }

    @Basic
    @Column(name = "QryGroup55")
    public String getQryGroup55() {
        return qryGroup55;
    }

    public void setQryGroup55(String qryGroup55) {
        this.qryGroup55 = qryGroup55;
    }

    @Basic
    @Column(name = "QryGroup56")
    public String getQryGroup56() {
        return qryGroup56;
    }

    public void setQryGroup56(String qryGroup56) {
        this.qryGroup56 = qryGroup56;
    }

    @Basic
    @Column(name = "QryGroup57")
    public String getQryGroup57() {
        return qryGroup57;
    }

    public void setQryGroup57(String qryGroup57) {
        this.qryGroup57 = qryGroup57;
    }

    @Basic
    @Column(name = "QryGroup58")
    public String getQryGroup58() {
        return qryGroup58;
    }

    public void setQryGroup58(String qryGroup58) {
        this.qryGroup58 = qryGroup58;
    }

    @Basic
    @Column(name = "QryGroup59")
    public String getQryGroup59() {
        return qryGroup59;
    }

    public void setQryGroup59(String qryGroup59) {
        this.qryGroup59 = qryGroup59;
    }

    @Basic
    @Column(name = "QryGroup60")
    public String getQryGroup60() {
        return qryGroup60;
    }

    public void setQryGroup60(String qryGroup60) {
        this.qryGroup60 = qryGroup60;
    }

    @Basic
    @Column(name = "QryGroup61")
    public String getQryGroup61() {
        return qryGroup61;
    }

    public void setQryGroup61(String qryGroup61) {
        this.qryGroup61 = qryGroup61;
    }

    @Basic
    @Column(name = "QryGroup62")
    public String getQryGroup62() {
        return qryGroup62;
    }

    public void setQryGroup62(String qryGroup62) {
        this.qryGroup62 = qryGroup62;
    }

    @Basic
    @Column(name = "QryGroup63")
    public String getQryGroup63() {
        return qryGroup63;
    }

    public void setQryGroup63(String qryGroup63) {
        this.qryGroup63 = qryGroup63;
    }

    @Basic
    @Column(name = "QryGroup64")
    public String getQryGroup64() {
        return qryGroup64;
    }

    public void setQryGroup64(String qryGroup64) {
        this.qryGroup64 = qryGroup64;
    }
    @Transient
    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    @Basic
    @Column(name = "CreateDate")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @OneToMany(mappedBy = "itemCode",cascade = CascadeType.ALL)
    public Set<OitwEntity> getOitwEntities() {
        return oitwEntities;
    }

    public void setOitwEntities(Set<OitwEntity> oitwEntities) {
        this.oitwEntities = oitwEntities;
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
    @Column(name = "ExportCode")
    public String getExportCode() {
        return exportCode;
    }

    public void setExportCode(String exportCode) {
        this.exportCode = exportCode;
    }

    @Basic
    @Column(name = "SalFactor1")
    public Long getSalFactor1() {
        return salFactor1;
    }

    public void setSalFactor1(Long salFactor1) {
        this.salFactor1 = salFactor1;
    }

    @Basic
    @Column(name = "SalFactor2")
    public Long getSalFactor2() {
        return salFactor2;
    }

    public void setSalFactor2(Long salFactor2) {
        this.salFactor2 = salFactor2;
    }

    @Basic
    @Column(name = "SalFactor3")
    public Long getSalFactor3() {
        return salFactor3;
    }

    public void setSalFactor3(Long salFactor3) {
        this.salFactor3 = salFactor3;
    }

    @Basic
    @Column(name = "SalFactor4")
    public Long getSalFactor4() {
        return salFactor4;
    }

    public void setSalFactor4(Long salFactor4) {
        this.salFactor4 = salFactor4;
    }

    @Basic
    @Column(name = "PurFactor1")
    public Long getPurFactor1() {
        return purFactor1;
    }

    public void setPurFactor1(Long purFactor1) {
        this.purFactor1 = purFactor1;
    }

    @Basic
    @Column(name = "PurFactor2")
    public Long getPurFactor2() {
        return purFactor2;
    }

    public void setPurFactor2(Long purFactor2) {
        this.purFactor2 = purFactor2;
    }

    @Basic
    @Column(name = "PurFactor3")
    public Long getPurFactor3() {
        return purFactor3;
    }

    public void setPurFactor3(Long purFactor3) {
        this.purFactor3 = purFactor3;
    }

    @Basic
    @Column(name = "PurFactor4")
    public Long getPurFactor4() {
        return purFactor4;
    }

    public void setPurFactor4(Long purFactor4) {
        this.purFactor4 = purFactor4;
    }

    @Basic
    @Column(name = "SalFormula")
    public String getSalFormula() {
        return salFormula;
    }

    public void setSalFormula(String salFormula) {
        this.salFormula = salFormula;
    }

    @Basic
    @Column(name = "PurFormula")
    public String getPurFormula() {
        return purFormula;
    }

    public void setPurFormula(String purFormula) {
        this.purFormula = purFormula;
    }

    @Basic
    @Column(name = "VatGroupPu")
    public String getVatGroupPu() {
        return vatGroupPu;
    }

    public void setVatGroupPu(String vatGroupPu) {
        this.vatGroupPu = vatGroupPu;
    }

    @Basic
    @Column(name = "AvgPrice")
    public Long getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(Long avgPrice) {
        this.avgPrice = avgPrice;
    }

    @Basic
    @Column(name = "PurPackMsr")
    public String getPurPackMsr() {
        return purPackMsr;
    }

    public void setPurPackMsr(String purPackMsr) {
        this.purPackMsr = purPackMsr;
    }

    @Basic
    @Column(name = "PurPackUn")
    public Long getPurPackUn() {
        return purPackUn;
    }

    public void setPurPackUn(Long purPackUn) {
        this.purPackUn = purPackUn;
    }

    @Basic
    @Column(name = "SalPackMsr")
    public String getSalPackMsr() {
        return salPackMsr;
    }

    public void setSalPackMsr(String salPackMsr) {
        this.salPackMsr = salPackMsr;
    }

    @Basic
    @Column(name = "SalPackUn")
    public Long getSalPackUn() {
        return salPackUn;
    }

    public void setSalPackUn(Long salPackUn) {
        this.salPackUn = salPackUn;
    }

    @Basic
    @Column(name = "SCNCounter")
    public Short getScnCounter() {
        return scnCounter;
    }

    public void setScnCounter(Short scnCounter) {
        this.scnCounter = scnCounter;
    }

    @Basic
    @Column(name = "ManBtchNum")
    public String getManBtchNum() {
        return manBtchNum;
    }

    public void setManBtchNum(String manBtchNum) {
        this.manBtchNum = manBtchNum;
    }

    @Basic
    @Column(name = "ManOutOnly")
    public String getManOutOnly() {
        return manOutOnly;
    }

    public void setManOutOnly(String manOutOnly) {
        this.manOutOnly = manOutOnly;
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
    @Column(name = "validFor")
    public String getValidFor() {
        return validFor;
    }

    public void setValidFor(String validFor) {
        this.validFor = validFor;
    }

    @Basic
    @Column(name = "validFrom")
    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    @Basic
    @Column(name = "validTo")
    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    @Basic
    @Column(name = "frozenFor")
    public String getFrozenFor() {
        return frozenFor;
    }

    public void setFrozenFor(String frozenFor) {
        this.frozenFor = frozenFor;
    }

    @Basic
    @Column(name = "frozenFrom")
    public Date getFrozenFrom() {
        return frozenFrom;
    }

    public void setFrozenFrom(Date frozenFrom) {
        this.frozenFrom = frozenFrom;
    }

    @Basic
    @Column(name = "frozenTo")
    public Date getFrozenTo() {
        return frozenTo;
    }

    public void setFrozenTo(Date frozenTo) {
        this.frozenTo = frozenTo;
    }

    @Basic
    @Column(name = "BlockOut")
    public String getBlockOut() {
        return blockOut;
    }

    public void setBlockOut(String blockOut) {
        this.blockOut = blockOut;
    }

    @Basic
    @Column(name = "ValidComm")
    public String getValidComm() {
        return validComm;
    }

    public void setValidComm(String validComm) {
        this.validComm = validComm;
    }

    @Basic
    @Column(name = "FrozenComm")
    public String getFrozenComm() {
        return frozenComm;
    }

    public void setFrozenComm(String frozenComm) {
        this.frozenComm = frozenComm;
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
    @Column(name = "ObjType")
    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    @Basic
    @Column(name = "SWW")
    public String getSww() {
        return sww;
    }

    public void setSww(String sww) {
        this.sww = sww;
    }

    @Basic
    @Column(name = "Deleted")
    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    @Basic
    @Column(name = "DocEntry")
    public Integer getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(Integer docEntry) {
        this.docEntry = docEntry;
    }

    @Basic
    @Column(name = "ExpensAcct")
    public String getExpensAcct() {
        return expensAcct;
    }

    public void setExpensAcct(String expensAcct) {
        this.expensAcct = expensAcct;
    }

    @Basic
    @Column(name = "FrgnInAcct")
    public String getFrgnInAcct() {
        return frgnInAcct;
    }

    public void setFrgnInAcct(String frgnInAcct) {
        this.frgnInAcct = frgnInAcct;
    }

    @Basic
    @Column(name = "ShipType")
    public Short getShipType() {
        return shipType;
    }

    public void setShipType(Short shipType) {
        this.shipType = shipType;
    }

    @Basic
    @Column(name = "GLMethod")
    public String getGlMethod() {
        return glMethod;
    }

    public void setGlMethod(String glMethod) {
        this.glMethod = glMethod;
    }

    @Basic
    @Column(name = "ECInAcct")
    public String getEcInAcct() {
        return ecInAcct;
    }

    public void setEcInAcct(String ecInAcct) {
        this.ecInAcct = ecInAcct;
    }

    @Basic
    @Column(name = "FrgnExpAcc")
    public String getFrgnExpAcc() {
        return frgnExpAcc;
    }

    public void setFrgnExpAcc(String frgnExpAcc) {
        this.frgnExpAcc = frgnExpAcc;
    }

    @Basic
    @Column(name = "ECExpAcc")
    public String getEcExpAcc() {
        return ecExpAcc;
    }

    public void setEcExpAcc(String ecExpAcc) {
        this.ecExpAcc = ecExpAcc;
    }

    @Basic
    @Column(name = "TaxType")
    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    @Basic
    @Column(name = "ByWh")
    public String getByWh() {
        return byWh;
    }

    public void setByWh(String byWh) {
        this.byWh = byWh;
    }

    @Basic
    @Column(name = "WTLiable")
    public String getWtLiable() {
        return wtLiable;
    }

    public void setWtLiable(String wtLiable) {
        this.wtLiable = wtLiable;
    }

    @Basic
    @Column(name = "ItemType")
    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @Basic
    @Column(name = "WarrntTmpl")
    public String getWarrntTmpl() {
        return warrntTmpl;
    }

    public void setWarrntTmpl(String warrntTmpl) {
        this.warrntTmpl = warrntTmpl;
    }

    @Basic
    @Column(name = "BaseUnit")
    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
    }

    @Basic
    @Column(name = "CountryOrg")
    public String getCountryOrg() {
        return countryOrg;
    }

    public void setCountryOrg(String countryOrg) {
        this.countryOrg = countryOrg;
    }

    @Basic
    @Column(name = "StockValue")
    public Long getStockValue() {
        return stockValue;
    }

    public void setStockValue(Long stockValue) {
        this.stockValue = stockValue;
    }

    @Basic
    @Column(name = "Phantom")
    public String getPhantom() {
        return phantom;
    }

    public void setPhantom(String phantom) {
        this.phantom = phantom;
    }

    @Basic
    @Column(name = "IssueMthd")
    public String getIssueMthd() {
        return issueMthd;
    }

    public void setIssueMthd(String issueMthd) {
        this.issueMthd = issueMthd;
    }

    @Basic
    @Column(name = "FREE1")
    public String getFree1() {
        return free1;
    }

    public void setFree1(String free1) {
        this.free1 = free1;
    }

    @Basic
    @Column(name = "PricingPrc")
    public Long getPricingPrc() {
        return pricingPrc;
    }

    public void setPricingPrc(Long pricingPrc) {
        this.pricingPrc = pricingPrc;
    }

    @Basic
    @Column(name = "MngMethod")
    public String getMngMethod() {
        return mngMethod;
    }

    public void setMngMethod(String mngMethod) {
        this.mngMethod = mngMethod;
    }

    @Basic
    @Column(name = "ReorderPnt")
    public Long getReorderPnt() {
        return reorderPnt;
    }

    public void setReorderPnt(Long reorderPnt) {
        this.reorderPnt = reorderPnt;
    }

    @Basic
    @Column(name = "InvntryUom")
    public String getInvntryUom() {
        return invntryUom;
    }

    public void setInvntryUom(String invntryUom) {
        this.invntryUom = invntryUom;
    }

    @Basic
    @Column(name = "PlaningSys")
    public String getPlaningSys() {
        return planingSys;
    }

    public void setPlaningSys(String planingSys) {
        this.planingSys = planingSys;
    }

    @Basic
    @Column(name = "PrcrmntMtd")
    public String getPrcrmntMtd() {
        return prcrmntMtd;
    }

    public void setPrcrmntMtd(String prcrmntMtd) {
        this.prcrmntMtd = prcrmntMtd;
    }

    @Basic
    @Column(name = "OrdrIntrvl")
    public Short getOrdrIntrvl() {
        return ordrIntrvl;
    }

    public void setOrdrIntrvl(Short ordrIntrvl) {
        this.ordrIntrvl = ordrIntrvl;
    }

    @Basic
    @Column(name = "OrdrMulti")
    public Long getOrdrMulti() {
        return ordrMulti;
    }

    public void setOrdrMulti(Long ordrMulti) {
        this.ordrMulti = ordrMulti;
    }

    @Basic
    @Column(name = "MinOrdrQty")
    public Long getMinOrdrQty() {
        return minOrdrQty;
    }

    public void setMinOrdrQty(Long minOrdrQty) {
        this.minOrdrQty = minOrdrQty;
    }

    @Basic
    @Column(name = "LeadTime")
    public Integer getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(Integer leadTime) {
        this.leadTime = leadTime;
    }

    @Basic
    @Column(name = "IndirctTax")
    public String getIndirctTax() {
        return indirctTax;
    }

    public void setIndirctTax(String indirctTax) {
        this.indirctTax = indirctTax;
    }

    @Basic
    @Column(name = "TaxCodeAR")
    public String getTaxCodeAr() {
        return taxCodeAr;
    }

    public void setTaxCodeAr(String taxCodeAr) {
        this.taxCodeAr = taxCodeAr;
    }

    @Basic
    @Column(name = "TaxCodeAP")
    public String getTaxCodeAp() {
        return taxCodeAp;
    }

    public void setTaxCodeAp(String taxCodeAp) {
        this.taxCodeAp = taxCodeAp;
    }

    @Basic
    @Column(name = "OSvcCode")
    public Integer getoSvcCode() {
        return oSvcCode;
    }

    public void setoSvcCode(Integer oSvcCode) {
        this.oSvcCode = oSvcCode;
    }

    @Basic
    @Column(name = "ISvcCode")
    public Integer getiSvcCode() {
        return iSvcCode;
    }

    public void setiSvcCode(Integer iSvcCode) {
        this.iSvcCode = iSvcCode;
    }

    @Basic
    @Column(name = "ServiceGrp")
    public Integer getServiceGrp() {
        return serviceGrp;
    }

    public void setServiceGrp(Integer serviceGrp) {
        this.serviceGrp = serviceGrp;
    }

    @Basic
    @Column(name = "NCMCode")
    public Integer getNcmCode() {
        return ncmCode;
    }

    public void setNcmCode(Integer ncmCode) {
        this.ncmCode = ncmCode;
    }

    @Basic
    @Column(name = "MatType")
    public String getMatType() {
        return matType;
    }

    public void setMatType(String matType) {
        this.matType = matType;
    }

    @Basic
    @Column(name = "MatGrp")
    public Integer getMatGrp() {
        return matGrp;
    }

    public void setMatGrp(Integer matGrp) {
        this.matGrp = matGrp;
    }

    @Basic
    @Column(name = "ProductSrc")
    public String getProductSrc() {
        return productSrc;
    }

    public void setProductSrc(String productSrc) {
        this.productSrc = productSrc;
    }

    @Basic
    @Column(name = "ServiceCtg")
    public Integer getServiceCtg() {
        return serviceCtg;
    }

    public void setServiceCtg(Integer serviceCtg) {
        this.serviceCtg = serviceCtg;
    }

    @Basic
    @Column(name = "ItemClass")
    public String getItemClass() {
        return itemClass;
    }

    public void setItemClass(String itemClass) {
        this.itemClass = itemClass;
    }

    @Basic
    @Column(name = "Excisable")
    public String getExcisable() {
        return excisable;
    }

    public void setExcisable(String excisable) {
        this.excisable = excisable;
    }

    @Basic
    @Column(name = "ChapterID")
    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    @Basic
    @Column(name = "NotifyASN")
    public String getNotifyAsn() {
        return notifyAsn;
    }

    public void setNotifyAsn(String notifyAsn) {
        this.notifyAsn = notifyAsn;
    }

    @Basic
    @Column(name = "ProAssNum")
    public String getProAssNum() {
        return proAssNum;
    }

    public void setProAssNum(String proAssNum) {
        this.proAssNum = proAssNum;
    }

    @Basic
    @Column(name = "AssblValue")
    public Long getAssblValue() {
        return assblValue;
    }

    public void setAssblValue(Long assblValue) {
        this.assblValue = assblValue;
    }

    @Basic
    @Column(name = "DNFEntry")
    public Integer getDnfEntry() {
        return dnfEntry;
    }

    public void setDnfEntry(Integer dnfEntry) {
        this.dnfEntry = dnfEntry;
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
    @Column(name = "Spec")
    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    @Basic
    @Column(name = "TaxCtg")
    public String getTaxCtg() {
        return taxCtg;
    }

    public void setTaxCtg(String taxCtg) {
        this.taxCtg = taxCtg;
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
    @Column(name = "Number")
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Basic
    @Column(name = "FuelCode")
    public Integer getFuelCode() {
        return fuelCode;
    }

    public void setFuelCode(Integer fuelCode) {
        this.fuelCode = fuelCode;
    }

    @Basic
    @Column(name = "BeverTblC")
    public String getBeverTblC() {
        return beverTblC;
    }

    public void setBeverTblC(String beverTblC) {
        this.beverTblC = beverTblC;
    }

    @Basic
    @Column(name = "BeverGrpC")
    public String getBeverGrpC() {
        return beverGrpC;
    }

    public void setBeverGrpC(String beverGrpC) {
        this.beverGrpC = beverGrpC;
    }

    @Basic
    @Column(name = "BeverTM")
    public Integer getBeverTm() {
        return beverTm;
    }

    public void setBeverTm(Integer beverTm) {
        this.beverTm = beverTm;
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
    @Column(name = "ToleranDay")
    public Integer getToleranDay() {
        return toleranDay;
    }

    public void setToleranDay(Integer toleranDay) {
        this.toleranDay = toleranDay;
    }

    @Basic
    @Column(name = "UgpEntry")
    public String getUgpEntry() {
        return ugpEntry;
    }

    public void setUgpEntry(String ugpEntry) {
        this.ugpEntry = ugpEntry;
    }

    @Basic
    @Column(name = "PUoMEntry")
    public Integer getpUoMEntry() {
        return pUoMEntry;
    }

    public void setpUoMEntry(Integer pUoMEntry) {
        this.pUoMEntry = pUoMEntry;
    }

    @Basic
    @Column(name = "SUoMEntry")
    public Integer getsUoMEntry() {
        return sUoMEntry;
    }

    public void setsUoMEntry(Integer sUoMEntry) {
        this.sUoMEntry = sUoMEntry;
    }

    @Basic
    @Column(name = "IUoMEntry")
    public Integer getiUoMEntry() {
        return iUoMEntry;
    }

    public void setiUoMEntry(Integer iUoMEntry) {
        this.iUoMEntry = iUoMEntry;
    }

    @Basic
    @Column(name = "IssuePriBy")
    public Short getIssuePriBy() {
        return issuePriBy;
    }

    public void setIssuePriBy(Short issuePriBy) {
        this.issuePriBy = issuePriBy;
    }

    @Basic
    @Column(name = "AssetClass")
    public String getAssetClass() {
        return assetClass;
    }

    public void setAssetClass(String assetClass) {
        this.assetClass = assetClass;
    }

    @Basic
    @Column(name = "AssetGroup")
    public String getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(String assetGroup) {
        this.assetGroup = assetGroup;
    }

    @Basic
    @Column(name = "InventryNo")
    public String getInventryNo() {
        return inventryNo;
    }

    public void setInventryNo(String inventryNo) {
        this.inventryNo = inventryNo;
    }

    @Basic
    @Column(name = "Technician")
    public Integer getTechnician() {
        return technician;
    }

    public void setTechnician(Integer technician) {
        this.technician = technician;
    }

    @Basic
    @Column(name = "Employee")
    public Integer getEmployee() {
        return employee;
    }

    public void setEmployee(Integer employee) {
        this.employee = employee;
    }

    @Basic
    @Column(name = "Location")
    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    @Basic
    @Column(name = "StatAsset")
    public String getStatAsset() {
        return statAsset;
    }

    public void setStatAsset(String statAsset) {
        this.statAsset = statAsset;
    }

    @Basic
    @Column(name = "Cession")
    public String getCession() {
        return cession;
    }

    public void setCession(String cession) {
        this.cession = cession;
    }

    @Basic
    @Column(name = "DeacAftUL")
    public String getDeacAftUl() {
        return deacAftUl;
    }

    public void setDeacAftUl(String deacAftUl) {
        this.deacAftUl = deacAftUl;
    }

    @Basic
    @Column(name = "AsstStatus")
    public String getAsstStatus() {
        return asstStatus;
    }

    public void setAsstStatus(String asstStatus) {
        this.asstStatus = asstStatus;
    }

    @Basic
    @Column(name = "CapDate")
    public Date getCapDate() {
        return capDate;
    }

    public void setCapDate(Date capDate) {
        this.capDate = capDate;
    }

    @Basic
    @Column(name = "AcqDate")
    public Date getAcqDate() {
        return acqDate;
    }

    public void setAcqDate(Date acqDate) {
        this.acqDate = acqDate;
    }

    @Basic
    @Column(name = "RetDate")
    public Date getRetDate() {
        return retDate;
    }

    public void setRetDate(Date retDate) {
        this.retDate = retDate;
    }

    @Basic
    @Column(name = "GLPickMeth")
    public String getGlPickMeth() {
        return glPickMeth;
    }

    public void setGlPickMeth(String glPickMeth) {
        this.glPickMeth = glPickMeth;
    }

    @Basic
    @Column(name = "NoDiscount")
    public String getNoDiscount() {
        return noDiscount;
    }

    public void setNoDiscount(String noDiscount) {
        this.noDiscount = noDiscount;
    }

    @Basic
    @Column(name = "MgrByQty")
    public String getMgrByQty() {
        return mgrByQty;
    }

    public void setMgrByQty(String mgrByQty) {
        this.mgrByQty = mgrByQty;
    }

    @Basic
    @Column(name = "AssetRmk1")
    public String getAssetRmk1() {
        return assetRmk1;
    }

    public void setAssetRmk1(String assetRmk1) {
        this.assetRmk1 = assetRmk1;
    }

    @Basic
    @Column(name = "AssetRmk2")
    public String getAssetRmk2() {
        return assetRmk2;
    }

    public void setAssetRmk2(String assetRmk2) {
        this.assetRmk2 = assetRmk2;
    }

    @Basic
    @Column(name = "AssetAmnt1")
    public Long getAssetAmnt1() {
        return assetAmnt1;
    }

    public void setAssetAmnt1(Long assetAmnt1) {
        this.assetAmnt1 = assetAmnt1;
    }

    @Basic
    @Column(name = "AssetAmnt2")
    public Long getAssetAmnt2() {
        return assetAmnt2;
    }

    public void setAssetAmnt2(Long assetAmnt2) {
        this.assetAmnt2 = assetAmnt2;
    }

    @Basic
    @Column(name = "DeprGroup")
    public String getDeprGroup() {
        return deprGroup;
    }

    public void setDeprGroup(String deprGroup) {
        this.deprGroup = deprGroup;
    }

    @Basic
    @Column(name = "AssetSerNo")
    public String getAssetSerNo() {
        return assetSerNo;
    }

    public void setAssetSerNo(String assetSerNo) {
        this.assetSerNo = assetSerNo;
    }

    @Basic
    @Column(name = "CntUnitMsr")
    public String getCntUnitMsr() {
        return cntUnitMsr;
    }

    public void setCntUnitMsr(String cntUnitMsr) {
        this.cntUnitMsr = cntUnitMsr;
    }

    @Basic
    @Column(name = "NumInCnt")
    public Long getNumInCnt() {
        return numInCnt;
    }

    public void setNumInCnt(Long numInCnt) {
        this.numInCnt = numInCnt;
    }

    @Basic
    @Column(name = "INUoMEntry")
    public Integer getInUoMEntry() {
        return inUoMEntry;
    }

    public void setInUoMEntry(Integer inUoMEntry) {
        this.inUoMEntry = inUoMEntry;
    }

    @Basic
    @Column(name = "OneBOneRec")
    public String getOneBOneRec() {
        return oneBOneRec;
    }

    public void setOneBOneRec(String oneBOneRec) {
        this.oneBOneRec = oneBOneRec;
    }

    @Basic
    @Column(name = "RuleCode")
    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    @Basic
    @Column(name = "ScsCode")
    public String getScsCode() {
        return scsCode;
    }

    public void setScsCode(String scsCode) {
        this.scsCode = scsCode;
    }

    @Basic
    @Column(name = "SpProdType")
    public String getSpProdType() {
        return spProdType;
    }

    public void setSpProdType(String spProdType) {
        this.spProdType = spProdType;
    }

    @Basic
    @Column(name = "IWeight1")
    public Long getiWeight1() {
        return iWeight1;
    }

    public void setiWeight1(Long iWeight1) {
        this.iWeight1 = iWeight1;
    }

    @Basic
    @Column(name = "IWght1Unit")
    public Short getiWght1Unit() {
        return iWght1Unit;
    }

    public void setiWght1Unit(Short iWght1Unit) {
        this.iWght1Unit = iWght1Unit;
    }

    @Basic
    @Column(name = "IWeight2")
    public Long getiWeight2() {
        return iWeight2;
    }

    public void setiWeight2(Long iWeight2) {
        this.iWeight2 = iWeight2;
    }

    @Basic
    @Column(name = "IWght2Unit")
    public Short getiWght2Unit() {
        return iWght2Unit;
    }

    public void setiWght2Unit(Short iWght2Unit) {
        this.iWght2Unit = iWght2Unit;
    }

    @Basic
    @Column(name = "CompoWH")
    public String getCompoWh() {
        return compoWh;
    }

    public void setCompoWh(String compoWh) {
        this.compoWh = compoWh;
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
    @Column(name = "VirtAstItm")
    public String getVirtAstItm() {
        return virtAstItm;
    }

    public void setVirtAstItm(String virtAstItm) {
        this.virtAstItm = virtAstItm;
    }

    @Basic
    @Column(name = "SouVirAsst")
    public String getSouVirAsst() {
        return souVirAsst;
    }

    public void setSouVirAsst(String souVirAsst) {
        this.souVirAsst = souVirAsst;
    }

    @Basic
    @Column(name = "InCostRoll")
    public String getInCostRoll() {
        return inCostRoll;
    }

    public void setInCostRoll(String inCostRoll) {
        this.inCostRoll = inCostRoll;
    }

    @Basic
    @Column(name = "PrdStdCst")
    public Long getPrdStdCst() {
        return prdStdCst;
    }

    public void setPrdStdCst(Long prdStdCst) {
        this.prdStdCst = prdStdCst;
    }

    @Basic
    @Column(name = "EnAstSeri")
    public String getEnAstSeri() {
        return enAstSeri;
    }

    public void setEnAstSeri(String enAstSeri) {
        this.enAstSeri = enAstSeri;
    }

    @Basic
    @Column(name = "LinkRsc")
    public String getLinkRsc() {
        return linkRsc;
    }

    public void setLinkRsc(String linkRsc) {
        this.linkRsc = linkRsc;
    }

    @Basic
    @Column(name = "OnHldPert")
    public Long getOnHldPert() {
        return onHldPert;
    }

    public void setOnHldPert(Long onHldPert) {
        this.onHldPert = onHldPert;
    }

    @Basic
    @Column(name = "onHldLimt")
    public Long getOnHldLimt() {
        return onHldLimt;
    }

    public void setOnHldLimt(Long onHldLimt) {
        this.onHldLimt = onHldLimt;
    }

    @Basic
    @Column(name = "GSTRelevnt")
    public String getGstRelevnt() {
        return gstRelevnt;
    }

    public void setGstRelevnt(String gstRelevnt) {
        this.gstRelevnt = gstRelevnt;
    }

    @Basic
    @Column(name = "SACEntry")
    public Integer getSacEntry() {
        return sacEntry;
    }

    public void setSacEntry(Integer sacEntry) {
        this.sacEntry = sacEntry;
    }

    @Basic
    @Column(name = "GstTaxCtg")
    public String getGstTaxCtg() {
        return gstTaxCtg;
    }

    public void setGstTaxCtg(String gstTaxCtg) {
        this.gstTaxCtg = gstTaxCtg;
    }

    @Basic
    @Column(name = "U_IGroup")
    public String getuIGroup() {
        return uIGroup;
    }

    public void setuIGroup(String uIGroup) {
        this.uIGroup = uIGroup;
    }

    @Basic
    @Column(name = "U_SUBGR")
    public String getuSubgr() {
        return uSubgr;
    }

    public void setuSubgr(String uSubgr) {
        this.uSubgr = uSubgr;
    }

    @Basic
    @Column(name = "U_RDCODE")
    public String getuRdcode() {
        return uRdcode;
    }

    public void setuRdcode(String uRdcode) {
        this.uRdcode = uRdcode;
    }

    @Basic
    @Column(name = "U_IGroupName")
    public String getuIGroupName() {
        return uIGroupName;
    }

    public void setuIGroupName(String uIGroupName) {
        this.uIGroupName = uIGroupName;
    }

    @Basic
    @Column(name = "U_SUBGRName")
    public String getuSubgrName() {
        return uSubgrName;
    }

    public void setuSubgrName(String uSubgrName) {
        this.uSubgrName = uSubgrName;
    }

    @Basic
    @Column(name = "U_ProductBranch")
    public String getuProductBranch() {
        return uProductBranch;
    }

    public void setuProductBranch(String uProductBranch) {
        this.uProductBranch = uProductBranch;
    }

    @Basic
    @Column(name = "U_ProductGroup")
    public String getuProductGroup() {
        return uProductGroup;
    }

    public void setuProductGroup(String uProductGroup) {
        this.uProductGroup = uProductGroup;
    }

    @Basic
    @Column(name = "U_Forcast")
    public String getuForcast() {
        return uForcast;
    }

    public void setuForcast(String uForcast) {
        this.uForcast = uForcast;
    }

    @Basic
    @Column(name = "U_ShortName")
    public String getuShortName() {
        return uShortName;
    }

    public void setuShortName(String uShortName) {
        this.uShortName = uShortName;
    }

    @Column(name = "U_TechName")
    @Lob
    public String getuTechName() {
        return uTechName;
    }

    public void setuTechName(String uTechName) {
        this.uTechName = uTechName;
    }

    @Basic
    @Column(name = "U_DGroup")
    public String getuDGroup() {
        return uDGroup;
    }

    public void setuDGroup(String uDGroup) {
        this.uDGroup = uDGroup;
    }

    @Basic
    @Column(name = "U_DGroupName")
    public String getuDGroupName() {
        return uDGroupName;
    }

    public void setuDGroupName(String uDGroupName) {
        this.uDGroupName = uDGroupName;
    }

    @Basic
    @Column(name = "U_package")
    public Long getuPackage() {
        return uPackage;
    }

    public void setuPackage(Long uPackage) {
        this.uPackage = uPackage;
    }

    @Basic
    @Column(name = "U_Factory")
    public String getuFactory() {
        return uFactory;
    }

    public void setuFactory(String uFactory) {
        this.uFactory = uFactory;
    }

    @Basic
    @Column(name = "U_CDGT")
    public String getuCDGT() {
        return uCDGT;
    }

    public void setuCDGT(String uCDGT) {
        this.uCDGT = uCDGT;
    }

    @Basic
    @Column(name = "U_Type")
    public String getuType() {
        return uType;
    }

    public void setuType(String uType) {
        this.uType = uType;
    }

    @Basic
    @Column(name = "U_CLVL")
    public String getuCLVL() {
        return uCLVL;
    }

    public void setuCLVL(String uCLVL) {
        this.uCLVL = uCLVL;
    }

    @Basic
    @Column(name = "U_DaiOng")
    public Long getuDaiOng() {
        return uDaiOng;
    }

    public void setuDaiOng(Long uDaiOng) {
        this.uDaiOng = uDaiOng;
    }

    @Basic
    @Column(name = "U_DaiOng1")
    public Long getuDaiOng1() {
        return uDaiOng1;
    }

    public void setuDaiOng1(Long uDaiOng1) {
        this.uDaiOng1 = uDaiOng1;
    }

    @Basic
    @Column(name = "U_HeSo")
    public Long getuHeSo() {
        return uHeSo;
    }

    public void setuHeSo(Long uHeSo) {
        this.uHeSo = uHeSo;
    }

    @Basic
    @Column(name = "U_KH")
    public String getuKH() {
        return uKH;
    }

    public void setuKH(String uKH) {
        this.uKH = uKH;
    }

    @Basic
    @Column(name = "U_sothe")
    public String getuSoThe() {
        return uSoThe;
    }

    public void setuSoThe(String uSoThe) {
        this.uSoThe = uSoThe;
    }

    //    @Basic
//    @Column(name = "U_MSL")
//    public String getuMsl() {
//        return uMsl;
//    }
//
//    public void setuMsl(String uMsl) {
//        this.uMsl = uMsl;
//    }
//
//    @Basic
//    @Column(name = "U_PartNumber")
//    public String getuPartNumber() {
//        return uPartNumber;
//    }
//
//    public void setuPartNumber(String uPartNumber) {
//        this.uPartNumber = uPartNumber;
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        OitmEntity that = (OitmEntity) o;
//        return Objects.equals(itemCode, that.itemCode) && Objects.equals(itemName, that.itemName) && Objects.equals(frgnName, that.frgnName) && Objects.equals(itmsGrpCod, that.itmsGrpCod) && Objects.equals(cstGrpCode, that.cstGrpCode) && Objects.equals(vatGourpSa, that.vatGourpSa) && Objects.equals(codeBars, that.codeBars) && Objects.equals(vatLiable, that.vatLiable) && Objects.equals(prchseItem, that.prchseItem) && Objects.equals(sellItem, that.sellItem) && Objects.equals(invntItem, that.invntItem) && Objects.equals(onHand, that.onHand) && Objects.equals(isCommited, that.isCommited) && Objects.equals(onOrder, that.onOrder) && Objects.equals(incomeAcct, that.incomeAcct) && Objects.equals(exmptIncom, that.exmptIncom) && Objects.equals(maxLevel, that.maxLevel) && Objects.equals(dfltWh, that.dfltWh) && Objects.equals(cardCode, that.cardCode) && Objects.equals(suppCatNum, that.suppCatNum) && Objects.equals(buyUnitMsr, that.buyUnitMsr) && Objects.equals(numInBuy, that.numInBuy) && Objects.equals(reorderQty, that.reorderQty) && Objects.equals(minLevel, that.minLevel) && Objects.equals(lstEvlPric, that.lstEvlPric) && Objects.equals(lstEvlDate, that.lstEvlDate) && Objects.equals(customPer, that.customPer) && Objects.equals(canceled, that.canceled) && Objects.equals(mnufctTime, that.mnufctTime) && Objects.equals(wholSlsTax, that.wholSlsTax) && Objects.equals(retilrTax, that.retilrTax) && Objects.equals(spcialDisc, that.spcialDisc) && Objects.equals(dscountCod, that.dscountCod) && Objects.equals(trackSales, that.trackSales) && Objects.equals(salUnitMsr, that.salUnitMsr) && Objects.equals(numInSale, that.numInSale) && Objects.equals(consig, that.consig) && Objects.equals(queryGroup, that.queryGroup) && Objects.equals(counted, that.counted) && Objects.equals(openBlnc, that.openBlnc) && Objects.equals(evalSystem, that.evalSystem) && Objects.equals(userSign, that.userSign) && Objects.equals(free, that.free) && Objects.equals(picturName, that.picturName) && Objects.equals(transfered, that.transfered) && Objects.equals(blncTrnsfr, that.blncTrnsfr) && Objects.equals(userText, that.userText) && Objects.equals(serialNum, that.serialNum) && Objects.equals(commisPcnt, that.commisPcnt) && Objects.equals(commisSum, that.commisSum) && Objects.equals(commisGrp, that.commisGrp) && Objects.equals(treeType, that.treeType) && Objects.equals(treeQty, that.treeQty) && Objects.equals(lastPurPrc, that.lastPurPrc) && Objects.equals(lastPurCur, that.lastPurCur) && Objects.equals(lastPurDat, that.lastPurDat) && Objects.equals(exitCur, that.exitCur) && Objects.equals(exitPrice, that.exitPrice) && Objects.equals(exitWh, that.exitWh) && Objects.equals(assetItem, that.assetItem) && Objects.equals(wasCounted, that.wasCounted) && Objects.equals(manSerNum, that.manSerNum) && Objects.equals(sHeight1, that.sHeight1) && Objects.equals(sHght1Unit, that.sHght1Unit) && Objects.equals(sHeight2, that.sHeight2) && Objects.equals(sHght2Unit, that.sHght2Unit) && Objects.equals(sWidth1, that.sWidth1) && Objects.equals(sWdth1Unit, that.sWdth1Unit) && Objects.equals(sWidth2, that.sWidth2) && Objects.equals(sWdth2Unit, that.sWdth2Unit) && Objects.equals(sLength1, that.sLength1) && Objects.equals(sLen1Unit, that.sLen1Unit) && Objects.equals(slength2, that.slength2) && Objects.equals(sLen2Unit, that.sLen2Unit) && Objects.equals(sVolume, that.sVolume) && Objects.equals(sVolUnit, that.sVolUnit) && Objects.equals(sWeight1, that.sWeight1) && Objects.equals(sWght1Unit, that.sWght1Unit) && Objects.equals(sWeight2, that.sWeight2) && Objects.equals(sWght2Unit, that.sWght2Unit) && Objects.equals(bHeight1, that.bHeight1) && Objects.equals(bHght1Unit, that.bHght1Unit) && Objects.equals(bHeight2, that.bHeight2) && Objects.equals(bHght2Unit, that.bHght2Unit) && Objects.equals(bWidth1, that.bWidth1) && Objects.equals(bWdth1Unit, that.bWdth1Unit) && Objects.equals(bWidth2, that.bWidth2) && Objects.equals(bWdth2Unit, that.bWdth2Unit) && Objects.equals(bLength1, that.bLength1) && Objects.equals(bLen1Unit, that.bLen1Unit) && Objects.equals(blength2, that.blength2) && Objects.equals(bLen2Unit, that.bLen2Unit) && Objects.equals(bVolume, that.bVolume) && Objects.equals(bVolUnit, that.bVolUnit) && Objects.equals(bWeight1, that.bWeight1) && Objects.equals(bWght1Unit, that.bWght1Unit) && Objects.equals(bWeight2, that.bWeight2) && Objects.equals(bWght2Unit, that.bWght2Unit) && Objects.equals(fixCurrCms, that.fixCurrCms) && Objects.equals(firmCode, that.firmCode) && Objects.equals(lstSalDate, that.lstSalDate) && Objects.equals(qryGroup1, that.qryGroup1) && Objects.equals(qryGroup2, that.qryGroup2) && Objects.equals(qryGroup3, that.qryGroup3) && Objects.equals(qryGroup4, that.qryGroup4) && Objects.equals(qryGroup5, that.qryGroup5) && Objects.equals(qryGroup6, that.qryGroup6) && Objects.equals(qryGroup7, that.qryGroup7) && Objects.equals(qryGroup8, that.qryGroup8) && Objects.equals(qryGroup9, that.qryGroup9) && Objects.equals(qryGroup10, that.qryGroup10) && Objects.equals(qryGroup11, that.qryGroup11) && Objects.equals(qryGroup12, that.qryGroup12) && Objects.equals(qryGroup13, that.qryGroup13) && Objects.equals(qryGroup14, that.qryGroup14) && Objects.equals(qryGroup15, that.qryGroup15) && Objects.equals(qryGroup16, that.qryGroup16) && Objects.equals(qryGroup17, that.qryGroup17) && Objects.equals(qryGroup18, that.qryGroup18) && Objects.equals(qryGroup19, that.qryGroup19) && Objects.equals(qryGroup20, that.qryGroup20) && Objects.equals(qryGroup21, that.qryGroup21) && Objects.equals(qryGroup22, that.qryGroup22) && Objects.equals(qryGroup23, that.qryGroup23) && Objects.equals(qryGroup24, that.qryGroup24) && Objects.equals(qryGroup25, that.qryGroup25) && Objects.equals(qryGroup26, that.qryGroup26) && Objects.equals(qryGroup27, that.qryGroup27) && Objects.equals(qryGroup28, that.qryGroup28) && Objects.equals(qryGroup29, that.qryGroup29) && Objects.equals(qryGroup30, that.qryGroup30) && Objects.equals(qryGroup31, that.qryGroup31) && Objects.equals(qryGroup32, that.qryGroup32) && Objects.equals(qryGroup33, that.qryGroup33) && Objects.equals(qryGroup34, that.qryGroup34) && Objects.equals(qryGroup35, that.qryGroup35) && Objects.equals(qryGroup36, that.qryGroup36) && Objects.equals(qryGroup37, that.qryGroup37) && Objects.equals(qryGroup38, that.qryGroup38) && Objects.equals(qryGroup39, that.qryGroup39) && Objects.equals(qryGroup40, that.qryGroup40) && Objects.equals(qryGroup41, that.qryGroup41) && Objects.equals(qryGroup42, that.qryGroup42) && Objects.equals(qryGroup43, that.qryGroup43) && Objects.equals(qryGroup44, that.qryGroup44) && Objects.equals(qryGroup45, that.qryGroup45) && Objects.equals(qryGroup46, that.qryGroup46) && Objects.equals(qryGroup47, that.qryGroup47) && Objects.equals(qryGroup48, that.qryGroup48) && Objects.equals(qryGroup49, that.qryGroup49) && Objects.equals(qryGroup50, that.qryGroup50) && Objects.equals(qryGroup51, that.qryGroup51) && Objects.equals(qryGroup52, that.qryGroup52) && Objects.equals(qryGroup53, that.qryGroup53) && Objects.equals(qryGroup54, that.qryGroup54) && Objects.equals(qryGroup55, that.qryGroup55) && Objects.equals(qryGroup56, that.qryGroup56) && Objects.equals(qryGroup57, that.qryGroup57) && Objects.equals(qryGroup58, that.qryGroup58) && Objects.equals(qryGroup59, that.qryGroup59) && Objects.equals(qryGroup60, that.qryGroup60) && Objects.equals(qryGroup61, that.qryGroup61) && Objects.equals(qryGroup62, that.qryGroup62) && Objects.equals(qryGroup63, that.qryGroup63) && Objects.equals(qryGroup64, that.qryGroup64) && Objects.equals(createDate, that.createDate) && Objects.equals(updateDate, that.updateDate) && Objects.equals(exportCode, that.exportCode) && Objects.equals(salFactor1, that.salFactor1) && Objects.equals(salFactor2, that.salFactor2) && Objects.equals(salFactor3, that.salFactor3) && Objects.equals(salFactor4, that.salFactor4) && Objects.equals(purFactor1, that.purFactor1) && Objects.equals(purFactor2, that.purFactor2) && Objects.equals(purFactor3, that.purFactor3) && Objects.equals(purFactor4, that.purFactor4) && Objects.equals(salFormula, that.salFormula) && Objects.equals(purFormula, that.purFormula) && Objects.equals(vatGroupPu, that.vatGroupPu) && Objects.equals(avgPrice, that.avgPrice) && Objects.equals(purPackMsr, that.purPackMsr) && Objects.equals(purPackUn, that.purPackUn) && Objects.equals(salPackMsr, that.salPackMsr) && Objects.equals(salPackUn, that.salPackUn) && Objects.equals(scnCounter, that.scnCounter) && Objects.equals(manBtchNum, that.manBtchNum) && Objects.equals(manOutOnly, that.manOutOnly) && Objects.equals(dataSource, that.dataSource) && Objects.equals(validFor, that.validFor) && Objects.equals(validFrom, that.validFrom) && Objects.equals(validTo, that.validTo) && Objects.equals(frozenFor, that.frozenFor) && Objects.equals(frozenFrom, that.frozenFrom) && Objects.equals(frozenTo, that.frozenTo) && Objects.equals(blockOut, that.blockOut) && Objects.equals(validComm, that.validComm) && Objects.equals(frozenComm, that.frozenComm) && Objects.equals(logInstanc, that.logInstanc) && Objects.equals(objType, that.objType) && Objects.equals(sww, that.sww) && Objects.equals(deleted, that.deleted) && Objects.equals(docEntry, that.docEntry) && Objects.equals(expensAcct, that.expensAcct) && Objects.equals(frgnInAcct, that.frgnInAcct) && Objects.equals(shipType, that.shipType) && Objects.equals(glMethod, that.glMethod) && Objects.equals(ecInAcct, that.ecInAcct) && Objects.equals(frgnExpAcc, that.frgnExpAcc) && Objects.equals(ecExpAcc, that.ecExpAcc) && Objects.equals(taxType, that.taxType) && Objects.equals(byWh, that.byWh) && Objects.equals(wtLiable, that.wtLiable) && Objects.equals(itemType, that.itemType) && Objects.equals(warrntTmpl, that.warrntTmpl) && Objects.equals(baseUnit, that.baseUnit) && Objects.equals(countryOrg, that.countryOrg) && Objects.equals(stockValue, that.stockValue) && Objects.equals(phantom, that.phantom) && Objects.equals(issueMthd, that.issueMthd) && Objects.equals(free1, that.free1) && Objects.equals(pricingPrc, that.pricingPrc) && Objects.equals(mngMethod, that.mngMethod) && Objects.equals(reorderPnt, that.reorderPnt) && Objects.equals(invntryUom, that.invntryUom) && Objects.equals(planingSys, that.planingSys) && Objects.equals(prcrmntMtd, that.prcrmntMtd) && Objects.equals(ordrIntrvl, that.ordrIntrvl) && Objects.equals(ordrMulti, that.ordrMulti) && Objects.equals(minOrdrQty, that.minOrdrQty) && Objects.equals(leadTime, that.leadTime) && Objects.equals(indirctTax, that.indirctTax) && Objects.equals(taxCodeAr, that.taxCodeAr) && Objects.equals(taxCodeAp, that.taxCodeAp) && Objects.equals(oSvcCode, that.oSvcCode) && Objects.equals(iSvcCode, that.iSvcCode) && Objects.equals(serviceGrp, that.serviceGrp) && Objects.equals(ncmCode, that.ncmCode) && Objects.equals(matType, that.matType) && Objects.equals(matGrp, that.matGrp) && Objects.equals(productSrc, that.productSrc) && Objects.equals(serviceCtg, that.serviceCtg) && Objects.equals(itemClass, that.itemClass) && Objects.equals(excisable, that.excisable) && Objects.equals(chapterId, that.chapterId) && Objects.equals(notifyAsn, that.notifyAsn) && Objects.equals(proAssNum, that.proAssNum) && Objects.equals(assblValue, that.assblValue) && Objects.equals(dnfEntry, that.dnfEntry) && Objects.equals(userSign2, that.userSign2) && Objects.equals(spec, that.spec) && Objects.equals(taxCtg, that.taxCtg) && Objects.equals(series, that.series) && Objects.equals(number, that.number) && Objects.equals(fuelCode, that.fuelCode) && Objects.equals(beverTblC, that.beverTblC) && Objects.equals(beverGrpC, that.beverGrpC) && Objects.equals(beverTm, that.beverTm) && Objects.equals(attachment, that.attachment) && Objects.equals(atcEntry, that.atcEntry) && Objects.equals(toleranDay, that.toleranDay) && Objects.equals(ugpEntry, that.ugpEntry) && Objects.equals(pUoMEntry, that.pUoMEntry) && Objects.equals(sUoMEntry, that.sUoMEntry) && Objects.equals(iUoMEntry, that.iUoMEntry) && Objects.equals(issuePriBy, that.issuePriBy) && Objects.equals(assetClass, that.assetClass) && Objects.equals(assetGroup, that.assetGroup) && Objects.equals(inventryNo, that.inventryNo) && Objects.equals(technician, that.technician) && Objects.equals(employee, that.employee) && Objects.equals(location, that.location) && Objects.equals(statAsset, that.statAsset) && Objects.equals(cession, that.cession) && Objects.equals(deacAftUl, that.deacAftUl) && Objects.equals(asstStatus, that.asstStatus) && Objects.equals(capDate, that.capDate) && Objects.equals(acqDate, that.acqDate) && Objects.equals(retDate, that.retDate) && Objects.equals(glPickMeth, that.glPickMeth) && Objects.equals(noDiscount, that.noDiscount) && Objects.equals(mgrByQty, that.mgrByQty) && Objects.equals(assetRmk1, that.assetRmk1) && Objects.equals(assetRmk2, that.assetRmk2) && Objects.equals(assetAmnt1, that.assetAmnt1) && Objects.equals(assetAmnt2, that.assetAmnt2) && Objects.equals(deprGroup, that.deprGroup) && Objects.equals(assetSerNo, that.assetSerNo) && Objects.equals(cntUnitMsr, that.cntUnitMsr) && Objects.equals(numInCnt, that.numInCnt) && Objects.equals(inUoMEntry, that.inUoMEntry) && Objects.equals(oneBOneRec, that.oneBOneRec) && Objects.equals(ruleCode, that.ruleCode) && Objects.equals(scsCode, that.scsCode) && Objects.equals(spProdType, that.spProdType) && Objects.equals(iWeight1, that.iWeight1) && Objects.equals(iWght1Unit, that.iWght1Unit) && Objects.equals(iWeight2, that.iWeight2) && Objects.equals(iWght2Unit, that.iWght2Unit) && Objects.equals(compoWh, that.compoWh) && Objects.equals(createTs, that.createTs) && Objects.equals(updateTs, that.updateTs) && Objects.equals(virtAstItm, that.virtAstItm) && Objects.equals(souVirAsst, that.souVirAsst) && Objects.equals(inCostRoll, that.inCostRoll) && Objects.equals(prdStdCst, that.prdStdCst) && Objects.equals(enAstSeri, that.enAstSeri) && Objects.equals(linkRsc, that.linkRsc) && Objects.equals(onHldPert, that.onHldPert) && Objects.equals(onHldLimt, that.onHldLimt) && Objects.equals(gstRelevnt, that.gstRelevnt) && Objects.equals(sacEntry, that.sacEntry) && Objects.equals(gstTaxCtg, that.gstTaxCtg) && Objects.equals(uIGroup, that.uIGroup) && Objects.equals(uSubgr, that.uSubgr) && Objects.equals(uRdcode, that.uRdcode) && Objects.equals(uIGroupName, that.uIGroupName) && Objects.equals(uSubgrName, that.uSubgrName) && Objects.equals(uProductBranch, that.uProductBranch) && Objects.equals(uProductGroup, that.uProductGroup) && Objects.equals(uForcast, that.uForcast) && Objects.equals(uShortName, that.uShortName) && Objects.equals(uTechName, that.uTechName) && Objects.equals(uDGroup, that.uDGroup) && Objects.equals(uDGroupName, that.uDGroupName) && Objects.equals(uPackage, that.uPackage) && Objects.equals(uMsl, that.uMsl) && Objects.equals(uPartNumber, that.uPartNumber);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(itemCode, itemName, frgnName, itmsGrpCod, cstGrpCode, vatGourpSa, codeBars, vatLiable, prchseItem, sellItem, invntItem, onHand, isCommited, onOrder, incomeAcct, exmptIncom, maxLevel, dfltWh, cardCode, suppCatNum, buyUnitMsr, numInBuy, reorderQty, minLevel, lstEvlPric, lstEvlDate, customPer, canceled, mnufctTime, wholSlsTax, retilrTax, spcialDisc, dscountCod, trackSales, salUnitMsr, numInSale, consig, queryGroup, counted, openBlnc, evalSystem, userSign, free, picturName, transfered, blncTrnsfr, userText, serialNum, commisPcnt, commisSum, commisGrp, treeType, treeQty, lastPurPrc, lastPurCur, lastPurDat, exitCur, exitPrice, exitWh, assetItem, wasCounted, manSerNum, sHeight1, sHght1Unit, sHeight2, sHght2Unit, sWidth1, sWdth1Unit, sWidth2, sWdth2Unit, sLength1, sLen1Unit, slength2, sLen2Unit, sVolume, sVolUnit, sWeight1, sWght1Unit, sWeight2, sWght2Unit, bHeight1, bHght1Unit, bHeight2, bHght2Unit, bWidth1, bWdth1Unit, bWidth2, bWdth2Unit, bLength1, bLen1Unit, blength2, bLen2Unit, bVolume, bVolUnit, bWeight1, bWght1Unit, bWeight2, bWght2Unit, fixCurrCms, firmCode, lstSalDate, qryGroup1, qryGroup2, qryGroup3, qryGroup4, qryGroup5, qryGroup6, qryGroup7, qryGroup8, qryGroup9, qryGroup10, qryGroup11, qryGroup12, qryGroup13, qryGroup14, qryGroup15, qryGroup16, qryGroup17, qryGroup18, qryGroup19, qryGroup20, qryGroup21, qryGroup22, qryGroup23, qryGroup24, qryGroup25, qryGroup26, qryGroup27, qryGroup28, qryGroup29, qryGroup30, qryGroup31, qryGroup32, qryGroup33, qryGroup34, qryGroup35, qryGroup36, qryGroup37, qryGroup38, qryGroup39, qryGroup40, qryGroup41, qryGroup42, qryGroup43, qryGroup44, qryGroup45, qryGroup46, qryGroup47, qryGroup48, qryGroup49, qryGroup50, qryGroup51, qryGroup52, qryGroup53, qryGroup54, qryGroup55, qryGroup56, qryGroup57, qryGroup58, qryGroup59, qryGroup60, qryGroup61, qryGroup62, qryGroup63, qryGroup64, createDate, updateDate, exportCode, salFactor1, salFactor2, salFactor3, salFactor4, purFactor1, purFactor2, purFactor3, purFactor4, salFormula, purFormula, vatGroupPu, avgPrice, purPackMsr, purPackUn, salPackMsr, salPackUn, scnCounter, manBtchNum, manOutOnly, dataSource, validFor, validFrom, validTo, frozenFor, frozenFrom, frozenTo, blockOut, validComm, frozenComm, logInstanc, objType, sww, deleted, docEntry, expensAcct, frgnInAcct, shipType, glMethod, ecInAcct, frgnExpAcc, ecExpAcc, taxType, byWh, wtLiable, itemType, warrntTmpl, baseUnit, countryOrg, stockValue, phantom, issueMthd, free1, pricingPrc, mngMethod, reorderPnt, invntryUom, planingSys, prcrmntMtd, ordrIntrvl, ordrMulti, minOrdrQty, leadTime, indirctTax, taxCodeAr, taxCodeAp, oSvcCode, iSvcCode, serviceGrp, ncmCode, matType, matGrp, productSrc, serviceCtg, itemClass, excisable, chapterId, notifyAsn, proAssNum, assblValue, dnfEntry, userSign2, spec, taxCtg, series, number, fuelCode, beverTblC, beverGrpC, beverTm, attachment, atcEntry, toleranDay, ugpEntry, pUoMEntry, sUoMEntry, iUoMEntry, issuePriBy, assetClass, assetGroup, inventryNo, technician, employee, location, statAsset, cession, deacAftUl, asstStatus, capDate, acqDate, retDate, glPickMeth, noDiscount, mgrByQty, assetRmk1, assetRmk2, assetAmnt1, assetAmnt2, deprGroup, assetSerNo, cntUnitMsr, numInCnt, inUoMEntry, oneBOneRec, ruleCode, scsCode, spProdType, iWeight1, iWght1Unit, iWeight2, iWght2Unit, compoWh, createTs, updateTs, virtAstItm, souVirAsst, inCostRoll, prdStdCst, enAstSeri, linkRsc, onHldPert, onHldLimt, gstRelevnt, sacEntry, gstTaxCtg, uIGroup, uSubgr, uRdcode, uIGroupName, uSubgrName, uProductBranch, uProductGroup, uForcast, uShortName, uTechName, uDGroup, uDGroupName, uPackage, uMsl, uPartNumber);
//    }
}
