package com.nhnacademy.twojopingfront.order.dto.response;

public record PaymentResponse(
        String status,
        String orderId,
        String paymentKey,
        String method // 결제수단
) {
}
