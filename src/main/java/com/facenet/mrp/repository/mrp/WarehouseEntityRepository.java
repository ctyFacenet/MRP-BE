package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.WarehouseEntity;
import com.facenet.mrp.service.dto.InventoryDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WarehouseEntityRepository extends JpaRepository<WarehouseEntity, Integer>, JpaSpecificationExecutor<WarehouseEntity> {
    void deleteAllByWarehouse(int warehouse);
    List<WarehouseEntity> findAllByWarehouse(int warehouse);

    @Query(value = "select new com.facenet.mrp.service.dto.InventoryDetailDTO(" +
        "CAST(w.remain AS long)) " +
        "from WarehouseEntity as w " +
        "where w.itemCode = :itemCode and w.warehouse = :type")
    List<InventoryDetailDTO> getInventoryDetailByItemCode(@Param("itemCode") String itemCode, @Param("type") Integer type);
}
