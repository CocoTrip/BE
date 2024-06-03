package com.example.demo.domain.review.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifyReviewRequest {
    private int score;
    private String content;
    private String imgUrl;
    private String tendency;
}
