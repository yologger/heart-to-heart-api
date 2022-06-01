package com.yologger.heart_to_heart_api.service.auth;

import com.yologger.heart_to_heart_api.config.TestAwsS3Config;
import com.yologger.heart_to_heart_api.controller.auth.exception.MemberAlreadyExistException;
import com.yologger.heart_to_heart_api.repository.member.MemberEntity;
import com.yologger.heart_to_heart_api.repository.member.MemberRepository;
import com.yologger.heart_to_heart_api.service.auth.model.JoinRequestDto;
import com.yologger.heart_to_heart_api.service.auth.model.JoinResponseDto;
import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestAwsS3Config.class)
@DisplayName("AuthService 테스트")
@Transactional
public class AuthServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    S3Mock s3Mock;

    @AfterEach
    public void shutdownMockS3(){
        s3Mock.stop();
    }

    @Nested
    @DisplayName("회원가입 테스트")
    class JoinTest {

        @Test
        @DisplayName("사용자가 이미 존재하는 경우")
        void user_already_exist() {

            // Given
            MemberEntity member = MemberEntity.builder()
                    .email("ronaldo@gmail.com")
                    .email("ronaldo@gmail.com")
                    .name("Cristiano Ronaldo")
                    .nickname("CR7")
                    .password("1234Asdf!@")
                    .build();

            memberRepository.save(member);

            JoinRequestDto request = JoinRequestDto.builder()
                    .email("ronaldo@gmail.com")
                    .name("Cristiano Ronaldo")
                    .nickname("CR7")
                    .password("1234Asdf!@")
                    .build();

            // When & Then
            assertThatThrownBy(() -> authService.join(request))
                    .isInstanceOf(MemberAlreadyExistException.class);
        }

        @Test
        @DisplayName("사용자가 존재하지 않는 경우")
        void user_not_exist() {

            // Given
            String dummyEmail = "ronaldo@gmail.com";
            String name = "Cristiano Ronaldo";
            String nickname = "CR7";
            String password = "4321Fdsa@!";

            JoinRequestDto request = JoinRequestDto.builder()
                    .email(dummyEmail)
                    .name(name)
                    .nickname(nickname)
                    .password(password)
                    .build();

            assertThatNoException().isThrownBy(() -> {
                ResponseEntity<JoinResponseDto> response = authService.join(request);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            });

        }
    }
}
