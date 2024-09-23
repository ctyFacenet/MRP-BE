package com.facenet.mrp.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindPurchaseOrderProgressFilter {
    private String soCode;
    private String mrpSubCode;
    private String itemCode;
    private String poCode;
    private String prCode;
    private String mrpCode;
    private String contractNumber;
    private String vendorCode;
    private String vendorName;
    private String poCreateUser;
    private Date poCreateDate;
    private Date dueDate;
    private String itemName;
    private Boolean getDetail;
}
