package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.PurchaseRecommendationBatch;
import com.facenet.mrp.domain.mrp.PurchaseRecommendationDetailEntity;
import com.facenet.mrp.domain.mrp.PurchaseRecommendationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRecommendationBatchRepository extends JpaRepository<PurchaseRecommendationBatch,Long> {
    @Query(value = "select max(p.batch) from PurchaseRecommendationBatch p join PurchaseRecommendationDetailEntity pd on p.purchaseRecommendationDetailId = pd.purchaseRecommendationDetailId where p.isActive = true and pd.isActive = true and pd.purchaseRecommendation.purchaseRecommendationId = :purchaseRecommenId")
    Integer maxBatch(@Param("purchaseRecommenId") Integer purchaseRecommenId);

    @Query(value = "select p from PurchaseRecommendationBatch p join PurchaseRecommendationDetailEntity pd on p.purchaseRecommendationDetailId = pd.purchaseRecommendationDetailId where p.isActive = true and pd.isActive = true and pd.purchaseRecommendation.purchaseRecommendationId = :purchaseRecommenId and p.batch = :batch and p.status in (2,4)")
    List<PurchaseRecommendationBatch> getListBatch(@Param("purchaseRecommenId") Integer purchaseRecommenId,@Param("batch") Integer batch);

    @Query(value = "select p from PurchaseRecommendationBatch p " +
        "join PurchaseRecommendationDetailEntity pd on p.purchaseRecommendationDetailId = pd.purchaseRecommendationDetailId " +
        "where p.isActive = true and pd.isActive = true " +
        "and pd.purchaseRecommendation.purchaseRecommendationId = :purchaseRecommenId and p.status in (2,4)")
    List<PurchaseRecommendationBatch> getListBatch(@Param("purchaseRecommenId") Integer purchaseRecommenId);

    @Modifying
    @Query(value = "update purchase_recommendation_batch as p join purchase_recommendation_detail as pd on p.purchase_recommendation_detail_id = pd.purchase_recommendation_detail_id set p.note = :note, p.status = :status where p.is_active = true and pd.is_active = true and pd.purchase_recommendation_id = :purchaseRecommendation and (p.status = 2 or p.status = 4) and p.item_code in :items and p.batch = :batchNumber",nativeQuery = true)
    int approveRecommendation(@Param("purchaseRecommendation") PurchaseRecommendationEntity purchaseRecommendation,
                              @Param("status") int status,
                              @Param("items") List<String> items,
                              @Param("note") String note,
                              @Param("batchNumber") Integer batchNumber);

    @Query("select count (p) from PurchaseRecommendationBatch p join PurchaseRecommendationDetailEntity pd on p.purchaseRecommendationDetailId = pd.purchaseRecommendationDetailId " +
        "where p.isActive = true and pd.isActive = true and p.status = 3 and pd.purchaseRecommendation.purchaseRecommendationId = :purchaseRecommendationId and p.batch = :batch")
    Integer countByStatus(
        @Param("purchaseRecommendationId") Integer purchaseRecommendationId,
        @Param("batch") Integer batch
    );

    @Query("select p from PurchaseRecommendationBatch p join PurchaseRecommendationDetailEntity pd on p.purchaseRecommendationDetailId = pd.purchaseRecommendationDetailId where pd.purchaseRecommendation.purchaseRecommendationId = :purchaseRecommendationEntity and p.itemCode = :itemCode and pd.isActive = true and p.isActive = true ")
    PurchaseRecommendationBatch findByPRIdAndItemCode(@Param("purchaseRecommendationEntity") Integer purchaseRecommendationEntity, @Param("itemCode") String itemCode);

    @Query(value = "select count(p) from PurchaseRecommendationBatch p join PurchaseRecommendationDetailEntity pd on p.purchaseRecommendationDetailId = pd.purchaseRecommendationDetailId where p.isActive = true and pd.isActive = true and pd.purchaseRecommendation.purchaseRecommendationId = :purchaseRecommenId and p.batch = :batch and p.status = 2")
     Integer countBatchNotApproval(@Param("purchaseRecommenId") Integer purchaseRecommenId,@Param("batch") Integer batch);

    @Query(value = "select p.itemCode from PurchaseRecommendationEntity a join PurchaseRecommendationDetailEntity b on " +
        "a.purchaseRecommendationId = b.purchaseRecommendationId join PurchaseRecommendationBatch p on " +
        "b.purchaseRecommendationDetailId = p.purchaseRecommendationDetailId where a.mrpSubCode = :mrpCode and a.isActive = true and b.isActive = true and p.isActive = true ")
    List<String> getAllItemHasSend(@Param("mrpCode") String mrpCode);
}
