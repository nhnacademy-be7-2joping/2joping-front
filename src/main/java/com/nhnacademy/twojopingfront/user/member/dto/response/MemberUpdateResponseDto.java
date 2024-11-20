package com.nhnacademy.twojopingfront.user.member.dto.response;

import com.nhnacademy.twojopingfront.user.member.dto.enums.Gender;

import java.time.LocalDate;

/**
 * 회원 정보 수정 결과를 반환하기 위한 DTO 클래스입니다.
 * 수정된 회원 정보를 포함하며, 클라이언트에 전달됩니다.
 *
 * @author Luha
 * @since 1.0
 */
public record MemberUpdateResponseDto(
        String name,
        Gender gender,
        LocalDate birthday,
        String phone,
        String email,
        String nickName
) {
}
