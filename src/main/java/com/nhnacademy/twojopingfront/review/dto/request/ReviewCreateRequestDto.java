package com.nhnacademy.twojopingfront.review.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;


/**
 * 리뷰 생성할 때 Request DTO
 *
 * @author : 이유현
 * @date : 2024-11-12
 */

public record ReviewCreateRequestDto (
    ReviewDetailRequestDto reviewDetailRequestDto,
    ReviewImageUrlRequestDto reviewImageUrlRequestDto
)
{}
