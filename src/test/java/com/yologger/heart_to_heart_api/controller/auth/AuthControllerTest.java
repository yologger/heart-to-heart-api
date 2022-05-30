package com.yologger.heart_to_heart_api.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yologger.heart_to_heart_api.common.util.JwtUtil;
import com.yologger.heart_to_heart_api.config.TestSecurityConfig;
import com.yologger.heart_to_heart_api.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"local"})
@DisplayName("AuthController 테스트")
@Import(TestSecurityConfig.class)
class AuthControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    JwtUtil jwtUtil;

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
            Map<String, String> body = new HashMap<>();
            body.put("email", "Smith@gmail.com");
            body.put("name", "Smith");
            body.put("nickname", "Smith");
            body.put("password", "4321Qwer32!!");

            String mockAccessToken = jwtUtil.generateAccessToken(1L, "Smith@gmail.com", "Smith", "Smith");

            mvc.perform(post("/auth/join")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + mockAccessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)
            ))
            .andExpect(status().isOk())
            .andDo(print());
        }
    }
}