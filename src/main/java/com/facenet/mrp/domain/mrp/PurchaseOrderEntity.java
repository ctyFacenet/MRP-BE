package com.facenet.mrp.domain.mrp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString
@Entity
@Table(name = "purchase_order")
public class PurchaseOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "po_code", nullable = false)
    private String poCode;

    @Column(name = "vendor_name", nullable = false)
    private String vendorName;

    @Column(name = "vendor_code", nullable = false)
    private String vendorCode;

    @Column(name = "order_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @Column(name = "delivery_date")
    @Temporal(TemporalType.DATE)
    private Date deliveryDate;

    @Column(name = "request_user", nullable = false)
    private String requestUser;

    @Column(name = "note")
    private String note;

    @Column(name = "unit")
    private String unit;

    @Column(name = "shipping_type")
    private String shippingType;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseOrderItemEntity> items;
}
