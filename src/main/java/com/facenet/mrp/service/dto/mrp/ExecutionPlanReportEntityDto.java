package com.facenet.mrp.service.dto.mrp;

import com.facenet.mrp.domain.mrp.ExecutionPlanReportEntity;
import com.facenet.mrp.service.dto.BaseDynamicDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link ExecutionPlanReportEntity}
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExecutionPlanReportEntityDto extends BaseDynamicDTO implements Serializable {
    Boolean isActive;
    Long id;
    @Size(max = 50)
    String nameCompare;
    Integer type;
    Double totalQuantity;
    Instant startDate;
    Instant endDate;
}
