package com.yologger.heart_to_heart_api.service.auth;

import com.yologger.heart_to_heart_api.controller.auth.exception.MemberAlreadyExistException;
import com.yologger.heart_to_heart_api.repository.member.MemberEntity;
import com.yologger.heart_to_heart_api.repository.member.MemberRepository;
import com.yologger.heart_to_heart_api.service.auth.model.JoinRequestDto;
import com.yologger.heart_to_heart_api.service.auth.model.JoinResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService 테스트")
public class AuthServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
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
            // Given

            when(memberRepository.findByEmail(any())).thenReturn(Optional.ofNullable(null));

            String encryptedPassword = "fdsafdsa12!@321";
            when(passwordEncoder.encode(any())).thenReturn(encryptedPassword);

            MemberEntity member = MemberEntity.builder()
                    .email("ronaldo@gmail.com")
                    .name("Cristiano Ronaldo")
                    .nickname("CR7")
                    .password("1234Asdf!@")
                    .build();

            when(memberRepository.save(any())).thenReturn(member);

            // When & Then
            JoinRequestDto request = JoinRequestDto.builder()
                    .email("ronaldo@gmail.com")
                    .name("Cristiano Ronaldo")
                    .nickname("CR7")
                    .password("1234Asdf!@")
                    .build();

            assertThatNoException().isThrownBy(() -> {
                ResponseEntity<JoinResponseDto> response = authService.join(request);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            });
        }
    }
}
