package com.okerry.backend.controller;

import com.okerry.backend.service.AuthService;
import com.okerry.backend.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthServiceImpl authServiceImpl;

    @PostMapping("/send-email")
    public ResponseEntity<Void> sendCode(@RequestBody Map<String, String> okMap) throws Exception {

        String email = okMap.get("email");

        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        authServiceImpl.sendEmailAuthCode(okMap);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/code-check")
    public ResponseEntity<Void> codeCheck(@RequestBody Map<String, String> okMap) throws Exception {
        String email = okMap.get("email");
        String curCode = okMap.get("code");

        if (curCode == null || curCode.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        // 인증번호 확인
        String code = authServiceImpl.selectEmailAuthCode(okMap);

        if(curCode.equals(code)){
            // 인증여부 갱신
            authServiceImpl.updateEmailAuthStatus(okMap);
        }else{
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
