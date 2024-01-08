package com.facenet.mrp.repository.sap;

import com.facenet.mrp.domain.sap.OitwEntity;
import com.facenet.mrp.service.dto.mrp.CurrentInventory;
import com.facenet.mrp.service.dto.mrp.CurrentWarehouseInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface OitwRepository  extends JpaRepository<OitwEntity,String> {

    @Query("select o from OitwEntity o where o.itemCode = ?1")
    List<OitwEntity> getOitwEntityByItemCode(String itemCode);

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.CurrentInventory(itemCode , sum(onHand))" +
        " from OitwEntity" +
        " where itemCode in (:listItemCode)" +
        " and whsCode in (:listWhsCode)" +
        " group by itemCode")
    List<CurrentInventory> getAllCurrentInventoryByWhs(@Param("listItemCode") Collection<String> listItemCode,
                                                       @Param("listWhsCode") Collection<String> listWhsCode);

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.CurrentInventory(itemCode , sum(onHand))" +
        " from OitwEntity" +
        " where whsCode in (:listWhsCode)" +
        " group by itemCode")
    List<CurrentInventory> getAllCurrentInventoryByWhs(@Param("listWhsCode") Collection<String> listWhsCode);

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.CurrentWarehouseInventory(itemCode , onHand, whsCode)" +
        " from OitwEntity" +
        " where itemCode in (:listItemCode)" +
        " and whsCode in (:listWhsCode) and onHand <> 0")
    List<CurrentWarehouseInventory> getAllCurrentInventoryWithWhs(@Param("listItemCode")List<String> listItemCode,
                                                                  @Param("listWhsCode")List<String> listWhsCode);

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.CurrentWarehouseInventory(itemCode , onHand, whsCode)" +
        " from OitwEntity" +
        " where onHand <> 0")
    List<CurrentWarehouseInventory> getAllCurrentInventoryWithWhs();

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.CurrentInventory(itemCode , sum(onHand))" +
        " from OitwEntity" +
        " where itemCode in (:listItemCode)" +
        " group by itemCode")
    List<CurrentInventory> getAllInStockQuantityByItemCode(@Param("listItemCode")List<String> listItemCode);

    @Query("select new com.facenet.mrp.service.dto.mrp.CurrentInventory(itemCode, sum(onHand)) " +
        "from OitwEntity " +
        "where whsCode in (:warehouses) and itemCode = :itemCode " +
        "group by itemCode")
    List<CurrentInventory> getInStockQuantityByItemCodeAndWhs(@Param("itemCode") String itemCode, @Param("warehouses") List<String> warehouses);
}
