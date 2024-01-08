package com.facenet.mrp.repository.sap;

import com.facenet.mrp.domain.sap.Por1Entity;
import com.facenet.mrp.domain.sap.Por1EntityPK;
import com.facenet.mrp.service.dto.mrp.ItemQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface Por1Repository extends JpaRepository<Por1Entity, Por1EntityPK> {
    @Query("select new com.facenet.mrp.service.dto.mrp.ItemQuantity(po.itemCode, sum(po.openQty)) " +
        "from Por1Entity po " +
        "where po.itemCode in :itemCode and po.lineStatus = 'O'" +
        "group by po.itemCode ")
    List<ItemQuantity> getOpenQuantity(@Param("itemCode") List<String> itemCode);

    default Map<String, Double> getOpenQuantityMap(List<String> itemCode) {
        return getOpenQuantity(itemCode).stream().collect(Collectors.toMap(
            ItemQuantity::getItemCode, ItemQuantity::getQuantity
        ));
    }

    @Query("select new com.facenet.mrp.service.dto.mrp.ItemQuantity(po.itemCode, sum(po.openQty)) " +
        "from Por1Entity po " +
        "where po.lineStatus = 'O'" +
        "group by po.itemCode ")
    List<ItemQuantity> getOpenQuantity();

    default Map<String, Double> getOpenQuantityMap() {
        return getOpenQuantity().stream().collect(Collectors.toMap(
            ItemQuantity::getItemCode, ItemQuantity::getQuantity
        ));
    }

    @Query("select po.baseQty from Por1Entity po " +
        "where po.docEntry = :poCode and po.lineNum = :lineNumber")
    Double getPrQuantityByItemCodeAndPoCode(@Param("poCode") Integer poCode,
                                            @Param("lineNumber") Integer lineNumber);

    @Query("select po.quantity from Por1Entity po " +
        "where po.docEntry = :poCode and po.lineNum = :lineNumber")
    Double getPoQuantityByItemCodeAndPoCode(@Param("poCode") Integer poCode,
                                            @Param("lineNumber") Integer lineNumber);

}
