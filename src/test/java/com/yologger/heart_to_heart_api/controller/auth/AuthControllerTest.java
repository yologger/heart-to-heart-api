package com.yologger.heart_to_heart_api.controller.auth;

import com.yologger.heart_to_heart_api.repository.member.MemberRepository;
import com.yologger.heart_to_heart_api.service.auth.model.JoinRequestDto;
import com.yologger.heart_to_heart_api.service.auth.model.JoinResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
     webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles(profiles = {"local"})
@DisplayName("AuthController 테스트")
class AuthControllerTest {

    @Autowired
    TestRestTemplate template;

    @Autowired
    MemberRepository memberRepository;

    @Nested
    @DisplayName("회원가입 테스트")
    class Join {

        @Test
        @DisplayName("회원가입 성공 테스트")
        void join() {
            JoinRequestDto request = JoinRequestDto.builder()
                    .email("Smith@gmail.com")
                    .name("Smith")
                    .nickname("Smith")
                    .password("4321Qwer32!!")
                    .build();
            ResponseEntity<JoinResponseDto> response = template.postForEntity("/auth/join", request, JoinResponseDto.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            memberRepository.deleteById(response.getBody().getMemberId());
        }
    }
}