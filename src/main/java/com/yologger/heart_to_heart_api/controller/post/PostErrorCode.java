package com.yologger.heart_to_heart_api.controller.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PostErrorCode {
    INVALID_WRITER_ID(400, "POST_000", "Invalid writer id"),
    INVALID_CONTENT_TYPE(400, "POST_001", "Content type must be image"),
    FILE_UPLOAD_ERROR(400, "POST_002", "File upload error"),

    NO_POSTS_EXIST(400, "POST_003", "No posts exist."),
    NO_POST_EXIST(400, "POST_004", "No post exist."),

    ;

    private final int status;
    private final String code;
    private final String message;
}
