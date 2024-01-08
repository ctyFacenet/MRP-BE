package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.LeadTimeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeadTimeRepository extends PagingAndSortingRepository<LeadTimeEntity, Integer> {
    @Query(value = "select l from LeadTimeEntity l where l.vendorCode = :vendorCode and l.itemCode = :itemCode and l.isActive = 1")
    LeadTimeEntity getLeadTimeByVendorCodeAndItemCode(@Param("vendorCode") String vendorCode, @Param("itemCode") String itemCode);

    @Query(value = "select l from LeadTimeEntity l where l.vendorCode = :vendorCode and l.isActive = 1 order by l.updatedAt desc")
    List<LeadTimeEntity> getLeadTime(@Param("vendorCode") String vendorCode);

    List<LeadTimeEntity> findAllByVendorCodeAndItemCode(String vendorCode, String itemCode);
}
