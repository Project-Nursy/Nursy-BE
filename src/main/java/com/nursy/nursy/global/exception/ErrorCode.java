package com.nursy.nursy.global.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus getStatus();
    String getMessage();
}
