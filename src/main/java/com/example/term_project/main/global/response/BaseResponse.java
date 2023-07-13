package com.example.term_project.main.global.response;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BaseResponse<T> {

    private final Boolean success;
    private final Integer code;
    private final String message;
    private final T data;

    public BaseResponse(T data) {
        this.success = true;
        this.code = ResponseCode.OK.getCode();
        this.message = ResponseCode.OK.getMessage();
        this.data = data;
    }

    public BaseResponse(ResponseCode errorCode) {
        this.success = false;
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.data = null;
    }
}
