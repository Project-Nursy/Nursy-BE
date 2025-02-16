package com.nursy.nursy.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode implements ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류"),//500
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청"),//400
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근이 거부되었습니다"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유효하지 않은 사용자 입니다."),

    //ward
    WARD_ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 병동에 대한 접근 권한이 없습니다."),

    //nurse
    NURSE_NOT_FOUND(HttpStatus.NOT_FOUND, "간호사를 찾을 수 없습니다.");


    private final HttpStatus status;
    private final String message;
    @Override
    public HttpStatus getStatus() {
        return status;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
