package com.nhnacademy.twojopingfront.order.dto.request;

public record PaymentRequest(
        String orderId,
        String amount,
        String paymentKey
) {
}
