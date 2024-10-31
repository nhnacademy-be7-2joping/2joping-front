package com.nhnacademy.twojopingfront.user.dto.request;

public record LoginNonMemberRequestDto(
        String name,
        String password,
        String phone,
        String email
) {
}
