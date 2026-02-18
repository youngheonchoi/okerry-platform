package com.okerry.backend.service.impl;

import com.okerry.backend.dao.AuthServiceDAO;
import com.okerry.backend.service.AuthService;
import com.okerry.backend.util.PasswordUtil;
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

    @Override
    public String selectEmailAuthCode(Map<String, String> okMap) throws Exception {
        return authServiceDAO.selectEmailAuthCode(okMap);
    }

    @Override
    public void updateEmailAuthStatus(Map<String, String> okMap) throws Exception {
        authServiceDAO.updateEmailAuthStatus(okMap);
    }

    @Override
    public void insertUser(Map<String, String> okMap) throws Exception {
        okMap.put("password", PasswordUtil.hash(okMap.get("password")));
        authServiceDAO.insertUser(okMap);
    }

    @Override
    public int selectEmailCheck(Map<String, String> okMap) throws Exception {
        return authServiceDAO.selectEmailCheck(okMap);
    }

    @Override
    public Map<String, String> actionLogin(Map<String, String> okMap) throws Exception {
        // 1) 사용자 조회
        Map<String, String> userMap = authServiceDAO.selectUser(okMap);
        if (userMap == null) {
            return null; // 사용자 없음
        }

        // 2) 비밀번호 검증 (해시 비교)
        if (!PasswordUtil.matches(okMap.get("password"), userMap.get("password"))) {
            return null; // 비밀번호 불일치
        }

        // 3) 로그인 처리(마지막로그인 업데이트/로그 저장 등)
        authServiceDAO.actionLogin(okMap);

        // 4) 세션에 넣을 데이터 반환 (비밀번호는 제거!)
        userMap.remove("password");
        return userMap;
    }

}
