package io.cocotrip.domain.review.application;

import io.cocotrip.domain.review.application.dto.request.WriteReviewRequest;
import io.cocotrip.domain.review.persistence.Review;
import io.cocotrip.domain.review.persistence.ReviewRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @MockBean
    private ReviewRepository reviewRepository;

    private Review review;

    @BeforeEach
    void setUp() {
        review = Review.createReview(1L, 3L, 5, "힐링하다 갑니다.", "http://image.com");
    }

    @Test
    @DisplayName("정상 리뷰 작성")
    void writeReview() {
        // given
        WriteReviewRequest request = new WriteReviewRequest(3L, 5, "힐링하다 갑니다.", "http://image.com");
        given(reviewRepository.write(any(Review.class))).willReturn(1L);

        // when
        Long reviewId = reviewService.writeReview(request);

        // then
        assertThat(reviewId).isEqualTo(1L);
        verify(reviewRepository, times(1)).write(any(Review.class));
    }

    @Test
    @DisplayName("정상 placeId로 리뷰 조회")
    void findByPlaceId() {
        // given
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);
        given(reviewRepository.findByPlaceId(anyLong())).willReturn(reviews);

        // when
        List<Review> result = reviewService.findByPlaceId(2L);

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).contains(review);
    }

    @Test
    @DisplayName("정상 reviewId로 리뷰 조회")
    void findByReviewId() {
        // given
        given(reviewRepository.findByReviewId(anyLong())).willReturn(Optional.ofNullable(review));

        // when
        Review result = reviewService.findByReviewId(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(review);
    }

}