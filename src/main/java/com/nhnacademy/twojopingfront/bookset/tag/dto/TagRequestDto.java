package com.nhnacademy.twojopingfront.bookset.tag.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 태그 생성/수정 요청을 위한 DTO
 *  *  @author : 박채연
 *  *  @date : 2024-11-17
 */
public record TagRequestDto(
        @NotBlank(message = "태그 이름은 필수 입력 사항입니다.")
        String name
) {}
