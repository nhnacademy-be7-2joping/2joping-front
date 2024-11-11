package com.nhnacademy.twojopingfront.admin.wrap.dto;

/**
 * 포장 정책 정보를 반환하는 DTO
 */
public record WrapResponseDto(
        Long wrapId,
        String name,
        int wrapPrice,
        boolean isActive
) {}
