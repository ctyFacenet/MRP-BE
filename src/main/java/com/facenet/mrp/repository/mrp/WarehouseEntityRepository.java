package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.WarehouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarehouseEntityRepository extends JpaRepository<WarehouseEntity, Integer> {
    void deleteAllByWarehouse(int warehouse);
    List<WarehouseEntity> findAllByWarehouse(int warehouse);
}
