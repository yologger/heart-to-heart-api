package com.yologger.heart_to_heart_api.controller.auth;

import com.yologger.heart_to_heart_api.controller.auth.exception.*;
import com.yologger.heart_to_heart_api.service.auth.AuthService;
import com.yologger.heart_to_heart_api.service.auth.model.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Api(tags = "인증 관련 엔드포인트")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Email verification code.
     * @throws MemberAlreadyExistException - In case member already exists. (AUTH_000)
     * @throws MessagingException - In case an error has occurred in gmail system. (AUTH_001)
     * @throws MailException - In case an error has occurred in gmail system. (AUTH_001)
     */
    @PostMapping(value = "/emailVerificationCode", consumes = "application/json", produces = "application/json")
    ResponseEntity<EmailVerificationCodeResponseDTO> emailVerificationCode(@Valid @RequestBody EmailVerificationCodeRequestDTO request) throws MemberAlreadyExistException, MessagingException, MailException {
        return new ResponseEntity<>(authService.emailVerificationCode(request.getEmail()), HttpStatus.ACCEPTED);
    }

    /**
     * Confirm verification code.
     * @throws InvalidEmailException - In case expired verification code. (AUTH_002)
     * @throws InvalidVerificationCodeException - In case invalid verification code. (AUTH_003)
     * @throws ExpiredVerificationCodeException - In case expired verification code. (AUTH_004)
     */
    @PostMapping(value = "/confirmVerificationCode", consumes = "application/json", produces = "application/json")
    ResponseEntity<ConfirmVerificationCodeResponseDTO> confirmVerificationCode(@Valid @RequestBody ConfirmVerificationCodeRequestDTO request) throws InvalidVerificationCodeException, InvalidEmailException, ExpiredVerificationCodeException {
        return new ResponseEntity<>(authService.confirmVerificationCode(request), HttpStatus.OK);
    }

    /**
     * Join.
     * @throws MemberAlreadyExistException - In case given email already exists. (AUTH_000)
     */
    @ApiOperation(value = "회원가입")
    @ApiResponses({
            @ApiResponse(code = 201, message = "회원 가입 실패"),
            @ApiResponse(code = 400, message = "중복된 이메일")
    })
    @PostMapping(value = "/join", consumes = "application/json", produces = "application/json")
    public ResponseEntity<JoinResponseDTO> join(@Valid @RequestBody JoinRequestDTO request) throws MemberAlreadyExistException {
        return new ResponseEntity<>(authService.join(request), HttpStatus.CREATED);
    }

    /**
     * Log in.
     * @throws MemberNotExistException - In case member already exists. (AUTH_005)
     * @throws InvalidPasswordException - In case of invalid email. (AUTH_006)
     */
    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) throws MemberNotExistException, BadCredentialsException {
        return new ResponseEntity<>(authService.login(request), HttpStatus.OK);
    }

    @GetMapping(value = "/verifyAccessToken")
    public ResponseEntity<VerifyAccessTokenResponseDTO> verifyAccessToken() {
        VerifyAccessTokenResponseDTO response = VerifyAccessTokenResponseDTO.builder()
                .message("verified")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Reissue access token, refresh token.
     * @throws InvalidRefreshTokenException - In case invalid refresh token (AUTH_007)
     * @throws ExpiredRefreshTokenException - In case expired refresh token (AUTH_008)
     * @throws MemberNotExistException - In case invalid refresh token (AUTH_005)
     */
    @PostMapping(value = "/reissueToken", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ReissueTokenResponseDTO> reissueToken(@Valid @RequestBody ReissueTokenRequestDTO request) throws InvalidRefreshTokenException, ExpiredRefreshTokenException, MemberNotExistException {
        return new ResponseEntity<>(authService.reissueToken(request), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping(value = "/logout")
    public ResponseEntity<LogoutResponseDTO> logout(@Valid @RequestHeader(value = "Authorization", required = true) String authHeader) throws InvalidAccessTokenException, ExpiredAccessTokenException, BearerNotIncludedException {
        return new ResponseEntity<>(authService.logout(authHeader), HttpStatus.OK);
    }
}
