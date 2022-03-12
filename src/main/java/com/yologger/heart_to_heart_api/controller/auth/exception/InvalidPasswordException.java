package com.yologger.heart_to_heart_api.controller.auth.exception;

import com.yologger.heart_to_heart_api.common.base.BusinessException;

public class InvalidPasswordException extends BusinessException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
