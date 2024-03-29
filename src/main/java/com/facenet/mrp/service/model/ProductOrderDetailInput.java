package com.facenet.mrp.service.model;

public class ProductOrderDetailInput {

    private int pageNumber;
    private int pageSize;
    private ProductOrderDetailFilter filter;

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

    public ProductOrderDetailFilter getFilter() {
        return filter;
    }

    public void setFilter(ProductOrderDetailFilter filter) {
        this.filter = filter;
    }
}
