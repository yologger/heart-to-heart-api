package com.yologger.heart_to_heart_api.service.post;

import com.yologger.heart_to_heart_api.config.TestAwsS3Config;
import com.yologger.heart_to_heart_api.controller.post.exception.InvalidWriterIdException;
import com.yologger.heart_to_heart_api.service.post.model.GetPostsResponseDTO;
import com.yologger.heart_to_heart_api.service.post.model.RegisterPostRequestDTO;
import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestAwsS3Config.class)
@DisplayName("PostService 테스트")
@Transactional
class PostServiceTest {

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
        public void writePost_whenUserNotExists() {
            RegisterPostRequestDTO request = RegisterPostRequestDTO.builder()
                    .memberId(1L)
                    .content("content")
                    .build();

            assertThatThrownBy(() -> postService.registerPost(request))
                    .isInstanceOf(InvalidWriterIdException.class);
        }
    }

    @Nested
    @DisplayName("글 조회 테스트")
    class GetPostsTest {

        @Test
        @DisplayName("글 조회 성공 테스트")
        @Sql(scripts = {
                "classpath:sql/dummy/users.sql",
                "classpath:sql/dummy/posts.sql",
                "classpath:sql/dummy/post_images.sql"
        })
        void test_query_posts() {
            Long dummyMemberId = 0L;
            Integer dummyPage = 0;
            Integer dummySize = 10;

            GetPostsResponseDTO response = postService.getAllPosts(dummyMemberId, dummyPage, dummySize);

            assertThat(response.getSize()).isNotZero();
        }
    }
}