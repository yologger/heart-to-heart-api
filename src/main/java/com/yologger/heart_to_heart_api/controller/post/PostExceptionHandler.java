package com.yologger.heart_to_heart_api.controller.post;

import com.yologger.heart_to_heart_api.common.base.ErrorResponseDto;
import com.yologger.heart_to_heart_api.controller.post.exception.FileUploadException;
import com.yologger.heart_to_heart_api.controller.post.exception.InvalidContentTypeException;
import com.yologger.heart_to_heart_api.controller.post.exception.InvalidWriterIdException;
import com.yologger.heart_to_heart_api.controller.post.exception.NoPostsExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = PostController.class)
public class PostExceptionHandler {

    @ExceptionHandler(value = InvalidWriterIdException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidMemberIdException(InvalidWriterIdException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(PostErrorCode.INVALID_WRITER_ID.getCode())
                .message(PostErrorCode.INVALID_WRITER_ID.getMessage())
                .status(PostErrorCode.INVALID_WRITER_ID.getStatus())
                .build();
        return new ResponseEntity(response, HttpStatus.valueOf(PostErrorCode.INVALID_WRITER_ID.getStatus()));
    }

    @ExceptionHandler(value = InvalidContentTypeException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidContentTypeException(InvalidContentTypeException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(PostErrorCode.INVALID_CONTENT_TYPE.getCode())
                .message(PostErrorCode.INVALID_CONTENT_TYPE.getMessage())
                .status(PostErrorCode.INVALID_CONTENT_TYPE.getStatus())
                .build();
        return new ResponseEntity(response, HttpStatus.valueOf(PostErrorCode.INVALID_CONTENT_TYPE.getStatus()));
    }

    @ExceptionHandler(value = FileUploadException.class)
    public ResponseEntity<ErrorResponseDto> handleFileUploadException(FileUploadException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(PostErrorCode.FILE_UPLOAD_ERROR.getCode())
                .message(PostErrorCode.FILE_UPLOAD_ERROR.getMessage())
                .status(PostErrorCode.FILE_UPLOAD_ERROR.getStatus())
                .build();
        return new ResponseEntity(response, HttpStatus.valueOf(PostErrorCode.FILE_UPLOAD_ERROR.getStatus()));
    }


    @ExceptionHandler(value = NoPostsExistException.class)
    public ResponseEntity<ErrorResponseDto> handleNoPostsExistException(NoPostsExistException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(PostErrorCode.NO_POST_EXIST.getCode())
                .message(PostErrorCode.NO_POST_EXIST.getMessage())
                .status(PostErrorCode.NO_POST_EXIST.getStatus())
                .build();
        return new ResponseEntity(response, HttpStatus.valueOf(PostErrorCode.NO_POST_EXIST.getStatus()));
    }
}
