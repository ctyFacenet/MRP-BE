package com.facenet.mrp.repository.sap;

import com.facenet.mrp.domain.sap.OwhsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwhsRepository extends JpaRepository<OwhsEntity, String> {
    @Query(value = "select o from OwhsEntity o")
    List<OwhsEntity> getOwhs();

    @Query("SELECT o FROM OwhsEntity o WHERE o.whsCode IN :warehouseCodes")
    List<OwhsEntity> findByWarehouseCodes(@Param("warehouseCodes") List<String> warehouseCodes);
}
