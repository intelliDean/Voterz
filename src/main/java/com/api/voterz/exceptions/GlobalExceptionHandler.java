package com.api.voterz.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ImageUploadException.class)
    public ResponseEntity<?> handleImageUploadException(ImageUploadException ex, WebRequest webRequest) {
        ErrorResponse error = ErrorResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timeStamp(LocalDateTime.now().toString())
                .build();
        return ResponseEntity.status(error.getStatusCode()).body(error);
    }
    @ExceptionHandler(VoterzException.class)
    public ResponseEntity<?> handleVoterzException(VoterzException ex, WebRequest webRequest) {
        ErrorResponse error = ErrorResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timeStamp(LocalDateTime.now().toString())
                .build();
        return ResponseEntity.status(error.getStatusCode()).body(error);
    }
}
