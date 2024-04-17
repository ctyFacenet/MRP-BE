package com.facenet.mrp.service.dto.mrp;

import com.facenet.mrp.domain.mrp.ExecutionPlanReportDetailQuantityEntity;
import com.facenet.mrp.service.dto.BaseDynamicDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link ExecutionPlanReportDetailQuantityEntity}
 */
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionPlanReportDetailQuantityEntityDto extends BaseDynamicDTO implements Serializable {
    Long id;
    ExecutionPlanReportDetailEntityDto idExecutionPlanReportDetail;
    Integer quantity;
}
