package com.yologger.heart_to_heart_api.controller.auth.exception;

import com.yologger.heart_to_heart_api.common.base.BusinessException;

public class MemberAlreadyExistException extends BusinessException {
    public MemberAlreadyExistException(String message) {
        super(message);
    }
}