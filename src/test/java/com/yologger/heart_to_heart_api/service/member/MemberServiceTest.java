package com.yologger.heart_to_heart_api.service.member;

import com.yologger.heart_to_heart_api.config.TestAwsS3Config;
import com.yologger.heart_to_heart_api.service.member.model.DeleteAccountResponseDTO;
import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestAwsS3Config.class)
@DisplayName("MemberService 테스트")
@Transactional
class MemberServiceTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    S3Mock s3Mock;

    @AfterEach
    public void shutdownMockS3(){
        s3Mock.stop();
    }

    @DisplayName("회원 탈퇴 테스트")
    @Sql(scripts = "classpath:sql/dummy/users.sql")
    @Nested
    public class DeleteAccountTest {
        @Test
        @DisplayName("회원 탈퇴 성공 테스트")
        public void deleteAccount_success() {
            Long targetId = 1L;
            assertThatNoException().isThrownBy(() -> {
                DeleteAccountResponseDTO response = memberService.deleteAccount(targetId);
                assertThat(response.getMessage()).isEqualTo("deleted.");
            });
        }
    }
}