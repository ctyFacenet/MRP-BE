package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.MrpBomVersionDetail;
import com.facenet.mrp.service.dto.mrp.MrpDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MrpBomDetailRepository extends JpaRepository<MrpBomVersionDetail, Integer> {

    List<MrpBomVersionDetail> findAllByMrpPoIdAndParentPathEndingWithIgnoreCaseAndStatus(String mrpPoId,String parentPath, String status);

    MrpBomVersionDetail findByMrpPoIdAndParentPathAndItemCodeIgnoreCase(String mrpPoId,String parentPath,String itemCode);

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.MrpDetailDTO(bom.itemCode, bom.itemName, bom.quantity, bom.version, bom.itemGroup) from MrpBomVersionDetail bom, ProductOrder po " +
        "where bom.mrpPoId = po.mrpPoId " +
        "and po.productOrderCode = :soCode " +
        "and bom.parentPath = :parentPath " +
        "and bom.isActive = true ")
    List<MrpDetailDTO> getMrpDetailNotInBom(@Param("soCode")String soCode, @Param("parentPath")String parentPath);

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.MrpDetailDTO(bom.itemCode, bom.itemName, bom.quantity, bom.version, bom.itemGroup) from MrpBomVersionDetail bom, ProductOrder po " +
        "where bom.mrpPoId = po.mrpPoId " +
        "and bom.itemGroup = 'BTP' " +
        "and po.productOrderCode = :soCode " +
        "and bom.parentPath = :parentPath " +
        "and bom.isActive = true ")
    List<MrpDetailDTO> getMrpDetailBTPNotInBom(@Param("soCode")String soCode, @Param("parentPath")String parentPath);

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.MrpDetailDTO(bom.itemCode, bom.itemName, bom.quantity, bom.version, bom.itemGroup) from MrpBomVersionDetail bom, ProductOrder po " +
        "where bom.mrpPoId = po.mrpPoId " +
        "and (bom.itemGroup <> 'BTP') " +
        "and po.productOrderCode = :soCode " +
        "and bom.parentPath = :parentPath " +
        "and bom.isActive = true ")
    List<MrpDetailDTO> getMrpDetailNVLNotInBom(@Param("soCode")String soCode, @Param("parentPath")String parentPath);

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.MrpDetailDTO(bom.itemCode, bom.itemName, bom.quantity, bom.version, bom.itemGroup, bom.parentPath) from MrpBomVersionDetail bom, ProductOrder po " +
        "where bom.mrpPoId = po.mrpPoId " +
        "and po.productOrderCode = :soCode " +
        "and bom.isActive = true ")
    List<MrpDetailDTO> getMrpDetailNotInBom(@Param("soCode") String soCode);

}
