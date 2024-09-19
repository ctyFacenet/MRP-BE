package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.PurchaseOrderItemProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderItemProgressRepository extends JpaRepository<PurchaseOrderItemProgressEntity, Long> {
}
