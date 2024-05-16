package com.example.demo.auth.service;

import com.example.demo.auth.entity.RefreshToken;
import com.example.demo.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new IllegalArgumentException("해당 리프레시 토큰이 없습니다."));
    }
}
