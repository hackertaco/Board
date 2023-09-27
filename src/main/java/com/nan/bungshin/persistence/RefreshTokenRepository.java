package com.nan.bungshin.persistence;

import com.nan.bungshin.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByPayload(String payload);
    void deleteByPayload(String payload);
}
