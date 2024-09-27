package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.VendorItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface VendorItemRepository extends JpaRepository<VendorItemEntity, String> {
    @Query("select v.itemCode from VendorItemEntity v where v.vendorCode = :vendorCode")
    Page<String> getItemBelongToVendor(@Param("vendorCode") String vendorCode, Pageable pageable);

    @Query("select v from VendorItemEntity v where v.vendorCode in :vendors")
    List<VendorItemEntity> findAllItemOfVendorList(@Param("vendors") Set<String> vendors);

    Boolean existsByItemCodeAndVendorCode(String itemCode, String vendorCode);

    VendorItemEntity findByItemCodeAndVendorCode(String itemCode, String vendor);
    List<VendorItemEntity> findAllBySap(int sap);

    @Modifying
    @Query("delete from VendorItemEntity v where v.itemCode = :itemCode and v.vendorCode = :vendorCode and v.sap is null")
    void deleteByItemCodeAndVendorCode(@Param("itemCode") String itemCode, @Param("vendorCode") String vendorCode);
}
