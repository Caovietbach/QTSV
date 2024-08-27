package org.example.qtsv.service.Impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;
import netscape.javascript.JSObject;
import org.example.qtsv.entity.Student;
import org.example.qtsv.entity.UserEntity;
import org.example.qtsv.exception.ValidateException;
import org.example.qtsv.repository.UserRepository;
import org.example.qtsv.response.login.UserLoginResponse;
import org.example.qtsv.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;




@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    private final String SECRET_KEY = "secretasafnanfhfihasshfhasifiaifhiashfiaihaifsihfiasifhaijisahfiash";

    public void save(UserEntity user) {
        repo.save(user);
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 8))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public int extractExpiration(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        long currentTimeMillis = System.currentTimeMillis();
        long expirationTimeMillis = expiration.getTime();
        int exp = (int) ((expirationTimeMillis - currentTimeMillis) / 1000);

        return exp;
    }

    public UserLoginResponse getLoginInfo(String token){
        UserLoginResponse res = new UserLoginResponse();
        res.setAccessToken(token);
        res.setTokenType("Bearer");
        res.setExpiresIn(extractExpiration(token));
        return res;
    }

    @Override
    public String validateLogin(UserEntity user) {
        UserEntity userCheck = repo.findByuserName(user.getUserName());

        if (user.getUserName() == null) {
            throw new ValidateException("Please input user name");}
        else if (user.getPassword() == null){
            throw new ValidateException("Please input password");}
        else if (userCheck == null){
            throw new ValidateException("No user have this user name");
        } else if (userCheck.getPassword() == user.getPassword() ){
            throw new ValidateException("Incorrect password");
        } else if (userCheck.getStatus() != 1) {
            throw new ValidateException("User is not active");
        } else {
            return "Login successful";
        }
    }

}
