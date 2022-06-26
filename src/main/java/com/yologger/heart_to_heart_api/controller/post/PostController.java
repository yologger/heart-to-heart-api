package com.yologger.heart_to_heart_api.controller.post;


import com.yologger.heart_to_heart_api.controller.post.exception.*;
import com.yologger.heart_to_heart_api.service.post.PostService;
import com.yologger.heart_to_heart_api.service.post.model.DeletePostResponseDTO;
import com.yologger.heart_to_heart_api.service.post.model.GetPostsResponseDTO;
import com.yologger.heart_to_heart_api.service.post.model.RegisterPostRequestDTO;
import com.yologger.heart_to_heart_api.service.post.model.RegisterPostResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/post")
@Api(tags = "게시글 관련 엔드포인트")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/registerPost")
    public ResponseEntity<RegisterPostResponseDTO> registerPost(
            @RequestPart(value = "files", required = false) MultipartFile[] files,
            @Valid @NotNull @RequestParam(value = "member_id", required = true) Long memberId,
            @Valid @NotBlank @RequestParam(value = "content", required = true) String content
    ) throws InvalidWriterIdException, InvalidContentTypeException, FileUploadException {
        RegisterPostRequestDTO request = RegisterPostRequestDTO.builder()
                .files(files)
                .memberId(memberId)
                .content(content)
                .build();
        return new ResponseEntity<>(postService.registerPost(request), HttpStatus.CREATED);
    }

    @ApiOperation(value = "사용자 id로 게시글 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "member_id", value = "사용자 아이디", required = true, paramType = "query"),
            @ApiImplicitParam(name = "page", value = "페이지", required = true, paramType = "query"),
            @ApiImplicitParam(name = "size", value = "페이지 크기", required = true, paramType = "query")
    })
    @GetMapping("/posts")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<GetPostsResponseDTO> getAllPosts(
        @Valid @NotNull @RequestParam(value = "member_id", required = true) Long memberId,
        @Valid @NotNull @RequestParam(value = "page", required = true) Integer page,
        @Valid @NotNull @RequestParam(value = "size", required = true) Integer size
    ) {
        return new ResponseEntity(postService.getAllPosts(memberId, page, size), HttpStatus.OK);
    }

    @GetMapping("/posts/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<GetPostsResponseDTO>  getPosts(
            @Valid @NotNull @PathVariable(required = true) Long id,
            @Valid @NotNull @RequestParam(value = "page", required = true) Integer page,
            @Valid @NotNull @RequestParam(value = "size", required = true) Integer size
    ) throws NoPostsExistException {
        return new ResponseEntity<>(postService.getPosts(id, page, size), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<DeletePostResponseDTO> deletePost(@Valid @NotNull @PathVariable(required = true) Long id) throws NoPostExistException, FileUploadException {
        return new ResponseEntity<>(postService.deletePost(id), HttpStatus.NO_CONTENT);
    }
}
