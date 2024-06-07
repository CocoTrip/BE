package com.example.demo.domain.review.application;

import com.example.demo.domain.review.persistence.Review;
import com.example.demo.domain.review.persistence.ReviewRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
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
        when(reviewRepository.write(review)).thenReturn(1L);

        // when
        Long reviewId = reviewService.writeReview(review);

        // then
        assertThat(reviewId).isEqualTo(1L);
        verify(reviewRepository, times(1)).write(review);
    }

    @Test
    @DisplayName("정상 placeId로 리뷰 조회")
    void findByPlaceId() {
        // when
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);
        when(reviewRepository.findByPlaceId(anyLong())).thenReturn(reviews);

        // then
        List<Review> result = reviewService.findByPlaceId(2L);
        assertThat(result).isNotEmpty();
        assertThat(result).contains(review);
    }

    @Test
    @DisplayName("정상 reviewId로 리뷰 조회")
    void findByReviewId() {
        // when
        when(reviewRepository.findByReviewId(anyLong())).thenReturn(Optional.ofNullable(review));

        // then
        Review result = reviewService.findByReviewId(1L);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(review);
    }

}