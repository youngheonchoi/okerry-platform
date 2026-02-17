package com.okerry.backend.service;

import java.util.Map;

public interface AuthService {

    public void sendEmailAuthCode(Map<String, String> okMap) throws Exception;

    public String selectEmailAuthCode(Map<String, String> okMap) throws Exception;

    public void updateEmailAuthStatus(Map<String, String> okMap) throws Exception;
}
