package com.nhnacademy.twojopingfront.bookset.category.dto.response;

public record CategoryResponseDto(
        Long categoryId,
        Long parentCategoryId,
        String name
) {}