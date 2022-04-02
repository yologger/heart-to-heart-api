package com.yologger.heart_to_heart_api.controller.post;


import com.yologger.heart_to_heart_api.controller.post.exception.*;
import com.yologger.heart_to_heart_api.service.post.PostService;
import com.yologger.heart_to_heart_api.service.post.model.DeletePostResponseDTO;
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
    public ResponseEntity<GetPostsResponseDto> getAllPosts(
            @Valid @NotNull @RequestParam(value = "member_id", required = true) Long memberId,
            @Valid @NotNull @RequestParam(value = "page", required = true) Integer page,
            @Valid @NotNull @RequestParam(value = "size", required = true) Integer size
    ) throws NoPostsExistException {
        return postService.getAllPosts(memberId, page, size);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<GetPostsResponseDto>  getPosts(
            @Valid @NotNull @PathVariable(required = true) Long id,
            @Valid @NotNull @RequestParam(value = "page", required = true) Integer page,
            @Valid @NotNull @RequestParam(value = "size", required = true) Integer size
    ) throws NoPostsExistException {
        return postService.getPosts(id, page, size);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DeletePostResponseDTO> deletePost(@Valid @NotNull @PathVariable(required = true) Long id) throws NoPostExistException, FileUploadException {
        return postService.deletePost(id);
    }
}
