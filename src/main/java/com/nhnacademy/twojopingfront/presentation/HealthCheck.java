package com.nhnacademy.twojopingfront.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    @GetMapping("/hello")
    public String helloCheck() {
        return "hello";
    }

    @GetMapping("/bye")
    public String byeCheck() {
        return "bye";
    }
}
