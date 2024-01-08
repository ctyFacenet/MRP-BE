package com.facenet.mrp.repository.sap;

import com.facenet.mrp.domain.sap.Itm2Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface Itm2Repository extends JpaRepository<Itm2Entity, String> {
    @Query("select i from Itm2Entity i where i.vendorCode in :vendors")
    List<Itm2Entity> findAllItemOfVendorList(@Param("vendors") Set<String> vendors);

    Boolean existsByItemCodeAndVendorCode(String itemCode, String vendorCode);
}
