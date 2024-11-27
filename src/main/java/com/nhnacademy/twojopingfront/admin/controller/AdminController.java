package com.nhnacademy.twojopingfront.admin.controller;

import com.nhnacademy.twojopingfront.admin.client.OrderClient;
import com.nhnacademy.twojopingfront.order.dto.request.OrderStateRequestDto;
import com.nhnacademy.twojopingfront.order.dto.response.OrderListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final OrderClient orderClient;

    @GetMapping
    public String adminHome() {
        return "admin/admin-index";
    }

    @GetMapping("/order-list")
    public String orderList(Model model) {

        List<OrderListResponseDto> orderList = orderClient.getOrders().getBody();

        model.addAttribute("orderList", orderList);

        return "admin/order/order-list";
    }

    @PostMapping("/order-list")
    public String updateOrderStatus(@RequestBody OrderStateRequestDto orderStateRequestDto) {

        //TODO api 삽입
        orderClient.updateOrderState(orderStateRequestDto);

        return "admin/order/order-list";
    }
}
