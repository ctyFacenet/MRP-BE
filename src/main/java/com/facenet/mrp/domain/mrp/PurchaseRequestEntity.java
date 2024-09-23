package com.facenet.mrp.domain.mrp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "purchase_request", schema = "material_requirements_planning", catalog = "")
public class PurchaseRequestEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "pr_code")
    private String prCode;
    @Basic
    @Column(name = "mrp_code")
    private String mrpCode;
    @Basic
    @Column(name = "pr_create_user")
    private String prCreateUser;
    @Basic
    @Column(name = "so_code")
    private String soCode;
    @Basic
    @Column(name = "period")
    private String period;
    @Basic
    @Column(name = "pr_create_date")
    private Timestamp prCreateDate;
    @Basic
    @Column(name = "approval_date")
    private Timestamp approvalDate;
    @Basic
    @Column(name = "approval_user")
    private String approvalUser;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "deleted_at")
    private Timestamp deletedAt;
    @Basic
    @Column(name = "deleted_by")
    private String deletedBy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrCode() {
        return prCode;
    }

    public void setPrCode(String prCode) {
        this.prCode = prCode;
    }

    public String getMrpCode() {
        return mrpCode;
    }

    public void setMrpCode(String mrpCode) {
        this.mrpCode = mrpCode;
    }

    public String getPrCreateUser() {
        return prCreateUser;
    }

    public void setPrCreateUser(String prCreateUser) {
        this.prCreateUser = prCreateUser;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Timestamp getPrCreateDate() {
        return prCreateDate;
    }

    public void setPrCreateDate(Timestamp prCreateDate) {
        this.prCreateDate = prCreateDate;
    }

    public Timestamp getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Timestamp approval_date) {
        this.approvalDate = approval_date;
    }

    public String getApprovalUser() {
        return approvalUser;
    }

    public void setApprovalUser(String approvalUser) {
        this.approvalUser = approvalUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchaseRequestEntity that = (PurchaseRequestEntity) o;

        if (id != that.id) return false;
        if (prCode != null ? !prCode.equals(that.prCode) : that.prCode != null) return false;
        if (mrpCode != null ? !mrpCode.equals(that.mrpCode) : that.mrpCode != null) return false;
        if (prCreateUser != null ? !prCreateUser.equals(that.prCreateUser) : that.prCreateUser != null) return false;
        if (soCode != null ? !soCode.equals(that.soCode) : that.soCode != null) return false;
        if (period != null ? !period.equals(that.period) : that.period != null) return false;
        if (prCreateDate != null ? !prCreateDate.equals(that.prCreateDate) : that.prCreateDate != null) return false;
        if (approvalDate != null ? !approvalDate.equals(that.approvalDate) : that.approvalDate != null) return false;
        if (approvalUser != null ? !approvalUser.equals(that.approvalUser) : that.approvalUser != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (deletedAt != null ? !deletedAt.equals(that.deletedAt) : that.deletedAt != null) return false;
        if (deletedBy != null ? !deletedBy.equals(that.deletedBy) : that.deletedBy != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (prCode != null ? prCode.hashCode() : 0);
        result = 31 * result + (mrpCode != null ? mrpCode.hashCode() : 0);
        result = 31 * result + (prCreateUser != null ? prCreateUser.hashCode() : 0);
        result = 31 * result + (soCode != null ? soCode.hashCode() : 0);
        result = 31 * result + (period != null ? period.hashCode() : 0);
        result = 31 * result + (prCreateDate != null ? prCreateDate.hashCode() : 0);
        result = 31 * result + (approvalDate != null ? approvalDate.hashCode() : 0);
        result = 31 * result + (approvalUser != null ? approvalUser.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (deletedAt != null ? deletedAt.hashCode() : 0);
        result = 31 * result + (deletedBy != null ? deletedBy.hashCode() : 0);
        return result;
    }
}
