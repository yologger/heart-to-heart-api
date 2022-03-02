package com.yologger.heart_to_heart_api.service.auth.exception;

import com.yologger.heart_to_heart_api.common.base.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(value = MemberAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDto> handleMemberAlreadyExistException(MemberAlreadyExistException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(AuthErrorCode.MEMBER_ALREADY_EXISTS.getCode())
                .message(AuthErrorCode.MEMBER_ALREADY_EXISTS.getMessage())
                .status(AuthErrorCode.MEMBER_ALREADY_EXISTS.getStatus())
                .build();
        // return ResponseEntity.badRequest().body(response);
        return new ResponseEntity(response, HttpStatus.valueOf(AuthErrorCode.MEMBER_ALREADY_EXISTS.getStatus()));
    }

    @ExceptionHandler(value = InvalidEmailException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidEmailException(InvalidEmailException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(AuthErrorCode.INVALID_EMAIL.getCode())
                .message(AuthErrorCode.INVALID_EMAIL.getMessage())
                .status(AuthErrorCode.INVALID_EMAIL.getStatus())
                .build();
        // return ResponseEntity.badRequest().body(response);
        return new ResponseEntity(response, HttpStatus.valueOf(AuthErrorCode.INVALID_EMAIL.getStatus()));
    }

    @ExceptionHandler(value = InvalidVerificationCodeException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidVerificationCodeException(InvalidVerificationCodeException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(AuthErrorCode.INVALID_VERIFICATION_CODE.getCode())
                .message(AuthErrorCode.INVALID_VERIFICATION_CODE.getMessage())
                .status(AuthErrorCode.INVALID_VERIFICATION_CODE.getStatus())
                .build();
        // return ResponseEntity.badRequest().body(response);
        return new ResponseEntity(response, HttpStatus.valueOf(AuthErrorCode.INVALID_VERIFICATION_CODE.getStatus()));
    }

    @ExceptionHandler(value = ExpiredVerificationCodeException.class)
    public ResponseEntity<ErrorResponseDto> handleExpiredVerificationCodeException(ExpiredVerificationCodeException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(AuthErrorCode.EXPIRED_VERIFICATION_CODE.getCode())
                .message(AuthErrorCode.EXPIRED_VERIFICATION_CODE.getMessage())
                .status(AuthErrorCode.EXPIRED_VERIFICATION_CODE.getStatus())
                .build();
        // return ResponseEntity.badRequest().body(response);
        return new ResponseEntity(response, HttpStatus.valueOf(AuthErrorCode.EXPIRED_VERIFICATION_CODE.getStatus()));
    }
}
