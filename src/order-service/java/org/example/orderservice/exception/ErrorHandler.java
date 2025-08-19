package org.example.orderservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ErrorHandler {

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ErrorResponse> handle(ClientException exception){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setCode(exception.getErrorCode());
        return ResponseEntity.status(exception.getStatusCode()).body(errorResponse);

    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException exception){
        var errorResponse = new ErrorResponse();
        errorResponse.setMessage("The requested method is not supported for the given URL");
        errorResponse.setCode("METHOD_NOT_ALLOWED");
        return ResponseEntity.status(405).body(errorResponse);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedMediaType(HttpMediaTypeNotSupportedException exception){
        var errorResponse = new ErrorResponse();
        errorResponse.setMessage("The requested media type is not supported");
        errorResponse.setCode("UNSUPPORTED_MEDIA_TYPE");
        return ResponseEntity.status(415).body(errorResponse);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception exception){
        var errorResponse = new ErrorResponse();
        errorResponse.setMessage("An unexpected error occurred ");
        errorResponse.setCode("INTERNAL_SERVER_ERROR");
        return ResponseEntity.status(500).body(errorResponse);
    }
}


