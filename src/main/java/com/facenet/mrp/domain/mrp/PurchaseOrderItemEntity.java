package com.facenet.mrp.domain.mrp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@ToString
@Entity
@Table(name = "purchase_order_item")
public class PurchaseOrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "unit")
    private String unit;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "price")
    private Double price;

    @Column(name = "total")
    private Double total;

    @Column(name = "discount_percent")
    private Double discountPercent;

    @Column(name = "tax_percent")
    private Double taxPercent;

    @Column(name = "gross_total")
    private Double grossTotal;

    @Column(name = "note")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrderEntity purchaseOrder;
}
