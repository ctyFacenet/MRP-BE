package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.WarehouseEntity;
import com.facenet.mrp.service.dto.InventoryDetailDTO;
import com.facenet.mrp.service.dto.mrp.CurrentInventory;
import com.facenet.mrp.service.dto.mrp.CurrentWarehouseInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface WarehouseEntityRepository extends JpaRepository<WarehouseEntity, Integer>, JpaSpecificationExecutor<WarehouseEntity> {
    void deleteAllByWarehouse(int warehouse);
    List<WarehouseEntity> findAllByWarehouse(int warehouse);

    @Query(value = "select new com.facenet.mrp.service.dto.InventoryDetailDTO(" +
        "w.remain) " +
        "from WarehouseEntity as w " +
        "where w.itemCode = :itemCode and w.warehouse = :type")
    List<InventoryDetailDTO> getInventoryDetailByItemCode(@Param("itemCode") String itemCode, @Param("type") Integer type);

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.CurrentInventory(itemCode , sum(remain))" +
        " from WarehouseEntity" +
        " where warehouse in (:warehouseList)" +
        " group by itemCode")
    List<CurrentInventory> getAllCurrentInventory(@Param("warehouseList") List<Integer> warehouseList);

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.CurrentWarehouseInventory(itemCode , remain, whsCode)" +
        " from WarehouseEntity" +
        " where remain <> 0 and warehouse in (2,3)")
    List<CurrentWarehouseInventory> getAllCurrentInventoryWithWhs();

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.CurrentInventory(itemCode , sum(remain))" +
        " from WarehouseEntity" +
        " where itemCode in (:listItemCode)" +
        " and whsCode in (:listWhsCode)" +
        " group by itemCode")
    List<CurrentInventory> getAllCurrentInventoryByWhs(@Param("listItemCode") Collection<String> listItemCode,
                                                       @Param("listWhsCode") Collection<String> listWhsCode);

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.CurrentWarehouseInventory(itemCode , remain, whsCode)" +
        " from WarehouseEntity" +
        " where itemCode in (:listItemCode)" +
        " and whsCode in (:listWhsCode) and remain <> 0")
    List<CurrentWarehouseInventory> getAllCurrentInventoryWithWhs(@Param("listItemCode")List<String> listItemCode,
                                                                  @Param("listWhsCode")List<String> listWhsCode);

    @Query("select new com.facenet.mrp.service.dto.mrp.CurrentInventory(itemCode, sum(remain)) " +
        "from WarehouseEntity " +
        "where whsCode in (:warehouses) and itemCode = :itemCode " +
        "group by itemCode")
    List<CurrentInventory> getInStockQuantityByItemCodeAndWhs(@Param("itemCode") String itemCode, @Param("warehouses") List<String> warehouses);

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.CurrentInventory(itemCode , sum(remain))" +
        " from WarehouseEntity" +
        " where itemCode in (:listItemCode)" +
        " group by itemCode")
    List<CurrentInventory> getAllInStockQuantityByItemCode(@Param("listItemCode")List<String> listItemCode);

}
