package org.example.qtsv.service;

import org.example.qtsv.entity.UserEntity;
import org.example.qtsv.response.login.UserLoginResponse;

import java.util.Date;

public interface UserService {
    UserEntity getUserByName(String username);

    String validateLogin(UserEntity user);

    String validateUser(UserEntity user);

    String generateToken(String username);
    int extractExpiration(String token);

    UserLoginResponse getLoginInfo(String token);


    UserEntity extractUser(String token);

    void save(UserEntity user);



    void logout(UserEntity user);



}
