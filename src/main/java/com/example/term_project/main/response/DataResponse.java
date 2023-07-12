package com.example.term_project.main.response;

import lombok.Getter;

@Getter
public class DataResponse<T> extends BaseResponse {

    private final T data;

    public DataResponse(T data) {
        super();
        this.data = data;
    }
}
