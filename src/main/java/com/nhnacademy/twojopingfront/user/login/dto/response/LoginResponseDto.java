package com.nhnacademy.twojopingfront.user.login.dto.response;

public record LoginResponseDto(
        Long customerId, // 고객 ID
        String id,// 사용자 정보
        String role,// 사용자 권한 (ROLE_MEMBER || ROLE_ADMIN)
        String token  // JWT 토큰
) {

}

