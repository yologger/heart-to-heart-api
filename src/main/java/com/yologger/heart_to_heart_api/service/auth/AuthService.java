package com.yologger.heart_to_heart_api.service.auth;

import com.yologger.heart_to_heart_api.common.exception.business.UserAlreadyExistException;
import com.yologger.heart_to_heart_api.repository.user.UserEntity;
import com.yologger.heart_to_heart_api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<JoinResponseDto> join(JoinRequestDto request) throws UserAlreadyExistException {

        // Check If User already exists.
        Optional<UserEntity> result = userRepository.findByEmail(request.getEmail());
        if (result.isPresent()) throw new UserAlreadyExistException("User Already Exists.");

        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        UserEntity newUser = UserEntity.builder()
                .email(request.getEmail())
                .password(encryptedPassword)
                .nickname(request.getNickname())
                .name(request.getName())
                .build();

        UserEntity created = userRepository.save(newUser);

        JoinResponseDto response = JoinResponseDto.builder()
                .userId(created.getId())
                .build();

        return ResponseEntity.created(null).body(response);
    }
}