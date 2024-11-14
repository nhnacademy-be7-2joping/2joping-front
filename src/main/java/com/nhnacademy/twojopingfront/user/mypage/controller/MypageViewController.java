package com.nhnacademy.twojopingfront.user.mypage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageViewController {

    @GetMapping
    public String mypageView(Model model) {

        return "user/mypage/mypage";
    }

    @GetMapping("/edit-profile")
    public String editProfileView(Model model) {

        return "user/mypage/edit-profile";
    }

    @GetMapping("/order-list")
    public String orderListView(Model model) {

        return "user/mypage/order-list";
    }

    @GetMapping("/return-exchange-history")
    public String returnExchangeHistoryView(Model model) {

        return "user/mypage/return-exchange-history";
    }

    @GetMapping("/address-list")
    public String addressListView(Model model) {

        return "user/mypage/address-list";
    }

    @GetMapping("/review-history")
    public String reviewHistoryView(Model model) {

        return "user/mypage/review-history";
    }
}
