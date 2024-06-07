package com.example.demo.domain.review.web;

import com.example.demo.global.common.ApiResponse;
import com.example.demo.domain.review.application.dto.response.GetReviewsResponse;
import com.example.demo.domain.review.application.dto.request.ModifyReviewRequest;
import com.example.demo.domain.review.application.dto.request.WriteReviewRequest;
import com.example.demo.domain.review.persistence.Review;
import com.example.demo.domain.review.application.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 작성 API
     * @param WriteReviewRequest request
     * @return ApiResponse
     */
    @PostMapping
    public ApiResponse writeReview(@RequestBody WriteReviewRequest request) {
        // 헤더로 받은 jwt 토큰을 복호화 해서 유저 정보 획득, 아마도 userId가 아닌 User 객체 자체를 변수로 사용할듯
        // 현재는 더미 데이터 사용
        Long findUserId = 1L;

        // 리뷰 생성 및 등록
        Review review = Review.createReview(findUserId, request.getPlaceId(), request.getScore(), request.getContent(), request.getImgUrl());
        Long reviewId = reviewService.writeReview(review);
        return new ApiResponse<>(200, "API OK");
    }

    /**
     * 리뷰 수정 API
     * @param Long reviewId
     * @param ModifyReviewRequest request
     * @return ApiResponse
     */
    @PostMapping("/{reviewId}")
    public ApiResponse modifyReview(
            @PathVariable("reviewId") Long reviewId,
            @RequestBody ModifyReviewRequest request
    ) {
        // reviewId를 통해 review 조회
        Review review = reviewService.findByReviewId(reviewId);

        // review의 작성자가 맞는지 확인
        Long userId = 1L;  // 더미 데이터
        if (review.getUserId() != userId) {
            throw new IllegalArgumentException("해당 기능에 대한 권한이 존재하지 않습니다.");
        }

        // 위 검증을 통과한다면, review를 업데이트
        reviewService.updateReview(reviewId, request);
        return new ApiResponse<>(200, "API OK");
    }

    /**
     * 리뷰 삭제 API
     * @param Long reviewId
     * @return ApiResponse
     */
    @DeleteMapping("/{reviewId}")
    public ApiResponse deleteReview(@PathVariable("reviewId") Long reviewId) {
        // reviewId를 통해 review 조회
        Review review = reviewService.findByReviewId(reviewId);

        // review의 작성자가 맞는지 확인
        Long userId = 1L;  // 더미 데이터
        if (review.getUserId() != userId) {
            throw new IllegalArgumentException("해당 기능에 대한 권한이 존재하지 않습니다.");
        }

        // 위 검증을 통과한다면, review를 삭제
        reviewService.deleteReview(reviewId);
        return new ApiResponse<>(200, "API OK");
    }

    /**
     * 리뷰 조회 API
     * @param Long placeId
     * @return ApiResponse<GetReviewsResponse>
     * 이슈사항 : 삭제 시, deletedAt이 요청한 시간으로 업데이트 되는 형식 -> Status 사용하는 것이 더 좋아 보임
     */
    @GetMapping("/{placeId}")
    public ApiResponse<GetReviewsResponse> getReviews(@PathVariable("placeId") Long placeId) {
        // placeId로 해당 장소에 대한 리뷰 리스트 조회
        List<Review> findReviews = reviewService.findByPlaceId(placeId);

        GetReviewsResponse response = new GetReviewsResponse(findReviews);
        return new ApiResponse<GetReviewsResponse>(200, "API OK", response);
    }
}
