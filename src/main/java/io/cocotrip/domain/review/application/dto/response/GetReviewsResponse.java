package io.cocotrip.domain.review.application.dto.response;

import io.cocotrip.domain.review.persistence.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetReviewsResponse {
    private List<Review> reviews;
}
