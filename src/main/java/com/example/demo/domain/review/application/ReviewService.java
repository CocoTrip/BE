package com.example.demo.domain.review.application;

import com.example.demo.domain.review.application.dto.request.ModifyReviewRequest;
import com.example.demo.domain.review.persistence.Review;
import com.example.demo.domain.review.persistence.ReviewRepository;
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
    public Long writeReview(Review review) {
        Long reviewId = reviewRepository.write(review);
        return reviewId;
    }

    @Transactional
    public void updateReview(Long reviewId, ModifyReviewRequest request) {
        reviewRepository.update(reviewId, request);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        reviewRepository.delete(reviewId);
    }

    public List<Review> findByPlaceId(Long placeId) {
        List<Review> result = reviewRepository.findByPlaceId(placeId);
        return result;
    }

    public Review findByReviewId(Long reviewId) {
        Review review = reviewRepository.findByReviewId(reviewId).orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));
        return review;
    }

}
