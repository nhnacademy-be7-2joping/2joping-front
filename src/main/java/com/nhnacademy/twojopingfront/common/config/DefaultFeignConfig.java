package com.nhnacademy.twojopingfront.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.twojopingfront.common.decoder.CustomFeignErrorDecoder;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public class DefaultFeignConfig {
    private final ObjectMapper objectMapper;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            Object accessToken = SecurityContextHolder.getContext().getAuthentication().getCredentials();
            if (accessToken instanceof String) {
                if (!((String) accessToken).isBlank()) {
                    template.header(HttpHeaders.COOKIE, "accessToken=" + accessToken);
                }
            }
        };
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomFeignErrorDecoder(objectMapper);
    }
}
