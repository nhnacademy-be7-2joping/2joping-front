package com.nhnacademy.twojopingfront.user.login.dto.request;

public record LoginNonMemberRequestDto(
        String name,
        String password,
        String phone,
        String email
) {
}
