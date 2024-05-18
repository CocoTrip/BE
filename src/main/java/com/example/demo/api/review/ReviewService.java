package com.example.demo.api.review;

import com.example.demo.api.review.dto.ModifyReviewRequest;
import com.example.demo.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public void writeReview(Review review) {
        reviewRepository.write(review);
    }

    @Transactional
    public void updateReview(Long reviewId, ModifyReviewRequest request) {
        reviewRepository.update(reviewId, request);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        reviewRepository.delete(reviewId);
    }
}
