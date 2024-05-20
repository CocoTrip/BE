package com.example.demo.domain.user.repository;

import com.example.demo.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * UserRepository
 * User 엔티티의 JPA Repository
 * */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 이메일로 유저 정보를 조회하는 메소드
     * @param email String
     * @return Optional<User>
     * */
    Optional<User> findByEmail(String email);
}
