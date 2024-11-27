package com.nhnacademy.twojopingfront.bookset.tag.dto;

/**
 * 태그 정보를 반환하는 DTO
 *  @author : 박채연
 *  @date : 2024-11-17
 *
 */
public record TagResponseDto(
        Long tagId,
        String name
) {}
