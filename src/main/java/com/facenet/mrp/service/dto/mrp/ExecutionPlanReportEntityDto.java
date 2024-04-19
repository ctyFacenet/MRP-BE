package com.facenet.mrp.service.dto.mrp;

import com.facenet.mrp.domain.mrp.ExecutionPlanReportEntity;
import com.facenet.mrp.service.dto.BaseDynamicDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link ExecutionPlanReportEntity}
 */
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

    public ExecutionPlanReportEntityDto() {
    }

    public ExecutionPlanReportEntityDto(Boolean isActive, Long id, String nameCompare, Integer type, Double totalQuantity, Instant startDate, Instant endDate) {
        this.isActive = isActive;
        this.id = id;
        this.nameCompare = nameCompare;
        this.type = type;
        this.totalQuantity = totalQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
