package com.nhnacademy.twojopingfront.user.mypage.controller;

import com.nhnacademy.twojopingfront.user.member.adaptor.MemberAdaptor;
import com.nhnacademy.twojopingfront.user.member.dto.response.MemberAddressResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageViewController {
    private final MemberAdaptor memberAdaptor;


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

        List<MemberAddressResponseDto> addresses = memberAdaptor.getMemberAddresses();
        model.addAttribute("addresses", addresses);
        return "user/mypage/address-list";
    }

    @GetMapping("/review-history")
    public String reviewHistoryView(Model model) {

        return "user/mypage/review-history";
    }
}
