package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.ForecastTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ForecastTableRepository extends JpaRepository<ForecastTableEntity,Long> {
    @Query("select count(a) from ForecastTableEntity a where a.parentFc = :parent")
    Integer countFc(@Param("parent") Integer parent);
}
