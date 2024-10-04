package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.WarehouseChosenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseChosenEntityRepository extends JpaRepository<WarehouseChosenEntity, Integer>
{

}
