package com.yologger.heart_to_heart_api.common.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

@DisplayName("MailUtil 테스트")
class MailUtilTest {

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    @DisplayName("이메일 인증코드 테스트")
    void generateCode() {
        boolean isMarried = true;
        assertThat(isMarried).isTrue();

        boolean hasRole = false;
        assertThat(hasRole).isFalse();
    }
}