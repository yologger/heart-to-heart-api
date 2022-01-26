package com.yologger.heart_to_heart_api.common.exception.business;

public class UserAlreadyExistException extends BusinessException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}