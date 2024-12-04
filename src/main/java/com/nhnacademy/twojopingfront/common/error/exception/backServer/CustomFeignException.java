package com.nhnacademy.twojopingfront.common.error.exception.backServer;

import com.nhnacademy.twojopingfront.common.error.dto.ErrorResponseDto;
import lombok.Getter;

@Getter
public class CustomFeignException extends RuntimeException {
    private final ErrorResponseDto<Void> errorResponse;

    public CustomFeignException(ErrorResponseDto<Void> errorResponse) {
        super(errorResponse.errorMessage());
        this.errorResponse = errorResponse;
    }
}
