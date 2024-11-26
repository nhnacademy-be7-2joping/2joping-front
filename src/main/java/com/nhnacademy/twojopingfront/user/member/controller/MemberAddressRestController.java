package com.nhnacademy.twojopingfront.user.member.controller;

import com.nhnacademy.twojopingfront.common.annotation.RedirectOnError;
import com.nhnacademy.twojopingfront.user.member.adaptor.MemberAdaptor;
import com.nhnacademy.twojopingfront.user.member.dto.request.MemberAddressRequestDto;
import com.nhnacademy.twojopingfront.user.member.dto.response.MemberAddressResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("members/address")
public class MemberAddressRestController {

    private final MemberAdaptor memberAdaptor;

    @RedirectOnError(url = "/mypage/address-list")
    @PostMapping
    public String createUser(@Valid @ModelAttribute MemberAddressRequestDto requestDto,
                             Model model) {

        List<MemberAddressResponseDto> addresses = memberAdaptor.createAddress(requestDto);
        model.addAttribute("addresses", addresses);


        return "user/mypage/address-list";
    }

}
