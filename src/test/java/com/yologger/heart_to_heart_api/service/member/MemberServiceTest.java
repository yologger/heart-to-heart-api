package com.yologger.heart_to_heart_api.service.member;

import com.yologger.heart_to_heart_api.config.TestAwsS3Config;
import com.yologger.heart_to_heart_api.controller.member.exception.InvalidMemberIdException;
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

import static org.assertj.core.api.Assertions.*;

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

    @DisplayName("사용자 정보 조회 테스트")
    @Nested
    public class getMemberInfoTest {
        @Test
        @DisplayName("사용자 정보 조회 실패 - 사용자가 존재하지 않을 때")
        public void getMemberInfo_success() {
            Long targetId = 1L;
            assertThatThrownBy(() -> {
                memberService.getMemberInfo(targetId);
            }).isInstanceOf(InvalidMemberIdException.class);
        }
    }

    @DisplayName("회원 탈퇴 테스트")
    @Nested
    public class DeleteAccountTest {
        @Test
        @DisplayName("회원 탈퇴 성공 테스트")
        @Sql(scripts = "classpath:sql/dummy/users.sql")
        public void deleteAccount_success() {
            Long targetId = 1L;
            assertThatNoException().isThrownBy(() -> {
                DeleteAccountResponseDTO response = memberService.deleteAccount(targetId);
                assertThat(response.getMessage()).isEqualTo("deleted.");
            });
        }

        @Test
        @DisplayName("회원 탈퇴 실패 테스트 - 잘못된 사용자 ID")
        public void deleteAccount_failure_whenMemberIdNotExist() {
            Long targetId = 1L;
            assertThatThrownBy(() -> {
                memberService.deleteAccount(targetId);
            }).isInstanceOf(InvalidMemberIdException.class);
        }
    }
}