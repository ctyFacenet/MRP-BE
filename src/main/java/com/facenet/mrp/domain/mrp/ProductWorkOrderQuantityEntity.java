package com.facenet.mrp.domain.mrp;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "product_work_order_quantity")
@Entity
public class ProductWorkOrderQuantityEntity extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "wo_name")
    private String woName;

    @Column(name = "scada_asset_id")
    private String scadaAssetId;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "branch_code")
    private String branchCode;

    @Column(name = "group_code")
    private String groupCode;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "lot_number")
    private String lotNumber;

    @Column(name = "sap_wo")
    private String sapWO;

    @Column(name = "line")
    private String line;

    @Column(name = "total_assembly_quantity")
    private Double totalAssemblyQuantity;

    @Column(name = "total_packing_quantity")
    private Double totalPackingQuantity;

    @Column(name = "total_error")
    private Integer totalError;
}
