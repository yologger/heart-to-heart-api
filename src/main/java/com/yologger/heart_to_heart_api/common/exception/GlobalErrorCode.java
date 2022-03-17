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
    MISSING_REQUEST_HEADER(400, "GLOBAL005", "Missing Request Header"),
    ILLEGAL_STATE(400, "GLOBAL006", "Illegal State"),
    MISSING_REQUEST_PARAMETER(400, "GLOBAL007", "Missing Request Parameter"),
    FILE_SIZE_LIMIT_EXCEEDED(400, "GLOBAL008", "File size limit exceeded. File size must be less than 100 MB."),

    // Auth
    MISSING_AUTHORIZATION_HEADER(400, "GLOBAL100", "'Authorization' header must not be empty"),
    BEARER_NOT_INCLUDED(400, "GLOBAL101", "'Authorization' header must start with 'Bearer'"),
    ACCESS_TOKEN_EMPTY(400, "GLOBAL102", "'Authorization' header does not include access token"),
    INVALID_ACCESS_TOKEN(400, "GLOBAL103", "Invalid Access Token"),
    EXPIRED_ACCESS_TOKEN(400, "GLOBAL104", "Expired access token"),
    ;


    private final int status;
    private final String code;
    private final String message;
}
