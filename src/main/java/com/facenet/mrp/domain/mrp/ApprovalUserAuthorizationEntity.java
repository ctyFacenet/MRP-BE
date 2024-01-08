package com.facenet.mrp.domain.mrp;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "approval_user_authorization", schema = "material_requirements_planning", catalog = "")
public class ApprovalUserAuthorizationEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeqGen")
    @GenericGenerator(name = "userSeqGen", strategy = "increment")
    @Id
    @Column(name = "approval_user_authorization_id")
    private Integer approvalUserAuthorizationId;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "purchase_recommendation_id")
    private Integer purchaseRecommendationId;
    @Basic
    @Column(name = "batch")
    private Integer batch;

    @Basic
    @Column(name = "is_accepted")
    private Boolean isAccepted = false;

    public Integer getApprovalUserAuthorizationId() {
        return approvalUserAuthorizationId;
    }

    public void setApprovalUserAuthorizationId(Integer approvalUserAuthorizationId) {
        this.approvalUserAuthorizationId = approvalUserAuthorizationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPurchaseRecommendationId() {
        return purchaseRecommendationId;
    }

    public void setPurchaseRecommendationId(Integer purchaseRecommendationId) {
        this.purchaseRecommendationId = purchaseRecommendationId;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApprovalUserAuthorizationEntity that = (ApprovalUserAuthorizationEntity) o;

        if (approvalUserAuthorizationId != null ? !approvalUserAuthorizationId.equals(that.approvalUserAuthorizationId) : that.approvalUserAuthorizationId != null)
            return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (purchaseRecommendationId != null ? !purchaseRecommendationId.equals(that.purchaseRecommendationId) : that.purchaseRecommendationId != null)
            return false;
        if (batch != null ? !batch.equals(that.batch) : that.batch != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = approvalUserAuthorizationId != null ? approvalUserAuthorizationId.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (purchaseRecommendationId != null ? purchaseRecommendationId.hashCode() : 0);
        result = 31 * result + (batch != null ? batch.hashCode() : 0);
        return result;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }
}
