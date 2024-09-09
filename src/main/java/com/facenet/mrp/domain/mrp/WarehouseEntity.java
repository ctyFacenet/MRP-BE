package com.facenet.mrp.domain.mrp;

import javax.persistence.*;

@Entity
@Table(name = "warehouse", schema = "material_requirements_planning", catalog = "")
public class WarehouseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "warehouse")
    private Integer warehouse;
    @Basic
    @Column(name = "item_code")
    private String itemCode;
    @Basic
    @Column(name = "item_name")
    private String itemName;
    @Basic
    @Column(name = "remain")
    private Long remain;
    @Basic
    @Column(name = "unit")
    private String unit;
    @Basic
    @Column(name = "color")
    private String color;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "technical_name")
    private String technicalName;
    @Basic
    @Column(name = "group_code")
    private String group;
    @Basic
    @Column(name = "group_name")
    private String groupName;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "whs_code")
    private String whsCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Integer warehouse) {
        this.warehouse = warehouse;
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

    public Long getRemain() {
        return remain;
    }

    public void setRemain(Long remain) {
        this.remain = remain;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTechnicalName() {
        return technicalName;
    }

    public void setTechnicalName(String technicalName) {
        this.technicalName = technicalName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWhsCode() {
        return whsCode;
    }

    public void setWhsCode(String whsCode) {
        this.whsCode = whsCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WarehouseEntity that = (WarehouseEntity) o;

        if (id != that.id) return false;
        if (warehouse != null ? !warehouse.equals(that.warehouse) : that.warehouse != null) return false;
        if (itemCode != null ? !itemCode.equals(that.itemCode) : that.itemCode != null) return false;
        if (itemName != null ? !itemName.equals(that.itemName) : that.itemName != null) return false;
        if (remain != null ? !remain.equals(that.remain) : that.remain != null) return false;
        if (unit != null ? !unit.equals(that.unit) : that.unit != null) return false;
        if (color != null ? !color.equals(that.color) : that.color != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (technicalName != null ? !technicalName.equals(that.technicalName) : that.technicalName != null)
            return false;
        if (group != null ? !group.equals(that.group) : that.group != null) return false;
        if (groupName != null ? !groupName.equals(that.groupName) : that.groupName != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (warehouse != null ? warehouse.hashCode() : 0);
        result = 31 * result + (itemCode != null ? itemCode.hashCode() : 0);
        result = 31 * result + (itemName != null ? itemName.hashCode() : 0);
        result = 31 * result + (remain != null ? remain.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (technicalName != null ? technicalName.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (groupName != null ? groupName.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
