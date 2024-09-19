package org.example.qtsv.repository;

import org.example.qtsv.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByuserName(String username); // Use this if 'username' is the correct property name
}
