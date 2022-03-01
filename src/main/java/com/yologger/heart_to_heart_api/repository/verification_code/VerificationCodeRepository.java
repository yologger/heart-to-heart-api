package com.yologger.heart_to_heart_api.repository.verification_code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCodeEntity, Long> {
    Optional<VerificationCodeEntity> findByEmail(String email);
}
