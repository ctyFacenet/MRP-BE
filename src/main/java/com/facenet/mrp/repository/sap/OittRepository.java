package com.facenet.mrp.repository.sap;

import com.facenet.mrp.domain.sap.CoittEntity;
import com.facenet.mrp.domain.sap.OittEntity;
import com.facenet.mrp.service.dto.DetailBomVersionDTO;
import com.facenet.mrp.service.dto.mrp.CloneBomDTO;
import com.facenet.mrp.service.dto.mrp.MrpDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OittRepository extends JpaRepository<OittEntity, Integer> {
    @Query(value = "select new com.facenet.mrp.service.dto.mrp.CloneBomDTO(" +
        "oi2.itmsGrpCod.itmsGrpCode, co.code, oi2.itemName, co.qauntity, " +
        "oi.itmsGrpCod.itmsGrpCode ,ci.code, oi.itemName, ci.quantity) " +
        "from OittEntity co " +
        "join Itt1Entity ci on co.code = ci.father " +
        "join OitmEntity oi on oi.itemCode = ci.code " +
        "join OitmEntity oi2 on oi2.itemCode = co.code " +
        "and co.uStatus = '0' ")
    List<CloneBomDTO> cloneAllMrpProductBom();

    @Query(value = "select distinct new com.facenet.mrp.service.dto.mrp.MrpDetailDTO(ci.code, oi.itemName, ci.quantity, oi.itmsGrpCod.itmsGrpCode) " +
        "from OittEntity co " +
        "join Itt1Entity ci on co.code = ci.father " +
        "join OitmEntity oi on oi.itemCode = ci.code " +
        "and co.uStatus = '0' " +
        "and co.code = :itemCode ")
    List<MrpDetailDTO> getAllMrpProductBomList(
        @Param("itemCode")String itemCode
    );

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.MrpDetailDTO(ci.code, oi.itemName, ci.quantity, oi.itmsGrpCod.itmsGrpCode) " +
        "from OittEntity co " +
        "join Itt1Entity ci on co.code = ci.father " +
        "join OitmEntity oi on oi.itemCode = ci.code " +
        "and co.uStatus = '0' " +
        "and co.code = :itemCode " )
    List<MrpDetailDTO> getAllMrpProductBom(
        @Param("itemCode")String itemCode
    );

    @Query(value = "select b from OittEntity b where b.uStatus = '0' and b.code = :productCode")
    OittEntity getViewBomVersionWithProduct(@Param("productCode") String productCode);

    @Query(value = "select new com.facenet.mrp.service.dto.DetailBomVersionDTO(a.code,oi.itemName, a.warehouse) " +
        "from Itt1Entity a join OittEntity b on a.father = b.code " +
        "join OitmEntity oi on oi.itemCode = a.code " +
        "where b.uStatus = '0' and b.code = :productCode")
    List<DetailBomVersionDTO> getDetailBomVersionWithProduct(@Param("productCode") String productCode);
}
