package io.cocotrip.domain.auth.application.dto;

import lombok.Getter;

@Getter
public class CreateAccessTokenRequest {
    private String refreshToken;
}
