package com.yologger.heart_to_heart_api.service.auth;

import com.yologger.heart_to_heart_api.config.TestAwsS3Config;
import com.yologger.heart_to_heart_api.controller.auth.exception.MemberAlreadyExistException;
import com.yologger.heart_to_heart_api.controller.auth.exception.MemberNotExistException;
import com.yologger.heart_to_heart_api.repository.member.AuthorityType;
import com.yologger.heart_to_heart_api.repository.member.MemberEntity;
import com.yologger.heart_to_heart_api.repository.member.MemberRepository;
import com.yologger.heart_to_heart_api.service.auth.model.JoinRequestDTO;
import com.yologger.heart_to_heart_api.service.auth.model.JoinResponseDTO;
import com.yologger.heart_to_heart_api.service.auth.model.LoginRequestDTO;
import com.yologger.heart_to_heart_api.service.auth.model.LoginResponseDTO;
import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.BadCredentialsException;
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
        @DisplayName("회원가입 실패 테스트 - 사용자가 이미 존재하는 경우")
        void user_already_exist() {

            // Given
            MemberEntity member = MemberEntity.builder()
                    .email("ronaldo@gmail.com")
                    .email("ronaldo@gmail.com")
                    .name("Cristiano Ronaldo")
                    .nickname("CR7")
                    .password(passwordEncoder.encode("1234Asdf!@"))
                    .build();

            memberRepository.save(member);

            JoinRequestDTO request = JoinRequestDTO.builder()
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
        @DisplayName("회원가입 성공 테스트 - 사용자가 존재하지 않는 경우")
        void user_not_exist() {

            // Given
            String dummyEmail = "ronaldo@gmail.com";
            String name = "Cristiano Ronaldo";
            String nickname = "CR7";
            String password = "4321Fdsa@!";

            JoinRequestDTO request = JoinRequestDTO.builder()
                    .email(dummyEmail)
                    .name(name)
                    .nickname(nickname)
                    .password(password)
                    .build();

            assertThatNoException().isThrownBy(() -> {
                JoinResponseDTO response = authService.join(request);
                assertThat(response.getMemberId()).isNotNull();
            });
        }
    }

    @Nested
    @DisplayName("로그인 테스트")
    class LoginTest {
        @Test
        @DisplayName("로그인 실패 테스트 - 사용자가 존재하지 않을 때")
        public void test_login_failure_member_not_exist() {
            String dummyEmail = "ronaldo@gmail.com";
            String dummyPassword = "4321Fdsa@!";

            LoginRequestDTO request = LoginRequestDTO.builder()
                    .email(dummyEmail)
                    .password(dummyPassword)
                    .build();

            assertThatThrownBy(() -> authService.login(request))
                    .isInstanceOf(MemberNotExistException.class);
        }

        @Test
        @DisplayName("로그인 실패 테스트 - 비밀번호가 틀렸을 때")
        public void test_login_failure_wrong_password() {

            // Given
            String dummyEmail = "ronaldo@gmail.com";
            String dummyName = "Cristiano Ronaldo";
            String dummyNickname = "CR7";
            String dummyPassword = "4321Fdsa@!";

            MemberEntity member = MemberEntity.builder()
                    .email(dummyEmail)
                    .name(dummyName)
                    .nickname(dummyNickname)
                    .authority(AuthorityType.USER)
                    .password(passwordEncoder.encode(dummyPassword))
                    .build();

            memberRepository.save(member);

            String wrongPassword = "1234Fdsa@!";

            LoginRequestDTO request = LoginRequestDTO.builder()
                    .email(dummyEmail)
                    .password(wrongPassword)
                    .build();

            // When, Then
            assertThatThrownBy(() -> authService.login(request))
                    .isInstanceOf(BadCredentialsException.class);
        }

        @Test
        @DisplayName("로그인 성공 테스트 - 비밀번호")
        public void test_login_success() {

            // Given
            String dummyEmail = "ronaldo@gmail.com";
            String dummyName = "Cristiano Ronaldo";
            String dummyNickname = "CR7";
            String dummyPassword = "4321Fdsa@!";

            MemberEntity member = MemberEntity.builder()
                    .email(dummyEmail)
                    .name(dummyName)
                    .nickname(dummyNickname)
                    .authority(AuthorityType.USER)
                    .password(passwordEncoder.encode(dummyPassword))
                    .build();

            memberRepository.save(member);

            LoginRequestDTO request = LoginRequestDTO.builder()
                    .email(dummyEmail)
                    .password(dummyPassword)
                    .build();

            assertThatNoException().isThrownBy(() -> {
                LoginResponseDTO response = authService.login(request);
                assertThat(response.getAccessToken()).isNotBlank();
                assertThat(response.getRefreshToken()).isNotBlank();
            });
        }
    }
}