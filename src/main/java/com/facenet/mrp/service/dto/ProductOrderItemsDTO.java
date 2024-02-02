package com.facenet.mrp.service.dto;

import com.facenet.mrp.service.utils.Constants;

public class ProductOrderItemsDTO {
    private Integer productId;
    private String productCode;

    private String productOrderChild;
    private String productName;
    private String bomVersion;
    private Integer status;

    public ProductOrderItemsDTO(Integer productId, String productCode, String productName, String bomVersion, Integer status) {
        this.productId = productId;
        this.productCode = productCode;
        this.productName = productName;
        this.bomVersion = bomVersion;
        this.status = status;
    }

    public ProductOrderItemsDTO(Integer productId, String productCode, String productName, String productOrderChild, String bomVersion, Integer status) {
        this.productId = productId;
        this.productCode = productCode;
        this.productName = productName;
        this.productOrderChild = productOrderChild;
        this.bomVersion = bomVersion;
        this.status = status;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBomVersion() {
        return bomVersion;
    }

    public void setBomVersion(String bomVersion) {
        this.bomVersion = bomVersion;
    }

    public Integer getStatus() {
        return status != Constants.ProductOrder.STATUS_NEW ? Constants.ProductOrder.STATUS_ORDER_ANALYTICS : Constants.ProductOrder.STATUS_NEW;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductOrderChild() {
        return productOrderChild;
    }

    public void setProductOrderChild(String productOrderChild) {
        this.productOrderChild = productOrderChild;
    }
}
