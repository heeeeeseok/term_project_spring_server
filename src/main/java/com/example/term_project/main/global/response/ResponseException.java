package com.example.term_project.main.global.response;

import lombok.Getter;

@Getter
public class ResponseException extends RuntimeException{
    private final ResponseCode errorCode;

    public ResponseException(ResponseCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
