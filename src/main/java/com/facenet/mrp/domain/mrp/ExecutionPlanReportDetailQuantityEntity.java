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

@Getter
@Setter
@Entity
@Table(name = "execution_plan_report_detail_quantity")
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionPlanReportDetailQuantityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_execution_plan_report_detail")
    private ExecutionPlanReportDetailEntity idExecutionPlanReportDetail;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "order")
    private Integer order;

}
