package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.PurchaseRequestDetailEntity;
import com.facenet.mrp.domain.mrp.PurchaseRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRequestDetailEntityRepository extends JpaRepository<PurchaseRequestDetailEntity, Integer>
{
    List<PurchaseRequestDetailEntity> findAllByPrCodeAndIsActive(String prCode, int active);
}
