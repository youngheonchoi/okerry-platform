package com.okerry.backend.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
public interface AuthServiceDAO {
    int insertAuthCode(Map<String, String> okMap);

    String selectEmailAuthCode(Map<String, String> okMap);

    void updateEmailAuthStatus(Map<String, String> okMap);
}