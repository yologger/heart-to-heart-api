package com.yologger.heart_to_heart_api.repository.post_image;

import com.yologger.heart_to_heart_api.config.TestQueryDslConfig;
import com.yologger.heart_to_heart_api.repository.member.MemberRepository;
import com.yologger.heart_to_heart_api.repository.post.PostEntity;
import com.yologger.heart_to_heart_api.repository.post.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

@DataJpaTest
@DisplayName("PostImageRepository 테스트")
@Import(TestQueryDslConfig.class)
public class PostImageRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostImageRepository postImageRepository;

    @AfterEach
    public void tearDown() {
        postImageRepository.deleteAll();
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 이미지 저장 및 조회 테스트")
    public void addPostTest() {

        // Add Post
        String content = "content";

        PostEntity newPost = PostEntity.builder()
                .content(content)
                .build();

        postRepository.save(newPost);

        // Add PostImage
        String dummyImageUrl = "https://dummy.dummy/dummy.png";

        PostImageEntity postImage = PostImageEntity.builder()
                .imageUrl(dummyImageUrl)
                .post(newPost)
                .build();

        postImageRepository.save(postImage);

        List<PostImageEntity> postImages = postImageRepository.findAll();
        assertThat(postImages.size()).isEqualTo(1);
    }
}
