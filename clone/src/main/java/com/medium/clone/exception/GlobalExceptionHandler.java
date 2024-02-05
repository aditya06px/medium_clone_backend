package com.medium.clone.exception;

import com.medium.clone.responseDto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiResponse<?> handleResourceNotFoundException(ResourceNotFoundException ex) {

        return ApiResponse.builder()
                .data(ex.getMessage())
                .message("please provide valid input data")
                .statusCode(HttpStatus.NOT_FOUND)
                .success(false)
                .build();
    }


    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception ex) {

        return ApiResponse.builder()
                .data(ex.getMessage())
                .message("Internal server error occurred")
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .success(false)
                .build();

    }
}
