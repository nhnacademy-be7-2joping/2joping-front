package com.nhnacademy.twojopingfront.common.base.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index(@CookieValue(name = "accessToken", defaultValue = "") String accessToken, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth != null ? auth.getName() : "anonymous";
        model.addAttribute("token", accessToken);

        return "index";
    }
}
