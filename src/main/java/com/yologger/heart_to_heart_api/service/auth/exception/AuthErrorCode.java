package com.yologger.heart_to_heart_api.service.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuthErrorCode {

    USER_ALREADY_EXISTS(400, "A001", "User Already Exists."),
    INVALID_EMAIL(400, "A002", "Invalid Email"),
    INVALID_VERIFICATION_CODE(400, "A003", "Invalid Verification Code"),
    EXPIRED_VERIFICATION_CODE(400, "A004", "Expired Verification code"),
    ;

    private final int status;
    private final String code;
    private final String message;
}
