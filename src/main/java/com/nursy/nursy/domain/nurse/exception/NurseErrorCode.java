//package com.nursy.nursy.domain.nurse.exception;
//
//import com.nursy.nursy.global.exception.ErrorCode;
//import com.nursy.nursy.global.exception.GlobalErrorCode;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import org.springframework.http.HttpStatus;
//
//@Getter
//@AllArgsConstructor
//public enum NurseErrorCode implements ErrorCode {
//    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유효하지 않은 사용자 입니다."),
//    ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 병동에 대한 접근 권한이 없습니다."),
//    NURSE_NOT_FOUND(HttpStatus.NOT_FOUND, "간호사를 찾을 수 없습니다.");
//
//    private final HttpStatus status;
//    private final String message;
//
//    @Override
//    public HttpStatus getStatus() {
//        return status;
//    }
//
//    @Override
//    public String getMessage() {
//        return message;
//    }
//}
