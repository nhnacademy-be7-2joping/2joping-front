package com.nhnacademy.twojopingfront.user.member.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원가입 성공 시 회원에게 환영 메시지를 전달하는 DTO입니다.
 * 회원의 닉네임과 고정된 성공 메시지를 포함합니다.
 *
 * <p>
 * 필드:
 * - nickname: 회원의 닉네임
 * - MESSAGE: 회원가입 성공 시 표시될 고정 메시지
 * </p>
 *
 * @author Luha
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberCreateSuccessResponseDto {

    private String nickname;
    private final String MESSAGE = "님 회원가입을 축하드립니다. 로그인해주세요" ;

}
