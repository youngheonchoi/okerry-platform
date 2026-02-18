package com.okerry.backend.controller;

import com.okerry.backend.service.AuthService;
import com.okerry.backend.service.impl.AuthServiceImpl;
import jakarta.servlet.http.HttpSession;
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

        // 중복 이메일 확인
        int count = authServiceImpl.selectEmailCheck(okMap);

        if(count <= 0){
            authServiceImpl.sendEmailAuthCode(okMap);
        }else{
            return ResponseEntity.badRequest().build();
        }

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

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody Map<String, String> okMap) throws Exception {
        String email = okMap.get("email");
        String password = okMap.get("password");

        // 회원가입
        authServiceImpl.insertUser(okMap);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/actionLogin")
    public ResponseEntity<?> actionLogin(@RequestBody Map<String, String> okMap, HttpSession session) throws Exception {

        Map<String, String> loginUser = authServiceImpl.actionLogin(okMap);

        if (loginUser == null) {
            return ResponseEntity.status(401).body("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        // 세션 생성/저장 (세션 attribute 이름: loginMap)
        session.setAttribute("loginMap", loginUser);

        return ResponseEntity.ok(loginUser);
    }
}
