package com.example.term_project.main.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {
    OK(0, HttpStatus.OK, "Ok"),

    SIGNUP_WITH_DUPLICATED_EMAIL(1000, HttpStatus.BAD_REQUEST, "중복된 이메일 입니다"),

    SIGNUP_WITH_DUPLICATED_USERNAME(1001, HttpStatus.BAD_REQUEST, "중복된 닉네임 입니다"),

    LOGIN_WITH_WRONG_EMAIL(1002, HttpStatus.BAD_REQUEST, "이메일을 확인해 주세요"),

    LOGIN_WITH_WRONG_PASSWORD(1003, HttpStatus.BAD_REQUEST, "비밀번호를 확인해 주세요"),

    S3_UPLOAD_FAILED(2001, HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 실패"),

    S3_DELETE_FAILED(2002, HttpStatus.INTERNAL_SERVER_ERROR, "파일 삭제 실패"),

    S3_DELETE_NOT_FOUND(2003, HttpStatus.BAD_REQUEST, "파일을 찾을 수 없음"),

    S3_INVALID_FILE(2004, HttpStatus.BAD_REQUEST, "잘못된 형식의 파일입니다."),

    BAD_REQUEST(10004, HttpStatus.BAD_REQUEST, "Bad request"),

    VALIDATION_ERROR(10001, HttpStatus.BAD_REQUEST, "Validation error"),

    NOT_FOUND(10002, HttpStatus.NOT_FOUND, "Requested resource is not found"),

    INTERNAL_ERROR(20000, HttpStatus.INTERNAL_SERVER_ERROR, "Internal error"),

    DATA_ACCESS_ERROR(20001, HttpStatus.INTERNAL_SERVER_ERROR, "Data access error"),

    UNAUTHORIZED(40000, HttpStatus.UNAUTHORIZED, "User unauthorized");
    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;

    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
        // 결과 예시 - "Validation error - Reason why it isn't valid"
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", this.name(), this.getCode());
    }
}
