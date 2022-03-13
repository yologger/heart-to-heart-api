package com.yologger.heart_to_heart_api.service.post.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PostErrorCode {
    INVALID_WRITER_ID(400, "POST000", "Invalid writer id"),
    ;

    private final int status;
    private final String code;
    private final String message;
}
