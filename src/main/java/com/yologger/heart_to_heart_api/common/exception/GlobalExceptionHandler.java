package com.yologger.heart_to_heart_api.common.exception;

import com.yologger.heart_to_heart_api.common.base.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(GlobalErrorCode.INVALID_INPUT_VALUE.getCode())
                .message(GlobalErrorCode.INVALID_INPUT_VALUE.getMessage())
                .status(GlobalErrorCode.INVALID_INPUT_VALUE.getStatus())
                .build();
        // return ResponseEntity.badRequest().body(response);
        return new ResponseEntity(response, HttpStatus.valueOf(GlobalErrorCode.INVALID_INPUT_VALUE.getStatus()));
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNoHandlerFoundException(NoHandlerFoundException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(GlobalErrorCode.NOT_FOUND.getCode())
                .message(GlobalErrorCode.NOT_FOUND.getMessage())
                .status(GlobalErrorCode.NOT_FOUND.getStatus())
                .build();
        // return ResponseEntity.badRequest().body(errorBody);
        return new ResponseEntity(response, HttpStatus.valueOf(GlobalErrorCode.NOT_FOUND.getStatus()));
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(GlobalErrorCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED.getCode())
                .message(GlobalErrorCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED.getMessage())
                .status(GlobalErrorCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED.getStatus())
                .build();
        return new ResponseEntity(response, HttpStatus.valueOf(GlobalErrorCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED.getStatus()));
    }



    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(GlobalErrorCode.HTTP_MESSAGE_NOT_READABLE.getCode())
                .message(GlobalErrorCode.HTTP_MESSAGE_NOT_READABLE.getMessage())
                .status(GlobalErrorCode.HTTP_MESSAGE_NOT_READABLE.getStatus())
                .build();
        // return ResponseEntity.badRequest().body(errorBody);
        return new ResponseEntity(response, HttpStatus.valueOf(GlobalErrorCode.HTTP_MESSAGE_NOT_READABLE.getStatus()));
    }

    @ExceptionHandler(value = MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponseDto> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(GlobalErrorCode.MISSING_REQUEST_HEADER.getCode())
                .message(GlobalErrorCode.MISSING_REQUEST_HEADER.getMessage())
                .status(GlobalErrorCode.MISSING_REQUEST_HEADER.getStatus())
                .build();
        return new ResponseEntity(response, HttpStatus.valueOf(GlobalErrorCode.MISSING_REQUEST_HEADER.getStatus()));
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDto> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(GlobalErrorCode.MISSING_REQUEST_PARAMETER.getCode())
                .message(e.getMessage())
                .status(GlobalErrorCode.MISSING_REQUEST_PARAMETER.getStatus())
                .build();
        return new ResponseEntity(response, HttpStatus.valueOf(GlobalErrorCode.MISSING_REQUEST_PARAMETER.getStatus()));
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponseDto> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(GlobalErrorCode.FILE_SIZE_LIMIT_EXCEEDED.getCode())
                .message(GlobalErrorCode.FILE_SIZE_LIMIT_EXCEEDED.getMessage())
                .status(GlobalErrorCode.FILE_SIZE_LIMIT_EXCEEDED.getStatus())
                .build();
        return new ResponseEntity(response, HttpStatus.valueOf(GlobalErrorCode.FILE_SIZE_LIMIT_EXCEEDED.getStatus()));
    }

    @ExceptionHandler(value = MissingServletRequestPartException.class)
    public ResponseEntity<ErrorResponseDto> handleMissingServletRequestPartException(MissingServletRequestPartException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(GlobalErrorCode.MISSING_SERVLET_REQUEST_PART.getCode())
                // .message(GlobalErrorCode.MISSING_SERVLET_REQUEST_PART.getMessage())
                .message(e.getLocalizedMessage())
                .status(GlobalErrorCode.MISSING_SERVLET_REQUEST_PART.getStatus())
                .build();
        return new ResponseEntity(response, HttpStatus.valueOf(GlobalErrorCode.MISSING_SERVLET_REQUEST_PART.getStatus()));
    }
}