package com.facenet.mrp.service.dto.mrp;

import com.facenet.mrp.domain.mrp.ExecutionPlanReportDetailQuantityEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link ExecutionPlanReportDetailQuantityEntity}
 */
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionPlanReportDetailQuantityEntityDto implements Serializable {
    Long id;
    ExecutionPlanReportDetailEntityDto idExecutionPlanReportDetail;
    Integer quantity;
}
