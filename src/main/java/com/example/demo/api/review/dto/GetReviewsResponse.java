package com.example.demo.api.review.dto;

import com.example.demo.domain.Review;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GetReviewsResponse {
    private List<Review> reviews;
}
