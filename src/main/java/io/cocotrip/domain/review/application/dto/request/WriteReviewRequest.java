package io.cocotrip.domain.review.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WriteReviewRequest {
    private Long placeId;
    private int score;
    private String content;
    private String imgUrl;
}
