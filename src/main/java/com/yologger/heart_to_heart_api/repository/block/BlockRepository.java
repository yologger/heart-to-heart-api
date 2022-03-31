package com.yologger.heart_to_heart_api.repository.block;

import com.yologger.heart_to_heart_api.repository.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BlockRepository extends JpaRepository<BlockEntity, Long> {
    @Modifying
    @Query("delete from BlockEntity b where b.member = :member and b.blocking = :target")
    void deleteByMemberAndTarget(@Param("member") MemberEntity member, @Param("target") MemberEntity target);
}
