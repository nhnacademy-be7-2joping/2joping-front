package com.nhnacademy.twojopingfront.user.login.controller;

import com.nhnacademy.twojopingfront.order_detail.dto.response.OrderDetailResponseDto;
import com.nhnacademy.twojopingfront.user.login.dto.request.LoginNonMemberRequestDto;
import com.nhnacademy.twojopingfront.user.login.dto.request.LoginRequestDto;
import com.nhnacademy.twojopingfront.user.login.dto.response.LoginResponseDto;
import com.nhnacademy.twojopingfront.user.service.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * LoginController는 사용자의 로그인 요청을 처리하고, 로그인 페이지를 반환하는 역할을 수행
 * 사용자가 로그인 정보를 입력하면, LoginService를 통해 인증을 수행하고 HttpOnly 쿠키로 JWT 토큰을 설정.
 * 로그인 성공 시 access token과 refresh token이 쿠키에 저장됨.
 *
 * @author Sauter001
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    /**
     * 로그인 페이지
     *
     * @param message alert 위한 메시지 유형
     * @param model   로그인 상태에 따른 페이지 변경용
     * @return 로그인 페이지 view 이름
     */
    @GetMapping("/login")
    public String login(@RequestParam(value = "msg", required = false) String message,
                        String errorMessage, Model model) {
        model.addAttribute("message", message);

        return "login/login";
    }

    /**
     * 사용자의 로그인 요청을 처리하고, 인증 성공 시 access token과 refresh token을 쿠키에 저장.
     * LoginService를 통해 로그인 수행 후, 응답에 따라 쿠키 설정.
     *
     * @param loginRequestDto ID, 비밀번호 포함한 사용자 로그인 정보 추가
     * @param accessToken     로그인 여부 판정을 위한 access token 값. 기본은 빈 문자열
     * @param model           로그인 결과 판정을 위한 모델
     * @param response        로그인 성공 시 쿠키 설정에 사용함
     * @return 로그인 성공하는 경우 main 페이지로 redirect
     */
    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequestDto loginRequestDto, @CookieValue(value = "accessToken",
            defaultValue = "") String accessToken,
                        Model model,
                        HttpServletResponse response) {
        ResponseEntity<LoginResponseDto> responseEntity = loginService.login(loginRequestDto);
        LoginResponseDto responseDto = responseEntity.getBody();
        List<String> cookies = responseEntity.getHeaders().get(HttpHeaders.SET_COOKIE);

        if (cookies != null) {
            for (String cookieHeader : cookies) {
                String name = cookieHeader.split("=")[0];
                String value = cookieHeader.split("=")[1].split(";")[0];
                Cookie cookie = new Cookie(name, value);
                cookie.setMaxAge(60 * 60 * 24); // 1일
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
            }
        }

        model.addAttribute("token", accessToken);
        model.addAttribute(Objects.requireNonNull(responseDto));
        return "redirect:/";
    }

    @PostMapping("/login/non-member")
    public String loginNonMember(@ModelAttribute LoginNonMemberRequestDto loginNonMemberRequestDto, Model model) {
        List<OrderDetailResponseDto> orderDetailResponseDtos =
                loginService.getOrderForNonMember(loginNonMemberRequestDto);
        model.addAttribute("orders", orderDetailResponseDtos);
        return "/user/nonmember-orders";
    }

    /**
     * 사용자의 로그아웃 요청을 처리하는 메서드.
     * 서버에 저장된 인증 정보를 무효화하고, 클라이언트에 저장된 JWT access token과 refresh token 쿠키를 삭제함.
     *
     * @param request  저장된 jwt token을 가져오기 위한 요청
     * @param response 로그아웃 시 쿠키 삭제 적용한 응답
     * @return 메인 페이지(/)로 redirect
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        Map<String, String> jwtCookieMap = new HashMap<>();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accessToken") || cookie.getName().equals("refreshToken")) {
                    jwtCookieMap.put(cookie.getName(), cookie.getValue());
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    cookie.setHttpOnly(true);
                    response.addCookie(cookie);
                }
            }
        }

        loginService.logout(jwtCookieMap);

        return "redirect:/";
    }
}
