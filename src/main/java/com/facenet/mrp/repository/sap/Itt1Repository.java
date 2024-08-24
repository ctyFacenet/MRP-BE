package com.facenet.mrp.repository.sap;

import com.facenet.mrp.domain.sap.Itt1Entity;
import com.facenet.mrp.service.dto.BomItemDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Itt1Repository extends JpaRepository<Itt1Entity,Long> {
    @Query("select new com.facenet.mrp.service.dto.BomItemDetailDTO(" +
        "oi.itmsGrpCod.itmsGrpCode,i.code,oi.itemName,i.quantity,oi.salUnitMsr,i.warehouse,oi.issueMthd" +
        ") from Itt1Entity i " +
        "join OittEntity o on i.father = o.code " +
        "join OitmEntity oi on oi.itemCode = i.code " +
        "where o.code = :productCode and o.uStatus = '0'"
    )
    List<BomItemDetailDTO> getAllItemsOfBom(@Param("productCode") String productCode);
}
