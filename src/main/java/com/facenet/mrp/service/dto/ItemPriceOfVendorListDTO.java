package com.facenet.mrp.service.dto;

import java.util.List;

public class ItemPriceOfVendorListDTO {
    private Integer currentSelection;
    private List<ItemPriceOfVendorDTO> priceList;

    public Integer getCurrentSelection() {
        return currentSelection;
    }

    public void setCurrentSelection(Integer currentSelection) {
        this.currentSelection = currentSelection;
    }

    public List<ItemPriceOfVendorDTO> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<ItemPriceOfVendorDTO> priceList) {
        this.priceList = priceList;
    }
}
