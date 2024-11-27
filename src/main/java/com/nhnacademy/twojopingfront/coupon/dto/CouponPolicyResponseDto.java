package com.nhnacademy.twojopingfront.coupon.dto;

/**
 * CouponPolicyResponseDto
 *
 * 이 클래스는 쿠폰 정책에 대한 응답 데이터를 담고 있는 DTO입니다.
 * 쿠폰 정책의 ID, 이름, 할인 유형, 할인 값, 사용 제한, 기간, 세부 정보, 최대 할인 금액 정보를 포함합니다.
 * 이 DTO는 쿠폰 정책 정보를 클라이언트에 전달하기 위해 사용됩니다.
 *
 * @author Luha
 * @since 1.0
 */
public record CouponPolicyResponseDto(
        Long couponPolicyId,
        String name,
        String discountType,
        Integer discountValue,
        Integer usageLimit,
        Integer duration,
        String detail,
        Integer maxDiscount
) {
}
