package com.yologger.heart_to_heart_api.controller.auth;

import com.yologger.heart_to_heart_api.common.base.ErrorResponseDto;
import com.yologger.heart_to_heart_api.controller.auth.exception.*;
import com.yologger.heart_to_heart_api.service.auth.AuthService;
import com.yologger.heart_to_heart_api.service.auth.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthController {

    private final AuthService authService;

    @PostMapping
    @RequestMapping(value = "/emailVerificationCode", consumes = "application/json", produces = "application/json")
    ResponseEntity<EmailVerificationCodeResponseDto> emailVerificationCode(@Valid @RequestBody EmailVerificationCodeRequestDto request) throws MemberAlreadyExistException, MessagingException, MailException {
        return authService.emailVerificationCode(request.getEmail());
    }

    @PostMapping
    @RequestMapping(value = "/confirmVerificationCode", consumes = "application/json", produces = "application/json")
    ResponseEntity<ConfirmVerificationCodeResponseDto> confirmVerificationCode(@Valid @RequestBody ConfirmVerificationCodeRequestDto request) throws InvalidVerificationCodeException, InvalidEmailException, ExpiredVerificationCodeException {
        return authService.confirmVerificationCode(request);
    }

    @PostMapping
    @RequestMapping(value = "/join", consumes = "application/json", produces = "application/json")
    public ResponseEntity<JoinResponseDto> join(@Valid @RequestBody JoinRequestDto request) throws MemberAlreadyExistException {
        return authService.join(request);
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request) throws MemberNotExistException, InvalidPasswordException {
        return authService.login(request);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<LogoutResponseDto> logout(@Valid @RequestHeader(value = "Authorization", required = true) String authHeader) throws InvalidAccessTokenException, ExpiredAccessTokenException, BearerNotIncludedException {
        return authService.logout(authHeader);
    }

    @PostMapping(value = "/reissueToken", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ReissueTokenResponseDto> reissueToken(@Valid @RequestBody ReissueTokenRequestDto request) throws ExpiredRefreshTokenException, InvalidRefreshTokenException, MemberNotExistException {
        return authService.reissueToken(request);
    }

    @GetMapping(value = "/verifyAccessToken")
    public ResponseEntity<VerifyAccessTokenResponse> verifyAccessToken() {
        VerifyAccessTokenResponse response = VerifyAccessTokenResponse.builder()
                .message("verified")
                .build();
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler({MessagingException.class, MailAuthenticationException.class, MailSendException.class, MailException.class})
    public ResponseEntity<ErrorResponseDto> handleMailException(Exception e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(AuthErrorCode.MAIL_ERROR.getCode())
                .message(AuthErrorCode.MAIL_ERROR.getMessage())
                .status(AuthErrorCode.MAIL_ERROR.getStatus())
                .build();
        return new ResponseEntity(response, HttpStatus.valueOf(AuthErrorCode.MAIL_ERROR.getStatus()));
    }

    @ExceptionHandler(value = MemberAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDto> handleMemberAlreadyExistException(MemberAlreadyExistException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(AuthErrorCode.MEMBER_ALREADY_EXIST.getCode())
                .message(AuthErrorCode.MEMBER_ALREADY_EXIST.getMessage())
                .status(AuthErrorCode.MEMBER_ALREADY_EXIST.getStatus())
                .build();
        return new ResponseEntity(response, HttpStatus.valueOf(AuthErrorCode.MEMBER_ALREADY_EXIST.getStatus()));
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

    @ExceptionHandler(value = MemberNotExistException.class)
    public ResponseEntity<ErrorResponseDto> handleMemberDoesNotExistException(MemberNotExistException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(AuthErrorCode.MEMBER_NOT_EXIST.getCode())
                .message(AuthErrorCode.MEMBER_NOT_EXIST.getMessage())
                .status(AuthErrorCode.MEMBER_NOT_EXIST.getStatus())
                .build();
        // return ResponseEntity.badRequest().body(response);
        return new ResponseEntity(response, HttpStatus.valueOf(AuthErrorCode.MEMBER_NOT_EXIST.getStatus()));
    }

    @ExceptionHandler(value = InvalidPasswordException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidPasswordException(InvalidPasswordException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(AuthErrorCode.INVALID_PASSWORD.getCode())
                .message(AuthErrorCode.INVALID_PASSWORD.getMessage())
                .status(AuthErrorCode.INVALID_PASSWORD.getStatus())
                .build();
        // return ResponseEntity.badRequest().body(response);
        return new ResponseEntity(response, HttpStatus.valueOf(AuthErrorCode.INVALID_PASSWORD.getStatus()));
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
}
