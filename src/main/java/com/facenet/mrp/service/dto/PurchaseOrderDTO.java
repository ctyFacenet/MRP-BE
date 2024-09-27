package com.facenet.mrp.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString
public class PurchaseOrderDTO {

    private Long id;
    private List<String> prCodes;
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
    private String receiveAddress;
    private String paymentType;
    private String paymentAddress;
    private Double totalSummary;
    private Double discountSummary;
    private Double taxSummary;
    private Double grossTotalSummary;
    private Double wholeDiscountPercent;
    private Double wholeDiscountValue;
    private Double wholeTaxPercent;
    private Double wholeTaxValue;
    private Double finalTotal;
    private Boolean isDraft;
    private List<PurchaseOrderItemDTO> items;

    @Setter
    @Getter
    @ToString
    public static class PurchaseOrderPurchaseRequestDTO {
        private String purchaseOrderId;
        private String purchaseRequestCode;
    }

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
        private Double discountValue;
        private Double taxPercent;
        private Double taxValue;
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
