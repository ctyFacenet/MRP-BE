package com.facenet.mrp.domain.mrp;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "vendor", schema = "material_requirements_planning", catalog = "")
public class VendorEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendorSeqGen")
    @GenericGenerator(name = "vendorSeqGen", strategy = "increment")
    @Id
    @Column(name = "vendor_id")
    private Integer vendorId;
    @Basic
    @Column(name = "vendor_code")
    private String vendorCode;
    @Basic
    @Column(name = "vendor_name")
    private String vendorName;

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VendorEntity that = (VendorEntity) o;

        if (vendorId != null ? !vendorId.equals(that.vendorId) : that.vendorId != null) return false;
        if (vendorCode != null ? !vendorCode.equals(that.vendorCode) : that.vendorCode != null) return false;
        if (vendorName != null ? !vendorName.equals(that.vendorName) : that.vendorName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = vendorId != null ? vendorId.hashCode() : 0;
        result = 31 * result + (vendorCode != null ? vendorCode.hashCode() : 0);
        result = 31 * result + (vendorName != null ? vendorName.hashCode() : 0);
        return result;
    }
}
