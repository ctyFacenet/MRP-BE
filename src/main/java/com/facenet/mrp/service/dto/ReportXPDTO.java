package com.facenet.mrp.service.dto;

import java.time.Instant;

public class ReportXPDTO {
    private String soCode;                 // Mã SO/FC
    private String poCode;                   // Mã PO
    private String mrpCode;                  // Mã MRP
    private String itemCode;                 // Mã vật tư
    private String itemDescription;          // Mô tả vật tư
    private String vendorCode;               // Mã NCC
    private String vendorName;               // Tên NCC
    private Integer requiredPurchaseQty;     // Số lượng cần mua
    private Integer approvedPurchaseQty;     // Số lượng đã được phê duyệt đặt hàng
    private Instant approvalDate;          // Ngày duyệt
    private Integer receivedQty;             // Số lượng đã về
    private Instant arrivalDate;           // Ngày hàng về
    private Integer unreceivedQty;           // Số lượng chưa về
    private Double completionRate;           // Tỉ lệ hoàn thành
    private String status;                   // Tình trạng
    private String customerCode;             // Mã khách hàng
    private String customerName;             // Tên khách hàng
    private String orderer;                  // NGƯỜI ĐẶT HÀNG
    private String buyer;                    // NGƯỜI MUA HÀNG
    private Instant orderTime;         // Thời gian đặt hàng
    private Instant deliveryTime;      // Thời gian trả hàng
    private Integer totalOrderedQty;         // Tổng số lượng sản phẩm đặt hàng
    private Integer totalRequiredItemQty;    // Tổng số lượng vật tư cần mua
    private Integer totalPrItemQty;          // Tổng số lượng vật tư đã lên PR

    private Instant startTime;  //thời gian bắt đầu filter
    private Instant endTime;    //thời gian kết thúc filter

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getPoCode() {
        return poCode;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    public String getMrpCode() {
        return mrpCode;
    }

    public void setMrpCode(String mrpCode) {
        this.mrpCode = mrpCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Integer getRequiredPurchaseQty() {
        return requiredPurchaseQty;
    }

    public void setRequiredPurchaseQty(Integer requiredPurchaseQty) {
        this.requiredPurchaseQty = requiredPurchaseQty;
    }

    public Integer getApprovedPurchaseQty() {
        return approvedPurchaseQty;
    }

    public void setApprovedPurchaseQty(Integer approvedPurchaseQty) {
        this.approvedPurchaseQty = approvedPurchaseQty;
    }

    public Instant getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Instant approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Integer getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(Integer receivedQty) {
        this.receivedQty = receivedQty;
    }

    public Instant getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Instant arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Integer getUnreceivedQty() {
        return unreceivedQty;
    }

    public void setUnreceivedQty(Integer unreceivedQty) {
        this.unreceivedQty = unreceivedQty;
    }

    public Double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(Double completionRate) {
        this.completionRate = completionRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderer() {
        return orderer;
    }

    public void setOrderer(String orderer) {
        this.orderer = orderer;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public Instant getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Instant orderTime) {
        this.orderTime = orderTime;
    }

    public Instant getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Instant deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Integer getTotalOrderedQty() {
        return totalOrderedQty;
    }

    public void setTotalOrderedQty(Integer totalOrderedQty) {
        this.totalOrderedQty = totalOrderedQty;
    }

    public Integer getTotalRequiredItemQty() {
        return totalRequiredItemQty;
    }

    public void setTotalRequiredItemQty(Integer totalRequiredItemQty) {
        this.totalRequiredItemQty = totalRequiredItemQty;
    }

    public Integer getTotalPrItemQty() {
        return totalPrItemQty;
    }

    public void setTotalPrItemQty(Integer totalPrItemQty) {
        this.totalPrItemQty = totalPrItemQty;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }
}
