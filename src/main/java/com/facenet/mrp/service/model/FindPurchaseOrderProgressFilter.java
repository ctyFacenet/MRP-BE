package com.facenet.mrp.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindPurchaseOrderProgressFilter {
    private Long id;
    private String prCode;
    private String poCode;
    private String soCode;
    private String mrpCode;
    private String vendorName;
    private String vendorCode;
    private Date orderDate;
    private Date deliveryDate;
    private String requestUser;
    private int buyingProgress;
    private int unmetDeadlineCount;
    private String status;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
}
