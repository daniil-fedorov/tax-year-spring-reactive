package com.taxyear.reactivespring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class TaxYearControllerExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    protected Mono<ResponseEntity<String>> handleIllegalArgumentException() {

        String bodyOfResponse = "Invalid year parameter. Year must be a valid tax year (e.g., 2021, 2022, 2023).";

        return Mono.just(ResponseEntity.badRequest().body(bodyOfResponse));
    }

    @ExceptionHandler(value = Exception.class)
    protected Mono<ResponseEntity<String>> handleEverythingElse() {

        String bodyOfResponse = "Internal server error.";

        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bodyOfResponse));
    }
}
