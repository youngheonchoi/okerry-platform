package com.okerry.backend.service.impl;

import com.okerry.backend.dao.AuthServiceDAO;
import com.okerry.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthServiceDAO authServiceDAO;

    private final JavaMailSender mailSender;

    public AuthServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmailAuthCode(Map<String, String> okMap) throws Exception {
        int code = ThreadLocalRandom.current().nextInt(100000, 1000000);
        okMap.put("code", String.valueOf(code));
        int cnt = authServiceDAO.insertAuthCode(okMap);

        if(cnt > 0){

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(okMap.get("email"));
            message.setSubject("okerry platform 인증 메일");
            message.setText(String.valueOf(code));

            mailSender.send(message);
        }

    }
}
