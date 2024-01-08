package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.PurchaseHasRecommendationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseHasRecommendationRepository extends JpaRepository<PurchaseHasRecommendationEntity,Long> {

    @Query("select p from PurchaseHasRecommendationEntity p where p.isActive = true and p.purchaseRecommendationId = :purchaseRecommendationId")
    List<PurchaseHasRecommendationEntity> getPurchaseHasRecommendationEntity(@Param("purchaseRecommendationId") Integer purchaseRecommendationId);

    @Query("select p from PurchaseHasRecommendationEntity p where p.isActive = true and p.purchaseRecommendationId = :purchaseRecommendationId and p.batch = :batch")
    PurchaseHasRecommendationEntity getPurchaseHasRecommendationBatch(@Param("purchaseRecommendationId") Integer purchaseRecommendationId, @Param("batch") Integer batch);

    @Modifying
    @Query("update PurchaseHasRecommendationEntity p set p.status = 3 where p.purchaseRecommendationId = :purchaseRecommendationId and p.batch = :batch and p.isActive = true")
    int updateStatusPurchaseHasRecomment(@Param("purchaseRecommendationId") Integer purchaseRecommendationId,@Param("batch") Integer batch);
}
