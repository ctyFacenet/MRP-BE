package com.facenet.mrp.domain.sap;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "OITW")
@IdClass(OitwEntityPK.class)
public class OitwEntity {
    private String itemCode;
    private String whsCode;

    private OwhsEntity owhsEntity;
    private Long onHand;
    private Long isCommited;
    private Long onOrder;
    private Long consig;
    private Long counted;
    private String wasCounted;
    private Short userSign;
    private Long minStock;
    private Long maxStock;
    private Long minOrder;
    private Long avgPrice;
    private String locked;
    private String balInvntAc;
    private String saleCostAc;
    private String transferAc;
    private String revenuesAc;
    private String varianceAc;
    private String decreasAc;
    private String increasAc;
    private String returnAc;
    private String expensesAc;
    private String euRevenuAc;
    private String euExpensAc;
    private String frRevenuAc;
    private String frExpensAc;
    private String exmptIncom;
    private String priceDifAc;
    private String exchangeAc;
    private String balanceAcc;
    private String purchaseAc;
    private String paReturnAc;
    private String purchOfsAc;
    private String shpdGdsAct;
    private String vatRevAct;
    private Long stockValue;
    private String decresGlAc;
    private String incresGlAc;
    private String stokRvlAct;
    private String stkOffsAct;
    private String wipAcct;
    private String wipVarAcct;
    private String costRvlAct;
    private String cstOffsAct;
    private String expClrAct;
    private String expOfstAct;
    private String object;
    private Integer logInstanc;
    private Date createDate;
    private Short userSign2;
    private Date updateDate;
    private String arcmAct;
    private String arcmFrnAct;
    private String arcmeuAct;
    private String arcmExpAct;
    private String apcmAct;
    private String apcmFrnAct;
    private String apcmeuAct;
    private String revRetAct;
    private String negStckAct;
    private String stkInTnAct;
    private String purBalAct;
    private String whICenAct;
    private String whOCenAct;
    private String wipOffset;
    private String stockOffst;
    private Integer dftBinAbs;
    private String dftBinEnfd;
    private String freezed;
    private Integer freezeDoc;
    private String freeChrgSa;
    private String freeChrgPu;

