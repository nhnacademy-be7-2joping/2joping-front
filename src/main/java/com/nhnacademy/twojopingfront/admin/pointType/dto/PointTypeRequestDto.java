package com.nhnacademy.twojopingfront.admin.pointType.dto;


import com.nhnacademy.twojopingfront.admin.pointType.enums.PointTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * 포인트 타입 요청을 위한 DTO
 *  *  @author : 박채연
 *  *  @date : 2024-11-18
 */
public record PointTypeRequestDto(
        @NotNull PointTypeEnum type,
        @Positive Integer accVal,
        @NotBlank String name,
        boolean isActive
) {}
