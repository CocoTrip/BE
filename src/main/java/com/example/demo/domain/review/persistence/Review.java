package com.example.demo.domain.review.persistence;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "place_id")
    private Long placeId;

    @Column(name = "score")
    private int score;

    @Column(name = "content")
    private String content;

    @Column(name = "imgUrl")
    private String imgUrl;

    @Column(name = "tendency")
    private String tendency;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(name = "deleted_at")
    private String deletedAt;


    // 생성 메서드
    public static Review createReview(Long userId, Long placeId, int score, String content, String imgUrl, String tendency) {
        Review review = new Review();

        review.userId = userId;
        review.placeId = placeId;
        review.score = score;
        review.content = content;
        review.imgUrl = imgUrl;
        review.tendency = tendency;

        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        review.createdAt = currentTime;
        review.updatedAt = currentTime;

        return review;
    }

    // 업데이트 메서드
    public static void updateReview(Review review, int score, String content, String imgUrl, String tendency) {
        review.score = score;
        review.content = content;
        review.imgUrl = imgUrl;
        review.tendency = tendency;

        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        review.updatedAt = currentTime;
    }

    // 삭제 메서드
    public static void deleteReview(Review review) {
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        review.deletedAt = currentTime;
    }
}
