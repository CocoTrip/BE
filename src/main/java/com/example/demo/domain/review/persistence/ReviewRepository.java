package com.example.demo.domain.review.persistence;

import com.example.demo.domain.review.application.dto.request.ModifyReviewRequest;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {

    private final EntityManager em;

    public Long write(Review review) {
        if (review.getReviewId() == null) {
            em.persist(review);
        } else {
            em.merge(review);
        }
        return review.getReviewId();
    }

    public void update(Long reviewId, ModifyReviewRequest request) {
        Review review = em.find(Review.class, reviewId);
        Review.updateReview(review, request.getScore(), request.getContent(), request.getImgUrl());
    }

    public void delete(Long reviewId) {
        Review review = em.find(Review.class, reviewId);
        Review.deleteReview(review);
    }

    public List<Review> findByPlaceId(Long placeId) {
        return em.createQuery("select r from Review r where r.placeId = :placeId and r.deletedAt is null", Review.class)
                .setParameter("placeId", placeId)
                .getResultList();
    }

    public Optional<Review> findByReviewId(Long reviewId) {
        Review review = em.find(Review.class, reviewId);
        // 삭제 X : deletedAt = null, 삭제 O : deletedAt = 삭제 API 호출한 시간
        if (review.getDeletedAt() == null) {
            return Optional.ofNullable(review);
        }
        return Optional.empty();
    }
}
