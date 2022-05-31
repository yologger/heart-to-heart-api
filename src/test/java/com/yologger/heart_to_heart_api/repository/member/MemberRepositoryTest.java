package com.yologger.heart_to_heart_api.repository.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;

@DataJpaTest
@DisplayName("MemberRepository 테스트")
@Import(MemberRepositoryTestConfig.class)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    public void setUp() {

    }

    @Test
    @DisplayName("사용자 추가 및 전체 조회하기 테스트")
    public void test_queryMember() {
        // Given

        String dummyEmail = "CR7@gmail.com";
        String dummyName = "Cristiano Ronaldo";
        String dummyPassword = "12341234";
        String dummyNickname = "CR7";

        MemberEntity newMember = MemberEntity.builder()
                .email(dummyEmail)
                .name(dummyName)
                .password(dummyPassword)
                .nickname(dummyNickname)
                .authority(AuthorityType.USER)
                .build();

        // When
        MemberEntity saved = memberRepository.save(newMember);
        List<MemberEntity> members = memberRepository.findAll();

        // Then
        assertThat(members.size()).isEqualTo(1);
        assertThat(saved.getEmail()).isEqualTo(dummyEmail);
        assertThat(saved.getAuthority()).isEqualTo(AuthorityType.USER);
    }

    @Test
    @DisplayName("이메일로 사용자 조회하기 테스트")
    public void test_findByEmail() {

        String email = "CR7@gmail.com";
        String name = "Cristiano Ronaldo";
        String password = "12341234";
        String nickname = "CR7";

        MemberEntity input = MemberEntity.builder()
                .email(email)
                .name(name)
                .password(password)
                .nickname(nickname)
                .authority(AuthorityType.USER)
                .build();

        memberRepository.save(input);

        // When
        Optional<MemberEntity> output = memberRepository.findOneByEmail(email);

        // Then
        assertThat(output.isPresent()).isTrue();
        assertThat(output.get().getEmail()).isEqualTo(email);
    }
}