package com.yologger.heart_to_heart_api.service.auth;

import com.yologger.heart_to_heart_api.common.util.AccessTokenProvider;
import com.yologger.heart_to_heart_api.common.util.MailUtil;
import com.yologger.heart_to_heart_api.common.util.RefreshTokenProvider;
import com.yologger.heart_to_heart_api.controller.auth.exception.*;
import com.yologger.heart_to_heart_api.repository.member.AuthorityType;
import com.yologger.heart_to_heart_api.repository.member.MemberEntity;
import com.yologger.heart_to_heart_api.repository.member.MemberRepository;
import com.yologger.heart_to_heart_api.repository.verification_code.VerificationCodeEntity;
import com.yologger.heart_to_heart_api.repository.verification_code.VerificationCodeRepository;
import com.yologger.heart_to_heart_api.service.auth.model.*;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AccessTokenProvider accessTokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;
    private final MailUtil mailUtil;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    private final VerificationCodeRepository verificationCodeRepository;
    private final AuthenticationManager authenticationManager;

    /**
     * Email verification code.
     * @throws MemberAlreadyExistException - In case member already exists. (AUTH_000)
     * @throws MessagingException - In case an error has occurred in gmail system. (AUTH_001)
     * @throws MailException - In case an error has occurred in gmail system. (AUTH_001)
     */
    @Transactional(rollbackFor = Exception.class)
    public EmailVerificationCodeResponseDTO emailVerificationCode(String email) throws MemberAlreadyExistException, MessagingException, MailAuthenticationException, MailSendException, MailException {

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

            EmailVerificationCodeResponseDTO response = EmailVerificationCodeResponseDTO.builder()
                    .message("Email sent.")
                    .build();

            return response;
        }
    }

    /**
     * Confirm verification code.
     * @throws InvalidEmailException - In case expired verification code. (AUTH_002)
     * @throws InvalidVerificationCodeException - In case invalid verification code. (AUTH_003)
     * @throws ExpiredVerificationCodeException - In case expired verification code. (AUTH_004)
     */
    @Transactional(rollbackFor = Exception.class)
    public ConfirmVerificationCodeResponseDTO confirmVerificationCode(@NotNull ConfirmVerificationCodeRequestDTO request) throws InvalidEmailException, InvalidVerificationCodeException, ExpiredVerificationCodeException {
        Optional<VerificationCodeEntity> result = verificationCodeRepository.findByEmail(request.getEmail());
        if (!result.isPresent()) {
            throw new InvalidEmailException("Invalid Email.");
        } else {
            if (Objects.equals(request.getVerificationCode(), result.get().getVerificationCode())) {
                if (result.get().getUpdatedAt().plusMinutes(5).isBefore(LocalDateTime.now())) {
                    throw new ExpiredVerificationCodeException("Expired Verification code");
                } else {
                    ConfirmVerificationCodeResponseDTO response = ConfirmVerificationCodeResponseDTO.builder()
                            .message("Valid Verification Code.")
                            .build();
                    return response;
                }
            } else {
                throw new InvalidVerificationCodeException("Invalid Verification Code.");
            }
        }
    }

    private boolean emailAlreadyExist(String email) {
        Optional<MemberEntity> result = memberRepository.findOneByEmail(email);
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

    /**
     * Join.
     * @throws MemberAlreadyExistException - In case given email already exists. (AUTH_000)
     */
    @Transactional(rollbackFor = Exception.class)
    public JoinResponseDTO join(JoinRequestDTO request) throws MemberAlreadyExistException {

        // Check If User already exists.
        Optional<MemberEntity> result = memberRepository.findOneByEmail(request.getEmail());
        if (result.isPresent()) throw new MemberAlreadyExistException("Member Already Exists.");

        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        MemberEntity newMember = MemberEntity.builder()
                .email(request.getEmail())
                .password(encryptedPassword)
                .nickname(request.getNickname())
                .authority(AuthorityType.USER)
                .name(request.getName())
                .build();

        MemberEntity created = memberRepository.save(newMember);

        JoinResponseDTO response = JoinResponseDTO.builder()
                .memberId(created.getId())
                .build();

        return response;
    }

    /**
     * Log in.
     * @throws MemberNotExistException - In case member already exists. (AUTH_005)
     * @throws InvalidPasswordException - In case of invalid email. (AUTH_006)
     */
    @Transactional(rollbackFor = Exception.class)
    public LoginResponseDTO login(LoginRequestDTO request) throws MemberNotExistException, BadCredentialsException {

        MemberEntity member = memberRepository.findOneByEmail(request.getEmail())
                .orElseThrow(() -> new MemberNotExistException("Member does not exist."));

        // 인증 수행
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        // 인증정보 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 토큰 생성
        String accessToken = accessTokenProvider.createToken(authentication);
        String refreshToken = refreshTokenProvider.createToken(authentication);

        // DB 업데이트
        member.setAccessToken(accessToken);
        member.setRefreshToken(refreshToken);

        LoginResponseDTO response = LoginResponseDTO.builder()
                .memberId(member.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .name(member.getName())
                .nickname(member.getNickname())
                .build();

        return response;
    }

    /**
     * Reissue access token, refresh token.
     * @throws InvalidRefreshTokenException - In case invalid refresh token (AUTH_007)
     * @throws ExpiredRefreshTokenException - In case expired refresh token (AUTH_008)
     * @throws MemberNotExistException - In case invalid refresh token (AUTH_005)
     */
    @Transactional(rollbackFor = Exception.class)
    public ReissueTokenResponseDTO reissueToken(ReissueTokenRequestDTO request) throws ExpiredRefreshTokenException, InvalidRefreshTokenException {

        // Compare with ex-refresh token
        MemberEntity member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new InvalidRefreshTokenException("Member does not exist"));

        if (!(member.getRefreshToken().equals(request.getRefreshToken()))) {
            log.info("잘못된 REFRESH TOKEN 토큰입니다.");
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }

        try {
            // Verify refresh token
            refreshTokenProvider.validateToken(request.getRefreshToken());
            Authentication authentication = refreshTokenProvider.getAuthentication(request.getRefreshToken());
            log.info("유효한 REFRESH TOKEN 입니다. 토큰이 새로 발행됩니다.");

            // Reissue access token, refresh token
            String newAccessToken = accessTokenProvider.createToken(authentication);
            String newRefreshToken = refreshTokenProvider.createToken(authentication);

            member.setAccessToken(newAccessToken);
            member.setRefreshToken(newRefreshToken);

            return ReissueTokenResponseDTO.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .name(member.getName())
                    .nickname(member.getNickname())
                    .memberId(member.getId())
                    .email(member.getEmail())
                    .build();

        } catch (ExpiredJwtException e) {
            log.info("만료된 REFRESH TOKEN 토큰입니다.");
            throw new ExpiredRefreshTokenException("Expired refresh token");
        } catch (Exception e) {
            log.info("잘못된 REFRESH TOKEN 토큰입니다.");
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public LogoutResponseDTO logout(String authHeader) throws InvalidAccessTokenException {
        String accessToken = authHeader.substring(7);
        // accessTokenProvider.validateToken(accessToken);
        Authentication authentication = accessTokenProvider.getAuthentication(accessToken);
        User user = (User)authentication.getPrincipal();
        String email = user.getUsername();
        MemberEntity member = memberRepository.findOneByEmail(email)
                .orElseThrow(() -> new InvalidAccessTokenException("Invalid access token."));
        member.clearAccessToken();
        member.clearRefreshToken();
        log.info("로그아웃 합니다.");
        return LogoutResponseDTO.builder()
                .message("Logged out ")
                .build();
    }
}