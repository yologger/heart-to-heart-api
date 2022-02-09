//package com.yologger.heart_to_heart_api.repository.user;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@DataJpaTest
//// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@DisplayName("UserRepository 테스트")
//class UserRepositoryTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @BeforeEach
//    void setUp() {
//    }
//
//    @AfterEach
//    public void tearDown() {
//        userRepository.deleteAll();
//    }
//
//    @Test
//    @DisplayName("사용자 전체 조회하기 테스트")
//    public void test_queryUser() {
//        // Given
//        String email = "CR7@gmail.com";
//        String name = "Cristiano Ronaldo";
//        String password = "12341234";
//        String nickname = "CR7";
//
//        UserEntity input = UserEntity.builder()
//                .email(email)
//                .name(name)
//                .password(password)
//                .nickname(nickname)
//                .build();
//
//        userRepository.save(input);
//
//        // When
//        List<UserEntity> users = userRepository.findAll();
//        UserEntity result = users.get(0);
//
//        // Then
//        assertThat(result.getEmail()).isEqualTo(email);
//        assertThat(result.getName()).isEqualTo(name);
//    }
//
//    @Test
//    @DisplayName("이메일로 사용자 조회하기 테스트")
//    public void test_findByEmail() {
//        // Given
//        String email = "CR7@gmail.com";
//        String name = "Cristiano Ronaldo";
//        String password = "12341234";
//        String nickname = "CR7";
//
//        UserEntity input = UserEntity.builder()
//                .email(email)
//                .name(name)
//                .password(password)
//                .nickname(nickname)
//                .build();
//
//        userRepository.save(input);
//
//        // When
//        Optional<UserEntity> output = userRepository.findByEmail(email);
//
//        // Then
//        assertTrue(output.isPresent());
//        assertThat(output.get().getEmail()).isEqualTo(email);
//    }
//}