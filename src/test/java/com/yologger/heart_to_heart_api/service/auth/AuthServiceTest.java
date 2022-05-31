package com.yologger.heart_to_heart_api.service.auth;

import com.yologger.heart_to_heart_api.config.TestAwsS3Config;
import com.yologger.heart_to_heart_api.repository.member.MemberRepository;
import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestAwsS3Config.class)
@DisplayName("AuthService 테스트")
public class AuthServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    S3Mock s3Mock;

    @AfterEach
    public void shutdownMockS3(){
        s3Mock.stop();
    }

    @Nested
    @DisplayName("회원가입 테스트")
    class JoinTest {
//        @Test
//        @DisplayName("사용자가 이미 존재하는 경우")
//        void user_already_exist() {
//
//            // Given
//            MemberEntity member = MemberEntity.builder()
//                    .email("ronaldo@gmail.com")
//                    .email("ronaldo@gmail.com")
//                    .name("Cristiano Ronaldo")
//                    .nickname("CR7")
//                    .password("1234Asdf!@")
//                    .build();
//
//            memberRepository.save(member);
//
//            JoinRequestDto request = JoinRequestDto.builder()
//                    .email("ronaldo@gmail.com")
//                    .name("Cristiano Ronaldo")
//                    .nickname("CR7")
//                    .password("1234Asdf!@")
//                    .build();
//
//            // When & Then
//            assertThatThrownBy(() -> authService.join(request))
//                    .isInstanceOf(MemberAlreadyExistException.class);
//        }

//        @Test
//        @DisplayName("사용자가 존재하지 않는 경우")
//        void user_not_exist() {
//            // Given
//
//            when(memberRepository.findByEmail(any())).thenReturn(Optional.ofNullable(null));
//
//            String encryptedPassword = "fdsafdsa12!@321";
//            when(passwordEncoder.encode(any())).thenReturn(encryptedPassword);
//
//            MemberEntity member = MemberEntity.builder()
//                    .email("ronaldo@gmail.com")
//                    .name("Cristiano Ronaldo")
//                    .nickname("CR7")
//                    .password("1234Asdf!@")
//                    .build();
//
//            when(memberRepository.save(any())).thenReturn(member);
//
//            // When & Then
//            JoinRequestDto request = JoinRequestDto.builder()
//                    .email("ronaldo@gmail.com")
//                    .name("Cristiano Ronaldo")
//                    .nickname("CR7")
//                    .password("1234Asdf!@")
//                    .build();
//
//            assertThatNoException().isThrownBy(() -> {
//                ResponseEntity<JoinResponseDto> response = authService.join(request);
//                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//            });
//        }
    }
}
