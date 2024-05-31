package com.example.demo.domain.review.application.dto.request;

import lombok.Getter;

@Getter
public class ModifyReviewRequest {
    private int score;
    private String content;
    private String imgUrl;
    private String tendency;
}
