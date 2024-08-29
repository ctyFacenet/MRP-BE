package com.facenet.mrp.service.dto.mrp;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.facenet.mrp.domain.mrp.VendorItemEntity}
 */
@NoArgsConstructor
@AllArgsConstructor
public class VendorItemEntityDto implements Serializable {
    private String vendorCode;
    private String itemCode;
    private Integer timeUsed;
    private Integer sap;


    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Integer getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(Integer timeUsed) {
        this.timeUsed = timeUsed;
    }

    public VendorItemEntityDto(String vendorCode, String itemCode) {
        this.vendorCode = vendorCode;
        this.itemCode = itemCode;
    }

    public Integer getSap() {
        return sap;
    }

    public void setSap(Integer sap) {
        this.sap = sap;
    }
}
