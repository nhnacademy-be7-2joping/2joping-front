package com.nhnacademy.twojopingfront.common.gateway;

import com.nhnacademy.twojopingfront.common.config.GatewayConfig;
import com.nhnacademy.twojopingfront.common.error.exception.gateway.GatewayConnectFailException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * GatewayClient는 외부 게이트웨이 서버와의 통신을 담당하는 클래스입니다.
 * RestTemplate을 사용하여 다양한 HTTP 요청을 게이트웨이에 전송하고,
 * 그 응답을 처리합니다.
 * @author Luha
 * @since 1.0
 */
@Component
public class GatewayClient {

    private final RestTemplate restTemplate;
    private final String gatewayBaseUrl;

    /**
     * GatewayClient 생성자.
     * RestTemplate과 게이트웨이 서버의 기본 URL을 초기화합니다.
     *
     * @param restTemplate HTTP 요청을 전송할 RestTemplate 인스턴스
     * @param gatewayConfig 게이트웨이 URL을 제공하는 GatewayConfig 설정 객체
     */
    public GatewayClient(RestTemplate restTemplate, GatewayConfig gatewayConfig) {
        this.restTemplate = restTemplate;
        this.gatewayBaseUrl = gatewayConfig.getGatewayBaseUrl();
    }

    /**
     * 게이트웨이 서버로 HTTP 요청을 전송하는 메서드.
     * HTTP 메서드, 엔드포인트, 요청 데이터, 응답 타입을 받아 요청을 수행합니다.
     *
     * @param <T> 요청 데이터의 타입
     * @param <R> 응답 데이터의 타입
     * @param method HTTP 메서드 (GET, POST, PUT, DELETE 등)
     * @param endPoint 게이트웨이 서버의 엔드포인트 URL
     * @param data 요청 본문으로 전송할 데이터
     * @param responseType 응답 본문의 타입
     * @return ResponseEntity<R> 응답 데이터가 포함된 ResponseEntity 객체
     * @throws RuntimeException 게이트웨이 통신 오류 발생 시 예외 발생
     */
    public<T, R>ResponseEntity<R> sendToGateway(HttpMethod method, String endPoint, T data, Class<R> responseType) {
        String url = gatewayBaseUrl + endPoint;

        try{

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<T> requestEntity = new HttpEntity<>(data, headers);

            return restTemplate.exchange(url, method, requestEntity, responseType);

        }catch(GatewayConnectFailException e){

            throw new GatewayConnectFailException("게이트 웨이 통신 실패\n" + endPoint +"\n"+ e.getMessage());

        }
    }
    public<T, R>ResponseEntity<R> sendToGateway(HttpMethod method, String endPoint, T data, ParameterizedTypeReference<R> responseType) {
        String url = gatewayBaseUrl + endPoint;

        try{

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("X-Customer-Id", "1");

            HttpEntity<T> requestEntity = new HttpEntity<>(data, headers);

            return restTemplate.exchange(url, method, requestEntity, responseType);

        }catch(GatewayConnectFailException e){

            throw new GatewayConnectFailException("게이트 웨이 통신 실패\n" + endPoint +"\n"+ e.getMessage());

        }
    }

}
