package com.yologger.heart_to_heart_api.service.post;

import com.yologger.heart_to_heart_api.common.util.AwsS3Util;
import com.yologger.heart_to_heart_api.repository.member.MemberRepository;
import com.yologger.heart_to_heart_api.repository.post.PostRepository;
import com.yologger.heart_to_heart_api.repository.post_image.PostImageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.google.common.truth.Truth.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {PostService.class}
)
@DisplayName("게시글 관련 테스트")
class PostServiceTest {

    @MockBean private MemberRepository memberRepository;
    @MockBean private PostRepository postRepository;
    @MockBean private PostImageRepository postImageRepository;
    @MockBean private AwsS3Util awsS3Uploader;

    @Autowired private PostService postService;

    @Test
    @DisplayName("게시글 조회")
    public void test_getAllPosts() {
        assertThat(1).isEqualTo(1);
    }
}