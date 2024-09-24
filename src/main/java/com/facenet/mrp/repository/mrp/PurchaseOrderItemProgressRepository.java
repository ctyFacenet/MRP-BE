package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.PurchaseOrderItemProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderItemProgressRepository extends JpaRepository<PurchaseOrderItemProgressEntity, Long> {
    @Query("SELECT p FROM PurchaseOrderItemProgressEntity p WHERE p.purchaseOrderItem.id = :purchaseOrderItemId")
    List<PurchaseOrderItemProgressEntity> findAllByPurchaseOrderItemId(@Param("purchaseOrderItemId") Long purchaseOrderItemId);
}
