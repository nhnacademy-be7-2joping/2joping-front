package com.nhnacademy.twojopingfront.user.member.point.controller;

import com.nhnacademy.twojopingfront.common.util.MemberUtils;
import com.nhnacademy.twojopingfront.user.member.point.dto.GetMyPageSimplePointHistoriesResponse;
import com.nhnacademy.twojopingfront.user.member.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/points")
@RequiredArgsConstructor
public class PointController {

//    private final PointService pointService;
//
//    @GetMapping("/histories")
//    public String getMyPage(Model model) {
//        Long customerId = MemberUtils.getCustomerId();
//        GetMyPageSimplePointHistoriesResponse response = pointService.getMyPageSimplePointHistories(customerId);
//        model.addAttribute("memberPoint", response.memberPoint());
//        model.addAttribute("getSimplePointHistoriesResponses", response.getSimplePointHistoriesResponses());
//        return "user/mypage/mypage"; // Thymeleaf 템플릿 이름
//    }

}
