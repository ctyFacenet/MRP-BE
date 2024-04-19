package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.ExecutionPlanReportDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExecutionPlanReportDetailEntityRepository extends JpaRepository<ExecutionPlanReportDetailEntity, Long> {

    @Query("select e from ExecutionPlanReportDetailEntity e where e.idExecutionPlanReport.id = :id and e.isActive = true ")
    List<ExecutionPlanReportDetailEntity> getAllById(@Param("id") Long id);

    @Query("select e.type from ExecutionPlanReportEntity e where e.id = :id and e.isActive = true ")
    Integer getType(@Param("id") Long id);
}
