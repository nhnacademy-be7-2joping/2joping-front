package com.nhnacademy.twojopingfront.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index(@CookieValue(name = "accessToken", defaultValue = "") String accessToken, Model model) {
        model.addAttribute("token", accessToken);

        return "index";
    }
}
