package com.nhnacademy.twojopingfront.order_detail.controller;


import com.nhnacademy.twojopingfront.common.util.MemberUtils;
import com.nhnacademy.twojopingfront.order_detail.dto.response.OrderDetailResponseDto;
import com.nhnacademy.twojopingfront.order_detail.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/od")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @GetMapping("/{orderId}")
    public String getOrderDetail(Long orderId, Model model) {
        OrderDetailResponseDto responseDto = orderDetailService.getOrderDetail(orderId);
        model.addAttribute("orderDetail", responseDto);
                return "임시";
    }

    @GetMapping("/customer")
    public String getOrderDetailsByCustomerId(Model model) {
        Long customerId = MemberUtils.getCustomerId();

        List<OrderDetailResponseDto> responseDto = orderDetailService.getOrderDetailsByCustomerId(customerId.toString());

        return "임시";
    }
}
