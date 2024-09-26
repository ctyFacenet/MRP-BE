package com.facenet.mrp.service.dto.mrp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class PurchaseOrderProgressDTO {

    private Long id;
    private List<String> prCodes;
    private String poCode;
    private List<String> soCodes;
    private List<String> mrpCodes;
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
