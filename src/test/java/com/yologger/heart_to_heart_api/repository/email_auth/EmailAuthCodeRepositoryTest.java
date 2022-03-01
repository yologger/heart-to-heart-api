package com.yologger.heart_to_heart_api.repository.email_auth;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

@DataJpaTest
@DisplayName("EmailAuthCodeRepository 테스트")
class EmailAuthCodeRepositoryTest {

    @Autowired
    private EmailAuthCodeRepository repo;

    @BeforeEach
    void setup() {

    }

    @AfterEach
    void tearDown() {
        repo.deleteAll();
    }

    @Test
    @DisplayName("새로운 인증 코드 추가 테스트")
    public void test_insertNewAuthCode() {
        // Given
        String email = "ronaldo@gmail.com";
        String authCode = "aF23Zke";

        EmailAuthCodeEntity codeEntity = EmailAuthCodeEntity.builder()
                .email(email)
                .code(authCode)
                .build();

        repo.save(codeEntity);

        // When
        List<EmailAuthCodeEntity> results = repo.findAll();
        EmailAuthCodeEntity result = results.get(0);

        // Then
        assertThat(result.getCode()).isEqualTo(authCode);
        assertThat(result.getEmail()).isEqualTo(email);
    }

}