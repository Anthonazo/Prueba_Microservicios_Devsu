package com.devsu.hackerearth.backend.account.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HandleBalanceException.class)
    ResponseEntity<Object> handleBalance(HandleBalanceException ex) {
        Map<String, Object> message = new HashMap<>();
        message.put("Fecha", LocalDateTime.now());
        message.put("Mensaje", ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

}
