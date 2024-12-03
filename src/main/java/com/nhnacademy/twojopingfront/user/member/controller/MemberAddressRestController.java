package com.nhnacademy.twojopingfront.user.member.controller;

import com.nhnacademy.twojopingfront.common.annotation.RedirectOnError;
import com.nhnacademy.twojopingfront.user.member.adaptor.MemberAdaptor;
import com.nhnacademy.twojopingfront.user.member.dto.request.AddressUpdateRequestDto;
import com.nhnacademy.twojopingfront.user.member.dto.request.MemberAddressRequestDto;
import com.nhnacademy.twojopingfront.user.member.dto.response.AddressDeleteResponseDto;
import com.nhnacademy.twojopingfront.user.member.dto.response.AddressUpdateResponseDto;
import com.nhnacademy.twojopingfront.user.member.dto.response.MemberAddressResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("members/address")
public class MemberAddressRestController {

    private final MemberAdaptor memberAdaptor;

    @RedirectOnError(url = "/mypage/address-list")
    @PostMapping
    public String createAddress(@Valid @ModelAttribute MemberAddressRequestDto requestDto,
                             Model model) {

        List<MemberAddressResponseDto> addresses = memberAdaptor.createAddress(requestDto);
        model.addAttribute("addresses", addresses);


        return "user/mypage/address-list";
    }
    @RedirectOnError(url = "/mypage/address-list")
    @PutMapping("/{member-address-id}")
    public String updateAddress(@Valid @ModelAttribute AddressUpdateRequestDto requestDto,
                                @PathVariable(name = "member-address-id") Long memberAddressId,
                                RedirectAttributes redirectAttributes) {

        AddressUpdateResponseDto response = memberAdaptor.updateAddress(requestDto, memberAddressId);

        redirectAttributes.addFlashAttribute("errorCode", response.memberAddressId());
        redirectAttributes.addFlashAttribute("errorMessage", response.message());

        return "redirect:/mypage/address-list";
    }

    @RedirectOnError(url = "/mypage/address-list")
    @DeleteMapping("/{member-address-id}")
    public String deleteAddress(@PathVariable(name = "member-address-id") Long memberAddressId,
                                RedirectAttributes redirectAttributes) {

        AddressDeleteResponseDto response = memberAdaptor.deleteAddress(memberAddressId);

        redirectAttributes.addFlashAttribute("errorCode", response.memberAddressId());
        redirectAttributes.addFlashAttribute("errorMessage", response.message());

        return "redirect:/mypage/address-list";
    }


}
