package org.example.qtsv.service;

import org.example.qtsv.entity.UserEntity;
import org.example.qtsv.response.login.UserLoginResponse;

import java.util.Date;

public interface UserService {

    String validateLogin(UserEntity user);

    String generateToken(String username);

    int extractExpiration(String token);

    UserLoginResponse getLoginInfo(String token);
    void save(UserEntity user);
}
