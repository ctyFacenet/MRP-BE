package com.facenet.mrp.domain.mrp;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "mrp", schema = "material_requirements_planning", catalog = "")
@EntityListeners(AuditingEntityListener.class)
public class MrpEntity implements Serializable {
    private int mrpId;
    private String mrpCode;
    private String mrpDescription;
    private Date startTime;
    private Date endTime;
    private Byte status;
    private Byte isActive;
    private String createdBy;
    private Timestamp createdAt;
    private String updatedBy;
    private Timestamp updatedAt;
    private String mrpPoId;
    private Date lastAccess;

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

    @Basic
    @Column(name = "last_access")
    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MrpEntity mrp = (MrpEntity) o;
        return mrpId == mrp.mrpId && Objects.equals(mrpCode, mrp.mrpCode) && Objects.equals(mrpDescription, mrp.mrpDescription) && Objects.equals(startTime, mrp.startTime) && Objects.equals(endTime, mrp.endTime) && Objects.equals(status, mrp.status) && Objects.equals(isActive, mrp.isActive) && Objects.equals(createdBy, mrp.createdBy) && Objects.equals(createdAt, mrp.createdAt) && Objects.equals(updatedBy, mrp.updatedBy) && Objects.equals(updatedAt, mrp.updatedAt) && Objects.equals(mrpPoId, mrp.mrpPoId) && Objects.equals(lastAccess, mrp.lastAccess);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mrpId, mrpCode, mrpDescription, startTime, endTime, status, isActive, createdBy, createdAt, updatedBy, updatedAt, mrpPoId, lastAccess);
    }
}
