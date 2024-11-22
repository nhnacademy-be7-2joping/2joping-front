package com.nhnacademy.twojopingfront.common.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;

public class AccessTokenInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        Object accessToken = SecurityContextHolder.getContext().getAuthentication().getCredentials();
        if (accessToken instanceof String) {
            requestTemplate.header(HttpHeaders.COOKIE, "accessToken=" + accessToken);
        }
    }
}
