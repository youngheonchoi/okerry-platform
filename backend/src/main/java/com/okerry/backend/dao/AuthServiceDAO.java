package com.okerry.backend.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface AuthServiceDAO {
    int insertAuthCode(Map<String, String> okMap);

    String selectEmailAuthCode(Map<String, String> okMap);

    void updateEmailAuthStatus(Map<String, String> okMap);

    void insertUser(Map<String, String> okMap);

    int selectEmailCheck(Map<String, String> okMap);

    Map<String, String> selectUser(Map<String, String> okMap);

    int actionLogin(Map<String, String> okMap);
}