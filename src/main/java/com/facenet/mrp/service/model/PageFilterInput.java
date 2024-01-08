package com.facenet.mrp.service.model;

import javax.validation.constraints.NotNull;

public class PageFilterInput<T> {
    @NotNull(message = "pageNumber must not be null")
    private Integer pageNumber;

    @NotNull(message = "pageSize must not be null")
    private Integer pageSize;

    @NotNull(message = "filter must not be null")
    private T filter;

    public PageFilterInput() {
    }

    public PageFilterInput(Integer pageNumber, Integer pageSize, T filter) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.filter = filter;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public T getFilter() {
        return filter;
    }

    public void setFilter(T filter) {
        this.filter = filter;
    }
}
