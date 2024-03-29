package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.MrpRequiredQuantityEntity;
import com.facenet.mrp.service.dto.ReportDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MrpRequiredQuantityRepository extends JpaRepository<MrpRequiredQuantityEntity, Integer> {

    @Query("select new com.facenet.mrp.service.dto.ReportDetailDTO(r.mrpSubCode, r.itemCode, r.itemName, r.reqQuantity) from MrpRequiredQuantityEntity r " +
        "where r.isActive = true " +
        "and r.soCode = :soCode " +
        "and r.itemGroup = 'NVL' " +
        "group by r.itemCode, r.mrpSubCode")
    List<ReportDetailDTO> getAllMaterialRequiredQuantity(@Param("soCode")String soCode);

    @Modifying
    @Query("update MrpRequiredQuantityEntity m set m.isActive = false " +
        "where m.mrpSubCode = :mrpSubCode")
    int deleteByMrpSubCode(@Param("mrpSubCode") String mrpSubCode);

    @Modifying
    @Query("update MrpRequiredQuantityEntity m set m.isActive = false " +
        "where m.mrpSubCode in :mrpSubCode")
    int deleteByMrpSubCode(@Param("mrpSubCode") List<String> mrpSubCode);
}
