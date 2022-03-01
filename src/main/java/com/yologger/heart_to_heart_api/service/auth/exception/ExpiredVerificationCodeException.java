package com.yologger.heart_to_heart_api.service.auth.exception;

import com.yologger.heart_to_heart_api.common.base.BusinessException;

public class ExpiredVerificationCodeException extends BusinessException {
    public ExpiredVerificationCodeException(String message) {
        super(message);
    }
}