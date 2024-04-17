package com.facenet.mrp.domain.mrp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "execution_plan_report_detail")
@AllArgsConstructor
@NoArgsConstructor
public class ExecutionPlanReportDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_execution_plan_report")
    private ExecutionPlanReportEntity idExecutionPlanReport;
    @Size(max = 200)
    @Column(name = "product_code", length = 200)
    private String productCode;

    @Size(max = 200)
    @Column(name = "product_name", length = 200)
    private String productName;

    @Size(max = 200)
    @Column(name = "version", length = 200)
    private String version;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @Column(name = "is_active")
    private Boolean isActive;



}
