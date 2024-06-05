package io.cocotrip.domain.auth.application;

import io.cocotrip.domain.user.application.UserService;
import io.cocotrip.domain.user.persistence.User;
import io.cocotrip.global.config.jwt.TokenProvider;
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
    public String createNewAccessToken(final String refreshToken) {
        if (!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }

        final Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        final User user = userService.findById(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
