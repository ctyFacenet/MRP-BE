package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<SaleEntity,Integer> {
    @Query(value = "select s from SaleEntity s where s.isActive = 1 and s.vendorCode = :vendorCode")
    SaleEntity getAllSaleVendor(@Param("vendorCode") String vendorCode);
}
