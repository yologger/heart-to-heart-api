package com.yologger.heart_to_heart_api.controller.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberErrorCode {
    MEMBER_NOT_EXIST (400, "MEMBER001", "Member not exist"),
    FILE_UPLOAD_ERROR (400, "MEMBER002", "File Upload Error"),
    IO_ERROR (400, "MEMBER003", "IO Error"),
    ;

    private final int status;
    private final String code;
    private final String message;
}
