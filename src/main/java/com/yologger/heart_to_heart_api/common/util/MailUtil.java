package com.yologger.heart_to_heart_api.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
@EnableAsync
public class MailUtil {

    private final JavaMailSender mailSender;

    @Async
    public void sendEmail(String subject, String content, String to) throws MessagingException, MailAuthenticationException, MailSendException, MailException {
        MimeMessage mail = mailSender.createMimeMessage();
        mail.setSubject(subject, "utf-8");
        mail.setText(content.toString(), "utf-8", "html");
        mail.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        mailSender.send(mail);
    }
}
