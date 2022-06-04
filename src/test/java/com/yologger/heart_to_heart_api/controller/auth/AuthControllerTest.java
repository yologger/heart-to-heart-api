package com.yologger.heart_to_heart_api.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yologger.heart_to_heart_api.config.SecurityConfig;
import com.yologger.heart_to_heart_api.controller.auth.exception.MemberAlreadyExistException;
import com.yologger.heart_to_heart_api.controller.auth.exception.MemberNotExistException;
import com.yologger.heart_to_heart_api.service.auth.AuthService;
import com.yologger.heart_to_heart_api.service.auth.model.JoinResponseDTO;
import com.yologger.heart_to_heart_api.service.auth.model.LoginResponseDTO;
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
import org.springframework.security.authentication.BadCredentialsException;
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

            // Given
            when(mockAuthService.join(any()))
                    .thenThrow(new MemberAlreadyExistException("Member Already Exists."));

            // When & Then
            String mockEmail = "smith@gmail.com";
            String mockName = "smith";
            String mockNickname = "smith";
            String mockPassword = "1234Qwer56!";

            Map<String, String> body = new HashMap<>();
            body.put("email", mockEmail);
            body.put("name", mockName);
            body.put("nickname", mockNickname);
            body.put("password", mockPassword);

            mvc.perform(MockMvcRequestBuilders.post("/auth/join")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(body))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code", is("AUTH_000")))
            .andDo(print());
        }

        @Test
        @DisplayName("회원가입 성공 태스트")
        public void test_join_success() throws Exception {

            // Given
            Long mockMemberId = 1L;

            JoinResponseDTO mockResponse = JoinResponseDTO.builder()
                    .memberId(mockMemberId)
                    .build();

            when(mockAuthService.join(any()))
                    .thenReturn(mockResponse);

            // When
            String mockEmail = "smith@gmail.com";
            String mockName = "smith";
            String mockNickname = "smith";
            String mockPassword = "1234Qwer56!";

            Map<String, String> body = new HashMap<>();
            body.put("email", mockEmail);
            body.put("name", mockName);
            body.put("nickname", mockNickname);
            body.put("password", mockPassword);

            mvc.perform(MockMvcRequestBuilders.post("/auth/join")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(body))
            )
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.member_id").exists())
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("로그인 테스트")
    class LoginTest {
        @Test
        @DisplayName("로그인 실패 테스트 - 사용자가 존재하지 않을 때")
        public void test_login_failure_when_user_not_exist() throws Exception {
            when(mockAuthService.login(any()))
                    .thenThrow(new MemberNotExistException("Member does not exist."));

            // When
            String mockEmail = "smith@gmail.com";
            String mockPassword = "1234Qwer56!";

            Map<String, String> body = new HashMap<>();
            body.put("email", mockEmail);
            body.put("password", mockPassword);

            mvc.perform(MockMvcRequestBuilders.post("/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body))
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code", is("AUTH_005")))
                    .andDo(print());
        }

        @Test
        @DisplayName("로그인 실패 테스트 - 잘못된 비밀번호")
        public void test_login_failure_when_invalid_password() throws Exception {

            // Given
            when(mockAuthService.login(any()))
                    .thenThrow(new BadCredentialsException("Invalid password"));


            String mockEmail = "smith@gmail.com";
            String mockPassword = "1234Qwer56!";

            Map<String, String> body = new HashMap<>();
            body.put("email", mockEmail);
            body.put("password", mockPassword);

            // When, Then
            mvc.perform(MockMvcRequestBuilders.post("/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body))
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code", is("AUTH_006")))
                    .andDo(print());
        }

        @Test
        @DisplayName("로그인 성공 테스트")
        public void test_login_success() throws Exception {

            // Given
            Long mockId = 1L;
            String mockName = "smith";
            String mockNickname = "smith";
            String mockAccessToken = "mock_access_token";
            String mockRefreshToken = "mock_refresh_token";

            LoginResponseDTO mockResponse = LoginResponseDTO.builder()
                    .memberId(mockId)
                    .accessToken(mockAccessToken)
                    .refreshToken(mockRefreshToken)
                    .name(mockName)
                    .nickname(mockNickname)
                    .build();

            when(mockAuthService.login(any()))
                    .thenReturn(mockResponse);

            // When, Then
            String mockEmail = "smith@gmail.com";
            String mockPassword = "1234Qwer56!";

            Map<String, String> body = new HashMap<>();
            body.put("email", mockEmail);
            body.put("password", mockPassword);

            mvc.perform(MockMvcRequestBuilders.post("/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body))
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is(mockName)))
                    .andExpect(jsonPath("$.nickname", is(mockNickname)))
                    .andExpect(jsonPath("$.access_token").exists())
                    .andExpect(jsonPath("$.refresh_token").exists())
                    .andDo(print());
        }
    }
}