package com.nhnacademy.twojopingfront.user.controller;

import com.nhnacademy.twojopingfront.user.dto.request.LoginNonMemberRequestDto;
import com.nhnacademy.twojopingfront.user.dto.request.LoginRequestDto;
import com.nhnacademy.twojopingfront.user.dto.response.LoginResponseDto;
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

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String login(@CookieValue(value = "accessToken",
            defaultValue = "") String accessToken, Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequestDto loginRequestDto, @CookieValue(value = "accessToken",
            defaultValue = "") String accessToken,
                        Model model,
                        HttpServletRequest request,
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

    /**
     *
     * @return 비회원 로그인 페이지 view
     */
    @GetMapping("/login/non-member")
    public String loginNonMember() {
        return "login-nonmember";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accessToken") || cookie.getName().equals("refreshToken")) {
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    cookie.setHttpOnly(true);
                    response.addCookie(cookie);
                }
            }
        }

        return "redirect:/";
    }
}
