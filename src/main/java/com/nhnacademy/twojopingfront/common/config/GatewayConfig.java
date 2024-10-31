package com.nhnacademy.twojopingfront.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.twojopingfront.common.error.handler.CustomResponseErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

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
    public RestTemplate restTemplate(ObjectMapper objectMapper) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new CustomResponseErrorHandler(objectMapper));
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