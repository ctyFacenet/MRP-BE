package com.facenet.mrp.repository.sap;

import com.facenet.mrp.domain.sap.CoittEntity;
import com.facenet.mrp.domain.sap.OittEntity;
import com.facenet.mrp.service.dto.BomItemDetailDTO;
import com.facenet.mrp.service.dto.DetailBomVersionDTO;
import com.facenet.mrp.service.dto.mrp.CloneBomDTO;
import com.facenet.mrp.service.dto.mrp.MrpDetailDTO;
import com.facenet.mrp.service.dto.sap.CoittCitt1DTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(value = "select new com.facenet.mrp.service.dto.sap.CoittCitt1DTO(a.code,'1.0',1,b.code,'1.0')"+
        " FROM OittEntity a join Itt1Entity b on a.code = b.father join OitmEntity z on z.itemCode = b.code"+
        " where z.itmsGrpCod.itmsGrpCode = 101 order by a.code")
    List<CoittCitt1DTO> getAllDistinct();

    @Query(value = "select x from OittEntity x")
    List<OittEntity> getAllOitt();

    @Query(value = "select new com.facenet.mrp.service.dto.DetailBomVersionDTO(a.code,oi.itemName,a.quantity,a.warehouse,b.uStatus) " +
        "from Itt1Entity a " +
        "join OittEntity b on a.father = b.code " +
        "join OitmEntity oi on oi.itemCode = b.code " +
        "where b.uStatus = '0' and b.code = :productCode ")
    Page<DetailBomVersionDTO> getDetailBomVersionByProductPaging(Pageable pageable, @Param("productCode") String productCode);


    @Query("select new com.facenet.mrp.service.dto.BomItemDetailDTO(" +
        "oi.itmsGrpCod.itmsGrpCode,ci.code,oi.itemName,oi.uTechName, ci.quantity,ci.uom,ci.warehouse" +
        ") from Itt1Entity ci " +
        "join OittEntity co on ci.father = co.code " +
        "join OitmEntity oi on oi.itemCode = ci.code " +
        "where co.code = :productCode and co.uStatus = '0'"
    )
    List<BomItemDetailDTO> getAllItemsOfBom(@Param("productCode") String productCode);
}
