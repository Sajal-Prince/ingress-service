package com.projectpay.ingress_service.exception;

import lombok.NonNull;
import org.projectpay.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<@NonNull ExceptionDto> handleNotFound(TransactionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDto(ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(UUIDAlreadyPresentException.class)
    public ResponseEntity<@NonNull ExceptionDto> handleUUidPresent(UUIDAlreadyPresentException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionDto(ex.getMessage(),LocalDateTime.now()));
    }
}
