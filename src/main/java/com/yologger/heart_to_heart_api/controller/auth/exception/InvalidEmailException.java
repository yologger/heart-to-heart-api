package com.yologger.heart_to_heart_api.controller.auth.exception;

import com.yologger.heart_to_heart_api.common.base.BusinessException;

public class InvalidEmailException extends BusinessException {
    public InvalidEmailException(String message) {
        super(message);
    }
}
