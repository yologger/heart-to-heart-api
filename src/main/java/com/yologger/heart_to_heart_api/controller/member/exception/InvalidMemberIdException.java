package com.yologger.heart_to_heart_api.controller.member.exception;

import com.yologger.heart_to_heart_api.common.base.BusinessException;

public class InvalidMemberIdException extends BusinessException {
    public InvalidMemberIdException(String message) {
        super(message);
    }
}
