package com.yologger.heart_to_heart_api.common.util;

import io.jsonwebtoken.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
@DisplayName("AccessTokenProvider 테스트")
class AccessTokenProviderTest {

    @InjectMocks
    AccessTokenProvider accessTokenProvider;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(accessTokenProvider, "secret", "dummy_access_key");
        ReflectionTestUtils.setField(accessTokenProvider, "expireTimeInSeconds", 1440);
    }

    @Test
    @DisplayName("AccessToken 발행 성공, 검증 성공 테스트")
    public void test_issue_success_validate_success() {
        String dummyEmail = "john@gmail.com";
        String dummyPassword = "1234";
        Authentication dummyAuth = new UsernamePasswordAuthenticationToken(dummyEmail, dummyPassword);
        String accessToken = accessTokenProvider.createToken(dummyAuth);

        assertThatNoException().isThrownBy(() -> {
            accessTokenProvider.validateToken(accessToken);
        });
    }

    @Test
    @DisplayName("AccessToken 발행 성공, 검증 실패 테스트")
    public void test_issue_success_validate_failure() {
        String dummyEmail = "john@gmail.com";
        String dummyPassword = "1234";
        Authentication dummyAuth = new UsernamePasswordAuthenticationToken(dummyEmail, dummyPassword);
        String accessToken = accessTokenProvider.createToken(dummyAuth);

        assertThatThrownBy(() -> accessTokenProvider.validateToken(accessToken + "dummy"))
                .isInstanceOf(SignatureException.class);
    }
}