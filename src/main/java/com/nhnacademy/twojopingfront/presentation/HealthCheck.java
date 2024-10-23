package com.nhnacademy.twojopingfront.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthCheck {

    @GetMapping("/hello")
    public String healthCheck() {
        return "Hello, world";
    }
}
