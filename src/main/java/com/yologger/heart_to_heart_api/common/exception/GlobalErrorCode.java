package com.yologger.heart_to_heart_api.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GlobalErrorCode {

    // Global
    NOT_FOUND(404, "GLOBAL001", "Not Found"),
    INVALID_INPUT_VALUE(400, "GLOBAL002", "Invalid input"),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(405, "GLOBAL003", "HTTP request not supported"),
    HTTP_MESSAGE_NOT_READABLE(400, "GLOBAL004", "HTTP message not readable"),
    MISSING_REQUEST_HEADER(400, "G004", "Missing Request Header"),
    MISSING_AUTHORIZATION_HEADER(400, "G005", "'Authorization' header must not be empty"),
    NOT_STARTED_WITH_BEARER(400, "G006", "'Authorization' header must start with 'Bearer'"),
    ACCESS_TOKEN_EMPTY(400, "G007", "'Authorization' header does not include access token."),
    INVALID_ACCESS_TOKEN(400, "G008", "Invalid access token"),
    EXPIRED_ACCESS_TOKEN(400, "G009", "Expired access token"),
    ;


    private final int status;
    private final String code;
    private final String message;
}
