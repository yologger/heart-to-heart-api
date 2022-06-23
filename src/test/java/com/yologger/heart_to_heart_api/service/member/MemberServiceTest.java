package com.yologger.heart_to_heart_api.service.member;

import com.yologger.heart_to_heart_api.config.TestAwsS3Config;
import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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

}