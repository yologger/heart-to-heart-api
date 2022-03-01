package com.yologger.heart_to_heart_api.repository.email_auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailAuthCodeRepository extends JpaRepository<EmailAuthCodeEntity, Long> {

    Optional<EmailAuthCodeEntity> findByEmail(String email);
}
