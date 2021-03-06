package com.yologger.heart_to_heart_api.controller.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberErrorCode {
    MEMBER_NOT_EXIST (400, "MEMBER_001", "Member not exist"),
    FILE_UPLOAD_ERROR (400, "MEMBER_002", "File Upload Error"),
    IO_ERROR (400, "MEMBER_003", "IO Error"),
    INVALID_CONTENT_TYPE(400, "MEMBER_004", "Content type must be image"),
    INVALID_MEMBER_ID(400, "MEMBER_005", "Invalid member_id"),
    ALREADY_BLOCKING(400, "MEMBER_006", "Already blocking"),
    AWS_S3_ERROR(400, "MEMBER_007", "Aws S3 error"),
    ;

    private final int status;
    private final String code;
    private final String message;
}
