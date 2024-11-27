package com.nhnacademy.twojopingfront.user.member.dto.response;

/**
 * 회원 주소 정보를 반환하기 위한 DTO 클래스입니다.
 * 주소 관련 데이터가 포함되어 있으며, 주로 회원의 주소 조회 시 사용됩니다.
 *
 * @author Luha
 * @since 1.0
 */
public record MemberAddressResponseDto(
        Long memberAddressId,       // 주소 ID (DB에서 생성된 ID)
        String postalCode,          // 우편번호
        String roadAddress,         // 도로명 주소
        String detailAddress,       // 상세 주소
        String addressAlias,        // 주소 별칭
        boolean isDefaultAddress,   // 기본 주소 여부
        String receiver             // 수신인
) {}