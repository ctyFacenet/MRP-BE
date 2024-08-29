package com.facenet.mrp.service.dto.mrp;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.facenet.mrp.domain.mrp.ItemEntity}
 */
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntityDto implements Serializable {
    private Integer itemId;
    private String itemCode;
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

    public ItemEntityDto(String itemCode, String itemName) {
        this.itemCode = itemCode;
        this.itemName = itemName;
    }
}
