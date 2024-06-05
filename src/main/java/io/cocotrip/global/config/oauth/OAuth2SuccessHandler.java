package io.cocotrip.global.config.oauth;

import io.cocotrip.domain.auth.persistence.PrincipalDetails;
import io.cocotrip.domain.auth.persistence.RefreshToken;
import io.cocotrip.domain.auth.persistence.RefreshTokenRepository;
import io.cocotrip.domain.user.application.UserService;
import io.cocotrip.domain.user.persistence.User;
import io.cocotrip.global.config.jwt.TokenProvider;
import io.cocotrip.global.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * OAuth2SuccessHandler OAuth2 로그인 성공 시 처리하는 핸들러 클래스
 */
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);
    public static final String REDIRECT_PATH = "/";

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
    private final UserService userService;

    /**
     * OAuth2 로그인 성공 시 처리하는 메소드
     *
     * @param request        HttpServletRequest 객체
     * @param response       HttpServletResponse 객체
     * @param authentication Authentication 객체
     */
    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) throws IOException {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();

        //리프레시 토큰 생성 -> 저장 -> 쿠키에 저장
        String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);
        saveRefreshTokenCookie(user.getUserId(), refreshToken);
        addRefreshTokenToCookie(request, response, refreshToken);

        // 액세스 토큰 생성 -> 패스에 엑세스 토큰 추가
        String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
        String targetUrl = getTargetUrl(accessToken);

        //인증 관련 설정값, 쿠키 제거
        clearAuthenticationAttributes(request, response);

        // 리다이렉트
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    //생성된 리프레시 토큰을 쿠키에 저장

    /**
     * 생성된 리프레시 토큰을 쿠키에 저장하는 메소드
     *
     * @param userId       Long
     * @param refreshToken String
     */
    private void saveRefreshTokenCookie(final Long userId, final String newRefreshToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
                .map(entity -> entity.update(newRefreshToken)).orElse(new RefreshToken(userId, newRefreshToken));

        refreshTokenRepository.save(refreshToken);
    }

    private void addRefreshTokenToCookie(final HttpServletRequest request, final HttpServletResponse response,
                                         final String refreshToken) {
        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
    }

    //인증 관련 설정값, 쿠키 제거

    /**
     * 인증 관련 설정값, 쿠키 제거하는 메소드
     *
     * @param request  HttpServletRequest 객체
     * @param response HttpServletResponse 객체
     */
    private void clearAuthenticationAttributes(final HttpServletRequest request, final HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequest(request, response);
    }

    //엑세스 토큰을 패스에 추가

    /**
     * 엑세스 토큰을 패스에 추가하는 메소드
     *
     * @param accessToken String
     * @return String
     */
    private String getTargetUrl(final String token) {
        return UriComponentsBuilder.fromUriString(REDIRECT_PATH).queryParam("token", token).build().toUriString();
    }
}
