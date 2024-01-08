package com.facenet.mrp.domain.mrp;

import javax.persistence.*;

@Entity
@Table(name = "config", schema = "material_requirements_planning", catalog = "")
public class ConfigEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "config_id")
    private Integer configId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "value")
    private String value;

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
