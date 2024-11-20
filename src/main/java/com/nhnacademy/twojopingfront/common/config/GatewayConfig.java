package com.nhnacademy.twojopingfront.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.twojopingfront.common.error.handler.CustomResponseErrorHandler;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * GatewayConfig 클래스는 게이트웨이 서버와의 통신을 위한 설정을 제공합니다.
 * 외부 프로퍼티 파일에서 게이트웨이 URL을 로드하고, RestTemplate 빈을 생성하여
 * 외부 API 호출에 사용할 수 있도록 합니다.
 * @author Luha
 * @since 1.0
 */
@Configuration
@PropertySource("classpath:properties/gateway.properties")
public class GatewayConfig {

    @Value("${gateway.base-url}") //게이트웨이 기본 URL, 외부 properties 파일에서 로드됨
    private String gatewayBaseUrl;

    /**
     * RestTemplate을 빈으로 등록하여 애플리케이션 전체에서 HTTP 요청에 사용할 수 있도록 합니다.
     *
     * @return RestTemplate 인스턴스
     */
    @Bean
    public RestTemplate restTemplate(ObjectMapper objectMapper, HttpServletRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new CustomResponseErrorHandler(objectMapper));

        // 쿠키를 자동으로 추가하는 인터셉터 설정
        restTemplate.getInterceptors().add((httpRequest, body, execution) -> {
            if (request.getCookies() != null) {
                StringBuilder cookiesBuilder = new StringBuilder();
                String accessTokenValue = null;

                // 모든 쿠키를 순회
                for (Cookie cookie : request.getCookies()) {
                    // 기존 쿠키를 Cookie 헤더에 추가
                    cookiesBuilder.append(cookie.getName()).append("=").append(cookie.getValue()).append("; ");

                    // accessToken이 있는지 확인
                    if ("accessToken".equals(cookie.getName())) {
                        accessTokenValue = cookie.getValue();
                    }
                }

                // accessToken 값을 Cookie 헤더에 추가
                if (accessTokenValue != null) {
                    cookiesBuilder.append("accessToken=").append(accessTokenValue).append("; ");
                }

                // Cookie 헤더 추가
                if (!cookiesBuilder.isEmpty()) {
                    // 마지막 "; " 제거
                    cookiesBuilder.setLength(cookiesBuilder.length() - 2);
                    httpRequest.getHeaders().add("Cookie", cookiesBuilder.toString());
                }
            }

            return execution.execute(httpRequest, body);
        });

        return restTemplate;
    }
    /**
     * 게이트웨이 기본 URL을 반환합니다.
     *
     * @return 게이트웨이 서버의 기본 URL 문자열
     */
    public String getGatewayBaseUrl() {
        return gatewayBaseUrl;
    }
}