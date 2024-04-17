package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.ExecutionPlanReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExecutionPlanReportEntityRepository extends JpaRepository<ExecutionPlanReportEntity, Long> {
    List<ExecutionPlanReportEntity> findExecutionPlanReportEntitiesByNameCompare(String nameCompare);


}
