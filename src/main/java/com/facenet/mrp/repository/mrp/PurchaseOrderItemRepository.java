package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.PurchaseOrderItemEntity;
import com.facenet.mrp.domain.mrp.PurchaseorderPurchaseRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItemEntity, Long> {
    @Query("SELECT p FROM PurchaseOrderItemEntity p WHERE p.purchaseOrder.id = :purchaseOrderId")
    List<PurchaseOrderItemEntity> findAllByPurchaseOrderId(@Param("purchaseOrderId") Long purchaseOrderId);
}
