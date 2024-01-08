package com.facenet.mrp.service.model;

public class ProductOrderInput {

    private int pageNumber;
    private int pageSize;
    private ProductOrderFilter filter;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public ProductOrderFilter getFilter() {
        return filter;
    }

    public void setFilter(ProductOrderFilter filter) {
        this.filter = filter;
    }



}
