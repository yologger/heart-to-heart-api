package com.yologger.heart_to_heart_api.controller.post;


import com.yologger.heart_to_heart_api.common.base.ErrorResponseDto;
import com.yologger.heart_to_heart_api.service.post.PostService;
import com.yologger.heart_to_heart_api.service.post.exception.*;
import com.yologger.heart_to_heart_api.service.post.model.GetPostsResponseDto;
import com.yologger.heart_to_heart_api.service.post.model.RegisterPostRequestDto;
import com.yologger.heart_to_heart_api.service.post.model.RegisterPostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/registerPost")
    public ResponseEntity<RegisterPostResponseDto> registerPost(
            @RequestPart(value = "files", required = false) MultipartFile[] files,
            @Valid @NotNull @RequestParam(value = "member_id", required = true) Long memberId,
            @Valid @NotBlank @RequestParam(value = "content", required = true) String content
    ) throws InvalidWriterIdException, InvalidContentTypeException, FileUploadException {
        RegisterPostRequestDto request = RegisterPostRequestDto.builder()
                .files(files)
                .memberId(memberId)
                .content(content)
                .build();
        return postService.registerPost(request);
    }

    @GetMapping("/posts")
    public ResponseEntity<GetPostsResponseDto> getPosts(
            @Valid @NotNull @RequestParam(value = "page", required = true) Integer page,
            @Valid @NotNull @RequestParam(value = "size", required = true) Integer size
    ) throws NoPostsExistException {
        return postService.getPosts(page, size);
    }

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
                .code(PostErrorCode.NO_POSTS_EXIST.getCode())
                .message(PostErrorCode.NO_POSTS_EXIST.getMessage())
                .status(PostErrorCode.NO_POSTS_EXIST.getStatus())
                .build();
        return new ResponseEntity(response, HttpStatus.valueOf(PostErrorCode.NO_POSTS_EXIST.getStatus()));
    }
}
