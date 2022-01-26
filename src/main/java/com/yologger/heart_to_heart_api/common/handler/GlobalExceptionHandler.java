package com.yologger.heart_to_heart_api.common.handler;

import com.yologger.heart_to_heart_api.common.exception.business.UserAlreadyExistException;
import com.yologger.heart_to_heart_api.common.response.ErrorCode;
import com.yologger.heart_to_heart_api.common.response.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNoHandlerFoundException(NoHandlerFoundException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(ErrorCode.NOT_FOUND.getCode())
                .message(ErrorCode.NOT_FOUND.getMessage())
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(ErrorCode.INVALID_INPUT_VALUE.getCode())
                .message(ErrorCode.INVALID_INPUT_VALUE.getMessage())
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDto> handleUserAlreadyExistException(UserAlreadyExistException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(ErrorCode.USER_ALREADY_EXISTS.getCode())
                .message(ErrorCode.USER_ALREADY_EXISTS.getMessage())
                .build();
        return ResponseEntity.badRequest().body(response);
    }
}