package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.PurchaseorderPurchaseRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseOrderPurchaseRequestRepository extends JpaRepository<PurchaseorderPurchaseRequestEntity, Long>  {
    List<PurchaseorderPurchaseRequestEntity> findAllByPurchaseOrderId(Long purchaseOrderId);


}
