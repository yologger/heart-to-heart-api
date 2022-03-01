package com.yologger.heart_to_heart_api.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GlobalErrorCode {

    // Global
    NOT_FOUND(404, "G001", "NOT FOUND"),
    INVALID_INPUT_VALUE(400, "G002", "INVALID INPUT VALUE"),
    ;

    private final int status;
    private final String code;
    private final String message;
}
