package com.yologger.heart_to_heart_api.common.exception;

import com.yologger.heart_to_heart_api.common.base.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNoHandlerFoundException(NoHandlerFoundException e) {
        final ErrorResponseDto errorBody = ErrorResponseDto.builder()
                .code(GlobalErrorCode.NOT_FOUND.getCode())
                .message(GlobalErrorCode.NOT_FOUND.getMessage())
                .status(GlobalErrorCode.NOT_FOUND.getStatus())
                .build();
        // return ResponseEntity.badRequest().body(errorBody);
        return new ResponseEntity(errorBody, HttpStatus.valueOf(errorBody.getStatus()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(GlobalErrorCode.INVALID_INPUT_VALUE.getCode())
                .message(GlobalErrorCode.INVALID_INPUT_VALUE.getMessage())
                .status(GlobalErrorCode.INVALID_INPUT_VALUE.getStatus())
                .build();
        // return ResponseEntity.badRequest().body(response);
        return new ResponseEntity(response, HttpStatus.valueOf(response.getStatus()));
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(GlobalErrorCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED.getCode())
                .message(GlobalErrorCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED.getMessage())
                .status(GlobalErrorCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED.getStatus())
                .build();
        return new ResponseEntity(response, HttpStatus.valueOf(response.getStatus()));
    }
}