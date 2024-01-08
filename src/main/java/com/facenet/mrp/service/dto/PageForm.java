package com.facenet.mrp.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PageForm {
    private Integer pageNumber;
    private Integer pageSize;

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
    @JsonCreator
    public PageForm(@JsonProperty("pageSize") Integer pageSize, @JsonProperty("pageNumber") Integer pageNumber) {
        this.pageNumber=pageNumber;
        this.pageSize=pageSize;
    }
}
