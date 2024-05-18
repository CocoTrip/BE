package com.example.demo.api.review.dto;

import com.example.demo.domain.review.Review;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GetReviewsResponse {
    private List<Review> reviews;
}
