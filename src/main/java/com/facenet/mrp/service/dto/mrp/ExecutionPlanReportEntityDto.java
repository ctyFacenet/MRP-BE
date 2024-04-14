package com.facenet.mrp.service.dto.mrp;

import com.facenet.mrp.domain.mrp.ExecutionPlanReportEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link ExecutionPlanReportEntity}
 */
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionPlanReportEntityDto implements Serializable {
    Long id;
    @Size(max = 50)
    String nameCompare;
    Integer type;
    Double totalQuantity;
    Instant startDate;
    Instant endDate;
}
