package com.yologger.heart_to_heart_api.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yologger.heart_to_heart_api.config.SecurityConfig;
import com.yologger.heart_to_heart_api.controller.auth.exception.MemberAlreadyExistException;
import com.yologger.heart_to_heart_api.service.auth.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
    controllers = AuthController.class,
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
    }
)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("AuthController 테스트")
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthService mockAuthService;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("회원가입 테스트")
    class JoinTest {

        @Test
        @DisplayName("회원가입 실패 태스트 - 사용자가 이미 존재하는 경우")
        public void test_join_when_user_already_exists() throws Exception {
            String mockEmail = "smith@gmail.com";
            String mockName = "smith";
            String mockNickname = "smith";
            String mockPassword = "1234Qwer56!";

            Map<String, String> body = new HashMap<>();
            body.put("email", mockEmail);
            body.put("name", mockName);
            body.put("nickname", mockNickname);
            body.put("password", mockPassword);

            when(mockAuthService.join(any()))
                    .thenThrow(new MemberAlreadyExistException("Member Already Exists."));

            mvc.perform(MockMvcRequestBuilders.post("/auth/join")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(body))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code", is("AUTH_000")))
            .andDo(print());
        }
    }
}