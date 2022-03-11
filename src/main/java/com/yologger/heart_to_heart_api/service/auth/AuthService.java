package com.yologger.heart_to_heart_api.service.auth;

import com.yologger.heart_to_heart_api.common.util.JwtUtil;
import com.yologger.heart_to_heart_api.common.util.MailUtil;
import com.yologger.heart_to_heart_api.repository.member.MemberEntity;
import com.yologger.heart_to_heart_api.repository.member.MemberRepository;
import com.yologger.heart_to_heart_api.repository.verification_code.VerificationCodeEntity;
import com.yologger.heart_to_heart_api.repository.verification_code.VerificationCodeRepository;
import com.yologger.heart_to_heart_api.service.auth.exception.*;
import com.yologger.heart_to_heart_api.service.auth.model.*;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final MailUtil mailUtil;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final AuthenticationManager authenticationManager;

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

            mailUtil.sendEmail(title, content.toString(), email);

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

    @Transactional
    public ResponseEntity<LoginResponseDto> login(LoginRequestDto request) throws MemberDoesNotExistException, InvalidPasswordException {

        // Check if member exists.
        MemberEntity member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new MemberDoesNotExistException("Member does not exist."));

        // Check if password correct.
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new InvalidPasswordException("Invalid password exception");
        }

        // 인증 수행
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        // Principal(본인)
        User me = (User) auth.getPrincipal();

        // 토큰 생성
        String accessToken = jwtUtil.generateAccessToken(member.getId(), member.getEmail(), member.getName(), member.getNickname());
        String refreshToken = jwtUtil.generateRefreshToken(member.getId(), member.getEmail(), member.getName(), member.getNickname());

        // DB 업데이트
        member.setAccessToken(accessToken);
        member.setRefreshToken(refreshToken);

        LoginResponseDto response = LoginResponseDto.builder()
                .userId(member.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .name(member.getName())
                .nickname(member.getNickname())
                .build();

        return ResponseEntity.ok(response);
    }

    @Transactional
    public ResponseEntity<ReissueTokenResponseDto> reissueToken(ReissueTokenRequestDto request) throws ExpiredRefreshTokenException, InvalidRefreshTokenException, MemberDoesNotExistException {
        // Compare with ex-refresh token
        Optional<MemberEntity> result = memberRepository.findById(request.getId());
        if (!result.isPresent()) {
            throw new MemberDoesNotExistException("Member does not exist");
        }
        if (!(result.get().getRefreshToken().equals(request.getRefreshToken()))) {
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }

        try {
            // Verify refresh token
            jwtUtil.verifyRefreshToken(request.getRefreshToken());

            // Reissue access token, refresh token
            MemberEntity member = result.get();
            String newAccessToken = member.getAccessToken();
            String newRefreshToken = member.getRefreshToken();
            member.setAccessToken(newAccessToken);
            member.setRefreshToken(newRefreshToken);

            ReissueTokenResponseDto response = ReissueTokenResponseDto.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .name(member.getName())
                    .nickname(member.getNickname())
                    .userId(member.getId())
                    .build();

            return ResponseEntity.ok(response);

        } catch (ExpiredJwtException e) {
            throw new ExpiredRefreshTokenException("Expired refresh token");
        } catch (Exception e) {
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }
    }

    @Transactional
    public ResponseEntity<LogoutResponseDto> logout(String authHeader) throws BearerNotIncludedException, ExpiredAccessTokenException, InvalidAccessTokenException {
        if (!authHeader.startsWith("Bearer")) {
            throw new BearerNotIncludedException("Authorization does not include 'bearer'.");
        } else {
            try {
                String accessToken = authHeader.substring(7);
                Long memberId = jwtUtil.verifyAccessTokenAndGetMemberId(accessToken);
                Optional<MemberEntity> result = memberRepository.findById(memberId);
                if (!result.isPresent()) {
                    throw new InvalidAccessTokenException("Invalid refresh token.");
                }
                MemberEntity member = result.get();
                member.clearAccessToken();
                member.clearRefreshToken();
                return ResponseEntity.ok(LogoutResponseDto.builder().message("Logged out ").build());
            } catch (ExpiredJwtException e) {
                throw new ExpiredAccessTokenException("Expired access token");
            } catch (Exception e) {
                throw new InvalidAccessTokenException("Invalid refresh token.");
            }
        }
    }
}