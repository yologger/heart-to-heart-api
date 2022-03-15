package com.yologger.heart_to_heart_api.service.post.exception;

import com.yologger.heart_to_heart_api.common.base.BusinessException;

public class NoPostsExistException extends BusinessException {
    public NoPostsExistException(String message) {
        super(message);
    }
}
