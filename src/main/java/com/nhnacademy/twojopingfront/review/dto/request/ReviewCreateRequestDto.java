package com.nhnacademy.twojopingfront.review.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;


/**
 * 리뷰 생성할 때 Request DTO
 *
 * @author : 이유현
 * @date : 2024-11-12
 */

public record ReviewCreateRequestDto (
         Long reviewId,
         Long orderDetailId,
         Long customerId,
         Long bookId,
         int ratingValue,
         String title,
         String text,
         String imageUrl // TODO 임시
)
{}
