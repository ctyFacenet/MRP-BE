package com.facenet.mrp.domain.mrp;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "result_mrp_json", schema = "material_requirements_planning", catalog = "")
@EntityListeners(AuditingEntityListener.class)
public class ResultMrpJsonEntity {
    private int mrpResultId;
    private String mrpCode;
    private String mrpSubCode;
    private String mrpPoCode;
    private String dataResultJson;
    private Byte isActive;
    private Timestamp createdAt;
    private String createdBy;
    private Timestamp updatedAt;
    private String updatedBy;

    @Id
    @Column(name = "mrp_result_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getMrpResultId() {
        return mrpResultId;
    }

    public void setMrpResultId(int mrpResultId) {
        this.mrpResultId = mrpResultId;
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
    @Column(name = "mrp_sub_code")
    public String getMrpSubCode() {
        return mrpSubCode;
    }

    public void setMrpSubCode(String mrpSubCode) {
        this.mrpSubCode = mrpSubCode;
    }

    @Basic
    @Column(name = "mrp_po_code")
    public String getMrpPoCode() {
        return mrpPoCode;
    }

    public void setMrpPoCode(String mrpPoCode) {
        this.mrpPoCode = mrpPoCode;
    }

    @Basic
    @Column(name = "data_result_json")
    public String getDataResultJson() {
        return dataResultJson;
    }

    public void setDataResultJson(String dataResultJson) {
        this.dataResultJson = dataResultJson;
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
    @Column(name = "created_at")
    @CreatedDate
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
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
    @Column(name = "updated_at")
    @LastModifiedDate
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultMrpJsonEntity that = (ResultMrpJsonEntity) o;
        return mrpResultId == that.mrpResultId && Objects.equals(mrpCode, that.mrpCode) && Objects.equals(mrpSubCode, that.mrpSubCode) && Objects.equals(mrpPoCode, that.mrpPoCode) && Objects.equals(dataResultJson, that.dataResultJson) && Objects.equals(isActive, that.isActive) && Objects.equals(createdAt, that.createdAt) && Objects.equals(createdBy, that.createdBy) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(updatedBy, that.updatedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mrpResultId, mrpCode, mrpSubCode, mrpPoCode, dataResultJson, isActive, createdAt, createdBy, updatedAt, updatedBy);
    }
}
