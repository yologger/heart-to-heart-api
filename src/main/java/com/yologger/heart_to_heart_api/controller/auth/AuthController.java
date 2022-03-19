package com.yologger.heart_to_heart_api.controller.auth;

import com.yologger.heart_to_heart_api.controller.auth.exception.*;
import com.yologger.heart_to_heart_api.service.auth.AuthService;
import com.yologger.heart_to_heart_api.service.auth.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthController {

    private final AuthService authService;

    /**
     * Email verification code.
     * @throws MemberAlreadyExistException - In case member already exists. (AUTH_000)
     * @throws MessagingException - In case an error has occurred in gmail system. (AUTH_001)
     * @throws MailException - In case an error has occurred in gmail system. (AUTH_001)
     */
    @PostMapping
    @RequestMapping(value = "/emailVerificationCode", consumes = "application/json", produces = "application/json")
    ResponseEntity<EmailVerificationCodeResponseDto> emailVerificationCode(@Valid @RequestBody EmailVerificationCodeRequestDto request) throws MemberAlreadyExistException, MessagingException, MailException {
        return authService.emailVerificationCode(request.getEmail());
    }

    /**
     * Confirm verification code.
     * @throws InvalidEmailException - In case expired verification code. (AUTH_002)
     * @throws InvalidVerificationCodeException - In case invalid verification code. (AUTH_003)
     * @throws ExpiredVerificationCodeException - In case expired verification code. (AUTH_004)
     */
    @PostMapping
    @RequestMapping(value = "/confirmVerificationCode", consumes = "application/json", produces = "application/json")
    ResponseEntity<ConfirmVerificationCodeResponseDto> confirmVerificationCode(@Valid @RequestBody ConfirmVerificationCodeRequestDto request) throws InvalidVerificationCodeException, InvalidEmailException, ExpiredVerificationCodeException {
        return authService.confirmVerificationCode(request);
    }

    /**
     * Join.
     * @throws MemberAlreadyExistException - In case given email already exists. (AUTH_000)
     */
    @PostMapping
    @RequestMapping(value = "/join", consumes = "application/json", produces = "application/json")
    public ResponseEntity<JoinResponseDto> join(@Valid @RequestBody JoinRequestDto request) throws MemberAlreadyExistException {
        return authService.join(request);
    }

    /**
     * Log in.
     * @throws MemberNotExistException - In case member already exists. (AUTH_005)
     * @throws InvalidPasswordException - In case of invalid email. (AUTH_006)
     */
    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request) throws MemberNotExistException, InvalidPasswordException {
        return authService.login(request);
    }

    @GetMapping(value = "/verifyAccessToken")
    public ResponseEntity<VerifyAccessTokenResponse> verifyAccessToken() {
        VerifyAccessTokenResponse response = VerifyAccessTokenResponse.builder()
                .message("verified")
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * Reissue access token, refresh token.
     * @throws InvalidRefreshTokenException - In case invalid refresh token (AUTH_007)
     * @throws ExpiredRefreshTokenException - In case expired refresh token (AUTH_008)
     * @throws MemberNotExistException - In case invalid refresh token (AUTH_005)
     */
    @PostMapping(value = "/reissueToken", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ReissueTokenResponseDto> reissueToken(@Valid @RequestBody ReissueTokenRequestDto request) throws InvalidRefreshTokenException, ExpiredRefreshTokenException, MemberNotExistException {
        log.info("Reissuing token succeeds");
        return authService.reissueToken(request);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<LogoutResponseDto> logout(@Valid @RequestHeader(value = "Authorization", required = true) String authHeader) throws InvalidAccessTokenException, ExpiredAccessTokenException, BearerNotIncludedException {
        return authService.logout(authHeader);
    }
}
