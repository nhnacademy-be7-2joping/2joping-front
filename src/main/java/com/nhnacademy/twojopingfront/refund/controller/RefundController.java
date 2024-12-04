package com.nhnacademy.twojopingfront.refund.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/refunds")
@RequiredArgsConstructor

public class RefundController {

    @GetMapping("/new")
    public String showRefundRequestForm() {
        return "refund/refund-request-form";
    }



}


