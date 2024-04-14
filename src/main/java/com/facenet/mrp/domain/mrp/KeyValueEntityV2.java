package com.facenet.mrp.domain.mrp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "key_value")
public class KeyValueEntityV2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //    @GenericGenerator(name = "machine_id_gen", strategy = "increment")
    @Column(name = "key_value_id", nullable = false)
    private Integer id;

    @Column(name = "entity_id")
    private Integer entityType;

    @Column(name = "key_id")
    private Integer keyId;


    @Column(name = "entity_key")
    private Integer entityKey;

    @Column(name = "common_value")
    private String commonValue;

    @ManyToOne
    @JoinColumn(name = "column_id")
    private ColumnPropertyEntity columnPropertyEntity;

    @Column(name = "string_value")
    private String stringValue;

    @Column(name = "int_value")
    private Integer intValue;

    @Column(name = "double_value")
    private Double doubleValue;

    @Column(name = "boolean_value")
    private Boolean booleanValue;

    @Column(name = "json_value")
    private String jsonValue;

    @Column(name = "date_value")
    private LocalDate dateValue;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
