package com.example.demo.domain.auth.service;

import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.global.config.jwt.TokenProvider;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * TokenService 토큰을 다루는 서비스 클래스
 */
@RequiredArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    /**
     * 리프레시 토큰으로 새로운 액세스 토큰 생성
     *
     * @param refreshToken 리프레시 토큰
     * @return 새로운 액세스 토큰
     */
    public String createNewAccessToken(String refreshToken) {
        if (!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findById(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
