package com.facenet.mrp.domain.mrp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class ExecutionPlanReportEntity extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Size(max = 50)
    @Column(name = "name_compare", length = 50)
    private String nameCompare;

    @Column(name = "type")
    private Integer type;

    @Column(name = "total_quantity")
    private Double totalQuantity;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "is_active")
    private Boolean isActive;

}
