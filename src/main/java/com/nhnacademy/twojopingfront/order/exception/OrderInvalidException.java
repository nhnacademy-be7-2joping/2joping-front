package com.nhnacademy.twojopingfront.order.exception;

import com.nhnacademy.twojopingfront.common.error.dto.ErrorResponseDto;
import com.nhnacademy.twojopingfront.common.error.enums.RedirectType;
import com.nhnacademy.twojopingfront.common.error.exception.backServer.CustomApiException;

public class OrderInvalidException extends CustomApiException {
    public OrderInvalidException(String message) {
        this(createErrorResponse(message));
    }

    public OrderInvalidException(ErrorResponseDto<Void> errorResponseDto) {
        super(errorResponseDto);
    }

    private static ErrorResponseDto<Void> createErrorResponse(String message) {
        ErrorResponseDto<Void> errorResponseDto =
                new ErrorResponseDto<>(400, "Bad request", message,
                                       RedirectType.REDIRECT, "/orders/form", null
                );
        return errorResponseDto;
    }
}
