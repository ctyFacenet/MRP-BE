package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.PurchaseRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRequestEntityRepository extends JpaRepository<PurchaseRequestEntity, Integer>
{
    @Query("SELECT pr.prCode FROM PurchaseRequestEntity pr ORDER BY pr.prCode DESC")
    List<String> findLastPRCode();

    PurchaseRequestEntity findByPrCode(String prCode);
}
