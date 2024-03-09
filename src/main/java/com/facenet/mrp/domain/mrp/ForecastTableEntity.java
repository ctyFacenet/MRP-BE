package com.facenet.mrp.domain.mrp;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "forecast_table", schema = "material_requirements_planning", catalog = "")
@EntityListeners(AuditingEntityListener.class)
public class ForecastTableEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "fc_code")
    private String fcCode;
    @Basic
    @Column(name = "fc_name")
    private String fcName;
    @Basic
    @Column(name = "time_start")
    private Date timeStart;
    @Basic
    @Column(name = "time_end")
    private Date timeEnd;
    @Basic
    @Column(name = "fc_mode")
    private String fcMode;
    @Basic
    @Column(name = "warehouse")
    private String warehouse;
    @Basic
    @Column(name = "fc_source")
    private String fcSource;
    @Basic
    @Column(name = "priority")
    private Integer priority;
    @Basic
    @Column(name = "note")
    private String note;
    @Basic
    @Column(name = "status")
    private Integer status;
    @Basic
    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;
    @Basic
    @Column(name = "created_at")
    @CreatedDate
    private Timestamp createdAt;
    @Basic
    @Column(name = "updated_by")
    @LastModifiedBy
    private String updatedBy;
    @Basic
    @Column(name = "updated_at")
    @LastModifiedDate
    private Timestamp updatedAt;
    @Basic
    @Column(name = "parent_fc")
    private int parentFc;
    @Basic
    @Column(name = "result")
    private String result;
    @Basic
    @Column(name = "is_active")
    private Integer isActive;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFcCode() {
        return fcCode;
    }

    public void setFcCode(String fcCode) {
        this.fcCode = fcCode;
    }

    public String getFcName() {
        return fcName;
    }

    public void setFcName(String fcName) {
        this.fcName = fcName;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getFcMode() {
        return fcMode;
    }

    public void setFcMode(String fcMode) {
        this.fcMode = fcMode;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getFcSource() {
        return fcSource;
    }

    public void setFcSource(String fcSource) {
        this.fcSource = fcSource;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getParentFc() {
        return parentFc;
    }

    public void setParentFc(int parentFc) {
        this.parentFc = parentFc;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ForecastTableEntity that = (ForecastTableEntity) o;

        if (id != that.id) return false;
        if (parentFc != that.parentFc) return false;
        if (fcCode != null ? !fcCode.equals(that.fcCode) : that.fcCode != null) return false;
        if (fcName != null ? !fcName.equals(that.fcName) : that.fcName != null) return false;
        if (timeStart != null ? !timeStart.equals(that.timeStart) : that.timeStart != null) return false;
        if (timeEnd != null ? !timeEnd.equals(that.timeEnd) : that.timeEnd != null) return false;
        if (fcMode != null ? !fcMode.equals(that.fcMode) : that.fcMode != null) return false;
        if (warehouse != null ? !warehouse.equals(that.warehouse) : that.warehouse != null) return false;
        if (fcSource != null ? !fcSource.equals(that.fcSource) : that.fcSource != null) return false;
        if (priority != null ? !priority.equals(that.priority) : that.priority != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (updatedBy != null ? !updatedBy.equals(that.updatedBy) : that.updatedBy != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;
        if (result != null ? !result.equals(that.result) : that.result != null) return false;
        if (isActive != null ? !isActive.equals(that.isActive) : that.isActive != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result1 = id;
        result1 = 31 * result1 + (fcCode != null ? fcCode.hashCode() : 0);
        result1 = 31 * result1 + (fcName != null ? fcName.hashCode() : 0);
        result1 = 31 * result1 + (timeStart != null ? timeStart.hashCode() : 0);
        result1 = 31 * result1 + (timeEnd != null ? timeEnd.hashCode() : 0);
        result1 = 31 * result1 + (fcMode != null ? fcMode.hashCode() : 0);
        result1 = 31 * result1 + (warehouse != null ? warehouse.hashCode() : 0);
        result1 = 31 * result1 + (fcSource != null ? fcSource.hashCode() : 0);
        result1 = 31 * result1 + (priority != null ? priority.hashCode() : 0);
        result1 = 31 * result1 + (note != null ? note.hashCode() : 0);
        result1 = 31 * result1 + (status != null ? status.hashCode() : 0);
        result1 = 31 * result1 + (createdBy != null ? createdBy.hashCode() : 0);
        result1 = 31 * result1 + (createdAt != null ? createdAt.hashCode() : 0);
        result1 = 31 * result1 + (updatedBy != null ? updatedBy.hashCode() : 0);
        result1 = 31 * result1 + (updatedAt != null ? updatedAt.hashCode() : 0);
        result1 = 31 * result1 + parentFc;
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (isActive != null ? isActive.hashCode() : 0);
        return result1;
    }
}
