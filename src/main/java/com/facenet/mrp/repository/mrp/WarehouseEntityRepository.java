package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.WarehouseEntity;
import com.facenet.mrp.service.dto.InventoryDetailDTO;
import com.facenet.mrp.service.dto.mrp.CurrentInventory;
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
        " where type in (:typeList)" +
        " group by itemCode")
    List<CurrentInventory> getAllCurrentInventory(@Param("typeList") List<Integer> typeList);
}
