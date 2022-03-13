package com.yologger.heart_to_heart_api.repository.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@DisplayName("MemberRepository 테스트")
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
    @DisplayName("사용자 전체 조회하기 테스트")
    public void test_queryMember() {
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
        List<MemberEntity> members = memberRepository.findAll();
        MemberEntity member = members.get(0);

        // Then
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getName()).isEqualTo(name);
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
        assertTrue(output.isPresent());
        assertThat(output.get().getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("id로 사용자 조회 테스트")

    public void test_findById() {
        // Given
        Long id = 1L;
        String email = "CR7@gmail.com";
        String name = "Cristiano Ronaldo";
        String password = "12341234";
        String nickname = "CR7";

        MemberEntity input = MemberEntity.builder()
                .id(id)
                .email(email)
                .name(name)
                .password(password)
                .nickname(nickname)
                .build();

        memberRepository.save(input);

        // When
        Optional<MemberEntity> result = memberRepository.findById(1L);

        // Then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(id);
    }
}