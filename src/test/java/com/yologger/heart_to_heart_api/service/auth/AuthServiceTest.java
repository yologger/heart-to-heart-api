package com.yologger.heart_to_heart_api.service.auth;

import com.yologger.heart_to_heart_api.common.util.JwtUtil;
import com.yologger.heart_to_heart_api.common.util.MailUtil;
import com.yologger.heart_to_heart_api.controller.auth.exception.MemberAlreadyExistException;
import com.yologger.heart_to_heart_api.repository.member.MemberEntity;
import com.yologger.heart_to_heart_api.repository.member.MemberRepository;
import com.yologger.heart_to_heart_api.repository.verification_code.VerificationCodeRepository;
import com.yologger.heart_to_heart_api.service.auth.model.JoinRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = {AuthService.class}
)
@DisplayName("AuthService 테스트")
public class AuthServiceTest {

    @MockBean
    private MailUtil mailUtil;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private VerificationCodeRepository verificationCodeRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Nested
    @DisplayName("회원가입 테스트")
    class JoinTest {
        @Test
        @DisplayName("사용자가 이미 존재하는 경우")
        void user_already_exist() {
            // Given
            JoinRequestDto request = JoinRequestDto.builder()
                    .email("ronaldo@gmail.com")
                    .name("Cristiano Ronaldo")
                    .nickname("CR7")
                    .password("1234Asdf!@")
                    .build();

            MemberEntity member = MemberEntity.builder()
                    .email("ronaldo@gmail.com")
                    .name("Cristiano Ronaldo")
                    .nickname("CR7")
                    .password("1234Asdf!@")
                    .build();

            when(memberRepository.findByEmail(any())).thenReturn(Optional.ofNullable(member));

            // When & Then
            assertThatThrownBy(() -> authService.join(request))
                    .isInstanceOf(MemberAlreadyExistException.class);
        }

        @Test
        @DisplayName("사용자가 존재하지 않는 경우")
        void user_not_exist() {

        }
    }
}
