package com.facenet.mrp.domain.mrp;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "item_2", schema = "material_requirements_planning", catalog = "")
public class ItemEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemSeqGen")
    @GenericGenerator(name = "itemSeqGen", strategy = "increment")
    @Id
    @Column(name = "item_id")
    private Integer itemId;
    @Basic
    @Column(name = "item_code")
    private String itemCode;
    @Basic
    @Column(name = "item_name")
    private String itemName;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemEntity that = (ItemEntity) o;

        if (itemId != null ? !itemId.equals(that.itemId) : that.itemId != null) return false;
        if (itemCode != null ? !itemCode.equals(that.itemCode) : that.itemCode != null) return false;
        if (itemName != null ? !itemName.equals(that.itemName) : that.itemName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = itemId != null ? itemId.hashCode() : 0;
        result = 31 * result + (itemCode != null ? itemCode.hashCode() : 0);
        result = 31 * result + (itemName != null ? itemName.hashCode() : 0);
        return result;
    }
}
