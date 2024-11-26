package com.nhnacademy.twojopingfront.payment.controller.dto.response;

public record PaymentErrorResponse(
        String code,
        String message
) {
}
