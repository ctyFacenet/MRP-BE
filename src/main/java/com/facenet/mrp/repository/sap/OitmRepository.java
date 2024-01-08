package com.facenet.mrp.repository.sap;

import com.facenet.mrp.domain.sap.OitmEntity;
import com.facenet.mrp.service.dto.ItemInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface OitmRepository extends JpaRepository<OitmEntity, Integer>,OitmCustomRepository {

    Boolean existsByItemCode(String itemCode);

    @Query("select o.itemName from OitmEntity o where o.itemCode = :itemCode")
    String getItemName(@Param("itemCode") String itemCode);

    OitmEntity getByItemCode(String itemCode);
}
