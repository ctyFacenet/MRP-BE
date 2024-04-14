package com.facenet.mrp.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeyDictionaryDTO implements Serializable {

    private Integer id;
    private String keyName;
    private String keyTitle;
    private String width = "200px";
    private Boolean check;
    private Integer dataType;
    private Integer entityType;
    private Integer entryIndex;
    private Boolean isRequired;
    private Integer isFixed;

    public KeyDictionaryDTO(
        Integer id,
        String keyName,
        String keyTitle,
        String width,
        Boolean check,
        Integer dataType,
        Integer entityType,
        Integer entryIndex,
        Integer isFixed
    ) {
        this.id = id;
        this.keyName = keyName;
        this.keyTitle = keyTitle;
        this.width = width;
        this.check = check;
        this.dataType = dataType;
        this.entityType = entityType;
        this.entryIndex = entryIndex;
        this.isFixed = isFixed;
    }

    public KeyDictionaryDTO(String keyName, String keyTitle, String width, Boolean check, Integer dataType, Integer entityType) {
        this.keyName = keyName;
        this.keyTitle = keyTitle;
        this.width = width;
        this.check = check;
        this.dataType = dataType;
        this.entityType = entityType;
    }

}
