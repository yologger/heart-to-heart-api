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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final AwsS3Util awsS3Uploader;

    @Transactional(rollbackFor = Exception.class)
    public RegisterPostResponseDTO registerPost(RegisterPostRequestDTO request) throws InvalidWriterIdException, InvalidContentTypeException, FileUploadException {
        // Check if memberId is valid.
        MemberEntity writer = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new InvalidWriterIdException("Invalid Member Id."));

        // In case files do not exist.
        if (request.getFiles() == null || request.getFiles().length == 0) {
            // Save post.
            PostEntity newPost = PostEntity.builder()
                    .writer(writer)
                    .content(request.getContent())
                    .build();

            PostEntity saved = postRepository.save(newPost);

            RegisterPostResponseDTO response = RegisterPostResponseDTO.builder()
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

            return response;

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

            RegisterPostResponseDTO response = RegisterPostResponseDTO.builder()
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

            return response;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public GetPostsResponseDTO getAllPosts(Long memberId, Integer page, Integer size) {

        List<PostEntity> postEntities = postRepository.findAllPostsOrderByCreatedAtDescExceptBlocking(memberId, page, size);

        List<PostDTO> postDTOS = new ArrayList<PostDTO>();

        for (PostEntity postEntity: postEntities) {
            List<String> postImageUris = new ArrayList<String>();
            for (PostImageEntity postImageEntity: postEntity.getImageUrls()) {
                postImageUris.add(postImageEntity.getImageUrl());
            }
            PostDTO postDTO = PostDTO.builder()
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
            postDTOS.add(postDTO);
        }

        GetPostsResponseDTO response = GetPostsResponseDTO.builder()
                .size(postDTOS.size())
                .posts(postDTOS)
                .build();

        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    public GetPostsResponseDTO getPosts(Long memberId, Integer page, Integer size) {

        List<PostEntity> postEntities = postRepository.findAllByWriterId(memberId, page, size);

        List<PostDTO> postDTOs = new ArrayList<PostDTO>();

        for (PostEntity postEntity: postEntities) {
            List<String> postImageUris = new ArrayList<String>();
            for (PostImageEntity postImageEntity: postEntity.getImageUrls()) {
                postImageUris.add(postImageEntity.getImageUrl());
            }
            PostDTO postDTO = PostDTO.builder()
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
            postDTOs.add(postDTO);
        }

        GetPostsResponseDTO response = GetPostsResponseDTO.builder()
                .size(postDTOs.size())
                .posts(postDTOs)
                .build();

        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    public DeletePostResponseDTO deletePost(Long id) throws NoPostExistException, FileUploadException {

        PostEntity post = postRepository.findById(id).orElseThrow(() -> new NoPostExistException("Invalid 'post_id'"));

        try {
            post.getImageUrls().forEach((imageUrl) -> awsS3Uploader.delete(imageUrl.getImageUrl()));
            postRepository.delete(post);
            DeletePostResponseDTO response = DeletePostResponseDTO.builder()
                    .message("deleted")
                    .build();
            return response;
        } catch (SdkClientException e) {
            log.error(e.getMessage());
            throw new FileUploadException(e.getMessage());
        }
    }
}
