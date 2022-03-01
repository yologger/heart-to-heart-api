package com.yologger.heart_to_heart_api.service.auth.exception;

import com.yologger.heart_to_heart_api.common.base.BusinessException;

public class UserAlreadyExistException extends BusinessException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}