package com.yologger.heart_to_heart_api.controller.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuthErrorCode {
    MEMBER_ALREADY_EXIST(400, "AUTH_000", "Member Already Exists."),
    MAIL_SYSTEM_ERROR(400, "AUTH_001", "Mail System Error."),

    INVALID_EMAIL(400, "AUTH_002", "Invalid Email."),
    INVALID_VERIFICATION_CODE(400, "AUTH_003", "Invalid Verification Code"),
    EXPIRED_VERIFICATION_CODE(400, "AUTH_004", "Expired Verification code"),

    MEMBER_NOT_EXIST(400, "AUTH_005", "Member Not Exist."),
    INVALID_PASSWORD(400, "AUTH_006", "Invalid Password"),

    INVALID_REFRESH_TOKEN(401, "AUTH_007", "Invalid Refresh Token"),
    EXPIRED_REFRESH_TOKEN(401, "AUTH_008", "Expired Refresh Token"),

    BEARER_NOT_INCLUDED(401, "AUTH_009", "'Authorization' header must start with 'Bearer"),
    INVALID_ACCESS_TOKEN(401, "AUTH_010", "Invalid Access Token"),
    EXPIRED_ACCESS_TOKEN(401, "AUTH_011", "Expired Access Token"),
    ;

    private final int status;
    private final String code;
    private final String message;
}
