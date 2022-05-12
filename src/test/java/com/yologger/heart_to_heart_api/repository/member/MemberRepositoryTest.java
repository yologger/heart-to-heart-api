package com.yologger.heart_to_heart_api.repository.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;

@DataJpaTest
@DisplayName("MemberRepository 테스트")
@Import(MemberRepositoryTestConfig.class)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    public void tearDown() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자 추가 및 전체 조회하기 테스트")
    public void test_queryMember() {

        // Given
        String dummyEmail = "CR7@gmail.com";
        String dummyName = "Cristiano Ronaldo";
        String dummyPassword = "12341234";
        String dummyNickname = "CR7";

        MemberEntity input = MemberEntity.builder()
                .email(dummyEmail)
                .name(dummyName)
                .password(dummyPassword)
                .nickname(dummyNickname)
                .build();

        memberRepository.save(input);

        // When
        List<MemberEntity> members = memberRepository.findAll();

        // Then
        assertThat(members.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("이메일로 사용자 조회하기 테스트")
    public void test_findByEmail() {
        // Given
        String email = "CR7@gmail.com";
        String name = "Cristiano Ronaldo";
        String password = "12341234";
        String nickname = "CR7";

        MemberEntity input = MemberEntity.builder()
                .email(email)
                .name(name)
                .password(password)
                .nickname(nickname)
                .build();

        memberRepository.save(input);

        // When
        Optional<MemberEntity> output = memberRepository.findByEmail(email);

        // Then
        assertThat(output.isPresent()).isTrue();
        assertThat(output.get().getEmail()).isEqualTo(email);
    }
}