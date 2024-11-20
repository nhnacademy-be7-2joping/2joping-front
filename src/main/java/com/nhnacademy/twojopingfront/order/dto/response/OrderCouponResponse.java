package com.nhnacademy.twojopingfront.order.dto.response;

import java.time.LocalDateTime;

public record OrderCouponResponse(
    Long couponUsageId,
    String name,
    LocalDateTime invalidTime,
    String discountType,
    Integer discountValue,
    Integer maxDiscount
) {
}
