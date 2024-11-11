package com.nhnacademy.twojopingfront.admin.wrap.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * 포장 정책 생성 요청을 위한 DTO
 */
public record WrapRequestDto(
        @NotBlank
        String name,

        @Positive
        Integer wrapPrice,

        boolean isActive
) {}