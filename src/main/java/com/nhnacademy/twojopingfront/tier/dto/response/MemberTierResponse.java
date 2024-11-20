package com.nhnacademy.twojopingfront.tier.dto.response;

import com.nhnacademy.twojopingfront.tier.enums.Tier;

/**
 * MemberTierResponse
 *
 * 회원 등급 정보를 포함하는 응답 객체.
 * 이 객체는 외부 API로부터 회원의 등급 정보를 받아와 클라이언트에 전달합니다.
 *
 * @author Luha
 * @since 1.0
 */
public record MemberTierResponse(
        int usage,
        Tier tier,
        int nextTierPrice,
        int accRate

) {
}
