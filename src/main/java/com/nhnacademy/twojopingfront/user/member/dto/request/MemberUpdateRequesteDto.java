package com.nhnacademy.twojopingfront.user.member.dto.request;


import jakarta.validation.constraints.*;

/**
 * 회원 정보 수정 요청을 처리하기 위한 DTO 클래스입니다.
 * 이 클래스는 사용자로부터 입력받은 데이터를 검증하고, 백엔드 서비스에서 사용될 수 있도록 제공합니다.
 *
 * @author Luha
 * @since 1.0
 */
public record MemberUpdateRequesteDto(

        @NotBlank(message = "전화번호는 필수 입력 사항입니다.")
        @Pattern(regexp = "010-\\d{3,4}-\\d{4}", message = "전화번호는 '010-0000-0000' 형식이어야 합니다.")
        String phone,

        @NotBlank(message = "이메일은 필수 입력 사항입니다.")
        @Email(message = "유효한 이메일 주소를 입력해 주세요.")
        String email,

        @NotBlank(message = "닉네임은 필수 입력 사항입니다.")
        @Size(min = 2, max = 20, message = "닉네임은 최소 2자 이상, 최대 20자까지 입력 가능합니다.")
        String nickName

) {
}
