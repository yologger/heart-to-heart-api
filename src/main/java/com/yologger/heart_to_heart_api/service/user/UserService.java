package com.yologger.heart_to_heart_api.service.user;

import com.yologger.heart_to_heart_api.common.exception.business.UserAlreadyExistException;
import com.yologger.heart_to_heart_api.common.util.MailUtil;
import com.yologger.heart_to_heart_api.repository.email_auth.EmailAuthCodeEntity;
import com.yologger.heart_to_heart_api.repository.email_auth.EmailAuthCodeRepository;
import com.yologger.heart_to_heart_api.repository.user.UserEntity;
import com.yologger.heart_to_heart_api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final MailUtil mailUtil;
    private final UserRepository userRepository;
    private final EmailAuthCodeRepository emailAuthCodeRepository;

    @Transactional
    public ResponseEntity<SendEmailVerificationResponseDto> requestEmailVerification(String email) throws UserAlreadyExistException {

        if (mailAlreadyExist(email)) {
            throw new UserAlreadyExistException("User Already Exists.");
        } else {
            // Generate email authentication code.
            String emailAuthCode = generateEmailAuthCode();

            // Save email authentication code.
            Optional<EmailAuthCodeEntity> result = emailAuthCodeRepository.findByEmail(email);
            if (result.isPresent()) {
                result.get().updateCode(emailAuthCode);
            } else {
                EmailAuthCodeEntity authCodeEntity = EmailAuthCodeEntity.builder()
                        .code(emailAuthCode)
                        .email(email)
                        .build();
                emailAuthCodeRepository.save(authCodeEntity);
            }

            // Send email asynchronously.
            String title = "Email 인증 요청";

            StringBuilder content = new StringBuilder();
            content.append("<div align='center' style='border:1px solid; padding: 50px; black; font-family:verdana';>");
            content.append("<div>Heart to Heart 에서 이메일 인증을 요청합니다</div>");
            content.append("<h3>회원가입 코드</h3>");
            content.append("<h1>" + emailAuthCode +"</h1>");
            content.append("</div>");

            String to = "yologger1013@gmail.com";
            mailUtil.sendEmail(title, content.toString(), to);

            SendEmailVerificationResponseDto response = SendEmailVerificationResponseDto.builder()
                    .message("Email sent.")
                    .build();

            return ResponseEntity.ok().body(response);
        }
    }

    private boolean mailAlreadyExist(String email) {
        Optional<UserEntity> result = userRepository.findByEmail(email);
        return result.isPresent();
    }

    private String generateEmailAuthCode() {
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
}
