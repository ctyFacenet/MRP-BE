package com.facenet.mrp.domain.mrp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "execution_plan_report")
@AllArgsConstructor
@NoArgsConstructor
public class ExecutionPlanReportEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 50)
    @Column(name = "nameCompare", length = 50)
    private String nameCompare;

    @Column(name = "type")
    private Integer type;

    @Column(name = "totalQuantity")
    private Double totalQuantity;

    @Column(name = "startDate")
    private Instant startDate;

    @Column(name = "endDate")
    private Instant endDate;

}
