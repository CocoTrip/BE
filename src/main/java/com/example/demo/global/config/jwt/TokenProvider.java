package com.example.demo.global.config.jwt;

import com.example.demo.domain.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * TokenProvider 토큰을 생성하고 유효성을 검사하는 클래스
 */
@RequiredArgsConstructor
@Component
public class TokenProvider {

    private final JwtProperties jwtProperties;

    /**
     * 토큰 발급
     *
     * @param user      User 객체
     * @param expiredAt 만료 시간
     * @return String
     */
    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    /**
     * 토큰 생성
     *
     * @param user User 객체
     * @return String
     */
    private String makeToken(Date expiry, User user) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 type: jwt
                .setIssuer(jwtProperties.getIssuer()) // 발급자
                .setIssuedAt(now) // 발급 시간
                .setExpiration(expiry) // 만료 시간
                .setSubject(user.getEmail()) // 주제 : 유저 이메일
                .claim("id", user.getUserId()) // 클레인 ID : 유저 ID
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()) // 서명 알고리즘, 비밀키
                .compact();
    }

    /**
     * 토큰 유효성 검사
     *
     * @param token String
     * @return boolean
     */
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 토큰에서 Authentication 객체 생성
     *
     * @param token String
     * @return Authentication
     */
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities), token,
                authorities);
    }

    // 토큰에서 유저 ID 추출

    /**
     * 토큰에서 유저 ID 추출
     *
     * @param token String
     * @return Long
     */
    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    /**
     * 클레임 조회
     *
     * @param token String
     * @return Claims
     */
    private Claims getClaims(String token) {
        return Jwts.parser() //클레임 조회
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
