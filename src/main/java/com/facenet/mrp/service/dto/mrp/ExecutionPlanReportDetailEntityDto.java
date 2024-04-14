package com.facenet.mrp.service.dto.mrp;

import com.facenet.mrp.domain.mrp.ExecutionPlanReportDetailEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * DTO for {@link ExecutionPlanReportDetailEntity}
 */
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionPlanReportDetailEntityDto implements Serializable {
    Long id;
    ExecutionPlanReportEntityDto idExcutionPlanReport;
    @Size(max = 200)
    String productCode;
    @Size(max = 200)
    String productName;
    @Size(max = 200)
    String version;
    Integer totalQuantity;
}
