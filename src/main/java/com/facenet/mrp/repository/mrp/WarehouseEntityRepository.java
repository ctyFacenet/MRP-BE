package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.WarehouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface WarehouseEntityRepository extends JpaRepository<WarehouseEntity, Integer>, JpaSpecificationExecutor<WarehouseEntity> {
    void deleteAllByWarehouse(int warehouse);
    List<WarehouseEntity> findAllByWarehouse(int warehouse);
}
