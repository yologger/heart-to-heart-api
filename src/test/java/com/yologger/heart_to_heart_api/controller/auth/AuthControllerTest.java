package com.yologger.heart_to_heart_api.controller.auth;

import com.yologger.heart_to_heart_api.common.response.ErrorResponseDto;
import com.yologger.heart_to_heart_api.service.auth.JoinResponseDto;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("AuthController 테스트")
public class AuthControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Nested
    @DisplayName("회원가입 테스트")
    class SignUp {

        @Test
        @DisplayName("회원가입 성공 테스트")
        public void signup_success() throws JSONException {

            // Given
            String url = "http://localhost:" + port + "/auth/join";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            JSONObject body = new JSONObject();
            body.put("email", "ronaldo@gmail.com");
            body.put("name", "Cristiano Ronaldo");
            body.put("nickname", "CR7");
            body.put("password", "1234Asdf!@");

            HttpEntity<String> request = new HttpEntity<String>(body.toString(), headers);

            // When
            JoinResponseDto response = restTemplate.postForObject(url, request, JoinResponseDto.class);

            // Then
            assertNotNull(response.getUserId());
        }

        @Test
        @DisplayName("회원가입 실패 테스트")
        public void signup_failure() throws JSONException {

            // Given
            String url = "http://localhost:" + port + "/auth/join";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            JSONObject body = new JSONObject();
            body.put("email", "ronaldo@gmail.com");
            body.put("nickname", "CR7");
            body.put("password", "1234Asdf!@");

            HttpEntity<String> request = new HttpEntity<String>(body.toString(), headers);

            // When
            ErrorResponseDto response = restTemplate.postForObject(url, request, ErrorResponseDto.class);

            // Then
            assertEquals(response.getCode(), "C002");
            assertEquals(response.getMessage(), "Invalid Input Value");
        }
    }
}