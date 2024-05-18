package com.example.demo.api.review;

import com.example.demo.domain.Review;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
