package com.yologger.heart_to_heart_api.service.post;

import com.yologger.heart_to_heart_api.config.TestAwsS3Config;
import com.yologger.heart_to_heart_api.controller.post.exception.InvalidWriterIdException;
import com.yologger.heart_to_heart_api.controller.post.exception.NoPostExistException;
import com.yologger.heart_to_heart_api.service.post.model.DeletePostResponseDTO;
import com.yologger.heart_to_heart_api.service.post.model.GetPostsResponseDTO;
import com.yologger.heart_to_heart_api.service.post.model.RegisterPostRequestDTO;
import com.yologger.heart_to_heart_api.service.post.model.RegisterPostResponseDTO;
import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestAwsS3Config.class)
@DisplayName("PostService 테스트")
@Transactional
@Sql(scripts = {
    "classpath:sql/dummy/users.sql",
    "classpath:sql/dummy/posts.sql",
    "classpath:sql/dummy/post_images.sql"
})
class PostServiceTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PostService postService;

    @Autowired
    S3Mock s3Mock;

    @AfterEach
    public void shutdownMockS3() {
        s3Mock.stop();
    }

    @Nested
    @DisplayName("글 작성 테스트")
    class WritePostTest {
        @Test
        @DisplayName("실패 테스트 - 사용자가 존재하지 않을 때")
        public void writePost_failure_whenUserNotExists() {
            RegisterPostRequestDTO request = RegisterPostRequestDTO.builder()
                    .memberId(0L)
                    .content("content")
                    .build();

            assertThatThrownBy(() -> postService.registerPost(request))
                    .isInstanceOf(InvalidWriterIdException.class);
        }

        @Test
        @DisplayName("성공 테스트 - 이미지 없을 때")
        public void writePost_success_noImages() {

            // Given
            Long dummyId = 1L;
            String dummyContent = "dummyContent";

            RegisterPostRequestDTO request = RegisterPostRequestDTO.builder()
                    .memberId(dummyId)
                    .content(dummyContent)
                    .build();

            // When & Then
            assertThatNoException().isThrownBy(() -> {
                RegisterPostResponseDTO response = postService.registerPost(request);
                assertThat(response.getPostId()).isNotNull();
                assertThat(response.getContent()).isEqualTo(dummyContent);
            });
        }

        @Test
        @DisplayName("성공 테스트 - 이미지 있을 때")
        public void writePost_success_withImages() {
            // Given
            Long dummyId = 1L;

            String dummyContent = "dummyContent";
            MockMultipartFile firstFile = new MockMultipartFile("files", "image1.png", "image/png", "some image".getBytes());
            MockMultipartFile secondFile = new MockMultipartFile("files", "image2.jpg", "image/jpg", "another image".getBytes());

            MultipartFile[] files = {firstFile, secondFile};

            RegisterPostRequestDTO request = RegisterPostRequestDTO.builder()
                    .files(files)
                    .memberId(dummyId)
                    .content(dummyContent)
                    .build();

            // When & Then
            assertThatNoException().isThrownBy(() -> {
                RegisterPostResponseDTO response = postService.registerPost(request);
                assertThat(response.getPostId()).isNotNull();
                assertThat(response.getContent()).isEqualTo(dummyContent);
            });
        }
    }

    @Nested
    @DisplayName("글 조회 테스트")
    class GetPostsTest {

        @Test
        @DisplayName("글 조회 성공 테스트")
        void getPosts_success() {
            Long dummyMemberId = 1L;
            Integer dummyPage = 0;
            Integer dummySize = 10;

            GetPostsResponseDTO response = postService.getAllPosts(dummyMemberId, dummyPage, dummySize);

            assertThat(response.getSize()).isNotZero();
        }
    }

    @Nested
    @DisplayName("글 삭제 테스트")
    class DeletePostTest {
        @Test
        @DisplayName("글 삭제 성공 테스트")
        void deletePost_success() {
            // Given
            Long postId = 5L;

            // When & Then
            assertThatNoException().isThrownBy(() -> {
                DeletePostResponseDTO response = postService.deletePost(postId);
                assertThat(response.getMessage()).isEqualTo("deleted");
            });
        }

        @Test
        @DisplayName("글 삭제 실패 테스트 - 글 id가 존재하지 않을 때")
        void deletePost_failure_whenIdNotExist() {
            // Given
            Long postId = 20L;

            // When & Then
            assertThatThrownBy(() -> {
                postService.deletePost(postId);
            }).isInstanceOf(NoPostExistException.class);
        }
    }
}