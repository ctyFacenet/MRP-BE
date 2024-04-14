package com.facenet.mrp.domain.mrp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
//@Table(name = "column_properties")
@Table(name = "column_properties")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ColumnPropertyEntity {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //    @GenericGenerator(name = "column_properties_id_gen", strategy = "increment")
    @Column(name = "column_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "key_name")
    private String keyName;

    @Size(max = 255)
    @Column(name = "key_title")
    private String keyTitle;

    @Size(max = 20)
    @Column(name = "width", length = 20)
    private String width;

    @Column(name = "entity_type")
    private Integer entityType;

    @Column(name = "is_visible")
    private Boolean check;

    @Column(name = "data_type")
    private Integer dataType;

    @Column(name = "is_active")
    @JsonIgnore
    private Boolean isActive = true;

    @Column(name = "entry_index")
    @JsonProperty("entryIndex")
    private Integer index;

    @Column(name = "is_fixed")
    private Integer isFixed = 0;

    @Column(name = "is_required")
    private Boolean isRequired;
    @Column(name = "is_deletable")
    private Boolean isDeletable;

    public Boolean getDeletable() {
        return isDeletable;
    }

    public void setDeletable(Boolean deletable) {
        isDeletable = deletable;
    }

    public String getDataTypeConvertString() {
        switch (dataType) {
            case 1:
                return "Integer";
            case 2:
                return "Float";
            case 3:
                return "String";
            case 4:
                return "Json";
            case 5:
                return "Date";
            case 6:
                return "Boolean";
            default:
                return null;
        }
    }

    public String getCheckConvertString() {
        if (check != null && check) {
            return "Hiển thị";
        } else {
            return "Không hiển thị";
        }
    }

    public String getIsRequiredConvertString() {
        if (isRequired != null && isRequired) {
            return "Bắt buộc";
        } else {
            return "Không bắt buộc";
        }
    }
}
