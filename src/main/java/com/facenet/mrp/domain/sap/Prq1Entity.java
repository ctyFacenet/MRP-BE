package com.facenet.mrp.domain.sap;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "PRQ1")
@IdClass(Prq1EntityPK.class)
public class Prq1Entity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "DocEntry")
    private Integer docEntry;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "LineNum")
    private Integer lineNum;

    @Basic
    @Column(name = "U_SO")
    private String uSo;

    @Basic
    @Column(name = "U_Mcode")
    private String uMcode;

    @Basic
    @Column(name = "TargetType")
    private Integer targetType;
    @Basic
    @Column(name = "TrgetEntry")
    private Integer trgetEntry;
    @Basic
    @Column(name = "BaseRef")
    private String baseRef;
    @Basic
    @Column(name = "BaseType")
    private Integer baseType;
    @Basic
    @Column(name = "BaseEntry")
    private Integer baseEntry;
    @Basic
    @Column(name = "BaseLine")
    private Integer baseLine;
    @Basic
    @Column(name = "LineStatus")
    private String lineStatus;
    @Basic
    @Column(name = "ItemCode")
    private String itemCode;
    @Basic
    @Column(name = "Dscription")
    private String dscription;
    @Basic
    @Column(name = "Quantity")
    private Long quantity;
    @Basic
    @Column(name = "ShipDate")
    private Date shipDate;
    @Basic
    @Column(name = "OpenQty")
    private Double openQty;
    @Basic
    @Column(name = "Price")
    private BigDecimal price;
    @Basic
    @Column(name = "Currency")
    private String currency;
    @Basic
    @Column(name = "Rate")
    private BigDecimal rate;
    @Basic
    @Column(name = "DiscPrcnt")
    private BigDecimal discPrcnt;
    @Basic
    @Column(name = "LineTotal")
    private BigDecimal lineTotal;
    @Basic
    @Column(name = "TotalFrgn")
    private BigDecimal totalFrgn;
    @Basic
    @Column(name = "OpenSum")
    private BigDecimal openSum;
    @Basic
    @Column(name = "OpenSumFC")
    private BigDecimal openSumFc;
    @Basic
    @Column(name = "VendorNum")
    private String vendorNum;
    @Basic
    @Column(name = "SerialNum")
    private String serialNum;
    @Basic
    @Column(name = "WhsCode")
    private String whsCode;
    @Basic
    @Column(name = "SlpCode")
    private Integer slpCode;
    @Basic
    @Column(name = "Commission")
    private BigDecimal commission;
    @Basic
    @Column(name = "TreeType")
    private String treeType;
    @Basic
    @Column(name = "AcctCode")
    private String acctCode;
    @Basic
    @Column(name = "TaxStatus")
    private String taxStatus;
    @Basic
    @Column(name = "GrossBuyPr")
    private BigDecimal grossBuyPr;
    @Basic
    @Column(name = "PriceBefDi")
    private BigDecimal priceBefDi;
    @Basic
    @Column(name = "DocDate")
    private Date docDate;
    @Basic
    @Column(name = "Flags")
    private Integer flags;
    @Basic
    @Column(name = "OpenCreQty")
    private BigDecimal openCreQty;
    @Basic
    @Column(name = "UseBaseUn")
    private String useBaseUn;
    @Basic
    @Column(name = "SubCatNum")
    private String subCatNum;
    @Basic
    @Column(name = "BaseCard")
    private String baseCard;
    @Basic
    @Column(name = "TotalSumSy")
    private BigDecimal totalSumSy;
    @Basic
    @Column(name = "OpenSumSys")
    private BigDecimal openSumSys;
    @Basic
    @Column(name = "InvntSttus")
    private String invntSttus;
    @Basic
    @Column(name = "OcrCode")
    private String ocrCode;
    @Basic
    @Column(name = "Project")
    private String project;
    @Basic
    @Column(name = "CodeBars")
    private String codeBars;
    @Basic
    @Column(name = "VatPrcnt")
    private BigDecimal vatPrcnt;
    @Basic
    @Column(name = "VatGroup")
    private String vatGroup;
    @Basic
    @Column(name = "PriceAfVAT")
    private BigDecimal priceAfVat;
    @Basic
    @Column(name = "Height1")
    private BigDecimal height1;
    @Basic
    @Column(name = "Hght1Unit")
    private Short hght1Unit;
    @Basic
    @Column(name = "Height2")
    private BigDecimal height2;
    @Basic
    @Column(name = "Hght2Unit")
    private Short hght2Unit;
    @Basic
    @Column(name = "Width1")
    private BigDecimal width1;
    @Basic
    @Column(name = "Wdth1Unit")
    private Short wdth1Unit;
    @Basic
    @Column(name = "Width2")
    private BigDecimal width2;
    @Basic
    @Column(name = "Wdth2Unit")
    private Short wdth2Unit;
    @Basic
    @Column(name = "Length1")
    private BigDecimal length1;
    @Basic
    @Column(name = "Len1Unit")
    private Short len1Unit;
    @Basic
    @Column(name = "length2")
    private BigDecimal length2;
    @Basic
    @Column(name = "Len2Unit")
    private Short len2Unit;
    @Basic
    @Column(name = "Volume")
    private BigDecimal volume;
    @Basic
    @Column(name = "VolUnit")
    private Short volUnit;
    @Basic
    @Column(name = "Weight1")
    private BigDecimal weight1;
    @Basic
    @Column(name = "Wght1Unit")
    private Short wght1Unit;
    @Basic
    @Column(name = "Weight2")
    private BigDecimal weight2;
    @Basic
    @Column(name = "Wght2Unit")
    private Short wght2Unit;
    @Basic
    @Column(name = "Factor1")
    private BigDecimal factor1;
    @Basic
    @Column(name = "Factor2")
    private BigDecimal factor2;
    @Basic
    @Column(name = "Factor3")
    private BigDecimal factor3;
    @Basic
    @Column(name = "Factor4")
    private BigDecimal factor4;
    @Basic
    @Column(name = "PackQty")
    private BigDecimal packQty;
    @Basic
    @Column(name = "UpdInvntry")
    private String updInvntry;
    @Basic
    @Column(name = "BaseDocNum")
    private Integer baseDocNum;
    @Basic
    @Column(name = "BaseAtCard")
    private String baseAtCard;
    @Basic
    @Column(name = "SWW")
    private String sww;
    @Basic
    @Column(name = "VatSum")
    private BigDecimal vatSum;
    @Basic
    @Column(name = "VatSumFrgn")
    private BigDecimal vatSumFrgn;
    @Basic
    @Column(name = "VatSumSy")
    private BigDecimal vatSumSy;
    @Basic
    @Column(name = "FinncPriod")
    private Integer finncPriod;
    @Basic
    @Column(name = "ObjType")
    private String objType;
    @Basic
    @Column(name = "LogInstanc")
    private Integer logInstanc;
    @Basic
    @Column(name = "BlockNum")
    private String blockNum;
    @Basic
    @Column(name = "ImportLog")
    private String importLog;
    @Basic
    @Column(name = "DedVatSum")
    private BigDecimal dedVatSum;
    @Basic
    @Column(name = "DedVatSumF")
    private BigDecimal dedVatSumF;
    @Basic
    @Column(name = "DedVatSumS")
    private BigDecimal dedVatSumS;
    @Basic
    @Column(name = "IsAqcuistn")
    private String isAqcuistn;
    @Basic
    @Column(name = "DistribSum")
    private BigDecimal distribSum;
    @Basic
    @Column(name = "DstrbSumFC")
    private BigDecimal dstrbSumFc;
    @Basic
    @Column(name = "DstrbSumSC")
    private BigDecimal dstrbSumSc;
    @Basic
    @Column(name = "GrssProfit")
    private BigDecimal grssProfit;
    @Basic
    @Column(name = "GrssProfSC")
    private BigDecimal grssProfSc;
    @Basic
    @Column(name = "GrssProfFC")
    private BigDecimal grssProfFc;
    @Basic
    @Column(name = "VisOrder")
    private Integer visOrder;
    @Basic
    @Column(name = "INMPrice")
    private BigDecimal inmPrice;
    @Basic
    @Column(name = "PoTrgNum")
    private Integer poTrgNum;
    @Basic
    @Column(name = "PoTrgEntry")
    private String poTrgEntry;
    @Basic
    @Column(name = "DropShip")
    private String dropShip;
    @Basic
    @Column(name = "PoLineNum")
    private Integer poLineNum;
    @Basic
    @Column(name = "Address")
    private String address;
    @Basic
    @Column(name = "TaxCode")
    private String taxCode;
    @Basic
    @Column(name = "TaxType")
    private String taxType;
    @Basic
    @Column(name = "OrigItem")
    private String origItem;
    @Basic
    @Column(name = "BackOrdr")
    private String backOrdr;
    @Basic
    @Column(name = "FreeTxt")
    private String freeTxt;
    @Basic
    @Column(name = "PickStatus")
    private String pickStatus;
    @Basic
    @Column(name = "PickOty")
    private BigDecimal pickOty;
    @Basic
    @Column(name = "PickIdNo")
    private Integer pickIdNo;
    @Basic
    @Column(name = "TrnsCode")
    private Short trnsCode;
    @Basic
    @Column(name = "VatAppld")
    private BigDecimal vatAppld;
    @Basic
    @Column(name = "VatAppldFC")
    private BigDecimal vatAppldFc;
    @Basic
    @Column(name = "VatAppldSC")
    private BigDecimal vatAppldSc;
    @Basic
    @Column(name = "BaseQty")
    private BigDecimal baseQty;
    @Basic
    @Column(name = "BaseOpnQty")
    private BigDecimal baseOpnQty;
    @Basic
    @Column(name = "VatDscntPr")
    private BigDecimal vatDscntPr;
    @Basic
    @Column(name = "WtLiable")
    private String wtLiable;
    @Basic
    @Column(name = "DeferrTax")
    private String deferrTax;
    @Basic
    @Column(name = "EquVatPer")
    private BigDecimal equVatPer;
    @Basic
    @Column(name = "EquVatSum")
    private BigDecimal equVatSum;
    @Basic
    @Column(name = "EquVatSumF")
    private BigDecimal equVatSumF;
    @Basic
    @Column(name = "EquVatSumS")
    private BigDecimal equVatSumS;
    @Basic
    @Column(name = "LineVat")
    private BigDecimal lineVat;
    @Basic
    @Column(name = "LineVatlF")
    private BigDecimal lineVatlF;
    @Basic
    @Column(name = "LineVatS")
    private BigDecimal lineVatS;
    @Basic
    @Column(name = "unitMsr")
    private String unitMsr;
    @Basic
    @Column(name = "NumPerMsr")
    private BigDecimal numPerMsr;
    @Basic
    @Column(name = "CEECFlag")
    private String ceecFlag;
    @Basic
    @Column(name = "ToStock")
    private BigDecimal toStock;
    @Basic
    @Column(name = "ToDiff")
    private BigDecimal toDiff;
    @Basic
    @Column(name = "ExciseAmt")
    private BigDecimal exciseAmt;
    @Basic
    @Column(name = "TaxPerUnit")
    private BigDecimal taxPerUnit;
    @Basic
    @Column(name = "TotInclTax")
    private BigDecimal totInclTax;
    @Basic
    @Column(name = "CountryOrg")
    private String countryOrg;
    @Basic
    @Column(name = "StckDstSum")
    private BigDecimal stckDstSum;
    @Basic
    @Column(name = "ReleasQtty")
    private BigDecimal releasQtty;
    @Basic
    @Column(name = "LineType")
    private String lineType;
    @Basic
    @Column(name = "TranType")
    private String tranType;
    @Basic
    @Column(name = "Text")
    private String text;
    @Basic
    @Column(name = "OwnerCode")
    private Integer ownerCode;
    @Basic
    @Column(name = "StockPrice")
    private BigDecimal stockPrice;
    @Basic
    @Column(name = "ConsumeFCT")
    private String consumeFct;
    @Basic
    @Column(name = "LstByDsSum")
    private BigDecimal lstByDsSum;
    @Basic
    @Column(name = "StckINMPr")
    private BigDecimal stckInmPr;
    @Basic
    @Column(name = "LstBINMPr")
    private BigDecimal lstBinmPr;
    @Basic
    @Column(name = "StckDstFc")
    private BigDecimal stckDstFc;
    @Basic
    @Column(name = "StckDstSc")
    private BigDecimal stckDstSc;
    @Basic
    @Column(name = "LstByDsFc")
    private BigDecimal lstByDsFc;
    @Basic
    @Column(name = "LstByDsSc")
    private BigDecimal lstByDsSc;
    @Basic
    @Column(name = "StockSum")
    private BigDecimal stockSum;
    @Basic
    @Column(name = "StockSumFc")
    private BigDecimal stockSumFc;
    @Basic
    @Column(name = "StockSumSc")
    private BigDecimal stockSumSc;
    @Basic
    @Column(name = "StckSumApp")
    private BigDecimal stckSumApp;
    @Basic
    @Column(name = "StckAppFc")
    private BigDecimal stckAppFc;
    @Basic
    @Column(name = "StckAppSc")
    private BigDecimal stckAppSc;
    @Basic
    @Column(name = "ShipToCode")
    private String shipToCode;
    @Basic
    @Column(name = "ShipToDesc")
    private String shipToDesc;
    @Basic
    @Column(name = "StckAppD")
    private BigDecimal stckAppD;
    @Basic
    @Column(name = "StckAppDFC")
    private BigDecimal stckAppDfc;
    @Basic
    @Column(name = "StckAppDSC")
    private BigDecimal stckAppDsc;
    @Basic
    @Column(name = "BasePrice")
    private String basePrice;
    @Basic
    @Column(name = "GTotal")
    private BigDecimal gTotal;
    @Basic
    @Column(name = "GTotalFC")
    private BigDecimal gTotalFc;
    @Basic
    @Column(name = "GTotalSC")
    private BigDecimal gTotalSc;
    @Basic
    @Column(name = "DistribExp")
    private String distribExp;
    @Basic
    @Column(name = "DescOW")
    private String descOw;
    @Basic
    @Column(name = "DetailsOW")
    private String detailsOw;
    @Basic
    @Column(name = "GrossBase")
    private Short grossBase;
    @Basic
    @Column(name = "VatWoDpm")
    private BigDecimal vatWoDpm;
    @Basic
    @Column(name = "VatWoDpmFc")
    private BigDecimal vatWoDpmFc;
    @Basic
    @Column(name = "VatWoDpmSc")
    private BigDecimal vatWoDpmSc;
    @Basic
    @Column(name = "CFOPCode")
    private String cfopCode;
    @Basic
    @Column(name = "CSTCode")
    private String cstCode;
    @Basic
    @Column(name = "Usage")
    private Integer usage;
    @Basic
    @Column(name = "TaxOnly")
    private String taxOnly;
    @Basic
    @Column(name = "WtCalced")
    private String wtCalced;
    @Basic
    @Column(name = "QtyToShip")
    private BigDecimal qtyToShip;
    @Basic
    @Column(name = "DelivrdQty")
    private BigDecimal delivrdQty;
    @Basic
    @Column(name = "OrderedQty")
    private BigDecimal orderedQty;
    @Basic
    @Column(name = "CogsOcrCod")
    private String cogsOcrCod;
    @Basic
    @Column(name = "CiOppLineN")
    private Integer ciOppLineN;
    @Basic
    @Column(name = "CogsAcct")
    private String cogsAcct;
    @Basic
    @Column(name = "ChgAsmBoMW")
    private String chgAsmBoMw;
    @Basic
    @Column(name = "ActDelDate")
    private Date actDelDate;
    @Basic
    @Column(name = "OcrCode2")
    private String ocrCode2;
    @Basic
    @Column(name = "OcrCode3")
    private String ocrCode3;
    @Basic
    @Column(name = "OcrCode4")
    private String ocrCode4;
    @Basic
    @Column(name = "OcrCode5")
    private String ocrCode5;
    @Basic
    @Column(name = "TaxDistSum")
    private BigDecimal taxDistSum;
    @Basic
    @Column(name = "TaxDistSFC")
    private BigDecimal taxDistSfc;
    @Basic
    @Column(name = "TaxDistSSC")
    private BigDecimal taxDistSsc;
    @Basic
    @Column(name = "PostTax")
    private String postTax;
    @Basic
    @Column(name = "Excisable")
    private String excisable;
    @Basic
    @Column(name = "AssblValue")
    private BigDecimal assblValue;
    @Basic
    @Column(name = "RG23APart1")
    private Integer rg23APart1;
    @Basic
    @Column(name = "RG23APart2")
    private Integer rg23APart2;
    @Basic
    @Column(name = "RG23CPart1")
    private Integer rg23CPart1;
    @Basic
    @Column(name = "RG23CPart2")
    private Integer rg23CPart2;
    @Basic
    @Column(name = "CogsOcrCo2")
    private String cogsOcrCo2;
    @Basic
    @Column(name = "CogsOcrCo3")
    private String cogsOcrCo3;
    @Basic
    @Column(name = "CogsOcrCo4")
    private String cogsOcrCo4;
    @Basic
    @Column(name = "CogsOcrCo5")
    private String cogsOcrCo5;
    @Basic
    @Column(name = "LnExcised")
    private String lnExcised;
    @Basic
    @Column(name = "LocCode")
    private Integer locCode;
    @Basic
    @Column(name = "StockValue")
    private BigDecimal stockValue;
    @Basic
    @Column(name = "GPTtlBasPr")
    private BigDecimal gpTtlBasPr;
    @Basic
    @Column(name = "unitMsr2")
    private String unitMsr2;
    @Basic
    @Column(name = "NumPerMsr2")
    private BigDecimal numPerMsr2;
    @Basic
    @Column(name = "SpecPrice")
    private String specPrice;
    @Basic
    @Column(name = "CSTfIPI")
    private String csTfIpi;
    @Basic
    @Column(name = "CSTfPIS")
    private String csTfPis;
    @Basic
    @Column(name = "CSTfCOFINS")
    private String csTfCofins;
    @Basic
    @Column(name = "ExLineNo")
    private String exLineNo;
    @Basic
    @Column(name = "isSrvCall")
    private String isSrvCall;
    @Basic
    @Column(name = "PQTReqQty")
    private BigDecimal pqtReqQty;
    @Basic
    @Column(name = "PQTReqDate")
    private Date pqtReqDate;
    @Basic
    @Column(name = "PcDocType")
    private Integer pcDocType;
    @Basic
    @Column(name = "PcQuantity")
    private BigDecimal pcQuantity;
    @Basic
    @Column(name = "LinManClsd")
    private String linManClsd;
    @Basic
    @Column(name = "VatGrpSrc")
    private String vatGrpSrc;
    @Basic
    @Column(name = "NoInvtryMv")
    private String noInvtryMv;
    @Basic
    @Column(name = "ActBaseEnt")
    private Integer actBaseEnt;
    @Basic
    @Column(name = "ActBaseLn")
    private Integer actBaseLn;
    @Basic
    @Column(name = "ActBaseNum")
    private Integer actBaseNum;
    @Basic
    @Column(name = "OpenRtnQty")
    private BigDecimal openRtnQty;
    @Basic
    @Column(name = "AgrNo")
    private Integer agrNo;
    @Basic
    @Column(name = "AgrLnNum")
    private Integer agrLnNum;
    @Basic
    @Column(name = "CredOrigin")
    private String credOrigin;
    @Basic
    @Column(name = "Surpluses")
    private BigDecimal surpluses;
    @Basic
    @Column(name = "DefBreak")
    private BigDecimal defBreak;
    @Basic
    @Column(name = "Shortages")
    private BigDecimal shortages;
    @Basic
    @Column(name = "UomEntry")
    private Integer uomEntry;
    @Basic
    @Column(name = "UomEntry2")
    private Integer uomEntry2;
    @Basic
    @Column(name = "UomCode")
    private String uomCode;
    @Basic
    @Column(name = "UomCode2")
    private String uomCode2;
    @Basic
    @Column(name = "FromWhsCod")
    private String fromWhsCod;
    @Basic
    @Column(name = "NeedQty")
    private String needQty;
    @Basic
    @Column(name = "PartRetire")
    private String partRetire;
    @Basic
    @Column(name = "RetireQty")
    private BigDecimal retireQty;
    @Basic
    @Column(name = "RetireAPC")
    private BigDecimal retireApc;
    @Basic
    @Column(name = "RetirAPCFC")
    private BigDecimal retirApcfc;
    @Basic
    @Column(name = "RetirAPCSC")
    private BigDecimal retirApcsc;
    @Basic
    @Column(name = "InvQty")
    private BigDecimal invQty;
    @Basic
    @Column(name = "OpenInvQty")
    private BigDecimal openInvQty;
    @Basic
    @Column(name = "EnSetCost")
    private String enSetCost;
    @Basic
    @Column(name = "RetCost")
    private BigDecimal retCost;
    @Basic
    @Column(name = "Incoterms")
    private Integer incoterms;
    @Basic
    @Column(name = "TransMod")
    private Integer transMod;
    @Basic
    @Column(name = "LineVendor")
    private String lineVendor;
    @Basic
    @Column(name = "DistribIS")
    private String distribIs;
    @Basic
    @Column(name = "ISDistrb")
    private BigDecimal isDistrb;
    @Basic
    @Column(name = "ISDistrbFC")
    private BigDecimal isDistrbFc;
    @Basic
    @Column(name = "ISDistrbSC")
    private BigDecimal isDistrbSc;
    @Basic
    @Column(name = "IsByPrdct")
    private String isByPrdct;
    @Basic
    @Column(name = "ItemType")
    private Integer itemType;
    @Basic
    @Column(name = "PriceEdit")
    private String priceEdit;
    @Basic
    @Column(name = "PrntLnNum")
    private Integer prntLnNum;
    @Basic
    @Column(name = "LinePoPrss")
    private String linePoPrss;
    @Basic
    @Column(name = "FreeChrgBP")
    private String freeChrgBp;
    @Basic
    @Column(name = "TaxRelev")
    private String taxRelev;
    @Basic
    @Column(name = "LegalText")
    private String legalText;
    @Basic
    @Column(name = "ThirdParty")
    private String thirdParty;
    @Basic
    @Column(name = "LicTradNum")
    private String licTradNum;
    @Basic
    @Column(name = "InvQtyOnly")
    private String invQtyOnly;
    @Basic
    @Column(name = "UnencReasn")
    private Integer unencReasn;
    @Basic
    @Column(name = "ShipFromCo")
    private String shipFromCo;
    @Basic
    @Column(name = "ShipFromDe")
    private String shipFromDe;
    @Basic
    @Column(name = "FisrtBin")
    private String fisrtBin;
    @Basic
    @Column(name = "AllocBinC")
    private String allocBinC;
    @Basic
    @Column(name = "ExpType")
    private String expType;
    @Basic
    @Column(name = "ExpUUID")
    private String expUuid;
    @Basic
    @Column(name = "ExpOpType")
    private String expOpType;
    @Basic
    @Column(name = "DIOTNat")
    private String diotNat;
    @Basic
    @Column(name = "MYFtype")
    private String myFtype;
    @Basic
    @Column(name = "ItmTaxType")
    private String itmTaxType;
    @Basic
    @Column(name = "SacEntry")
    private Integer sacEntry;
    @Basic
    @Column(name = "U_RDCODE")
    private String uRdcode;
    @Basic
    @Column(name = "U_Tenkythuat")
    private String uTenkythuat;
    @Basic
    @Column(name = "U_QCCode")
    private Integer uQcCode;
    @Basic
    @Column(name = "U_QCLine")
    private Integer uQcLine;
    @Basic
    @Column(name = "U_CKNB")
    private String uCknb;
    @Basic
    @Column(name = "U_KM")
    private String uKm;
    @Basic
    @Column(name = "U_SendToHO")
    private Short uSendToHo;
    @Basic
    @Column(name = "U_DVTthuc")
    private String uDvTthuc;

    public Integer getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(Integer docEntry) {
        this.docEntry = docEntry;
    }

    public Integer getLineNum() {
        return lineNum;
    }

    public void setLineNum(Integer lineNum) {
        this.lineNum = lineNum;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public Integer getTrgetEntry() {
        return trgetEntry;
    }

    public void setTrgetEntry(Integer trgetEntry) {
        this.trgetEntry = trgetEntry;
    }

    public String getBaseRef() {
        return baseRef;
    }

    public void setBaseRef(String baseRef) {
        this.baseRef = baseRef;
    }

    public Integer getBaseType() {
        return baseType;
    }

    public void setBaseType(Integer baseType) {
        this.baseType = baseType;
    }

    public Integer getBaseEntry() {
        return baseEntry;
    }

    public void setBaseEntry(Integer baseEntry) {
        this.baseEntry = baseEntry;
    }

    public Integer getBaseLine() {
        return baseLine;
    }

    public void setBaseLine(Integer baseLine) {
        this.baseLine = baseLine;
    }

    public String getLineStatus() {
        return lineStatus;
    }

    public void setLineStatus(String lineStatus) {
        this.lineStatus = lineStatus;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDscription() {
        return dscription;
    }

    public void setDscription(String dscription) {
        this.dscription = dscription;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public Double getOpenQty() {
        return openQty;
    }

    public void setOpenQty(Double openQty) {
        this.openQty = openQty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getDiscPrcnt() {
        return discPrcnt;
    }

    public void setDiscPrcnt(BigDecimal discPrcnt) {
        this.discPrcnt = discPrcnt;
    }

    public BigDecimal getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(BigDecimal lineTotal) {
        this.lineTotal = lineTotal;
    }

    public BigDecimal getTotalFrgn() {
        return totalFrgn;
    }

    public void setTotalFrgn(BigDecimal totalFrgn) {
        this.totalFrgn = totalFrgn;
    }

    public BigDecimal getOpenSum() {
        return openSum;
    }

    public void setOpenSum(BigDecimal openSum) {
        this.openSum = openSum;
    }

    public BigDecimal getOpenSumFc() {
        return openSumFc;
    }

    public void setOpenSumFc(BigDecimal openSumFc) {
        this.openSumFc = openSumFc;
    }

    public String getVendorNum() {
        return vendorNum;
    }

    public void setVendorNum(String vendorNum) {
        this.vendorNum = vendorNum;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getWhsCode() {
        return whsCode;
    }

    public void setWhsCode(String whsCode) {
        this.whsCode = whsCode;
    }

    public Integer getSlpCode() {
        return slpCode;
    }

    public void setSlpCode(Integer slpCode) {
        this.slpCode = slpCode;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public String getTreeType() {
        return treeType;
    }

    public void setTreeType(String treeType) {
        this.treeType = treeType;
    }

    public String getAcctCode() {
        return acctCode;
    }

    public void setAcctCode(String acctCode) {
        this.acctCode = acctCode;
    }

    public String getTaxStatus() {
        return taxStatus;
    }

    public void setTaxStatus(String taxStatus) {
        this.taxStatus = taxStatus;
    }

    public BigDecimal getGrossBuyPr() {
        return grossBuyPr;
    }

    public void setGrossBuyPr(BigDecimal grossBuyPr) {
        this.grossBuyPr = grossBuyPr;
    }

    public BigDecimal getPriceBefDi() {
        return priceBefDi;
    }

    public void setPriceBefDi(BigDecimal priceBefDi) {
        this.priceBefDi = priceBefDi;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public Integer getFlags() {
        return flags;
    }

    public void setFlags(Integer flags) {
        this.flags = flags;
    }

    public BigDecimal getOpenCreQty() {
        return openCreQty;
    }

    public void setOpenCreQty(BigDecimal openCreQty) {
        this.openCreQty = openCreQty;
    }

    public String getUseBaseUn() {
        return useBaseUn;
    }

    public void setUseBaseUn(String useBaseUn) {
        this.useBaseUn = useBaseUn;
    }

    public String getSubCatNum() {
        return subCatNum;
    }

    public void setSubCatNum(String subCatNum) {
        this.subCatNum = subCatNum;
    }

    public String getBaseCard() {
        return baseCard;
    }

    public void setBaseCard(String baseCard) {
        this.baseCard = baseCard;
    }

    public BigDecimal getTotalSumSy() {
        return totalSumSy;
    }

    public void setTotalSumSy(BigDecimal totalSumSy) {
        this.totalSumSy = totalSumSy;
    }

    public BigDecimal getOpenSumSys() {
        return openSumSys;
    }

    public void setOpenSumSys(BigDecimal openSumSys) {
        this.openSumSys = openSumSys;
    }

    public String getInvntSttus() {
        return invntSttus;
    }

    public void setInvntSttus(String invntSttus) {
        this.invntSttus = invntSttus;
    }

    public String getOcrCode() {
        return ocrCode;
    }

    public void setOcrCode(String ocrCode) {
        this.ocrCode = ocrCode;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getCodeBars() {
        return codeBars;
    }

    public void setCodeBars(String codeBars) {
        this.codeBars = codeBars;
    }

    public BigDecimal getVatPrcnt() {
        return vatPrcnt;
    }

    public void setVatPrcnt(BigDecimal vatPrcnt) {
        this.vatPrcnt = vatPrcnt;
    }

    public String getVatGroup() {
        return vatGroup;
    }

    public void setVatGroup(String vatGroup) {
        this.vatGroup = vatGroup;
    }

    public BigDecimal getPriceAfVat() {
        return priceAfVat;
    }

    public void setPriceAfVat(BigDecimal priceAfVat) {
        this.priceAfVat = priceAfVat;
    }

    public BigDecimal getHeight1() {
        return height1;
    }

    public void setHeight1(BigDecimal height1) {
        this.height1 = height1;
    }

    public Short getHght1Unit() {
        return hght1Unit;
    }

    public void setHght1Unit(Short hght1Unit) {
        this.hght1Unit = hght1Unit;
    }

    public BigDecimal getHeight2() {
        return height2;
    }

    public void setHeight2(BigDecimal height2) {
        this.height2 = height2;
    }

    public Short getHght2Unit() {
        return hght2Unit;
    }

    public void setHght2Unit(Short hght2Unit) {
        this.hght2Unit = hght2Unit;
    }

    public BigDecimal getWidth1() {
        return width1;
    }

    public void setWidth1(BigDecimal width1) {
        this.width1 = width1;
    }

    public Short getWdth1Unit() {
        return wdth1Unit;
    }

    public void setWdth1Unit(Short wdth1Unit) {
        this.wdth1Unit = wdth1Unit;
    }

    public BigDecimal getWidth2() {
        return width2;
    }

    public void setWidth2(BigDecimal width2) {
        this.width2 = width2;
    }

    public Short getWdth2Unit() {
        return wdth2Unit;
    }

    public void setWdth2Unit(Short wdth2Unit) {
        this.wdth2Unit = wdth2Unit;
    }

    public BigDecimal getLength1() {
        return length1;
    }

    public void setLength1(BigDecimal length1) {
        this.length1 = length1;
    }

    public Short getLen1Unit() {
        return len1Unit;
    }

    public void setLen1Unit(Short len1Unit) {
        this.len1Unit = len1Unit;
    }

    public BigDecimal getLength2() {
        return length2;
    }

    public void setLength2(BigDecimal length2) {
        this.length2 = length2;
    }

    public Short getLen2Unit() {
        return len2Unit;
    }

    public void setLen2Unit(Short len2Unit) {
        this.len2Unit = len2Unit;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public Short getVolUnit() {
        return volUnit;
    }

    public void setVolUnit(Short volUnit) {
        this.volUnit = volUnit;
    }

    public BigDecimal getWeight1() {
        return weight1;
    }

    public void setWeight1(BigDecimal weight1) {
        this.weight1 = weight1;
    }

    public Short getWght1Unit() {
        return wght1Unit;
    }

    public void setWght1Unit(Short wght1Unit) {
        this.wght1Unit = wght1Unit;
    }

    public BigDecimal getWeight2() {
        return weight2;
    }

    public void setWeight2(BigDecimal weight2) {
        this.weight2 = weight2;
    }

    public Short getWght2Unit() {
        return wght2Unit;
    }

    public void setWght2Unit(Short wght2Unit) {
        this.wght2Unit = wght2Unit;
    }

    public BigDecimal getFactor1() {
        return factor1;
    }

    public void setFactor1(BigDecimal factor1) {
        this.factor1 = factor1;
    }

    public BigDecimal getFactor2() {
        return factor2;
    }

    public void setFactor2(BigDecimal factor2) {
        this.factor2 = factor2;
    }

    public BigDecimal getFactor3() {
        return factor3;
    }

    public void setFactor3(BigDecimal factor3) {
        this.factor3 = factor3;
    }

    public BigDecimal getFactor4() {
        return factor4;
    }

    public void setFactor4(BigDecimal factor4) {
        this.factor4 = factor4;
    }

    public BigDecimal getPackQty() {
        return packQty;
    }

    public void setPackQty(BigDecimal packQty) {
        this.packQty = packQty;
    }

    public String getUpdInvntry() {
        return updInvntry;
    }

    public void setUpdInvntry(String updInvntry) {
        this.updInvntry = updInvntry;
    }

    public Integer getBaseDocNum() {
        return baseDocNum;
    }

    public void setBaseDocNum(Integer baseDocNum) {
        this.baseDocNum = baseDocNum;
    }

    public String getBaseAtCard() {
        return baseAtCard;
    }

    public void setBaseAtCard(String baseAtCard) {
        this.baseAtCard = baseAtCard;
    }

    public String getSww() {
        return sww;
    }

    public void setSww(String sww) {
        this.sww = sww;
    }

    public BigDecimal getVatSum() {
        return vatSum;
    }

    public void setVatSum(BigDecimal vatSum) {
        this.vatSum = vatSum;
    }

    public BigDecimal getVatSumFrgn() {
        return vatSumFrgn;
    }

    public void setVatSumFrgn(BigDecimal vatSumFrgn) {
        this.vatSumFrgn = vatSumFrgn;
    }

    public BigDecimal getVatSumSy() {
        return vatSumSy;
    }

    public void setVatSumSy(BigDecimal vatSumSy) {
        this.vatSumSy = vatSumSy;
    }

    public Integer getFinncPriod() {
        return finncPriod;
    }

    public void setFinncPriod(Integer finncPriod) {
        this.finncPriod = finncPriod;
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

    public String getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(String blockNum) {
        this.blockNum = blockNum;
    }

    public String getImportLog() {
        return importLog;
    }

    public void setImportLog(String importLog) {
        this.importLog = importLog;
    }

    public BigDecimal getDedVatSum() {
        return dedVatSum;
    }

    public void setDedVatSum(BigDecimal dedVatSum) {
        this.dedVatSum = dedVatSum;
    }

    public BigDecimal getDedVatSumF() {
        return dedVatSumF;
    }

    public void setDedVatSumF(BigDecimal dedVatSumF) {
        this.dedVatSumF = dedVatSumF;
    }

    public BigDecimal getDedVatSumS() {
        return dedVatSumS;
    }

    public void setDedVatSumS(BigDecimal dedVatSumS) {
        this.dedVatSumS = dedVatSumS;
    }

    public String getIsAqcuistn() {
        return isAqcuistn;
    }

    public void setIsAqcuistn(String isAqcuistn) {
        this.isAqcuistn = isAqcuistn;
    }

    public BigDecimal getDistribSum() {
        return distribSum;
    }

    public void setDistribSum(BigDecimal distribSum) {
        this.distribSum = distribSum;
    }

    public BigDecimal getDstrbSumFc() {
        return dstrbSumFc;
    }

    public void setDstrbSumFc(BigDecimal dstrbSumFc) {
        this.dstrbSumFc = dstrbSumFc;
    }

    public BigDecimal getDstrbSumSc() {
        return dstrbSumSc;
    }

    public void setDstrbSumSc(BigDecimal dstrbSumSc) {
        this.dstrbSumSc = dstrbSumSc;
    }

    public BigDecimal getGrssProfit() {
        return grssProfit;
    }

    public void setGrssProfit(BigDecimal grssProfit) {
        this.grssProfit = grssProfit;
    }

    public BigDecimal getGrssProfSc() {
        return grssProfSc;
    }

    public void setGrssProfSc(BigDecimal grssProfSc) {
        this.grssProfSc = grssProfSc;
    }

    public BigDecimal getGrssProfFc() {
        return grssProfFc;
    }

    public void setGrssProfFc(BigDecimal grssProfFc) {
        this.grssProfFc = grssProfFc;
    }

    public Integer getVisOrder() {
        return visOrder;
    }

    public void setVisOrder(Integer visOrder) {
        this.visOrder = visOrder;
    }

    public BigDecimal getInmPrice() {
        return inmPrice;
    }

    public void setInmPrice(BigDecimal inmPrice) {
        this.inmPrice = inmPrice;
    }

    public Integer getPoTrgNum() {
        return poTrgNum;
    }

    public void setPoTrgNum(Integer poTrgNum) {
        this.poTrgNum = poTrgNum;
    }

    public String getPoTrgEntry() {
        return poTrgEntry;
    }

    public void setPoTrgEntry(String poTrgEntry) {
        this.poTrgEntry = poTrgEntry;
    }

    public String getDropShip() {
        return dropShip;
    }

    public void setDropShip(String dropShip) {
        this.dropShip = dropShip;
    }

    public Integer getPoLineNum() {
        return poLineNum;
    }

    public void setPoLineNum(Integer poLineNum) {
        this.poLineNum = poLineNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public String getOrigItem() {
        return origItem;
    }

    public void setOrigItem(String origItem) {
        this.origItem = origItem;
    }

    public String getBackOrdr() {
        return backOrdr;
    }

    public void setBackOrdr(String backOrdr) {
        this.backOrdr = backOrdr;
    }

    public String getFreeTxt() {
        return freeTxt;
    }

    public void setFreeTxt(String freeTxt) {
        this.freeTxt = freeTxt;
    }

    public String getPickStatus() {
        return pickStatus;
    }

    public void setPickStatus(String pickStatus) {
        this.pickStatus = pickStatus;
    }

    public BigDecimal getPickOty() {
        return pickOty;
    }

    public void setPickOty(BigDecimal pickOty) {
        this.pickOty = pickOty;
    }

    public Integer getPickIdNo() {
        return pickIdNo;
    }

    public void setPickIdNo(Integer pickIdNo) {
        this.pickIdNo = pickIdNo;
    }

    public Short getTrnsCode() {
        return trnsCode;
    }

    public void setTrnsCode(Short trnsCode) {
        this.trnsCode = trnsCode;
    }

    public BigDecimal getVatAppld() {
        return vatAppld;
    }

    public void setVatAppld(BigDecimal vatAppld) {
        this.vatAppld = vatAppld;
    }

    public BigDecimal getVatAppldFc() {
        return vatAppldFc;
    }

    public void setVatAppldFc(BigDecimal vatAppldFc) {
        this.vatAppldFc = vatAppldFc;
    }

    public BigDecimal getVatAppldSc() {
        return vatAppldSc;
    }

    public void setVatAppldSc(BigDecimal vatAppldSc) {
        this.vatAppldSc = vatAppldSc;
    }

    public BigDecimal getBaseQty() {
        return baseQty;
    }

    public void setBaseQty(BigDecimal baseQty) {
        this.baseQty = baseQty;
    }

    public BigDecimal getBaseOpnQty() {
        return baseOpnQty;
    }

    public void setBaseOpnQty(BigDecimal baseOpnQty) {
        this.baseOpnQty = baseOpnQty;
    }

    public BigDecimal getVatDscntPr() {
        return vatDscntPr;
    }

    public void setVatDscntPr(BigDecimal vatDscntPr) {
        this.vatDscntPr = vatDscntPr;
    }

    public String getWtLiable() {
        return wtLiable;
    }

    public void setWtLiable(String wtLiable) {
        this.wtLiable = wtLiable;
    }

    public String getDeferrTax() {
        return deferrTax;
    }

    public void setDeferrTax(String deferrTax) {
        this.deferrTax = deferrTax;
    }

    public BigDecimal getEquVatPer() {
        return equVatPer;
    }

    public void setEquVatPer(BigDecimal equVatPer) {
        this.equVatPer = equVatPer;
    }

    public BigDecimal getEquVatSum() {
        return equVatSum;
    }

    public void setEquVatSum(BigDecimal equVatSum) {
        this.equVatSum = equVatSum;
    }

    public BigDecimal getEquVatSumF() {
        return equVatSumF;
    }

    public void setEquVatSumF(BigDecimal equVatSumF) {
        this.equVatSumF = equVatSumF;
    }

    public BigDecimal getEquVatSumS() {
        return equVatSumS;
    }

    public void setEquVatSumS(BigDecimal equVatSumS) {
        this.equVatSumS = equVatSumS;
    }

    public BigDecimal getLineVat() {
        return lineVat;
    }

    public void setLineVat(BigDecimal lineVat) {
        this.lineVat = lineVat;
    }

    public BigDecimal getLineVatlF() {
        return lineVatlF;
    }

    public void setLineVatlF(BigDecimal lineVatlF) {
        this.lineVatlF = lineVatlF;
    }

    public BigDecimal getLineVatS() {
        return lineVatS;
    }

    public void setLineVatS(BigDecimal lineVatS) {
        this.lineVatS = lineVatS;
    }

    public String getUnitMsr() {
        return unitMsr;
    }

    public void setUnitMsr(String unitMsr) {
        this.unitMsr = unitMsr;
    }

    public BigDecimal getNumPerMsr() {
        return numPerMsr;
    }

    public void setNumPerMsr(BigDecimal numPerMsr) {
        this.numPerMsr = numPerMsr;
    }

    public String getCeecFlag() {
        return ceecFlag;
    }

    public void setCeecFlag(String ceecFlag) {
        this.ceecFlag = ceecFlag;
    }

    public BigDecimal getToStock() {
        return toStock;
    }

    public void setToStock(BigDecimal toStock) {
        this.toStock = toStock;
    }

    public BigDecimal getToDiff() {
        return toDiff;
    }

    public void setToDiff(BigDecimal toDiff) {
        this.toDiff = toDiff;
    }

    public BigDecimal getExciseAmt() {
        return exciseAmt;
    }

    public void setExciseAmt(BigDecimal exciseAmt) {
        this.exciseAmt = exciseAmt;
    }

    public BigDecimal getTaxPerUnit() {
        return taxPerUnit;
    }

    public void setTaxPerUnit(BigDecimal taxPerUnit) {
        this.taxPerUnit = taxPerUnit;
    }

    public BigDecimal getTotInclTax() {
        return totInclTax;
    }

    public void setTotInclTax(BigDecimal totInclTax) {
        this.totInclTax = totInclTax;
    }

    public String getCountryOrg() {
        return countryOrg;
    }

    public void setCountryOrg(String countryOrg) {
        this.countryOrg = countryOrg;
    }

    public BigDecimal getStckDstSum() {
        return stckDstSum;
    }

    public void setStckDstSum(BigDecimal stckDstSum) {
        this.stckDstSum = stckDstSum;
    }

    public BigDecimal getReleasQtty() {
        return releasQtty;
    }

    public void setReleasQtty(BigDecimal releasQtty) {
        this.releasQtty = releasQtty;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(Integer ownerCode) {
        this.ownerCode = ownerCode;
    }

    public BigDecimal getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(BigDecimal stockPrice) {
        this.stockPrice = stockPrice;
    }

    public String getConsumeFct() {
        return consumeFct;
    }

    public void setConsumeFct(String consumeFct) {
        this.consumeFct = consumeFct;
    }

    public BigDecimal getLstByDsSum() {
        return lstByDsSum;
    }

    public void setLstByDsSum(BigDecimal lstByDsSum) {
        this.lstByDsSum = lstByDsSum;
    }

    public BigDecimal getStckInmPr() {
        return stckInmPr;
    }

    public void setStckInmPr(BigDecimal stckInmPr) {
        this.stckInmPr = stckInmPr;
    }

    public BigDecimal getLstBinmPr() {
        return lstBinmPr;
    }

    public void setLstBinmPr(BigDecimal lstBinmPr) {
        this.lstBinmPr = lstBinmPr;
    }

    public BigDecimal getStckDstFc() {
        return stckDstFc;
    }

    public void setStckDstFc(BigDecimal stckDstFc) {
        this.stckDstFc = stckDstFc;
    }

    public BigDecimal getStckDstSc() {
        return stckDstSc;
    }

    public void setStckDstSc(BigDecimal stckDstSc) {
        this.stckDstSc = stckDstSc;
    }

    public BigDecimal getLstByDsFc() {
        return lstByDsFc;
    }

    public void setLstByDsFc(BigDecimal lstByDsFc) {
        this.lstByDsFc = lstByDsFc;
    }

    public BigDecimal getLstByDsSc() {
        return lstByDsSc;
    }

    public void setLstByDsSc(BigDecimal lstByDsSc) {
        this.lstByDsSc = lstByDsSc;
    }

    public BigDecimal getStockSum() {
        return stockSum;
    }

    public void setStockSum(BigDecimal stockSum) {
        this.stockSum = stockSum;
    }

    public BigDecimal getStockSumFc() {
        return stockSumFc;
    }

    public void setStockSumFc(BigDecimal stockSumFc) {
        this.stockSumFc = stockSumFc;
    }

    public BigDecimal getStockSumSc() {
        return stockSumSc;
    }

    public void setStockSumSc(BigDecimal stockSumSc) {
        this.stockSumSc = stockSumSc;
    }

    public BigDecimal getStckSumApp() {
        return stckSumApp;
    }

    public void setStckSumApp(BigDecimal stckSumApp) {
        this.stckSumApp = stckSumApp;
    }

    public BigDecimal getStckAppFc() {
        return stckAppFc;
    }

    public void setStckAppFc(BigDecimal stckAppFc) {
        this.stckAppFc = stckAppFc;
    }

    public BigDecimal getStckAppSc() {
        return stckAppSc;
    }

    public void setStckAppSc(BigDecimal stckAppSc) {
        this.stckAppSc = stckAppSc;
    }

    public String getShipToCode() {
        return shipToCode;
    }

    public void setShipToCode(String shipToCode) {
        this.shipToCode = shipToCode;
    }

    public String getShipToDesc() {
        return shipToDesc;
    }

    public void setShipToDesc(String shipToDesc) {
        this.shipToDesc = shipToDesc;
    }

    public BigDecimal getStckAppD() {
        return stckAppD;
    }

    public void setStckAppD(BigDecimal stckAppD) {
        this.stckAppD = stckAppD;
    }

    public BigDecimal getStckAppDfc() {
        return stckAppDfc;
    }

    public void setStckAppDfc(BigDecimal stckAppDfc) {
        this.stckAppDfc = stckAppDfc;
    }

    public BigDecimal getStckAppDsc() {
        return stckAppDsc;
    }

    public void setStckAppDsc(BigDecimal stckAppDsc) {
        this.stckAppDsc = stckAppDsc;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getgTotal() {
        return gTotal;
    }

    public void setgTotal(BigDecimal gTotal) {
        this.gTotal = gTotal;
    }

    public BigDecimal getgTotalFc() {
        return gTotalFc;
    }

    public void setgTotalFc(BigDecimal gTotalFc) {
        this.gTotalFc = gTotalFc;
    }

    public BigDecimal getgTotalSc() {
        return gTotalSc;
    }

    public void setgTotalSc(BigDecimal gTotalSc) {
        this.gTotalSc = gTotalSc;
    }

    public String getDistribExp() {
        return distribExp;
    }

    public void setDistribExp(String distribExp) {
        this.distribExp = distribExp;
    }

    public String getDescOw() {
        return descOw;
    }

    public void setDescOw(String descOw) {
        this.descOw = descOw;
    }

    public String getDetailsOw() {
        return detailsOw;
    }

    public void setDetailsOw(String detailsOw) {
        this.detailsOw = detailsOw;
    }

    public Short getGrossBase() {
        return grossBase;
    }

    public void setGrossBase(Short grossBase) {
        this.grossBase = grossBase;
    }

    public BigDecimal getVatWoDpm() {
        return vatWoDpm;
    }

    public void setVatWoDpm(BigDecimal vatWoDpm) {
        this.vatWoDpm = vatWoDpm;
    }

    public BigDecimal getVatWoDpmFc() {
        return vatWoDpmFc;
    }

    public void setVatWoDpmFc(BigDecimal vatWoDpmFc) {
        this.vatWoDpmFc = vatWoDpmFc;
    }

    public BigDecimal getVatWoDpmSc() {
        return vatWoDpmSc;
    }

    public void setVatWoDpmSc(BigDecimal vatWoDpmSc) {
        this.vatWoDpmSc = vatWoDpmSc;
    }

    public String getCfopCode() {
        return cfopCode;
    }

    public void setCfopCode(String cfopCode) {
        this.cfopCode = cfopCode;
    }

    public String getCstCode() {
        return cstCode;
    }

    public void setCstCode(String cstCode) {
        this.cstCode = cstCode;
    }

    public Integer getUsage() {
        return usage;
    }

    public void setUsage(Integer usage) {
        this.usage = usage;
    }

    public String getTaxOnly() {
        return taxOnly;
    }

    public void setTaxOnly(String taxOnly) {
        this.taxOnly = taxOnly;
    }

    public String getWtCalced() {
        return wtCalced;
    }

    public void setWtCalced(String wtCalced) {
        this.wtCalced = wtCalced;
    }

    public BigDecimal getQtyToShip() {
        return qtyToShip;
    }

    public void setQtyToShip(BigDecimal qtyToShip) {
        this.qtyToShip = qtyToShip;
    }

    public BigDecimal getDelivrdQty() {
        return delivrdQty;
    }

    public void setDelivrdQty(BigDecimal delivrdQty) {
        this.delivrdQty = delivrdQty;
    }

    public BigDecimal getOrderedQty() {
        return orderedQty;
    }

    public void setOrderedQty(BigDecimal orderedQty) {
        this.orderedQty = orderedQty;
    }

    public String getCogsOcrCod() {
        return cogsOcrCod;
    }

    public void setCogsOcrCod(String cogsOcrCod) {
        this.cogsOcrCod = cogsOcrCod;
    }

    public Integer getCiOppLineN() {
        return ciOppLineN;
    }

    public void setCiOppLineN(Integer ciOppLineN) {
        this.ciOppLineN = ciOppLineN;
    }

    public String getCogsAcct() {
        return cogsAcct;
    }

    public void setCogsAcct(String cogsAcct) {
        this.cogsAcct = cogsAcct;
    }

    public String getChgAsmBoMw() {
        return chgAsmBoMw;
    }

    public void setChgAsmBoMw(String chgAsmBoMw) {
        this.chgAsmBoMw = chgAsmBoMw;
    }

    public Date getActDelDate() {
        return actDelDate;
    }

    public void setActDelDate(Date actDelDate) {
        this.actDelDate = actDelDate;
    }

    public String getOcrCode2() {
        return ocrCode2;
    }

    public void setOcrCode2(String ocrCode2) {
        this.ocrCode2 = ocrCode2;
    }

    public String getOcrCode3() {
        return ocrCode3;
    }

    public void setOcrCode3(String ocrCode3) {
        this.ocrCode3 = ocrCode3;
    }

    public String getOcrCode4() {
        return ocrCode4;
    }

    public void setOcrCode4(String ocrCode4) {
        this.ocrCode4 = ocrCode4;
    }

    public String getOcrCode5() {
        return ocrCode5;
    }

    public void setOcrCode5(String ocrCode5) {
        this.ocrCode5 = ocrCode5;
    }

    public BigDecimal getTaxDistSum() {
        return taxDistSum;
    }

    public void setTaxDistSum(BigDecimal taxDistSum) {
        this.taxDistSum = taxDistSum;
    }

    public BigDecimal getTaxDistSfc() {
        return taxDistSfc;
    }

    public void setTaxDistSfc(BigDecimal taxDistSfc) {
        this.taxDistSfc = taxDistSfc;
    }

    public BigDecimal getTaxDistSsc() {
        return taxDistSsc;
    }

    public void setTaxDistSsc(BigDecimal taxDistSsc) {
        this.taxDistSsc = taxDistSsc;
    }

    public String getPostTax() {
        return postTax;
    }

    public void setPostTax(String postTax) {
        this.postTax = postTax;
    }

    public String getExcisable() {
        return excisable;
    }

    public void setExcisable(String excisable) {
        this.excisable = excisable;
    }

    public BigDecimal getAssblValue() {
        return assblValue;
    }

    public void setAssblValue(BigDecimal assblValue) {
        this.assblValue = assblValue;
    }

    public Integer getRg23APart1() {
        return rg23APart1;
    }

    public void setRg23APart1(Integer rg23APart1) {
        this.rg23APart1 = rg23APart1;
    }

    public Integer getRg23APart2() {
        return rg23APart2;
    }

    public void setRg23APart2(Integer rg23APart2) {
        this.rg23APart2 = rg23APart2;
    }

    public Integer getRg23CPart1() {
        return rg23CPart1;
    }

    public void setRg23CPart1(Integer rg23CPart1) {
        this.rg23CPart1 = rg23CPart1;
    }

    public Integer getRg23CPart2() {
        return rg23CPart2;
    }

    public void setRg23CPart2(Integer rg23CPart2) {
        this.rg23CPart2 = rg23CPart2;
    }

    public String getCogsOcrCo2() {
        return cogsOcrCo2;
    }

    public void setCogsOcrCo2(String cogsOcrCo2) {
        this.cogsOcrCo2 = cogsOcrCo2;
    }

    public String getCogsOcrCo3() {
        return cogsOcrCo3;
    }

    public void setCogsOcrCo3(String cogsOcrCo3) {
        this.cogsOcrCo3 = cogsOcrCo3;
    }

    public String getCogsOcrCo4() {
        return cogsOcrCo4;
    }

    public void setCogsOcrCo4(String cogsOcrCo4) {
        this.cogsOcrCo4 = cogsOcrCo4;
    }

    public String getCogsOcrCo5() {
        return cogsOcrCo5;
    }

    public void setCogsOcrCo5(String cogsOcrCo5) {
        this.cogsOcrCo5 = cogsOcrCo5;
    }

    public String getLnExcised() {
        return lnExcised;
    }

    public void setLnExcised(String lnExcised) {
        this.lnExcised = lnExcised;
    }

    public Integer getLocCode() {
        return locCode;
    }

    public void setLocCode(Integer locCode) {
        this.locCode = locCode;
    }

    public BigDecimal getStockValue() {
        return stockValue;
    }

    public void setStockValue(BigDecimal stockValue) {
        this.stockValue = stockValue;
    }

    public BigDecimal getGpTtlBasPr() {
        return gpTtlBasPr;
    }

    public void setGpTtlBasPr(BigDecimal gpTtlBasPr) {
        this.gpTtlBasPr = gpTtlBasPr;
    }

    public String getUnitMsr2() {
        return unitMsr2;
    }

    public void setUnitMsr2(String unitMsr2) {
        this.unitMsr2 = unitMsr2;
    }

    public BigDecimal getNumPerMsr2() {
        return numPerMsr2;
    }

    public void setNumPerMsr2(BigDecimal numPerMsr2) {
        this.numPerMsr2 = numPerMsr2;
    }

    public String getSpecPrice() {
        return specPrice;
    }

    public void setSpecPrice(String specPrice) {
        this.specPrice = specPrice;
    }

    public String getCsTfIpi() {
        return csTfIpi;
    }

    public void setCsTfIpi(String csTfIpi) {
        this.csTfIpi = csTfIpi;
    }

    public String getCsTfPis() {
        return csTfPis;
    }

    public void setCsTfPis(String csTfPis) {
        this.csTfPis = csTfPis;
    }

    public String getCsTfCofins() {
        return csTfCofins;
    }

    public void setCsTfCofins(String csTfCofins) {
        this.csTfCofins = csTfCofins;
    }

    public String getExLineNo() {
        return exLineNo;
    }

    public void setExLineNo(String exLineNo) {
        this.exLineNo = exLineNo;
    }

    public String getIsSrvCall() {
        return isSrvCall;
    }

    public void setIsSrvCall(String isSrvCall) {
        this.isSrvCall = isSrvCall;
    }

    public BigDecimal getPqtReqQty() {
        return pqtReqQty;
    }

    public void setPqtReqQty(BigDecimal pqtReqQty) {
        this.pqtReqQty = pqtReqQty;
    }

    public Date getPqtReqDate() {
        return pqtReqDate;
    }

    public void setPqtReqDate(Date pqtReqDate) {
        this.pqtReqDate = pqtReqDate;
    }

    public Integer getPcDocType() {
        return pcDocType;
    }

    public void setPcDocType(Integer pcDocType) {
        this.pcDocType = pcDocType;
    }

    public BigDecimal getPcQuantity() {
        return pcQuantity;
    }

    public void setPcQuantity(BigDecimal pcQuantity) {
        this.pcQuantity = pcQuantity;
    }

    public String getLinManClsd() {
        return linManClsd;
    }

    public void setLinManClsd(String linManClsd) {
        this.linManClsd = linManClsd;
    }

    public String getVatGrpSrc() {
        return vatGrpSrc;
    }

    public void setVatGrpSrc(String vatGrpSrc) {
        this.vatGrpSrc = vatGrpSrc;
    }

    public String getNoInvtryMv() {
        return noInvtryMv;
    }

    public void setNoInvtryMv(String noInvtryMv) {
        this.noInvtryMv = noInvtryMv;
    }

    public Integer getActBaseEnt() {
        return actBaseEnt;
    }

    public void setActBaseEnt(Integer actBaseEnt) {
        this.actBaseEnt = actBaseEnt;
    }

    public Integer getActBaseLn() {
        return actBaseLn;
    }

    public void setActBaseLn(Integer actBaseLn) {
        this.actBaseLn = actBaseLn;
    }

    public Integer getActBaseNum() {
        return actBaseNum;
    }

    public void setActBaseNum(Integer actBaseNum) {
        this.actBaseNum = actBaseNum;
    }

    public BigDecimal getOpenRtnQty() {
        return openRtnQty;
    }

    public void setOpenRtnQty(BigDecimal openRtnQty) {
        this.openRtnQty = openRtnQty;
    }

    public Integer getAgrNo() {
        return agrNo;
    }

    public void setAgrNo(Integer agrNo) {
        this.agrNo = agrNo;
    }

    public Integer getAgrLnNum() {
        return agrLnNum;
    }

    public void setAgrLnNum(Integer agrLnNum) {
        this.agrLnNum = agrLnNum;
    }

    public String getCredOrigin() {
        return credOrigin;
    }

    public void setCredOrigin(String credOrigin) {
        this.credOrigin = credOrigin;
    }

    public BigDecimal getSurpluses() {
        return surpluses;
    }

    public void setSurpluses(BigDecimal surpluses) {
        this.surpluses = surpluses;
    }

    public BigDecimal getDefBreak() {
        return defBreak;
    }

    public void setDefBreak(BigDecimal defBreak) {
        this.defBreak = defBreak;
    }

    public BigDecimal getShortages() {
        return shortages;
    }

    public void setShortages(BigDecimal shortages) {
        this.shortages = shortages;
    }

    public Integer getUomEntry() {
        return uomEntry;
    }

    public void setUomEntry(Integer uomEntry) {
        this.uomEntry = uomEntry;
    }

    public Integer getUomEntry2() {
        return uomEntry2;
    }

    public void setUomEntry2(Integer uomEntry2) {
        this.uomEntry2 = uomEntry2;
    }

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    public String getUomCode2() {
        return uomCode2;
    }

    public void setUomCode2(String uomCode2) {
        this.uomCode2 = uomCode2;
    }

    public String getFromWhsCod() {
        return fromWhsCod;
    }

    public void setFromWhsCod(String fromWhsCod) {
        this.fromWhsCod = fromWhsCod;
    }

    public String getNeedQty() {
        return needQty;
    }

    public void setNeedQty(String needQty) {
        this.needQty = needQty;
    }

    public String getPartRetire() {
        return partRetire;
    }

    public void setPartRetire(String partRetire) {
        this.partRetire = partRetire;
    }

    public BigDecimal getRetireQty() {
        return retireQty;
    }

    public void setRetireQty(BigDecimal retireQty) {
        this.retireQty = retireQty;
    }

    public BigDecimal getRetireApc() {
        return retireApc;
    }

    public void setRetireApc(BigDecimal retireApc) {
        this.retireApc = retireApc;
    }

    public BigDecimal getRetirApcfc() {
        return retirApcfc;
    }

    public void setRetirApcfc(BigDecimal retirApcfc) {
        this.retirApcfc = retirApcfc;
    }

    public BigDecimal getRetirApcsc() {
        return retirApcsc;
    }

    public void setRetirApcsc(BigDecimal retirApcsc) {
        this.retirApcsc = retirApcsc;
    }

    public BigDecimal getInvQty() {
        return invQty;
    }

    public void setInvQty(BigDecimal invQty) {
        this.invQty = invQty;
    }

    public BigDecimal getOpenInvQty() {
        return openInvQty;
    }

    public void setOpenInvQty(BigDecimal openInvQty) {
        this.openInvQty = openInvQty;
    }

    public String getEnSetCost() {
        return enSetCost;
    }

    public void setEnSetCost(String enSetCost) {
        this.enSetCost = enSetCost;
    }

    public BigDecimal getRetCost() {
        return retCost;
    }

    public void setRetCost(BigDecimal retCost) {
        this.retCost = retCost;
    }

    public Integer getIncoterms() {
        return incoterms;
    }

    public void setIncoterms(Integer incoterms) {
        this.incoterms = incoterms;
    }

    public Integer getTransMod() {
        return transMod;
    }

    public void setTransMod(Integer transMod) {
        this.transMod = transMod;
    }

    public String getLineVendor() {
        return lineVendor;
    }

    public void setLineVendor(String lineVendor) {
        this.lineVendor = lineVendor;
    }

    public String getDistribIs() {
        return distribIs;
    }

    public void setDistribIs(String distribIs) {
        this.distribIs = distribIs;
    }

    public BigDecimal getIsDistrb() {
        return isDistrb;
    }

    public void setIsDistrb(BigDecimal isDistrb) {
        this.isDistrb = isDistrb;
    }

    public BigDecimal getIsDistrbFc() {
        return isDistrbFc;
    }

    public void setIsDistrbFc(BigDecimal isDistrbFc) {
        this.isDistrbFc = isDistrbFc;
    }

    public BigDecimal getIsDistrbSc() {
        return isDistrbSc;
    }

    public void setIsDistrbSc(BigDecimal isDistrbSc) {
        this.isDistrbSc = isDistrbSc;
    }

    public String getIsByPrdct() {
        return isByPrdct;
    }

    public void setIsByPrdct(String isByPrdct) {
        this.isByPrdct = isByPrdct;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public String getPriceEdit() {
        return priceEdit;
    }

    public void setPriceEdit(String priceEdit) {
        this.priceEdit = priceEdit;
    }

    public Integer getPrntLnNum() {
        return prntLnNum;
    }

    public void setPrntLnNum(Integer prntLnNum) {
        this.prntLnNum = prntLnNum;
    }

    public String getLinePoPrss() {
        return linePoPrss;
    }

    public void setLinePoPrss(String linePoPrss) {
        this.linePoPrss = linePoPrss;
    }

    public String getFreeChrgBp() {
        return freeChrgBp;
    }

    public void setFreeChrgBp(String freeChrgBp) {
        this.freeChrgBp = freeChrgBp;
    }

    public String getTaxRelev() {
        return taxRelev;
    }

    public void setTaxRelev(String taxRelev) {
        this.taxRelev = taxRelev;
    }

    public String getLegalText() {
        return legalText;
    }

    public void setLegalText(String legalText) {
        this.legalText = legalText;
    }

    public String getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(String thirdParty) {
        this.thirdParty = thirdParty;
    }

    public String getLicTradNum() {
        return licTradNum;
    }

    public void setLicTradNum(String licTradNum) {
        this.licTradNum = licTradNum;
    }

    public String getInvQtyOnly() {
        return invQtyOnly;
    }

    public void setInvQtyOnly(String invQtyOnly) {
        this.invQtyOnly = invQtyOnly;
    }

    public Integer getUnencReasn() {
        return unencReasn;
    }

    public void setUnencReasn(Integer unencReasn) {
        this.unencReasn = unencReasn;
    }

    public String getShipFromCo() {
        return shipFromCo;
    }

    public void setShipFromCo(String shipFromCo) {
        this.shipFromCo = shipFromCo;
    }

    public String getShipFromDe() {
        return shipFromDe;
    }

    public void setShipFromDe(String shipFromDe) {
        this.shipFromDe = shipFromDe;
    }

    public String getFisrtBin() {
        return fisrtBin;
    }

    public void setFisrtBin(String fisrtBin) {
        this.fisrtBin = fisrtBin;
    }

    public String getAllocBinC() {
        return allocBinC;
    }

    public void setAllocBinC(String allocBinC) {
        this.allocBinC = allocBinC;
    }

    public String getExpType() {
        return expType;
    }

    public void setExpType(String expType) {
        this.expType = expType;
    }

    public String getExpUuid() {
        return expUuid;
    }

    public void setExpUuid(String expUuid) {
        this.expUuid = expUuid;
    }

    public String getExpOpType() {
        return expOpType;
    }

    public void setExpOpType(String expOpType) {
        this.expOpType = expOpType;
    }

    public String getDiotNat() {
        return diotNat;
    }

    public void setDiotNat(String diotNat) {
        this.diotNat = diotNat;
    }

    public String getMyFtype() {
        return myFtype;
    }

    public void setMyFtype(String myFtype) {
        this.myFtype = myFtype;
    }

    public String getItmTaxType() {
        return itmTaxType;
    }

    public void setItmTaxType(String itmTaxType) {
        this.itmTaxType = itmTaxType;
    }

    public Integer getSacEntry() {
        return sacEntry;
    }

    public void setSacEntry(Integer sacEntry) {
        this.sacEntry = sacEntry;
    }

    public String getuRdcode() {
        return uRdcode;
    }

    public void setuRdcode(String uRdcode) {
        this.uRdcode = uRdcode;
    }

    public String getuTenkythuat() {
        return uTenkythuat;
    }

    public void setuTenkythuat(String uTenkythuat) {
        this.uTenkythuat = uTenkythuat;
    }

    public Integer getuQcCode() {
        return uQcCode;
    }

    public void setuQcCode(Integer uQcCode) {
        this.uQcCode = uQcCode;
    }

    public Integer getuQcLine() {
        return uQcLine;
    }

    public void setuQcLine(Integer uQcLine) {
        this.uQcLine = uQcLine;
    }

    public String getuCknb() {
        return uCknb;
    }

    public void setuCknb(String uCknb) {
        this.uCknb = uCknb;
    }

    public String getuKm() {
        return uKm;
    }

    public void setuKm(String uKm) {
        this.uKm = uKm;
    }

    public Short getuSendToHo() {
        return uSendToHo;
    }

    public void setuSendToHo(Short uSendToHo) {
        this.uSendToHo = uSendToHo;
    }

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

        Prq1Entity that = (Prq1Entity) o;

        if (docEntry != null ? !docEntry.equals(that.docEntry) : that.docEntry != null) return false;
        if (lineNum != null ? !lineNum.equals(that.lineNum) : that.lineNum != null) return false;
        if (targetType != null ? !targetType.equals(that.targetType) : that.targetType != null) return false;
        if (trgetEntry != null ? !trgetEntry.equals(that.trgetEntry) : that.trgetEntry != null) return false;
        if (baseRef != null ? !baseRef.equals(that.baseRef) : that.baseRef != null) return false;
        if (baseType != null ? !baseType.equals(that.baseType) : that.baseType != null) return false;
        if (baseEntry != null ? !baseEntry.equals(that.baseEntry) : that.baseEntry != null) return false;
        if (baseLine != null ? !baseLine.equals(that.baseLine) : that.baseLine != null) return false;
        if (lineStatus != null ? !lineStatus.equals(that.lineStatus) : that.lineStatus != null) return false;
        if (itemCode != null ? !itemCode.equals(that.itemCode) : that.itemCode != null) return false;
        if (dscription != null ? !dscription.equals(that.dscription) : that.dscription != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (shipDate != null ? !shipDate.equals(that.shipDate) : that.shipDate != null) return false;
        if (openQty != null ? !openQty.equals(that.openQty) : that.openQty != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) return false;
        if (rate != null ? !rate.equals(that.rate) : that.rate != null) return false;
        if (discPrcnt != null ? !discPrcnt.equals(that.discPrcnt) : that.discPrcnt != null) return false;
        if (lineTotal != null ? !lineTotal.equals(that.lineTotal) : that.lineTotal != null) return false;
        if (totalFrgn != null ? !totalFrgn.equals(that.totalFrgn) : that.totalFrgn != null) return false;
        if (openSum != null ? !openSum.equals(that.openSum) : that.openSum != null) return false;
        if (openSumFc != null ? !openSumFc.equals(that.openSumFc) : that.openSumFc != null) return false;
        if (vendorNum != null ? !vendorNum.equals(that.vendorNum) : that.vendorNum != null) return false;
        if (serialNum != null ? !serialNum.equals(that.serialNum) : that.serialNum != null) return false;
        if (whsCode != null ? !whsCode.equals(that.whsCode) : that.whsCode != null) return false;
        if (slpCode != null ? !slpCode.equals(that.slpCode) : that.slpCode != null) return false;
        if (commission != null ? !commission.equals(that.commission) : that.commission != null) return false;
        if (treeType != null ? !treeType.equals(that.treeType) : that.treeType != null) return false;
        if (acctCode != null ? !acctCode.equals(that.acctCode) : that.acctCode != null) return false;
        if (taxStatus != null ? !taxStatus.equals(that.taxStatus) : that.taxStatus != null) return false;
        if (grossBuyPr != null ? !grossBuyPr.equals(that.grossBuyPr) : that.grossBuyPr != null) return false;
        if (priceBefDi != null ? !priceBefDi.equals(that.priceBefDi) : that.priceBefDi != null) return false;
        if (docDate != null ? !docDate.equals(that.docDate) : that.docDate != null) return false;
        if (flags != null ? !flags.equals(that.flags) : that.flags != null) return false;
        if (openCreQty != null ? !openCreQty.equals(that.openCreQty) : that.openCreQty != null) return false;
        if (useBaseUn != null ? !useBaseUn.equals(that.useBaseUn) : that.useBaseUn != null) return false;
        if (subCatNum != null ? !subCatNum.equals(that.subCatNum) : that.subCatNum != null) return false;
        if (baseCard != null ? !baseCard.equals(that.baseCard) : that.baseCard != null) return false;
        if (totalSumSy != null ? !totalSumSy.equals(that.totalSumSy) : that.totalSumSy != null) return false;
        if (openSumSys != null ? !openSumSys.equals(that.openSumSys) : that.openSumSys != null) return false;
        if (invntSttus != null ? !invntSttus.equals(that.invntSttus) : that.invntSttus != null) return false;
        if (ocrCode != null ? !ocrCode.equals(that.ocrCode) : that.ocrCode != null) return false;
        if (project != null ? !project.equals(that.project) : that.project != null) return false;
        if (codeBars != null ? !codeBars.equals(that.codeBars) : that.codeBars != null) return false;
        if (vatPrcnt != null ? !vatPrcnt.equals(that.vatPrcnt) : that.vatPrcnt != null) return false;
        if (vatGroup != null ? !vatGroup.equals(that.vatGroup) : that.vatGroup != null) return false;
        if (priceAfVat != null ? !priceAfVat.equals(that.priceAfVat) : that.priceAfVat != null) return false;
        if (height1 != null ? !height1.equals(that.height1) : that.height1 != null) return false;
        if (hght1Unit != null ? !hght1Unit.equals(that.hght1Unit) : that.hght1Unit != null) return false;
        if (height2 != null ? !height2.equals(that.height2) : that.height2 != null) return false;
        if (hght2Unit != null ? !hght2Unit.equals(that.hght2Unit) : that.hght2Unit != null) return false;
        if (width1 != null ? !width1.equals(that.width1) : that.width1 != null) return false;
        if (wdth1Unit != null ? !wdth1Unit.equals(that.wdth1Unit) : that.wdth1Unit != null) return false;
        if (width2 != null ? !width2.equals(that.width2) : that.width2 != null) return false;
        if (wdth2Unit != null ? !wdth2Unit.equals(that.wdth2Unit) : that.wdth2Unit != null) return false;
        if (length1 != null ? !length1.equals(that.length1) : that.length1 != null) return false;
        if (len1Unit != null ? !len1Unit.equals(that.len1Unit) : that.len1Unit != null) return false;
        if (length2 != null ? !length2.equals(that.length2) : that.length2 != null) return false;
        if (len2Unit != null ? !len2Unit.equals(that.len2Unit) : that.len2Unit != null) return false;
        if (volume != null ? !volume.equals(that.volume) : that.volume != null) return false;
        if (volUnit != null ? !volUnit.equals(that.volUnit) : that.volUnit != null) return false;
        if (weight1 != null ? !weight1.equals(that.weight1) : that.weight1 != null) return false;
        if (wght1Unit != null ? !wght1Unit.equals(that.wght1Unit) : that.wght1Unit != null) return false;
        if (weight2 != null ? !weight2.equals(that.weight2) : that.weight2 != null) return false;
        if (wght2Unit != null ? !wght2Unit.equals(that.wght2Unit) : that.wght2Unit != null) return false;
        if (factor1 != null ? !factor1.equals(that.factor1) : that.factor1 != null) return false;
        if (factor2 != null ? !factor2.equals(that.factor2) : that.factor2 != null) return false;
        if (factor3 != null ? !factor3.equals(that.factor3) : that.factor3 != null) return false;
        if (factor4 != null ? !factor4.equals(that.factor4) : that.factor4 != null) return false;
        if (packQty != null ? !packQty.equals(that.packQty) : that.packQty != null) return false;
        if (updInvntry != null ? !updInvntry.equals(that.updInvntry) : that.updInvntry != null) return false;
        if (baseDocNum != null ? !baseDocNum.equals(that.baseDocNum) : that.baseDocNum != null) return false;
        if (baseAtCard != null ? !baseAtCard.equals(that.baseAtCard) : that.baseAtCard != null) return false;
        if (sww != null ? !sww.equals(that.sww) : that.sww != null) return false;
        if (vatSum != null ? !vatSum.equals(that.vatSum) : that.vatSum != null) return false;
        if (vatSumFrgn != null ? !vatSumFrgn.equals(that.vatSumFrgn) : that.vatSumFrgn != null) return false;
        if (vatSumSy != null ? !vatSumSy.equals(that.vatSumSy) : that.vatSumSy != null) return false;
        if (finncPriod != null ? !finncPriod.equals(that.finncPriod) : that.finncPriod != null) return false;
        if (objType != null ? !objType.equals(that.objType) : that.objType != null) return false;
        if (logInstanc != null ? !logInstanc.equals(that.logInstanc) : that.logInstanc != null) return false;
        if (blockNum != null ? !blockNum.equals(that.blockNum) : that.blockNum != null) return false;
        if (importLog != null ? !importLog.equals(that.importLog) : that.importLog != null) return false;
        if (dedVatSum != null ? !dedVatSum.equals(that.dedVatSum) : that.dedVatSum != null) return false;
        if (dedVatSumF != null ? !dedVatSumF.equals(that.dedVatSumF) : that.dedVatSumF != null) return false;
        if (dedVatSumS != null ? !dedVatSumS.equals(that.dedVatSumS) : that.dedVatSumS != null) return false;
        if (isAqcuistn != null ? !isAqcuistn.equals(that.isAqcuistn) : that.isAqcuistn != null) return false;
        if (distribSum != null ? !distribSum.equals(that.distribSum) : that.distribSum != null) return false;
        if (dstrbSumFc != null ? !dstrbSumFc.equals(that.dstrbSumFc) : that.dstrbSumFc != null) return false;
        if (dstrbSumSc != null ? !dstrbSumSc.equals(that.dstrbSumSc) : that.dstrbSumSc != null) return false;
        if (grssProfit != null ? !grssProfit.equals(that.grssProfit) : that.grssProfit != null) return false;
        if (grssProfSc != null ? !grssProfSc.equals(that.grssProfSc) : that.grssProfSc != null) return false;
        if (grssProfFc != null ? !grssProfFc.equals(that.grssProfFc) : that.grssProfFc != null) return false;
        if (visOrder != null ? !visOrder.equals(that.visOrder) : that.visOrder != null) return false;
        if (inmPrice != null ? !inmPrice.equals(that.inmPrice) : that.inmPrice != null) return false;
        if (poTrgNum != null ? !poTrgNum.equals(that.poTrgNum) : that.poTrgNum != null) return false;
        if (poTrgEntry != null ? !poTrgEntry.equals(that.poTrgEntry) : that.poTrgEntry != null) return false;
        if (dropShip != null ? !dropShip.equals(that.dropShip) : that.dropShip != null) return false;
        if (poLineNum != null ? !poLineNum.equals(that.poLineNum) : that.poLineNum != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (taxCode != null ? !taxCode.equals(that.taxCode) : that.taxCode != null) return false;
        if (taxType != null ? !taxType.equals(that.taxType) : that.taxType != null) return false;
        if (origItem != null ? !origItem.equals(that.origItem) : that.origItem != null) return false;
        if (backOrdr != null ? !backOrdr.equals(that.backOrdr) : that.backOrdr != null) return false;
        if (freeTxt != null ? !freeTxt.equals(that.freeTxt) : that.freeTxt != null) return false;
        if (pickStatus != null ? !pickStatus.equals(that.pickStatus) : that.pickStatus != null) return false;
        if (pickOty != null ? !pickOty.equals(that.pickOty) : that.pickOty != null) return false;
        if (pickIdNo != null ? !pickIdNo.equals(that.pickIdNo) : that.pickIdNo != null) return false;
        if (trnsCode != null ? !trnsCode.equals(that.trnsCode) : that.trnsCode != null) return false;
        if (vatAppld != null ? !vatAppld.equals(that.vatAppld) : that.vatAppld != null) return false;
        if (vatAppldFc != null ? !vatAppldFc.equals(that.vatAppldFc) : that.vatAppldFc != null) return false;
        if (vatAppldSc != null ? !vatAppldSc.equals(that.vatAppldSc) : that.vatAppldSc != null) return false;
        if (baseQty != null ? !baseQty.equals(that.baseQty) : that.baseQty != null) return false;
        if (baseOpnQty != null ? !baseOpnQty.equals(that.baseOpnQty) : that.baseOpnQty != null) return false;
        if (vatDscntPr != null ? !vatDscntPr.equals(that.vatDscntPr) : that.vatDscntPr != null) return false;
        if (wtLiable != null ? !wtLiable.equals(that.wtLiable) : that.wtLiable != null) return false;
        if (deferrTax != null ? !deferrTax.equals(that.deferrTax) : that.deferrTax != null) return false;
        if (equVatPer != null ? !equVatPer.equals(that.equVatPer) : that.equVatPer != null) return false;
        if (equVatSum != null ? !equVatSum.equals(that.equVatSum) : that.equVatSum != null) return false;
        if (equVatSumF != null ? !equVatSumF.equals(that.equVatSumF) : that.equVatSumF != null) return false;
        if (equVatSumS != null ? !equVatSumS.equals(that.equVatSumS) : that.equVatSumS != null) return false;
        if (lineVat != null ? !lineVat.equals(that.lineVat) : that.lineVat != null) return false;
        if (lineVatlF != null ? !lineVatlF.equals(that.lineVatlF) : that.lineVatlF != null) return false;
        if (lineVatS != null ? !lineVatS.equals(that.lineVatS) : that.lineVatS != null) return false;
        if (unitMsr != null ? !unitMsr.equals(that.unitMsr) : that.unitMsr != null) return false;
        if (numPerMsr != null ? !numPerMsr.equals(that.numPerMsr) : that.numPerMsr != null) return false;
        if (ceecFlag != null ? !ceecFlag.equals(that.ceecFlag) : that.ceecFlag != null) return false;
        if (toStock != null ? !toStock.equals(that.toStock) : that.toStock != null) return false;
        if (toDiff != null ? !toDiff.equals(that.toDiff) : that.toDiff != null) return false;
        if (exciseAmt != null ? !exciseAmt.equals(that.exciseAmt) : that.exciseAmt != null) return false;
        if (taxPerUnit != null ? !taxPerUnit.equals(that.taxPerUnit) : that.taxPerUnit != null) return false;
        if (totInclTax != null ? !totInclTax.equals(that.totInclTax) : that.totInclTax != null) return false;
        if (countryOrg != null ? !countryOrg.equals(that.countryOrg) : that.countryOrg != null) return false;
        if (stckDstSum != null ? !stckDstSum.equals(that.stckDstSum) : that.stckDstSum != null) return false;
        if (releasQtty != null ? !releasQtty.equals(that.releasQtty) : that.releasQtty != null) return false;
        if (lineType != null ? !lineType.equals(that.lineType) : that.lineType != null) return false;
        if (tranType != null ? !tranType.equals(that.tranType) : that.tranType != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (ownerCode != null ? !ownerCode.equals(that.ownerCode) : that.ownerCode != null) return false;
        if (stockPrice != null ? !stockPrice.equals(that.stockPrice) : that.stockPrice != null) return false;
        if (consumeFct != null ? !consumeFct.equals(that.consumeFct) : that.consumeFct != null) return false;
        if (lstByDsSum != null ? !lstByDsSum.equals(that.lstByDsSum) : that.lstByDsSum != null) return false;
        if (stckInmPr != null ? !stckInmPr.equals(that.stckInmPr) : that.stckInmPr != null) return false;
        if (lstBinmPr != null ? !lstBinmPr.equals(that.lstBinmPr) : that.lstBinmPr != null) return false;
        if (stckDstFc != null ? !stckDstFc.equals(that.stckDstFc) : that.stckDstFc != null) return false;
        if (stckDstSc != null ? !stckDstSc.equals(that.stckDstSc) : that.stckDstSc != null) return false;
        if (lstByDsFc != null ? !lstByDsFc.equals(that.lstByDsFc) : that.lstByDsFc != null) return false;
        if (lstByDsSc != null ? !lstByDsSc.equals(that.lstByDsSc) : that.lstByDsSc != null) return false;
        if (stockSum != null ? !stockSum.equals(that.stockSum) : that.stockSum != null) return false;
        if (stockSumFc != null ? !stockSumFc.equals(that.stockSumFc) : that.stockSumFc != null) return false;
        if (stockSumSc != null ? !stockSumSc.equals(that.stockSumSc) : that.stockSumSc != null) return false;
        if (stckSumApp != null ? !stckSumApp.equals(that.stckSumApp) : that.stckSumApp != null) return false;
        if (stckAppFc != null ? !stckAppFc.equals(that.stckAppFc) : that.stckAppFc != null) return false;
        if (stckAppSc != null ? !stckAppSc.equals(that.stckAppSc) : that.stckAppSc != null) return false;
        if (shipToCode != null ? !shipToCode.equals(that.shipToCode) : that.shipToCode != null) return false;
        if (shipToDesc != null ? !shipToDesc.equals(that.shipToDesc) : that.shipToDesc != null) return false;
        if (stckAppD != null ? !stckAppD.equals(that.stckAppD) : that.stckAppD != null) return false;
        if (stckAppDfc != null ? !stckAppDfc.equals(that.stckAppDfc) : that.stckAppDfc != null) return false;
        if (stckAppDsc != null ? !stckAppDsc.equals(that.stckAppDsc) : that.stckAppDsc != null) return false;
        if (basePrice != null ? !basePrice.equals(that.basePrice) : that.basePrice != null) return false;
        if (gTotal != null ? !gTotal.equals(that.gTotal) : that.gTotal != null) return false;
        if (gTotalFc != null ? !gTotalFc.equals(that.gTotalFc) : that.gTotalFc != null) return false;
        if (gTotalSc != null ? !gTotalSc.equals(that.gTotalSc) : that.gTotalSc != null) return false;
        if (distribExp != null ? !distribExp.equals(that.distribExp) : that.distribExp != null) return false;
        if (descOw != null ? !descOw.equals(that.descOw) : that.descOw != null) return false;
        if (detailsOw != null ? !detailsOw.equals(that.detailsOw) : that.detailsOw != null) return false;
        if (grossBase != null ? !grossBase.equals(that.grossBase) : that.grossBase != null) return false;
        if (vatWoDpm != null ? !vatWoDpm.equals(that.vatWoDpm) : that.vatWoDpm != null) return false;
        if (vatWoDpmFc != null ? !vatWoDpmFc.equals(that.vatWoDpmFc) : that.vatWoDpmFc != null) return false;
        if (vatWoDpmSc != null ? !vatWoDpmSc.equals(that.vatWoDpmSc) : that.vatWoDpmSc != null) return false;
        if (cfopCode != null ? !cfopCode.equals(that.cfopCode) : that.cfopCode != null) return false;
        if (cstCode != null ? !cstCode.equals(that.cstCode) : that.cstCode != null) return false;
        if (usage != null ? !usage.equals(that.usage) : that.usage != null) return false;
        if (taxOnly != null ? !taxOnly.equals(that.taxOnly) : that.taxOnly != null) return false;
        if (wtCalced != null ? !wtCalced.equals(that.wtCalced) : that.wtCalced != null) return false;
        if (qtyToShip != null ? !qtyToShip.equals(that.qtyToShip) : that.qtyToShip != null) return false;
        if (delivrdQty != null ? !delivrdQty.equals(that.delivrdQty) : that.delivrdQty != null) return false;
        if (orderedQty != null ? !orderedQty.equals(that.orderedQty) : that.orderedQty != null) return false;
        if (cogsOcrCod != null ? !cogsOcrCod.equals(that.cogsOcrCod) : that.cogsOcrCod != null) return false;
        if (ciOppLineN != null ? !ciOppLineN.equals(that.ciOppLineN) : that.ciOppLineN != null) return false;
        if (cogsAcct != null ? !cogsAcct.equals(that.cogsAcct) : that.cogsAcct != null) return false;
        if (chgAsmBoMw != null ? !chgAsmBoMw.equals(that.chgAsmBoMw) : that.chgAsmBoMw != null) return false;
        if (actDelDate != null ? !actDelDate.equals(that.actDelDate) : that.actDelDate != null) return false;
        if (ocrCode2 != null ? !ocrCode2.equals(that.ocrCode2) : that.ocrCode2 != null) return false;
        if (ocrCode3 != null ? !ocrCode3.equals(that.ocrCode3) : that.ocrCode3 != null) return false;
        if (ocrCode4 != null ? !ocrCode4.equals(that.ocrCode4) : that.ocrCode4 != null) return false;
        if (ocrCode5 != null ? !ocrCode5.equals(that.ocrCode5) : that.ocrCode5 != null) return false;
        if (taxDistSum != null ? !taxDistSum.equals(that.taxDistSum) : that.taxDistSum != null) return false;
        if (taxDistSfc != null ? !taxDistSfc.equals(that.taxDistSfc) : that.taxDistSfc != null) return false;
        if (taxDistSsc != null ? !taxDistSsc.equals(that.taxDistSsc) : that.taxDistSsc != null) return false;
        if (postTax != null ? !postTax.equals(that.postTax) : that.postTax != null) return false;
        if (excisable != null ? !excisable.equals(that.excisable) : that.excisable != null) return false;
        if (assblValue != null ? !assblValue.equals(that.assblValue) : that.assblValue != null) return false;
        if (rg23APart1 != null ? !rg23APart1.equals(that.rg23APart1) : that.rg23APart1 != null) return false;
        if (rg23APart2 != null ? !rg23APart2.equals(that.rg23APart2) : that.rg23APart2 != null) return false;
        if (rg23CPart1 != null ? !rg23CPart1.equals(that.rg23CPart1) : that.rg23CPart1 != null) return false;
        if (rg23CPart2 != null ? !rg23CPart2.equals(that.rg23CPart2) : that.rg23CPart2 != null) return false;
        if (cogsOcrCo2 != null ? !cogsOcrCo2.equals(that.cogsOcrCo2) : that.cogsOcrCo2 != null) return false;
        if (cogsOcrCo3 != null ? !cogsOcrCo3.equals(that.cogsOcrCo3) : that.cogsOcrCo3 != null) return false;
        if (cogsOcrCo4 != null ? !cogsOcrCo4.equals(that.cogsOcrCo4) : that.cogsOcrCo4 != null) return false;
        if (cogsOcrCo5 != null ? !cogsOcrCo5.equals(that.cogsOcrCo5) : that.cogsOcrCo5 != null) return false;
        if (lnExcised != null ? !lnExcised.equals(that.lnExcised) : that.lnExcised != null) return false;
        if (locCode != null ? !locCode.equals(that.locCode) : that.locCode != null) return false;
        if (stockValue != null ? !stockValue.equals(that.stockValue) : that.stockValue != null) return false;
        if (gpTtlBasPr != null ? !gpTtlBasPr.equals(that.gpTtlBasPr) : that.gpTtlBasPr != null) return false;
        if (unitMsr2 != null ? !unitMsr2.equals(that.unitMsr2) : that.unitMsr2 != null) return false;
        if (numPerMsr2 != null ? !numPerMsr2.equals(that.numPerMsr2) : that.numPerMsr2 != null) return false;
        if (specPrice != null ? !specPrice.equals(that.specPrice) : that.specPrice != null) return false;
        if (csTfIpi != null ? !csTfIpi.equals(that.csTfIpi) : that.csTfIpi != null) return false;
        if (csTfPis != null ? !csTfPis.equals(that.csTfPis) : that.csTfPis != null) return false;
        if (csTfCofins != null ? !csTfCofins.equals(that.csTfCofins) : that.csTfCofins != null) return false;
        if (exLineNo != null ? !exLineNo.equals(that.exLineNo) : that.exLineNo != null) return false;
        if (isSrvCall != null ? !isSrvCall.equals(that.isSrvCall) : that.isSrvCall != null) return false;
        if (pqtReqQty != null ? !pqtReqQty.equals(that.pqtReqQty) : that.pqtReqQty != null) return false;
        if (pqtReqDate != null ? !pqtReqDate.equals(that.pqtReqDate) : that.pqtReqDate != null) return false;
        if (pcDocType != null ? !pcDocType.equals(that.pcDocType) : that.pcDocType != null) return false;
        if (pcQuantity != null ? !pcQuantity.equals(that.pcQuantity) : that.pcQuantity != null) return false;
        if (linManClsd != null ? !linManClsd.equals(that.linManClsd) : that.linManClsd != null) return false;
        if (vatGrpSrc != null ? !vatGrpSrc.equals(that.vatGrpSrc) : that.vatGrpSrc != null) return false;
        if (noInvtryMv != null ? !noInvtryMv.equals(that.noInvtryMv) : that.noInvtryMv != null) return false;
        if (actBaseEnt != null ? !actBaseEnt.equals(that.actBaseEnt) : that.actBaseEnt != null) return false;
        if (actBaseLn != null ? !actBaseLn.equals(that.actBaseLn) : that.actBaseLn != null) return false;
        if (actBaseNum != null ? !actBaseNum.equals(that.actBaseNum) : that.actBaseNum != null) return false;
        if (openRtnQty != null ? !openRtnQty.equals(that.openRtnQty) : that.openRtnQty != null) return false;
        if (agrNo != null ? !agrNo.equals(that.agrNo) : that.agrNo != null) return false;
        if (agrLnNum != null ? !agrLnNum.equals(that.agrLnNum) : that.agrLnNum != null) return false;
        if (credOrigin != null ? !credOrigin.equals(that.credOrigin) : that.credOrigin != null) return false;
        if (surpluses != null ? !surpluses.equals(that.surpluses) : that.surpluses != null) return false;
        if (defBreak != null ? !defBreak.equals(that.defBreak) : that.defBreak != null) return false;
        if (shortages != null ? !shortages.equals(that.shortages) : that.shortages != null) return false;
        if (uomEntry != null ? !uomEntry.equals(that.uomEntry) : that.uomEntry != null) return false;
        if (uomEntry2 != null ? !uomEntry2.equals(that.uomEntry2) : that.uomEntry2 != null) return false;
        if (uomCode != null ? !uomCode.equals(that.uomCode) : that.uomCode != null) return false;
        if (uomCode2 != null ? !uomCode2.equals(that.uomCode2) : that.uomCode2 != null) return false;
        if (fromWhsCod != null ? !fromWhsCod.equals(that.fromWhsCod) : that.fromWhsCod != null) return false;
        if (needQty != null ? !needQty.equals(that.needQty) : that.needQty != null) return false;
        if (partRetire != null ? !partRetire.equals(that.partRetire) : that.partRetire != null) return false;
        if (retireQty != null ? !retireQty.equals(that.retireQty) : that.retireQty != null) return false;
        if (retireApc != null ? !retireApc.equals(that.retireApc) : that.retireApc != null) return false;
        if (retirApcfc != null ? !retirApcfc.equals(that.retirApcfc) : that.retirApcfc != null) return false;
        if (retirApcsc != null ? !retirApcsc.equals(that.retirApcsc) : that.retirApcsc != null) return false;
        if (invQty != null ? !invQty.equals(that.invQty) : that.invQty != null) return false;
        if (openInvQty != null ? !openInvQty.equals(that.openInvQty) : that.openInvQty != null) return false;
        if (enSetCost != null ? !enSetCost.equals(that.enSetCost) : that.enSetCost != null) return false;
        if (retCost != null ? !retCost.equals(that.retCost) : that.retCost != null) return false;
        if (incoterms != null ? !incoterms.equals(that.incoterms) : that.incoterms != null) return false;
        if (transMod != null ? !transMod.equals(that.transMod) : that.transMod != null) return false;
        if (lineVendor != null ? !lineVendor.equals(that.lineVendor) : that.lineVendor != null) return false;
        if (distribIs != null ? !distribIs.equals(that.distribIs) : that.distribIs != null) return false;
        if (isDistrb != null ? !isDistrb.equals(that.isDistrb) : that.isDistrb != null) return false;
        if (isDistrbFc != null ? !isDistrbFc.equals(that.isDistrbFc) : that.isDistrbFc != null) return false;
        if (isDistrbSc != null ? !isDistrbSc.equals(that.isDistrbSc) : that.isDistrbSc != null) return false;
        if (isByPrdct != null ? !isByPrdct.equals(that.isByPrdct) : that.isByPrdct != null) return false;
        if (itemType != null ? !itemType.equals(that.itemType) : that.itemType != null) return false;
        if (priceEdit != null ? !priceEdit.equals(that.priceEdit) : that.priceEdit != null) return false;
        if (prntLnNum != null ? !prntLnNum.equals(that.prntLnNum) : that.prntLnNum != null) return false;
        if (linePoPrss != null ? !linePoPrss.equals(that.linePoPrss) : that.linePoPrss != null) return false;
        if (freeChrgBp != null ? !freeChrgBp.equals(that.freeChrgBp) : that.freeChrgBp != null) return false;
        if (taxRelev != null ? !taxRelev.equals(that.taxRelev) : that.taxRelev != null) return false;
        if (legalText != null ? !legalText.equals(that.legalText) : that.legalText != null) return false;
        if (thirdParty != null ? !thirdParty.equals(that.thirdParty) : that.thirdParty != null) return false;
        if (licTradNum != null ? !licTradNum.equals(that.licTradNum) : that.licTradNum != null) return false;
        if (invQtyOnly != null ? !invQtyOnly.equals(that.invQtyOnly) : that.invQtyOnly != null) return false;
        if (unencReasn != null ? !unencReasn.equals(that.unencReasn) : that.unencReasn != null) return false;
        if (shipFromCo != null ? !shipFromCo.equals(that.shipFromCo) : that.shipFromCo != null) return false;
        if (shipFromDe != null ? !shipFromDe.equals(that.shipFromDe) : that.shipFromDe != null) return false;
        if (fisrtBin != null ? !fisrtBin.equals(that.fisrtBin) : that.fisrtBin != null) return false;
        if (allocBinC != null ? !allocBinC.equals(that.allocBinC) : that.allocBinC != null) return false;
        if (expType != null ? !expType.equals(that.expType) : that.expType != null) return false;
        if (expUuid != null ? !expUuid.equals(that.expUuid) : that.expUuid != null) return false;
        if (expOpType != null ? !expOpType.equals(that.expOpType) : that.expOpType != null) return false;
        if (diotNat != null ? !diotNat.equals(that.diotNat) : that.diotNat != null) return false;
        if (myFtype != null ? !myFtype.equals(that.myFtype) : that.myFtype != null) return false;
        if (itmTaxType != null ? !itmTaxType.equals(that.itmTaxType) : that.itmTaxType != null) return false;
        if (sacEntry != null ? !sacEntry.equals(that.sacEntry) : that.sacEntry != null) return false;
        if (uRdcode != null ? !uRdcode.equals(that.uRdcode) : that.uRdcode != null) return false;
        if (uTenkythuat != null ? !uTenkythuat.equals(that.uTenkythuat) : that.uTenkythuat != null) return false;
        if (uQcCode != null ? !uQcCode.equals(that.uQcCode) : that.uQcCode != null) return false;
        if (uQcLine != null ? !uQcLine.equals(that.uQcLine) : that.uQcLine != null) return false;
        if (uCknb != null ? !uCknb.equals(that.uCknb) : that.uCknb != null) return false;
        if (uKm != null ? !uKm.equals(that.uKm) : that.uKm != null) return false;
        if (uSendToHo != null ? !uSendToHo.equals(that.uSendToHo) : that.uSendToHo != null) return false;
        if (uDvTthuc != null ? !uDvTthuc.equals(that.uDvTthuc) : that.uDvTthuc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = docEntry != null ? docEntry.hashCode() : 0;
        result = 31 * result + (lineNum != null ? lineNum.hashCode() : 0);
        result = 31 * result + (targetType != null ? targetType.hashCode() : 0);
        result = 31 * result + (trgetEntry != null ? trgetEntry.hashCode() : 0);
        result = 31 * result + (baseRef != null ? baseRef.hashCode() : 0);
        result = 31 * result + (baseType != null ? baseType.hashCode() : 0);
        result = 31 * result + (baseEntry != null ? baseEntry.hashCode() : 0);
        result = 31 * result + (baseLine != null ? baseLine.hashCode() : 0);
        result = 31 * result + (lineStatus != null ? lineStatus.hashCode() : 0);
        result = 31 * result + (itemCode != null ? itemCode.hashCode() : 0);
        result = 31 * result + (dscription != null ? dscription.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (shipDate != null ? shipDate.hashCode() : 0);
        result = 31 * result + (openQty != null ? openQty.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (rate != null ? rate.hashCode() : 0);
        result = 31 * result + (discPrcnt != null ? discPrcnt.hashCode() : 0);
        result = 31 * result + (lineTotal != null ? lineTotal.hashCode() : 0);
        result = 31 * result + (totalFrgn != null ? totalFrgn.hashCode() : 0);
        result = 31 * result + (openSum != null ? openSum.hashCode() : 0);
        result = 31 * result + (openSumFc != null ? openSumFc.hashCode() : 0);
        result = 31 * result + (vendorNum != null ? vendorNum.hashCode() : 0);
        result = 31 * result + (serialNum != null ? serialNum.hashCode() : 0);
        result = 31 * result + (whsCode != null ? whsCode.hashCode() : 0);
        result = 31 * result + (slpCode != null ? slpCode.hashCode() : 0);
        result = 31 * result + (commission != null ? commission.hashCode() : 0);
        result = 31 * result + (treeType != null ? treeType.hashCode() : 0);
        result = 31 * result + (acctCode != null ? acctCode.hashCode() : 0);
        result = 31 * result + (taxStatus != null ? taxStatus.hashCode() : 0);
        result = 31 * result + (grossBuyPr != null ? grossBuyPr.hashCode() : 0);
        result = 31 * result + (priceBefDi != null ? priceBefDi.hashCode() : 0);
        result = 31 * result + (docDate != null ? docDate.hashCode() : 0);
        result = 31 * result + (flags != null ? flags.hashCode() : 0);
        result = 31 * result + (openCreQty != null ? openCreQty.hashCode() : 0);
        result = 31 * result + (useBaseUn != null ? useBaseUn.hashCode() : 0);
        result = 31 * result + (subCatNum != null ? subCatNum.hashCode() : 0);
        result = 31 * result + (baseCard != null ? baseCard.hashCode() : 0);
        result = 31 * result + (totalSumSy != null ? totalSumSy.hashCode() : 0);
        result = 31 * result + (openSumSys != null ? openSumSys.hashCode() : 0);
        result = 31 * result + (invntSttus != null ? invntSttus.hashCode() : 0);
        result = 31 * result + (ocrCode != null ? ocrCode.hashCode() : 0);
        result = 31 * result + (project != null ? project.hashCode() : 0);
        result = 31 * result + (codeBars != null ? codeBars.hashCode() : 0);
        result = 31 * result + (vatPrcnt != null ? vatPrcnt.hashCode() : 0);
        result = 31 * result + (vatGroup != null ? vatGroup.hashCode() : 0);
        result = 31 * result + (priceAfVat != null ? priceAfVat.hashCode() : 0);
        result = 31 * result + (height1 != null ? height1.hashCode() : 0);
        result = 31 * result + (hght1Unit != null ? hght1Unit.hashCode() : 0);
        result = 31 * result + (height2 != null ? height2.hashCode() : 0);
        result = 31 * result + (hght2Unit != null ? hght2Unit.hashCode() : 0);
        result = 31 * result + (width1 != null ? width1.hashCode() : 0);
        result = 31 * result + (wdth1Unit != null ? wdth1Unit.hashCode() : 0);
        result = 31 * result + (width2 != null ? width2.hashCode() : 0);
        result = 31 * result + (wdth2Unit != null ? wdth2Unit.hashCode() : 0);
        result = 31 * result + (length1 != null ? length1.hashCode() : 0);
        result = 31 * result + (len1Unit != null ? len1Unit.hashCode() : 0);
        result = 31 * result + (length2 != null ? length2.hashCode() : 0);
        result = 31 * result + (len2Unit != null ? len2Unit.hashCode() : 0);
        result = 31 * result + (volume != null ? volume.hashCode() : 0);
        result = 31 * result + (volUnit != null ? volUnit.hashCode() : 0);
        result = 31 * result + (weight1 != null ? weight1.hashCode() : 0);
        result = 31 * result + (wght1Unit != null ? wght1Unit.hashCode() : 0);
        result = 31 * result + (weight2 != null ? weight2.hashCode() : 0);
        result = 31 * result + (wght2Unit != null ? wght2Unit.hashCode() : 0);
        result = 31 * result + (factor1 != null ? factor1.hashCode() : 0);
        result = 31 * result + (factor2 != null ? factor2.hashCode() : 0);
        result = 31 * result + (factor3 != null ? factor3.hashCode() : 0);
        result = 31 * result + (factor4 != null ? factor4.hashCode() : 0);
        result = 31 * result + (packQty != null ? packQty.hashCode() : 0);
        result = 31 * result + (updInvntry != null ? updInvntry.hashCode() : 0);
        result = 31 * result + (baseDocNum != null ? baseDocNum.hashCode() : 0);
        result = 31 * result + (baseAtCard != null ? baseAtCard.hashCode() : 0);
        result = 31 * result + (sww != null ? sww.hashCode() : 0);
        result = 31 * result + (vatSum != null ? vatSum.hashCode() : 0);
        result = 31 * result + (vatSumFrgn != null ? vatSumFrgn.hashCode() : 0);
        result = 31 * result + (vatSumSy != null ? vatSumSy.hashCode() : 0);
        result = 31 * result + (finncPriod != null ? finncPriod.hashCode() : 0);
        result = 31 * result + (objType != null ? objType.hashCode() : 0);
        result = 31 * result + (logInstanc != null ? logInstanc.hashCode() : 0);
        result = 31 * result + (blockNum != null ? blockNum.hashCode() : 0);
        result = 31 * result + (importLog != null ? importLog.hashCode() : 0);
        result = 31 * result + (dedVatSum != null ? dedVatSum.hashCode() : 0);
        result = 31 * result + (dedVatSumF != null ? dedVatSumF.hashCode() : 0);
        result = 31 * result + (dedVatSumS != null ? dedVatSumS.hashCode() : 0);
        result = 31 * result + (isAqcuistn != null ? isAqcuistn.hashCode() : 0);
        result = 31 * result + (distribSum != null ? distribSum.hashCode() : 0);
        result = 31 * result + (dstrbSumFc != null ? dstrbSumFc.hashCode() : 0);
        result = 31 * result + (dstrbSumSc != null ? dstrbSumSc.hashCode() : 0);
        result = 31 * result + (grssProfit != null ? grssProfit.hashCode() : 0);
        result = 31 * result + (grssProfSc != null ? grssProfSc.hashCode() : 0);
        result = 31 * result + (grssProfFc != null ? grssProfFc.hashCode() : 0);
        result = 31 * result + (visOrder != null ? visOrder.hashCode() : 0);
        result = 31 * result + (inmPrice != null ? inmPrice.hashCode() : 0);
        result = 31 * result + (poTrgNum != null ? poTrgNum.hashCode() : 0);
        result = 31 * result + (poTrgEntry != null ? poTrgEntry.hashCode() : 0);
        result = 31 * result + (dropShip != null ? dropShip.hashCode() : 0);
        result = 31 * result + (poLineNum != null ? poLineNum.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (taxCode != null ? taxCode.hashCode() : 0);
        result = 31 * result + (taxType != null ? taxType.hashCode() : 0);
        result = 31 * result + (origItem != null ? origItem.hashCode() : 0);
        result = 31 * result + (backOrdr != null ? backOrdr.hashCode() : 0);
        result = 31 * result + (freeTxt != null ? freeTxt.hashCode() : 0);
        result = 31 * result + (pickStatus != null ? pickStatus.hashCode() : 0);
        result = 31 * result + (pickOty != null ? pickOty.hashCode() : 0);
        result = 31 * result + (pickIdNo != null ? pickIdNo.hashCode() : 0);
        result = 31 * result + (trnsCode != null ? trnsCode.hashCode() : 0);
        result = 31 * result + (vatAppld != null ? vatAppld.hashCode() : 0);
        result = 31 * result + (vatAppldFc != null ? vatAppldFc.hashCode() : 0);
        result = 31 * result + (vatAppldSc != null ? vatAppldSc.hashCode() : 0);
        result = 31 * result + (baseQty != null ? baseQty.hashCode() : 0);
        result = 31 * result + (baseOpnQty != null ? baseOpnQty.hashCode() : 0);
        result = 31 * result + (vatDscntPr != null ? vatDscntPr.hashCode() : 0);
        result = 31 * result + (wtLiable != null ? wtLiable.hashCode() : 0);
        result = 31 * result + (deferrTax != null ? deferrTax.hashCode() : 0);
        result = 31 * result + (equVatPer != null ? equVatPer.hashCode() : 0);
        result = 31 * result + (equVatSum != null ? equVatSum.hashCode() : 0);
        result = 31 * result + (equVatSumF != null ? equVatSumF.hashCode() : 0);
        result = 31 * result + (equVatSumS != null ? equVatSumS.hashCode() : 0);
        result = 31 * result + (lineVat != null ? lineVat.hashCode() : 0);
        result = 31 * result + (lineVatlF != null ? lineVatlF.hashCode() : 0);
        result = 31 * result + (lineVatS != null ? lineVatS.hashCode() : 0);
        result = 31 * result + (unitMsr != null ? unitMsr.hashCode() : 0);
        result = 31 * result + (numPerMsr != null ? numPerMsr.hashCode() : 0);
        result = 31 * result + (ceecFlag != null ? ceecFlag.hashCode() : 0);
        result = 31 * result + (toStock != null ? toStock.hashCode() : 0);
        result = 31 * result + (toDiff != null ? toDiff.hashCode() : 0);
        result = 31 * result + (exciseAmt != null ? exciseAmt.hashCode() : 0);
        result = 31 * result + (taxPerUnit != null ? taxPerUnit.hashCode() : 0);
        result = 31 * result + (totInclTax != null ? totInclTax.hashCode() : 0);
        result = 31 * result + (countryOrg != null ? countryOrg.hashCode() : 0);
        result = 31 * result + (stckDstSum != null ? stckDstSum.hashCode() : 0);
        result = 31 * result + (releasQtty != null ? releasQtty.hashCode() : 0);
        result = 31 * result + (lineType != null ? lineType.hashCode() : 0);
        result = 31 * result + (tranType != null ? tranType.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (ownerCode != null ? ownerCode.hashCode() : 0);
        result = 31 * result + (stockPrice != null ? stockPrice.hashCode() : 0);
        result = 31 * result + (consumeFct != null ? consumeFct.hashCode() : 0);
        result = 31 * result + (lstByDsSum != null ? lstByDsSum.hashCode() : 0);
        result = 31 * result + (stckInmPr != null ? stckInmPr.hashCode() : 0);
        result = 31 * result + (lstBinmPr != null ? lstBinmPr.hashCode() : 0);
        result = 31 * result + (stckDstFc != null ? stckDstFc.hashCode() : 0);
        result = 31 * result + (stckDstSc != null ? stckDstSc.hashCode() : 0);
        result = 31 * result + (lstByDsFc != null ? lstByDsFc.hashCode() : 0);
        result = 31 * result + (lstByDsSc != null ? lstByDsSc.hashCode() : 0);
        result = 31 * result + (stockSum != null ? stockSum.hashCode() : 0);
        result = 31 * result + (stockSumFc != null ? stockSumFc.hashCode() : 0);
        result = 31 * result + (stockSumSc != null ? stockSumSc.hashCode() : 0);
        result = 31 * result + (stckSumApp != null ? stckSumApp.hashCode() : 0);
        result = 31 * result + (stckAppFc != null ? stckAppFc.hashCode() : 0);
        result = 31 * result + (stckAppSc != null ? stckAppSc.hashCode() : 0);
        result = 31 * result + (shipToCode != null ? shipToCode.hashCode() : 0);
        result = 31 * result + (shipToDesc != null ? shipToDesc.hashCode() : 0);
        result = 31 * result + (stckAppD != null ? stckAppD.hashCode() : 0);
        result = 31 * result + (stckAppDfc != null ? stckAppDfc.hashCode() : 0);
        result = 31 * result + (stckAppDsc != null ? stckAppDsc.hashCode() : 0);
        result = 31 * result + (basePrice != null ? basePrice.hashCode() : 0);
        result = 31 * result + (gTotal != null ? gTotal.hashCode() : 0);
        result = 31 * result + (gTotalFc != null ? gTotalFc.hashCode() : 0);
        result = 31 * result + (gTotalSc != null ? gTotalSc.hashCode() : 0);
        result = 31 * result + (distribExp != null ? distribExp.hashCode() : 0);
        result = 31 * result + (descOw != null ? descOw.hashCode() : 0);
        result = 31 * result + (detailsOw != null ? detailsOw.hashCode() : 0);
        result = 31 * result + (grossBase != null ? grossBase.hashCode() : 0);
        result = 31 * result + (vatWoDpm != null ? vatWoDpm.hashCode() : 0);
        result = 31 * result + (vatWoDpmFc != null ? vatWoDpmFc.hashCode() : 0);
        result = 31 * result + (vatWoDpmSc != null ? vatWoDpmSc.hashCode() : 0);
        result = 31 * result + (cfopCode != null ? cfopCode.hashCode() : 0);
        result = 31 * result + (cstCode != null ? cstCode.hashCode() : 0);
        result = 31 * result + (usage != null ? usage.hashCode() : 0);
        result = 31 * result + (taxOnly != null ? taxOnly.hashCode() : 0);
        result = 31 * result + (wtCalced != null ? wtCalced.hashCode() : 0);
        result = 31 * result + (qtyToShip != null ? qtyToShip.hashCode() : 0);
        result = 31 * result + (delivrdQty != null ? delivrdQty.hashCode() : 0);
        result = 31 * result + (orderedQty != null ? orderedQty.hashCode() : 0);
        result = 31 * result + (cogsOcrCod != null ? cogsOcrCod.hashCode() : 0);
        result = 31 * result + (ciOppLineN != null ? ciOppLineN.hashCode() : 0);
        result = 31 * result + (cogsAcct != null ? cogsAcct.hashCode() : 0);
        result = 31 * result + (chgAsmBoMw != null ? chgAsmBoMw.hashCode() : 0);
        result = 31 * result + (actDelDate != null ? actDelDate.hashCode() : 0);
        result = 31 * result + (ocrCode2 != null ? ocrCode2.hashCode() : 0);
        result = 31 * result + (ocrCode3 != null ? ocrCode3.hashCode() : 0);
        result = 31 * result + (ocrCode4 != null ? ocrCode4.hashCode() : 0);
        result = 31 * result + (ocrCode5 != null ? ocrCode5.hashCode() : 0);
        result = 31 * result + (taxDistSum != null ? taxDistSum.hashCode() : 0);
        result = 31 * result + (taxDistSfc != null ? taxDistSfc.hashCode() : 0);
        result = 31 * result + (taxDistSsc != null ? taxDistSsc.hashCode() : 0);
        result = 31 * result + (postTax != null ? postTax.hashCode() : 0);
        result = 31 * result + (excisable != null ? excisable.hashCode() : 0);
        result = 31 * result + (assblValue != null ? assblValue.hashCode() : 0);
        result = 31 * result + (rg23APart1 != null ? rg23APart1.hashCode() : 0);
        result = 31 * result + (rg23APart2 != null ? rg23APart2.hashCode() : 0);
        result = 31 * result + (rg23CPart1 != null ? rg23CPart1.hashCode() : 0);
        result = 31 * result + (rg23CPart2 != null ? rg23CPart2.hashCode() : 0);
        result = 31 * result + (cogsOcrCo2 != null ? cogsOcrCo2.hashCode() : 0);
        result = 31 * result + (cogsOcrCo3 != null ? cogsOcrCo3.hashCode() : 0);
        result = 31 * result + (cogsOcrCo4 != null ? cogsOcrCo4.hashCode() : 0);
        result = 31 * result + (cogsOcrCo5 != null ? cogsOcrCo5.hashCode() : 0);
        result = 31 * result + (lnExcised != null ? lnExcised.hashCode() : 0);
        result = 31 * result + (locCode != null ? locCode.hashCode() : 0);
        result = 31 * result + (stockValue != null ? stockValue.hashCode() : 0);
        result = 31 * result + (gpTtlBasPr != null ? gpTtlBasPr.hashCode() : 0);
        result = 31 * result + (unitMsr2 != null ? unitMsr2.hashCode() : 0);
        result = 31 * result + (numPerMsr2 != null ? numPerMsr2.hashCode() : 0);
        result = 31 * result + (specPrice != null ? specPrice.hashCode() : 0);
        result = 31 * result + (csTfIpi != null ? csTfIpi.hashCode() : 0);
        result = 31 * result + (csTfPis != null ? csTfPis.hashCode() : 0);
        result = 31 * result + (csTfCofins != null ? csTfCofins.hashCode() : 0);
        result = 31 * result + (exLineNo != null ? exLineNo.hashCode() : 0);
        result = 31 * result + (isSrvCall != null ? isSrvCall.hashCode() : 0);
        result = 31 * result + (pqtReqQty != null ? pqtReqQty.hashCode() : 0);
        result = 31 * result + (pqtReqDate != null ? pqtReqDate.hashCode() : 0);
        result = 31 * result + (pcDocType != null ? pcDocType.hashCode() : 0);
        result = 31 * result + (pcQuantity != null ? pcQuantity.hashCode() : 0);
        result = 31 * result + (linManClsd != null ? linManClsd.hashCode() : 0);
        result = 31 * result + (vatGrpSrc != null ? vatGrpSrc.hashCode() : 0);
        result = 31 * result + (noInvtryMv != null ? noInvtryMv.hashCode() : 0);
        result = 31 * result + (actBaseEnt != null ? actBaseEnt.hashCode() : 0);
        result = 31 * result + (actBaseLn != null ? actBaseLn.hashCode() : 0);
        result = 31 * result + (actBaseNum != null ? actBaseNum.hashCode() : 0);
        result = 31 * result + (openRtnQty != null ? openRtnQty.hashCode() : 0);
        result = 31 * result + (agrNo != null ? agrNo.hashCode() : 0);
        result = 31 * result + (agrLnNum != null ? agrLnNum.hashCode() : 0);
        result = 31 * result + (credOrigin != null ? credOrigin.hashCode() : 0);
        result = 31 * result + (surpluses != null ? surpluses.hashCode() : 0);
        result = 31 * result + (defBreak != null ? defBreak.hashCode() : 0);
        result = 31 * result + (shortages != null ? shortages.hashCode() : 0);
        result = 31 * result + (uomEntry != null ? uomEntry.hashCode() : 0);
        result = 31 * result + (uomEntry2 != null ? uomEntry2.hashCode() : 0);
        result = 31 * result + (uomCode != null ? uomCode.hashCode() : 0);
        result = 31 * result + (uomCode2 != null ? uomCode2.hashCode() : 0);
        result = 31 * result + (fromWhsCod != null ? fromWhsCod.hashCode() : 0);
        result = 31 * result + (needQty != null ? needQty.hashCode() : 0);
        result = 31 * result + (partRetire != null ? partRetire.hashCode() : 0);
        result = 31 * result + (retireQty != null ? retireQty.hashCode() : 0);
        result = 31 * result + (retireApc != null ? retireApc.hashCode() : 0);
        result = 31 * result + (retirApcfc != null ? retirApcfc.hashCode() : 0);
        result = 31 * result + (retirApcsc != null ? retirApcsc.hashCode() : 0);
        result = 31 * result + (invQty != null ? invQty.hashCode() : 0);
        result = 31 * result + (openInvQty != null ? openInvQty.hashCode() : 0);
        result = 31 * result + (enSetCost != null ? enSetCost.hashCode() : 0);
        result = 31 * result + (retCost != null ? retCost.hashCode() : 0);
        result = 31 * result + (incoterms != null ? incoterms.hashCode() : 0);
        result = 31 * result + (transMod != null ? transMod.hashCode() : 0);
        result = 31 * result + (lineVendor != null ? lineVendor.hashCode() : 0);
        result = 31 * result + (distribIs != null ? distribIs.hashCode() : 0);
        result = 31 * result + (isDistrb != null ? isDistrb.hashCode() : 0);
        result = 31 * result + (isDistrbFc != null ? isDistrbFc.hashCode() : 0);
        result = 31 * result + (isDistrbSc != null ? isDistrbSc.hashCode() : 0);
        result = 31 * result + (isByPrdct != null ? isByPrdct.hashCode() : 0);
        result = 31 * result + (itemType != null ? itemType.hashCode() : 0);
        result = 31 * result + (priceEdit != null ? priceEdit.hashCode() : 0);
        result = 31 * result + (prntLnNum != null ? prntLnNum.hashCode() : 0);
        result = 31 * result + (linePoPrss != null ? linePoPrss.hashCode() : 0);
        result = 31 * result + (freeChrgBp != null ? freeChrgBp.hashCode() : 0);
        result = 31 * result + (taxRelev != null ? taxRelev.hashCode() : 0);
        result = 31 * result + (legalText != null ? legalText.hashCode() : 0);
        result = 31 * result + (thirdParty != null ? thirdParty.hashCode() : 0);
        result = 31 * result + (licTradNum != null ? licTradNum.hashCode() : 0);
        result = 31 * result + (invQtyOnly != null ? invQtyOnly.hashCode() : 0);
        result = 31 * result + (unencReasn != null ? unencReasn.hashCode() : 0);
        result = 31 * result + (shipFromCo != null ? shipFromCo.hashCode() : 0);
        result = 31 * result + (shipFromDe != null ? shipFromDe.hashCode() : 0);
        result = 31 * result + (fisrtBin != null ? fisrtBin.hashCode() : 0);
        result = 31 * result + (allocBinC != null ? allocBinC.hashCode() : 0);
        result = 31 * result + (expType != null ? expType.hashCode() : 0);
        result = 31 * result + (expUuid != null ? expUuid.hashCode() : 0);
        result = 31 * result + (expOpType != null ? expOpType.hashCode() : 0);
        result = 31 * result + (diotNat != null ? diotNat.hashCode() : 0);
        result = 31 * result + (myFtype != null ? myFtype.hashCode() : 0);
        result = 31 * result + (itmTaxType != null ? itmTaxType.hashCode() : 0);
        result = 31 * result + (sacEntry != null ? sacEntry.hashCode() : 0);
        result = 31 * result + (uRdcode != null ? uRdcode.hashCode() : 0);
        result = 31 * result + (uTenkythuat != null ? uTenkythuat.hashCode() : 0);
        result = 31 * result + (uQcCode != null ? uQcCode.hashCode() : 0);
        result = 31 * result + (uQcLine != null ? uQcLine.hashCode() : 0);
        result = 31 * result + (uCknb != null ? uCknb.hashCode() : 0);
        result = 31 * result + (uKm != null ? uKm.hashCode() : 0);
        result = 31 * result + (uSendToHo != null ? uSendToHo.hashCode() : 0);
        result = 31 * result + (uDvTthuc != null ? uDvTthuc.hashCode() : 0);
        return result;
    }
}
