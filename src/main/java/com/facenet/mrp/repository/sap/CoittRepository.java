package com.facenet.mrp.repository.sap;

import com.facenet.mrp.domain.sap.CoittEntity;
import com.facenet.mrp.service.dto.BomItemDetailDTO;
import com.facenet.mrp.service.dto.BomItemDetailDraftDTO;
import com.facenet.mrp.service.dto.DetailBomVersionDTO;
import com.facenet.mrp.service.dto.mrp.CloneBomDTO;
import com.facenet.mrp.service.dto.mrp.MrpDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoittRepository extends JpaRepository<CoittEntity,Integer> {
    @Query(value = "select c from CoittEntity c where c.uActive = '1'")
    List<CoittEntity> getListBomVersionIsActive();

    @Query(value = "select c from CoittEntity c where c.uActive = '1' and c.uProNo = :productCode")
    List<CoittEntity> getListBomVersionByCode(@Param("productCode") String productCode);

    @Query(value = "select new com.facenet.mrp.service.dto.DetailBomVersionDTO(a.uItemCode,a.uItemName,a.uOtherNam,a.uCtrLevel,a.uQuantity,a.uPartNumber,a.uWhsCod,a.uLocation,a.uVendor,a.uAlter,a.uVersions,a.uRemarks) from Citt1Entity a join CoittEntity b on a.docEntry = b.docEntry where b.uActive = '1' and b.uProNo = :productCode and b.uVersions = :version")
    List<DetailBomVersionDTO> getDetailBomVersionWithProduct(@Param("productCode") String productCode, @Param("version") String version);

    @Query(value = "select b from CoittEntity b where b.uActive = '1' and b.uProNo = :productCode and b.uVersions = :version")
    CoittEntity getViewBomVersionWithProduct(@Param("productCode") String productCode, @Param("version") String version);

    @Query("select new com.facenet.mrp.service.dto.BomItemDetailDTO(" +
        "oi.itmsGrpCod.itmsGrpCode,ci.uItemCode,ci.uItemName,ci.uItmTech,ci.uCtrLevel,ci.uPartNumber,ci.uAlter,ci.uLocation,ci.uQuantity,ci.uUom,ci.uWhsCod,ci.uIssue,ci.uVendor,ci.uVersions,ci.uRemarks" +
        ") from Citt1Entity ci " +
        "join CoittEntity co on ci.docEntry = co.docEntry " +
        "join OitmEntity oi on oi.itemCode = ci.uItemCode " +
        "where co.uProNo = :productCode and co.uVersions = :version and co.uStatus = 'A'"
    )
    List<BomItemDetailDTO> getAllItemsOfBom(@Param("productCode") String productCode, @Param("version") String version);

    @Query("select new com.facenet.mrp.service.dto.BomItemDetailDraftDTO(" +
        "oi.itmsGrpCod.itmsGrpCode,ci.uItemCode,ci.uItemName,ci.uItmTech,ci.uCtrLevel,ci.uPartNumber,ci.uAlter,ci.uLocation,ci.uQuantity,ci.uUom,ci.uWhsCod,ci.uIssue,ci.uVendor,ci.uVersions,ci.uRemarks" +
        ") from Citt1Entity ci " +
        "join CoittEntity co on ci.docEntry = co.docEntry " +
        "join OitmEntity oi on oi.itemCode = ci.uItemCode " +
        "where co.uProNo = :productCode and co.uVersions = :version and co.uStatus = 'A'"
    )
    List<BomItemDetailDraftDTO> getAllItemsOfBomDraft(@Param("productCode") String productCode, @Param("version") String version);

    @Query(value = "select new com.facenet.mrp.service.dto.DetailBomVersionDTO(a.uItemCode,a.uItemName,a.uOtherNam,a.uCtrLevel,a.uQuantity,a.uPartNumber,a.uWhsCod,a.uLocation,a.uVendor,a.uAlter,a.uVersions,a.uRemarks,b.status) from Citt1Entity a join CoittEntity b on a.docEntry = b.docEntry where b.uActive = '1' and b.uProNo = :productCode and b.uVersions = :version")
    Page<DetailBomVersionDTO> getDetailBomVersionByProductPaging(Pageable pageable, @Param("productCode") String productCode, @Param("version") String version);

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.MrpDetailDTO(ci.uItemCode, ci.uItemName, ci.uAlter, ci.uQuantity, ci.uVersions, oi.itmsGrpCod.itmsGrpCode) " +
        "from CoittEntity co " +
        "join Citt1Entity ci on co.docEntry = ci.docEntry " +
        "join OitmEntity oi on oi.itemCode = ci.uItemCode " +
        "and co.uActive = '1' " +
        "and co.uProNo = :itemCode " +
        "and co.uVersions = :bomVersion")
    List<MrpDetailDTO> getAllMrpProductBom(
        @Param("itemCode")String itemCode,
        @Param("bomVersion")String bomVersion
    );

    @Query(value = "select distinct new com.facenet.mrp.service.dto.mrp.MrpDetailDTO(ci.uItemCode, ci.uItemName, ci.uAlter, ci.uQuantity, ci.uVersions, oi.itmsGrpCod.itmsGrpCode) " +
        "from CoittEntity co " +
        "join Citt1Entity ci on co.docEntry = ci.docEntry " +
        "join OitmEntity oi on oi.itemCode = ci.uItemCode " +
        "and co.uActive = '1' " +
        "and co.uProNo = :itemCode " +
        "and co.uVersions = :bomVersion")
    List<MrpDetailDTO> getAllMrpProductBomList(
        @Param("itemCode")String itemCode,
        @Param("bomVersion")String bomVersion
    );

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.MrpDetailDTO(ci.uItemCode, ci.uItemName, ci.uAlter, ci.uQuantity, ci.uVersions, oi.itmsGrpCod.itmsGrpCode) " +
        "from CoittEntity co join Citt1Entity ci on co.docEntry = ci.docEntry " +
        "join OitmEntity oi on oi.itemCode = ci.uItemCode " +
        "where " +
