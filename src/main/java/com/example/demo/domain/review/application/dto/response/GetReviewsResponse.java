package com.example.demo.domain.review.application.dto.response;

import com.example.demo.domain.review.persistence.Review;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GetReviewsResponse {
    private List<Review> reviews;
}
