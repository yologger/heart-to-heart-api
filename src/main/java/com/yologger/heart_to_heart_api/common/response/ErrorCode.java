package com.yologger.heart_to_heart_api.common.response;

public enum ErrorCode {

    // Common
    NOT_FOUND(404, "C001", "NOT FOUND"),
    INVALID_INPUT_VALUE(400, "C002", "Invalid Input Value"),


    // User
    USER_ALREADY_EXISTS(400, "U001", "User Already Exists."),
    ;

    private final String code;
    private final String message;
    private int status;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
