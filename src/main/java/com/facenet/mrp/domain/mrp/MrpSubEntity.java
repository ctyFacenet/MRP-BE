package com.facenet.mrp.domain.mrp;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "mrp_sub", schema = "material_requirements_planning", catalog = "")
@EntityListeners(AuditingEntityListener.class)
public class MrpSubEntity {
    private int mrpId;
    private String mrpCode;
    private String mrpDescription;
    private String mrpSubCode;
    private String analysisType;
    private Integer analysisKind;
    private Date startTime;
    private Date endTime;
    private String sortType;
    private String analysisSource;
    private String warehouseAnalysis;
    private String items;
    private Byte status;
    private Byte isActive;
    private String createdBy;
    private Timestamp createdAt;
    private String updatedBy;
    private Timestamp updatedAt;
    private String mrpPoId;
    private String analysisMode;

    @Id
    @Column(name = "mrp_id")
    public int getMrpId() {
        return mrpId;
    }

    public void setMrpId(int mrpId) {
        this.mrpId = mrpId;
    }

    @Basic
    @Column(name = "mrp_code")
    public String getMrpCode() {
        return mrpCode;
    }

    public void setMrpCode(String mrpCode) {
        this.mrpCode = mrpCode;
    }

    @Basic
    @Column(name = "mrp_description")
    public String getMrpDescription() {
        return mrpDescription;
    }

    public void setMrpDescription(String mrpDescription) {
        this.mrpDescription = mrpDescription;
    }

    @Basic
    @Column(name = "mrp_sub_code")
    public String getMrpSubCode() {
        return mrpSubCode;
    }

    public void setMrpSubCode(String mrpSubCode) {
        this.mrpSubCode = mrpSubCode;
    }

    @Basic
    @Column(name = "analysis_type")
    public String getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(String analysisType) {
        this.analysisType = analysisType;
    }

    @Basic
    @Column(name = "start_time")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "sort_type")
    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    @Basic
    @Column(name = "analysis_source")
    public String getAnalysisSource() {
        return analysisSource;
    }

    public void setAnalysisSource(String analysisSource) {
        this.analysisSource = analysisSource;
    }

    @Basic
    @Column(name = "warehouse_analysis")
    public String getWarehouseAnalysis() {
        return warehouseAnalysis;
    }

    public void setWarehouseAnalysis(String warehouseAnalysis) {
        this.warehouseAnalysis = warehouseAnalysis;
    }

    @Basic
    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "is_active")
    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    @Basic
    @Column(name = "created_by")
    @CreatedBy
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "created_at")
    @CreatedDate
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "updated_by")
    @LastModifiedBy
    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Basic
    @Column(name = "updated_at")
    @LastModifiedDate
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Basic
    @Column(name = "mrp_po_id")
    public String getMrpPoId() {
        return mrpPoId;
    }

    public void setMrpPoId(String mrpPoId) {
        this.mrpPoId = mrpPoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MrpSubEntity that = (MrpSubEntity) o;
        return mrpId == that.mrpId && Objects.equals(mrpCode, that.mrpCode) && Objects.equals(mrpDescription, that.mrpDescription) && Objects.equals(mrpSubCode, that.mrpSubCode) && Objects.equals(analysisType, that.analysisType) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(sortType, that.sortType) && Objects.equals(analysisSource, that.analysisSource) && Objects.equals(warehouseAnalysis, that.warehouseAnalysis) && Objects.equals(status, that.status) && Objects.equals(isActive, that.isActive) && Objects.equals(createdBy, that.createdBy) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedBy, that.updatedBy) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(mrpPoId, that.mrpPoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mrpId, mrpCode, mrpDescription, mrpSubCode, analysisType, startTime, endTime, sortType, analysisSource, warehouseAnalysis, status, isActive, createdBy, createdAt, updatedBy, updatedAt, mrpPoId);
    }

    @Basic
    @Column(name = "analysis_mode")
    public String getAnalysisMode() {
        return analysisMode;
    }

    public void setAnalysisMode(String analysisMode) {
        this.analysisMode = analysisMode;
    }

    @Basic
    @Column(name = "analysis_kind")
    public Integer getAnalysisKind() {
        return analysisKind;
    }

    public void setAnalysisKind(Integer analysisKind) {
        this.analysisKind = analysisKind;
    }

    @Basic
    @Column(name = "items")
    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }
}
