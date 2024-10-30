package com.nhnacademy.twojopingfront.user.service;

import com.nhnacademy.twojopingfront.user.client.LoginClient;
import com.nhnacademy.twojopingfront.user.dto.request.LoginRequestDto;
import com.nhnacademy.twojopingfront.user.dto.response.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final LoginClient loginClient;

    public ResponseEntity<LoginResponseDto> login(LoginRequestDto requestDto) {
        ResponseEntity<LoginResponseDto> responseEntity = loginClient.doLogin(requestDto);
        return responseEntity;
    }
}
