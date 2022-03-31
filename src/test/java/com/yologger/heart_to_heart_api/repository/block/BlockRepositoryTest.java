package com.yologger.heart_to_heart_api.repository.block;

import com.yologger.heart_to_heart_api.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayName("BlockRepository 테스트")
class BlockRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BlockRepository blockRepository;
}