package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.PurchaseOrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItemEntity, Long> {
}
