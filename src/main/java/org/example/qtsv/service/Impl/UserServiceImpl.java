package org.example.qtsv.service.Impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import netscape.javascript.JSObject;
import org.example.qtsv.entity.LastYearStudentEntity;
import org.example.qtsv.entity.Student;
import org.example.qtsv.entity.UserEntity;
import org.example.qtsv.exception.ValidateException;
import org.example.qtsv.repository.UserRepository;
import org.example.qtsv.response.login.UserLoginResponse;
import org.example.qtsv.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.security.Key;




@Service("User")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    private final String SECRET_KEY = "secretfortheproject123456789anhemtalanguoimotnha1234567890234567435554";

    //private byte[] key = Decoders.BASE64.decode(SECRET_KEY);


    public Key getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }



    public void save(UserEntity user) {
        repo.save(user);
    }

    public UserEntity getUserByName(String username) {
        return repo.findByuserName(username);
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        UserEntity u = repo.findByuserName(username);
        u.setStatus(1);
        save(u);
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
                .setSigningKey(getSecretKey())
                //.setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        long currentTimeMillis = System.currentTimeMillis();
        long expirationTimeMillis = expiration.getTime();
        int exp = (int) ((expirationTimeMillis - currentTimeMillis) / 1000);

        return exp;
    }

    public UserEntity extractUser(String token) {
        String user = Jwts.parser()
                .setSigningKey(getSecretKey())
                //.setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        UserEntity result = repo.findByuserName(user);
        return result;
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
        } else {
            return "Login successful";
        }
    }

    public String validateUser(UserEntity user){
        UserEntity userCheck = repo.findByuserName(user.getUserName());
        if (userCheck == null && userCheck.getStatus() != 1){
            throw new ValidateException("Please login to use service");
        } else {
            return "Pass";
        }
    }




    public void logout(UserEntity user){
        UserEntity u = repo.findByuserName(user.getUserName());
        u.setStatus(0);
        save(u);
    }



}
