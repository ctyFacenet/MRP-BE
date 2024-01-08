package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.PurchaseRecommendationEntity;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PurchaseRecommendationRepository extends JpaRepository<PurchaseRecommendationEntity, Integer> {
    boolean existsByMrpPoIdAndMrpSubCodeAndIsActiveTrue(String mrpPoId, String mrpSubCode);
    PurchaseRecommendationEntity findByPurchaseRecommendationIdAndIsActiveTrue(Integer id);
    PurchaseRecommendationEntity findByMrpSubCodeAndIsActiveTrue(String mrpSubCode);

    boolean existsByPurchaseRecommendationIdAndIsActiveTrue(Integer id);

    @Modifying
    @Query("update PurchaseRecommendationEntity p set p.status = :status where p.purchaseRecommendationId = :purchaseRecommendationId and p.isActive = true")
    int updateStatusPurchaseRecommend(@Param("purchaseRecommendationId") Integer purchaseRecommendationId, @Param("status") Integer status);

    @Query(value = "select p from PurchaseRecommendationEntity p where p.isActive = true and p.mrpPoId = :fcCode")
    PurchaseRecommendationEntity getPurchaseRecommendationEntityByFc(@Param("fcCode") String fcCode);

    boolean existsByMrpSubCodeAndIsActiveTrue(String mrpSubCode);

    @Modifying
    @Query("update PurchaseRecommendationEntity p set p.isActive = false where p.purchaseRecommendationId = :purchaseRecommendationId")
    int deletePurchaseRecommendation(@Param("purchaseRecommendationId") Integer purchaseRecommendationId);

    @Modifying
    @Query("update PurchaseRecommendationEntity p set p.status = :newStatus, p.updatedAt = current_time where p.purchaseRecommendationId = :purchaseRecommendationId and p.isActive = true")
    int changeStatus(@Param("purchaseRecommendationId") Integer purchaseRecommendationId, @Param("newStatus") int newStatus);
}
