package com.facenet.mrp.domain.mrp;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "product_work_order_daily_quantity")
@Entity
public class ProductWorkOrderDailyQuantityEntity extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Column(name = "assembly_quantity")
    private Double assemblyQuantity;

    @Column(name = "packing_quantity")
    private Double packingQuantity;

    @Column(name = "assembly_quantity_shift1")
    private Double assemblyQuantityShift1;

    @Column(name = "packing_quantity_shift1")
    private Double packingQuantityShift1;

    @Column(name = "assembly_quantity_shift2")
    private Double assemblyQuantityShift2;

    @Column(name = "packing_quantity_shift2")
    private Double packingQuantityShift2;

    private Integer error;

    @Column(name = "product_work_order_quantity_id")
    private Long productWorkOrderQuantityId;
}
