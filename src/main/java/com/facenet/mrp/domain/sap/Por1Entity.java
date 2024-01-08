package com.facenet.mrp.domain.sap;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "POR1")
@IdClass(Por1EntityPK.class)
public class Por1Entity {
    private int docEntry;
    private int lineNum;
    private String uSo;
    private String uMcode;
    private Integer targetType;
    private Integer trgetEntry;
    private String baseRef;
    private Integer baseType;
    private Integer baseEntry;
    private Integer baseLine;
    private String lineStatus;
    private String itemCode;
    private String dscription;
    private Long quantity;
    private Date shipDate;
    private Double openQty;
    private Long price;
    private String currency;
    private Long rate;
    private Long discPrcnt;
    private Long lineTotal;
    private Long totalFrgn;
    private Long openSum;
    private Long openSumFc;
    private String vendorNum;
    private String serialNum;
    private String whsCode;
    private Integer slpCode;
    private Long commission;
    private String treeType;
    private String acctCode;
    private String taxStatus;
    private Long grossBuyPr;
    private Long priceBefDi;
    private Date docDate;
    private Integer flags;
    private Long openCreQty;
    private String useBaseUn;
    private String subCatNum;
    private String baseCard;
    private Long totalSumSy;
    private Long openSumSys;
    private String invntSttus;
    private String ocrCode;
    private String project;
    private String codeBars;
    private Long vatPrcnt;
    private String vatGroup;
    private Long priceAfVat;
    private Long height1;
    private Short hght1Unit;
    private Long height2;
    private Short hght2Unit;
    private Long width1;
    private Short wdth1Unit;
    private Long width2;
    private Short wdth2Unit;
    private Long length1;
    private Short len1Unit;
    private Long length2;
    private Short len2Unit;
    private Long volume;
    private Short volUnit;
    private Long weight1;
    private Short wght1Unit;
    private Long weight2;
    private Short wght2Unit;
    private Long factor1;
    private Long factor2;
    private Long factor3;
    private Long factor4;
    private Long packQty;
    private String updInvntry;
    private Integer baseDocNum;
    private String baseAtCard;
    private String sww;
    private Long vatSum;
    private Long vatSumFrgn;
    private Long vatSumSy;
    private Integer finncPriod;
    private String objType;
    private Integer logInstanc;
    private String blockNum;
    private String importLog;
    private Long dedVatSum;
    private Long dedVatSumF;
    private Long dedVatSumS;
    private String isAqcuistn;
    private Long distribSum;
    private Long dstrbSumFc;
    private Long dstrbSumSc;
    private Long grssProfit;
    private Long grssProfSc;
    private Long grssProfFc;
    private Integer visOrder;
    private Long inmPrice;
    private Integer poTrgNum;
    private String poTrgEntry;
    private String dropShip;
    private Integer poLineNum;
    private String address;
    private String taxCode;
    private String taxType;
    private String origItem;
    private String backOrdr;
    private String freeTxt;
    private String pickStatus;
    private Long pickOty;
    private Integer pickIdNo;
    private Short trnsCode;
    private Long vatAppld;
    private Long vatAppldFc;
    private Long vatAppldSc;
    private Long baseQty;
    private Long baseOpnQty;
    private Long vatDscntPr;
    private String wtLiable;
    private String deferrTax;
    private Long equVatPer;
    private Long equVatSum;
    private Long equVatSumF;
    private Long equVatSumS;
    private Long lineVat;
    private Long lineVatlF;
    private Long lineVatS;
    private String unitMsr;
    private Long numPerMsr;
    private String ceecFlag;
    private Long toStock;
    private Long toDiff;
    private Long exciseAmt;
    private Long taxPerUnit;
    private Long totInclTax;
    private String countryOrg;
    private Long stckDstSum;
    private Long releasQtty;
    private String lineType;
    private String tranType;
    private String text;
    private Integer ownerCode;
    private Long stockPrice;
    private String consumeFct;
    private Long lstByDsSum;
    private Long stckInmPr;
    private Long lstBinmPr;
    private Long stckDstFc;
    private Long stckDstSc;
    private Long lstByDsFc;
    private Long lstByDsSc;
    private Long stockSum;
    private Long stockSumFc;
    private Long stockSumSc;
    private Long stckSumApp;
    private Long stckAppFc;
    private Long stckAppSc;
    private String shipToCode;
    private String shipToDesc;
    private Long stckAppD;
    private Long stckAppDfc;
    private Long stckAppDsc;
    private String basePrice;
    private Long gTotal;
    private Long gTotalFc;
    private Long gTotalSc;
    private String distribExp;
    private String descOw;
    private String detailsOw;
    private Short grossBase;
    private Long vatWoDpm;
    private Long vatWoDpmFc;
    private Long vatWoDpmSc;
    private String cfopCode;
    private String cstCode;
    private Integer usage;
    private String taxOnly;
    private String wtCalced;
    private Long qtyToShip;
    private Long delivrdQty;
    private Long orderedQty;
    private String cogsOcrCod;
    private Integer ciOppLineN;
    private String cogsAcct;
    private String chgAsmBoMw;
    private Date actDelDate;
    private String ocrCode2;
    private String ocrCode3;
    private String ocrCode4;
    private String ocrCode5;
    private Long taxDistSum;
    private Long taxDistSfc;
    private Long taxDistSsc;
    private String postTax;
    private String excisable;
    private Long assblValue;
    private Integer rg23APart1;
    private Integer rg23APart2;
    private Integer rg23CPart1;
    private Integer rg23CPart2;
    private String cogsOcrCo2;
    private String cogsOcrCo3;
    private String cogsOcrCo4;
    private String cogsOcrCo5;
    private String lnExcised;
    private Integer locCode;
    private Long stockValue;
    private Long gpTtlBasPr;
    private String unitMsr2;
    private Long numPerMsr2;
    private String specPrice;
    private String csTfIpi;
    private String csTfPis;
    private String csTfCofins;
    private String exLineNo;
    private String isSrvCall;
    private Long pqtReqQty;
    private Date pqtReqDate;
    private Integer pcDocType;
    private Long pcQuantity;
    private String linManClsd;
    private String vatGrpSrc;
    private String noInvtryMv;
    private Integer actBaseEnt;
    private Integer actBaseLn;
    private Integer actBaseNum;
    private Long openRtnQty;
    private Integer agrNo;
    private Integer agrLnNum;
    private String credOrigin;
    private Long surpluses;
    private Long defBreak;
    private Long shortages;
    private Integer uomEntry;
    private Integer uomEntry2;
    private String uomCode;
    private String uomCode2;
    private String fromWhsCod;
    private String needQty;
    private String partRetire;
    private Long retireQty;
    private Long retireApc;
    private Long retirApcfc;
    private Long retirApcsc;
    private Long invQty;
    private Long openInvQty;
    private String enSetCost;
    private Long retCost;
    private Integer incoterms;
    private Integer transMod;
    private String lineVendor;
    private String distribIs;
    private Long isDistrb;
    private Long isDistrbFc;
    private Long isDistrbSc;
    private String isByPrdct;
    private Integer itemType;
    private String priceEdit;
    private Integer prntLnNum;
    private String linePoPrss;
    private String freeChrgBp;
    private String taxRelev;
    private String legalText;
    private String thirdParty;
    private String licTradNum;
    private String invQtyOnly;
    private Integer unencReasn;
    private String shipFromCo;
    private String shipFromDe;
    private String fisrtBin;
    private String allocBinC;
    private String expType;
    private String expUuid;
    private String expOpType;
    private String diotNat;
    private String myFtype;
    private String itmTaxType;
    private Integer sacEntry;
    private String uRdcode;
    private String uTenkythuat;
    private Integer uQcCode;
    private Integer uQcLine;
    private String uCknb;
    private String uKm;
    private Short uSendToHo;
    private String uDvTthuc;

    @Id
    @Column(name = "DocEntry")
    public int getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(int docEntry) {
        this.docEntry = docEntry;
    }

    @Id
    @Column(name = "LineNum")
    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    @Basic
    @Column(name = "TargetType")
    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    @Basic
    @Column(name = "TrgetEntry")
    public Integer getTrgetEntry() {
        return trgetEntry;
    }

    public void setTrgetEntry(Integer trgetEntry) {
        this.trgetEntry = trgetEntry;
    }

    @Basic
    @Column(name = "BaseRef")
    public String getBaseRef() {
        return baseRef;
    }

    public void setBaseRef(String baseRef) {
        this.baseRef = baseRef;
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
    @Column(name = "BaseLine")
    public Integer getBaseLine() {
        return baseLine;
    }

    public void setBaseLine(Integer baseLine) {
        this.baseLine = baseLine;
    }

    @Basic
    @Column(name = "LineStatus")
    public String getLineStatus() {
        return lineStatus;
    }

    public void setLineStatus(String lineStatus) {
        this.lineStatus = lineStatus;
    }

    @Basic
    @Column(name = "ItemCode")
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Basic
    @Column(name = "Dscription")
    public String getDscription() {
        return dscription;
    }

    public void setDscription(String dscription) {
        this.dscription = dscription;
    }

    @Basic
    @Column(name = "Quantity")
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "ShipDate")
    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    @Basic
    @Column(name = "OpenQty")
    public Double getOpenQty() {
        return openQty;
    }

    public void setOpenQty(Double openQty) {
        this.openQty = openQty;
    }

    @Basic
    @Column(name = "Price")
    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Basic
    @Column(name = "Currency")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Basic
    @Column(name = "Rate")
    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
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
    @Column(name = "LineTotal")
    public Long getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(Long lineTotal) {
        this.lineTotal = lineTotal;
    }

    @Basic
    @Column(name = "TotalFrgn")
    public Long getTotalFrgn() {
        return totalFrgn;
    }

    public void setTotalFrgn(Long totalFrgn) {
        this.totalFrgn = totalFrgn;
    }

