package com.yologger.heart_to_heart_api.service.member;

import com.yologger.heart_to_heart_api.config.TestAwsS3Config;
import com.yologger.heart_to_heart_api.controller.member.exception.InvalidMemberIdException;
import com.yologger.heart_to_heart_api.service.member.model.*;
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
    public void shutdownMockS3() {
        s3Mock.stop();
    }

    @DisplayName("사용자 정보 조회 테스트")
    @Nested
    public class getMemberInfoTest {
        @Test
        @DisplayName("사용자 정보 조회 실패 - 사용자가 존재하지 않을 때")
        public void getMemberInfo_failure_whenUserNotExist() {
            Long targetId = 1L;
            assertThatThrownBy(() -> {
                memberService.getMemberInfo(targetId);
            }).isInstanceOf(InvalidMemberIdException.class);
        }

        @Test
        @DisplayName("사용자 정보 조회 성공")
        @Sql(scripts = {
                "classpath:sql/dummy/users.sql",
                "classpath:sql/dummy/posts.sql",
                "classpath:sql/dummy/post_images.sql"
        })
        public void getMemberInfo_success() {
            Long targetId = 1L;
            assertThatNoException().isThrownBy(() -> {
                GetMemberInfoResponseDTO response = memberService.getMemberInfo(targetId);
                assertThat(response.getMemberId()).isEqualTo(targetId);
                assertThat(response.getPostSize()).isEqualTo(2);
            });
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

    @DisplayName("사용자 차단 테스트")
    @Nested
    public class BlockTest {
        @Test
        @DisplayName("사용자 차단 실패 테스트 - 사용자가 존재하지 않을 때")
        public void block_failure_whenUserNotExist() {
            BlockMemberRequestDTO request = BlockMemberRequestDTO.builder()
                    .memberId(1L)
                    .targetId(2L)
                    .build();

            assertThatThrownBy(() -> {
                memberService.block(request);
            }).isInstanceOf(InvalidMemberIdException.class);
        }

        @Test
        @DisplayName("사용자 차단 성공 테스트")
        @Sql(scripts = "classpath:sql/dummy/users.sql")
        public void block_success() {
            Long memberId = 1L;
            Long targetId = 2L;

            BlockMemberRequestDTO request = BlockMemberRequestDTO.builder()
                    .memberId(memberId)
                    .targetId(targetId)
                    .build();

            assertThatNoException().isThrownBy(() -> {
                BlockMemberResponseDTO response = memberService.block(request);
                assertThat(response.getMemberId()).isEqualTo(memberId);
                assertThat(response.getTargetId()).isEqualTo(targetId);
            });
        }
    }

    @Nested
    @DisplayName("사용자 차단 해제 테스트")
    class UnblockTest {
        @Test
        @DisplayName("사용자 차단 해제 실패 테스트 - 사용자 ID가 존재하지 않을 때")
        public void unblock_failure_whenUserNotExist() {
            Long memberId = 1L;
            Long targetId = 2L;

            UnblockMemberRequestDTO request = UnblockMemberRequestDTO.builder()
                    .memberId(memberId)
                    .targetId(targetId)
                    .build();

            assertThatThrownBy(() -> {
                memberService.unblock(request);
            }).isInstanceOf(InvalidMemberIdException.class);
        }

        @Test
        @DisplayName("사용자 차단 해제 성공 테스트")
        @Sql(scripts = "classpath:sql/dummy/users.sql")
        public void unblock_success() {
            Long memberId = 1L;
            Long targetId = 2L;

            UnblockMemberRequestDTO request = UnblockMemberRequestDTO.builder()
                    .memberId(memberId)
                    .targetId(targetId)
                    .build();

            assertThatNoException().isThrownBy(() -> {
                UnblockMemberResponseDTO response = memberService.unblock(request);
                assertThat(response.getMemberId()).isEqualTo(memberId);
                assertThat(response.getTargetId()).isEqualTo(targetId);
            });
        }
    }

    @Nested
    @DisplayName("차단 사용자 목록 조회 테스트")
    public class getBlockingMemberTest {

        @Test
        @DisplayName("차단 사용자 목록 조회 실패 테스트 - 사용자가 존재하지 않을 때")
        void getBlockingMember_failure_whenUserNotExist() {
            Long memberId = 1L;
            assertThatThrownBy(() -> {
                memberService.getBlockingMember(memberId);
            }).isInstanceOf(InvalidMemberIdException.class);
        }
    }
}