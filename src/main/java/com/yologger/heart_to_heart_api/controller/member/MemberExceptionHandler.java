package com.yologger.heart_to_heart_api.controller.member;

import com.amazonaws.SdkClientException;
import com.yologger.heart_to_heart_api.common.base.ErrorResponseDto;
import com.yologger.heart_to_heart_api.controller.member.exception.InvalidContentTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@RestControllerAdvice(basePackageClasses = MemberController.class)
public class MemberExceptionHandler {



    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponseDto> handleIOException(IOException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(MemberErrorCode.IO_ERROR.getCode())
                .message(MemberErrorCode.IO_ERROR.getMessage())
                .status(MemberErrorCode.IO_ERROR.getStatus())
                .build();
        return new ResponseEntity(response, HttpStatus.valueOf(MemberErrorCode.IO_ERROR.getStatus()));
    }

    @ExceptionHandler(SdkClientException.class)
    public ResponseEntity<ErrorResponseDto> handleSdkClientException(SdkClientException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(MemberErrorCode.FILE_UPLOAD_ERROR.getCode())
                .message(MemberErrorCode.FILE_UPLOAD_ERROR.getMessage())
                .status(MemberErrorCode.FILE_UPLOAD_ERROR.getStatus())
                .build();
        return new ResponseEntity(response, HttpStatus.valueOf(MemberErrorCode.FILE_UPLOAD_ERROR.getStatus()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(EntityNotFoundException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(MemberErrorCode.MEMBER_NOT_EXIST.getCode())
                .message(MemberErrorCode.MEMBER_NOT_EXIST.getMessage())
                .status(MemberErrorCode.MEMBER_NOT_EXIST.getStatus())
                .build();
        return new ResponseEntity(response, HttpStatus.valueOf(MemberErrorCode.MEMBER_NOT_EXIST.getStatus()));
    }

    @ExceptionHandler(InvalidContentTypeException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidContentTypeException(InvalidContentTypeException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(MemberErrorCode.INVALID_CONTENT_TYPE.getCode())
                .message(MemberErrorCode.INVALID_CONTENT_TYPE.getMessage())
                .status(MemberErrorCode.INVALID_CONTENT_TYPE.getStatus())
                .build();
        return new ResponseEntity(response, HttpStatus.valueOf(MemberErrorCode.INVALID_CONTENT_TYPE.getStatus()));
    }
}
