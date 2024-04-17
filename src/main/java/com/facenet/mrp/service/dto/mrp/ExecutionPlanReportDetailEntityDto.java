package com.facenet.mrp.service.dto.mrp;

import com.facenet.mrp.domain.mrp.ExecutionPlanReportDetailEntity;
import com.facenet.mrp.service.dto.BaseDynamicDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * DTO for {@link ExecutionPlanReportDetailEntity}
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExecutionPlanReportDetailEntityDto extends BaseDynamicDTO implements Serializable {
    Long id;
    @Size(max = 200)
    String productCode;
    @Size(max = 200)
    String productName;
    @Size(max = 200)
    String version;
    Integer totalQuantity;
    Long idExecutionPlanReport;
    Boolean isActive;
}
