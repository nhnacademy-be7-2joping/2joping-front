package com.nhnacademy.twojopingfront.review.dto.request;

public record ReviewDetailRequestDto (
        Long orderDetailId,
        Long customerId,
        Long bookId,
        int ratingValue,
        String title,
        String text
)
{}
