package com.example.demo.domain.review;

import com.example.demo.api.review.dto.ModifyReviewRequest;
import com.example.demo.domain.review.Review;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {

    private final EntityManager em;

    public void write(Review review) {
        if (review.getReviewId() == null) {
            em.persist(review);
        } else {
            em.merge(review);
        }
    }

    public void update(Long reviewId, ModifyReviewRequest request) {
        Review review = em.find(Review.class, reviewId);
        Review.updateReview(review, request.getScore(), request.getContent(), request.getImgUrl(), request.getTendency());
    }

    public void delete(Long reviewId) {
        Review review = em.find(Review.class, reviewId);
        Review.deleteReview(review);
    }

    public List<Review> findReviewByPlaceId(Long placeId) {
        return em.createQuery("select r from Review r where r.placeId = :placeId", Review.class)
                .setParameter("placeId", placeId)
                .getResultList();
    }
}
