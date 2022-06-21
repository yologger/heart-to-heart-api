package com.yologger.heart_to_heart_api.controller.auth;

import com.yologger.heart_to_heart_api.common.base.ErrorResponseDto;
import com.yologger.heart_to_heart_api.controller.auth.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.mail.MessagingException;

@RestControllerAdvice(basePackageClasses = AuthController.class)
public class AuthExceptionHandler {

    @ExceptionHandler(value = MemberAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDto> handleMemberAlreadyExistException(MemberAlreadyExistException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(AuthErrorCode.MEMBER_ALREADY_EXIST.getCode())
                .message(AuthErrorCode.MEMBER_ALREADY_EXIST.getMessage())
                .status(AuthErrorCode.MEMBER_ALREADY_EXIST.getStatus().value())
                .build();
        return new ResponseEntity(response, AuthErrorCode.MEMBER_ALREADY_EXIST.getStatus());
    }

    @ExceptionHandler({MessagingException.class, MailAuthenticationException.class, MailSendException.class, MailException.class})
    public ResponseEntity<ErrorResponseDto> handleMailException(Exception e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(AuthErrorCode.MAIL_SYSTEM_ERROR.getCode())
                .message(AuthErrorCode.MAIL_SYSTEM_ERROR.getMessage())
                .status(AuthErrorCode.MAIL_SYSTEM_ERROR.getStatus().value())
                .build();
        return new ResponseEntity(response, AuthErrorCode.MAIL_SYSTEM_ERROR.getStatus());
    }

    @ExceptionHandler(value = InvalidEmailException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidEmailException(InvalidEmailException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(AuthErrorCode.INVALID_EMAIL.getCode())
                .message(AuthErrorCode.INVALID_EMAIL.getMessage())
                .status(AuthErrorCode.INVALID_EMAIL.getStatus().value())
                .build();
        return new ResponseEntity(response, AuthErrorCode.INVALID_EMAIL.getStatus());
    }

    @ExceptionHandler(value = InvalidVerificationCodeException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidVerificationCodeException(InvalidVerificationCodeException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(AuthErrorCode.INVALID_VERIFICATION_CODE.getCode())
                .message(AuthErrorCode.INVALID_VERIFICATION_CODE.getMessage())
                .status(AuthErrorCode.INVALID_VERIFICATION_CODE.getStatus().value())
                .build();
        return new ResponseEntity(response, AuthErrorCode.INVALID_VERIFICATION_CODE.getStatus());
    }

    @ExceptionHandler(value = ExpiredVerificationCodeException.class)
    public ResponseEntity<ErrorResponseDto> handleExpiredVerificationCodeException(ExpiredVerificationCodeException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(AuthErrorCode.EXPIRED_VERIFICATION_CODE.getCode())
                .message(AuthErrorCode.EXPIRED_VERIFICATION_CODE.getMessage())
                .status(AuthErrorCode.EXPIRED_VERIFICATION_CODE.getStatus().value())
                .build();
        return new ResponseEntity(response, AuthErrorCode.EXPIRED_VERIFICATION_CODE.getStatus());
    }

    @ExceptionHandler(value = MemberNotExistException.class)
    public ResponseEntity<ErrorResponseDto> handleMemberDoesNotExistException(MemberNotExistException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(AuthErrorCode.MEMBER_NOT_EXIST.getCode())
                .message(AuthErrorCode.MEMBER_NOT_EXIST.getMessage())
                .status(AuthErrorCode.MEMBER_NOT_EXIST.getStatus().value())
                .build();
        return new ResponseEntity(response, AuthErrorCode.MEMBER_NOT_EXIST.getStatus());
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleBadCredentialsException (BadCredentialsException  e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(AuthErrorCode.INVALID_PASSWORD.getCode())
                .message(AuthErrorCode.INVALID_PASSWORD.getMessage())
                .status(AuthErrorCode.INVALID_PASSWORD.getStatus().value())
                .build();
        return new ResponseEntity(response, AuthErrorCode.INVALID_PASSWORD.getStatus());
    }

    @ExceptionHandler(value = InvalidRefreshTokenException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidRefreshTokenException(InvalidRefreshTokenException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(AuthErrorCode.INVALID_REFRESH_TOKEN.getCode())
                .message(AuthErrorCode.INVALID_REFRESH_TOKEN.getMessage())
                .status(AuthErrorCode.INVALID_REFRESH_TOKEN.getStatus().value())
                .build();
        return new ResponseEntity(response, AuthErrorCode.INVALID_REFRESH_TOKEN.getStatus());
    }

    @ExceptionHandler(value = ExpiredRefreshTokenException.class)
    public ResponseEntity<ErrorResponseDto> handleExpiredRefreshTokenException(ExpiredRefreshTokenException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(AuthErrorCode.EXPIRED_REFRESH_TOKEN.getCode())
                .message(AuthErrorCode.EXPIRED_REFRESH_TOKEN.getMessage())
                .status(AuthErrorCode.EXPIRED_REFRESH_TOKEN.getStatus().value())
                .build();
        return new ResponseEntity(response, AuthErrorCode.EXPIRED_REFRESH_TOKEN.getStatus());
    }

    @ExceptionHandler(value = BearerNotIncludedException.class)
    public ResponseEntity<ErrorResponseDto> handleBearerNotIncludedException(BearerNotIncludedException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(AuthErrorCode.BEARER_NOT_INCLUDED.getCode())
                .message(AuthErrorCode.BEARER_NOT_INCLUDED.getMessage())
                .status(AuthErrorCode.BEARER_NOT_INCLUDED.getStatus().value())
                .build();
        return new ResponseEntity(response, AuthErrorCode.BEARER_NOT_INCLUDED.getStatus());
    }

    @ExceptionHandler(value = InvalidAccessTokenException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidAccessTokenException(InvalidAccessTokenException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(AuthErrorCode.INVALID_ACCESS_TOKEN.getCode())
                .message(AuthErrorCode.INVALID_ACCESS_TOKEN.getMessage())
                .status(AuthErrorCode.INVALID_ACCESS_TOKEN.getStatus().value())
                .build();
        return new ResponseEntity(response, AuthErrorCode.INVALID_ACCESS_TOKEN.getStatus());
    }

    @ExceptionHandler(value = ExpiredAccessTokenException.class)
    public ResponseEntity<ErrorResponseDto> handleExpiredAccessTokenException(ExpiredAccessTokenException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(AuthErrorCode.EXPIRED_ACCESS_TOKEN.getCode())
                .message(AuthErrorCode.EXPIRED_ACCESS_TOKEN.getMessage())
                .status(AuthErrorCode.EXPIRED_ACCESS_TOKEN.getStatus().value())
                .build();
        return new ResponseEntity(response, AuthErrorCode.EXPIRED_ACCESS_TOKEN.getStatus());
    }
}
