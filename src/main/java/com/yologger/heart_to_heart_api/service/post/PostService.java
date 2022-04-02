package com.yologger.heart_to_heart_api.service.post;

import com.amazonaws.SdkClientException;
import com.yologger.heart_to_heart_api.common.util.AwsS3Util;
import com.yologger.heart_to_heart_api.controller.post.exception.*;
import com.yologger.heart_to_heart_api.repository.member.MemberEntity;
import com.yologger.heart_to_heart_api.repository.member.MemberRepository;
import com.yologger.heart_to_heart_api.repository.post.PostEntity;
import com.yologger.heart_to_heart_api.repository.post.PostRepository;
import com.yologger.heart_to_heart_api.repository.post_image.PostImageEntity;
import com.yologger.heart_to_heart_api.repository.post_image.PostImageRepository;
import com.yologger.heart_to_heart_api.service.post.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final AwsS3Util awsS3Uploader;

    @Transactional
    public ResponseEntity<RegisterPostResponseDto> registerPost(RegisterPostRequestDto request) throws InvalidWriterIdException, InvalidContentTypeException, FileUploadException {
        // Check if memberId is valid.
        Optional<MemberEntity> result = memberRepository.findById(request.getMemberId());
        if (!result.isPresent()) {
            throw new InvalidWriterIdException("Invalid Member Id.");
        }

        MemberEntity writer = result.get();

        // In case files do not exist.
        if (request.getFiles() == null || request.getFiles().length == 0) {
            // Save post.
            PostEntity newPost = PostEntity.builder()
                    .writer(writer)
                    .content(request.getContent())
                    .build();

            PostEntity saved = postRepository.save(newPost);

            RegisterPostResponseDto response = RegisterPostResponseDto.builder()
                    .postId(saved.getId())
                    .writerId(writer.getId())
                    .writerEmail(writer.getEmail())
                    .writerNickname(writer.getNickname())
                    .avatarUrl(writer.getAvatarUrl())
                    .content(saved.getContent())
                    .imageUrls(null)
                    .createdAt(writer.getCreatedAt())
                    .updatedAt(writer.getUpdatedAt())
                    .build();

            return ResponseEntity.created(null).body(response);

        // In case files exist.
        } else {

            PostEntity newPost = PostEntity.builder()
                    .content(request.getContent())
                    .writer(writer)
                    .build();

            PostEntity savedPost = postRepository.save(newPost);

            List<String> imageUrls = new ArrayList<String>();
            MultipartFile[] files = request.getFiles();
            for (MultipartFile file: files) {
                // Check if contentType is image.
                if (!file.getContentType().startsWith("image")) {
                    // throws invalid content type.
                    throw new InvalidContentTypeException("content type must start with image");
                }
                try {
                    // Upload file
                    String imageUrl = awsS3Uploader.upload(file);

                    // Save to persistence context
                    postImageRepository.save(PostImageEntity.builder()
                            .imageUrl(imageUrl)
                            .post(savedPost)
                            .build());

                    imageUrls.add(imageUrl);

                } catch (IOException e) {
                    log.error(e.getMessage());
                    throw new FileUploadException(e.getMessage());
                } catch (SdkClientException e) {
                    log.error(e.getMessage());
                    throw new FileUploadException(e.getMessage());
                }
            }

            RegisterPostResponseDto response = RegisterPostResponseDto.builder()
                    .postId(savedPost.getId())
                    .writerId(savedPost.getWriter().getId())
                    .writerEmail(savedPost.getWriter().getEmail())
                    .writerNickname(savedPost.getWriter().getNickname())
                    .avatarUrl(savedPost.getWriter().getAvatarUrl())
                    .content(savedPost.getContent())
                    .imageUrls(imageUrls)
                    .createdAt(savedPost.getCreatedAt())
                    .updatedAt(savedPost.getUpdatedAt())
                    .build();

            return ResponseEntity.created(null).body(response);
        }
    }

    public ResponseEntity<GetPostsResponseDto> getPosts(Long memberId, Integer page, Integer size) throws NoPostsExistException {

        List<PostEntity> postEntities = postRepository.findAllPostsOrderByCreatedAtDescExceptBlocking(memberId, page, size);

        List<Post> posts = new ArrayList<Post>();

        for (PostEntity postEntity: postEntities) {
            List<String> postImageUris = new ArrayList<String>();
            for (PostImageEntity postImageEntity: postEntity.getImageUrls()) {
                postImageUris.add(postImageEntity.getImageUrl());
            }
            Post post = Post.builder()
                    .id(postEntity.getId())
                    .writerId(postEntity.getWriter().getId())
                    .writerEmail(postEntity.getWriter().getEmail())
                    .writerNickname(postEntity.getWriter().getNickname())
                    .avatarUrl(postEntity.getWriter().getAvatarUrl())
                    .content(postEntity.getContent())
                    .imageUrls(postImageUris)
                    .createdAt(postEntity.getCreatedAt())
                    .updatedAt(postEntity.getUpdatedAt())
                    .build();
            posts.add(post);
        }

        GetPostsResponseDto response = GetPostsResponseDto.builder()
                .size(posts.size())
                .posts(posts)
                .build();

        return ResponseEntity.created(null).body(response);
    }

    @Transactional
    public ResponseEntity<DeletePostResponseDTO> deletePost(Long id) throws NoPostExistException, FileUploadException {

        PostEntity post = postRepository.findById(id).orElseThrow(() -> new NoPostExistException("Invalid 'post_id'"));

        try {
            post.getImageUrls().forEach((imageUrl) -> awsS3Uploader.delete(imageUrl.getImageUrl()));
            postRepository.delete(post);
            DeletePostResponseDTO response = DeletePostResponseDTO.builder()
                    .message("deleted")
                    .build();
            return ResponseEntity.ok(response);
        } catch (SdkClientException e) {
            log.error(e.getMessage());
            throw new FileUploadException(e.getMessage());
        }
    }
}
