package io.pet.cocotrip.domain.auth.web;

import io.pet.cocotrip.domain.auth.application.TokenService;
import io.pet.cocotrip.domain.auth.application.dto.CreateAccessTokenRequest;
import io.pet.cocotrip.domain.auth.application.dto.CreateAccessTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * TokenApiController 토큰 관련 API 컨트롤러
 */
@RequiredArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@RestController
public class TokenApiController {
    private final TokenService tokenService;

    /**
     * 리프레시 토큰으로 새로운 액세스 토큰 생성 API
     *
     * @param request CreateAccessTokenRequest 객체
     * @return CreateAccessTokenResponse 객체
     */
    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken
    (@RequestBody final CreateAccessTokenRequest request) {
        final String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateAccessTokenResponse(newAccessToken));
    }
}
