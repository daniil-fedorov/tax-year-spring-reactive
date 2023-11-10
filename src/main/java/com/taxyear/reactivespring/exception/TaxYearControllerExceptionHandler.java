package com.taxyear.reactivespring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import static com.taxyear.reactivespring.exception.ExceptionMessages.INTERNAL_SERVER_ERROR_MESSAGE;
import static com.taxyear.reactivespring.exception.ExceptionMessages.INVALID_TAX_YEAR_MESSAGE;

@ControllerAdvice
public class TaxYearControllerExceptionHandler {

    @ExceptionHandler(value = TaxRepositoryException.class)
    protected Mono<ResponseEntity<String>> handleIllegalArgumentException() {

        return Mono.just(ResponseEntity.badRequest().body(INVALID_TAX_YEAR_MESSAGE));
    }

    @ExceptionHandler(value = Exception.class)
    protected Mono<ResponseEntity<String>> handleEverythingElse() {

        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(INTERNAL_SERVER_ERROR_MESSAGE));
    }
}
