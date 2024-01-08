package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.*;
import com.facenet.mrp.service.dto.ItemAnalysisDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface HistoryMrpRepository extends JpaRepository<MrpEntity,Long> {
    @Query(value = "select m from MrpEntity m where m.isActive = 1 order by m.mrpId desc")
    List<MrpEntity> getAllMrp();

    @Query(value = "select m from MrpSubEntity m where m.isActive = 1 and m.mrpCode = :mrpCode order by m.mrpId desc")
    List<MrpSubEntity> getMrpSub(@Param("mrpCode") String mrpCode);

    @Query(value = "select m from MrpDetailEntity m where m.isActive = 1 and m.mrpSubCode = :mrpSubCode")
    List<MrpDetailEntity> getDetailMrp(@Param("mrpSubCode") String mrpSubCode);

    @Query(value = "select m from MrpSubEntity m where m.isActive = 1 and m.mrpSubCode = :mrpSubCode")
    MrpSubEntity getMrpSubCode(@Param("mrpSubCode") String mrpSubCode);

    @Query(value = "select new com.facenet.mrp.service.dto.ItemAnalysisDTO(m.mrpSubCode,m.itemGroup,m.itemCode,m.itemDescription,m.requiredQuantity,m.inStockQuantity,m.baseQuantity,m.warehouseName,m.bomVersion,m.level,m.status,m.parentPath) from MrpDetailEntity m where m.isActive = 1 and m.mrpSubCode = :mrpSubCode and m.itemGroup = 'TP'")
    List<ItemAnalysisDTO> getAllProduct(@Param("mrpSubCode") String mrpSubCode);

    @Query(value = "select new com.facenet.mrp.service.dto.ItemAnalysisDTO(m.mrpSubCode,m.itemGroup,m.itemCode,m.itemDescription,m.requiredQuantity,m.inStockQuantity,m.baseQuantity,m.warehouseName,m.bomVersion,m.level,m.status,m.parentPath) from MrpDetailEntity m where m.isActive = 1 and m.mrpSubCode = :mrpSubCode and m.parentPath = :itemCode")
    List<ItemAnalysisDTO> getAllDetailBomItemDTO(@Param("mrpSubCode") String mrpSubCode,@Param("itemCode") String itemCode);

    @Query(value = "select m from MrpSubEntity m where m.isActive = 1 and m.mrpCode = :mrpCode and m.mrpSubCode = :mrpSubCode")
    MrpSubEntity getAllMrpSubCode(@Param("mrpCode") String mrpCode,@Param("mrpSubCode") String mrpSubCode);

    @Query(value = "select m from MrpEntity m where m.isActive = 1 and m.mrpPoId = :poCode and m.mrpCode = :mrpCode")
    MrpEntity getMrp(@Param("poCode") String poCode,@Param("mrpCode") String mrpCode);

    @Query(value = "select m from ResultMrpJsonEntity m where m.isActive = 1 and m.mrpSubCode = :mrpSubCode")
    ResultMrpJsonEntity getMrpResult(@Param("mrpSubCode") String mrpSubCode);

    boolean existsByMrpCodeAndIsActive(String mrpCode, Byte isActive);

    @Transactional
    @Modifying
    @Query("update MrpEntity m set m.isActive = 2 where m.mrpCode = :mrpCode")
    int deleteMrpHistory(@Param("mrpCode") String mrpCode);
}
