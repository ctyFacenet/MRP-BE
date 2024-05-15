package com.facenet.mrp.domain.mrp;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="pqc_work_order")
@EntityListeners(AuditingEntityListener.class)
public class PqcWorkOrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="bom_version")
    private String bomVersion;

    private String duyetNhapKho;

    private String ganLK;

    @Column(name = "InKem")
    private String inKem;

    @Column(name="kiemtra_nvl_result")
    private String kiemtraNvlResult;

    @Column(name="kiemtra_nvl")
    private String kiemTraNvl;

    private String kiemTraLapLan;

    private String kimLoai;

    private String KTChiTietNhapKho;

    private String KTLoiSuaLai;

    private String KTQuangDien;

    private String KTQuangDienBTP;

    private String KTQuaTrinhSX;

    private String loHan;

    private String mauDanhGiaCLSP;

    private String nhua;

    @Column(name="planing_work_order_code")
    private String planingWorkOrderCode;

    @Column(name="planing_work_order_id")
    private int planingWorkOrderId;

    @Column(name="production_code")
    private String productionCode;

    @Column(name="production_name")
    private String productionName;

    @Column(name="purchase_order_code")
    private String purchaseOrderCode;

    @Column(name="rut_nghiem")
    private String rutNghiem;


    @Column(name="lot_number")
    private String lotNumber;

    @Column(name="rutnghiem_nvl_result")
    private String rutnghiemNvlResult;

    private String son;

    @Column(name="work_order_id")
    private String workOrderId;

    @Column(name="created_by")
    private String createdBy;

    @Column(name="status")
    private String status;

    @Column(name="quantity_plan")
    private String quantityPlan;

    @Column(name="branch_name")
    private String branchName;

    @Column(name="group_name")
    private String groupName;

    @Column(name = "profile_id")
    private String profileId;

    @Column(name = "profile_name")
    private String profileName;

    @Column(name = "profile_code")
    private String profileCode;

    @Column(name = "sap_wo")
    private String sapWo;

    @Column(name = "doc_url")
    private String uDocURL;

    @Column(name = "doc_url2")
    private String uDocURL2;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;


    @PrePersist
    void createdAt() {
        this.createdAt = this.updatedAt = new Date();
    }

    @Transient
    String createdDateStr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBomVersion() {
        return bomVersion;
    }

    public void setBomVersion(String bomVersion) {
        this.bomVersion = bomVersion;
    }

    public String getDuyetNhapKho() {
        return duyetNhapKho;
    }

    public void setDuyetNhapKho(String duyetNhapKho) {
        this.duyetNhapKho = duyetNhapKho;
    }

    public String getGanLK() {
        return ganLK;
    }

    public void setGanLK(String ganLK) {
        this.ganLK = ganLK;
    }

    public String getInKem() {
        return inKem;
    }

    public void setInKem(String inKem) {
        this.inKem = inKem;
    }

    public String getKiemtraNvlResult() {
        return kiemtraNvlResult;
    }

    public void setKiemtraNvlResult(String kiemtraNvlResult) {
        this.kiemtraNvlResult = kiemtraNvlResult;
    }

    public String getKiemTraNvl() {
        return kiemTraNvl;
    }

    public void setKiemTraNvl(String kiemTraNvl) {
        this.kiemTraNvl = kiemTraNvl;
    }

    public String getKiemTraLapLan() {
        return kiemTraLapLan;
    }

    public void setKiemTraLapLan(String kiemTraLapLan) {
        this.kiemTraLapLan = kiemTraLapLan;
    }

    public String getKimLoai() {
        return kimLoai;
    }

    public void setKimLoai(String kimLoai) {
        this.kimLoai = kimLoai;
    }

    public String getKTChiTietNhapKho() {
        return KTChiTietNhapKho;
    }

    public void setKTChiTietNhapKho(String KTChiTietNhapKho) {
        this.KTChiTietNhapKho = KTChiTietNhapKho;
    }

    public String getKTLoiSuaLai() {
        return KTLoiSuaLai;
    }

    public void setKTLoiSuaLai(String KTLoiSuaLai) {
        this.KTLoiSuaLai = KTLoiSuaLai;
    }

    public String getKTQuangDien() {
        return KTQuangDien;
    }

    public void setKTQuangDien(String KTQuangDien) {
        this.KTQuangDien = KTQuangDien;
    }

    public String getKTQuangDienBTP() {
        return KTQuangDienBTP;
    }

    public void setKTQuangDienBTP(String KTQuangDienBTP) {
        this.KTQuangDienBTP = KTQuangDienBTP;
    }

    public String getKTQuaTrinhSX() {
        return KTQuaTrinhSX;
    }

    public void setKTQuaTrinhSX(String KTQuaTrinhSX) {
        this.KTQuaTrinhSX = KTQuaTrinhSX;
    }

    public String getLoHan() {
        return loHan;
    }

    public void setLoHan(String loHan) {
        this.loHan = loHan;
    }

    public String getMauDanhGiaCLSP() {
        return mauDanhGiaCLSP;
    }

    public void setMauDanhGiaCLSP(String mauDanhGiaCLSP) {
        this.mauDanhGiaCLSP = mauDanhGiaCLSP;
    }

    public String getNhua() {
        return nhua;
    }

    public void setNhua(String nhua) {
        this.nhua = nhua;
    }

    public String getPlaningWorkOrderCode() {
        return planingWorkOrderCode;
    }

    public void setPlaningWorkOrderCode(String planingWorkOrderCode) {
        this.planingWorkOrderCode = planingWorkOrderCode;
    }

    public int getPlaningWorkOrderId() {
        return planingWorkOrderId;
    }

    public void setPlaningWorkOrderId(int planingWorkOrderId) {
        this.planingWorkOrderId = planingWorkOrderId;
    }

    public String getProductionCode() {
        return productionCode;
    }

    public void setProductionCode(String productionCode) {
        this.productionCode = productionCode;
    }

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }

    public String getPurchaseOrderCode() {
        return purchaseOrderCode;
    }

    public void setPurchaseOrderCode(String purchaseOrderCode) {
        this.purchaseOrderCode = purchaseOrderCode;
    }

    public String getRutNghiem() {
        return rutNghiem;
    }

    public void setRutNghiem(String rutNghiem) {
        this.rutNghiem = rutNghiem;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getRutnghiemNvlResult() {
        return rutnghiemNvlResult;
    }

    public void setRutnghiemNvlResult(String rutnghiemNvlResult) {
        this.rutnghiemNvlResult = rutnghiemNvlResult;
    }

    public String getSon() {
        return son;
    }

    public void setSon(String son) {
        this.son = son;
    }

    public String getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQuantityPlan() {
        return quantityPlan;
    }

    public void setQuantityPlan(String quantityPlan) {
        this.quantityPlan = quantityPlan;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileCode() {
        return profileCode;
    }

    public void setProfileCode(String profileCode) {
        this.profileCode = profileCode;
    }

    public String getSapWo() {
        return sapWo;
    }

    public void setSapWo(String sapWo) {
        this.sapWo = sapWo;
    }

    public String getuDocURL() {
        return uDocURL;
    }

    public void setuDocURL(String uDocURL) {
        this.uDocURL = uDocURL;
    }

    public String getuDocURL2() {
        return uDocURL2;
    }

    public void setuDocURL2(String uDocURL2) {
        this.uDocURL2 = uDocURL2;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }
}
