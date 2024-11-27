package com.nhnacademy.twojopingfront.order.dto.response;

import java.time.LocalDate;

public record OrderListResponseDto(
        Long orderId,
        Long orderStateId,
        String customerName,
        String couponName,
        String receiver,
        String postalCode,
        String roadAddress,
        String detailAddress,
        Integer pointUsage,
        Integer shippingFee,
        Integer couponSalePrice,
        Integer totalPrice,
        LocalDate desiredDeliveryDate // LocalDate 타입
) {
}