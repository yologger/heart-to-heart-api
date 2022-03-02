package com.yologger.heart_to_heart_api.service.auth;

//@ExtendWith(MockitoExtension.class)
//@DisplayName("AuthService 테스트")
//public class AuthServiceTest {
//
//    @Mock
//    private MemberRepository userRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @InjectMocks
//    private AuthService authService;
//
//    @Nested
//    @DisplayName("회원가입 테스트")
//    class SignUpTest {
//
//        @Test
//        @DisplayName("사용자가 이미 존재하는 경우")
//        void user_already_exist() {
//            // Given
//            JoinRequestDto request = JoinRequestDto.builder().build();
//
//            UserEntity ex = UserEntity.builder()
//                    .email("ronaldo@gmail.com")
//                    .name("Cristiano Ronaldo")
//                    .nickname("CR7")
//                    .password("1234Asdf!@")
//                    .build();
//
//            when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(ex));
//
//            // When & Then
//            assertThatThrownBy(() -> authService.join(request))
//                    .isInstanceOf(UserAlreadyExistException.class);
//        }
//
//        @Test
//        @DisplayName("사용자가 존재하지 않는 경우")
//        void user_not_exist() throws MemberAlreadtExistException {
//            // Given
//            JoinRequestDto request = JoinRequestDto.builder().build();
//
//            Long fakeId = 231321L;
//
//            UserEntity fakeCreatedUser = UserEntity.builder()
//                    .id(fakeId)
//                    .email("ronaldo@gmail.com")
//                    .name("Cristiano Ronaldo")
//                    .nickname("CR7")
//                    .password("1234Asdf!@")
//                    .build();
//
//            when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
//            when(passwordEncoder.encode(any())).thenReturn("encrypted_password");
//            when(userRepository.save(any())).thenReturn(fakeCreatedUser);
//
//            // When
//            ResponseEntity<JoinResponseDto> response = authService.join(request);
//
//            // Then
//            JoinResponseDto responseBody = response.getBody();
//            assertThat(responseBody.getUserId()).isEqualTo(fakeId);
//        }
//    }
//
//    @Nested
//    @DisplayName("로그인 테스트")
//    class LoginTest {
//        @Test
//        @DisplayName("적절하지 않은 이메일을 입력한 경우")
//        void login_with_invalid_email() {
//            assertThat(1).isEqualTo(1);
//        }
//
//        @Test
//        @DisplayName("적절하지 않은 비밀번호을 입력한 경우")
//        void login_with_invalid_password() {
//            assertThat(1).isEqualTo(1);
//        }
//    }
//}
