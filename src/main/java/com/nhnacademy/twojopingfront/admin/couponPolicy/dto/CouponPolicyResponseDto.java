package com.nhnacademy.twojopingfront.admin.couponPolicy.dto;

public record CouponPolicyResponseDto(
        Long couponPolicyId,
        String name,
        String discountType,
        Integer discountValue,
        Integer usageLimit,
        Integer duration,
        String detail,
        Integer maxDiscount,
        Boolean isActive
) {

}
