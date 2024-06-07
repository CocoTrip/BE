package com.example.demo.domain.review.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModifyReviewRequest {
    private int score;
    private String content;
    private String imgUrl;
}
