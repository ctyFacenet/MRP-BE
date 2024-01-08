package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.MrpProductionQuantityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface MrpProductionQuantityRepository extends JpaRepository<MrpProductionQuantityEntity, Integer> {
    @Query("select m from MrpProductionQuantityEntity m " +
        "where m.isActive = true and m.itemGroup <> 'NVL' " +
        "and :startDate <= m.dueDate and m.dueDate <= :endDate")
    List<MrpProductionQuantityEntity> findAllByDueDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Modifying
    @Query("update MrpProductionQuantityEntity m set m.isActive = false " +
        "where m.mrpSubCode = :mrpSubCode")
    int deleteByMrpSubCode(@Param("mrpSubCode") String mrpSubCode);

    @Modifying
    @Query("update MrpProductionQuantityEntity m set m.isActive = false " +
        "where m.mrpSubCode in :mrpSubCode")
    int deleteByMrpSubCode(@Param("mrpSubCode") List<String> mrpSubCode);
}