    @Id
    @Column(name = "ItemCode")
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Id
    @Column(name = "WhsCode", insertable = false, updatable = false)
    public String getWhsCode() {
        return whsCode;
    }

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "WhsCode", referencedColumnName = "WhsCode", insertable = false, updatable = false)
    public OwhsEntity getOwhsEntity() {
        return owhsEntity;
    }

    public void setWhsCode(String whsCode) {
        this.whsCode = whsCode;
    }

    public void setOwhsEntity(OwhsEntity owhsEntity) {
        this.owhsEntity = owhsEntity;
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
    @Column(name = "Consig")
    public Long getConsig() {
        return consig;
    }

    public void setConsig(Long consig) {
        this.consig = consig;
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
    @Column(name = "WasCounted")
    public String getWasCounted() {
        return wasCounted;
    }

    public void setWasCounted(String wasCounted) {
        this.wasCounted = wasCounted;
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
    @Column(name = "MinStock")
    public Long getMinStock() {
        return minStock;
    }

    public void setMinStock(Long minStock) {
        this.minStock = minStock;
    }

    @Basic
    @Column(name = "MaxStock")
    public Long getMaxStock() {
        return maxStock;
    }

    public void setMaxStock(Long maxStock) {
        this.maxStock = maxStock;
    }

    @Basic
    @Column(name = "MinOrder")
    public Long getMinOrder() {
        return minOrder;
    }

    public void setMinOrder(Long minOrder) {
        this.minOrder = minOrder;
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
    @Column(name = "Locked")
    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    @Basic
    @Column(name = "BalInvntAc")
    public String getBalInvntAc() {
        return balInvntAc;
    }

    public void setBalInvntAc(String balInvntAc) {
        this.balInvntAc = balInvntAc;
    }

    @Basic
    @Column(name = "SaleCostAc")
    public String getSaleCostAc() {
        return saleCostAc;
    }

    public void setSaleCostAc(String saleCostAc) {
        this.saleCostAc = saleCostAc;
    }

    @Basic
    @Column(name = "TransferAc")
    public String getTransferAc() {
        return transferAc;
    }

    public void setTransferAc(String transferAc) {
        this.transferAc = transferAc;
    }

    @Basic
    @Column(name = "RevenuesAc")
    public String getRevenuesAc() {
        return revenuesAc;
    }

    public void setRevenuesAc(String revenuesAc) {
        this.revenuesAc = revenuesAc;
    }

    @Basic
    @Column(name = "VarianceAc")
    public String getVarianceAc() {
        return varianceAc;
    }

    public void setVarianceAc(String varianceAc) {
        this.varianceAc = varianceAc;
    }

    @Basic
    @Column(name = "DecreasAc")
    public String getDecreasAc() {
        return decreasAc;
    }

    public void setDecreasAc(String decreasAc) {
        this.decreasAc = decreasAc;
    }

    @Basic
    @Column(name = "IncreasAc")
    public String getIncreasAc() {
        return increasAc;
    }

    public void setIncreasAc(String increasAc) {
        this.increasAc = increasAc;
    }

    @Basic
    @Column(name = "ReturnAc")
    public String getReturnAc() {
        return returnAc;
    }

    public void setReturnAc(String returnAc) {
        this.returnAc = returnAc;
    }

    @Basic
    @Column(name = "ExpensesAc")
    public String getExpensesAc() {
        return expensesAc;
    }

    public void setExpensesAc(String expensesAc) {
        this.expensesAc = expensesAc;
    }

    @Basic
    @Column(name = "EURevenuAc")
    public String getEuRevenuAc() {
        return euRevenuAc;
    }

    public void setEuRevenuAc(String euRevenuAc) {
        this.euRevenuAc = euRevenuAc;
    }

    @Basic
    @Column(name = "EUExpensAc")
    public String getEuExpensAc() {
        return euExpensAc;
    }

    public void setEuExpensAc(String euExpensAc) {
        this.euExpensAc = euExpensAc;
    }

    @Basic
    @Column(name = "FrRevenuAc")
    public String getFrRevenuAc() {
        return frRevenuAc;
    }

    public void setFrRevenuAc(String frRevenuAc) {
        this.frRevenuAc = frRevenuAc;
    }

    @Basic
    @Column(name = "FrExpensAc")
    public String getFrExpensAc() {
        return frExpensAc;
    }

    public void setFrExpensAc(String frExpensAc) {
        this.frExpensAc = frExpensAc;
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
    @Column(name = "PriceDifAc")
    public String getPriceDifAc() {
        return priceDifAc;
    }

    public void setPriceDifAc(String priceDifAc) {
        this.priceDifAc = priceDifAc;
    }

    @Basic
    @Column(name = "ExchangeAc")
    public String getExchangeAc() {
        return exchangeAc;
    }

    public void setExchangeAc(String exchangeAc) {
        this.exchangeAc = exchangeAc;
    }

    @Basic
    @Column(name = "BalanceAcc")
    public String getBalanceAcc() {
        return balanceAcc;
    }

    public void setBalanceAcc(String balanceAcc) {
        this.balanceAcc = balanceAcc;
    }

    @Basic
    @Column(name = "PurchaseAc")
    public String getPurchaseAc() {
        return purchaseAc;
    }

    public void setPurchaseAc(String purchaseAc) {
        this.purchaseAc = purchaseAc;
    }

    @Basic
    @Column(name = "PAReturnAc")
    public String getPaReturnAc() {
        return paReturnAc;
    }

    public void setPaReturnAc(String paReturnAc) {
        this.paReturnAc = paReturnAc;
    }

    @Basic
    @Column(name = "PurchOfsAc")
    public String getPurchOfsAc() {
        return purchOfsAc;
    }

    public void setPurchOfsAc(String purchOfsAc) {
        this.purchOfsAc = purchOfsAc;
    }

    @Basic
    @Column(name = "ShpdGdsAct")
    public String getShpdGdsAct() {
        return shpdGdsAct;
    }

    public void setShpdGdsAct(String shpdGdsAct) {
        this.shpdGdsAct = shpdGdsAct;
    }

    @Basic
    @Column(name = "VatRevAct")
    public String getVatRevAct() {
        return vatRevAct;
    }

    public void setVatRevAct(String vatRevAct) {
        this.vatRevAct = vatRevAct;
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
    @Column(name = "DecresGlAc")
    public String getDecresGlAc() {
        return decresGlAc;
    }

    public void setDecresGlAc(String decresGlAc) {
        this.decresGlAc = decresGlAc;
    }

    @Basic
    @Column(name = "IncresGlAc")
    public String getIncresGlAc() {
        return incresGlAc;
    }

    public void setIncresGlAc(String incresGlAc) {
        this.incresGlAc = incresGlAc;
    }

    @Basic
    @Column(name = "StokRvlAct")
    public String getStokRvlAct() {
        return stokRvlAct;
    }

    public void setStokRvlAct(String stokRvlAct) {
        this.stokRvlAct = stokRvlAct;
    }

    @Basic
    @Column(name = "StkOffsAct")
    public String getStkOffsAct() {
        return stkOffsAct;
    }

    public void setStkOffsAct(String stkOffsAct) {
        this.stkOffsAct = stkOffsAct;
    }

    @Basic
    @Column(name = "WipAcct")
    public String getWipAcct() {
        return wipAcct;
    }

    public void setWipAcct(String wipAcct) {
        this.wipAcct = wipAcct;
    }

    @Basic
    @Column(name = "WipVarAcct")
    public String getWipVarAcct() {
        return wipVarAcct;
    }

    public void setWipVarAcct(String wipVarAcct) {
        this.wipVarAcct = wipVarAcct;
    }

    @Basic
    @Column(name = "CostRvlAct")
    public String getCostRvlAct() {
        return costRvlAct;
    }

    public void setCostRvlAct(String costRvlAct) {
        this.costRvlAct = costRvlAct;
    }

    @Basic
    @Column(name = "CstOffsAct")
    public String getCstOffsAct() {
        return cstOffsAct;
    }

    public void setCstOffsAct(String cstOffsAct) {
        this.cstOffsAct = cstOffsAct;
    }

    @Basic
    @Column(name = "ExpClrAct")
    public String getExpClrAct() {
        return expClrAct;
    }

    public void setExpClrAct(String expClrAct) {
        this.expClrAct = expClrAct;
    }

    @Basic
    @Column(name = "ExpOfstAct")
    public String getExpOfstAct() {
        return expOfstAct;
    }

    public void setExpOfstAct(String expOfstAct) {
        this.expOfstAct = expOfstAct;
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
    @Column(name = "logInstanc")
    public Integer getLogInstanc() {
        return logInstanc;
    }

    public void setLogInstanc(Integer logInstanc) {
        this.logInstanc = logInstanc;
    }

    @Basic
    @Column(name = "createDate")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "userSign2")
    public Short getUserSign2() {
        return userSign2;
    }

    public void setUserSign2(Short userSign2) {
        this.userSign2 = userSign2;
    }

    @Basic
    @Column(name = "updateDate")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Basic
    @Column(name = "ARCMAct")
    public String getArcmAct() {
        return arcmAct;
    }

    public void setArcmAct(String arcmAct) {
        this.arcmAct = arcmAct;
    }

    @Basic
    @Column(name = "ARCMFrnAct")
    public String getArcmFrnAct() {
        return arcmFrnAct;
    }

    public void setArcmFrnAct(String arcmFrnAct) {
        this.arcmFrnAct = arcmFrnAct;
    }

    @Basic
    @Column(name = "ARCMEUAct")
    public String getArcmeuAct() {
        return arcmeuAct;
    }

    public void setArcmeuAct(String arcmeuAct) {
        this.arcmeuAct = arcmeuAct;
    }

    @Basic
    @Column(name = "ARCMExpAct")
    public String getArcmExpAct() {
        return arcmExpAct;
    }

    public void setArcmExpAct(String arcmExpAct) {
        this.arcmExpAct = arcmExpAct;
    }

    @Basic
    @Column(name = "APCMAct")
    public String getApcmAct() {
        return apcmAct;
    }

    public void setApcmAct(String apcmAct) {
        this.apcmAct = apcmAct;
    }

    @Basic
    @Column(name = "APCMFrnAct")
    public String getApcmFrnAct() {
        return apcmFrnAct;
    }

    public void setApcmFrnAct(String apcmFrnAct) {
        this.apcmFrnAct = apcmFrnAct;
    }

    @Basic
    @Column(name = "APCMEUAct")
    public String getApcmeuAct() {
        return apcmeuAct;
    }

    public void setApcmeuAct(String apcmeuAct) {
        this.apcmeuAct = apcmeuAct;
    }

    @Basic
    @Column(name = "RevRetAct")
    public String getRevRetAct() {
        return revRetAct;
    }

    public void setRevRetAct(String revRetAct) {
        this.revRetAct = revRetAct;
    }

    @Basic
    @Column(name = "NegStckAct")
    public String getNegStckAct() {
        return negStckAct;
    }

    public void setNegStckAct(String negStckAct) {
        this.negStckAct = negStckAct;
    }

    @Basic
    @Column(name = "StkInTnAct")
    public String getStkInTnAct() {
        return stkInTnAct;
    }

    public void setStkInTnAct(String stkInTnAct) {
        this.stkInTnAct = stkInTnAct;
    }

    @Basic
    @Column(name = "PurBalAct")
    public String getPurBalAct() {
        return purBalAct;
    }

    public void setPurBalAct(String purBalAct) {
        this.purBalAct = purBalAct;
    }

    @Basic
    @Column(name = "WhICenAct")
    public String getWhICenAct() {
        return whICenAct;
    }

    public void setWhICenAct(String whICenAct) {
        this.whICenAct = whICenAct;
    }

    @Basic
    @Column(name = "WhOCenAct")
    public String getWhOCenAct() {
        return whOCenAct;
    }

    public void setWhOCenAct(String whOCenAct) {
        this.whOCenAct = whOCenAct;
    }

    @Basic
    @Column(name = "WipOffset")
    public String getWipOffset() {
        return wipOffset;
    }

    public void setWipOffset(String wipOffset) {
        this.wipOffset = wipOffset;
    }

    @Basic
    @Column(name = "StockOffst")
    public String getStockOffst() {
        return stockOffst;
    }

    public void setStockOffst(String stockOffst) {
        this.stockOffst = stockOffst;
    }

    @Basic
    @Column(name = "DftBinAbs")
    public Integer getDftBinAbs() {
        return dftBinAbs;
    }

    public void setDftBinAbs(Integer dftBinAbs) {
        this.dftBinAbs = dftBinAbs;
    }

    @Basic
    @Column(name = "DftBinEnfd")
    public String getDftBinEnfd() {
        return dftBinEnfd;
    }

    public void setDftBinEnfd(String dftBinEnfd) {
        this.dftBinEnfd = dftBinEnfd;
    }

    @Basic
    @Column(name = "Freezed")
    public String getFreezed() {
        return freezed;
    }

    public void setFreezed(String freezed) {
        this.freezed = freezed;
    }

    @Basic
    @Column(name = "FreezeDoc")
    public Integer getFreezeDoc() {
        return freezeDoc;
    }

    public void setFreezeDoc(Integer freezeDoc) {
        this.freezeDoc = freezeDoc;
    }

    @Basic
    @Column(name = "FreeChrgSA")
    public String getFreeChrgSa() {
        return freeChrgSa;
    }

    public void setFreeChrgSa(String freeChrgSa) {
        this.freeChrgSa = freeChrgSa;
    }

    @Basic
    @Column(name = "FreeChrgPU")
    public String getFreeChrgPu() {
        return freeChrgPu;
    }

    public void setFreeChrgPu(String freeChrgPu) {
        this.freeChrgPu = freeChrgPu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OitwEntity that = (OitwEntity) o;
        return Objects.equals(itemCode, that.itemCode) && Objects.equals(whsCode, that.whsCode) && Objects.equals(onHand, that.onHand) && Objects.equals(isCommited, that.isCommited) && Objects.equals(onOrder, that.onOrder) && Objects.equals(consig, that.consig) && Objects.equals(counted, that.counted) && Objects.equals(wasCounted, that.wasCounted) && Objects.equals(userSign, that.userSign) && Objects.equals(minStock, that.minStock) && Objects.equals(maxStock, that.maxStock) && Objects.equals(minOrder, that.minOrder) && Objects.equals(avgPrice, that.avgPrice) && Objects.equals(locked, that.locked) && Objects.equals(balInvntAc, that.balInvntAc) && Objects.equals(saleCostAc, that.saleCostAc) && Objects.equals(transferAc, that.transferAc) && Objects.equals(revenuesAc, that.revenuesAc) && Objects.equals(varianceAc, that.varianceAc) && Objects.equals(decreasAc, that.decreasAc) && Objects.equals(increasAc, that.increasAc) && Objects.equals(returnAc, that.returnAc) && Objects.equals(expensesAc, that.expensesAc) && Objects.equals(euRevenuAc, that.euRevenuAc) && Objects.equals(euExpensAc, that.euExpensAc) && Objects.equals(frRevenuAc, that.frRevenuAc) && Objects.equals(frExpensAc, that.frExpensAc) && Objects.equals(exmptIncom, that.exmptIncom) && Objects.equals(priceDifAc, that.priceDifAc) && Objects.equals(exchangeAc, that.exchangeAc) && Objects.equals(balanceAcc, that.balanceAcc) && Objects.equals(purchaseAc, that.purchaseAc) && Objects.equals(paReturnAc, that.paReturnAc) && Objects.equals(purchOfsAc, that.purchOfsAc) && Objects.equals(shpdGdsAct, that.shpdGdsAct) && Objects.equals(vatRevAct, that.vatRevAct) && Objects.equals(stockValue, that.stockValue) && Objects.equals(decresGlAc, that.decresGlAc) && Objects.equals(incresGlAc, that.incresGlAc) && Objects.equals(stokRvlAct, that.stokRvlAct) && Objects.equals(stkOffsAct, that.stkOffsAct) && Objects.equals(wipAcct, that.wipAcct) && Objects.equals(wipVarAcct, that.wipVarAcct) && Objects.equals(costRvlAct, that.costRvlAct) && Objects.equals(cstOffsAct, that.cstOffsAct) && Objects.equals(expClrAct, that.expClrAct) && Objects.equals(expOfstAct, that.expOfstAct) && Objects.equals(object, that.object) && Objects.equals(logInstanc, that.logInstanc) && Objects.equals(createDate, that.createDate) && Objects.equals(userSign2, that.userSign2) && Objects.equals(updateDate, that.updateDate) && Objects.equals(arcmAct, that.arcmAct) && Objects.equals(arcmFrnAct, that.arcmFrnAct) && Objects.equals(arcmeuAct, that.arcmeuAct) && Objects.equals(arcmExpAct, that.arcmExpAct) && Objects.equals(apcmAct, that.apcmAct) && Objects.equals(apcmFrnAct, that.apcmFrnAct) && Objects.equals(apcmeuAct, that.apcmeuAct) && Objects.equals(revRetAct, that.revRetAct) && Objects.equals(negStckAct, that.negStckAct) && Objects.equals(stkInTnAct, that.stkInTnAct) && Objects.equals(purBalAct, that.purBalAct) && Objects.equals(whICenAct, that.whICenAct) && Objects.equals(whOCenAct, that.whOCenAct) && Objects.equals(wipOffset, that.wipOffset) && Objects.equals(stockOffst, that.stockOffst) && Objects.equals(dftBinAbs, that.dftBinAbs) && Objects.equals(dftBinEnfd, that.dftBinEnfd) && Objects.equals(freezed, that.freezed) && Objects.equals(freezeDoc, that.freezeDoc) && Objects.equals(freeChrgSa, that.freeChrgSa) && Objects.equals(freeChrgPu, that.freeChrgPu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemCode, whsCode, onHand, isCommited, onOrder, consig, counted, wasCounted, userSign, minStock, maxStock, minOrder, avgPrice, locked, balInvntAc, saleCostAc, transferAc, revenuesAc, varianceAc, decreasAc, increasAc, returnAc, expensesAc, euRevenuAc, euExpensAc, frRevenuAc, frExpensAc, exmptIncom, priceDifAc, exchangeAc, balanceAcc, purchaseAc, paReturnAc, purchOfsAc, shpdGdsAct, vatRevAct, stockValue, decresGlAc, incresGlAc, stokRvlAct, stkOffsAct, wipAcct, wipVarAcct, costRvlAct, cstOffsAct, expClrAct, expOfstAct, object, logInstanc, createDate, userSign2, updateDate, arcmAct, arcmFrnAct, arcmeuAct, arcmExpAct, apcmAct, apcmFrnAct, apcmeuAct, revRetAct, negStckAct, stkInTnAct, purBalAct, whICenAct, whOCenAct, wipOffset, stockOffst, dftBinAbs, dftBinEnfd, freezed, freezeDoc, freeChrgSa, freeChrgPu);
    }
}
