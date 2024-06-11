package io.cocotrip.domain.review.application;

import io.cocotrip.domain.review.application.dto.request.ModifyReviewRequest;
import io.cocotrip.domain.review.application.dto.request.WriteReviewRequest;
import io.cocotrip.domain.review.persistence.Review;
import io.cocotrip.domain.review.persistence.ReviewRepository;
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
    public Long writeReview(WriteReviewRequest request) {
        // 현재는 더미 데이터 사용
        Long findUserId = 1L;
        // 리뷰 생성 및 등록
        Review review = Review.createReview(findUserId, request.getPlaceId(), request.getScore(), request.getContent(), request.getImgUrl());
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

        // review의 작성자가 맞는지 확인
        Long userId = 1L;  // 더미 데이터
        if (review.getUserId() != userId) {
            throw new IllegalArgumentException("해당 기능에 대한 권한이 존재하지 않습니다.");
        }
        return review;
    }

}
