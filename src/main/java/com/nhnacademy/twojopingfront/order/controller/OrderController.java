package com.nhnacademy.twojopingfront.order.controller;

import com.nhnacademy.twojopingfront.user.dto.request.LoginNonMemberRequestDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class OrderController {
    /**
     * 비회원 주문 조회를 위한 API
     * @param nonMemberRequestDto 비회원 주문조회 페이지에서 받은 정보
     * @param model
     * @return 비회원 주문 정보 페이지
     */
    @GetMapping("/orders")
    public String orders(@RequestBody LoginNonMemberRequestDto nonMemberRequestDto, Model model) {
        // order 조회 API 필요

        return "customer-orders";
    }
}
