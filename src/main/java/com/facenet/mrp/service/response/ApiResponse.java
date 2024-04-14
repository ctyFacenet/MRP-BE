package com.facenet.mrp.service.response;

import java.util.List;

public class ApiResponse<T> {
    private Result result;
    private List<T> data;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}

