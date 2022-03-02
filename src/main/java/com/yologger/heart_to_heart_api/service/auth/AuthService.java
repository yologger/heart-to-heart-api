package com.yologger.heart_to_heart_api.service.auth;

import com.yologger.heart_to_heart_api.service.auth.exception.ExpiredVerificationCodeException;
import com.yologger.heart_to_heart_api.service.auth.exception.InvalidEmailException;
import com.yologger.heart_to_heart_api.service.auth.exception.InvalidVerificationCodeException;
import com.yologger.heart_to_heart_api.service.auth.exception.MemberAlreadyExistException;
import com.yologger.heart_to_heart_api.common.util.MailUtil;
import com.yologger.heart_to_heart_api.repository.member.MemberEntity;
import com.yologger.heart_to_heart_api.repository.member.MemberRepository;
import com.yologger.heart_to_heart_api.repository.verification_code.VerificationCodeEntity;
import com.yologger.heart_to_heart_api.repository.verification_code.VerificationCodeRepository;
import com.yologger.heart_to_heart_api.service.auth.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final MailUtil mailUtil;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final VerificationCodeRepository verificationCodeRepository;

    @Transactional
    public ResponseEntity<EmailVerificationCodeResponseDto> emailVerificationCode(String email) throws MemberAlreadyExistException {

        if (emailAlreadyExist(email)) {
            throw new MemberAlreadyExistException("Member Already Exists.");
        } else {
            // Generate email authentication code.
            String verificationCode = generateVerificationCode();

            // Save email authentication code.
            Optional<VerificationCodeEntity> result = verificationCodeRepository.findByEmail(email);
            if (result.isPresent()) {
                result.get().updateVerificationCode(verificationCode);
            } else {
                VerificationCodeEntity authCodeEntity = VerificationCodeEntity.builder()
                        .verificationCode(verificationCode)
                        .email(email)
                        .build();
                verificationCodeRepository.save(authCodeEntity);
            }

            // Send email asynchronously.
            String title = "Email 인증 요청";

            StringBuilder content = new StringBuilder();
            content.append("<div align='center' style='border:1px solid; padding: 50px; black; font-family:verdana';>");
            content.append("<div>Heart to Heart 에서 이메일 인증을 요청합니다</div>");
            content.append("<h3>회원가입 코드</h3>");
            content.append("<h1>" + verificationCode +"</h1>");
            content.append("</div>");

            String to = "yologger1013@gmail.com";
            mailUtil.sendEmail(title, content.toString(), to);

            EmailVerificationCodeResponseDto response = EmailVerificationCodeResponseDto.builder()
                    .message("Email sent.")
                    .build();

            return ResponseEntity.ok().body(response);
        }
    }

    public ResponseEntity<ConfirmVerificationCodeResponseDto> confirmVerificationCode(ConfirmVerificationCodeRequestDto request) throws InvalidEmailException, InvalidVerificationCodeException, ExpiredVerificationCodeException {
        Optional<VerificationCodeEntity> result = verificationCodeRepository.findByEmail(request.getEmail());
        if (!result.isPresent()) {
            throw new InvalidEmailException("Invalid Email.");
        } else {
            if (Objects.equals(request.getVerificationCode(), result.get().getVerificationCode())) {
                if (result.get().getUpdatedAt().plusMinutes(5).isBefore(LocalDateTime.now())) {
                    throw new ExpiredVerificationCodeException("Expired Verification code");
                } else {
                    ConfirmVerificationCodeResponseDto response = ConfirmVerificationCodeResponseDto.builder()
                            .message("Valid Verification Code.")
                            .build();
                    return ResponseEntity.ok().body(response);
                }
            } else {
                throw new InvalidVerificationCodeException("Invalid Verification Code.");
            }
        }
    }

    private boolean emailAlreadyExist(String email) {
        Optional<MemberEntity> result = memberRepository.findByEmail(email);
        return result.isPresent();
    }

    private String generateVerificationCode() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
    }

    @Transactional
    public ResponseEntity<JoinResponseDto> join(JoinRequestDto request) throws MemberAlreadyExistException {

        // Check If User already exists.
        Optional<MemberEntity> result = memberRepository.findByEmail(request.getEmail());
        if (result.isPresent()) throw new MemberAlreadyExistException("Member Already Exists.");

        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        MemberEntity newMember = MemberEntity.builder()
                .email(request.getEmail())
                .password(encryptedPassword)
                .nickname(request.getNickname())
                .name(request.getName())
                .build();

        MemberEntity created = memberRepository.save(newMember);

        JoinResponseDto response = JoinResponseDto.builder()
                .userId(created.getId())
                .build();

        return ResponseEntity.created(null).body(response);
    }

//    public ResponseEntity<LoginResponseDto> login(LoginRequestDto request) {
//        // Todo("wqewqe")
//    }
}