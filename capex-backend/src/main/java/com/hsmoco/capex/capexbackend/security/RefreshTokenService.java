package com.hsmoco.capex.capexbackend.security;

import com.hsmoco.capex.capexbackend.security.jwt.JwtService;
import com.hsmoco.capex.capexbackend.user.User;
import com.hsmoco.capex.capexbackend.user.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository,
                               UserRepository userRepository,
                               JwtService jwtService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public RefreshToken createRefreshToken(User user) {
        String token = jwtService.generateRefreshToken(user);
        Claims claims = jwtService.parseToken(token);

        RefreshToken rt = new RefreshToken();
        rt.setToken(token);
        rt.setUser(user);
        rt.setExpiresAt(claims.getExpiration().toInstant());
        rt.setRevoked(false);

        return refreshTokenRepository.save(rt);
    }

    public User validateAndGetUser(String refreshToken) {
        RefreshToken rt = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (rt.isRevoked() || rt.getExpiresAt().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token expired or revoked");
        }

        Claims claims = jwtService.parseToken(refreshToken);
        String username = claims.getSubject();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void revokeToken(String refreshToken) {
        refreshTokenRepository.findByToken(refreshToken).ifPresent(rt -> {
            rt.setRevoked(true);
            refreshTokenRepository.save(rt);
        });
    }
}