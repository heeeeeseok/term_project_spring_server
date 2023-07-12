package com.example.term_project.main.response;


import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
public class BaseResponse {

    private final Boolean success;
    private final Integer code;
    private final String message;

    public BaseResponse() {
        this.success = true;
        this.code = ResponseCode.OK.getCode();
        this.message = ResponseCode.OK.getMessage();
    }

    public BaseResponse(ResponseCode errorCode) {
        this.success = false;
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }
}
