package com.nhnacademy.twojopingfront.common.error.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.twojopingfront.common.error.exception.backServer.CustomApiException;
import com.nhnacademy.twojopingfront.common.error.dto.ErrorResponseDto;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * `CustomResponseErrorHandler` 클래스는 Spring RestTemplate에서 발생하는 HTTP 오류에 대해 사용자 정의 오류 처리를 제공하는 핸들러입니다.
 * 서버로부터 반환된 응답이 성공적인(2xx) 상태 코드가 아닌 경우 해당 오류를 감지하고 처리합니다.
 *
 * 주로 클라이언트 측 오류(4xx)와 서버 측 오류(5xx)를 처리하며, 응답 바디에서 `ErrorResponseDto` 객체를 읽어와
 * 사용자 정의 예외 `CustomApiException`을 발생시켜 애플리케이션이 오류를 구체적으로 다룰 수 있도록 지원합니다.
 * @author Luha
 * @since 1.0
 */
public class CustomResponseErrorHandler implements ResponseErrorHandler {

    private final ObjectMapper objectMapper;


    /**
     * CustomResponseErrorHandler 생성자.
     *
     * @param objectMapper `ObjectMapper`를 사용하여 JSON 응답을 `ErrorResponseDto` 객체로 변환합니다.
     */
    public CustomResponseErrorHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 응답이 성공적이지 않은 상태(2xx 이외)인 경우 오류로 간주합니다.
     *
     * @param response 서버의 클라이언트 HTTP 응답.
     * @return 성공이 아닌 상태 코드를 가진 응답일 경우 true.
     * @throws IOException 입출력 오류가 발생한 경우.
     */
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return !response.getStatusCode().is2xxSuccessful();
    }

    /**
     * 오류 응답에 대한 처리를 수행합니다.
     * 클라이언트 오류(4xx)나 서버 오류(5xx)일 경우 응답을 `ErrorResponseDto`로 변환하고 `CustomApiException`을 발생시킵니다.
     *
     * @param response 서버의 클라이언트 HTTP 응답.
     * @throws IOException 오류 응답에서 데이터를 읽어오는 동안 오류가 발생한 경우.
     * @throws CustomApiException 클라이언트/서버 오류 시 `ErrorResponseDto` 정보를 담은 사용자 정의 예외.
     */
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
            ErrorResponseDto errorResponse = objectMapper.readValue(response.getBody(), ErrorResponseDto.class);
            throw new CustomApiException(errorResponse);
        }
    }
}