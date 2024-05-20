package com.example.demo.global.config.oauth;

import com.example.demo.global.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.web.util.WebUtils;

/**
 * OAuth2AuthorizationRequestBasedOnCookieRepository OAuth2AuthorizationRequest를 쿠키를 이용하여 저장하고 불러오는 클래스
 */
public class OAuth2AuthorizationRequestBasedOnCookieRepository implements
        AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    public final static String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    private final static int COOKIE_EXPIRE_SECONDS = 10000;

    /**
     * 인증 요청 쿠키 삭제
     *
     * @param request  HttpServletRequest 객체
     * @param response HttpServletResponse 객체
     * @return OAuth2AuthorizationRequest 객체
     */
    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request,
                                                                 HttpServletResponse response) {
        return this.loadAuthorizationRequest(request);
    }

    /**
     * 인증 요청 쿠키 불러오기
     *
     * @param request HttpServletRequest 객체
     * @return OAuth2AuthorizationRequest 객체
     */
    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        return CookieUtil.deserialize(cookie, OAuth2AuthorizationRequest.class);
    }

    /**
     * 인증 요청 쿠키 저장
     *
     * @param authorizationRequest OAuth2AuthorizationRequest 객체
     * @param request              HttpServletRequest 객체
     * @param response             HttpServletResponse 객체
     */
    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request,
                                         HttpServletResponse response) {
        if (authorizationRequest == null) {
            removeAuthorizationRequest(request, response);
            return;
        }
        CookieUtil.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
                CookieUtil.serialize(authorizationRequest), COOKIE_EXPIRE_SECONDS);
    }

    /**
     * 인증 요청 쿠키 삭제
     *
     * @param request  HttpServletRequest 객체
     * @param response HttpServletResponse 객체
     */
    public void removeAuthorizationCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
    }
}
