package com.yologger.heart_to_heart_api.service.post.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PostErrorCode {
    INVALID_WRITER_ID(400, "POST000", "Invalid writer id"),
    INVALID_CONTENT_TYPE(400, "POST001", "Content type must be image"),
    FILE_UPLOAD_ERROR(400, "POST002", "File upload error"),
    ;

    private final int status;
    private final String code;
    private final String message;
}
