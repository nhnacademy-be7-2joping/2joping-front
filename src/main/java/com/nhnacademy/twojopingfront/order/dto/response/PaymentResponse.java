package com.nhnacademy.twojopingfront.order.dto.response;

public record PaymentResponse(
        String status,
        String message,
        String paymentKey
) {
}
