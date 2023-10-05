package com.example.letai.repository;

import com.example.letai.model.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    public RefreshToken findByToken(String refreshToken);
}
