package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.ApprovalUserAuthorizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalUserAuthorizationRepository extends JpaRepository<ApprovalUserAuthorizationEntity, Integer> {
    List<ApprovalUserAuthorizationEntity> findByPurchaseRecommendationIdAndBatch(Integer purchaseRecommendationId, Integer batch);
}
