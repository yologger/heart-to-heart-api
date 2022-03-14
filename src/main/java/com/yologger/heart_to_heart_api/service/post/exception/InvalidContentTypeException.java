package com.yologger.heart_to_heart_api.service.post.exception;

import com.yologger.heart_to_heart_api.common.base.BusinessException;

public class InvalidContentTypeException extends BusinessException {
    public InvalidContentTypeException(String message) {
        super(message);
    }
}
