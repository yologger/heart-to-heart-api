package com.yologger.heart_to_heart_api.controller.post;


import com.yologger.heart_to_heart_api.common.base.ErrorResponseDto;
import com.yologger.heart_to_heart_api.controller.auth.exception.AuthErrorCode;
import com.yologger.heart_to_heart_api.service.post.PostService;
import com.yologger.heart_to_heart_api.service.post.exception.InvalidWriterIdException;
import com.yologger.heart_to_heart_api.service.post.exception.PostErrorCode;
import com.yologger.heart_to_heart_api.service.post.model.RegisterPostRequestDto;
import com.yologger.heart_to_heart_api.service.post.model.RegisterPostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @Valid @NotNull @RequestParam(value = "member_id", required = true) Long memberId,
            @Valid @NotBlank @RequestParam(value = "content", required = true) String content
    ) throws InvalidWriterIdException {
        RegisterPostRequestDto request = RegisterPostRequestDto.builder()
                .memberId(memberId)
                .content(content)
                .build();
        return postService.registerPost(request);
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

}
