package com.nhnacademy.twojopingfront.common.error.exception.backServer;

import com.nhnacademy.twojopingfront.common.error.dto.ErrorResponseDto;

/**
 * CustomApiException은 서버에서 발생한 API 오류를 나타내기 위한 예외 클래스입니다.
 * 이 클래스는 ErrorResponseDto를 사용하여 상세한 오류 정보를 포함하며, 클라이언트에게 전달될 오류 메시지를 관리합니다.
 *
 * @author Luha
 * @since 1.0
 */
public class CustomApiException extends RuntimeException {

    private final ErrorResponseDto errorResponse;

    public CustomApiException(ErrorResponseDto errorResponse) {
        super(errorResponse.errorMessage());
        this.errorResponse = errorResponse;
    }

    public ErrorResponseDto getErrorResponse() {
        return errorResponse;
    }
}