    @Basic
    @Column(name = "OpenSum")
    public Long getOpenSum() {
        return openSum;
    }

    public void setOpenSum(Long openSum) {
        this.openSum = openSum;
    }

    @Basic
    @Column(name = "OpenSumFC")
    public Long getOpenSumFc() {
        return openSumFc;
    }

    public void setOpenSumFc(Long openSumFc) {
        this.openSumFc = openSumFc;
    }

    @Basic
    @Column(name = "VendorNum")
    public String getVendorNum() {
        return vendorNum;
    }

    public void setVendorNum(String vendorNum) {
        this.vendorNum = vendorNum;
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
    @Column(name = "WhsCode")
    public String getWhsCode() {
        return whsCode;
    }

    public void setWhsCode(String whsCode) {
        this.whsCode = whsCode;
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
    @Column(name = "Commission")
    public Long getCommission() {
        return commission;
    }

    public void setCommission(Long commission) {
        this.commission = commission;
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
    @Column(name = "AcctCode")
    public String getAcctCode() {
        return acctCode;
    }

    public void setAcctCode(String acctCode) {
        this.acctCode = acctCode;
    }

    @Basic
    @Column(name = "TaxStatus")
    public String getTaxStatus() {
        return taxStatus;
    }

    public void setTaxStatus(String taxStatus) {
        this.taxStatus = taxStatus;
    }

    @Basic
    @Column(name = "GrossBuyPr")
    public Long getGrossBuyPr() {
        return grossBuyPr;
    }

    public void setGrossBuyPr(Long grossBuyPr) {
        this.grossBuyPr = grossBuyPr;
    }

    @Basic
    @Column(name = "PriceBefDi")
    public Long getPriceBefDi() {
        return priceBefDi;
    }

    public void setPriceBefDi(Long priceBefDi) {
        this.priceBefDi = priceBefDi;
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
    @Column(name = "Flags")
    public Integer getFlags() {
        return flags;
    }

    public void setFlags(Integer flags) {
        this.flags = flags;
    }

    @Basic
    @Column(name = "OpenCreQty")
    public Long getOpenCreQty() {
        return openCreQty;
    }

    public void setOpenCreQty(Long openCreQty) {
        this.openCreQty = openCreQty;
    }

    @Basic
    @Column(name = "UseBaseUn")
    public String getUseBaseUn() {
        return useBaseUn;
    }

    public void setUseBaseUn(String useBaseUn) {
        this.useBaseUn = useBaseUn;
    }

    @Basic
    @Column(name = "SubCatNum")
    public String getSubCatNum() {
        return subCatNum;
    }

    public void setSubCatNum(String subCatNum) {
        this.subCatNum = subCatNum;
    }

    @Basic
    @Column(name = "BaseCard")
    public String getBaseCard() {
        return baseCard;
    }

    public void setBaseCard(String baseCard) {
        this.baseCard = baseCard;
    }

    @Basic
    @Column(name = "TotalSumSy")
    public Long getTotalSumSy() {
        return totalSumSy;
    }

    public void setTotalSumSy(Long totalSumSy) {
        this.totalSumSy = totalSumSy;
    }

    @Basic
    @Column(name = "OpenSumSys")
    public Long getOpenSumSys() {
        return openSumSys;
    }

    public void setOpenSumSys(Long openSumSys) {
        this.openSumSys = openSumSys;
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
    @Column(name = "OcrCode")
    public String getOcrCode() {
        return ocrCode;
    }

    public void setOcrCode(String ocrCode) {
        this.ocrCode = ocrCode;
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
    @Column(name = "CodeBars")
    public String getCodeBars() {
        return codeBars;
    }

    public void setCodeBars(String codeBars) {
        this.codeBars = codeBars;
    }

    @Basic
    @Column(name = "VatPrcnt")
    public Long getVatPrcnt() {
        return vatPrcnt;
    }

    public void setVatPrcnt(Long vatPrcnt) {
        this.vatPrcnt = vatPrcnt;
    }

    @Basic
    @Column(name = "VatGroup")
    public String getVatGroup() {
        return vatGroup;
    }

    public void setVatGroup(String vatGroup) {
        this.vatGroup = vatGroup;
    }

    @Basic
    @Column(name = "PriceAfVAT")
    public Long getPriceAfVat() {
        return priceAfVat;
    }

    public void setPriceAfVat(Long priceAfVat) {
        this.priceAfVat = priceAfVat;
    }

    @Basic
    @Column(name = "Height1")
    public Long getHeight1() {
        return height1;
    }

    public void setHeight1(Long height1) {
        this.height1 = height1;
    }

    @Basic
    @Column(name = "Hght1Unit")
    public Short getHght1Unit() {
        return hght1Unit;
    }

    public void setHght1Unit(Short hght1Unit) {
        this.hght1Unit = hght1Unit;
    }

    @Basic
    @Column(name = "Height2")
    public Long getHeight2() {
        return height2;
    }

    public void setHeight2(Long height2) {
        this.height2 = height2;
    }

    @Basic
    @Column(name = "Hght2Unit")
    public Short getHght2Unit() {
        return hght2Unit;
    }

    public void setHght2Unit(Short hght2Unit) {
        this.hght2Unit = hght2Unit;
    }

    @Basic
    @Column(name = "Width1")
    public Long getWidth1() {
        return width1;
    }

    public void setWidth1(Long width1) {
        this.width1 = width1;
    }

    @Basic
    @Column(name = "Wdth1Unit")
    public Short getWdth1Unit() {
        return wdth1Unit;
    }

    public void setWdth1Unit(Short wdth1Unit) {
        this.wdth1Unit = wdth1Unit;
    }

    @Basic
    @Column(name = "Width2")
    public Long getWidth2() {
        return width2;
    }

    public void setWidth2(Long width2) {
        this.width2 = width2;
    }

    @Basic
    @Column(name = "Wdth2Unit")
    public Short getWdth2Unit() {
        return wdth2Unit;
    }

    public void setWdth2Unit(Short wdth2Unit) {
        this.wdth2Unit = wdth2Unit;
    }

    @Basic
    @Column(name = "Length1")
    public Long getLength1() {
        return length1;
    }

    public void setLength1(Long length1) {
        this.length1 = length1;
    }

    @Basic
    @Column(name = "Len1Unit")
    public Short getLen1Unit() {
        return len1Unit;
    }

    public void setLen1Unit(Short len1Unit) {
        this.len1Unit = len1Unit;
    }

    @Basic
    @Column(name = "length2")
    public Long getLength2() {
        return length2;
    }

    public void setLength2(Long length2) {
        this.length2 = length2;
    }

    @Basic
    @Column(name = "Len2Unit")
    public Short getLen2Unit() {
        return len2Unit;
    }

    public void setLen2Unit(Short len2Unit) {
        this.len2Unit = len2Unit;
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
    @Column(name = "Weight1")
    public Long getWeight1() {
        return weight1;
    }

    public void setWeight1(Long weight1) {
        this.weight1 = weight1;
    }

    @Basic
    @Column(name = "Wght1Unit")
    public Short getWght1Unit() {
        return wght1Unit;
    }

    public void setWght1Unit(Short wght1Unit) {
        this.wght1Unit = wght1Unit;
    }

    @Basic
    @Column(name = "Weight2")
    public Long getWeight2() {
        return weight2;
    }

    public void setWeight2(Long weight2) {
        this.weight2 = weight2;
    }

    @Basic
    @Column(name = "Wght2Unit")
    public Short getWght2Unit() {
        return wght2Unit;
    }

    public void setWght2Unit(Short wght2Unit) {
        this.wght2Unit = wght2Unit;
    }

    @Basic
    @Column(name = "Factor1")
    public Long getFactor1() {
        return factor1;
    }

    public void setFactor1(Long factor1) {
        this.factor1 = factor1;
    }

    @Basic
    @Column(name = "Factor2")
    public Long getFactor2() {
        return factor2;
    }

    public void setFactor2(Long factor2) {
        this.factor2 = factor2;
    }

    @Basic
    @Column(name = "Factor3")
    public Long getFactor3() {
        return factor3;
    }

    public void setFactor3(Long factor3) {
        this.factor3 = factor3;
    }

    @Basic
    @Column(name = "Factor4")
    public Long getFactor4() {
        return factor4;
    }

    public void setFactor4(Long factor4) {
        this.factor4 = factor4;
    }

    @Basic
    @Column(name = "PackQty")
    public Long getPackQty() {
        return packQty;
    }

    public void setPackQty(Long packQty) {
        this.packQty = packQty;
    }

    @Basic
    @Column(name = "UpdInvntry")
    public String getUpdInvntry() {
        return updInvntry;
    }

    public void setUpdInvntry(String updInvntry) {
        this.updInvntry = updInvntry;
    }

    @Basic
    @Column(name = "BaseDocNum")
    public Integer getBaseDocNum() {
        return baseDocNum;
    }

    public void setBaseDocNum(Integer baseDocNum) {
        this.baseDocNum = baseDocNum;
    }

    @Basic
    @Column(name = "BaseAtCard")
    public String getBaseAtCard() {
        return baseAtCard;
    }

    public void setBaseAtCard(String baseAtCard) {
        this.baseAtCard = baseAtCard;
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
    @Column(name = "VatSum")
    public Long getVatSum() {
        return vatSum;
    }

    public void setVatSum(Long vatSum) {
        this.vatSum = vatSum;
    }

    @Basic
    @Column(name = "VatSumFrgn")
    public Long getVatSumFrgn() {
        return vatSumFrgn;
    }

    public void setVatSumFrgn(Long vatSumFrgn) {
        this.vatSumFrgn = vatSumFrgn;
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
    @Column(name = "FinncPriod")
    public Integer getFinncPriod() {
        return finncPriod;
    }

    public void setFinncPriod(Integer finncPriod) {
        this.finncPriod = finncPriod;
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
    @Column(name = "LogInstanc")
    public Integer getLogInstanc() {
        return logInstanc;
    }

    public void setLogInstanc(Integer logInstanc) {
        this.logInstanc = logInstanc;
    }

    @Basic
    @Column(name = "BlockNum")
    public String getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(String blockNum) {
        this.blockNum = blockNum;
    }

    @Basic
    @Column(name = "ImportLog")
    public String getImportLog() {
        return importLog;
    }

    public void setImportLog(String importLog) {
        this.importLog = importLog;
    }

    @Basic
    @Column(name = "DedVatSum")
    public Long getDedVatSum() {
        return dedVatSum;
    }

    public void setDedVatSum(Long dedVatSum) {
        this.dedVatSum = dedVatSum;
    }

    @Basic
    @Column(name = "DedVatSumF")
    public Long getDedVatSumF() {
        return dedVatSumF;
    }

    public void setDedVatSumF(Long dedVatSumF) {
        this.dedVatSumF = dedVatSumF;
    }

    @Basic
    @Column(name = "DedVatSumS")
    public Long getDedVatSumS() {
        return dedVatSumS;
    }

    public void setDedVatSumS(Long dedVatSumS) {
        this.dedVatSumS = dedVatSumS;
    }

    @Basic
    @Column(name = "IsAqcuistn")
    public String getIsAqcuistn() {
        return isAqcuistn;
    }

    public void setIsAqcuistn(String isAqcuistn) {
        this.isAqcuistn = isAqcuistn;
    }

    @Basic
    @Column(name = "DistribSum")
    public Long getDistribSum() {
        return distribSum;
    }

    public void setDistribSum(Long distribSum) {
        this.distribSum = distribSum;
    }

    @Basic
    @Column(name = "DstrbSumFC")
    public Long getDstrbSumFc() {
        return dstrbSumFc;
    }

    public void setDstrbSumFc(Long dstrbSumFc) {
        this.dstrbSumFc = dstrbSumFc;
    }

    @Basic
    @Column(name = "DstrbSumSC")
    public Long getDstrbSumSc() {
        return dstrbSumSc;
    }

    public void setDstrbSumSc(Long dstrbSumSc) {
        this.dstrbSumSc = dstrbSumSc;
    }

    @Basic
    @Column(name = "GrssProfit")
    public Long getGrssProfit() {
        return grssProfit;
    }

    public void setGrssProfit(Long grssProfit) {
        this.grssProfit = grssProfit;
    }

    @Basic
    @Column(name = "GrssProfSC")
    public Long getGrssProfSc() {
        return grssProfSc;
    }

    public void setGrssProfSc(Long grssProfSc) {
        this.grssProfSc = grssProfSc;
    }

    @Basic
    @Column(name = "GrssProfFC")
    public Long getGrssProfFc() {
        return grssProfFc;
    }

    public void setGrssProfFc(Long grssProfFc) {
        this.grssProfFc = grssProfFc;
    }

    @Basic
    @Column(name = "VisOrder")
    public Integer getVisOrder() {
        return visOrder;
    }

    public void setVisOrder(Integer visOrder) {
        this.visOrder = visOrder;
    }

    @Basic
    @Column(name = "INMPrice")
    public Long getInmPrice() {
        return inmPrice;
    }

    public void setInmPrice(Long inmPrice) {
        this.inmPrice = inmPrice;
    }

    @Basic
    @Column(name = "PoTrgNum")
    public Integer getPoTrgNum() {
        return poTrgNum;
    }

    public void setPoTrgNum(Integer poTrgNum) {
        this.poTrgNum = poTrgNum;
    }

    @Basic
    @Column(name = "PoTrgEntry")
    public String getPoTrgEntry() {
        return poTrgEntry;
    }

    public void setPoTrgEntry(String poTrgEntry) {
        this.poTrgEntry = poTrgEntry;
    }

    @Basic
    @Column(name = "DropShip")
    public String getDropShip() {
        return dropShip;
    }

    public void setDropShip(String dropShip) {
        this.dropShip = dropShip;
    }

    @Basic
    @Column(name = "PoLineNum")
    public Integer getPoLineNum() {
        return poLineNum;
    }

    public void setPoLineNum(Integer poLineNum) {
        this.poLineNum = poLineNum;
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
    @Column(name = "TaxCode")
    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
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
    @Column(name = "OrigItem")
    public String getOrigItem() {
        return origItem;
    }

    public void setOrigItem(String origItem) {
        this.origItem = origItem;
    }

    @Basic
    @Column(name = "BackOrdr")
    public String getBackOrdr() {
        return backOrdr;
    }

    public void setBackOrdr(String backOrdr) {
        this.backOrdr = backOrdr;
    }

    @Basic
    @Column(name = "FreeTxt")
    public String getFreeTxt() {
        return freeTxt;
    }

    public void setFreeTxt(String freeTxt) {
        this.freeTxt = freeTxt;
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
    @Column(name = "PickOty")
    public Long getPickOty() {
        return pickOty;
    }

    public void setPickOty(Long pickOty) {
        this.pickOty = pickOty;
    }

    @Basic
    @Column(name = "PickIdNo")
    public Integer getPickIdNo() {
        return pickIdNo;
    }

    public void setPickIdNo(Integer pickIdNo) {
        this.pickIdNo = pickIdNo;
    }

    @Basic
    @Column(name = "TrnsCode")
    public Short getTrnsCode() {
        return trnsCode;
    }

    public void setTrnsCode(Short trnsCode) {
        this.trnsCode = trnsCode;
    }

    @Basic
    @Column(name = "VatAppld")
    public Long getVatAppld() {
        return vatAppld;
    }

    public void setVatAppld(Long vatAppld) {
        this.vatAppld = vatAppld;
    }

    @Basic
    @Column(name = "VatAppldFC")
    public Long getVatAppldFc() {
        return vatAppldFc;
    }

    public void setVatAppldFc(Long vatAppldFc) {
        this.vatAppldFc = vatAppldFc;
    }

    @Basic
    @Column(name = "VatAppldSC")
    public Long getVatAppldSc() {
        return vatAppldSc;
    }

    public void setVatAppldSc(Long vatAppldSc) {
        this.vatAppldSc = vatAppldSc;
    }

    @Basic
    @Column(name = "BaseQty")
    public Long getBaseQty() {
        return baseQty;
    }

    public void setBaseQty(Long baseQty) {
        this.baseQty = baseQty;
    }

    @Basic
    @Column(name = "BaseOpnQty")
    public Long getBaseOpnQty() {
        return baseOpnQty;
    }

    public void setBaseOpnQty(Long baseOpnQty) {
        this.baseOpnQty = baseOpnQty;
    }

    @Basic
    @Column(name = "VatDscntPr")
    public Long getVatDscntPr() {
        return vatDscntPr;
    }

    public void setVatDscntPr(Long vatDscntPr) {
        this.vatDscntPr = vatDscntPr;
    }

    @Basic
    @Column(name = "WtLiable")
    public String getWtLiable() {
        return wtLiable;
    }

    public void setWtLiable(String wtLiable) {
        this.wtLiable = wtLiable;
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
    @Column(name = "EquVatPer")
    public Long getEquVatPer() {
        return equVatPer;
    }

    public void setEquVatPer(Long equVatPer) {
        this.equVatPer = equVatPer;
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
    @Column(name = "LineVat")
    public Long getLineVat() {
        return lineVat;
    }

    public void setLineVat(Long lineVat) {
        this.lineVat = lineVat;
    }

    @Basic
    @Column(name = "LineVatlF")
    public Long getLineVatlF() {
        return lineVatlF;
    }

    public void setLineVatlF(Long lineVatlF) {
        this.lineVatlF = lineVatlF;
    }

    @Basic
    @Column(name = "LineVatS")
    public Long getLineVatS() {
        return lineVatS;
    }

    public void setLineVatS(Long lineVatS) {
        this.lineVatS = lineVatS;
    }

    @Basic
    @Column(name = "unitMsr")
    public String getUnitMsr() {
        return unitMsr;
    }

    public void setUnitMsr(String unitMsr) {
        this.unitMsr = unitMsr;
    }

    @Basic
    @Column(name = "NumPerMsr")
    public Long getNumPerMsr() {
        return numPerMsr;
    }

    public void setNumPerMsr(Long numPerMsr) {
        this.numPerMsr = numPerMsr;
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
    @Column(name = "ToStock")
    public Long getToStock() {
        return toStock;
    }

    public void setToStock(Long toStock) {
        this.toStock = toStock;
    }

    @Basic
    @Column(name = "ToDiff")
    public Long getToDiff() {
        return toDiff;
    }

    public void setToDiff(Long toDiff) {
        this.toDiff = toDiff;
    }

    @Basic
    @Column(name = "ExciseAmt")
    public Long getExciseAmt() {
        return exciseAmt;
    }

    public void setExciseAmt(Long exciseAmt) {
        this.exciseAmt = exciseAmt;
    }

    @Basic
    @Column(name = "TaxPerUnit")
    public Long getTaxPerUnit() {
        return taxPerUnit;
    }

    public void setTaxPerUnit(Long taxPerUnit) {
        this.taxPerUnit = taxPerUnit;
    }

    @Basic
    @Column(name = "TotInclTax")
    public Long getTotInclTax() {
        return totInclTax;
    }

    public void setTotInclTax(Long totInclTax) {
        this.totInclTax = totInclTax;
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
    @Column(name = "StckDstSum")
    public Long getStckDstSum() {
        return stckDstSum;
    }

    public void setStckDstSum(Long stckDstSum) {
        this.stckDstSum = stckDstSum;
    }

    @Basic
    @Column(name = "ReleasQtty")
    public Long getReleasQtty() {
        return releasQtty;
    }

    public void setReleasQtty(Long releasQtty) {
        this.releasQtty = releasQtty;
    }

    @Basic
    @Column(name = "LineType")
    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    @Basic
    @Column(name = "TranType")
    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    @Basic
    @Column(name = "Text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
    @Column(name = "StockPrice")
    public Long getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(Long stockPrice) {
        this.stockPrice = stockPrice;
    }

    @Basic
    @Column(name = "ConsumeFCT")
    public String getConsumeFct() {
        return consumeFct;
    }

    public void setConsumeFct(String consumeFct) {
        this.consumeFct = consumeFct;
    }

    @Basic
    @Column(name = "LstByDsSum")
    public Long getLstByDsSum() {
        return lstByDsSum;
    }

    public void setLstByDsSum(Long lstByDsSum) {
        this.lstByDsSum = lstByDsSum;
    }

    @Basic
    @Column(name = "StckINMPr")
    public Long getStckInmPr() {
        return stckInmPr;
    }

    public void setStckInmPr(Long stckInmPr) {
        this.stckInmPr = stckInmPr;
    }

    @Basic
    @Column(name = "LstBINMPr")
    public Long getLstBinmPr() {
        return lstBinmPr;
    }

    public void setLstBinmPr(Long lstBinmPr) {
        this.lstBinmPr = lstBinmPr;
    }

    @Basic
    @Column(name = "StckDstFc")
    public Long getStckDstFc() {
        return stckDstFc;
    }

    public void setStckDstFc(Long stckDstFc) {
        this.stckDstFc = stckDstFc;
    }

    @Basic
    @Column(name = "StckDstSc")
    public Long getStckDstSc() {
        return stckDstSc;
    }

    public void setStckDstSc(Long stckDstSc) {
        this.stckDstSc = stckDstSc;
    }

    @Basic
    @Column(name = "LstByDsFc")
    public Long getLstByDsFc() {
        return lstByDsFc;
    }

    public void setLstByDsFc(Long lstByDsFc) {
        this.lstByDsFc = lstByDsFc;
    }

    @Basic
    @Column(name = "LstByDsSc")
    public Long getLstByDsSc() {
        return lstByDsSc;
    }

    public void setLstByDsSc(Long lstByDsSc) {
        this.lstByDsSc = lstByDsSc;
    }

    @Basic
    @Column(name = "StockSum")
    public Long getStockSum() {
        return stockSum;
    }

    public void setStockSum(Long stockSum) {
        this.stockSum = stockSum;
    }

    @Basic
    @Column(name = "StockSumFc")
    public Long getStockSumFc() {
        return stockSumFc;
    }

    public void setStockSumFc(Long stockSumFc) {
        this.stockSumFc = stockSumFc;
    }

    @Basic
    @Column(name = "StockSumSc")
    public Long getStockSumSc() {
        return stockSumSc;
    }

    public void setStockSumSc(Long stockSumSc) {
        this.stockSumSc = stockSumSc;
    }

    @Basic
    @Column(name = "StckSumApp")
    public Long getStckSumApp() {
        return stckSumApp;
    }

    public void setStckSumApp(Long stckSumApp) {
        this.stckSumApp = stckSumApp;
    }

    @Basic
    @Column(name = "StckAppFc")
    public Long getStckAppFc() {
        return stckAppFc;
    }

    public void setStckAppFc(Long stckAppFc) {
        this.stckAppFc = stckAppFc;
    }

    @Basic
    @Column(name = "StckAppSc")
    public Long getStckAppSc() {
        return stckAppSc;
    }

    public void setStckAppSc(Long stckAppSc) {
        this.stckAppSc = stckAppSc;
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
    @Column(name = "ShipToDesc")
    public String getShipToDesc() {
        return shipToDesc;
    }

    public void setShipToDesc(String shipToDesc) {
        this.shipToDesc = shipToDesc;
    }

    @Basic
    @Column(name = "StckAppD")
    public Long getStckAppD() {
        return stckAppD;
    }

    public void setStckAppD(Long stckAppD) {
        this.stckAppD = stckAppD;
    }

    @Basic
    @Column(name = "StckAppDFC")
    public Long getStckAppDfc() {
        return stckAppDfc;
    }

    public void setStckAppDfc(Long stckAppDfc) {
        this.stckAppDfc = stckAppDfc;
    }

    @Basic
    @Column(name = "StckAppDSC")
    public Long getStckAppDsc() {
        return stckAppDsc;
    }

    public void setStckAppDsc(Long stckAppDsc) {
        this.stckAppDsc = stckAppDsc;
    }

    @Basic
    @Column(name = "BasePrice")
    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }

    @Basic
    @Column(name = "GTotal")
    public Long getgTotal() {
        return gTotal;
    }

    public void setgTotal(Long gTotal) {
        this.gTotal = gTotal;
    }

    @Basic
    @Column(name = "GTotalFC")
    public Long getgTotalFc() {
        return gTotalFc;
    }

    public void setgTotalFc(Long gTotalFc) {
        this.gTotalFc = gTotalFc;
    }

    @Basic
    @Column(name = "GTotalSC")
    public Long getgTotalSc() {
        return gTotalSc;
    }

    public void setgTotalSc(Long gTotalSc) {
        this.gTotalSc = gTotalSc;
    }

    @Basic
    @Column(name = "DistribExp")
    public String getDistribExp() {
        return distribExp;
    }

    public void setDistribExp(String distribExp) {
        this.distribExp = distribExp;
    }

    @Basic
    @Column(name = "DescOW")
    public String getDescOw() {
        return descOw;
    }

    public void setDescOw(String descOw) {
        this.descOw = descOw;
    }

    @Basic
    @Column(name = "DetailsOW")
    public String getDetailsOw() {
        return detailsOw;
    }

    public void setDetailsOw(String detailsOw) {
        this.detailsOw = detailsOw;
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
    @Column(name = "VatWoDpm")
    public Long getVatWoDpm() {
        return vatWoDpm;
    }

    public void setVatWoDpm(Long vatWoDpm) {
        this.vatWoDpm = vatWoDpm;
    }

    @Basic
    @Column(name = "VatWoDpmFc")
    public Long getVatWoDpmFc() {
        return vatWoDpmFc;
    }

    public void setVatWoDpmFc(Long vatWoDpmFc) {
        this.vatWoDpmFc = vatWoDpmFc;
    }

    @Basic
    @Column(name = "VatWoDpmSc")
    public Long getVatWoDpmSc() {
        return vatWoDpmSc;
    }

    public void setVatWoDpmSc(Long vatWoDpmSc) {
        this.vatWoDpmSc = vatWoDpmSc;
    }

    @Basic
    @Column(name = "CFOPCode")
    public String getCfopCode() {
        return cfopCode;
    }

    public void setCfopCode(String cfopCode) {
        this.cfopCode = cfopCode;
    }

    @Basic
    @Column(name = "CSTCode")
    public String getCstCode() {
        return cstCode;
    }

    public void setCstCode(String cstCode) {
        this.cstCode = cstCode;
    }

    @Basic
    @Column(name = "Usage")
    public Integer getUsage() {
        return usage;
    }

    public void setUsage(Integer usage) {
        this.usage = usage;
    }

    @Basic
    @Column(name = "TaxOnly")
    public String getTaxOnly() {
        return taxOnly;
    }

    public void setTaxOnly(String taxOnly) {
        this.taxOnly = taxOnly;
    }

    @Basic
    @Column(name = "WtCalced")
    public String getWtCalced() {
        return wtCalced;
    }

    public void setWtCalced(String wtCalced) {
        this.wtCalced = wtCalced;
    }

    @Basic
    @Column(name = "QtyToShip")
    public Long getQtyToShip() {
        return qtyToShip;
    }

    public void setQtyToShip(Long qtyToShip) {
        this.qtyToShip = qtyToShip;
    }

    @Basic
    @Column(name = "DelivrdQty")
    public Long getDelivrdQty() {
        return delivrdQty;
    }

    public void setDelivrdQty(Long delivrdQty) {
        this.delivrdQty = delivrdQty;
    }

    @Basic
    @Column(name = "OrderedQty")
    public Long getOrderedQty() {
        return orderedQty;
    }

    public void setOrderedQty(Long orderedQty) {
        this.orderedQty = orderedQty;
    }

    @Basic
    @Column(name = "CogsOcrCod")
    public String getCogsOcrCod() {
        return cogsOcrCod;
    }

    public void setCogsOcrCod(String cogsOcrCod) {
        this.cogsOcrCod = cogsOcrCod;
    }

    @Basic
    @Column(name = "CiOppLineN")
    public Integer getCiOppLineN() {
        return ciOppLineN;
    }

    public void setCiOppLineN(Integer ciOppLineN) {
        this.ciOppLineN = ciOppLineN;
    }

    @Basic
    @Column(name = "CogsAcct")
    public String getCogsAcct() {
        return cogsAcct;
    }

    public void setCogsAcct(String cogsAcct) {
        this.cogsAcct = cogsAcct;
    }

    @Basic
    @Column(name = "ChgAsmBoMW")
    public String getChgAsmBoMw() {
        return chgAsmBoMw;
    }

    public void setChgAsmBoMw(String chgAsmBoMw) {
        this.chgAsmBoMw = chgAsmBoMw;
    }

    @Basic
    @Column(name = "ActDelDate")
    public Date getActDelDate() {
        return actDelDate;
    }

    public void setActDelDate(Date actDelDate) {
        this.actDelDate = actDelDate;
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
    @Column(name = "TaxDistSum")
    public Long getTaxDistSum() {
        return taxDistSum;
    }

    public void setTaxDistSum(Long taxDistSum) {
        this.taxDistSum = taxDistSum;
    }

    @Basic
    @Column(name = "TaxDistSFC")
    public Long getTaxDistSfc() {
        return taxDistSfc;
    }

    public void setTaxDistSfc(Long taxDistSfc) {
        this.taxDistSfc = taxDistSfc;
    }

    @Basic
    @Column(name = "TaxDistSSC")
    public Long getTaxDistSsc() {
        return taxDistSsc;
    }

    public void setTaxDistSsc(Long taxDistSsc) {
        this.taxDistSsc = taxDistSsc;
    }

    @Basic
    @Column(name = "PostTax")
    public String getPostTax() {
        return postTax;
    }

    public void setPostTax(String postTax) {
        this.postTax = postTax;
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
    @Column(name = "AssblValue")
    public Long getAssblValue() {
        return assblValue;
    }

    public void setAssblValue(Long assblValue) {
        this.assblValue = assblValue;
    }

    @Basic
    @Column(name = "RG23APart1")
    public Integer getRg23APart1() {
        return rg23APart1;
    }

    public void setRg23APart1(Integer rg23APart1) {
        this.rg23APart1 = rg23APart1;
    }

    @Basic
    @Column(name = "RG23APart2")
    public Integer getRg23APart2() {
        return rg23APart2;
    }

    public void setRg23APart2(Integer rg23APart2) {
        this.rg23APart2 = rg23APart2;
    }

    @Basic
    @Column(name = "RG23CPart1")
    public Integer getRg23CPart1() {
        return rg23CPart1;
    }

    public void setRg23CPart1(Integer rg23CPart1) {
        this.rg23CPart1 = rg23CPart1;
    }

    @Basic
    @Column(name = "RG23CPart2")
    public Integer getRg23CPart2() {
        return rg23CPart2;
    }

    public void setRg23CPart2(Integer rg23CPart2) {
        this.rg23CPart2 = rg23CPart2;
    }

    @Basic
    @Column(name = "CogsOcrCo2")
    public String getCogsOcrCo2() {
        return cogsOcrCo2;
    }

    public void setCogsOcrCo2(String cogsOcrCo2) {
        this.cogsOcrCo2 = cogsOcrCo2;
    }

    @Basic
    @Column(name = "CogsOcrCo3")
    public String getCogsOcrCo3() {
        return cogsOcrCo3;
    }

    public void setCogsOcrCo3(String cogsOcrCo3) {
        this.cogsOcrCo3 = cogsOcrCo3;
    }

    @Basic
    @Column(name = "CogsOcrCo4")
    public String getCogsOcrCo4() {
        return cogsOcrCo4;
    }

    public void setCogsOcrCo4(String cogsOcrCo4) {
        this.cogsOcrCo4 = cogsOcrCo4;
    }

    @Basic
    @Column(name = "CogsOcrCo5")
    public String getCogsOcrCo5() {
        return cogsOcrCo5;
    }

    public void setCogsOcrCo5(String cogsOcrCo5) {
        this.cogsOcrCo5 = cogsOcrCo5;
    }

    @Basic
    @Column(name = "LnExcised")
    public String getLnExcised() {
        return lnExcised;
    }

    public void setLnExcised(String lnExcised) {
        this.lnExcised = lnExcised;
    }

    @Basic
    @Column(name = "LocCode")
    public Integer getLocCode() {
        return locCode;
    }

    public void setLocCode(Integer locCode) {
        this.locCode = locCode;
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
    @Column(name = "GPTtlBasPr")
    public Long getGpTtlBasPr() {
        return gpTtlBasPr;
    }

    public void setGpTtlBasPr(Long gpTtlBasPr) {
        this.gpTtlBasPr = gpTtlBasPr;
    }

    @Basic
    @Column(name = "unitMsr2")
    public String getUnitMsr2() {
        return unitMsr2;
    }

    public void setUnitMsr2(String unitMsr2) {
        this.unitMsr2 = unitMsr2;
    }

    @Basic
    @Column(name = "NumPerMsr2")
    public Long getNumPerMsr2() {
        return numPerMsr2;
    }

    public void setNumPerMsr2(Long numPerMsr2) {
        this.numPerMsr2 = numPerMsr2;
    }

    @Basic
    @Column(name = "SpecPrice")
    public String getSpecPrice() {
        return specPrice;
    }

    public void setSpecPrice(String specPrice) {
        this.specPrice = specPrice;
    }

    @Basic
    @Column(name = "CSTfIPI")
    public String getCsTfIpi() {
        return csTfIpi;
    }

    public void setCsTfIpi(String csTfIpi) {
        this.csTfIpi = csTfIpi;
    }

    @Basic
    @Column(name = "CSTfPIS")
    public String getCsTfPis() {
        return csTfPis;
    }

    public void setCsTfPis(String csTfPis) {
        this.csTfPis = csTfPis;
    }

    @Basic
    @Column(name = "CSTfCOFINS")
    public String getCsTfCofins() {
        return csTfCofins;
    }

    public void setCsTfCofins(String csTfCofins) {
        this.csTfCofins = csTfCofins;
    }

    @Basic
    @Column(name = "ExLineNo")
    public String getExLineNo() {
        return exLineNo;
    }

    public void setExLineNo(String exLineNo) {
        this.exLineNo = exLineNo;
    }

    @Basic
    @Column(name = "isSrvCall")
    public String getIsSrvCall() {
        return isSrvCall;
    }

    public void setIsSrvCall(String isSrvCall) {
        this.isSrvCall = isSrvCall;
    }

    @Basic
    @Column(name = "PQTReqQty")
    public Long getPqtReqQty() {
        return pqtReqQty;
    }

    public void setPqtReqQty(Long pqtReqQty) {
        this.pqtReqQty = pqtReqQty;
    }

    @Basic
    @Column(name = "PQTReqDate")
    public Date getPqtReqDate() {
        return pqtReqDate;
    }

    public void setPqtReqDate(Date pqtReqDate) {
        this.pqtReqDate = pqtReqDate;
    }

    @Basic
    @Column(name = "PcDocType")
    public Integer getPcDocType() {
        return pcDocType;
    }

    public void setPcDocType(Integer pcDocType) {
        this.pcDocType = pcDocType;
    }

    @Basic
    @Column(name = "PcQuantity")
    public Long getPcQuantity() {
        return pcQuantity;
    }

    public void setPcQuantity(Long pcQuantity) {
        this.pcQuantity = pcQuantity;
    }

    @Basic
    @Column(name = "LinManClsd")
    public String getLinManClsd() {
        return linManClsd;
    }

    public void setLinManClsd(String linManClsd) {
        this.linManClsd = linManClsd;
    }

    @Basic
    @Column(name = "VatGrpSrc")
    public String getVatGrpSrc() {
        return vatGrpSrc;
    }

    public void setVatGrpSrc(String vatGrpSrc) {
        this.vatGrpSrc = vatGrpSrc;
    }

    @Basic
    @Column(name = "NoInvtryMv")
    public String getNoInvtryMv() {
        return noInvtryMv;
    }

    public void setNoInvtryMv(String noInvtryMv) {
        this.noInvtryMv = noInvtryMv;
    }

    @Basic
    @Column(name = "ActBaseEnt")
    public Integer getActBaseEnt() {
        return actBaseEnt;
    }

    public void setActBaseEnt(Integer actBaseEnt) {
        this.actBaseEnt = actBaseEnt;
    }

    @Basic
    @Column(name = "ActBaseLn")
    public Integer getActBaseLn() {
        return actBaseLn;
    }

    public void setActBaseLn(Integer actBaseLn) {
        this.actBaseLn = actBaseLn;
    }

    @Basic
    @Column(name = "ActBaseNum")
    public Integer getActBaseNum() {
        return actBaseNum;
    }

    public void setActBaseNum(Integer actBaseNum) {
        this.actBaseNum = actBaseNum;
    }

    @Basic
    @Column(name = "OpenRtnQty")
    public Long getOpenRtnQty() {
        return openRtnQty;
    }

    public void setOpenRtnQty(Long openRtnQty) {
        this.openRtnQty = openRtnQty;
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
    @Column(name = "AgrLnNum")
    public Integer getAgrLnNum() {
        return agrLnNum;
    }

    public void setAgrLnNum(Integer agrLnNum) {
        this.agrLnNum = agrLnNum;
    }

    @Basic
    @Column(name = "CredOrigin")
    public String getCredOrigin() {
        return credOrigin;
    }

    public void setCredOrigin(String credOrigin) {
        this.credOrigin = credOrigin;
    }

    @Basic
    @Column(name = "Surpluses")
    public Long getSurpluses() {
        return surpluses;
    }

    public void setSurpluses(Long surpluses) {
        this.surpluses = surpluses;
    }

    @Basic
    @Column(name = "DefBreak")
    public Long getDefBreak() {
        return defBreak;
    }

    public void setDefBreak(Long defBreak) {
        this.defBreak = defBreak;
    }

    @Basic
    @Column(name = "Shortages")
    public Long getShortages() {
        return shortages;
    }

    public void setShortages(Long shortages) {
        this.shortages = shortages;
    }

    @Basic
    @Column(name = "UomEntry")
    public Integer getUomEntry() {
        return uomEntry;
    }

    public void setUomEntry(Integer uomEntry) {
        this.uomEntry = uomEntry;
    }

    @Basic
    @Column(name = "UomEntry2")
    public Integer getUomEntry2() {
        return uomEntry2;
    }

    public void setUomEntry2(Integer uomEntry2) {
        this.uomEntry2 = uomEntry2;
    }

    @Basic
    @Column(name = "UomCode")
    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    @Basic
    @Column(name = "UomCode2")
    public String getUomCode2() {
        return uomCode2;
    }

    public void setUomCode2(String uomCode2) {
        this.uomCode2 = uomCode2;
    }

    @Basic
    @Column(name = "FromWhsCod")
    public String getFromWhsCod() {
        return fromWhsCod;
    }

    public void setFromWhsCod(String fromWhsCod) {
        this.fromWhsCod = fromWhsCod;
    }

    @Basic
    @Column(name = "NeedQty")
    public String getNeedQty() {
        return needQty;
    }

    public void setNeedQty(String needQty) {
        this.needQty = needQty;
    }

    @Basic
    @Column(name = "PartRetire")
    public String getPartRetire() {
        return partRetire;
    }

    public void setPartRetire(String partRetire) {
        this.partRetire = partRetire;
    }

    @Basic
    @Column(name = "RetireQty")
    public Long getRetireQty() {
        return retireQty;
    }

    public void setRetireQty(Long retireQty) {
        this.retireQty = retireQty;
    }

    @Basic
    @Column(name = "RetireAPC")
    public Long getRetireApc() {
        return retireApc;
    }

    public void setRetireApc(Long retireApc) {
        this.retireApc = retireApc;
    }

    @Basic
    @Column(name = "RetirAPCFC")
    public Long getRetirApcfc() {
        return retirApcfc;
    }

    public void setRetirApcfc(Long retirApcfc) {
        this.retirApcfc = retirApcfc;
    }

    @Basic
    @Column(name = "RetirAPCSC")
    public Long getRetirApcsc() {
        return retirApcsc;
    }

    public void setRetirApcsc(Long retirApcsc) {
        this.retirApcsc = retirApcsc;
    }

    @Basic
    @Column(name = "InvQty")
    public Long getInvQty() {
        return invQty;
    }

    public void setInvQty(Long invQty) {
        this.invQty = invQty;
    }

    @Basic
    @Column(name = "OpenInvQty")
    public Long getOpenInvQty() {
        return openInvQty;
    }

    public void setOpenInvQty(Long openInvQty) {
        this.openInvQty = openInvQty;
    }

    @Basic
    @Column(name = "EnSetCost")
    public String getEnSetCost() {
        return enSetCost;
    }

    public void setEnSetCost(String enSetCost) {
        this.enSetCost = enSetCost;
    }

    @Basic
    @Column(name = "RetCost")
    public Long getRetCost() {
        return retCost;
    }

    public void setRetCost(Long retCost) {
        this.retCost = retCost;
    }

    @Basic
    @Column(name = "Incoterms")
    public Integer getIncoterms() {
        return incoterms;
    }

    public void setIncoterms(Integer incoterms) {
        this.incoterms = incoterms;
    }

    @Basic
    @Column(name = "TransMod")
    public Integer getTransMod() {
        return transMod;
    }

    public void setTransMod(Integer transMod) {
        this.transMod = transMod;
    }

    @Basic
    @Column(name = "LineVendor")
    public String getLineVendor() {
        return lineVendor;
    }

    public void setLineVendor(String lineVendor) {
        this.lineVendor = lineVendor;
    }

    @Basic
    @Column(name = "DistribIS")
    public String getDistribIs() {
        return distribIs;
    }

    public void setDistribIs(String distribIs) {
        this.distribIs = distribIs;
    }

    @Basic
    @Column(name = "ISDistrb")
    public Long getIsDistrb() {
        return isDistrb;
    }

    public void setIsDistrb(Long isDistrb) {
        this.isDistrb = isDistrb;
    }

    @Basic
    @Column(name = "ISDistrbFC")
    public Long getIsDistrbFc() {
        return isDistrbFc;
    }

    public void setIsDistrbFc(Long isDistrbFc) {
        this.isDistrbFc = isDistrbFc;
    }

    @Basic
    @Column(name = "ISDistrbSC")
    public Long getIsDistrbSc() {
        return isDistrbSc;
    }

    public void setIsDistrbSc(Long isDistrbSc) {
        this.isDistrbSc = isDistrbSc;
    }

    @Basic
    @Column(name = "IsByPrdct")
    public String getIsByPrdct() {
        return isByPrdct;
    }

    public void setIsByPrdct(String isByPrdct) {
        this.isByPrdct = isByPrdct;
    }

    @Basic
    @Column(name = "ItemType")
    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    @Basic
    @Column(name = "PriceEdit")
    public String getPriceEdit() {
        return priceEdit;
    }

    public void setPriceEdit(String priceEdit) {
        this.priceEdit = priceEdit;
    }

    @Basic
    @Column(name = "PrntLnNum")
    public Integer getPrntLnNum() {
        return prntLnNum;
    }

    public void setPrntLnNum(Integer prntLnNum) {
        this.prntLnNum = prntLnNum;
    }

    @Basic
    @Column(name = "LinePoPrss")
    public String getLinePoPrss() {
        return linePoPrss;
    }

    public void setLinePoPrss(String linePoPrss) {
        this.linePoPrss = linePoPrss;
    }

    @Basic
    @Column(name = "FreeChrgBP")
    public String getFreeChrgBp() {
        return freeChrgBp;
    }

    public void setFreeChrgBp(String freeChrgBp) {
        this.freeChrgBp = freeChrgBp;
    }

    @Basic
    @Column(name = "TaxRelev")
    public String getTaxRelev() {
        return taxRelev;
    }

    public void setTaxRelev(String taxRelev) {
        this.taxRelev = taxRelev;
    }

    @Basic
    @Column(name = "LegalText")
    public String getLegalText() {
        return legalText;
    }

    public void setLegalText(String legalText) {
        this.legalText = legalText;
    }

    @Basic
    @Column(name = "ThirdParty")
    public String getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(String thirdParty) {
        this.thirdParty = thirdParty;
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
    @Column(name = "InvQtyOnly")
    public String getInvQtyOnly() {
        return invQtyOnly;
    }

    public void setInvQtyOnly(String invQtyOnly) {
        this.invQtyOnly = invQtyOnly;
    }

    @Basic
    @Column(name = "UnencReasn")
    public Integer getUnencReasn() {
        return unencReasn;
    }

    public void setUnencReasn(Integer unencReasn) {
        this.unencReasn = unencReasn;
    }

    @Basic
    @Column(name = "ShipFromCo")
    public String getShipFromCo() {
        return shipFromCo;
    }

    public void setShipFromCo(String shipFromCo) {
        this.shipFromCo = shipFromCo;
    }

    @Basic
    @Column(name = "ShipFromDe")
    public String getShipFromDe() {
        return shipFromDe;
    }

    public void setShipFromDe(String shipFromDe) {
        this.shipFromDe = shipFromDe;
    }

    @Basic
    @Column(name = "FisrtBin")
    public String getFisrtBin() {
        return fisrtBin;
    }

    public void setFisrtBin(String fisrtBin) {
        this.fisrtBin = fisrtBin;
    }

    @Basic
    @Column(name = "AllocBinC")
    public String getAllocBinC() {
        return allocBinC;
    }

    public void setAllocBinC(String allocBinC) {
        this.allocBinC = allocBinC;
    }

    @Basic
    @Column(name = "ExpType")
    public String getExpType() {
        return expType;
    }

    public void setExpType(String expType) {
        this.expType = expType;
    }

    @Basic
    @Column(name = "ExpUUID")
    public String getExpUuid() {
        return expUuid;
    }

    public void setExpUuid(String expUuid) {
        this.expUuid = expUuid;
    }

    @Basic
    @Column(name = "ExpOpType")
    public String getExpOpType() {
        return expOpType;
    }

    public void setExpOpType(String expOpType) {
        this.expOpType = expOpType;
    }

    @Basic
    @Column(name = "DIOTNat")
    public String getDiotNat() {
        return diotNat;
    }

    public void setDiotNat(String diotNat) {
        this.diotNat = diotNat;
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
    @Column(name = "ItmTaxType")
    public String getItmTaxType() {
        return itmTaxType;
    }

    public void setItmTaxType(String itmTaxType) {
        this.itmTaxType = itmTaxType;
    }

    @Basic
    @Column(name = "SacEntry")
    public Integer getSacEntry() {
        return sacEntry;
    }

    public void setSacEntry(Integer sacEntry) {
        this.sacEntry = sacEntry;
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
    @Column(name = "U_Tenkythuat")
    public String getuTenkythuat() {
        return uTenkythuat;
    }

    public void setuTenkythuat(String uTenkythuat) {
        this.uTenkythuat = uTenkythuat;
    }

    @Basic
    @Column(name = "U_QCCode")
    public Integer getuQcCode() {
        return uQcCode;
    }

    public void setuQcCode(Integer uQcCode) {
        this.uQcCode = uQcCode;
    }

    @Basic
    @Column(name = "U_QCLine")
    public Integer getuQcLine() {
        return uQcLine;
    }

    public void setuQcLine(Integer uQcLine) {
        this.uQcLine = uQcLine;
    }

    @Basic
    @Column(name = "U_CKNB")
    public String getuCknb() {
        return uCknb;
    }

    public void setuCknb(String uCknb) {
        this.uCknb = uCknb;
    }

    @Basic
    @Column(name = "U_KM")
    public String getuKm() {
        return uKm;
    }

    public void setuKm(String uKm) {
        this.uKm = uKm;
    }

    @Basic
    @Column(name = "U_SendToHO")
    public Short getuSendToHo() {
        return uSendToHo;
    }

    public void setuSendToHo(Short uSendToHo) {
        this.uSendToHo = uSendToHo;
    }

    @Basic
    @Column(name = "U_DVTthuc")
    public String getuDvTthuc() {
        return uDvTthuc;
    }

    public void setuDvTthuc(String uDvTthuc) {
        this.uDvTthuc = uDvTthuc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Por1Entity that = (Por1Entity) o;
        return docEntry == that.docEntry && lineNum == that.lineNum && Objects.equals(targetType, that.targetType) && Objects.equals(trgetEntry, that.trgetEntry) && Objects.equals(baseRef, that.baseRef) && Objects.equals(baseType, that.baseType) && Objects.equals(baseEntry, that.baseEntry) && Objects.equals(baseLine, that.baseLine) && Objects.equals(lineStatus, that.lineStatus) && Objects.equals(itemCode, that.itemCode) && Objects.equals(dscription, that.dscription) && Objects.equals(quantity, that.quantity) && Objects.equals(shipDate, that.shipDate) && Objects.equals(openQty, that.openQty) && Objects.equals(price, that.price) && Objects.equals(currency, that.currency) && Objects.equals(rate, that.rate) && Objects.equals(discPrcnt, that.discPrcnt) && Objects.equals(lineTotal, that.lineTotal) && Objects.equals(totalFrgn, that.totalFrgn) && Objects.equals(openSum, that.openSum) && Objects.equals(openSumFc, that.openSumFc) && Objects.equals(vendorNum, that.vendorNum) && Objects.equals(serialNum, that.serialNum) && Objects.equals(whsCode, that.whsCode) && Objects.equals(slpCode, that.slpCode) && Objects.equals(commission, that.commission) && Objects.equals(treeType, that.treeType) && Objects.equals(acctCode, that.acctCode) && Objects.equals(taxStatus, that.taxStatus) && Objects.equals(grossBuyPr, that.grossBuyPr) && Objects.equals(priceBefDi, that.priceBefDi) && Objects.equals(docDate, that.docDate) && Objects.equals(flags, that.flags) && Objects.equals(openCreQty, that.openCreQty) && Objects.equals(useBaseUn, that.useBaseUn) && Objects.equals(subCatNum, that.subCatNum) && Objects.equals(baseCard, that.baseCard) && Objects.equals(totalSumSy, that.totalSumSy) && Objects.equals(openSumSys, that.openSumSys) && Objects.equals(invntSttus, that.invntSttus) && Objects.equals(ocrCode, that.ocrCode) && Objects.equals(project, that.project) && Objects.equals(codeBars, that.codeBars) && Objects.equals(vatPrcnt, that.vatPrcnt) && Objects.equals(vatGroup, that.vatGroup) && Objects.equals(priceAfVat, that.priceAfVat) && Objects.equals(height1, that.height1) && Objects.equals(hght1Unit, that.hght1Unit) && Objects.equals(height2, that.height2) && Objects.equals(hght2Unit, that.hght2Unit) && Objects.equals(width1, that.width1) && Objects.equals(wdth1Unit, that.wdth1Unit) && Objects.equals(width2, that.width2) && Objects.equals(wdth2Unit, that.wdth2Unit) && Objects.equals(length1, that.length1) && Objects.equals(len1Unit, that.len1Unit) && Objects.equals(length2, that.length2) && Objects.equals(len2Unit, that.len2Unit) && Objects.equals(volume, that.volume) && Objects.equals(volUnit, that.volUnit) && Objects.equals(weight1, that.weight1) && Objects.equals(wght1Unit, that.wght1Unit) && Objects.equals(weight2, that.weight2) && Objects.equals(wght2Unit, that.wght2Unit) && Objects.equals(factor1, that.factor1) && Objects.equals(factor2, that.factor2) && Objects.equals(factor3, that.factor3) && Objects.equals(factor4, that.factor4) && Objects.equals(packQty, that.packQty) && Objects.equals(updInvntry, that.updInvntry) && Objects.equals(baseDocNum, that.baseDocNum) && Objects.equals(baseAtCard, that.baseAtCard) && Objects.equals(sww, that.sww) && Objects.equals(vatSum, that.vatSum) && Objects.equals(vatSumFrgn, that.vatSumFrgn) && Objects.equals(vatSumSy, that.vatSumSy) && Objects.equals(finncPriod, that.finncPriod) && Objects.equals(objType, that.objType) && Objects.equals(logInstanc, that.logInstanc) && Objects.equals(blockNum, that.blockNum) && Objects.equals(importLog, that.importLog) && Objects.equals(dedVatSum, that.dedVatSum) && Objects.equals(dedVatSumF, that.dedVatSumF) && Objects.equals(dedVatSumS, that.dedVatSumS) && Objects.equals(isAqcuistn, that.isAqcuistn) && Objects.equals(distribSum, that.distribSum) && Objects.equals(dstrbSumFc, that.dstrbSumFc) && Objects.equals(dstrbSumSc, that.dstrbSumSc) && Objects.equals(grssProfit, that.grssProfit) && Objects.equals(grssProfSc, that.grssProfSc) && Objects.equals(grssProfFc, that.grssProfFc) && Objects.equals(visOrder, that.visOrder) && Objects.equals(inmPrice, that.inmPrice) && Objects.equals(poTrgNum, that.poTrgNum) && Objects.equals(poTrgEntry, that.poTrgEntry) && Objects.equals(dropShip, that.dropShip) && Objects.equals(poLineNum, that.poLineNum) && Objects.equals(address, that.address) && Objects.equals(taxCode, that.taxCode) && Objects.equals(taxType, that.taxType) && Objects.equals(origItem, that.origItem) && Objects.equals(backOrdr, that.backOrdr) && Objects.equals(freeTxt, that.freeTxt) && Objects.equals(pickStatus, that.pickStatus) && Objects.equals(pickOty, that.pickOty) && Objects.equals(pickIdNo, that.pickIdNo) && Objects.equals(trnsCode, that.trnsCode) && Objects.equals(vatAppld, that.vatAppld) && Objects.equals(vatAppldFc, that.vatAppldFc) && Objects.equals(vatAppldSc, that.vatAppldSc) && Objects.equals(baseQty, that.baseQty) && Objects.equals(baseOpnQty, that.baseOpnQty) && Objects.equals(vatDscntPr, that.vatDscntPr) && Objects.equals(wtLiable, that.wtLiable) && Objects.equals(deferrTax, that.deferrTax) && Objects.equals(equVatPer, that.equVatPer) && Objects.equals(equVatSum, that.equVatSum) && Objects.equals(equVatSumF, that.equVatSumF) && Objects.equals(equVatSumS, that.equVatSumS) && Objects.equals(lineVat, that.lineVat) && Objects.equals(lineVatlF, that.lineVatlF) && Objects.equals(lineVatS, that.lineVatS) && Objects.equals(unitMsr, that.unitMsr) && Objects.equals(numPerMsr, that.numPerMsr) && Objects.equals(ceecFlag, that.ceecFlag) && Objects.equals(toStock, that.toStock) && Objects.equals(toDiff, that.toDiff) && Objects.equals(exciseAmt, that.exciseAmt) && Objects.equals(taxPerUnit, that.taxPerUnit) && Objects.equals(totInclTax, that.totInclTax) && Objects.equals(countryOrg, that.countryOrg) && Objects.equals(stckDstSum, that.stckDstSum) && Objects.equals(releasQtty, that.releasQtty) && Objects.equals(lineType, that.lineType) && Objects.equals(tranType, that.tranType) && Objects.equals(text, that.text) && Objects.equals(ownerCode, that.ownerCode) && Objects.equals(stockPrice, that.stockPrice) && Objects.equals(consumeFct, that.consumeFct) && Objects.equals(lstByDsSum, that.lstByDsSum) && Objects.equals(stckInmPr, that.stckInmPr) && Objects.equals(lstBinmPr, that.lstBinmPr) && Objects.equals(stckDstFc, that.stckDstFc) && Objects.equals(stckDstSc, that.stckDstSc) && Objects.equals(lstByDsFc, that.lstByDsFc) && Objects.equals(lstByDsSc, that.lstByDsSc) && Objects.equals(stockSum, that.stockSum) && Objects.equals(stockSumFc, that.stockSumFc) && Objects.equals(stockSumSc, that.stockSumSc) && Objects.equals(stckSumApp, that.stckSumApp) && Objects.equals(stckAppFc, that.stckAppFc) && Objects.equals(stckAppSc, that.stckAppSc) && Objects.equals(shipToCode, that.shipToCode) && Objects.equals(shipToDesc, that.shipToDesc) && Objects.equals(stckAppD, that.stckAppD) && Objects.equals(stckAppDfc, that.stckAppDfc) && Objects.equals(stckAppDsc, that.stckAppDsc) && Objects.equals(basePrice, that.basePrice) && Objects.equals(gTotal, that.gTotal) && Objects.equals(gTotalFc, that.gTotalFc) && Objects.equals(gTotalSc, that.gTotalSc) && Objects.equals(distribExp, that.distribExp) && Objects.equals(descOw, that.descOw) && Objects.equals(detailsOw, that.detailsOw) && Objects.equals(grossBase, that.grossBase) && Objects.equals(vatWoDpm, that.vatWoDpm) && Objects.equals(vatWoDpmFc, that.vatWoDpmFc) && Objects.equals(vatWoDpmSc, that.vatWoDpmSc) && Objects.equals(cfopCode, that.cfopCode) && Objects.equals(cstCode, that.cstCode) && Objects.equals(usage, that.usage) && Objects.equals(taxOnly, that.taxOnly) && Objects.equals(wtCalced, that.wtCalced) && Objects.equals(qtyToShip, that.qtyToShip) && Objects.equals(delivrdQty, that.delivrdQty) && Objects.equals(orderedQty, that.orderedQty) && Objects.equals(cogsOcrCod, that.cogsOcrCod) && Objects.equals(ciOppLineN, that.ciOppLineN) && Objects.equals(cogsAcct, that.cogsAcct) && Objects.equals(chgAsmBoMw, that.chgAsmBoMw) && Objects.equals(actDelDate, that.actDelDate) && Objects.equals(ocrCode2, that.ocrCode2) && Objects.equals(ocrCode3, that.ocrCode3) && Objects.equals(ocrCode4, that.ocrCode4) && Objects.equals(ocrCode5, that.ocrCode5) && Objects.equals(taxDistSum, that.taxDistSum) && Objects.equals(taxDistSfc, that.taxDistSfc) && Objects.equals(taxDistSsc, that.taxDistSsc) && Objects.equals(postTax, that.postTax) && Objects.equals(excisable, that.excisable) && Objects.equals(assblValue, that.assblValue) && Objects.equals(rg23APart1, that.rg23APart1) && Objects.equals(rg23APart2, that.rg23APart2) && Objects.equals(rg23CPart1, that.rg23CPart1) && Objects.equals(rg23CPart2, that.rg23CPart2) && Objects.equals(cogsOcrCo2, that.cogsOcrCo2) && Objects.equals(cogsOcrCo3, that.cogsOcrCo3) && Objects.equals(cogsOcrCo4, that.cogsOcrCo4) && Objects.equals(cogsOcrCo5, that.cogsOcrCo5) && Objects.equals(lnExcised, that.lnExcised) && Objects.equals(locCode, that.locCode) && Objects.equals(stockValue, that.stockValue) && Objects.equals(gpTtlBasPr, that.gpTtlBasPr) && Objects.equals(unitMsr2, that.unitMsr2) && Objects.equals(numPerMsr2, that.numPerMsr2) && Objects.equals(specPrice, that.specPrice) && Objects.equals(csTfIpi, that.csTfIpi) && Objects.equals(csTfPis, that.csTfPis) && Objects.equals(csTfCofins, that.csTfCofins) && Objects.equals(exLineNo, that.exLineNo) && Objects.equals(isSrvCall, that.isSrvCall) && Objects.equals(pqtReqQty, that.pqtReqQty) && Objects.equals(pqtReqDate, that.pqtReqDate) && Objects.equals(pcDocType, that.pcDocType) && Objects.equals(pcQuantity, that.pcQuantity) && Objects.equals(linManClsd, that.linManClsd) && Objects.equals(vatGrpSrc, that.vatGrpSrc) && Objects.equals(noInvtryMv, that.noInvtryMv) && Objects.equals(actBaseEnt, that.actBaseEnt) && Objects.equals(actBaseLn, that.actBaseLn) && Objects.equals(actBaseNum, that.actBaseNum) && Objects.equals(openRtnQty, that.openRtnQty) && Objects.equals(agrNo, that.agrNo) && Objects.equals(agrLnNum, that.agrLnNum) && Objects.equals(credOrigin, that.credOrigin) && Objects.equals(surpluses, that.surpluses) && Objects.equals(defBreak, that.defBreak) && Objects.equals(shortages, that.shortages) && Objects.equals(uomEntry, that.uomEntry) && Objects.equals(uomEntry2, that.uomEntry2) && Objects.equals(uomCode, that.uomCode) && Objects.equals(uomCode2, that.uomCode2) && Objects.equals(fromWhsCod, that.fromWhsCod) && Objects.equals(needQty, that.needQty) && Objects.equals(partRetire, that.partRetire) && Objects.equals(retireQty, that.retireQty) && Objects.equals(retireApc, that.retireApc) && Objects.equals(retirApcfc, that.retirApcfc) && Objects.equals(retirApcsc, that.retirApcsc) && Objects.equals(invQty, that.invQty) && Objects.equals(openInvQty, that.openInvQty) && Objects.equals(enSetCost, that.enSetCost) && Objects.equals(retCost, that.retCost) && Objects.equals(incoterms, that.incoterms) && Objects.equals(transMod, that.transMod) && Objects.equals(lineVendor, that.lineVendor) && Objects.equals(distribIs, that.distribIs) && Objects.equals(isDistrb, that.isDistrb) && Objects.equals(isDistrbFc, that.isDistrbFc) && Objects.equals(isDistrbSc, that.isDistrbSc) && Objects.equals(isByPrdct, that.isByPrdct) && Objects.equals(itemType, that.itemType) && Objects.equals(priceEdit, that.priceEdit) && Objects.equals(prntLnNum, that.prntLnNum) && Objects.equals(linePoPrss, that.linePoPrss) && Objects.equals(freeChrgBp, that.freeChrgBp) && Objects.equals(taxRelev, that.taxRelev) && Objects.equals(legalText, that.legalText) && Objects.equals(thirdParty, that.thirdParty) && Objects.equals(licTradNum, that.licTradNum) && Objects.equals(invQtyOnly, that.invQtyOnly) && Objects.equals(unencReasn, that.unencReasn) && Objects.equals(shipFromCo, that.shipFromCo) && Objects.equals(shipFromDe, that.shipFromDe) && Objects.equals(fisrtBin, that.fisrtBin) && Objects.equals(allocBinC, that.allocBinC) && Objects.equals(expType, that.expType) && Objects.equals(expUuid, that.expUuid) && Objects.equals(expOpType, that.expOpType) && Objects.equals(diotNat, that.diotNat) && Objects.equals(myFtype, that.myFtype) && Objects.equals(itmTaxType, that.itmTaxType) && Objects.equals(sacEntry, that.sacEntry) && Objects.equals(uRdcode, that.uRdcode) && Objects.equals(uTenkythuat, that.uTenkythuat) && Objects.equals(uQcCode, that.uQcCode) && Objects.equals(uQcLine, that.uQcLine) && Objects.equals(uCknb, that.uCknb) && Objects.equals(uKm, that.uKm) && Objects.equals(uSendToHo, that.uSendToHo) && Objects.equals(uDvTthuc, that.uDvTthuc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(docEntry, lineNum, targetType, trgetEntry, baseRef, baseType, baseEntry, baseLine, lineStatus, itemCode, dscription, quantity, shipDate, openQty, price, currency, rate, discPrcnt, lineTotal, totalFrgn, openSum, openSumFc, vendorNum, serialNum, whsCode, slpCode, commission, treeType, acctCode, taxStatus, grossBuyPr, priceBefDi, docDate, flags, openCreQty, useBaseUn, subCatNum, baseCard, totalSumSy, openSumSys, invntSttus, ocrCode, project, codeBars, vatPrcnt, vatGroup, priceAfVat, height1, hght1Unit, height2, hght2Unit, width1, wdth1Unit, width2, wdth2Unit, length1, len1Unit, length2, len2Unit, volume, volUnit, weight1, wght1Unit, weight2, wght2Unit, factor1, factor2, factor3, factor4, packQty, updInvntry, baseDocNum, baseAtCard, sww, vatSum, vatSumFrgn, vatSumSy, finncPriod, objType, logInstanc, blockNum, importLog, dedVatSum, dedVatSumF, dedVatSumS, isAqcuistn, distribSum, dstrbSumFc, dstrbSumSc, grssProfit, grssProfSc, grssProfFc, visOrder, inmPrice, poTrgNum, poTrgEntry, dropShip, poLineNum, address, taxCode, taxType, origItem, backOrdr, freeTxt, pickStatus, pickOty, pickIdNo, trnsCode, vatAppld, vatAppldFc, vatAppldSc, baseQty, baseOpnQty, vatDscntPr, wtLiable, deferrTax, equVatPer, equVatSum, equVatSumF, equVatSumS, lineVat, lineVatlF, lineVatS, unitMsr, numPerMsr, ceecFlag, toStock, toDiff, exciseAmt, taxPerUnit, totInclTax, countryOrg, stckDstSum, releasQtty, lineType, tranType, text, ownerCode, stockPrice, consumeFct, lstByDsSum, stckInmPr, lstBinmPr, stckDstFc, stckDstSc, lstByDsFc, lstByDsSc, stockSum, stockSumFc, stockSumSc, stckSumApp, stckAppFc, stckAppSc, shipToCode, shipToDesc, stckAppD, stckAppDfc, stckAppDsc, basePrice, gTotal, gTotalFc, gTotalSc, distribExp, descOw, detailsOw, grossBase, vatWoDpm, vatWoDpmFc, vatWoDpmSc, cfopCode, cstCode, usage, taxOnly, wtCalced, qtyToShip, delivrdQty, orderedQty, cogsOcrCod, ciOppLineN, cogsAcct, chgAsmBoMw, actDelDate, ocrCode2, ocrCode3, ocrCode4, ocrCode5, taxDistSum, taxDistSfc, taxDistSsc, postTax, excisable, assblValue, rg23APart1, rg23APart2, rg23CPart1, rg23CPart2, cogsOcrCo2, cogsOcrCo3, cogsOcrCo4, cogsOcrCo5, lnExcised, locCode, stockValue, gpTtlBasPr, unitMsr2, numPerMsr2, specPrice, csTfIpi, csTfPis, csTfCofins, exLineNo, isSrvCall, pqtReqQty, pqtReqDate, pcDocType, pcQuantity, linManClsd, vatGrpSrc, noInvtryMv, actBaseEnt, actBaseLn, actBaseNum, openRtnQty, agrNo, agrLnNum, credOrigin, surpluses, defBreak, shortages, uomEntry, uomEntry2, uomCode, uomCode2, fromWhsCod, needQty, partRetire, retireQty, retireApc, retirApcfc, retirApcsc, invQty, openInvQty, enSetCost, retCost, incoterms, transMod, lineVendor, distribIs, isDistrb, isDistrbFc, isDistrbSc, isByPrdct, itemType, priceEdit, prntLnNum, linePoPrss, freeChrgBp, taxRelev, legalText, thirdParty, licTradNum, invQtyOnly, unencReasn, shipFromCo, shipFromDe, fisrtBin, allocBinC, expType, expUuid, expOpType, diotNat, myFtype, itmTaxType, sacEntry, uRdcode, uTenkythuat, uQcCode, uQcLine, uCknb, uKm, uSendToHo, uDvTthuc);
    }

    @Basic
    @Column(name = "U_SO")
    public String getuSo() {
        return uSo;
    }

    public void setuSo(String uSo) {
        this.uSo = uSo;
    }

    @Basic
    @Column(name = "U_MCode")
    public String getuMcode() {
        return uMcode;
    }

    public void setuMcode(String uMcode) {
        this.uMcode = uMcode;
    }
}
