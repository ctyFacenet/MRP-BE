package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.MrpSubEntity;
import com.facenet.mrp.service.dto.ListDetailMrpDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MrpSubRepository extends JpaRepository<MrpSubEntity,Long> {
    boolean existsByMrpSubCodeAndIsActive(String mrpSubCode, Byte isActive);

    MrpSubEntity getByMrpSubCode(String mrpSubCode);
    MrpSubEntity getByMrpSubCodeAndStatus(String mrpSubCode, Byte status);

    List<MrpSubEntity> getAllByMrpPoIdAndIsActive(String productOrderCode, Byte isActive);

    @Query("select new com.facenet.mrp.service.dto.ListDetailMrpDTO(ms.mrpSubCode, ms.mrpCode, ms.mrpPoId, ms.mrpDescription, ms.sortType, ms.startTime, ms.endTime, ms.analysisType, ms.analysisKind, ms.analysisSource, ms.items, ms.warehouseAnalysis, ms.analysisMode, m.lastAccess, ms.status) " +
        "from MrpSubEntity ms " +
        "join MrpEntity m on ms.mrpCode = m.mrpCode " +
        "where ms.mrpPoId = :productOrderCode and ms.isActive = 1 and m.isActive = 1")
    List<ListDetailMrpDTO> getAllAnalyzedMrpOf(@Param("productOrderCode") String productOrderCode);

    @Transactional
    @Modifying
    @Query("update MrpSubEntity m set m.isActive = 2 where m.mrpSubCode = :mrpSubCode")
    int deleteSubMrpHistory(@Param("mrpSubCode") String mrpSubCode);

    @Transactional
    @Modifying
    @Query("update MrpSubEntity m set m.isActive = 2 where m.mrpCode = :mrpCode")
    int deleteAllSubMrpHistoryOfMrpCode(@Param("mrpCode") String mrpCode);

    @Query(value = "select count(mrpSubCode) from MrpSubEntity where mrpCode = :mrpCode group by mrpCode")
    int getMrpSubCodeCount(@Param("mrpCode")String mrpCode);

    @Query("select m.mrpSubCode from MrpSubEntity m where m.mrpCode = :mrpCode")
    List<String> getAllMrpSubCodeByMrpCode(@Param("mrpCode") String mrpCode);

    @Modifying
    @Query("update MrpSubEntity m set m.isActive = 0 where m.mrpPoId = :soCode")
    Integer deleteAllBySoCode(@Param("soCode") String soCode);
}
