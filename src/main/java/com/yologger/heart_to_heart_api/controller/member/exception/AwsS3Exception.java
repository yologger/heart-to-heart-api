package com.yologger.heart_to_heart_api.controller.member.exception;

import com.yologger.heart_to_heart_api.common.base.BusinessException;

public class AwsS3Exception extends BusinessException {
    public AwsS3Exception(String message) {
        super(message);
    }
}
