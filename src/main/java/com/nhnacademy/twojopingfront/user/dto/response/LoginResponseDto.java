package com.nhnacademy.twojopingfront.user.dto.response;

public record LoginResponseDto(
        Long customerId, // 고객 ID
        String id// 사용자 정보
) {

}

