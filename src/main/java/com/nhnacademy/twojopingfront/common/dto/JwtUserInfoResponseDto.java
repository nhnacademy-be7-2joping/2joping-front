package com.nhnacademy.twojopingfront.common.dto;

public record JwtUserInfoResponseDto(
        Long id,
        String nickName,
        String role
) {
}
