package com.facenet.mrp.service.response;

import com.facenet.mrp.domain.mrp.ColumnPropertyEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> extends CommonResponse<T> {

    private long dataCount;
    private Integer pageSize;
    private Integer pageNumber;
    private List<ColumnPropertyEntity> columns;

    public PageResponse(long dataCount) {
        this.dataCount = dataCount;
    }


    public PageResponse<T> success() {
        this.result = new Result().errorCode("00").message("Thành công").isOk(true);
        return this;
    }

    public PageResponse<T> columns(List<ColumnPropertyEntity> data) {
        this.columns = data;
        return this;
    }

    public PageResponse<T> dataCount(long dataCount) {
        this.dataCount = dataCount;
        return this;
    }

    @Override
    public PageResponse<T> result(String errorCode, String errorMessage, boolean ok) {
        super.result(errorCode, errorMessage, ok);
        return this;
    }

    @Override
    public PageResponse<T> errorCode(String errorCode) {
        super.errorCode(errorCode);
        return this;
    }

    @Override
    public PageResponse<T> message(String message) {
        super.message(message);
        return this;
    }

    @Override
    public PageResponse<T> isOk(boolean ok) {
        super.isOk(ok);
        return this;
    }

    @Override
    public PageResponse<T> data(T data) {
        super.data(data);
        return this;
    }

    public PageResponse pageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public PageResponse pageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Override
    public String toString() {
        return "PageResponse{" + "dataCount=" + dataCount + '}';
    }
}
