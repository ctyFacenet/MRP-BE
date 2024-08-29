package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.VendorsCombineEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorsCombineEntityRepository extends JpaRepository<VendorsCombineEntity, Integer>, JpaSpecificationExecutor<VendorsCombineEntity> {
    List<VendorsCombineEntity> findAllBySap(int sap);
    boolean existsByCode(String code);
}
