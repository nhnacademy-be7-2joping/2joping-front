package com.nhnacademy.twojopingfront.review.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;


public record ReviewModifyRequestDto (
        Long reviewId,
        int ratingValue,
        String title,
        String text,
        String imageUrl  // TODO 임시
)
{}
