package com.nhnacademy.twojopingfront.common.interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

public class AccessTokenRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    private static final String ACCESS_TOKEN_NAME = "accessToken=";

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        // SecurityContextHolder에서 액세스 토큰 가져오기
        Object accessToken = SecurityContextHolder.getContext().getAuthentication().getCredentials();
        if (accessToken instanceof String) {
            String accessTokenString = (String) accessToken;
            request.getHeaders().add(HttpHeaders.COOKIE, ACCESS_TOKEN_NAME + accessTokenString);
        }

        // 요청 진행
        return execution.execute(request, body);
    }
}
