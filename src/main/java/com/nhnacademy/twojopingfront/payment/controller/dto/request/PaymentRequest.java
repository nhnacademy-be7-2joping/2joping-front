package com.nhnacademy.twojopingfront.payment.controller.dto.request;

public record PaymentRequest(
        String orderId,
        String amount,
        String paymentKey
) {
}
