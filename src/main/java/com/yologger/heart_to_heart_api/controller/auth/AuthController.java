package com.yologger.heart_to_heart_api.controller.auth;

import com.yologger.heart_to_heart_api.service.auth.AuthService;
import com.yologger.heart_to_heart_api.service.auth.exception.*;
import com.yologger.heart_to_heart_api.service.auth.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthController {

    private final AuthService authService;

    @PostMapping
    @RequestMapping(value = "/emailVerificationCode", consumes = "application/json", produces = "application/json")
    ResponseEntity<EmailVerificationCodeResponseDto> emailVerificationCode(@Valid @RequestBody EmailVerificationCodeRequestDto request) throws MemberAlreadyExistException {
        return authService.emailVerificationCode(request.getEmail());
    }

    @PostMapping
    @RequestMapping(value = "/confirmVerificationCode", consumes = "application/json", produces = "application/json")
    ResponseEntity<ConfirmVerificationCodeResponseDto> confirmVerificationCode(@Valid @RequestBody ConfirmVerificationCodeRequestDto request) throws InvalidVerificationCodeException, InvalidEmailException, ExpiredVerificationCodeException {
        return authService.confirmVerificationCode(request);
    }

    @PostMapping
    @RequestMapping(value = "/join", consumes = "application/json", produces = "application/json")
    public ResponseEntity<JoinResponseDto> join(@Valid @RequestBody JoinRequestDto request) throws MemberAlreadyExistException {
        return authService.join(request);
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request) throws InvalidPasswordException, MemberDoesNotExistException {
        return authService.login(request);
    }

    @PostMapping(value = "/reissueToken", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ReissueTokenResponseDto> reissueToken(@Valid @RequestBody ReissueTokenRequestDto request) throws ExpiredRefreshTokenException, InvalidRefreshTokenException, MemberDoesNotExistException {
        return authService.reissueToken(request);
    }

    @PostMapping(value = "/logout")
    public  ResponseEntity<LogoutResponseDto> logout(@Valid @RequestHeader(value="Authorization", required = true) String authHeader) throws InvalidAccessTokenException, ExpiredAccessTokenException, BearerNotIncludedException {
        return authService.logout(authHeader);
    }
}
