package com.example.demo.domain.user.entity;

import com.example.demo.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    //GenerationType Identify 와 비교 필요
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "nickname", length = 20, unique = true)
    private String nickname;

    @Column(name = "email", length = 100, unique = true, nullable = false)
    private String email;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    public User(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

}
