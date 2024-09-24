package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.PurchaseOrderItemProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderItemProgressRepository extends JpaRepository<PurchaseOrderItemProgressEntity, Long> {
    List<PurchaseOrderItemProgressEntity> findByPurchaseOrderItemId(Long purchaseOrderItemId);
}
