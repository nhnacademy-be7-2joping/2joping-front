package com.nhnacademy.twojopingfront.user.login.controller;

import com.nhnacademy.twojopingfront.user.login.dto.request.LoginRequestDto;
import com.nhnacademy.twojopingfront.user.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequestDto loginRequestDto) {
//        loginService

        return "index";
    }
}
