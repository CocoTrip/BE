package io.cocotrip.domain.review.persistence;

import io.cocotrip.domain.review.application.dto.request.ModifyReviewRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@SpringJUnitConfig
@ComponentScan(basePackages = "io.cocotrip.domain.review.persistence")
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @PersistenceContext
    private EntityManager em;

    private Review review;

    @BeforeEach
    void setUp() {
        review = Review.createReview(1L, 3L, 5, "힐링하다 갑니다.", "http://image.com");

        em.persist(review);
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("정상 리뷰 작성")
    void writeReview() {
        // given
        Review newReview = Review.createReview(1L, 3L, 5, "힐링하다 갑니다.", "http://image.com");

        // when
        reviewRepository.write(newReview);
        em.flush();
        em.clear();

        // then
        Review findReview = em.find(Review.class, newReview.getReviewId());
        assertThat(findReview).isNotNull();
        assertThat(findReview.getScore()).isEqualTo(5);
    }

    @Test
    @DisplayName("정상 리뷰 수정")
    void modifyReview() {
        // given
        ModifyReviewRequest request = new ModifyReviewRequest(3, "킬링하다 갑니다.",
                "http://image.com");

        // when
        reviewRepository.update(review.getReviewId(), request);
        em.flush();
        em.clear();

        // then
        Review updatedReview = em.find(Review.class, review.getReviewId());
        assertThat(updatedReview.getScore()).isEqualTo(3);
        assertThat(updatedReview.getContent()).isEqualTo("킬링하다 갑니다.");
    }

    @Test
    @DisplayName("정상 리뷰 삭제")
    void deleteReview() {
        // when
        reviewRepository.delete(review.getReviewId());
        em.flush();
        em.clear();

        // then
        Optional<Review> result = reviewRepository.findByReviewId(review.getReviewId());
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("장소 id로 리뷰 전체 조회")
    void findByPlaceId() {
        // when
        List<Review> findReviews = reviewRepository.findByPlaceId(3L);

        // then
        assertThat(findReviews).isNotEmpty();
        assertThat(findReviews).hasSize(1);
        assertThat(findReviews.get(0).getReviewId()).isEqualTo(review.getReviewId());
    }

    @Test
    @DisplayName("리뷰 id로 리뷰 단건 조회")
    void findByReviewId() {
        // when
        Optional<Review> findReview = reviewRepository.findByReviewId(review.getReviewId());

        // then
        assertThat(findReview).isPresent();
        assertThat(findReview.get().getReviewId()).isEqualTo(review.getReviewId());
    }
}