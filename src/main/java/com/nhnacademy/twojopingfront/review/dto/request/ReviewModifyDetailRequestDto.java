package com.nhnacademy.twojopingfront.review.dto.request;

public record ReviewModifyDetailRequestDto (
        Long reviewId,
        int ratingValue,
        String title,
        String text
)
{}
