package org.example.qtsv.service;

import org.example.qtsv.entity.JwtBlacklist;

public interface JwtBlacklistService {

    void save(JwtBlacklist jwtBlacklist);

    boolean isTokenBlacklisted(String token);

    JwtBlacklist findJwt(String jwt);

    void addJwtToBlackList(String jwt);
}
