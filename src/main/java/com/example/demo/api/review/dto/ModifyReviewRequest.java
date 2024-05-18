package com.example.demo.api.review.dto;

import lombok.Getter;

@Getter
public class ModifyReviewRequest {
    private int score;
    private String content;
    private String imgUrl;
    private String tendency;
}
