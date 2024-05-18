package com.example.demo.api.review;

import com.example.demo.api.ApiResponse;
import com.example.demo.api.review.dto.ModifyReviewRequest;
import com.example.demo.api.review.dto.WriteReviewRequest;
import com.example.demo.domain.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 등록 API
     */
    @PostMapping
    public ApiResponse writeReview(@RequestBody WriteReviewRequest request) {

        // 헤더로 받은 jwt 토큰을 복호화 해서 유저 정보 획득, 아마도 userId가 아닌 user 객체 자체를 변수로 사용할듯
        // 더미 데이터
        Long findUserId = 1L;

        // 리뷰 생성 및 등록
        Review review = Review.createReview(findUserId, request.getPlaceId(), request.getScore(), request.getContent(), request.getImgUrl(), request.getTendency());
        reviewService.writeReview(review);

        return new ApiResponse<>(200, "API OK");
    }

    /**
     * 리뷰 수정 API
     */
    @PostMapping("/{reviewId}")
    public ApiResponse modifyReview(
            @PathVariable("reviewId") Long reviewId,
            @RequestBody ModifyReviewRequest request
    ) {
        // reviewId로 review 찾아서 업데이트
        reviewService.updateReview(reviewId, request);
        return new ApiResponse<>(200, "API OK");
    }

    /**
     * 리뷰 삭제 API
     */
    @DeleteMapping("/{reviewId}")
    public ApiResponse deleteReview(@PathVariable("reviewId") Long reviewId) {
        reviewService.deleteReview(reviewId);
        return new ApiResponse<>(200, "API OK");
    }
}