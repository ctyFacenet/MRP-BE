package com.facenet.mrp.domain.mrp;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@ToString
@Entity
@Table(name = "purchase_order_purchase_request")
@AllArgsConstructor // Tạo constructor với tất cả các tham số
@NoArgsConstructor // Tạo constructor không tham số
public class PurchaseorderPurchaseRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "purchase_order_id")
    private Long purchaseOrderId;

    @Column(name = "purchase_request_code")
    private String purchaseRequestCode;
}
