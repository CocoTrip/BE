package io.cocotrip.domain.user.web;

import io.cocotrip.domain.auth.persistence.PrincipalDetails;
import io.cocotrip.domain.user.application.UserService;
import io.cocotrip.domain.user.persistence.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController 유저 정보 조회 및 수정 컨트롤러
 */
@RequiredArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@RestController
public class UserController {

    private final UserService userService;

    /**
     * 현재 로그인한 유저 정보 조회
     *
     * @param authentication Authentication 객체
     * @return User 객체
     */
    @GetMapping("/api/me")
    public ResponseEntity<User> me(final Authentication authentication) {

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();
        return ResponseEntity.ok(user);
    }

    // 테스트용 코드
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
