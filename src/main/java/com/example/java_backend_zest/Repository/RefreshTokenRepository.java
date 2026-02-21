package com.example.java_backend_zest.Repository;


import com.example.java_backend_zest.entity.RefreshToken;
import com.example.java_backend_zest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}
