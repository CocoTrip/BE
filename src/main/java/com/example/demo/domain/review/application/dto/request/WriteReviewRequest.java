package com.example.demo.domain.review.application.dto.request;

import lombok.Getter;

@Getter
public class WriteReviewRequest {
    private Long placeId;
    private int score;
    private String content;
    private String imgUrl;
}
