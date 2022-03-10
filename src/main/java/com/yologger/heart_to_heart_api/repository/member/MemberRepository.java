package com.yologger.heart_to_heart_api.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // Query Method
    Optional<MemberEntity> findByEmail(String email);
}