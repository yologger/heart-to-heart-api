package com.yologger.heart_to_heart_api.service.auth.exception;

import com.yologger.heart_to_heart_api.common.base.ErrorResponseDto;
import com.yologger.heart_to_heart_api.controller.auth.exception.AuthErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {


    @ExceptionHandler(value = InvalidRefreshTokenException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidRefreshTokenException(InvalidRefreshTokenException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(AuthErrorCode.INVALID_REFRESH_TOKEN.getCode())
                .message(AuthErrorCode.INVALID_REFRESH_TOKEN.getMessage())
                .status(AuthErrorCode.INVALID_REFRESH_TOKEN.getStatus())
                .build();
        // return ResponseEntity.badRequest().body(response);
        return new ResponseEntity(response, HttpStatus.valueOf(AuthErrorCode.INVALID_REFRESH_TOKEN.getStatus()));
    }

    @ExceptionHandler(value = ExpiredRefreshTokenException.class)
    public ResponseEntity<ErrorResponseDto> handleExpiredRefreshTokenException(ExpiredRefreshTokenException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(AuthErrorCode.EXPIRED_REFRESH_TOKEN.getCode())
                .message(AuthErrorCode.EXPIRED_REFRESH_TOKEN.getMessage())
                .status(AuthErrorCode.EXPIRED_REFRESH_TOKEN.getStatus())
                .build();
        // return ResponseEntity.badRequest().body(response);
        return new ResponseEntity(response, HttpStatus.valueOf(AuthErrorCode.EXPIRED_REFRESH_TOKEN.getStatus()));
    }

    @ExceptionHandler(value = BearerNotIncludedException.class)
    public ResponseEntity<ErrorResponseDto> handleBearerNotIncludedException(BearerNotIncludedException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(AuthErrorCode.BEARER_NOT_INCLUDED.getCode())
                .message(AuthErrorCode.BEARER_NOT_INCLUDED.getMessage())
                .status(AuthErrorCode.BEARER_NOT_INCLUDED.getStatus())
                .build();
        // return ResponseEntity.badRequest().body(response);
        return new ResponseEntity(response, HttpStatus.valueOf(AuthErrorCode.BEARER_NOT_INCLUDED.getStatus()));
    }

    @ExceptionHandler(value = InvalidAccessTokenException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidAccessTokenException(InvalidAccessTokenException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(AuthErrorCode.INVALID_ACCESS_TOKEN.getCode())
                .message(AuthErrorCode.INVALID_ACCESS_TOKEN.getMessage())
                .status(AuthErrorCode.INVALID_ACCESS_TOKEN.getStatus())
                .build();
        // return ResponseEntity.badRequest().body(response);
        return new ResponseEntity(response, HttpStatus.valueOf(AuthErrorCode.INVALID_ACCESS_TOKEN.getStatus()));
    }

    @ExceptionHandler(value = ExpiredAccessTokenException.class)
    public ResponseEntity<ErrorResponseDto> handleExpiredAccessTokenException(ExpiredAccessTokenException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(AuthErrorCode.EXPIRED_ACCESS_TOKEN.getCode())
                .message(AuthErrorCode.EXPIRED_ACCESS_TOKEN.getMessage())
                .status(AuthErrorCode.EXPIRED_ACCESS_TOKEN.getStatus())
                .build();
        // return ResponseEntity.badRequest().body(response);
        return new ResponseEntity(response, HttpStatus.valueOf(AuthErrorCode.EXPIRED_ACCESS_TOKEN.getStatus()));
    }


}
