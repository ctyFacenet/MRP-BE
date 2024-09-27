package com.facenet.mrp.repository.sap;

import com.facenet.mrp.domain.sap.Pdn1Entity;
import com.facenet.mrp.domain.sap.Pdn1EntityPK;
import com.facenet.mrp.service.dto.mrp.VendorItemEntityDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Pdn1Repository extends JpaRepository<Pdn1Entity, Pdn1EntityPK> {
    @Query("select sum(p.quantity) from Pdn1Entity p " +
        "where p.baseEntry = :poCode and p.baseType = 22 and p.baseLine = :lineNumber")
    Double getGrpoQuantityByItemCodeAndPoCode(
                                              @Param("poCode") Integer poCode,
                                              @Param("lineNumber") Integer lineNumber);

    @Query("select new com.facenet.mrp.service.dto.mrp.VendorItemEntityDto(p.baseCard, p.itemCode) " +
        "from Pdn1Entity p " +
        "where p.docDate >= '2024-01-01' " +
        "group by p.baseCard, p.itemCode")
    List<VendorItemEntityDto> findDistinctVendorItems();

    @Query("SELECT p FROM Pdn1Entity p WHERE p.docDate >= '2024-01-01'")
    List<Pdn1Entity> findAllFrom2024();

    @Query("SELECT p FROM Pdn1Entity p WHERE p.itemCode = :itemCode AND p.baseCard = :baseCard ORDER BY p.docDate DESC")
    List<Pdn1Entity> findTop3ByItemCodeAndBaseCardOrderByDocDateDesc(@Param("itemCode") String itemCode,
                                                                     @Param("baseCard") String baseCard);
}
