package com.example.demo.config.jwt;

import com.example.demo.global.config.jwt.JwtProperties;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static java.util.Collections.emptyMap;

/**
 * JwtFactory
 * JWT 토큰을 생성하는 클래스
 * */
@Getter
public class JwtFactory {
    private String subject = "yoseb95@gmail.com";
    private Date issuedAt = new Date();
    private Date expiration = new Date(new Date().getTime() + Duration.ofDays(14).toMillis());
    private Map<String, Object> claims = emptyMap();

    /**
     * JwtFactory 생성자
     * @param subject 주제
     * @param issuedAt 발급 시간
     * @param expiration 만료 시간
     * @param claims 클레임
     * */
    @Builder
    public JwtFactory(String subject, Date issuedAt, Date expiration, Map<String, Object> claims) {
        this.subject = subject != null ? subject : this.subject;
        this.issuedAt = issuedAt != null ? issuedAt : this.issuedAt;
        this.expiration = expiration != null ? expiration : this.expiration;
        this.claims = claims != null ? claims : this.claims;
    }

    /**
     * 기본값으로 JwtFactory 생성
     * @return JwtFactory
     * */
    public static JwtFactory withDefaultValues() {
        return JwtFactory.builder().build();
    }

    /**
     * 토큰 생성
     * @param jwtProperties JwtProperties 객체
     * @return String
     * */
    public String createToken(JwtProperties jwtProperties) {
        return Jwts.builder()
                .setSubject(subject)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }
}
