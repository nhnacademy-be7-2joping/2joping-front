package com.nhnacademy.twojopingfront.point.controller;

import com.nhnacademy.twojopingfront.common.util.MemberUtils;
import com.nhnacademy.twojopingfront.point.dto.response.GetMyPageDetailPointHistory;
import com.nhnacademy.twojopingfront.point.service.PointDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class PointDetailController {
    private final PointDetailService pointDetailService;

    /**
     * 포인트 상세 내역 가져오기
     *
     * @return 포인트 상세 내역 데이터
     */
    @GetMapping("/points/histories/details")
    public String getDetailHistories(Model model) {
        Long customerId = MemberUtils.getCustomerId();

        GetMyPageDetailPointHistory response = pointDetailService.getMyPageDetailPointHistory(customerId);

        model.addAttribute("memberPoint", response.memberPoint());
        model.addAttribute("getDetailPointHistoriesResponses", response.getDetailPointHistoriesResponses());
        return "point/point-history";
    }
}