//        "ci.uVersions is not null " +
//        "and ci.uVersions <> '' " +
        "co.uActive = '1' " +
        "and co.uProNo = :itemCode " +
        "and co.uVersions = :bomVersion " +
        "and oi.itmsGrpCod.itmsGrpCode = 101")
    List<MrpDetailDTO> getAllBTPMrpProductBom(
        @Param("itemCode")String itemCode,
        @Param("bomVersion")String bomVersion
    );

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.MrpDetailDTO(c.uProNo, c.uProNam, c.uQuantity, c.uVersions, o.itmsGrpCod.itmsGrpCode) " +
        "from CoittEntity c " +
        "join OitmEntity o on c.uProNo = o.itemCode " +
        "where c.uProNo = :itemCode " +
        "and c.uActive = '1' " +
        "and c.uVersions = :bomVersion")
    List<MrpDetailDTO> getOneMrpProduct(@Param("itemCode")String itemCode, @Param("bomVersion")String bomVersion);

    @Query(value = "select new com.facenet.mrp.service.dto.DetailBomVersionDTO(a.uItemCode,a.uItemName,a.uOtherNam,a.uCtrLevel,a.uQuantity,a.uPartNumber,a.uWhsCod,a.uLocation,a.uVendor,a.uAlter,a.uVersions,a.uRemarks,b.status) " +
        "from Citt1Entity a join CoittEntity b on a.docEntry = b.docEntry where b.uActive = '1' and b.uProNo = :productCode and b.uVersions = :version and a.uItemCode=:itemCode")
    DetailBomVersionDTO getDetailBomVersionByProductAndItemCode(@Param("productCode") String productCode, @Param("version") String version, @Param("itemCode") String itemCode);

    @Query(value = "select new com.facenet.mrp.service.dto.DetailBomVersionDTO(a.uItemCode,a.uItemName,a.uOtherNam,a.uCtrLevel,a.uQuantity,a.uPartNumber,a.uWhsCod,a.uLocation,a.uVendor,a.uAlter,a.uVersions,a.uRemarks,b.status) " +
        "from Citt1Entity a join CoittEntity b on a.docEntry = b.docEntry where b.uActive = '1' and b.uProNo = :productCode  and a.uItemCode=:itemCode")
    DetailBomVersionDTO getDetailBomVersionByProductAndItemCode(@Param("productCode") String productCode,@Param("itemCode") String itemCode);

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.MrpDetailDTO(ci.uItemCode, ci.uItemName, ci.uAlter, ci.uQuantity, ci.uVersions, oi.itmsGrpCod.itmsGrpCode) " +
        "from CoittEntity co join Citt1Entity ci on co.docEntry = ci.docEntry " +
        "join OitmEntity oi on oi.itemCode = ci.uItemCode " +
        "where  " +
        "co.uActive = '1' " +
        "and co.uProNo = :itemCode " +
        "and co.uVersions = :bomVersion " +
        "and oi.itmsGrpCod.itmsGrpCode <> 101 ")
    List<MrpDetailDTO> getAllMrpNVLProductBom(
        @Param("itemCode")String itemCode,
        @Param("bomVersion")String bomVersion
    );

    List<CoittEntity> getCoittEntitiesByuProNoIn(Iterable<String> itemCodes);

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.MrpDetailDTO(ci.uItemCode, ci.uItemName, ci.uAlter, ci.uQuantity, ci.uVersions, oi.itmsGrpCod.itmsGrpCode) " +
        "from CoittEntity co " +
        "join Citt1Entity ci on co.docEntry = ci.docEntry " +
        "join OitmEntity oi on oi.itemCode = ci.uItemCode " +
        "and co.uActive = '1' " +
        "and co.uProNo in :itemCode " +
        "and co.uVersions in :bomVersion")
    List<MrpDetailDTO> getAllMrpProductBomInList(
        @Param("itemCode")List<String> itemCode,
        @Param("bomVersion")List<String> bomVersion
    );

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.CloneBomDTO(" +
        "oi2.itmsGrpCod.itmsGrpCode, co.uProNo, co.uProNam, co.uQuantity, co.uVersions, " +
        "oi.itmsGrpCod.itmsGrpCode ,ci.uItemCode, ci.uItemName, ci.uAlter, ci.uQuantity, ci.uVersions) " +
        "from CoittEntity co " +
        "join Citt1Entity ci on co.docEntry = ci.docEntry " +
        "join OitmEntity oi on oi.itemCode = ci.uItemCode " +
        "join OitmEntity oi2 on oi2.itemCode = co.uProNo " +
        "and co.uActive = '1' ")
    List<CloneBomDTO> cloneAllMrpProductBom();

    @Query(value = "select a from CoittEntity a where a.uProNo = :productCode")
    List<CoittEntity> getList(@Param("productCode") String productCode);

    @Query(value = "select y.uItemCode from CoittEntity x join Citt1Entity y on x.docEntry = y.docEntry join OitmEntity z on x.uProNo = z.itemCode where y.uVersions is not null and x.uProNo in " +
        "(select a.uProNo from CoittEntity a join OitmEntity b on a.uProNo = b.itemCode where b.itmsGrpCod.itmsGrpCode = 104)")
    List<String> getListBTP();
}
