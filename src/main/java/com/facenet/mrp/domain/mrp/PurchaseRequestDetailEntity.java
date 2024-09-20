package com.facenet.mrp.domain.mrp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "purchase_request_detail", schema = "material_requirements_planning", catalog = "")
public class PurchaseRequestDetailEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "pr_code")
    private String prCode;
    @Basic
    @Column(name = "so_code")
    private String soCode;
    @Basic
    @Column(name = "item_code")
    private String itemCode;
    @Basic
    @Column(name = "item_name")
    private String itemName;
    @Basic
    @Column(name = "required_quantity")
    private Double requiredQuantity;
    @Basic
    @Column(name = "due_date")
    private Timestamp dueDate;
    @Basic
    @Column(name = "note")
    private String note;
    @Basic
    @Column(name = "mrp_code")
    private String mrpCode;
    @Basic
    @Column(name = "is_active")
    private Integer isActive;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrCode() {
        return prCode;
    }

    public void setPrCode(String prCode) {
        this.prCode = prCode;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(Double requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public Timestamp getDueDate() {
        return dueDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMrpCode() {
        return mrpCode;
    }

    public void setMrpCode(String mrpCode) {
        this.mrpCode = mrpCode;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchaseRequestDetailEntity that = (PurchaseRequestDetailEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (prCode != null ? !prCode.equals(that.prCode) : that.prCode != null) return false;
        if (soCode != null ? !soCode.equals(that.soCode) : that.soCode != null) return false;
        if (itemCode != null ? !itemCode.equals(that.itemCode) : that.itemCode != null) return false;
        if (itemName != null ? !itemName.equals(that.itemName) : that.itemName != null) return false;
        if (requiredQuantity != null ? !requiredQuantity.equals(that.requiredQuantity) : that.requiredQuantity != null)
            return false;
        if (dueDate != null ? !dueDate.equals(that.dueDate) : that.dueDate != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (mrpCode != null ? !mrpCode.equals(that.mrpCode) : that.mrpCode != null) return false;
        if (isActive != null ? !isActive.equals(that.isActive) : that.isActive != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (prCode != null ? prCode.hashCode() : 0);
        result = 31 * result + (soCode != null ? soCode.hashCode() : 0);
        result = 31 * result + (itemCode != null ? itemCode.hashCode() : 0);
        result = 31 * result + (itemName != null ? itemName.hashCode() : 0);
        result = 31 * result + (requiredQuantity != null ? requiredQuantity.hashCode() : 0);
        result = 31 * result + (dueDate != null ? dueDate.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (mrpCode != null ? mrpCode.hashCode() : 0);
        result = 31 * result + (isActive != null ? isActive.hashCode() : 0);
        return result;
    }
}
