package com.facenet.mrp.domain.mrp;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "sap_on_order_duration_detail", schema = "material_requirements_planning", catalog = "")
@EntityListeners(AuditingEntityListener.class)
public class SapOnOrderDurationDetail {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
    @GenericGenerator(name = "seqGen", strategy = "increment")
    @Id
    @Column(name = "monitor_id", nullable = false)
    private Integer id;

    @Column(name = "fk_id", nullable = false,insertable = false, updatable = false)
    private Integer fkId;

    @Column(name = "fk_plan")
    private String fkPlan;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "fk_id", referencedColumnName = "id")
    private SapOnOrderSummary sapOnOrderSummary;

    @Column(name = "detail_due_date")
    private Date dueDate;

    @Column(name = "detail_quantity")
    private Double quantity;

    @Column(name = "note")
    private String note;

    @Column(name = "created_at", nullable = true, updatable = false)
    @CreatedDate
    private Timestamp createdAt;

    @Column(name = "created_by", nullable = true, length = 255)
    @CreatedBy
    private String createdBy;

    @Column(name = "updated_at", nullable = true)
    @LastModifiedDate
    private Timestamp updatedAt;

    @Column(name = "updated_by", nullable = true, length = 255)
    @LastModifiedBy
    private String updatedBy;

    @Column(name = "is_active")
    private Boolean isActive;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFkId() {
        return fkId;
    }

    public void setFkId(Integer fkId) {
        this.fkId = fkId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public SapOnOrderSummary getSapOnOrderSummary() {
        return sapOnOrderSummary;
    }

    public void setSapOnOrderSummary(SapOnOrderSummary sapOnOrderSummary) {
        this.sapOnOrderSummary = sapOnOrderSummary;
    }

    public String getFkPlan() {
        return fkPlan;
    }

    public void setFkPlan(String fkPlan) {
        this.fkPlan = fkPlan;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
