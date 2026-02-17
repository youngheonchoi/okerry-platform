package com.okerry.backend.service.impl;

import com.okerry.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AuthServiceImpl implements AuthService {
    private final JavaMailSender mailSender;

    public AuthServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmailAuthCode(Map<String, String> okMap) throws Exception {
        int number = ThreadLocalRandom.current().nextInt(100000, 1000000);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(okMap.get("email"));
        message.setSubject("okerry platform 인증 메일");
        message.setText(String.valueOf(number));

        mailSender.send(message);
    }
}
