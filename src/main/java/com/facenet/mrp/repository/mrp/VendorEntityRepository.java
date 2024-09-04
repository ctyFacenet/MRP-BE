package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.VendorEntity;
import com.facenet.mrp.service.dto.mrp.VendorCodeForDetailReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface VendorEntityRepository extends JpaRepository<VendorEntity, Integer>, JpaSpecificationExecutor<VendorEntity> {
    List<VendorEntity> findAllBySap(int sap);
    boolean existsByVendorCode(String code);

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.VendorCodeForDetailReport(s.vendorCode, s.vendorName) from VendorEntity s  where s.vendorCode in :vendorCodeList")
    List<VendorCodeForDetailReport> getVendorList(@Param("vendorCodeList") Set<String> vendorCodeList);
}
