package com.facenet.mrp.service.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString
public class CreatePurchaseOrderDTO {

    private String poCode;
    private String vendorName;
    private String vendorCode;
    private Date orderDate;
    private Date deliveryDate;
    private String requestUser;
    private String status;
    private String note;
    private String unit;
    private String shippingType;
    private List<PurchaseOrderItemDTO> items;

    @Setter
    @Getter
    @ToString
    public static class PurchaseOrderItemDTO {
        private String itemCode;
        private String itemName;
        private String unit;
        private Double quantity;
        private Double price;
        private Double total;
        private Double discountPercent;
        private Double taxPercent;
        private Double grossTotal;
        private String note;
        private List<PurchaseOrderItemProgressDTO> progress;
    }

    @Setter
    @Getter
    @ToString
    public static class PurchaseOrderItemProgressDTO {
        private Date date;
        private Double quantity;
    }
}
