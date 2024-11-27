package com.nhnacademy.twojopingfront.user.login.client;

import com.nhnacademy.twojopingfront.user.login.dto.request.LoginRequestDto;
import com.nhnacademy.twojopingfront.user.login.dto.response.LoginResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "loginClient", url = "localhost:8082")
public interface LoginClient {
    @PostMapping("/login")
    ResponseEntity<LoginResponseDto> doLogin(@RequestBody LoginRequestDto loginRequestDto);

    @GetMapping("/logout")
    ResponseEntity<?> doLogout(@CookieValue("accessToken") String accessToken,
                               @CookieValue("refreshToken") String refreshToken);
}
