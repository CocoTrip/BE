package com.example.demo.domain.review;

import com.example.demo.api.review.dto.ModifyReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<Review> findReviewByPlaceId(Long placeId) {
        List<Review> result = reviewRepository.findReviewByPlaceId(placeId);
        return result;
    }
}
