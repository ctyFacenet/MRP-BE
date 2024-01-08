package com.facenet.mrp.repository.sap;

import com.facenet.mrp.domain.sap.Crd1Entity;
import com.facenet.mrp.domain.sap.OcrdEntity;
import com.facenet.mrp.service.dto.ContactPersonDTO;
import com.facenet.mrp.service.dto.mrp.VendorCodeForDetailReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public interface OcrdRepository extends JpaRepository<OcrdEntity,Long> {
    @Query(value = "select s from OcrdEntity s  where s.cardCode = :vendorCode")
    OcrdEntity getVendor(@Param("vendorCode") String vendorCode);

    Boolean existsByCardCode(String vendorCode);

    @Query(value = "select s from Crd1Entity s  where s.cardCode = :vendorCode")
    Crd1Entity getTaxCode(@Param("vendorCode") String vendorCode);

    @Query(value = "select new com.facenet.mrp.service.dto.ContactPersonDTO(b.addId,a.name,a.gender,a.position,a.title,a.eMailL,a.tel1,a.fax,a.address,a.birthPlace,a.birthDate) from OcprEntity a join OcrdEntity b on a.cardCode = b.cardCode where a.cardCode = :vendorCode")
    ContactPersonDTO getContactPerson(@Param("vendorCode") String vendorCode);

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.VendorCodeForDetailReport(s.cardCode, s.cardName) from OcrdEntity s  where s.cardCode in :vendorCodeList")
    List<VendorCodeForDetailReport> getVendorList(@Param("vendorCodeList") Set<String> vendorCodeList);

    List<OcrdEntity> getAllByCardCodeIn(List<String> vendorCodes);

    default Map<String, String> vendorMap(List<String> vendorCodes) {
        return getAllByCardCodeIn(vendorCodes).stream().collect(
            Collectors.toMap(OcrdEntity::getCardCode, OcrdEntity::getCardName)
        );
    }
}
