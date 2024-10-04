package com.facenet.mrp.domain.mrp;

import javax.persistence.*;

@Entity
@Table(name = "warehouse_chosen", schema = "material_requirements_planning", catalog = "")
public class WarehouseChosenEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "warehouse")
    private String warehouse;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WarehouseChosenEntity that = (WarehouseChosenEntity) o;

        if (id != that.id) return false;
        if (warehouse != null ? !warehouse.equals(that.warehouse) : that.warehouse != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (warehouse != null ? warehouse.hashCode() : 0);
        return result;
    }
}
