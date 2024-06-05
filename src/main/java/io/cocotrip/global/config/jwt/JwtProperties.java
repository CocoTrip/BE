package io.cocotrip.global.config.jwt;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

/**
 * JwtProperties application.yml 에서 jwt 설정 정보를 가져와서 담는 클래스
 */
@Getter
@ConfigurationProperties("jwt")
public class JwtProperties {
    private final String issuer;
    private final String secretKey;

    @ConstructorBinding
    public JwtProperties(String issuer, String secretKey) {
        this.issuer = issuer;
        this.secretKey = secretKey;
    }
}
