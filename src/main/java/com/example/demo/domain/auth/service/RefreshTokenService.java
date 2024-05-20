package com.example.demo.domain.auth.service;

import com.example.demo.domain.auth.entity.RefreshToken;
import com.example.demo.domain.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    //exception 처리 필요
    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new IllegalArgumentException("해당 리프레시 토큰이 없습니다."));
    }
}
