package com.example.demo.user.service;

import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * UserService
 * 유저 정보를 처리하는 서비스 클래스
 * */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Service
public class UserService {
    private final UserRepository userRepository;

    /**
     * 유저 정보를 저장하는 메소드
     * @param dto AddUserRequest 객체
     * @return Long
     * */
    public Long save(AddUserRequest dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .build()).getUserId();
    }

    /**
     * 유저 정보를 조회하는 메소드
     * @param userId Long
     * @return User
     * */
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
    }

    /**
     * 이메일로 유저 정보를 조회하는 메소드
     * @param email String
     * @return User
     * */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
    }

}
