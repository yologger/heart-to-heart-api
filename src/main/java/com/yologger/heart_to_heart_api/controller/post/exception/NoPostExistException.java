package com.yologger.heart_to_heart_api.controller.post.exception;

import com.yologger.heart_to_heart_api.common.base.BusinessException;

public class NoPostExistException extends BusinessException {
    public NoPostExistException(String message) {
        super(message);
    }
}
