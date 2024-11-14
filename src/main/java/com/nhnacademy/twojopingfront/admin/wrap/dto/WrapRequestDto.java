package com.nhnacademy.twojopingfront.admin.wrap.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * 포장 상품 생성 요청을 위한 DTO
 *
 *  @author : 박채연
 *  @date : 2024-11-07
 */
public record WrapRequestDto(
        @NotBlank
        String name,

        @Positive
        Integer wrapPrice,

        boolean isActive
) {}