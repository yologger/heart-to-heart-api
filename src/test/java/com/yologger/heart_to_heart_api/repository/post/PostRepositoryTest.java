package com.yologger.heart_to_heart_api.repository.post;


import com.yologger.heart_to_heart_api.repository.member.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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

//    @Test
//    @Ignore
//    @DisplayName("새로운 포스트 작성 및 조회 테스트")
//    public void savePost() {
//        // Given
//        String email = "yologger1013@gmail.com";
//        String name = "yologger";
//        String nickname = "yologger";
//        String password = "1234Qwer!";
//
//        MemberEntity writer = MemberEntity.builder()
//                .email(email)
//                .nickname(nickname)
//                .name(name)
//                .password(password)
//                .build();
//
//        MemberEntity savedWriter = memberRepository.save(writer);
//
//        // When
//        String content = "content";
//
//        PostEntity newPost = PostEntity.builder()
//                .content(content)
//                .writer(savedWriter)
//                .build();
//
//        postRepository.save(newPost);
//        Optional<PostEntity> savedPost = postRepository.findById(savedWriter.getId());
//
//        List<String> imageUrlStrings = Arrays.asList("/123123", "1231233");
//        List<PostImageEntity> postImageEntities = imageUrlStrings.stream().map(i -> PostImageEntity.builder().imageUrl(i).post(savedPost.get()).build()).collect(Collectors.toList());
//
//        postImageEntities.forEach(p -> {
//            p.setPost(savedPost.get());
//        });
//
//        // Then
//        assertThat(savedPost.isPresent()).isTrue();
//        assertThat(savedPost.get().getContent()).isEqualTo(content);
//        assertThat(savedPost.get().getWriter().getEmail()).isEqualTo("yologger1013@gmail.com");
//    }
}