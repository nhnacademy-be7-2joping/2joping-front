package com.nhnacademy.twojopingfront.review.dto.request;


public record ReviewModifyRequestDto (
        ReviewModifyDetailRequestDto reviewModifyDetailRequestDto,
        ReviewImageUrlRequestDto reviewImageUrlRequestDto,
        boolean deleteImage
)
{}
