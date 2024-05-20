package com.example.demo.domain.user.controller;

import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.service.UserService;
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
    public ResponseEntity<User> me(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        return ResponseEntity.ok(user);
    }
}
