package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.ExecutionPlanReportDetailEntity;
import com.facenet.mrp.domain.mrp.ExecutionPlanReportDetailQuantityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExecutionPlanReportDetailQuantityEntityRepository extends JpaRepository<ExecutionPlanReportDetailQuantityEntity, Long> {
    @Query("select e from ExecutionPlanReportDetailQuantityEntity e where e.idExecutionPlanReportDetail.id= :id ")
    List<ExecutionPlanReportDetailQuantityEntity> getAllById(@Param("id") Long id);
}
