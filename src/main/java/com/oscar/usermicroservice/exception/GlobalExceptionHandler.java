package com.oscar.usermicroservice.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFound(EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Resource not found: " + exception.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrity(DataIntegrityViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid data: " + Objects.requireNonNull(exception.getRootCause()).getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationError(MethodArgumentNotValidException exception) {
        StringBuilder errors = new StringBuilder();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> errors.append(error.getField())
                        .append(": ")
                        .append(error.getDefaultMessage())
                        .append("; "));
        return ResponseEntity.badRequest().body("Validation error(s): " + errors);
    }
}
