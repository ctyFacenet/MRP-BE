package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.VendorsCombineEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorsCombineEntityRepository extends JpaRepository<VendorsCombineEntity, Integer>, JpaSpecificationExecutor<VendorsCombineEntity> {
    void deleteAllBySap(int sap);
}
