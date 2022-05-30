package com.yologger.heart_to_heart_api.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GlobalErrorCode {

    // Global
    NOT_FOUND(404, "GLOBAL_001", "Not Found"),
    INVALID_INPUT_VALUE(400, "GLOBAL_002", "Invalid Input"),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(405, "GLOBAL_003", "HTTP Request Not Supported"),
    HTTP_MESSAGE_NOT_READABLE(400, "GLOBAL_004", "HTTP message not readable"),
    MISSING_REQUEST_HEADER(400, "GLOBAL_005", "Missing Request Header"),
    ILLEGAL_STATE(400, "GLOBAL_006", "Illegal State"),
    MISSING_REQUEST_PARAMETER(400, "GLOBAL_007", "Missing Request Parameter"),
    FILE_SIZE_LIMIT_EXCEEDED(400, "GLOBAL_008", "File Size Limit 10MB Exceeded."),
    MISSING_SERVLET_REQUEST_PART(400, "GLOBAL_009", "Missing Request Part."),

    // Auth
    MISSING_AUTHORIZATION_HEADER(400, "GLOBAL_100", "'Authorization' header must not be empty"),
    BEARER_NOT_INCLUDED(400, "GLOBAL_101", "'Authorization' header must start with 'Bearer'"),
    ACCESS_TOKEN_EMPTY(400, "GLOBAL_102", "'Authorization' header does not include access token"),
    INVALID_ACCESS_TOKEN(400, "GLOBAL_103", "Invalid access token"),
    EXPIRED_ACCESS_TOKEN(400, "GLOBAL_104", "Expired access token"),

    UNAUTHORIZED(403, "GLOBAL_105", "Unauthorized."),
    ;




    private final int status;
    private final String code;
    private final String message;
}
