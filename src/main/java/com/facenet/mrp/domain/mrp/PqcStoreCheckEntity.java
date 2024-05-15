package com.facenet.mrp.domain.mrp;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;



@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "pqc_store_check")
public class PqcStoreCheckEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "check_date")
    private String checkDate;

    @Column(name = "import_date")
    private Date importDate;

    @Column(name = "check_person")
    private String checkPerson;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    private String lot;

    private String quatity;

    @Column(name = "quatity_store")
    private String quatityStore;

    @Column(name = "total_err")
    private String totalErr;

    @Column(name = "note")
    private String note;
    @Column(name = "quantity_store_sap")
    private String quantityStoreSap;
    @Column(name = "date_approve_sap")
    private String dateApproveSap;

    @Column(name = "note_approve")
    private String noteApprove;

    @Column(name = "his_string")
    private String hisString;
    @Column(name = "conclude")
    private String conclude;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "work_order_id")
    private Long workOrderId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "status_approve_sap")
    private String statusApproveSap;

    @Column(name = "id_approve_planing")
    private String idApprovePlaning;
    @Column(name = "color_name")
    private String colorName;

    @Column(name = "color_Code")
    private String colorCode;

    @PrePersist
    public void create() {
        updatedAt  = createdAt  = new Date();
    }

    @Transient
    String statusSap;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public String getCheckPerson() {
        return checkPerson;
    }

    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getQuatity() {
        return quatity;
    }

    public void setQuatity(String quatity) {
        this.quatity = quatity;
    }

    public String getQuatityStore() {
        return quatityStore;
    }

    public void setQuatityStore(String quatityStore) {
        this.quatityStore = quatityStore;
    }

    public String getTotalErr() {
        return totalErr;
    }

    public void setTotalErr(String totalErr) {
        this.totalErr = totalErr;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getQuantityStoreSap() {
        return quantityStoreSap;
    }

    public void setQuantityStoreSap(String quantityStoreSap) {
        this.quantityStoreSap = quantityStoreSap;
    }

    public String getDateApproveSap() {
        return dateApproveSap;
    }

    public void setDateApproveSap(String dateApproveSap) {
        this.dateApproveSap = dateApproveSap;
    }

    public String getNoteApprove() {
        return noteApprove;
    }

    public void setNoteApprove(String noteApprove) {
        this.noteApprove = noteApprove;
    }

    public String getHisString() {
        return hisString;
    }

    public void setHisString(String hisString) {
        this.hisString = hisString;
    }

    public String getConclude() {
        return conclude;
    }

    public void setConclude(String conclude) {
        this.conclude = conclude;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusApproveSap() {
        return statusApproveSap;
    }

    public void setStatusApproveSap(String statusApproveSap) {
        this.statusApproveSap = statusApproveSap;
    }

    public String getIdApprovePlaning() {
        return idApprovePlaning;
    }

    public void setIdApprovePlaning(String idApprovePlaning) {
        this.idApprovePlaning = idApprovePlaning;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getStatusSap() {
        return statusSap;
    }

    public void setStatusSap(String statusSap) {
        this.statusSap = statusSap;
    }
}
