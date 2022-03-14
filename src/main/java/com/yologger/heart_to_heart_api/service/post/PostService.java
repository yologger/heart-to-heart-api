package com.yologger.heart_to_heart_api.service.post;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.yologger.heart_to_heart_api.common.util.AwsS3Uploader;
import com.yologger.heart_to_heart_api.repository.member.MemberEntity;
import com.yologger.heart_to_heart_api.repository.member.MemberRepository;
import com.yologger.heart_to_heart_api.repository.post.PostEntity;
import com.yologger.heart_to_heart_api.repository.post.PostRepository;
import com.yologger.heart_to_heart_api.repository.post_image.PostImageEntity;
import com.yologger.heart_to_heart_api.service.post.exception.FileUploadException;
import com.yologger.heart_to_heart_api.service.post.exception.InvalidContentTypeException;
import com.yologger.heart_to_heart_api.service.post.exception.InvalidWriterIdException;
import com.yologger.heart_to_heart_api.service.post.model.RegisterPostRequestDto;
import com.yologger.heart_to_heart_api.service.post.model.RegisterPostResponseDto;
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
    private final AwsS3Uploader awsS3Uploader;

    @Transactional
    public ResponseEntity<RegisterPostResponseDto> registerPost(RegisterPostRequestDto request) throws InvalidWriterIdException, InvalidContentTypeException, FileUploadException {
        // Check if memberId is valid.
        Optional<MemberEntity> result = memberRepository.findById(request.getMemberId());
        if (!result.isPresent()) {
            throw new InvalidWriterIdException("Invalid Member Id.");
        }

        // In case files do not exist.
        if (request.getFiles() == null || request.getFiles().length == 0) {
            // Save post.
            MemberEntity writer = result.get();
            PostEntity newPost = PostEntity.builder()
                    .writer(writer)
                    .content(request.getContent())
                    .build();

            PostEntity saved = postRepository.save(newPost);

            RegisterPostResponseDto responseDto = RegisterPostResponseDto.builder()
                    .postId(saved.getId())
                    .content(saved.getContent())
                    .writerId(writer.getId())
                    .build();

            return ResponseEntity.created(null).body(responseDto);

        // In case files exist.
        } else {
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
                    imageUrls.add(imageUrl);
                } catch (IOException e) {
                    log.error(e.getMessage());
                    throw new FileUploadException(e.getMessage());
                } catch (SdkClientException e) {
                    log.error(e.getMessage());
                    throw new FileUploadException(e.getMessage());
                }
            }
            // Save post.
            MemberEntity member = result.get();
            List<PostImageEntity> postImages = new ArrayList<PostImageEntity>();
            for (String imageUrl : imageUrls) {
                PostImageEntity postImage = PostImageEntity.builder()
                        .imageUrl(imageUrl)
                        .build();

                postImages.add(postImage);
            }
            PostEntity newPost = PostEntity.builder()
                    .content(request.getContent())
                    .writer(member)
                    .imageUrls(postImages)
                    .build();
            PostEntity created = postRepository.save(newPost);

            RegisterPostResponseDto response = RegisterPostResponseDto.builder()
                    .writerId(request.getMemberId())
                    .postId(created.getId())
                    .content(created.getContent())
                    .imageUrls(imageUrls)
                    .build();

            return ResponseEntity.created(null).body(response);
        }
    }
}
