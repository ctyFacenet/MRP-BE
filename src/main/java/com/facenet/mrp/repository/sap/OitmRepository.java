package com.facenet.mrp.repository.sap;

import com.facenet.mrp.domain.sap.OitmEntity;
import com.facenet.mrp.service.dto.ItemInfoDTO;
import com.facenet.mrp.service.dto.OitmDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OitmRepository extends JpaRepository<OitmEntity, Integer>,OitmCustomRepository {

    Boolean existsByItemCode(String itemCode);

    @Query("select o.itemName from OitmEntity o where o.itemCode = :itemCode")
    String getItemName(@Param("itemCode") String itemCode);

    OitmEntity getByItemCode(String itemCode);

    @Query("SELECT t FROM OitmEntity t where 1=1 "
        + " AND (:itemCod is NULL OR t.itemCode like %:itemCod%) "
        + " AND (:itemNam is NULL OR t.itemName like %:itemNam%)")
    Page<OitmEntity> search(@Param("itemCod") String itemCod,@Param("itemNam") String itemNam, Pageable pageable);
}
