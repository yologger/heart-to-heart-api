package com.yologger.heart_to_heart_api.service.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuthErrorCode {
    MEMBER_ALREADY_EXISTS(400, "A001", "Member Already Exists."),
    INVALID_EMAIL(400, "A002", "Invalid Email"),
    INVALID_VERIFICATION_CODE(400, "A003", "Invalid Verification Code"),
    EXPIRED_VERIFICATION_CODE(400, "A004", "Expired Verification code"),
    INVALID_PASSWORD(400, "A005", "Invalid Password"),
    MEMBER_NOT_EXIST(400, "A006", "Member Not Exist."),
    INVALID_REFRESH_TOKEN(400, "A007", "Invalid refresh token"),
    EXPIRED_REFRESH_TOKEN(400, "A008", "Expired refresh token"),
    INVALID_ACCESS_TOKEN(400, "A009", "Invalid access token"),
    EXPIRED_ACCESS_TOKEN(400, "A010", "expired access token"),
    BEARER_NOT_INCLUDED(400, "A011", "'Authorization' header must start with 'Bearer"),
    ;

    private final int status;
    private final String code;
    private final String message;
}
