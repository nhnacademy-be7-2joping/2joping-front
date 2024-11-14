package com.nhnacademy.twojopingfront.common.client;

import com.nhnacademy.twojopingfront.common.dto.JwtUserInfoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "jwtDecodeClient", url = "${gateway.auth-url}")
public interface JwtDecodeClient {
    @GetMapping("/user-info")
    JwtUserInfoResponseDto getUserInfo(@CookieValue("accessToken") String accessToken);

    @GetMapping("/refreshToken")
    ResponseEntity<?> refreshToken(@CookieValue("refreshToken") String refreshToken);
}
