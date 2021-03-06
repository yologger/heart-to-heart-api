package com.yologger.heart_to_heart_api.repository.post;


import com.yologger.heart_to_heart_api.repository.member.AuthorityType;
import com.yologger.heart_to_heart_api.repository.member.MemberEntity;
import com.yologger.heart_to_heart_api.repository.member.MemberRepository;
import com.yologger.heart_to_heart_api.repository.post_image.PostImageEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.truth.Truth.assertThat;

@DataJpaTest
@DisplayName("PostRepository 테스트")
@Import(PostRepositoryTestConfig.class)
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Nested
    @DisplayName("포스트 작성 테스트")
    public class SavePostTest {

        @Test
        @DisplayName("새로운 포스트 작성 테스트")
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

            postRepository.save(newPost);
            Optional<PostEntity> savedPost = postRepository.findById(savedWriter.getId());

            List<String> imageUrlStrings = Arrays.asList("/test/1.png", "/test/2.png");
            List<PostImageEntity> postImageEntities = imageUrlStrings.stream()
                    .map(i -> PostImageEntity.builder()
                            .imageUrl(i)
                            .post(savedPost.get())
                            .build()
                    ).collect(Collectors.toList());

            postImageEntities.forEach(p -> {
                p.setPost(savedPost.get());
            });

            // Then
            assertThat(savedPost.isPresent()).isTrue();
            assertThat(savedPost.get().getContent()).isEqualTo(content);
            assertThat(savedPost.get().getWriter().getEmail()).isEqualTo("yologger1013@gmail.com");
        }
    }

    @Nested
    @DisplayName("글 조회 테스트")
    public class GetAllPostsTest {
        @Test
        @DisplayName("글 조회 성공 테스트")
        public void getAllPosts_success() {
            Long notExistMemberId = 30L;
            List<PostEntity> posts = postRepository.findAllByWriterId(notExistMemberId, 0, 10);
            assertThat(posts.size()).isEqualTo(0);
        }
    }
}