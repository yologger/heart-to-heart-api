package com.yologger.heart_to_heart_api.controller.user;

import com.yologger.heart_to_heart_api.common.exception.business.UserAlreadyExistException;
import com.yologger.heart_to_heart_api.service.user.SendEmailVerificationRequestDto;
import com.yologger.heart_to_heart_api.service.user.SendEmailVerificationResponseDto;
import com.yologger.heart_to_heart_api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @RequestMapping(value = "/mail", consumes = "application/json", produces = "application/json")
    ResponseEntity<SendEmailVerificationResponseDto> sendEmailVerification(@Valid @RequestBody SendEmailVerificationRequestDto request) throws UserAlreadyExistException {
        return userService.requestEmailVerification(request.getEmail());
    }
}
