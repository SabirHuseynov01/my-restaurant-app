package org.example.restaurantservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(NotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setCode("NOT_FOUND");
        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Validation failed: " + Objects.requireNonNull(ex.getBindingResult().getFieldError().getDefaultMessage()));
        errorResponse.setCode("VALIDATION_ERROR");
        return ResponseEntity.badRequest().body(errorResponse);

    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handle(HttpRequestMethodNotSupportedException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Method not allowed:" + ex.getMethod());
        errorResponse.setCode("METHOD_NOT_ALLOWED");
        return ResponseEntity.status(405).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handle(HttpMessageNotReadableException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Malformed JSON request");
        errorResponse.setCode("MALFORMED_JSON");
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handle(HttpMediaTypeNotSupportedException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Unsupported media type:" + ex.getContentType());
        errorResponse.setCode("UNSUPPORTED_MEDIA_TYPE");
        return ResponseEntity.status(415).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("An unexpected error occured");
        errorResponse.setCode("INTERNAL_SERVER_ERROR");
        return ResponseEntity.status(500).body(errorResponse);
    }
}