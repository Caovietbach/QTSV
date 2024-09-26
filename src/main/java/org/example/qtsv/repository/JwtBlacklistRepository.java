package org.example.qtsv.repository;

import org.example.qtsv.entity.JwtBlacklist;
import org.example.qtsv.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtBlacklistRepository extends JpaRepository<JwtBlacklist, Long> {
    JwtBlacklist findByJwt(String jwt);
}

