package com.yologger.heart_to_heart_api.controller.user;

import com.yologger.heart_to_heart_api.service.user.SendEmailVerificationRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
@DisplayName("UserController 테스트")
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("이메일 인증 요청")
    void test_sendEmailVerification() {
        // Given

        // When

        // Then
    }
}