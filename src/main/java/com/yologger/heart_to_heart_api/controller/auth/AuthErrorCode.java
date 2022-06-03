package com.yologger.heart_to_heart_api.controller.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum AuthErrorCode {
    MEMBER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "AUTH_000", "Member already exists."),
    MAIL_SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AUTH_001", "Mail system error."),

    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "AUTH_002", "Invalid email."),
    INVALID_VERIFICATION_CODE(HttpStatus.UNAUTHORIZED, "AUTH_003", "Invalid Verification Code"),
    EXPIRED_VERIFICATION_CODE(HttpStatus.UNAUTHORIZED, "AUTH_004", "Expired Verification code"),

    MEMBER_NOT_EXIST(HttpStatus.BAD_REQUEST, "AUTH_005", "Member Not Exist."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "AUTH_006", "Invalid Password"),

    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_007", "Invalid Refresh Token"),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_008", "Expired Refresh Token"),

    BEARER_NOT_INCLUDED(HttpStatus.UNAUTHORIZED, "AUTH_009", "'Authorization' header must start with 'Bearer"),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_010", "Invalid Access Token"),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_011", "Expired Access Token"),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
