package com.yologger.heart_to_heart_api.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yologger.heart_to_heart_api.config.TestSecurityConfig;
import com.yologger.heart_to_heart_api.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"local"})
@DisplayName("AuthController 테스트")
@Import(TestSecurityConfig.class)
class AuthControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    MemberRepository memberRepository;

    @Nested
    @DisplayName("회원가입 테스트")
    class Join {

        @Test
        @WithMockUser
        @DisplayName("회원가입 성공 테스트")
        void join() throws Exception {

        }
    }
}