package com.nhnacademy.twojopingfront.admin.couponPolicy.dto;

import com.nhnacademy.twojopingfront.admin.couponPolicy.enums.DiscountType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCouponPolicyRequest(

        @NotNull(message = "정책 이름이 존재하지 않습니다.")
        String name,

        @Enumerated(EnumType.STRING)
        @NotNull(message = "할인 타입이 존재하지 않습니다.")
        DiscountType discountType,

        @NotNull(message = "할인 값이 존재하지 않습니다.")
        Integer discountValue,

        @NotNull(message = "사용 기한이 존재하지 않습니다.")
        Integer usageLimit,

        @NotNull(message = "기간이 존재하지 않습니다.")
        Integer duration,

        @NotNull(message = "정책 상세가 존재하지 않습니다.")
        String detail,

        @NotNull(message = "최대 할인 금액이 존재하지 않습니다.")
        Integer maxDiscount,

        Boolean isActive
) {
}
