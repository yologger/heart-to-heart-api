package com.yologger.heart_to_heart_api.controller.auth;

import com.yologger.heart_to_heart_api.common.exception.business.UserAlreadyExistException;
import com.yologger.heart_to_heart_api.service.auth.AuthService;
import com.yologger.heart_to_heart_api.service.auth.JoinRequestDto;
import com.yologger.heart_to_heart_api.service.auth.JoinResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/join", consumes = "application/json", produces = "application/json")
    public ResponseEntity<JoinResponseDto> join(@Valid @RequestBody JoinRequestDto request) throws UserAlreadyExistException { return authService.join(request); }
}
