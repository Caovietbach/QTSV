package org.example.qtsv.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue
    protected long id;
    private String userName;

    private String password;

    private int status;

    private String role;


    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }



    public UserEntity(){

    }

    public UserEntity(long id, String userName, String password,  String role, int status) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.status = status;

    }
}
