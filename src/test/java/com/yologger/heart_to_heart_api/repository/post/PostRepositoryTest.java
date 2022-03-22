package com.yologger.heart_to_heart_api.repository.post;


import com.yologger.heart_to_heart_api.repository.member.MemberEntity;
import com.yologger.heart_to_heart_api.repository.member.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;

@DataJpaTest
@DisplayName("PostRepository 테스트")
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown() {
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("새로운 포스트 작성 및 조회 테스트")
    public void savePost() {
        // Given
        String email = "yologger1013@gmail.com";
        String name = "yologger";
        String nickname = "yologger";
        String password = "1234Qwer!";

        MemberEntity writer = MemberEntity.builder()
                .email(email)
                .nickname(nickname)
                .name(name)
                .password(password)
                .build();

        MemberEntity savedWriter = memberRepository.save(writer);

        String content = "content";

        PostEntity newPost = PostEntity.builder()
                .content(content)
                .writer(savedWriter)
                .build();

        // When
        postRepository.save(newPost);
        Optional<PostEntity> savedPost = postRepository.findById(savedWriter.getId());

        assertThat(savedPost.isPresent()).isTrue();
        assertThat(savedPost.get().getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("새로운 포스트 작성 및 조회 테스트")
    public void getPosts() {
        assertThat("a").isEqualTo("a");
    }
}