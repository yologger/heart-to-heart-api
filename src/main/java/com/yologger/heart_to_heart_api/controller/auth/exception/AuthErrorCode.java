package com.yologger.heart_to_heart_api.controller.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuthErrorCode {
    MAIL_ERROR(400, "AUTH000", "Mail Error"),
    MEMBER_ALREADY_EXIST(400, "AUTH001", "Member Already Exists."),
    INVALID_EMAIL(400, "AUTH002", "Invalid Email"),
    INVALID_VERIFICATION_CODE(400, "AUTH003", "Invalid Verification Code"),
    EXPIRED_VERIFICATION_CODE(400, "AUTH004", "Expired Verification code"),
    INVALID_PASSWORD(400, "AUTH005", "Invalid Password"),
    MEMBER_NOT_EXIST(400, "AUTH006", "Member Not Exist."),
    INVALID_REFRESH_TOKEN(400, "AUTH007", "Invalid refresh token"),
    EXPIRED_REFRESH_TOKEN(400, "AUTH008", "Expired refresh token"),
    INVALID_ACCESS_TOKEN(400, "AUTH009", "Invalid access token"),
    EXPIRED_ACCESS_TOKEN(400, "AUTH010", "Expired access token"),
    BEARER_NOT_INCLUDED(400, "AUTH011", "'Authorization' header must start with 'Bearer"),
    ;

    private final int status;
    private final String code;
    private final String message;
}
