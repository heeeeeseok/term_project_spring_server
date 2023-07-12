package com.example.term_project.main.response;

import lombok.Getter;

@Getter
public class ResponseException extends RuntimeException{
    private final ResponseCode errorCode;

    public ResponseException() {
        super(ResponseCode.INTERNAL_ERROR.getMessage());
        this.errorCode = ResponseCode.INTERNAL_ERROR;
    }

    public ResponseException(String message) {
        super(ResponseCode.INTERNAL_ERROR.getMessage(message));
        this.errorCode = ResponseCode.INTERNAL_ERROR;
    }

    public ResponseException(String message, Throwable cause) {
        super(ResponseCode.INTERNAL_ERROR.getMessage(message), cause);
        this.errorCode = ResponseCode.INTERNAL_ERROR;
    }

    public ResponseException(Throwable cause) {
        super(ResponseCode.INTERNAL_ERROR.getMessage(cause));
        this.errorCode = ResponseCode.INTERNAL_ERROR;
    }

    public ResponseException(ResponseCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ResponseException(ResponseCode errorCode, String message) {
        super(errorCode.getMessage(message));
        this.errorCode = errorCode;
    }

    public ResponseException(ResponseCode errorCode, String message, Throwable cause) {
        super(errorCode.getMessage(message), cause);
        this.errorCode = errorCode;
    }

    public ResponseException(ResponseCode errorCode, Throwable cause) {
        super(errorCode.getMessage(cause), cause);
        this.errorCode = errorCode;
    }
}
