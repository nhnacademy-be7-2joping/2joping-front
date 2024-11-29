package com.nhnacademy.twojopingfront.user.service;

import com.nhnacademy.twojopingfront.common.error.dto.ErrorResponseDto;
import com.nhnacademy.twojopingfront.common.error.enums.RedirectType;
import com.nhnacademy.twojopingfront.common.error.exception.backServer.CustomApiException;
import com.nhnacademy.twojopingfront.user.login.client.LoginClient;
import com.nhnacademy.twojopingfront.user.login.dto.request.LoginRequestDto;
import com.nhnacademy.twojopingfront.user.login.dto.response.LoginResponseDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 인증 서버에 입력 받은 id, 비밀번호를 입력 받아 인증 서버에 로그인 요청 보냄
 * 로그인 성공 시 쿠키에 JWT access token, refresh token 포함한 응답 반환
 * 로그인 요청과 응답은 내부적으로 LoginClient를 통해 수행됨.
 *
 * @author Sauter001
 * @see LoginClient#doLogin(LoginRequestDto)
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class LoginService {
    private final LoginClient loginClient;

    /**
     * 사용자가 입력한 id와 비밀번호 기반으로 로그인 수행
     *
     * @param requestDto 사용자 로그인 요청 정보를 담은 DTO (ID와 비밀번호 포함)
     * @return LoginResponseDto로 customerId, loginId 반환
     */
    public ResponseEntity<LoginResponseDto> login(LoginRequestDto requestDto) {
        try {
            ResponseEntity<LoginResponseDto> responseEntity = loginClient.doLogin(requestDto);
            return responseEntity;
        } catch (FeignException e) {
            ErrorResponseDto<?> errorResponseDto = new ErrorResponseDto<>(
                    401,
                    "LOGIN_FAILED",
                    "로그인에 실패하였습니다.",
                    RedirectType.REDIRECT,
                    "/login",
                    null
            );
            throw new CustomApiException(errorResponseDto);
        }
    }

    public void logout(Map<String, String> jwtCookieMap) {
        loginClient.doLogout(jwtCookieMap.get("accessToken"), jwtCookieMap.get("refreshToken"));
    }
}
