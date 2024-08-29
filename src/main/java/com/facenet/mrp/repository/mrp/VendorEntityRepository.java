package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface VendorEntityRepository extends JpaRepository<VendorEntity, Integer>, JpaSpecificationExecutor<VendorEntity> {
    List<VendorEntity> findAllBySap(int sap);
    boolean existsByVendorCode(String code);
}
