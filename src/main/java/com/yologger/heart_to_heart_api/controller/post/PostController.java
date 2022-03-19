package com.yologger.heart_to_heart_api.controller.post;


import com.yologger.heart_to_heart_api.controller.post.exception.FileUploadException;
import com.yologger.heart_to_heart_api.controller.post.exception.InvalidContentTypeException;
import com.yologger.heart_to_heart_api.controller.post.exception.InvalidWriterIdException;
import com.yologger.heart_to_heart_api.controller.post.exception.NoPostsExistException;
import com.yologger.heart_to_heart_api.service.post.PostService;
import com.yologger.heart_to_heart_api.service.post.model.GetPostsResponseDto;
import com.yologger.heart_to_heart_api.service.post.model.RegisterPostRequestDto;
import com.yologger.heart_to_heart_api.service.post.model.RegisterPostResponseDto;
import lombok.RequiredArgsConstructor;
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
}
