package com.facenet.mrp.repository.sap;

import com.facenet.mrp.domain.sap.Prq1Entity;
import com.facenet.mrp.domain.sap.Prq1EntityPK;
import com.facenet.mrp.service.dto.mrp.ItemQuantity;
import com.facenet.mrp.service.dto.mrp.ItemQuantityWithDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface Prq1Repository extends JpaRepository<Prq1Entity, Prq1EntityPK> {
    @Query("select new com.facenet.mrp.service.dto.mrp.ItemQuantity(pr.itemCode, sum(pr.openQty)) " +
        "from Prq1Entity pr " +
        "where pr.itemCode in :itemCode and pr.lineStatus = 'O'" +
        "group by pr.itemCode ")
    List<ItemQuantity> getOpenQuantity(@Param("itemCode") List<String> itemCode);

    default Map<String, Double> getOpenQuantityMap(List<String> itemCode) {
        return getOpenQuantity(itemCode).stream().collect(Collectors.toMap(
            ItemQuantity::getItemCode, ItemQuantity::getQuantity
        ));
    }

    @Query("select new com.facenet.mrp.service.dto.mrp.ItemQuantity(pr.itemCode, sum(pr.openQty)) " +
        "from Prq1Entity pr " +
        "where pr.lineStatus = 'O'" +
        "group by pr.itemCode ")
    List<ItemQuantity> getOpenQuantity();

    default Map<String, Double> getOpenQuantityMap() {
        return getOpenQuantity().stream().collect(Collectors.toMap(
            ItemQuantity::getItemCode, ItemQuantity::getQuantity
        ));
    }

    @Query("select p.quantity from Prq1Entity p " +
        "where p.itemCode = :itemCode and p.trgetEntry = :poCode")
    Double getPrQuantityByItemCodeAndPoCode(@Param("itemCode") String itemCode, @Param("poCode") Integer poCode);
}
