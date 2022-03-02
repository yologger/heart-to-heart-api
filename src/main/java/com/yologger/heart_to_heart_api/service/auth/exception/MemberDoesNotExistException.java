package com.yologger.heart_to_heart_api.service.auth.exception;

import com.yologger.heart_to_heart_api.common.base.BusinessException;

public class MemberDoesNotExistException extends BusinessException {
    public MemberDoesNotExistException(String message) {
        super(message);
    }
}
