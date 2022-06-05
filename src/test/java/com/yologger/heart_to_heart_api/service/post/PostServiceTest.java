package com.yologger.heart_to_heart_api.service.post;

import com.yologger.heart_to_heart_api.config.TestAwsS3Config;
import com.yologger.heart_to_heart_api.service.post.model.GetPostsResponseDTO;
import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    @DisplayName("글 조회 테스트")
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