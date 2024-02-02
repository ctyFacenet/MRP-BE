package com.facenet.mrp.domain.mrp;

import javax.persistence.*;

@Entity
@Table(name = "vendor_item", schema = "material_requirements_planning", catalog = "")
@IdClass(VendorItemEntityPK.class)
public class VendorItemEntity {
    @Id
    @Column(name = "vendor_code")
    private String vendorCode;
    @Id
    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "time_used")
    private Integer timeUsed = 0;

    public VendorItemEntity() {
    }

    public VendorItemEntity(String vendorCode, String itemCode) {
        this.vendorCode = vendorCode;
        this.itemCode = itemCode;
    }

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

    public void incrementTimeUsed() {
        if (timeUsed == null) timeUsed = 0;
        this.timeUsed++;
    }
}
