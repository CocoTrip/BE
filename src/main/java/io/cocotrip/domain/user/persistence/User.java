package io.cocotrip.domain.user.persistence;

import io.cocotrip.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "oauth2_id", length = 100, unique = true)
    private String oauth2Id;

    @Column(name = "nickname", length = 20, unique = true)
    private String nickname;

    @Column(name = "email", length = 100, unique = false, nullable = false)
    private String email;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "role", length = 20)
    private String role;

    @Column(name = "provider", length = 100)
    private String provider;

    @Column(name = "provider_id", length = 100, unique = true)
    private String providerId;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    public User(String oauth2Id, String role,
                String provider, String providerId,
                String nickname,
                String email,
                String password) {
        this.oauth2Id = oauth2Id;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

}
