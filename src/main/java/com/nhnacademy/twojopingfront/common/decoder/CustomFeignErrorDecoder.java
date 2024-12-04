package com.nhnacademy.twojopingfront.common.decoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.twojopingfront.common.error.dto.ErrorResponseDto;
import com.nhnacademy.twojopingfront.common.error.enums.RedirectType;
import com.nhnacademy.twojopingfront.common.error.exception.backServer.CustomFeignException;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Slf4j
public class CustomFeignErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper;

    public CustomFeignErrorDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        String content = getResponseBody(response);

        if (content.isEmpty()) {
            return getDefaultErrorResponse();
        }

        try {
            if (response.status() >= 500) {
                return FeignException.errorStatus(methodKey, response);
            }
            ErrorResponseDto<Void> errorResponseDto = objectMapper.readValue(content, ErrorResponseDto.class);
            return new CustomFeignException(errorResponseDto);
        } catch (JsonProcessingException e) {
            return getDefaultErrorResponse();
        }
    }

    private String getResponseBody(Response response) {
        try {
            if (response.body() != null) {
                return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            // 읽기 실패 시 빈 문자열 반환
            return "";
        }

        return "";
    }

    private CustomFeignException getDefaultErrorResponse() {
        ErrorResponseDto<Void> defaultErrorResponse = new ErrorResponseDto<>(
                400,
                HttpStatus.BAD_REQUEST.name(),
                "API 요청 중 문제 발생",
                RedirectType.REDIRECT
                , "/",
                null
        );

        return new CustomFeignException(defaultErrorResponse);
    }
}
