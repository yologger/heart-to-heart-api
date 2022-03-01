package com.yologger.heart_to_heart_api.repository.verification_code;

import com.yologger.heart_to_heart_api.repository.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "verification_code")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCodeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String verificationCode;

    public void updateVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
