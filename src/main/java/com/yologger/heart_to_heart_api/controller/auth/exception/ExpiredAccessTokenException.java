package com.yologger.heart_to_heart_api.controller.auth.exception;

import com.yologger.heart_to_heart_api.common.base.BusinessException;

public class ExpiredAccessTokenException extends BusinessException {
    public ExpiredAccessTokenException(String message) {
        super(message);
    }
}
