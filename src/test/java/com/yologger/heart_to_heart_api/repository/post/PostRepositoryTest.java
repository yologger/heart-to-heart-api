package com.yologger.heart_to_heart_api.repository.post;


import com.yologger.heart_to_heart_api.config.TestQueryDslConfig;
import com.yologger.heart_to_heart_api.repository.member.AuthorityType;
import com.yologger.heart_to_heart_api.repository.member.MemberEntity;
import com.yologger.heart_to_heart_api.repository.member.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

@DataJpaTest
@DisplayName("PostRepository 테스트")
@Import(TestQueryDslConfig.class)
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    public void tearDown() {
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Nested
    @DisplayName("포스트 작성 테스트")
    public class SavePostTest {

        @Test
        @DisplayName("새로운 포스트 작성 테스트")
        public void savePost() {

            // Given
            // Create Member
            String email = "yologger1013@gmail.com";
            String name = "yologger";
            String nickname = "yologger";
            String password = "1234Qwer!";

            MemberEntity writer = MemberEntity.builder()
                    .email(email)
                    .nickname(nickname)
                    .name(name)
                    .authority(AuthorityType.USER)
                    .password(password)
                    .build();

            MemberEntity savedWriter = memberRepository.save(writer);

            // When
            String content = "content";

            PostEntity newPost = PostEntity.builder()
                    .content(content)
                    .writer(savedWriter)
                    .build();

            PostEntity savedPost = postRepository.save(newPost);

            // Then
            assertThat(savedPost.getContent()).isEqualTo(content);
            assertThat(savedPost.getWriter().getEmail()).isEqualTo("yologger1013@gmail.com");
        }
    }

    @Test
    @DisplayName("모든 게시글 조회 테스트")
    public void findAll_success() {

        // Given
        String email = "yologger1013@gmail.com";
        String name = "yologger";
        String nickname = "yologger";
        String password = "1234Qwer!";

        MemberEntity writer = MemberEntity.builder()
                .email(email)
                .nickname(nickname)
                .name(name)
                .authority(AuthorityType.USER)
                .password(password)
                .build();

        MemberEntity savedWriter = memberRepository.save(writer);

        String content1 = "content1";

        PostEntity newPost1 = PostEntity.builder()
                .content(content1)
                .writer(savedWriter)
                .build();

        postRepository.save(newPost1);

        String content2 = "content2";

        PostEntity newPost2 = PostEntity.builder()
                .content(content2)
                .writer(savedWriter)
                .build();

        postRepository.save(newPost2);

        List<PostEntity> posts = postRepository.findAll();
        assertThat(posts.size()).isEqualTo(2);
    }

    @Nested
    @DisplayName("작성자 ID로 게시글 조회 테스트")
    public class GetAllPostsTest {
        @Test
        @DisplayName("작성자 ID로 게시글 조회 성공 테스트 - 게시글이 없을 때")
        public void getAllPosts_success_whenPostsNotExists() {
            Long notExistMemberId = 30L;
            List<PostEntity> posts = postRepository.findAllByWriterId(notExistMemberId, 0, 10);
            assertThat(posts.size()).isEqualTo(0);
        }

        @Test
        @DisplayName("작성자 ID로 게시글 조회 성공 테스트 - 게시글이 있을 때")
        public void findAllByWriterId_success_whenPostExist() {

            // Given
            String email = "yologger1013@gmail.com";
            String name = "yologger";
            String nickname = "yologger";
            String password = "1234Qwer!";

            MemberEntity writer = MemberEntity.builder()
                    .email(email)
                    .nickname(nickname)
                    .name(name)
                    .authority(AuthorityType.USER)
                    .password(password)
                    .build();

            MemberEntity savedWriter = memberRepository.save(writer);

            String content = "content";

            PostEntity newPost = PostEntity.builder()
                    .content(content)
                    .writer(savedWriter)
                    .build();

            postRepository.save(newPost);

            List<PostEntity> posts = postRepository.findAllByWriterId(savedWriter.getId(), 0, 10);
            assertThat(posts.size()).isEqualTo(1);
        }
    }
}