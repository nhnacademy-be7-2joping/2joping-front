package com.nhnacademy.twojopingfront.admin.couponPolicy.dto;

import com.nhnacademy.twojopingfront.admin.couponPolicy.enums.DiscountType;

public record UpdateCouponPolicyRequest(

        String name,
        DiscountType discountType,
        Integer discountValue,
        Integer usageLimit,
        Integer duration,
        String detail,
        Integer maxDiscount,
        Boolean isActive
) {
}