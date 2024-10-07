package org.example.qtsv.service.Impl;

import jakarta.transaction.Transactional;
import org.example.qtsv.entity.JwtBlacklist;
import org.example.qtsv.entity.UserEntity;
import org.example.qtsv.repository.JwtBlacklistRepository;
import org.example.qtsv.service.JwtBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class JwtBlacklistServiceImpl implements JwtBlacklistService {

    @Autowired
    private JwtBlacklistRepository repo;

    public void save(JwtBlacklist jwtBlacklist) {
        repo.save(jwtBlacklist);
    }

    public JwtBlacklist findJwt(String jwtToken) {
        String jwt = jwtToken.substring(7);
        return repo.findByJwt(jwt);
    }

    public boolean isTokenBlacklisted(String jwtToken) {
        String token = jwtToken.substring(7);
        if (repo.findByJwt(token) != null){
            return true;
        } else {
            return false;
        }
    }

    public void addJwtToBlackList(String jwtToken){
        String jwt = jwtToken.substring(7);
        JwtBlacklist a = new JwtBlacklist();
        a.setJwt(jwt);
        save(a);
    }



}
